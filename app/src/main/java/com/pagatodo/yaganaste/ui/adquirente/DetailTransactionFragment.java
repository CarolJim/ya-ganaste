package com.pagatodo.yaganaste.ui.adquirente;


import android.app.Activity;
import android.content.Context;
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
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


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
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;

    private TransaccionEMVDepositResponse emvDepositResponse;
    private String emailToSend = "";

    private AdqPresenter adqPresenter;

    public DetailTransactionFragment() {
    }

    public static DetailTransactionFragment newInstance() {
        DetailTransactionFragment fragmentRegister = new DetailTransactionFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        emvDepositResponse  = TransactionAdqData.getCurrentTransaction().getTransaccionResponse();
        adqPresenter = new AdqPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

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
        imgTypeCard.setImageResource(emvDepositResponse.getMarcaTarjetaBancaria().equals("Visa") ? R.drawable.mastercard_canvas : R.drawable.mastercard_canvas);
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
        progressLayout.setTextMessage(message);
        progressLayout.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(GONE);
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
        //UI.showToastShort(error.toString(),getActivity());
    }

    @Override
    protected void continuePayment() {
        validateForm();
    }
}


