package com.pagatodo.yaganaste.ui.adquirente;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.SignatureData;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.TransaccionEMVDepositResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.SigningViewYaGanaste;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class GetSignatureFragment extends GenericFragment implements View.OnClickListener,INavigationView {

    private View rootview;
    @BindView(R.id.txtAmount)
    MontoTextView txtAmount;
    @BindView(R.id.txtNumberCard)
    StyleTextView txtNumberCard;
    @BindView(R.id.imgTypeCard)
    ImageView imgTypeCard;
    @BindView(R.id.txtNameOwnerCard)
    StyleTextView txtNameOwnerCard;
    @BindView(R.id.btnClear)
    StyleButton btnClear;
    @BindView(R.id.btnSendSignature)
    StyleButton btnSendSignature;
    @BindView(R.id.signature_here)
    StyleTextView firma_aqui;
    @BindView(R.id.layout_content_firma)
    FrameLayout layout_content_firma;

    private SigningViewYaGanaste signingView;
    private TransaccionEMVDepositResponse emvDepositResponse;
    private TransactionAdqData currentTransaction;

    private AdqPresenter adqPresenter;

    public GetSignatureFragment() {
    }

    public static GetSignatureFragment newInstance() {
        GetSignatureFragment fragmentRegister = new GetSignatureFragment();
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
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        emvDepositResponse  = TransactionAdqData.getCurrentTransaction().getTransaccionResponse();
        currentTransaction  = TransactionAdqData.getCurrentTransaction();
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

        rootview = inflater.inflate(R.layout.activity_signature, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        signingView = new SigningViewYaGanaste(getActivity(), firma_aqui,btnSendSignature);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout_content_firma.addView(this.signingView,params);
        signingView.setDrawingCacheEnabled(true);
        signingView.buildDrawingCache();
        btnClear.setOnClickListener(this);
        btnSendSignature.setOnClickListener(this);
        btnClear.setBackgroundResource(R.drawable.button_rounded_transparent);
        btnSendSignature.setBackgroundResource(R.drawable.button_rounded_transparent);


        /*Seteamos los datos de la transacción*/
        txtAmount.setText(String.format("$%s", currentTransaction.getAmount()));
       // txtNumberCard.setText(emvDepositResponse.getMaskedPan());

        String cardNumber = emvDepositResponse.getMaskedPan();
        String cardNumberFormat = StringUtils.ocultarCardNumberFormat(cardNumber);
        txtNumberCard.setText(cardNumberFormat);


        txtNameOwnerCard.setText(String.format("%s",emvDepositResponse.getName()));
        if(imgTypeCard != null){
            imgTypeCard.setImageDrawable(setDrawable());
        }
    }

    /**
     * Hacemos Set de la imagen dede el emvDepositResponse.getMarcaTarjetaBancaria()
     * @return
     */
    private Drawable setDrawable() {
        return emvDepositResponse.getMarcaTarjetaBancaria().equals("Visa") ? getDrawable(R.drawable.visa): getDrawable(R.drawable.mastercard_canvas);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnClear:
                signingView.clear();
                signingView.setHasSignature(false);
                btnSendSignature.setBackgroundResource(R.drawable.button_rounded_transparent);
                break;

            case R.id.btnSendSignature:
                sendSignature();
                break;

            default:
                break;
        }
    }

    private void sendSignature(){
        if(signingView.hasSignature()){
            SignatureData data = new SignatureData();
            data.setSignature(signingView.getSign());
            data.setSignatureWidth(String.valueOf(signingView.getW()));
            data.setSignatureHeight(String.valueOf(signingView.getH()));
            adqPresenter.sendSignature(data);
        }else {
            showError(getString(R.string.adq_error_signature));
        }
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
        //progressLayout.setTextMessage(message);
        //progressLayout.setVisibility(View.VISIBLE);
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        //progressLayout.setVisibility(GONE);
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(Object error) {
//        UI.showToast(error.toString(),getActivity());
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

    private Drawable getDrawable(int res){
        return ContextCompat.getDrawable(getActivity(), res);
    }
}

