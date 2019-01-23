package com.pagatodo.yaganaste.modules.sidebar.ChangePassword;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.view_manager.components.InputSecretPass;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Recursos.PUBLIC_KEY_RSA;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;

public class ChangePasswordFragment extends GenericFragment {

    private View rootView;
    private PreferUserActivity activity;

    @BindView(R.id.input_secret_current)
    InputSecretPass inputSecretPassCurrent;

    public static ChangePasswordFragment newInstance(){
        return new ChangePasswordFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (PreferUserActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.change_password_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        inputSecretPassCurrent.setRequestFocus();
        showKeyboard();
        inputSecretPassCurrent.setActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                hideKeyBoard();
                if (validate()){
                    /*this.interactor.onActiveCard(SingletonUser.getInstance().getDataUser().getEmisor()
                            .getCuentas().get(0).getTarjetas().get(0).getNumero().trim());*/
                    //interactor.validateCard(editNumberCard.getText().toString().trim());
                    activity.loadFragment(NewPasswwordFragment.newInstance(inputSecretPassCurrent.getTextEdit()),R.id.container,Direction.FORDWARD,false);

                }
                return true;
            }
            // Return true if you have consumed the action, else false.
            return false;
        });
        inputSecretPassCurrent.setOnClickListener(v -> inputSecretPassCurrent.setRequestFocus());

    }

    private boolean validatePass(){
        return Utils.getSHA256(inputSecretPassCurrent.getTextEdit())
                .equalsIgnoreCase(App.getInstance().getPrefs().loadData(SHA_256_FREJA));
        //Utils.cipherRSA(editNueva.editText.getText().toString().trim(), PUBLIC_KEY_RSA)
    }

    private boolean validate(){
        boolean isValid = true;
        if (!validatePass()){
            isValid = false;
            inputSecretPassCurrent.isError();
            UI.showErrorSnackBar(Objects.requireNonNull(getActivity()),
                    "Por favor verifica que la contraseña actual sea correcta",Snackbar.LENGTH_SHORT);
            //activity.showError("Por favor verifica que la contraseña actual sea correcta");
        }
        return isValid;
    }

    private void showKeyboard(){
        InputMethodManager imm = (InputMethodManager)  App.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager) App.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(inputSecretPassCurrent.getInputEditText().getWindowToken(), 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        hideKeyBoard();
    }
}
