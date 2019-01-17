package com.pagatodo.yaganaste.modules.sidebar.ChangePassword;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.pagatodo.view_manager.components.InputSecretPass;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordFragment extends GenericFragment {

    private View rootView;

    @BindView(R.id.input_secret_current)
    InputSecretPass inputSecretPassCurrent;

    public static ChangePasswordFragment newInstance(){
        return new ChangePasswordFragment();
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
        inputSecretPassCurrent.setActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if (validate()){

                    /*this.interactor.onActiveCard(SingletonUser.getInstance().getDataUser().getEmisor()
                            .getCuentas().get(0).getTarjetas().get(0).getNumero().trim());*/
                    //interactor.validateCard(editNumberCard.getText().toString().trim());
                    //interactor.onActiveCard("1234567890123456");
                }
                return true;
            }
            // Return true if you have consumed the action, else false.
            return false;
        });

    }

    private boolean validate(){
        return true;
    }
}
