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
    @BindView(R.id.asignar_edittext)
    CustomValidationEditText edtPin;
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

        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);

        layout_control = (LinearLayout) rootview.findViewById(R.id.asignar_control_layout);
        imageView.setVisibility(View.GONE);
        tv1Num = (TextView) rootview.findViewById(R.id.asignar_tv1);
        tv2Num = (TextView) rootview.findViewById(R.id.asignar_tv2);
        tv3Num = (TextView) rootview.findViewById(R.id.asignar_tv3);
        tv4Num = (TextView) rootview.findViewById(R.id.asignar_tv4);
        tv5Num = (TextView) rootview.findViewById(R.id.asignar_tv5);
        tv6Num = (TextView) rootview.findViewById(R.id.asignar_tv6);

        // EditTExt oculto que procesa el PIN y sirve como ancla para validacion
        // Se le asigna un TextWatcher personalizado para realizar las oepraciones
        edtPin = (CustomValidationEditText) rootview.findViewById(R.id.asignar_edittext);
        edtPin.setMaxLength(6); // Se asigna un maximo de 4 caracteres para no tener problrmas
        edtPin.addCustomTextWatcher(new AsignarContraseñaTextWatcher(edtPin, tv1Num, tv2Num, tv3Num, tv4Num, tv5Num, tv6Num));
        edtPin.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 6) {
                    keyboardView.hideCustomKeyboard();
                    btnNextAsignarPin.setVisibility(View.VISIBLE);
                    //validateForm();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Si ocamos el area especial del Layout abrimos el Keyboard
        layout_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPin.requestFocus();
                keyboardView.showCustomKeyboard(v);
            }
        });

        edtPin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                keyboardView.showCustomKeyboard(v);
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        btnNextAsignarPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });
        setValidationRules();
        keyboardView.showCustomKeyboard(rootview);
        edtPin.requestEditFocus();
    }

    /*Implementación de ValidationForms*/

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        getDataForm();

        if (nip.length() < PIN_LENGHT) {
            showValidationError(getString(R.string.asignar_pin));
            return;
        }

        onValidationSuccess();
    }

    private void showValidationError(Object err) {
        showValidationError(0, err);
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
        nip = edtPin.getText().toString().trim();
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
