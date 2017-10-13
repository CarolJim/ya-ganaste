package com.pagatodo.yaganaste.ui.account.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ILoginView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.BorderTitleLayout;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_BLOCK_CARD_BACK;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * Encargada de bloquear la Card por medio de nuestra contraseña.
 * 1 - Pedimos contraseña
 * 2 - Iniciamos session rapida
 * 3 - Actualizamos el estado de la session
 * 4 - Cerramos session
 */
public class BlockCardFragment extends GenericFragment implements ValidationForms,
        ILoginView, IMyCardView {

    public static final int BLOQUEO = 1;
    public static final int DESBLOQUEO = 2;

    @BindView(R.id.editUserPassword)
    CustomValidationEditText edtUserPass;
    @BindView(R.id.btnLoginExistUser)
    StyleButton btnLogin;
    @BindView(R.id.cardBlue)
    ImageView cardBlue;
    @BindView(R.id.cardGray)
    ImageView cardGray;
    @BindView(R.id.layoutContent)
    BorderTitleLayout tittleBorder;
    @BindView(R.id.txtMessageCard)
    StyleTextView txtMessageCard;

    private String password;
    private View rootview;
    private AccountPresenterNew accountPresenter;
    private PreferUserPresenter mPreferPresenter;
    boolean isChecked;
    private String cardStatusId;
    private int statusBloqueo;

    public BlockCardFragment() {
        // Required empty public constructor
    }

    public static BlockCardFragment newInstance() {
        BlockCardFragment fragment = new BlockCardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accountPresenter = ((AccountActivity) getActivity()).getPresenter();
            accountPresenter.setIView(this);
            mPreferPresenter = new PreferUserPresenter(this);
            mPreferPresenter.setIView(this);

            // Consultamos el estado del Singleton, que tiene el estado de nuestra tarjeta
            cardStatusId = App.getInstance().getStatusId();
            // cardStatusId = "1"; // Linea de TEst, eliminamos cuando el anterior funcione en actualizar
            if (cardStatusId == null) {
                cardStatusId = "1";
            }

            if (cardStatusId.equals("2")) {
                // Significa que la card esta bloqueada, despues de la operacion pasa a desbloqueada
                statusBloqueo = BLOQUEO;
            } else {
                // Significa que la card esta desbloqueada, despues de la operacion pasa a bloqueada
                statusBloqueo = DESBLOQUEO;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_block_card, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        if (cardStatusId.equals("1")) {
            // La tarjeta esta DESBLOQUEADA, mostramos la cCard Azul
            cardBlue.setVisibility(View.VISIBLE);
            cardGray.setVisibility(View.GONE);

            // Cambiamos las palabras a Bloquear
            tittleBorder.setTitle(App.getContext().getResources().getString(R.string.bloquear_tarjeta));
            txtMessageCard.setText(App.getContext().getResources().getString(R.string.ingresa_pass_block));

            // Cambiamos el texto del BTN
            btnLogin.setText(App.getContext().getResources().getString(R.string.txt_bloquear));
        } else {
            // La tarjeta esta BLOQUEADA, mostramos la cCard Gray
            cardBlue.setVisibility(View.GONE);
            cardGray.setVisibility(View.VISIBLE);

            // Cambiamos las palabras a Desboquear
            tittleBorder.setTitle(App.getContext().getResources().getString(R.string.desbloquear_tarjeta));
            txtMessageCard.setText(App.getContext().getResources().getString(R.string.ingresa_pass_desblock));

            // Cambiamos el texto del BTN
            btnLogin.setText(App.getContext().getResources().getString(R.string.txt_desbloquear));
        }
    }

    @OnClick(R.id.btnLoginExistUser)
    public void validatePass() {
        //  Toast.makeText(App.getContext(), "Click", Toast.LENGTH_SHORT).show();
        validateForm();
    }


    @Override
    public void setValidationRules() {
        edtUserPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtUserPass.imageViewIsGone(true);
                } else {
                    if (edtUserPass.getText().isEmpty() || !edtUserPass.isValidText()) {
                        edtUserPass.setIsInvalid();
                    } else if (edtUserPass.isValidText()) {
                        edtUserPass.setIsValid();
                    }
                }
            }
        });

        edtUserPass.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                edtUserPass.imageViewIsGone(true);
            }
        });

        // Asignamos el OnEditorActionListener a este CustomEditext para el efecto de consumir el servicio
        edtUserPass.addCustomEditorActionListener(new DoneOnEditorActionListener());

    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        String errorMsg = null;

        if (password.isEmpty()) {
            errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required) : errorMsg;
            edtUserPass.setIsInvalid();
            isValid = false;
        }

        if (isValid) {
            onValidationSuccess();
        } else {
            showDialogMesage(errorMsg, 0);
        }
    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        Preferencias preferencias = App.getInstance().getPrefs();
        accountPresenter.login(RequestHeaders.getUsername(), password);
        onEventListener.onEvent(EVENT_SHOW_LOADER, getContext().getString(R.string.update_data));
    }

    @Override
    public void getDataForm() {
        password = edtUserPass.getText().trim();
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, getContext().getString(R.string.update_data));
        if (cardStatusId.equals("1")) {
            // Operacion para Bloquear tarjeta
            mPreferPresenter.toPresenterBloquearCuenta(BLOQUEO);
            statusBloqueo=BLOQUEO;
        } else {
            // Operacion para Desbloquear tarjeta
            mPreferPresenter.toPresenterBloquearCuenta(DESBLOQUEO);
            statusBloqueo=DESBLOQUEO;
        }

    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void sendSuccessBloquearCuentaToView(BloquearCuentaResponse response) {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        String messageStatus = "";
        if (statusBloqueo == BLOQUEO) {
            messageStatus = getResources().getString(R.string.card_locked_success);
            App.getInstance().setStatusId(Recursos.ESTATUS_CUENTA_BLOQUEADA);
        } else if (statusBloqueo == DESBLOQUEO) {
            messageStatus = getResources().getString(R.string.card_unlocked_success);
            App.getInstance().setStatusId(Recursos.ESTATUS_CUENTA_DESBLOQUEADA);
        }

        // Armamos
        showDialogMesage(messageStatus +
                "\n" +
                getResources().getString(R.string.card_num_autorization) + ": "
                + response.getData().getNumeroAutorizacion(), 1);
        try {
            ApiAdtvo.cerrarSesion();
            // Toast.makeText(App.getContext(), "Close Session " + response.getData().getNumeroAutorizacion(), Toast.LENGTH_SHORT).show();
        } catch (OfflineException e) {
            e.printStackTrace();
        }

        // update de datos de Card instantaneos
        cardStatusId = App.getInstance().getStatusId();
        if (cardStatusId.equals("1")) {
            // La tarjeta esta DESBLOQUEADA, mostramos la cCard Azul
            cardBlue.setVisibility(View.VISIBLE);
            cardGray.setVisibility(View.GONE);

            // Cambiamos las palabras a Bloquear
            tittleBorder.setTitle(App.getContext().getResources().getString(R.string.bloquear_tarjeta));
        } else {
            // La tarjeta esta BLOQUEADA, mostramos la cCard Gray
            cardBlue.setVisibility(View.GONE);
            cardGray.setVisibility(View.VISIBLE);

            // Cambiamos las palabras a Desboquear
            tittleBorder.setTitle(App.getContext().getResources().getString(R.string.desbloquear_tarjeta));
        }
    }

    @Override
    public void sendErrorBloquearCuentaToView(String mensaje) {
        // Toast.makeText(App.getContext(), "Error Vloquear", Toast.LENGTH_SHORT).show();
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        showDialogMesage(mensaje, 0);
        try {
            ApiAdtvo.cerrarSesion();
            //Toast.makeText(App.getContext(), "Close Session ", Toast.LENGTH_SHORT).show();
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(Object error) {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        showDialogMesage(error.toString(), 0);
    }

    @Override
    public void loginSucced() {

    }

    /**
     * Encargada de reaccionar al codigo de pusacion KEYCODE_ENDCALL=6 para consumir el servicio
     */
    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == KeyEvent.KEYCODE_ENDCALL) {
                // Toast.makeText(getContext(), "Click Enter", Toast.LENGTH_SHORT).show();
                // actionBtnLogin();
            }
            return false;
        }
    }

    private void showDialogMesage(final String mensaje, final int backAction) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        if (backAction == 1) {
                            // Accion de Out
                            onEventListener.onEvent(EVENT_BLOCK_CARD_BACK, "");
                        }
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }
}
