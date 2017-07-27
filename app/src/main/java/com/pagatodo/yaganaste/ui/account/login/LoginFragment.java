package com.pagatodo.yaganaste.ui.account.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ILoginView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RECOVERY_PASS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class LoginFragment extends GenericFragment implements View.OnClickListener, ILoginView, ValidationForms {

    @BindView(R.id.imgLoginExistProfile)
    CircleImageView imgLoginExistProfile;
    @BindView(R.id.textNameUser)
    StyleTextView textNameUser;
    @BindView(R.id.edtxtUserName)
    CustomValidationEditText edtUserName;
    @BindView(R.id.edtxtUserPass)
    CustomValidationEditText edtUserPass;
    @BindView(R.id.btnLoginExistUser)
    StyleButton btnLogin;
    @BindView(R.id.txtLoginExistUserRecoverPass)
    StyleTextView txtLoginExistUserRecoverPass;
    private View rootview;
    private AccountPresenterNew accountPresenter;

    private String username = "";
    private String password = "";


    public static LoginFragment newInstance() {
        LoginFragment fragmentRegister = new LoginFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            if (accountPresenter != null) {
                accountPresenter.setIView(this);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootview == null) {
            rootview = inflater.inflate(R.layout.fragment_login, container, false);
            initViews();
        }
        return rootview;
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnLogin.setOnClickListener(this);
        txtLoginExistUserRecoverPass.setOnClickListener(this);

        if (!RequestHeaders.getTokenauth().isEmpty()) {
            Preferencias preferencias = App.getInstance().getPrefs();
            textNameUser.setText(preferencias.loadData(StringConstants.SIMPLE_NAME));

            edtUserName.setText(RequestHeaders.getUsername());
            edtUserName.setVisibility(GONE);

            textNameUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textNameUser.setVisibility(GONE);
                    edtUserName.setText(RequestHeaders.getUsername());//(textNameUser.getText().toString());
                    edtUserName.setVisibility(VISIBLE);
                }
            });
        } else {
            edtUserName.setText("");
            edtUserName.setVisibility(VISIBLE);
            textNameUser.setVisibility(GONE);
        }
        setValidationRules();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLoginExistUser:
                //Intent intent = new Intent(getActivity(), RegistryCupoActivity.class);
                //startActivity(intent);
                actionBtnLogin();
                break;
            case R.id.txtLoginExistUserRecoverPass:
                //  startActivity(new Intent(getActivity(), TabActivity.class));
                nextScreen(EVENT_RECOVERY_PASS, username);
                break;
            default:
                break;
        }
    }

    private void actionBtnLogin() {

        if (UtilsNet.isOnline(getActivity())) {
            validateForm();
        } else {
            UI.createSimpleCustomDialog("Error", getString(R.string.no_internet_access), getFragmentManager(), getFragmentTag());
        }
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
    public void showError(Object error) {
        //UI.showToastShort(error.toString(), getActivity());
        UI.createSimpleCustomDialogNoCancel(getString(R.string.title_error),
                error.toString(), getFragmentManager(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        edtUserName.clearFocus();
                        edtUserPass.clearFocus();
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                });
        setEnableViews(true);
    }

    /*Implementación de ValidationForm*/
    @Override
    public void setValidationRules() {
        edtUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtUserName.imageViewIsGone(true);
                } else {
                    if (edtUserName.getText().isEmpty() || !edtUserName.isValidText()) {
                        edtUserName.setIsInvalid();
                    } else if (edtUserName.isValidText()) {
                        edtUserName.setIsValid();
                    }
                }
            }
        });

        edtUserName.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                edtUserName.imageViewIsGone(true);
            }
        });

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
    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        String errorMsg = null;

        if (username.isEmpty()) {
            errorMsg = getString(R.string.datos_usuario_correo);
            edtUserName.setIsInvalid();
            isValid = false;
        }

        if (!edtUserName.getText().isEmpty() && !edtUserName.isValidText()) {
            errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.datos_usuario_correo) : errorMsg;
            edtUserName.setIsInvalid();
            isValid = false;
        }

        if (password.isEmpty()) {
            errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required) : errorMsg;
            edtUserPass.setIsInvalid();
            isValid = false;
        }

        if (isValid) {
            onValidationSuccess();
        } else {
            showError(errorMsg);
        }

    }


    @Override
    public void showValidationError(int id, Object error) {
        UI.showToastShort(error.toString(), getActivity());
        setEnableViews(true);
    }

    @Override
    public void onValidationSuccess() {

        setEnableViews(false);
        accountPresenter.login(username, password); // Realizamos el  Login
    }

    @Override
    public void getDataForm() {
        username = edtUserName.getText().trim();
        password = edtUserPass.getText().trim();
    }

    @Override
    public void loginSucced() {
        Intent intentLogin = new Intent(getActivity(), TabActivity.class);
        startActivity(intentLogin);
        getActivity().finish();
    }

    private void setEnableViews(boolean isEnable) {
        edtUserName.setEnabled(isEnable);
        edtUserPass.setEnabled(isEnable);
        btnLogin.setEnabled(isEnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        accountPresenter.setIView(this);
        if (!RequestHeaders.getTokenauth().isEmpty()) {
            edtUserName.setText(RequestHeaders.getUsername());
        }
    }
}

