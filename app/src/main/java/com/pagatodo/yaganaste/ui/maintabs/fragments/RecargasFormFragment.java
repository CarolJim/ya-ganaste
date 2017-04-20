package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.RecargasPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsFormPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsTabPresenter;
import com.pagatodo.yaganaste.utils.Constants;

import java.util.List;

import butterknife.BindView;

import static com.pagatodo.yaganaste.utils.Constants.*;

/**
 * Created by Jordan on 12/04/2017.
 */

public class RecargasFormFragment extends PaymentFormBaseFragment implements PaymentsManager,
        View.OnClickListener, TextWatcher, AdapterView.OnItemSelectedListener {

    @BindView(R.id.recargaNumber)
    EditText recargaNumber;
    @BindView(R.id.sp_montoRecarga)
    Spinner spinnerMontoRecarga;
    @BindView(R.id.layoutImageContact)
    RelativeLayout layoutImageContact;

    private SpinnerArrayAdapter dataAdapter;
    private IPaymentsTabPresenter paymentsTabPresenter;
    private IPaymentsFormPresenter recargasPresenter;
    private ComercioResponse comercioItem;
    private String errorText;

    private Double monto;
    private String numero = "";
    boolean isIAVE;

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
            paymentsTabPresenter = ((PaymentsTabFragment) getParentFragment()).getPresenter();

            comercioItem = paymentsTabPresenter.getCarouselItem().getComercio();
            isIAVE = comercioItem.getIdComercio() == 7;
            recargasPresenter = new RecargasPresenter(this, isIAVE);
            List<Double> montos = comercioItem.getListaMontos();
            montos.add(0, 0.0);
            dataAdapter = new SpinnerArrayAdapter(getContext(), MovementsTab.TAB1, montos);
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
        if (isIAVE) {
            recargaNumber.setHint(getString(R.string.tag_number));
            layoutImageContact.setVisibility(View.GONE);
        } else {
            recargaNumber.setHint(getString(R.string.phone_number_hint));
            layoutImageContact.setOnClickListener(this);
        }
        recargaNumber.addTextChangedListener(this);

        spinnerMontoRecarga.setAdapter(dataAdapter);
        spinnerMontoRecarga.setOnItemSelectedListener(this);
    }



    @Override
    protected void continuePayment() {
        if (!isValid) {
            showError();
            mySeekBar.setProgress(0);
        } else {
            Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
            //Se debe crear un objeto que se envía a la activity que realizará el pago
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.i(getFragmentTag(), s.toString());
        numero = s.toString().trim();
        recargasPresenter.validateFields(numero, monto);
    }

    @Override
    public void showError() {
        if (errorText != null && !errorText.equals("")) {
            Toast.makeText(getContext(), errorText, Toast.LENGTH_SHORT).show();
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
    public void onSuccess() {
        //mySeekBar.setEnabled(true);
        isValid = true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        monto = (Double) dataAdapter.getItem(position);
        recargasPresenter.validateFields(recargaNumber.getText().toString().trim(), monto);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
}
