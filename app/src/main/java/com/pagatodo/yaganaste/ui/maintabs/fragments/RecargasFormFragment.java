package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.RecargasPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IRecargasPresenter;
import com.pagatodo.yaganaste.utils.UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import butterknife.BindView;

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

    private SpinnerArrayAdapter dataAdapter;
    private IRecargasPresenter recargasPresenter;

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
            tab = TAB1;
            paymentsTabPresenter = ((PaymentsTabFragment) getParentFragment()).getPresenter();
            comercioItem = paymentsTabPresenter.getCarouselItem().getComercio();
            isIAVE = comercioItem.getIdComercio() == IAVE_ID;
            recargasPresenter = new RecargasPresenter(this, isIAVE);
            List<Double> montos = comercioItem.getListaMontos();
            montos.add(0, 0.0);
            dataAdapter = new SpinnerArrayAdapter(getContext(), TAB1, montos);
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
        }

        int longitudReferencia = comercioItem.getLongitudReferencia();
        if (longitudReferencia > 0 && longitudReferencia != 10) {
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(longitudReferencia);
            recargaNumber.setFilters(fArray);
        }

        if (isIAVE) {
            recargaNumber.setHint(getString(R.string.tag_number));
            layoutImageContact.setVisibility(View.GONE);
        } else {
            //recargaNumber.addTextChangedListener();
            recargaNumber.setHint(getString(R.string.phone_number_hint));
            layoutImageContact.setOnClickListener(this);
        }
        spinnerMontoRecarga.setAdapter(dataAdapter);
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
            payment = new Recarga(referencia, monto, comercioItem);
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
        monto = (Double) spinnerMontoRecarga.getSelectedItem();
        recargasPresenter.validateFields(referencia, monto, comercioItem.getLongitudReferencia(), isIAVE);
    }


    public void copyDataBaseDebug() throws IOException {
        Log.i("copiado", "inicia..");
        // abre la BD local
        InputStream myInput = new FileInputStream(new File(
                getContext().getDatabasePath("yaganaste.db").toURI())); // context.getAssets().open("db/"+DB_NAME);

        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/YaGanaste");
        if (!folder.exists()) {
            folder.mkdir();
        }

        File outFileName = new File(folder, "/yaganaste.db");
        outFileName.createNewFile();
        // Direccion de la
        //String outFileName = folder.getPath() + "/yaganaste.db"; // DataBaseUtilsB.DB_PATH +
        // DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.i("copiado", "termina..");
    }
}
