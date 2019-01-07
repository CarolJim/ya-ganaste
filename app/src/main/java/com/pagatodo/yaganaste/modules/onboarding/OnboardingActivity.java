package com.pagatodo.yaganaste.modules.onboarding;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.airbnb.lottie.parser.ColorParser;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerCatalogoRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCatalogosResponse;
import com.pagatodo.yaganaste.data.room_db.AppDatabase;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.onboarding.fragments.ScreenSlidePageFragment;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui._controllers.SplashActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IGetInfoFromFirebase;
import com.pagatodo.yaganaste.utils.FileDownloadListener;
import com.pagatodo.yaganaste.utils.ForcedUpdateChecker;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.NO_SIM_CARD;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Recursos.CATALOG_VERSION;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TYPE;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_SPLASH;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;

public class OnboardingActivity extends LoaderActivity
        implements OnboardingContracts.Presenter, View.OnClickListener, FileDownloadListener,
        IRequestResult, IGetInfoFromFirebase {
    private Preferencias preferencias;
    private AppDatabase db;
    private static final String TAG = "SplashActivity";
    boolean downloadFile = false;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    public TabLayout tabLayout;
    private OnboardingRouter router;
    public TextView skeepIntro;

    private DatabaseReference mDatabase;
    private List<ElementOnboarding> url;

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, OnboardingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide_pager);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ya-Ganaste-5_0").child("ASSTS").child("ONBRDNG");
        url = new ArrayList<>();
        skeepIntro = findViewById(R.id.skeep_intro);
        router = new OnboardingRouter(this);

        preferencias = App.getInstance().getPrefs();
        db = App.getAppDatabase();
        preferencias = App.getInstance().getPrefs();
        // new DatabaseManager().checkCountries();
        //new ForcedUpdateChecker(this).getUrls(this);

        Intent intent = null;
        if ((preferencias.containsData(IS_OPERADOR))||(preferencias.containsData(HAS_SESSION) || !RequestHeaders.getTokenauth().isEmpty())) {
            intent = new Intent(OnboardingActivity.this, MainActivity.class);
            intent.putExtra(SELECTION, MAIN_SCREEN);
            startActivity(intent);
            this.finish();
        }
        initViews();
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }


    @Override
    public void onBackPressed() {
        /*if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }*/
        super.onBackPressed();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void initViews() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager, true);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ElementOnboarding image = new ElementOnboarding(snapshot.getValue(String.class));
                        url.add(image);
                    }
                }
                setAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(eventListener);
        skeepIntro.setOnClickListener(this::onClick);
        SpannableString text = new SpannableString("Saltar Intro");
        text.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        skeepIntro.setText(text);
        skeepIntro.setTextColor(Color.parseColor("#FFFFFF"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skeep_intro:
                router.showStart(Direction.FORDWARD);
        }
    }

    @Override
    public void returnUri(Uri uriPath, String typeData) {
        File af = new File(String.valueOf(uriPath));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        String typeMime = "";
        switch (typeData) {
            case "1":
                typeMime = "text/*";
                break;
            case "2":
                typeMime = "image/*";
                break;
            case "3":
                typeMime = "video/*";
                break;

        }

        /**
         * Hacemos SET de la orden para abrir el File por medio de Uri.fromFile(af), si intentamos
         * abrir directo el URI no funciona, necesitamos ambas combinaciones
         */
        intent.setDataAndType(Uri.fromFile(af), typeMime);
        startActivity(intent);
        hideLoader();
        this.finish();
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        switch (result.getWebService()) {
            case OBTENER_CATALOGOS:
                ObtenerCatalogosResponse response = (ObtenerCatalogosResponse) result.getData();
                if (response.getCodigoRespuesta() == CODE_OK && response.getData() != null) {
                    preferencias.saveData(CATALOG_VERSION, response.getData().getVersion());
                    new DatabaseManager().insertComercios(response.getData().getComercios());
                }
               // callNextActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {

    }

    private void callNextActivity() {
        Intent intent = null;
        /*TODO Descomentar para validar flujo correctamente*/
        if (ValidatePermissions.validateSIMCard(this)) {
            /*if(!RequestHeaders.getTokenauth().isEmpty()) {
                intent = new Intent(SplashActivity.this, AccountActivity.class);
                intent.putExtra(SELECTION,GO_TO_LOGIN);
            }else {*/
            intent = new Intent(OnboardingActivity.this, MainActivity.class);
            intent.putExtra(SELECTION, MAIN_SCREEN);
            //}
        } else {
            intent = new Intent(OnboardingActivity.this, MainActivity.class);
            intent.putExtra(SELECTION, NO_SIM_CARD);
        }

        /**
         * Iniciamos el flujo normal solo si nuestra bandera de downloadFile es falsa, esto significa
         * que no estamos descargando nada, en caso contrario en automatico se hace el proceso para
         * descargar y abrir por el hilo de notificacion
         */
        Bundle bundle = new Bundle();
        bundle.putString(CONNECTION_TYPE, Utils.getTypeConnection());
        FirebaseAnalytics.getInstance(this).logEvent(EVENT_SPLASH, bundle);
        JSONObject props = new JSONObject();
        if (!BuildConfig.DEBUG) {
            try {
                props.put(CONNECTION_TYPE, Utils.getTypeConnection());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            App.mixpanel.track(EVENT_SPLASH, props);
        }
        if (!downloadFile) {
            startActivity(intent/*, SPLASH_ACTIVITY_RESULT, options.toBundle()*/);
            OnboardingActivity.this.finish();
        }

    }

    @Override
    public void onUrlsDownload() {
        new ForcedUpdateChecker(this).getPins(this);
    }

    @Override
    public void onPinsDownload() {
        final IRequestResult iRequestResult = this;
        try {
            ObtenerCatalogoRequest request = new ObtenerCatalogoRequest();
            request.setVersion(preferencias.loadData(CATALOG_VERSION).isEmpty() ? "1" : preferencias.loadData(CATALOG_VERSION));
            ApiAdtvo.obtenerCatalogos(request, iRequestResult);
        } catch (OfflineException e) {
            e.printStackTrace();
            callNextActivity();
        }
    }

    @Override
    public void onError() {
        finish();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private List<ElementOnboarding> list;

        public ScreenSlidePagerAdapter(FragmentManager supportFragmentManager, List<ElementOnboarding> list) {
            super(supportFragmentManager);
            this.list = list;
        }

        @SuppressLint("ResourceType")
        @Override
        public Fragment getItem(int i) {
            return new ScreenSlidePageFragment(list.get(i).getUrl_img());
            //return new ScreenSlidePageFragment();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @SuppressLint("ResourceAsColor")
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (position != 0) {
                //skeepIntro.setTextColor(R.color.blue_text_wallet);
            }
            return super.instantiateItem(container, position);
        }
    }

    void setAdapter() {
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), url);

        mPager.setAdapter(mPagerAdapter);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0){
                    skeepIntro.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    skeepIntro.setTextColor(Color.parseColor("#00A1E1"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mPager.setCurrentItem(0);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager, true);
    }

}
