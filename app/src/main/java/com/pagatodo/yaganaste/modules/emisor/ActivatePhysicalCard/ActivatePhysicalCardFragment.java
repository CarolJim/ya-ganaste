package com.pagatodo.yaganaste.modules.emisor.ActivatePhysicalCard;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.pagatodo.view_manager.buttons.ButtonContinue;
import com.pagatodo.view_manager.components.inputs.InputCardNumber;
import com.pagatodo.view_manager.components.inputs.InputSecretListener;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.modules.emisor.WalletEmisorInteractor;
import com.pagatodo.yaganaste.modules.emisor.WalletMainActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivatePhysicalCardFragment extends SupportFragment implements View.OnClickListener,
        EditText.OnEditorActionListener, InputSecretListener {

    private WalletMainActivity activity;
    private View rootView;
    private WalletEmisorInteractor interactor;

    @BindView(R.id.btn_continue_active_card)
    ButtonContinue btnContinue;
    @BindView(R.id.input_card)
    InputCardNumber inputNumberCard;


    public static ActivatePhysicalCardFragment newInstance(){
        return new ActivatePhysicalCardFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (WalletMainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactor = new WalletEmisorInteractor(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activate_physical_card_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnContinue.setOnClickListener(this);
        btnContinue.inactive();
        /*String cardnum = SingletonUser.getInstance().getDataUser().getEmisor()
                .getCuentas().get(0).getTarjetas().get(0).getNumero().trim().substring(0,6);*/
        String cardnum = SingletonUser.getInstance().getDataUser().getEmisor()
                .getCuentas().get(0).getTarjetas().get(0).getNumero().trim();
        inputNumberCard.setRequest();
        inputNumberCard.setInputSecretListener(this);
        showKeyboard();

    }



    @Override
    public void onClick(View view) {
        //this.activity.getRouter().onShowGeneratePIN();
        //hideKeyboard();
        //interactor.validateCard(editNumberCard.getText().toString().trim());
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE
                || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if (validate()){
                    //this.activity.getRouter().onShowGeneratePIN();
                    hideKeyBoard(inputNumberCard.getEditText());
                    //interactor.validateCard(editNumberCard.getText().toString().trim());
                }
            return true;
        }
        // Return true if you have consumed the action, else false.
        return false;
    }

    private boolean validate(){
        boolean isVailid = true;
        /*if (editNumberCard.getText().toString().length() < 16){
            isVailid = false;
            hideKeyboard();
              activity.showError(Objects.requireNonNull(getContext()).getResources().getString(R.string.text_error_active_card));
        }*/
        return isVailid;
    }

    @Override
    public void onStop() {
        super.onStop();
        //hideKeyboard();
        hideKeyBoard(inputNumberCard.getEditText());
    }

    @Override
    public void inputListenerFinish() {
        btnContinue.active();
    }

    @Override
    public void inputListenerBegin() {
        btnContinue.inactive();
    }
}
