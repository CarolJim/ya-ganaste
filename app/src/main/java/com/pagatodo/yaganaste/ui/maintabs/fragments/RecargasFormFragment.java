package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.RecargasPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IRecargasPresenter;
import com.pagatodo.yaganaste.utils.NumberTagPase;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

import butterknife.BindView;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB1;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.IAVE_ID;

/**
 * Created by Jordan on 12/04/2017.
 */

public class RecargasFormFragment extends PaymentFormBaseFragment implements PaymentsManager,
        View.OnClickListener {

    @BindView(R.id.recargaNumber)
    EditText recargaNumber;
    @BindView(R.id.sp_montoRecarga)
    Spinner spinnerMontoRecarga;
    @BindView(R.id.layoutImageContact)
    RelativeLayout layoutImageContact;
    @BindView(R.id.comisionText)
    StyleTextView comisionText;
    private int maxLength;
    PaymentsTabFragment fragment;

    boolean isIAVE;
    private SpinnerArrayAdapter dataAdapter;
    private IRecargasPresenter recargasPresenter;

    public static RecargasFormFragment newInstance() {
        RecargasFormFragment fragment = new RecargasFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            tab = TAB1;
            paymentsTabPresenter = ((PaymentsTabFragment) getParentFragment()).getPresenter();
            comercioItem = paymentsTabPresenter.getCarouselItem().getComercio();
            favoriteItem = paymentsTabPresenter.getCarouselItem().getFavoritos();
            if (comercioItem == null && favoriteItem != null) {
                comercioItem = paymentsTabPresenter.getComercioById(favoriteItem.getIdComercio());
            }
            isIAVE = comercioItem.getIdComercio() == IAVE_ID;
            recargasPresenter = new RecargasPresenter(this, isIAVE);
            List<Double> montos = comercioItem.getListaMontos();
            montos.add(0, 0D);
            dataAdapter = new SpinnerArrayAdapter(getContext(), TAB1, montos);

            // Hacemos una referencia directa al Fragment Padre
            fragment = (PaymentsTabFragment) getParentFragment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_recarga_form, container, false);

        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();
        if (comercioItem.getFormato().equals("N")) {
            recargaNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
            recargaNumber.setSingleLine();
        }

        int longitudReferencia = comercioItem.getLongitudReferencia();
        if (longitudReferencia > 0 && longitudReferencia != 10) {
            InputFilter[] fArray = new InputFilter[1];
            maxLength = Utils.calculateFilterLength(longitudReferencia);
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            recargaNumber.setFilters(fArray);
        }

        if (isIAVE) {
            recargaNumber.addTextChangedListener(new NumberTagPase(recargaNumber, maxLength));
            recargaNumber.setHint(getString(R.string.tag_number) + " (" + longitudReferencia + " Dígitos)");
            layoutImageContact.setVisibility(View.GONE);
        } else {
            recargaNumber.addTextChangedListener(new PhoneTextWatcher(recargaNumber));
            recargaNumber.setHint(getString(R.string.phone_number_hint));

            layoutImageContact.setOnClickListener(this);
        }

        recargaNumber.setSingleLine(true);
        recargaNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_DONE) {
                    UI.hideKeyBoard(getActivity());
                }
                return false;
            }
        });

        if (comercioItem.getSobrecargo() > 0) {
            comisionText.setText(String.format(getString(R.string.comision_service_payment), StringUtils.getCurrencyValue(comercioItem.getSobrecargo())));
        } else {
            comisionText.setVisibility(View.GONE);
        }
        spinnerMontoRecarga.setAdapter(dataAdapter);

        if (favoriteItem != null) {
            recargaNumber.setText(favoriteItem.getReferencia());
            //recargaNumber.setEnabled(false);
        }

        spinnerMontoRecarga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(App.getContext(), "Item " + position, Toast.LENGTH_SHORT).show();
                if(position != 0){
                    monto = (Double) spinnerMontoRecarga.getSelectedItem();
                    fragment.updateValueTabFrag(monto);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    protected void continuePayment() {
        if (!isValid) {
            showError();
            mySeekBar.setProgress(0);
            //copyDataBaseDebug();
        } else {
            //Se debe crear un objeto que se envía a la activity que realizará el pago
            //Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
            payment = new Recarga(referencia, monto, comercioItem, favoriteItem != null);
            sendPayment();
        }
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
        recargaNumber.setText(phoneNo);
    }


    @Override
    public void showError() {
        if (errorText != null && !errorText.equals("")) {
            //Toast.makeText(getContext(), errorText, Toast.LENGTH_SHORT).show();
            UI.createSimpleCustomDialog("Error", errorText, getActivity().getSupportFragmentManager(), getFragmentTag());
        }
    }

    @Override
    public void onError(String error) {
        //mySeekBar.setEnabled(false);
        isValid = false;
        errorText = error;
        //Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Double i) {
        //mySeekBar.setEnabled(true);
        this.monto = i;
        isValid = true;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        referencia = recargaNumber.getText().toString().trim();
        referencia = referencia.replaceAll(" ", "");
        monto = (Double) spinnerMontoRecarga.getSelectedItem();
        recargasPresenter.validateFields(referencia, monto, comercioItem.getLongitudReferencia(), isIAVE);
    }


}
