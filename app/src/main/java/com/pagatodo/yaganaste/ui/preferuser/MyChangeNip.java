package com.pagatodo.yaganaste.ui.preferuser;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.TarjetaActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IChangeNIPView;
import com.pagatodo.yaganaste.ui_wallet.Builder.Container;
import com.pagatodo.yaganaste.ui_wallet.Builder.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.adapters.InputTexAdapter;
import com.pagatodo.yaganaste.ui_wallet.pojos.InputText;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.AsignarNipCustomWatcher;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity.EVENT_SESSION_EXPIRED;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.MY_PERMISSIONS_REQUEST_PHONE;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CCAMBIAR_PASS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RAW;
import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyChangeNip#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyChangeNip extends GenericFragment implements ValidationForms, View.OnClickListener,
        IChangeNIPView{

    private static int PIN_LENGHT = 4;

    @BindView(R.id.content_linearlayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.fragment_myemail_btn)
    StyleButton finishBtn;
    @BindView(R.id.call_phone)
    TextView call_phone;
    private InputText.ViewHolderInputText nipActual;
    private InputText.ViewHolderInputText nipNueva;
    private InputText.ViewHolderInputText nipConfir;


    private View rootview;

    private String nip = "";
    private String nipNew = "";
    private String nipNewConfirm = "";

    private AccountPresenterNew accountPresenter;
    boolean isValid;
    public final static String EVENT_GO_CHANGE_NIP_SUCCESS = "EVENT_GO_CHANGE_NIP_SUCCESS";

    public MyChangeNip() {
    }

    public static MyChangeNip newInstance() {
        MyChangeNip fragment = new MyChangeNip();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
        }
        accountPresenter = ((TarjetaActivity) getActivity()).getPresenterAccount();
        accountPresenter.setIView(this);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_my_change_nip, container, false);

        initViews();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        finishBtn.setOnClickListener(this);
        Container s = new Container(getContext());
        nipActual = s.addLayoutPass(mLinearLayout, new InputText(R.string.asignar_nueva_contraseña));
        nipNueva = s.addLayoutPass(mLinearLayout, new InputText(R.string.confirma_nueva_contrasena));
        nipConfir = s.addLayoutPass(mLinearLayout, new InputText(R.string.confirma_nueva_contraseña));

        call_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCallIntent();
            }
        });

        nipActual.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                   //validateEt1();
                    if (nipActual.editText.getText().toString().isEmpty()) {
                        showSnakBar(getResources().getString(R.string.introduce_nip_valido));
                    }
                }
            }
        });

        nipNueva.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (nipNueva.editText.getText().toString().isEmpty()) {
                        showSnakBar(getResources().getString(R.string.introduce_nip_valido));
                    }
                }
            }
        });

        nipConfir.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (nipConfir.editText.getText().toString().isEmpty()) {
                        showSnakBar(getResources().getString(R.string.introduce_nip_valido));
                    }
                }
            }
        });
    }

    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void showSnakBar(String mensje){
        hideKeyBoard();
        UI.showErrorSnackBar(getActivity(), mensje, Snackbar.LENGTH_LONG);
    }

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

    public boolean isCustomKeyboardVisible() {
        return true;
    }


    private void showValidationError(Object err) {
        showValidationError(0, err);
    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        showLoader(getContext().getResources().getString(R.string.msg_renapo));
        accountPresenter.assignNIP(nipNewConfirm);
    }

    @Override
    public void getDataForm() {
        nip = nipActual.editText.getText().toString().trim();
        nipNew = nipNueva.editText.getText().toString().trim();
        nipNewConfirm = nipConfir.editText.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        getDataForm();
        isValid = true;
        if (nip.isEmpty())
            isValid = false;
        if (nipNew.isEmpty())
            isValid = false;
        if (nipNewConfirm.isEmpty())
            isValid = false;
        if (!nipNewConfirm.equalsIgnoreCase(nipNew)){
            isValid = false;
        }

        //Toast.makeText(getContext(), "" + isValid, Toast.LENGTH_SHORT).show();
        if (isValid) {
            onValidationSuccess();
        }
    }


    @Override
    public void nextScreen(String event, Object data) {

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

    @Override
    public void setSuccessChangeNip(Object data) {
        if (!data.toString().isEmpty())
            //  UI.showToastShort(error.toString(), getActivity());
            UI.createSimpleCustomDialog("", data.toString(), getFragmentManager(),
                    new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {
                            getActivity().onBackPressed();
                        }

                        @Override
                        public void actionCancel(Object... params) {

                        }
                    },
                    true, false);
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

        if(isValid){
            UI.createSimpleCustomDialog("", getResources().getString(R.string.deseaRealizarLlamada), getFragmentManager(),
                    doubleActions, true, true);
        }
    }
    DialogDoubleActions doubleActions = new DialogDoubleActions() {
        @Override
        public void actionConfirm(Object... params) {
            createCallIntent();
        }

        @Override
        public void actionCancel(Object... params) {

        }
    };

    private void createCallIntent() {
        String number = getString(R.string.numero_telefono_contactanos);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + number));

        if (!ValidatePermissions.isAllPermissionsActives(getActivity(), ValidatePermissions.getPermissionsCheck())) {
            ValidatePermissions.checkPermissions(getActivity(), new String[]{
                    Manifest.permission.CALL_PHONE},PERMISSION_GENERAL);
        } else {
            getActivity().startActivity(callIntent);
        }
    }
}
