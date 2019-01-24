package com.pagatodo.yaganaste.modules.emisor.ChangeNip;


import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.view_manager.buttons.ButtonContinue;
import com.pagatodo.view_manager.components.inputs.InputSecret;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.modules.emisor.WalletMainActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IChangeNIPView;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.Container;
import com.pagatodo.yaganaste.ui_wallet.pojos.InputText;
import com.pagatodo.yaganaste.utils.UtilsIntents;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.modules.emisor.WalletMainActivity.MY_PERMISSIONS_REQUEST_PHONE;
import static com.pagatodo.yaganaste.utils.Recursos.OLD_NIP;
import static com.pagatodo.yaganaste.utils.Recursos.PUBLIC_KEY_RSA;

/**
 * Created by Team Android on 22/03/2017.
 */
public class MyChangeNip extends GenericFragment implements View.OnClickListener,
        IChangeNIPView {

    @BindView(R.id.btn_continue)
    ButtonContinue finishBtn;
    @BindView(R.id.call_phone)
    TextView call_phone;
    @BindView(R.id.textInputEditText)
    InputSecret nipActual;

    private WalletMainActivity activity;

    private View rootview;

    private AccountPresenterNew accountPresenter;
    boolean isValid;
    public final static String EVENT_GO_CHANGE_NIP_SUCCESS = "EVENT_GO_CHANGE_NIP_SUCCESS";

    public static MyChangeNip newInstance() {
        return new MyChangeNip();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WalletMainActivity){
            activity = (WalletMainActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        accountPresenter = new AccountPresenterNew(getContext());
        accountPresenter.setIView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_my_change_nip, container, false);initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        finishBtn.setOnClickListener(this);
        call_phone.setOnClickListener(this);
        nipActual.setRequestFocus();
        showKeyboard();
        nipActual.setActionListener((v, actionId, keyEvent) ->{
            if (actionId== EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                hideKeyBoard(nipActual.getInputEditText());
                if (validate()){

                    /*mPreferPresenter.changePassToPresenter(
                            Utils.cipherRSA(hit, PUBLIC_KEY_RSA),
                            Utils.cipherRSA(inputSecretPassNew.getTextEdit(), PUBLIC_KEY_RSA)
                    );*/
                }
                return true;
            }
            return false;
        });
    }


    private void showSnakErrorBar(String mensje) {
        hideKeyBoard(nipActual.getInputEditText());
        UI.showErrorSnackBar(Objects.requireNonNull(getActivity()), mensje, Snackbar.LENGTH_LONG);
    }

    public boolean isCustomKeyboardVisible() {
        return true;
    }


    /*private void showValidationError(Object err) {
        showValidationError(0, err);
    }*/


   /*     showLoader(getContext().getResources().getString(R.string.msg_renapo));
        accountPresenter.changeNIP(nipNew, nipNewConfirm);
   */


   private boolean validate(){
        boolean isValid = true;

        if (nipActual.getTextEdit().length() < 4){
            isValid = false;
            UI.showErrorSnackBar(Objects.requireNonNull(getActivity()),"El NIP no es correcto",Snackbar.LENGTH_SHORT);
        }
        if (!nipActual.getTextEdit().equalsIgnoreCase(App.getInstance().getPrefs().loadData(OLD_NIP))){
            isValid = false;
            UI.showErrorSnackBar(Objects.requireNonNull(getActivity()),"El NIP no es correcto",Snackbar.LENGTH_SHORT);
        }

        return isValid;
   }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_myemail_btn:
                boolean isOnline = Utils.isDeviceOnline();
                if (isOnline) {

                } else {
                    showSnakErrorBar(getResources().getString(R.string.no_internet_access));
                }

                break;
            case R.id.call_phone:
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
            UI.showErrorSnackBar(getActivity(), error.toString(), Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void setSuccessChangeNip(Object data) {
        /*nipActual.editText.setText("");
        nipNueva.editText.setText("");
        nipConfir.editText.setText("");*/
        UI.showSuccessSnackBar(getActivity(), getResources().getString(R.string.exito_nip), Snackbar.LENGTH_SHORT);

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

    DialogDoubleActions doubleActions = new DialogDoubleActions() {
        @Override
        public void actionConfirm(Object... params) {
            UtilsIntents.createCallIntent(getActivity());
        }

        @Override
        public void actionCancel(Object... params) {

        }
    };

    @Override
    public void onStop() {
        super.onStop();
        hideKeyBoard(nipActual.getInputEditText());
    }
}
