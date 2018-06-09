package com.pagatodo.yaganaste.ui.account.login;

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
import com.pagatodo.yaganaste.interfaces.IChangePass6;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.AsignarNIPFragment;
import com.pagatodo.yaganaste.utils.AsignarContraseñaTextWatcher;
import com.pagatodo.yaganaste.utils.AsignarNipTextWatcher;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_CONFIRM_NEW_CONTRASE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_CONFIRM_PIN;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity.EVENT_SESSION_EXPIRED;

/**
 * Created by Armando Sandoval on 12/12/2017.
 */

public class NewPasswordLoginChange extends GenericFragment implements ValidationForms, IAccountCardNIPView {

    private static int PIN_LENGHT = 6;

    /*
    @BindView(R.id.asignar_edittext)
    CustomValidationEditText edtPin;
*/
    @BindView(R.id.edt_password)
    EditText edt_password;

    @BindView(R.id.btnNextAsignarPin)
    Button btnNextAsignarPin;
    @BindView(R.id.progressIndicator)
    ProgressLayout progressLayout;
    LinearLayout layout_control;
    TextView tv1Num;
    TextView tv2Num;
    TextView tv3Num;
    TextView tv4Num;
    TextView tv5Num;
    TextView tv6Num;
    private String nip = "";
    private Keyboard keyboard;
    ImageView asignar_iv1;
    private View rootview;

    ImageView imageView;


    public static NewPasswordLoginChange newInstance() {
        NewPasswordLoginChange fragmentRegister = new NewPasswordLoginChange();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_new_password_login, container, false);
        imageView = (ImageView)getActivity().findViewById(R.id.btn_back);
        initViews();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnNextAsignarPin.setVisibility(View.GONE);




    }






    /*Implementación de ValidationForms*/




    private void showValidationError(Object err) {
        showValidationError(0, err);
    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {

    }

    @Override
    public void showValidationError(int id, Object error) {
        //UI.showToastShort(error.toString(), getActivity());
        UI.createSimpleCustomDialog("", error.toString(), getFragmentManager(),
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
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        nextScreen(EVENT_GO_CONFIRM_NEW_CONTRASE, nip);
    }

    @Override
    public void getDataForm() {

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
            //UI.showToastShort(error.toString(), getActivity()); error
            UI.createSimpleCustomDialog("", error.toString(), getFragmentManager(),
                    new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {
                            if (error.toString().equals(Recursos.MESSAGE_OPEN_SESSION)) {
                                onEventListener.onEvent(EVENT_SESSION_EXPIRED, 1);
                            }
                        }

                        @Override
                        public void actionCancel(Object... params) {

                        }
                    },
                    true, false);
        }
    }


    public boolean isCustomKeyboardVisible() {
        return false;
    }


}
