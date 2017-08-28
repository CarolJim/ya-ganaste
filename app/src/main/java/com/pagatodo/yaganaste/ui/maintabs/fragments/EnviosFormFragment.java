package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataTitular;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.EnviosManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.EnviosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IEnviosPresenter;
import com.pagatodo.yaganaste.utils.NumberCardTextWatcher;
import com.pagatodo.yaganaste.utils.NumberClabeTextWatcher;
import com.pagatodo.yaganaste.utils.NumberTextWatcher;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_NEXT;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB3;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CABLE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Recursos.IDCOMERCIO_YA_GANASTE;

/**
 * Created by Jordan on 12/04/2017.
 */

public class EnviosFormFragment extends PaymentFormBaseFragment implements EnviosManager,
        AdapterView.OnItemSelectedListener, TextView.OnEditorActionListener, View.OnClickListener {

    @BindView(R.id.tipoEnvio)
    Spinner tipoEnvio;
    @BindView(R.id.cardNumber)
    StyleEdittext cardNumber;
    @BindView(R.id.layout_cardNumber)
    LinearLayout layout_cardNumber;
    @BindView(R.id.amountToSend)
    EditText amountToSend;
    @BindView(R.id.receiverName)
    EditText receiverName;
    @BindView(R.id.concept)
    EditText concept;
    @BindView(R.id.numberReference)
    EditText numberReference;
    @BindView(R.id.fragment_envios_referencia_layout)
    LinearLayout referenciaLayout;
    @BindView(R.id.layoutImageContact)
    RelativeLayout layoutImageContact;
    @BindView(R.id.imgMakePaymentContact)
    ImageView imgMakePaymentContact;


    TransferType selectedType;
    IEnviosPresenter enviosPresenter;
    private String nombreDestinatario;
    private String referenciaNumber;
    int keyIdComercio;
    int maxLength;
    private boolean isCuentaValida = true;

    public static EnviosFormFragment newInstance() {
        EnviosFormFragment fragment = new EnviosFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            tab = TAB3;
            paymentsTabPresenter = ((PaymentsTabFragment) getParentFragment()).getPresenter();
            comercioItem = paymentsTabPresenter.getCarouselItem().getComercio();
            keyIdComercio = comercioItem.getIdComercio();
            enviosPresenter = new EnviosPresenter(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_envios_form, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();

        List<String> tipoPago = new ArrayList<>();

        tipoPago.add(0, "");
        tipoPago.add(NUMERO_TELEFONO.getId(), NUMERO_TELEFONO.getName(getContext()));
        tipoPago.add(NUMERO_TARJETA.getId(), NUMERO_TARJETA.getName(getContext()));

        if (keyIdComercio == IDCOMERCIO_YA_GANASTE) {
            //receiverName.setVisibility(GONE);
            receiverName.setEnabled(false);
            receiverName.cancelLongPress();
            receiverName.setClickable(false);
            referenciaLayout.setVisibility(GONE);
            numberReference.setText("123456");
            //cardNumber.setOnFocusChangeListener(this);
            amountToSend.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            amountToSend.setOnEditorActionListener(this);

            concept.setImeOptions(IME_ACTION_DONE);

        } else {
            tipoPago.add(CABLE.getId(), CABLE.getName(getContext()));
            receiverName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
            concept.setImeOptions(IME_ACTION_NEXT);
        }

        concept.setSingleLine(true);
        concept.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_DONE) {
                    UI.hideKeyBoard(getActivity());
                } else if (actionId == IME_ACTION_NEXT) {
                    numberReference.requestFocus();
                }
                return false;
            }
        });

        SpinnerArrayAdapter dataAdapter = new SpinnerArrayAdapter(getContext(), TAB3, tipoPago);
        tipoEnvio.setAdapter(dataAdapter);
        tipoEnvio.setOnItemSelectedListener(this);
        amountToSend.addTextChangedListener(new NumberTextWatcher(amountToSend));
        receiverName.setSingleLine();

    }

    @Override
    public void continuePayment() {
        if (!isCuentaValida) {
            Formatter formatter = new Formatter();
            showError(formatter.format(getString(R.string.error_cuenta_no_valida), tipoEnvio.getSelectedItem().toString()).toString());
            formatter.close();
            mySeekBar.setProgress(0);
        } else if (!isValid) {
            showError();
            mySeekBar.setProgress(0);
        } else {
            //Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
            //Se debe crear un objeto que se envía a la activity que realizará el pago
            payment = new Envios(selectedType, referencia, monto, nombreDestinatario, concepto, referenciaNumber, comercioItem);
            sendPayment();
        }
    }

    @Override
    public void showError() {
        if (errorText != null && !errorText.equals("")) {
            //Toast.makeText(getContext(), errorText, Toast.LENGTH_SHORT).show();
            UI.createSimpleCustomDialog("Error", errorText, getActivity().getSupportFragmentManager(), getFragmentTag());
        }
    }

    @Override
    public void showError(String text) {
        if (!TextUtils.isEmpty(text)) {
            UI.createSimpleCustomDialog("Error", text, getActivity().getSupportFragmentManager(), getFragmentTag());
        }
    }

    @Override
    public void onError(String error) {
        isValid = false;
        errorText = error;
    }

    @Override
    public void onSuccess(Double monto) {
        this.monto = monto;
        isValid = true;
    }

    @Override
    public void setTitularName(DataTitular dataTitular) {
        isCuentaValida = true;
        receiverName.setText(dataTitular.getNombre().concat(" ").concat(dataTitular.getPrimerApellido()).concat(" ").concat(dataTitular.getSegundoApellido()));
    }

    @Override
    public void onFailGetTitulaName() {
        isCuentaValida = false;
        clearTitularName();
    }


    private void clearTitularName() {
        isCuentaValida = false;
        receiverName.setText("");
    }

    @Override
    public void showLoader(String text) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, text);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        referencia = cardNumber.getText().toString().trim();
        referencia = referencia.replaceAll(" ", "");
        concepto = concept.getText().toString().trim();
        nombreDestinatario = receiverName.getText().toString().trim();
        referenciaNumber = numberReference.getText().toString().trim();

        enviosPresenter.validateForms(selectedType, referencia,
                amountToSend.getText().toString().trim(),
                nombreDestinatario,
                concepto,
                referenciaNumber);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        layout_cardNumber.setVisibility(View.VISIBLE);
        cardNumber.setText("");
        cardNumber.removeTextChangedListener();


        InputFilter[] fArray = new InputFilter[1];

        if (position == NUMERO_TARJETA.getId()) {
            maxLength = comercioItem.getIdComercio() == 814 ? 17 : 19;
            cardNumber.setHint(getString(R.string.card_number, String.valueOf(
                    comercioItem.getIdComercio() == 814 ? 15 : 16
                    )));
            NumberCardTextWatcher numberCardTextWatcher = new NumberCardTextWatcher(cardNumber, maxLength);
            if (keyIdComercio == IDCOMERCIO_YA_GANASTE) {
                numberCardTextWatcher.setOnITextChangeListener(this);
            }
            cardNumber.addTextChangedListener(numberCardTextWatcher);
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
            selectedType = NUMERO_TARJETA;
        } else if (position == NUMERO_TELEFONO.getId()) {
            maxLength = 12;
            cardNumber.setHint(getString(R.string.transfer_phone_cellphone));
            layoutImageContact.setVisibility(View.VISIBLE);
            layoutImageContact.setOnClickListener(this);
            PhoneTextWatcher phoneTextWatcher = new PhoneTextWatcher(cardNumber);
            if (keyIdComercio == IDCOMERCIO_YA_GANASTE) {
                phoneTextWatcher.setOnITextChangeListener(this);
            }
            cardNumber.addTextChangedListener(phoneTextWatcher);
            selectedType = NUMERO_TELEFONO;
        } else if (position == CABLE.getId()) {
            maxLength = 22;
            cardNumber.setHint(getString(R.string.transfer_cable));
            cardNumber.addTextChangedListener(new NumberClabeTextWatcher(cardNumber));
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
            selectedType = CABLE;
        } else {
            maxLength = 2;
            cardNumber.setHint("");
            layout_cardNumber.setVisibility(GONE);
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
            selectedType = null;
        }

        fArray[0] = new InputFilter.LengthFilter(maxLength);
        cardNumber.setFilters(fArray);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*@Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.cardNumber && !hasFocus
                && cardNumber.getText().length() == maxLength) {
            //Buscar Nombre
            enviosPresenter.getTitularName(cardNumber.getText().toString().trim());
        }
    }*/

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_NEXT) {
            concept.requestFocus();
            return true;
        }
        return false;
    }

    @Override
    public void onTextChanged() {
        isCuentaValida = false;
        receiverName.setText("");
    }

    @Override
    public void onTextComplete() {
        enviosPresenter.getTitularName(cardNumber.getText().toString().trim());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layoutImageContact) {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            getActivity().startActivityForResult(contactPickerIntent, CONTACTS_CONTRACT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CONTACTS_CONTRACT) {
                contactPicked(data);
            }
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor;
        String phoneNo = null;
        Uri uri = data.getData();
        cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //get column index of the Phone Number
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            //int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex).replaceAll("\\s", "").replaceAll("\\+", "").replaceAll("-", "").trim();
            if (phoneNo.length() > 10) {
                phoneNo = phoneNo.substring(phoneNo.length() - 10);
            }
        }
        cardNumber.setText(phoneNo);
    }
}
