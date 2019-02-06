package com.pagatodo.yaganaste.modules.emisor.ChangeNip;


import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.view_manager.components.inputs.InputSecret;
import com.pagatodo.view_manager.components.inputs.InputSecretListener;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IChangeNIPView;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.UtilsIntents;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.modules.emisor.WalletMainActivity.MY_PERMISSIONS_REQUEST_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;


/**
 * Created by Team Android on 22/03/2017.
 */
public class MyChangeNip extends GenericFragment implements ValidationForms, View.OnClickListener,
        IChangeNIPView, InputSecretListener {


    @BindView(R.id.fragment_myemail_btn)
    StyleButton finishBtn;
    @BindView(R.id.call_phone)
    TextView call_phone;
    @BindView(R.id.input_nip_actual)
    InputSecret inputNipCurrent;
    @BindView(R.id.input_nip_new)
    InputSecret inputNipNew;
    @BindView(R.id.input_nip_confirm)
    InputSecret inputNipConfirm;



    private View rootview;

    private String nip = "";
    private String nipNew = "";
    private String nipNewConfirm = "";

    private AccountPresenterNew accountPresenter;

    public static MyChangeNip newInstance() {
        return new MyChangeNip();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        accountPresenter = new AccountPresenterNew(getContext());
        accountPresenter.setIView(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_my_change_nip, container, false);

        initViews();
        Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        finishBtn.setOnClickListener(this);
        inputNipCurrent.setRequestFocus();
        inputNipNew.desactive();
        inputNipConfirm.desactive();
        showKeyboard();
        inputNipCurrent.setInputSecretListener(this);
        inputNipNew.setInputSecretListener(this);

        inputNipNew.setInputSecretListener(new InputSecretListener() {
            @Override
            public void inputListenerFinish(View v) {
                inputNipConfirm.setRequestFocus();
            }

            @Override
            public void inputListenerBegin() {

            }
        });
        inputNipConfirm.setInputSecretListener(new InputSecretListener() {
            @Override
            public void inputListenerFinish(View v) {

            }

            @Override
            public void inputListenerBegin() {

            }
        });
        inputNipConfirm.setActionListener((v, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                validateForm();
                return true;
            }
            // Return true if you have consumed the action, else false.
            return false;
        });
        //Container s = new Container(getContext());
        //InputFilter[] fArray = new InputFilter[1];
        //fArray[0] = new InputFilter.LengthFilter(4);
        /*nipActual = s.addLayoutPass(mLinearLayout, new InputText(R.string.nip_actual));
        nipActual.editText.setFilters(fArray);
        nipNueva = s.addLayoutPass(mLinearLayout, new InputText(R.string.nip_nuevo));
        nipNueva.editText.setFilters(fArray);
        nipConfir = s.addLayoutPass(mLinearLayout, new InputText(R.string.nip_confima));
        nipConfir.editText.setFilters(fArray);*/
        call_phone.setOnClickListener(this);
        setValidationRules();

    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity())
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Objects.requireNonNull(getView()).getWindowToken(), 0);
    }

    private void showSnakErrorBar(String mensje) {
        hideKeyBoard();
        UI.showErrorSnackBar(Objects.requireNonNull(getActivity()), mensje, Snackbar.LENGTH_LONG);
    }

    @Override
    public void setValidationRules() {

        /*nipActual.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (nipActual.editText.getText().toString().isEmpty()) {
                        //showSnakErrorBar(getResources().getString(R.string.introduce_nip_valido));
                        nipActual.inputLayout.setBackgroundResource(R.drawable.inputtext_error);
                    } else {
                        nipActual.inputLayout.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                } else {
                    nipActual.inputLayout.setBackgroundResource(R.drawable.inputtext_active);
                }
            }
        });*/

        /*nipNueva.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (nipNueva.editText.getText().toString().isEmpty()) {
                        //showSnakErrorBar(getResources().getString(R.string.introduce_nip_valido));
                        nipNueva.inputLayout.setBackgroundResource(R.drawable.inputtext_error);
                    } else {
                        nipNueva.inputLayout.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                } else {
                    nipNueva.inputLayout.setBackgroundResource(R.drawable.inputtext_active);
                }

            }
        });*/

        /*nipConfir.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (nipConfir.editText.getText().toString().isEmpty()) {
                        //showSnakErrorBar(getResources().getString(R.string.introduce_nip_valido));
                        nipConfir.inputLayout.setBackgroundResource(R.drawable.inputtext_error);
                    } else {
                        if (nipConfir.editText.getText().toString().equalsIgnoreCase(nipNueva.editText.getText().toString())) {
                            nipConfir.inputLayout.setBackgroundResource(R.drawable.inputtext_normal);
                        } else {
                            nipConfir.inputLayout.setBackgroundResource(R.drawable.inputtext_error);
                        }
                    }
                } else {
                    nipConfir.inputLayout.setBackgroundResource(R.drawable.inputtext_active);
                }
            }
        });*/

    }

    @Override
    public void validateForm() {
        getDataForm();

        boolean isValid = true;
        //Validacion de Vacios y minimo de caracteres
        if (nip.isEmpty()) {
            isValid = false;
            hideKeyBoard();
            showSnakErrorBar(getString(R.string.cambiar_nip_actual));
        }
        if (nip.length() < 4) {
            isValid = false;
            hideKeyBoard();
            showSnakErrorBar(getString(R.string.new_nip_four_digits));
        }
        if (nipNew.isEmpty()) {
            isValid = false;
            hideKeyBoard();
            showSnakErrorBar(getString(R.string.cambiar_nip_nueva));
        }
        if (nipNew.length() < 4) {
            isValid = false;
            hideKeyBoard();
            showSnakErrorBar(getString(R.string.new_nip_four_digits));
        }
        if (nipNewConfirm.isEmpty()) {
            isValid = false;
            hideKeyBoard();
            showSnakErrorBar(getString(R.string.cambiar_nip_nueva));
        }
        if (nipNewConfirm.length() < 4) {
            isValid = false;
            hideKeyBoard();
            showSnakErrorBar(getString(R.string.new_nip_four_digits));
        }
        if (!nipNewConfirm.equalsIgnoreCase(nipNew)) {
            isValid = false;
            hideKeyBoard();
            showSnakErrorBar(getString(R.string.confirmar_pin));
        }

        if (isValid) {
            hideKeyBoard();
            onValidationSuccess();
        }
    }

    public boolean isCustomKeyboardVisible() {
        return true;
    }


    /*private void showValidationError(Object err) {
        showValidationError(0, err);
    }*/

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        showLoader(Objects.requireNonNull(getContext()).getResources().getString(R.string.msg_renapo));
        accountPresenter.changeNIP(nipNew, nipNewConfirm);
    }

    @Override
    public void getDataForm() {
        nip = inputNipCurrent.getText();
        nipNew = inputNipNew.getText();
        nipNewConfirm = inputNipConfirm.getText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_myemail_btn:
                boolean isOnline = Utils.isDeviceOnline();
                if (isOnline) {
                    validateForm();
                } else {
                    showSnakErrorBar(getResources().getString(R.string.no_internet_access));
                }

                break;
            case R.id.call_phone:
                //UtilsIntents.createCallIntent(getActivity());
                showDialogCallIntent();
                break;
        }

    }


    @Override
    public void nextScreen(String event, Object data) {
        //UI.showSuccessSnackBar(getActivity(),getResources().getString(R.string.success_nip),Snackbar.LENGTH_SHORT);
    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void showLoader(String message) {
        hideLoader();
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(final Object error) {
        if (!error.toString().isEmpty()) {
            /*UI.createSimpleCustomDialog("", error.toString(), getFragmentManager(),
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
            */
            UI.showErrorSnackBar(Objects.requireNonNull(getActivity()), error.toString(), Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void setSuccessChangeNip(Object data) {
        inputNipCurrent.setText("");
        inputNipNew.setText("");
        inputNipConfirm.setText("");
        UI.showSuccessSnackBar(Objects.requireNonNull(getActivity()),
                getResources().getString(R.string.exito_nip), Snackbar.LENGTH_SHORT);

    }

    private void showDialogCallIntent() {
        boolean isValid = true;

        int permissionCall = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.CALL_PHONE);

        // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
        if (permissionCall == -1) {
            ValidatePermissions.checkPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_PHONE);
            isValid = false;
        }

        if (isValid) {
            UI.createSimpleCustomDialog("", getResources().getString(R.string.deseaRealizarLlamada), getFragmentManager(),
                    doubleActions, true, true);
        }
    }

    private final DialogDoubleActions doubleActions = new DialogDoubleActions() {
        @Override
        public void actionConfirm(Object... params) {
            UtilsIntents.createCallIntent(Objects.requireNonNull(getActivity()));
        }

        @Override
        public void actionCancel(Object... params) {

        }
    };

    @Override
    public void onStop() {
        super.onStop();
        hideKeyBoard();
    }

    @Override
    public void inputListenerFinish(View view) {
        switch (view.getId()){
            case R.id.input_nip_actual:
                inputNipNew.setRequestFocus();
                inputNipNew.setVisibility(View.VISIBLE);
                rootview.findViewById(R.id.instr).setVisibility(View.VISIBLE);
                inputNipConfirm.setVisibility(View.VISIBLE);
                break;
            case R.id.input_nip_new:
                break;
            case R.id.input_nip_confirm:
                break;
        }
    }

    @Override
    public void inputListenerBegin() {

    }
}