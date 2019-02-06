package com.pagatodo.yaganaste.modules.registerAggregator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.RegisterAggregatorNew;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerBancoBinResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarTitularCuentaResponse;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.modules.emisor.WalletEmisorContracts;
import com.pagatodo.yaganaste.modules.emisor.WalletEmisorInteractor;
import com.pagatodo.yaganaste.modules.management.response.QrValidateResponse;
import com.pagatodo.yaganaste.modules.management.singletons.NotificationSingleton;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PersonalAccountFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.qrcode.Auxl;
import com.pagatodo.yaganaste.utils.qrcode.Qrlectura;

import java.util.ArrayList;
import java.util.Objects;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.REQUEST_CODE_FAVORITES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_MVIMIENTOS_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_MVIMIENTOS_EMISOR;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE_COMERCE;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENTS_ADQUIRENTE;

public class AggregatorActivity extends LoaderActivity implements
        AggregatorContracts.Presenter, AggregatorContracts.Listener,
        WalletEmisorContracts.Listener {
    private WalletEmisorInteractor interactor;
    private AggregatorRouter router;

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, AggregatorActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggregator);
        interactor = new WalletEmisorInteractor(this, this);
        RegisterAggregatorNew.getInstance().setqRs(new ArrayList<>());
       // router = new AggregatorRouter(this);
        initViews();
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }

    @Override
    public void initViews() {
        router.showBusinessData(Direction.FORDWARD);
    }

    @Override
    public void nextStep() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BARCODE_READER_REQUEST_CODE_COMERCE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    try {
                        Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                        JsonElement jelement = new JsonParser().parse(barcode.displayValue);
                        JsonObject jobject = jelement.getAsJsonObject();
                        jobject = jobject.getAsJsonObject("Aux");
                        String plate = jobject.get("Pl").getAsString();

                        interactor.valideteQR(plate);
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                        onErrorValidatePlate("QR Invalido");
                    } catch (NullPointerException e) {
                        onErrorValidatePlate("QR Invalido");
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
        } else if (requestCode == DocumentosFragment.REQUEST_TAKE_PHOTO || requestCode == DocumentosFragment.SELECT_FILE_PHOTO
                || requestCode == PAYMENTS_ADQUIRENTE) {
            getCurrentFragment().onActivityResult(requestCode, resultCode, data);
        } else if (data != null && requestCode != BARCODE_READER_REQUEST_CODE_COMERCE) {
            getCurrentFragment().onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == Constants.BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    if (barcode.displayValue.contains("Pl")) {
                        Qrlectura myQr = new Gson().fromJson(barcode.displayValue, Qrlectura.class);
                        Auxl auxl = myQr.getAux();
                        String plate = auxl.getPl();

                    /*    cardNumber.setText(myQr.getClabe());
                        receiverName.setText(myQr.getUserName());*/
                    }
                } else {
                    finish();
                }
            } else if (resultCode == 153) {

            }

        } else {
            finish();
        }

    }


    @Override
    public void backStep() {

    }


    public void onErrorValidatePlate(String error) {
        UI.showErrorSnackBar(this, error, Snackbar.LENGTH_SHORT);
    }


    public AggregatorRouter getRouter() {
        return router;
    }

    @Override
    public void showLoad() {
        this.showLoader("");
    }

    @Override
    public void hideLoad() {
        this.hideLoader();
    }

    @Override
    public void onSouccesValidateCard() {

    }

    @Override
    public void onErrorRequest(String msj) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(findViewById(R.id.fragment_container).getWindowToken(), 0);
        UI.showErrorSnackBar(this, msj, Snackbar.LENGTH_SHORT);
        //this.router.onShowGeneratePIN();
    }

    @Override
    public void onSouccesDataQR(QrValidateResponse qRresponse) {


    }

    @Override
    public void onSouccesGetTitular(ConsultarTitularCuentaResponse dataTitular) {

    }

    @Override
    public void onSouccessgetgetDataBank(ObtenerBancoBinResponse data) {

    }


}
