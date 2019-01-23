package com.pagatodo.yaganaste.ui.adquirente.fragments;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.TransaccionEMVDepositResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.AdqActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.adquirente.presenters.AdqPresenter;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentFormBaseFragment;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class DetailTransactionFragment extends PaymentFormBaseFragment implements ValidationForms, INavigationView,
        View.OnClickListener {

    @BindView(R.id.txtAmountPayment)
    MontoTextView txtAmountPayment;
    @BindView(R.id.txtNumberCard)
    StyleTextView txtMaskedPan;
    @BindView(R.id.txtConceptMessage)
    StyleTextView txtConceptMessage;
    @BindView(R.id.txtHora)
    StyleTextView txtHour;
    @BindView(R.id.txtFecha)
    StyleTextView txtDate;
    @BindView(R.id.txtAutorizacion)
    StyleTextView txtAuthorization;
    @BindView(R.id.imgTypeCard)
    ImageView imgTypeCard;
    @BindView(R.id.edtEmailSendticket)
    EditText edtEmailSendticket;

    @BindView(R.id.tv_monto_entero)
    StyleTextView tvMontoEntero;
    @BindView(R.id.tv_monto_decimal)
    StyleTextView tvMontoDecimal;


    @BindView(R.id.text_email)
    TextInputLayout text_email;

    @BindView(R.id.edtEmailSendticketold)
    CustomValidationEditText edtEmailSendticketold;
    @BindView(R.id.layout_enviado)
    LinearLayout lyt_concept;
    ImageView imageshae;

    @BindView(R.id.btnNextEnviarticket)
    StyleButton btnNextEnviarticket;

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
        emvDepositResponse = TransactionAdqData.getCurrentTransaction().getTransaccionResponse();
        adqPresenter = new AdqPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_detail_transaction, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();
        txtAmountPayment.setText(String.format(" %s", StringUtils.getCurrencyValue(TransactionAdqData.getCurrentTransaction().getAmount())));


        String mDescription = TransactionAdqData.getCurrentTransaction().getDescription();
        String mAuthorization = TransactionAdqData.getCurrentTransaction().getTransaccionResponse().getAutorizacion();
        if (mDescription != null && !mDescription.isEmpty()) {
            txtConceptMessage.setText(mDescription);
        } else {
            lyt_concept.setVisibility(View.INVISIBLE);
        }
        if (mAuthorization != null && !mAuthorization.isEmpty()) {
            txtAuthorization.setText(mAuthorization);
        }
        SimpleDateFormat dateFormatH = new SimpleDateFormat("HH:mm:ss");

        // fecha.setText(DateUtil.getBirthDateCustomString(Calendar.getInstance()));
        txtDate.setText(DateUtil.getPaymentDateSpecialCustom(Calendar.getInstance()));
        txtHour.setText(dateFormatH.format(new Date()) + " hrs");

        txtConceptMessage.setSelected(true);
        txtAuthorization.setSelected(true);

        String cardNumber = emvDepositResponse.getMaskedPan();
        String cardNumberFormat = StringUtils.ocultarCardNumberFormat(cardNumber);
        txtMaskedPan.setText(cardNumberFormat);

        //txtMaskedPan.setText(String.format("%s", emvDepositResponse.getMaskedPan()));
        if (imgTypeCard != null) {
            if (emvDepositResponse.getMarcaTarjetaBancaria() != null)
                imgTypeCard.setImageResource(emvDepositResponse.getMarcaTarjetaBancaria().equals("Visa") ? R.drawable.visa : R.drawable.mastercard_canvas);
            else
                imgTypeCard.setImageResource(R.mipmap.pagatodo_icon);
        }
        /*imageshae = (ImageView) getActivity().findViewById(R.id.deposito_Share);
        imageshae.setVisibility(View.VISIBLE);
        imageshae.setOnClickListener(this);*/
        String monto = StringUtils.getCurrencyValue(TransactionAdqData.getCurrentTransaction().getAmount());
        StringBuilder cashAmountBuilder = new StringBuilder(monto);
        // Limpiamos del caracter $ en caso de tenerlo
        int positionMoney = monto.indexOf("$");
        if (positionMoney == 0) {
            monto = cashAmountBuilder.deleteCharAt(0).toString();
        }

        String[] valueAmountArray = monto.split("\\.");
        tvMontoEntero.setText(valueAmountArray[0]);
        tvMontoDecimal.setText(valueAmountArray[1]);


        btnNextEnviarticket.setOnClickListener(this);
        // Automatic Ticket Send to User
        adqPresenter.sendTicket(RequestHeaders.getUsername(), true);

        edtEmailSendticket.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    text_email.setBackgroundResource(R.drawable.inputtext_active);
                }
            }
        });
    }

    @Override
    public void setValidationRules() {
    }

    @Override
    public void validateForm() {
        getDataForm();
        if (emailToSend.isEmpty()) {
            TransactionAdqData.resetCurrentTransaction();
            onEventListener.onEvent(AdqActivity.EVENT_GO_LOGIN_FRAGMENT, null);
            return;
        } else if (!emailToSend.isEmpty() && !ValidateForm.isValidEmailAddress(edtEmailSendticket.getText().toString())) {
            //showValidationError(getString(R.string.check_your_mail));
            text_email.setBackgroundResource(R.drawable.input_text_error);
            UI.showErrorSnackBar(getActivity(),getString(R.string.check_your_mail), Snackbar.LENGTH_SHORT);
            return;
        }
        text_email.setBackgroundResource(R.drawable.inputtext_normal);
        onValidationSuccess();
    }

    private void showValidationError(Object err) {
        showValidationError(0, err);
    }

    @Override
    public void showValidationError(int id, Object o) {
        UI.showToast(o.toString(), getActivity());
        mySeekBar.setProgress(0);
    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        mySeekBar.setProgress(0);
        adqPresenter.sendTicket(emailToSend, false);
    }

    @Override
    public void getDataForm() {
        emailToSend = edtEmailSendticket.getText().toString();
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
        DialogDoubleActions doubleActions = new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {

            }

            @Override
            public void actionCancel(Object... params) {

            }
        };
        UI.createSimpleCustomDialog("Error", error.toString(), getFragmentManager(), doubleActions, true, false);
    }

    @Override
    protected void continuePayment() {
        validateForm();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.deposito_Share) {
            takeScreenshot();
            //shareContent();
        }
        if (v.getId() == R.id.btnNextEnviarticket){
            validateForm();
        }



    }

    private void takeScreenshot() {
        try {
            View v1 = getActivity().getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            File carpeta = new File(Environment.getExternalStorageDirectory() + getString(R.string.path_image));
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }
            File imageFile = new File(Environment.getExternalStorageDirectory() + getString(R.string.path_image) + "/", System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            sendScreenshot(imageFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void sendScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        Uri uri = Uri.fromFile(imageFile);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Compartir Con..."));
    }
}


