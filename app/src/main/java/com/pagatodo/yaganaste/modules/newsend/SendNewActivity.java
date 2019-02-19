package com.pagatodo.yaganaste.modules.newsend;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.newsend.SendFromCard.SendFromCardFragment;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.preferuser.interfases.ICropper;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.steelkiwi.cropiwa.image.CropIwaResultReceiver;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.RESUL_FAVORITES;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.CONTACTS_CONTRACT_LOCAL;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.FAVORITE_PROCESS;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE_COMERCE;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.CREDITCARD_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE_FROM_CERO;

public class SendNewActivity extends LoaderActivity implements SendNewContracts.Presenter, SendNewContracts.Listener,
        CropIwaResultReceiver.Listener, ICropper {
    @BindView(R.id.referencianumber_edtx)
    EditText referencianumber_edtx;
    private static final String TAG_ID_FRAGMENT = "TAG_ID_FRAGMENT";
    public static final int ID_ALL_FAVO = 101;

    public static final int PAYMENT_CARD = 102;
    public static final int PAYMENT_PHONE = 103;
    public static final int PAYMENT_CLABE = 104;
    CameraManager cameraManager;

    SendNewRouter router;
    int idFragment;

    ImageView imgAddfavo;

    public static Intent createIntent(Activity activity, int tag) {
        Intent intent = new Intent(activity, SendNewActivity.class);
        intent.putExtra(TAG_ID_FRAGMENT, tag);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new);

        imgAddfavo = (ImageView) findViewById(R.id.imgAddfavo);
        router = new SendNewRouter(this);
        cameraManager = new CameraManager(this);
        if (getIntent().getExtras() != null) {
            idFragment = getIntent().getExtras().getInt(TAG_ID_FRAGMENT);
        }
        initViews();
    }


    public void showaddfavo(boolean show) {

        if (show)
            imgAddfavo.setVisibility(View.VISIBLE);
        else
            imgAddfavo.setVisibility(View.GONE);
    }


    @Override
    public boolean requiresTimer() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraManager.setOnActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CONTACTS_CONTRACT) {
                contactPicked(data, 2);
            } else if (requestCode == CONTACTS_CONTRACT_LOCAL) {
                Fragment fragment = (SendFromCardFragment) getCurrentFragment();
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
        if (requestCode == CREDITCARD_READER_REQUEST_CODE) {
            Fragment fragment = (SendFromCardFragment) getCurrentFragment();
            fragment.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == BARCODE_READER_REQUEST_CODE_COMERCE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    try {
                        Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                        JsonElement jelement = new JsonParser().parse(barcode.displayValue);
                        JsonObject jobject = jelement.getAsJsonObject();
                        jobject = jobject.getAsJsonObject("Aux");
                        String plate = jobject.get("Pl").getAsString();

                        //interactor.valideteQR(plate);
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                        //onErrorValidatePlate("QR Invalido");
                    } catch (NullPointerException e) {
                        //onErrorValidatePlate("QR Invalido");
                    }
                    //interactor.onValidateQr(plate);
                    /*if (barcode.displayValue.contains("reference") &&
                            barcode.displayValue.contains("commerce") && barcode.displayValue.contains("codevisivility")) {
                        MyQrCommerce myQr = new Gson().fromJson(barcode.displayValue, MyQrCommerce.class);
                        Log.d("Ya codigo qr", myQr.getCommerce());
                        Log.d("Ya codigo qr", myQr.getReference());

                        loadFragment(PayQRFragment.newInstance(myQr.getCommerce(), myQr.getReference(), Boolean.parseBoolean(myQr.getCodevisivility())), R.id.fragment_container);
                    } else {
                        UI.showErrorSnackBar(this, getString(R.string.transfer_qr_invalid), Snackbar.LENGTH_SHORT);
                    }*/
                } else {
                    finish();
                }
            }
        }

    }

    @Override
    public void initViews() {
        switch (idFragment) {
            case ID_ALL_FAVO:
                router.showAllFavorites(Direction.FORDWARD);
                break;
            case PAYMENT_CARD:
                router.showsendfragment(Direction.FORDWARD, PAYMENT_CARD);
                break;
            case PAYMENT_PHONE:
                router.showsendfragment(Direction.FORDWARD, PAYMENT_PHONE);
                break;
            case PAYMENT_CLABE:
                router.showsendfragment(Direction.FORDWARD, PAYMENT_CLABE);
                break;
        }

        imgAddfavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddFavorite = new Intent(SendNewActivity.this, FavoritesActivity.class);
                intentAddFavorite.putExtra(CURRENT_TAB_ID, Constants.PAYMENT_ENVIOS);
                intentAddFavorite.putExtra(FAVORITE_PROCESS, NEW_FAVORITE_FROM_CERO);
                startActivityForResult(intentAddFavorite, RESUL_FAVORITES);
            }
        });

    }

    private void contactPicked(Intent data, int tipoNumero) {
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
            // recargaNumber.setText(phoneNo);
        } else {
            //number_card_edt.setText(phoneNo);
        }
    }

    @Override
    public void onCropSuccess(Uri croppedUri) {

    }

    @Override
    public void onCropFailed(Throwable e) {

    }

    @Override
    public void onCropper(Uri uri) {

    }

    @Override
    public void onHideProgress() {

    }

    @Override
    public void failLoadJpg() {

    }
}
