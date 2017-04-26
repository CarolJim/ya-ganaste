
package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.provisioning.manager.ProvisioningManager;
import com.pagatodo.yaganaste.freja.provisioning.presenter.ProvisioningPresenter;
import com.pagatodo.yaganaste.freja.provisioning.presenter.ProvisioningPresenterAbs;
import com.pagatodo.yaganaste.net.FirebaseInstanceServer;
import com.pagatodo.yaganaste.ui._manager.PagerAdapter;
import com.pagatodo.yaganaste.ui.account.login.ScreenSlidePagefragment;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.ui.otp.activities.OtpCodeActivity;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.ViewPagerCustomDuration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.PRIVACIDAD;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String SELECTION = "SELECTION";
    public final static String GO_TO_LOGIN = "GO_TO_LOGIN";
    public final static String GO_TO_REGISTER = "GO_TO_REGISTER";

    @BindView(R.id.btnMainCreateAccount)
    StyleButton btnMainCreateAccount;
    @BindView(R.id.txtMainLogin)
    StyleTextView txtMainLogin;
    @BindView(R.id.viewpager)
    AutoScrollViewPager pager;
    private PagerAdapter pagerAdapter;
    private List<Fragment> listSlides;
    private Preferencias pref;

    private int[] imgs = {
            R.mipmap.carrousel_one,
            R.mipmap.carrousel_two,
            R.mipmap.carrousel_three,
            R.mipmap.carrousel_four,
            R.mipmap.carrousel_five,
            R.mipmap.carrousel_six
    };

    private String[] topText = {
            "La Cuenta",
            "La Única App",
            "Recarga",
            "Paga",
            "Envía",
            "Compra"
    };

    private String[] bothText = {
            "que no te Cuesta Nada...",
            "que te da Dinero por Comprar",
            "Tiempo Aire y Tag",
            "tus Servicios",
            "y Recibe Dinero",
            "en Miles de Comercios"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();

        Log.i("FCM","IDNOTI: " + FirebaseInstanceId.getInstance().getToken());

        pref = App.getInstance().getPrefs();

    }

    private void initViews() {
        ButterKnife.bind(this);
        btnMainCreateAccount.setOnClickListener(this);
        listSlides = new ArrayList<Fragment>();

        for (int index = 0; index < imgs.length; index++) {
            listSlides.add(ScreenSlidePagefragment.newInstance(imgs[index], topText[index], bothText[index]));
        }


        String textLogin = getString(R.string.tienes_cuenta);
        SpannableString ss = new SpannableString(textLogin);
        ClickableSpan span1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                intent.putExtra(SELECTION,GO_TO_LOGIN);
                startActivity(intent);
            }
        };
        ss.setSpan(span1, 22, textLogin.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)),  22, textLogin.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtMainLogin.setText(ss);
        txtMainLogin.setMovementMethod(LinkMovementMethod.getInstance());

        /*
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        for(Fragment slide: listSlides){
            pagerAdapter.addFragment(slide);
        }
        this.pager.setAdapter(pagerAdapter);
        this.pager.startAutoScroll(7000);

        this.pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                // Page is not an immediate sibling, just make transparent
                if(position < -1 || position > 1) {
                    page.setAlpha(0);
                }
                // Page is sibling to left or right
                else if (position <= 0 || position <= 1) {

                    // Calculate alpha.  Position is decimal in [-1,0] or [0,1]
                    float alpha = (position <= 0) ? position + 1 : 1 - position;
                    page.setAlpha(alpha);

                }
                // Page is active, make fully visible
                else if (position == 0) {
                    page.setAlpha(1);
                }
            }
        });*/


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMainCreateAccount:
                Intent intent = new Intent(MainActivity.this, TabActivity.class);
                //intent.putExtra(SELECTION,GO_TO_REGISTER);
                startActivity(intent);
                break;
        }
    }


}

