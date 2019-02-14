package com.pagatodo.yaganaste.modules.newsend.SendFromCard;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.card.payment.CardIOActivity;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pagatodo.view_manager.components.HeadWallet;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.qrcode.InterbankQr;
import com.pagatodo.yaganaste.utils.qrcode.MyQr;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.CONTACTS_CONTRACT_LOCAL;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.CREDITCARD_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_ENVIOS;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_RECARGAS;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_SERVICIOS;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendFromCardFragment extends GenericFragment {
    @BindView(R.id.headWallet)
    HeadWallet headWallet;
    @BindView(R.id.HeadAccount)
    HeadWallet HeadAccount;
    @BindView(R.id.number_card_edt)
    EditText number_card_edt;

    CameraManager cameraManager;
    private View rootView;

    public SendFromCardFragment() {
        // Required empty public constructor
    }
    public static SendFromCardFragment newInstance(){
        return new SendFromCardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_send_from_card, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        headWallet.setAmount(App.getInstance().getPrefs().loadData(USER_BALANCE));
    }
/*
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera:
                Intent scanIntent = new Intent(this,CardIOActivity.class);

                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true);
                scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true);
                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true);
                scanIntent.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false);
                scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, true);
                scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, true);
                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, true);
                //scanIntent.putExtra(CardIOActivity.EXTRA_UNBLUR_DIGITS, 8);

                // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
                startActivityForResult(scanIntent, CREDITCARD_READER_REQUEST_CODE);
                break;
        }
    }
*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*cameraManager.setOnActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CONTACTS_CONTRACT) {
                contactPicked(data, 2);
                // Ocultamos el mensaje de error si esta visible
                // Borrar editReferError.setVisibilityImageError(false);
            } else if (requestCode == CONTACTS_CONTRACT_LOCAL) {
                contactPicked(data, 1);
                // Ocultamos el mensaje de error si esta visible
                // Borrar  editReferError.setVisibilityImageError(false);
            }
        }*/
/*
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    if (current_tab == PAYMENT_RECARGAS) {
                        recargaNumber.setText(barcode.displayValue);
                    } else if (current_tab == PAYMENT_SERVICIOS) {
                        referenceNumber.setText(barcode.displayValue);
                    } else if (current_tab == PAYMENT_ENVIOS) {
                        if (barcode.displayValue.contains("userName") && barcode.displayValue.contains("phoneNumber") &&
                                barcode.displayValue.contains("cardNumber") && barcode.displayValue.contains("clabe")) {
                            MyQr myQr = new Gson().fromJson(barcode.displayValue, MyQr.class);
                            number_card_edt.setText(myQr.getClabe());
                        } else if (barcode.displayValue.contains("Opt") && barcode.displayValue.contains("Aux") &&
                                barcode.displayValue.contains("Typ") && barcode.displayValue.contains("Ver")) {
                            InterbankQr interbankQr = new Gson().fromJson(barcode.displayValue, InterbankQr.class);
                            number_card_edt.setText(interbankQr.getOptionalData().beneficiaryAccount);
                        } else {
                            UI.showErrorSnackBar(this, getString(R.string.transfer_qr_invalid), Snackbar.LENGTH_SHORT);
                        }
                    }
                }
            }
        }*/

        if (requestCode == CREDITCARD_READER_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                resultDisplayStr = "Card Number: " + scanResult.getFormattedCardNumber();
                number_card_edt.setText(scanResult.getFormattedCardNumber().trim());
            } else {
                resultDisplayStr = "Scan was canceled.";
            }
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.e(getString(R.string.app_name), "@CreditCard Scanner: " + resultDisplayStr);
            }
        }
    }
    /*private void contactPicked(Intent data, int tipoNumero) {
        Cursor cursor;
        String phoneNo = null;
        Uri uri = data.getData();
        cursor = this.getContentResolver().query(uri, null, null, null, null);
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

        // Hacemos set en el elemento, ya sea de TAE o de Envios
        if (tipoNumero == 1) {
            //recargaNumber.setText(phoneNo);
        } else {
            number_card_edt.setText(phoneNo);
        }
    }*/
}
