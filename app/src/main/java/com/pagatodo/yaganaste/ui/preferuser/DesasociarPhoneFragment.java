package com.pagatodo.yaganaste.ui.preferuser;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferDesasociarView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;

/**
 * A simple {@link Fragment} subclass.
 * Se encarga de hacer las peticiones para Desasociar el dispositivo actual
 */
public class DesasociarPhoneFragment extends GenericFragment implements View.OnClickListener,
        IPreferDesasociarView {

    @BindView(R.id.fragment_desasociar_btn)
    StyleButton btn_desasociar;
    View rootview;
    PreferUserPresenter mPreferPresenter;
    Context mContext;
    /**
     * Acciones para controlar el Dialog de OnClick, al aceptar inciamos el proceso
     */
    DialogDoubleActions doubleActions = new DialogDoubleActions() {
        @Override
        public void actionConfirm(Object... params) {
            onEventListener.onEvent("DISABLE_BACK", true);
            mPreferPresenter.DesasociarToPresenter();
        }

        @Override
        public void actionCancel(Object... params) {

        }
    };
    /**
     * Acciones para controlar el Dialog de exito en desasociar, al aceptar enviamos el controlar a
     * PreferActivity que contiene el mecanimo para cerrar la app y dejarla en Main
     */
    DialogDoubleActions closeSession = new DialogDoubleActions() {
        @Override
        public void actionConfirm(Object... params) {
            onEventListener.onEvent("DESASOCIAR_CLOSE_SESSION", null);
        }

        @Override
        public void actionCancel(Object... params) {

        }
    };

    public DesasociarPhoneFragment() {
        // Required empty public constructor
    }

    public static DesasociarPhoneFragment newInstance() {
        DesasociarPhoneFragment fragmentDesasociarPhone = new DesasociarPhoneFragment();
        return fragmentDesasociarPhone;
    }

    @Override
    public void onAttach(Context context) {
        mPreferPresenter = ((PreferUserActivity) getActivity()).getPreferPresenter();
        mPreferPresenter.setIView(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_desasociar_phone, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        btn_desasociar.setOnClickListener(this);
    }

    /**
     * Listener que abre el Dialog para confirmar el Desasociar dispositivo
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            UI.createSimpleCustomDialog("", getResources().getString(R.string.deseaDesasociarDispositivo), getFragmentManager(),
                    doubleActions, true, true);
        } else {
            // Toast.makeText(this, "Is OffLine Privacidad", Toast.LENGTH_SHORT).show();
            showDialogCustom(getResources().getString(R.string.no_internet_access));
        }

    }

    /**
     * Exito al Desasociar, mostramos un dialogo para notificar y cerrar la aplicacion, en el
     * iteractor ya cerramos la session.
     *
     * @param mensaje
     */
    @Override
    public void sendSuccessDesasociarToView(String mensaje) {
        //showDialogCustom(mensaje);
        SingletonUser.getInstance().setCardStatusId(null);
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                closeSession, true, false);
        App.getInstance().getPrefs().clearPreferences();
        App.getInstance().clearCache();
        RequestHeaders.clearPreferences();
    }

    /**
     * Mensaje de error de conexion al no tener 200.
     *
     * @param mensaje
     */
    @Override
    public void sendErrorServerView(String mensaje) {
        showDialogCustom(mensaje);
        onEventListener.onEvent("DISABLE_BACK", false);
    }

    /**
     * Exito en la peticion de servidor y Fail en el cambio de Pass.
     * Tambien se usa para mostrar un error de conexion al servidor, desde el Presenter para no tener
     * un tercer metodo
     *
     * @param mensaje
     */
    @Override
    public void sendErrorDesasociarToView(String mensaje) {
        hideLoader();
        showDialogCustom(mensaje);
        onEventListener.onEvent("DISABLE_BACK", false);
    }

    public void hideLoader() {
        // progressLayout.setVisibility(GONE);
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
    }

    /**
     * Adminsitrador de mensaje que no tienen acciones, solo informativos, usados comunmente en errores
     *
     * @param mensaje
     */
    public void showDialogCustom(final String mensaje) {
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

//    /**
//     * HEredamos este metodo de GenericFRagment, y por medio de la referencia super, comunicamos
//     * el DataSourceResult para el paso final
//     * @param response
//     */
//    @Override
//    public void errorSessionExpired(DataSourceResult response) {
//        super.errorSessionExpired(response);
//    }
}
