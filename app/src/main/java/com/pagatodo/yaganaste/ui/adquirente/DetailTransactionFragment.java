package com.pagatodo.yaganaste.ui.adquirente;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.TransaccionEMVDepositResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentFormBaseFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DetailTransactionFragment extends PaymentFormBaseFragment implements ValidationForms,INavigationView {

    @BindView(R.id.txtAmountPayment)
    StyleTextView txtAmountPayment;
    @BindView(R.id.txtNumberCard)
    StyleTextView txtMaskedPan;
    @BindView(R.id.imgTypeCard)
    ImageView imgTypeCard;
    @BindView(R.id.edtEmailSendticket)
    CustomValidationEditText edtEmailSendticket;


    private TransaccionEMVDepositResponse emvDepositResponse;
    private String emailToSend = "";

    private AdqPresenter adqPresenter;

    public static DetailTransactionFragment newInstance() {
        DetailTransactionFragment fragmentRegister = new DetailTransactionFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        emvDepositResponse  = TransactionAdqData.getCurrentTransaction().getTransaccionResponse();
        adqPresenter = new AdqPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_detail_transaction, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();
        txtAmountPayment.setText(String.format("$%s",TransactionAdqData.getCurrentTransaction().getAmount()));
        txtMaskedPan.setText(String.format("%s",emvDepositResponse.getMaskedPan()));
        imgTypeCard.setImageResource("Visa".equals(emvDepositResponse.getMarcaTarjetaBancaria()) ? R.drawable.mastercard_canvas : R.drawable.mastercard_canvas);
    }

    @Override
    public void setValidationRules() {
    }

    @Override
    public void validateForm() {
        getDataForm();
        if(emailToSend.isEmpty() || !edtEmailSendticket.isValidText()){
            showValidationError(getString(R.string.check_your_mail));
            return;
        }
        onValidationSuccess();
    }

    @Override
    public void showValidationError(Object o) {
        UI.showToast(o.toString(),getActivity());
        mySeekBar.setProgress(0);
    }

    @Override
    public void onValidationSuccess() {
        mySeekBar.setProgress(0);
        adqPresenter.sendTicket(emailToSend);
    }

    @Override
    public void getDataForm() {
        emailToSend = edtEmailSendticket.getText().trim();
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event,data);
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
        DialogDoubleActions doubleActions = new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {

            }

            @Override
            public void actionCancel(Object... params) {

            }
        };
        UI.createSimpleCustomDialog("Error", error.toString(), getFragmentManager(),doubleActions, true, false);
    }

    @Override
    protected void continuePayment() {
        validateForm();
    }
}


