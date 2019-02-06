package com.pagatodo.yaganaste.modules.emisor.ActivatePhysicalCard;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.view_manager.buttons.ButtonContinue;
import com.pagatodo.view_manager.components.inputs.InputCardNumber;
import com.pagatodo.view_manager.components.inputs.InputSecretListener;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerBancoBinResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarTitularCuentaResponse;
import com.pagatodo.yaganaste.modules.emisor.WalletEmisorContracts;
import com.pagatodo.yaganaste.modules.emisor.WalletEmisorInteractor;
import com.pagatodo.yaganaste.modules.emisor.WalletMainActivity;
import com.pagatodo.yaganaste.modules.management.response.QrValidateResponse;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.utils.UI;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivatePhysicalCardFragment extends SupportFragment implements View.OnClickListener,
        EditText.OnEditorActionListener, InputSecretListener, WalletEmisorContracts.Listener {

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
        interactor = new WalletEmisorInteractor(this,activity);
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
        inputNumberCard.setText("538984");
        inputNumberCard.getEditText().setOnEditorActionListener(this);
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
                hideKeyBoard(inputNumberCard.getEditText());
                if (validate()){
                    //this.activity.getRouter().onShowGeneratePIN();

                    interactor.validateCard(inputNumberCard.getText().trim());
                }
            return true;
        }
        // Return true if you have consumed the action, else false.
        return false;
    }

    private boolean validate(){
        boolean isVailid = true;
        if (inputNumberCard.getText().length() < 16){
            isVailid = false;
            inputNumberCard.isError();
            UI.showErrorSnackBar(Objects.requireNonNull(getActivity()),
                    Objects.requireNonNull(getContext()).getResources().getString(R.string.text_error_active_card),
                    Snackbar.LENGTH_SHORT);
        }
        return isVailid;
    }

    @Override
    public void onStop() {
        super.onStop();
        //hideKeyboard();
        hideKeyBoard(inputNumberCard.getEditText());
    }



    @Override
    public void inputListenerFinish(View view) {
        btnContinue.active();
    }

    @Override
    public void inputListenerBegin() {
        btnContinue.inactive();
    }

    @Override
    public void showLoad() {
        this.activity.showLoader("");
    }

    @Override
    public void hideLoad() {
        this.activity.hideLoad();
    }

    @Override
    public void onSouccesValidateCard() {

    }

    @Override
    public void onErrorRequest(String msj) {
        hideKeyBoard(getView());
        UI.showErrorSnackBar(Objects.requireNonNull(getActivity()),msj,Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onSouccesDataQR(QrValidateResponse QRresponse) {

    }

    @Override
    public void onSouccesGetTitular(ConsultarTitularCuentaResponse data) {

    }

    @Override
    public void onSouccessgetgetDataBank(ObtenerBancoBinResponse data) {

    }
}
