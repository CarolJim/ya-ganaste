package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;
import com.pagatodo.yaganaste.utils.customviews.NoSwipeViewPager;
import com.pagatodo.yaganaste.utils.customviews.yaganasteviews.CardEmisorSelected;
import com.pagatodo.yaganaste.utils.customviews.yaganasteviews.TabLayoutEmAd;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @author Juan Guerra on 10/11/2016.
 */

public class HomeTabFragment extends SupportFragment implements TabsView, TabLayoutEmAd.InviteAdquirenteCallback,
        AbstractAdEmFragment.UpdateBalanceCallback, TabLayoutEmAd.onBlockCard, TabLayoutEmAd.clikdongle {

    @BindView(R.id.my_card_name_user)
    TextView mNameTV;
    @BindView(R.id.my_card_num_cuenta)
    TextView mCuentaTV;
    private String mTDC;
    PreferUserPresenter mPreferPresenter;

    private NoSwipeViewPager pagerAdquirente;
    private View rootView;
    private HomeFragmentPresenter homeFragmentPresenter;
    private TabLayoutEmAd tabLayoutEmAd;
    private GenericPagerAdapter pagerAdapter;
    private String nombreCompleto, cuentaUsuario;

    CircleImageView imageView;

    private CardEmisorSelected cardEmisorSelected;

    public static HomeTabFragment newInstance() {
        HomeTabFragment homeTabFragment = new HomeTabFragment();
        Bundle args = new Bundle();
        homeTabFragment.setArguments(args);
        return homeTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = (CircleImageView) getActivity().findViewById(R.id.imgToRight_prefe);
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
        tabLayoutEmAd = (TabLayoutEmAd) rootView.findViewById(R.id.tab_em_adq);
        pagerAdquirente = (NoSwipeViewPager) rootView.findViewById(R.id.pager_adquirente);
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
                UI.createCustomDialogextranjero("Ya Ganaste", "Texto", getFragmentManager(), getFragmentTag(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {

                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                }, " ", "Llamar");
                return false;
            }
        });

    }

    @Override
    public void loadViewPager(ViewPagerData viewPagerData) {
        pagerAdapter = new GenericPagerAdapter<>(getActivity(), getChildFragmentManager(), viewPagerData.getFragmentList(), viewPagerData.getTabData());
        pagerAdquirente.setAdapter(pagerAdapter);
        pagerAdquirente.setIsSwipeable(SingletonUser.getInstance().getDataUser().isEsAgente() &&
                SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == Recursos.CRM_DOCTO_APROBADO);
    }


    @Override
    public void onInviteAdquirente() {
        if (onEventListener != null) {
            onEventListener.onEvent(TabActivity.EVENT_INVITE_ADQUIRENTE, null);
        }
    }

    @Override
    public void onUpdateBalance() {
        tabLayoutEmAd.updateData();
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
        String nombre = nombre();
        String cuenta = cuenta();
        Intent intent = new Intent(getContext(), TarjetaActivity.class);
        startActivity(intent);
    }


    private Boolean estadoTarejta() {
        String statusId = SingletonUser.getInstance().getCardStatusId();
        Boolean estado;
        if (statusId != null && statusId.equals(Recursos.ESTATUS_DE_NO_BLOQUEADA)) {
            //mycard_switch.setChecked(false);
            //imgStatus.setImageResource(R.drawable.ic_candado_open);
            estado = true;
        } else {
            //mycard_switch.setChecked(true);
            //imgStatus.setImageResource(R.drawable.ic_candado_closed);
            estado = false;
        }
        return estado;
    }

    private String cuenta() {
        UsuarioClienteResponse usuarioClienteResponse = SingletonUser.getInstance().getDataUser().getUsuario();
        mTDC = usuarioClienteResponse.getCuentas().get(0).getTarjeta();
        cuentaUsuario = (getResources().getString(R.string.tarjeta) + ": " + StringUtils.ocultarCardNumberFormat(mTDC));
        //mCuentaTV.setText(getResources().getString(R.string.tarjeta) + ": " + StringUtils.ocultarCardNumberFormat(mTDC));
        return cuentaUsuario;
    }

    private void checkDataCard() {
        /**
         * Si tenemos Internet consumos el servicio para actualizar la informacion de la ceunta,
         * consulamos el estado de bloquero y la informacion de ultimo acceso
         * else mostramos la unformacion que traemos desde sl Singleton
         */
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            // Verificamos el estado de bloqueo de la Card
            String f = SingletonUser.getInstance().getCardStatusId();
            if (f == null || f.isEmpty() || f.equals("0")) {
                mPreferPresenter.toPresenterEstatusCuenta(mTDC);
            }

            // Obtenemos el estado de la Card actual
            // Creamos el objeto ActualizarAvatarRequest
            // TODO Frank se comento este metodo debido a cambios, borrar en versiones posteriores
            /*ActualizarDatosCuentaRequest datosCuentaRequest = new ActualizarDatosCuentaRequest();
            mPreferPresenter.sendPresenterUpdateDatosCuenta(datosCuentaRequest);*/
        } else {
            showDialogMesage(getResources().getString(R.string.no_internet_access));
        }
    }

    private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

    @Override
    public void longclickdongle() {
        Intent intent = new Intent(getContext(), DongleBatteryHome.class);
        startActivity(intent);
    }
}