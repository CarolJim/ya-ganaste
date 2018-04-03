package com.pagatodo.yaganaste.ui.account.register;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAccountCardNIPView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.AsignarNipTextWatcher;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_CONFIRM_PIN;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity.EVENT_SESSION_EXPIRED;

/**
 * A simple {@link GenericFragment} subclass.
 * Encargada de mostrar en pantalla un EditTExt oculto que procesa la infromacion del PIN asignado
 * y un arreglo de 4 TextView que mostraran al usuario el PIN que escoja, solo se muestra menos de
 * un segundo para cambiarse por un Bullet Azul
 */
public class AsignarNIPFragment extends GenericFragment implements ValidationForms, IAccountCardNIPView {

    private static int PIN_LENGHT = 4;
    @BindView(R.id.nip)
    CustomValidationEditText edtPin;
    @BindView(R.id.confim_nip)
    CustomValidationEditText confim_nip;
    @BindView(R.id.btnNextAsignarPin)
    Button btnNextAsignarPin;
    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;
    @BindView(R.id.progressIndicator)
    ProgressLayout progressLayout;
    LinearLayout layout_control;
    TextView tv1Num;
    TextView tv2Num;
    TextView tv3Num;
    TextView tv4Num;
    ImageView asignar_iv1;
    private View rootview;
    private String nip = "";
    private String conf_nip = "";
    private Keyboard keyboard;
    ImageView imageView;
    private AccountPresenterNew accountPresenter;


    public AsignarNIPFragment() {
    }

    public static AsignarNIPFragment newInstance() {
        AsignarNIPFragment fragmentRegister = new AsignarNIPFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //rootview = inflater.inflate(R.layout.fragment_asignar_nip, container, false);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
        imageView = (ImageView) getActivity().findViewById(R.id.btn_back);
        initViews();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootview;
    }

    @Override
    public void initViews() {
        //  ButterKnife.bind(this, rootview);
        //btnNextAsignarPin.setVisibility(View.GONE);


    }

    /*Implementación de ValidationForms*/

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        getDataForm();

        if (nip.length() < PIN_LENGHT || conf_nip.length() < PIN_LENGHT) {
            showValidationError(getString(R.string.asignar_pin));
            return;
        } else if (!nip.equals(conf_nip)) {
            showValidationError(getString(R.string.confirmar_pin));
            return;
        }

        onValidationSuccess();
    }

    private void showValidationError(Object err) {
        showValidationError(0, err);
    }

    @Override
    public void showValidationError(int id, Object error) {



        UI.showAlertDialog(getActivity(), getResources().getString(R.string.app_name),error.toString(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });





    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        accountPresenter.assignNIP(nip);
    }

    @Override
    public void getDataForm() {
        nip = edtPin.getText().toString().trim();
        conf_nip = confim_nip.getEditText().toString().trim();
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(final Object error) {
        if (!error.toString().isEmpty()) {

            UI.showAlertDialog(getActivity(), getResources().getString(R.string.app_name),error.toString(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (error.toString().equals(Recursos.MESSAGE_OPEN_SESSION)) {
                        onEventListener.onEvent(EVENT_SESSION_EXPIRED, 1);
                    }
                }
            });



        }
    }


    public boolean isCustomKeyboardVisible() {
        return false;
    }
}