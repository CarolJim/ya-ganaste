package com.pagatodo.yaganaste.ui.account.login;


import android.app.KeyguardManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccessCodeGenerateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccessCodeGenerateFragment extends GenericFragment implements View.OnClickListener {

    @BindView(R.id.editPassword)
    CustomValidationEditText editPassword;
    @BindView(R.id.errorPasswordMessage)
    ErrorMessage errorPasswordMessage;
    @BindView(R.id.btnGenerateCode)
    StyleButton btnGenerateCode;
    View rootView;

    public interface OtpInterface {
        void loadCode(String code);
    }

    public static AccessCodeGenerateFragment newInstance() {
        AccessCodeGenerateFragment fragment = new AccessCodeGenerateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_access_code_generate, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnGenerateCode.setOnClickListener(this);
        editPassword.addCustomTextWatcher(new MTextWatcher());
        // Validar versión compatible con lectura de huellas digitales
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FingerprintManager fingerprintManager = getActivity().getSystemService(FingerprintManager.class);
            // Validar que el hardware esté disponible para lectura de huellas digitales
            if(fingerprintManager.isHardwareDetected()){
                if(fingerprintManager.hasEnrolledFingerprints()){
                    // Llamar diálogo de lectura
                    Log.i(getString(R.string.app_name), "Fingerprints Enrolled Detected");
                } else {
                    // Llamar diálogo de ayuda
                    Log.i(getString(R.string.app_name), "No Fingerprints Enrolled Detected");
                }
            } else {
                Log.e(getString(R.string.app_name), "No fingerprint hardware detected.");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGenerateCode:
                loadOtp();
                break;
            default:
                //Nothing To Do
                break;
        }
    }

    private void loadOtp() {
        if (editPassword.isValidText()) {
            onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.generando_token));
            ((OtpInterface) getParentFragment()).loadCode(Utils.getSHA256(editPassword.getText()));
        } else if (editPassword.getText().isEmpty()) {
            editPassword.setIsInvalid();
            errorPasswordMessage.setMessageText(getString(R.string.datos_usuario_pass));
        } else {
            editPassword.setIsInvalid();
            errorPasswordMessage.setMessageText(getString(R.string.datos_usuario_pass_formato));
        }
    }


    private class MTextWatcher extends AbstractTextWatcher {
        @Override
        public void afterTextChanged(String s) {
            editPassword.imageViewIsGone(true);
            errorPasswordMessage.setMessageText(null);
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (!menuVisible && editPassword != null && isAdded()) {
            editPassword.setText(null);
            UI.hideKeyBoard(getActivity());
        }
    }
}