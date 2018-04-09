package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._controllers.TarjetaActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.DongleBatteryHome;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.HomeFragmentPresenter;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;
import com.pagatodo.yaganaste.utils.customviews.NoSwipeViewPager;
import com.pagatodo.yaganaste.utils.customviews.yaganasteviews.CardEmisorSelected;
import com.pagatodo.yaganaste.utils.customviews.yaganasteviews.TabLayoutEmAd;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeTabFragment extends SupportFragment implements TabsView, TabLayoutEmAd.InviteAdquirenteCallback,
        AbstractAdEmFragment.UpdateBalanceCallback, TabLayoutEmAd.onBlockCard, TabLayoutEmAd.clikdongle {

    private static final int MY_PERMISSIONS_REQUEST_SOUND = 100;

    private String mTDC;
    PreferUserPresenter mPreferPresenter;

    private NoSwipeViewPager pagerAdquirente;
    private View rootView;
    private HomeFragmentPresenter homeFragmentPresenter;
    private TabLayoutEmAd tabLayoutEmAd;
    private GenericPagerAdapter pagerAdapter;
    private String nombreCompleto, cuentaUsuario;
    private int currentPage;

    //CircleImageView imageView;
    ImageView imageView;

    private CardEmisorSelected cardEmisorSelected;

    /*public static HomeTabFragment newInstance() {
        HomeTabFragment homeTabFragment = new HomeTabFragment();
        Bundle args = new Bundle();
        homeTabFragment.setArguments(args);
        return homeTabFragment;
    }

    public static HomeTabFragment newInstance(int setCurrentPage) {
        HomeTabFragment homeTabFragment = new HomeTabFragment();
        Bundle args = new Bundle();
        args.putInt("CURRENT_PAGE",setCurrentPage);
        homeTabFragment.setArguments(args);
        return homeTabFragment;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        imageView = (ImageView) getActivity().findViewById(R.id.imgNotifications);
        if (getArguments() != null){
            currentPage = getArguments().getInt("CURRENT_PAGE",0);
        }
        this.homeFragmentPresenter = new HomeFragmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_main_tab_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (tabLayoutEmAd != null) {
            tabLayoutEmAd.updatestatusCard();
        }
    }

    @Override
    public void initViews() {
        tabLayoutEmAd = rootView.findViewById(R.id.tab_em_adq);
        pagerAdquirente = rootView.findViewById(R.id.pager_adquirente);

        homeFragmentPresenter.getPagerData(ViewPagerDataFactory.TABS.HOME_FRAGMENT);

        tabLayoutEmAd.setInviteAdquirenteCallback(this);
        tabLayoutEmAd.setOnBlockCard(this);
        tabLayoutEmAd.setClickdongle(this);
        tabLayoutEmAd.setOnBlockCard(this);
        tabLayoutEmAd.setUpWithViewPager(pagerAdquirente);
         cardEmisorSelected = new CardEmisorSelected(getContext());


         cardEmisorSelected.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
         /*       UI.createCustomDialogextranjero("Ya Ganaste", "Texto", getFragmentManager(), getFragmentTag(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {

                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                }, " ", "Llamar");*/
                return false;
            }
        });

    }

    @Override
    public void loadViewPager(ViewPagerData viewPagerData) {
        pagerAdapter = new GenericPagerAdapter<>(getActivity(), getChildFragmentManager(), viewPagerData.getFragmentList(), viewPagerData.getTabData());
        pagerAdquirente.setAdapter(pagerAdapter);
        /*pagerAdquirente.setIsSwipeable(SingletonUser.getInstance().getDataUser().isEsAgente() &&
                SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == Recursos.CRM_DOCTO_APROBADO);*/
        pagerAdquirente.setCurrentItem(currentPage);
        pagerAdquirente.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });

    }


    @Override
    public void onInviteAdquirente() {
        if (onEventListener != null) {
            onEventListener.onEvent(TabActivity.EVENT_INVITE_ADQUIRENTE, null);
        }
    }

    @Override
    public void onUpdateBalance() {
        //tabLayoutEmAd.updateData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<Fragment> all = getFragments();
        for (Fragment current : all) {
            if (current instanceof PaymentsFragment) {
                current.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public PaymentsFragment getPaymentsFragment() {
        return (PaymentsFragment) pagerAdapter.getItem(1);
    }

    public void setCurrentItem(int item) {
        pagerAdquirente.setCurrentItem(item);
    }

    public String nombre() {
        UsuarioClienteResponse userData = SingletonUser.getInstance().getDataUser().getUsuario();

        String nombreprimerUser;

        String apellidoMostrarUser;
        if (userData.getPrimerApellido().isEmpty()) {
            apellidoMostrarUser = userData.getSegundoApellido();
        } else {
            apellidoMostrarUser = userData.getPrimerApellido();
        }
        nombreprimerUser = StringUtils.getFirstName(userData.getNombre());
        if (nombreprimerUser.isEmpty()) {
            nombreprimerUser = userData.getNombre();

        }

        //tv_name.setText(mName);
        //mNameTV.setText(nombreprimerUser+" "+apellidoMostrarUser);

        nombreCompleto = nombreprimerUser + " " + apellidoMostrarUser;

        return nombreCompleto;
    }


    @Override
    public void onLongClickBlockCard() {
        // Vibrate for 500 milliseconds
        /*Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        v.vibrate(100);
        String nombre = nombre();
        String cuenta = cuenta();
        Intent intent = new Intent(getContext(), TarjetaActivity.class);
        startActivity(intent);*/
    }

    private String cuenta() {
        UsuarioClienteResponse usuarioClienteResponse = SingletonUser.getInstance().getDataUser().getUsuario();
        mTDC = usuarioClienteResponse.getCuentas().get(0).getTarjeta();
        cuentaUsuario = (getResources().getString(R.string.tarjeta) + ": " + StringUtils.ocultarCardNumberFormat(mTDC));
        //mCuentaTV.setText(getResources().getString(R.string.tarjeta) + ": " + StringUtils.ocultarCardNumberFormat(mTDC));
        return cuentaUsuario;
    }

    @Override
    public void longclickdongle() {
        boolean isValid = true;

        int permissionCall = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.RECORD_AUDIO);

        // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
        if (permissionCall == -1) {
            ValidatePermissions.checkPermissions(getActivity(),
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST_SOUND);
            isValid = false;
        }

        if(isValid){
            Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(100);
            Intent intent = new Intent(getContext(), DongleBatteryHome.class);
            startActivity(intent);
        }
    }
}