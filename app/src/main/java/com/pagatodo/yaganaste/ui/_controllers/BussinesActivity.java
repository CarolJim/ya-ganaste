package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.Window;
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
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.modules.emisor.WalletEmisorInteractor;
import com.pagatodo.yaganaste.modules.register.RegContracts;
import com.pagatodo.yaganaste.modules.register.RegInteractor;
import com.pagatodo.yaganaste.modules.registerAggregator.BusinessData.BusinessDataFragment;
import com.pagatodo.yaganaste.modules.registerAggregator.LinkedQRs.LinkedQRsFragment;
import com.pagatodo.yaganaste.modules.registerAggregator.NameQR.NameQRFragment;
import com.pagatodo.yaganaste.modules.registerAggregator.PhysicalCodeAggregator.PhysicalCodeAggregatorFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DatosNegocioFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DomicilioNegocioFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.InformacionLavadoDineroFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.StatusRegisterAdquirienteFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.AdditionalInformationFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.PreDomicilioNegocioFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.qrcode.Auxl;
import com.pagatodo.yaganaste.utils.qrcode.Qrlectura;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.EVENT_ERROR_DOCUMENTS;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.EVENT_SCAN_QR;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.ADQ_REVISION;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE_COMERCE;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENTS_ADQUIRENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_PROCESS;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_DOCUMENTACION;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_PENDIENTE;


public class BussinesActivity extends LoaderActivity implements
        RegContracts.Listener{

    private static final String AGENT_NUMBER = "AGENT_NUMBER";
    //Nuevo diseño-flujo
    public final static String EVENT_GO_BUSSINES_DATA = "EVENT_GO_BUSSINES_DATA";
    public final static String EVENT_GO_BUSSINES_PRE_ADDRESS = "EVENT_GO_BUSSINES_PRE_ADDRESS";
    public final static String EVENT_GO_BUSSINES_ADDRESS = "EVENT_GO_BUSSINES_ADDRESS";
    public final static String EVENT_GO_BUSSINES_DOCUMENTS = "EVENT_GO_BUSSINES_DOCUMENTS";
    public final static String EVENT_GO_BUSSINES_COMPLETE = "EVENT_GO_BUSSINES_COMPLETE";
    public final static String EVENT_GO_BUSSINES_DATA_BACK = "EVENT_GO_BUSSINES_DATA_BACK";
    public final static String EVENT_GO_BUSSINES_PRE_ADDRESS_BACK = "EVENT_GO_BUSSINES_PRE_ADDRESS_BACK";
    public final static String EVENT_GO_BUSSINES_ADDRESS_BACK = "EVENT_GO_BUSSINES_ADDRESS_BACK";
    public final static String EVENT_DOC_CHECK = "EVENT_DOC_CHECK";
    public final static String EVENT_HAS_QR = "EVENT_HAS_QR";
    public final static String EVENT_SUCCESS_AGGREGATOR = "EVENT_SUCCESS_AGGREGATOR";
    public final static String EVENT_NO_QR_LINKED = "EVENT_NO_QR_LINKED";
    public final static String EVENT_QR_LINKED = "EVENT_QR_LINKED";
    public final static String EVENT_SET_ADDRESS = "EVENT_SET_ADDRESS";
    public final static String EVENT_SET_BUSINESS_LIST = "EVENT_SET_BUSINESS_LIST";
    public final static String EVENT_SET_BUSINESS_LIST2 = "EVENT_SET_BUSINESS_LIST2";
    public final static String EVENT_SET_COLONIES_LIST = "EVENT_SET_COLONIES_LIST";
    public final static String EVENT_GO_BUSSINES_ADITIONAL_INFORMATION = "EVENT_GO_BUSSINES_INFORMACION_ADICIONAL";
    public final static String EVENT_GO_BUSSINES_ADITIONAL_INFORMATION_BACK = "EVENT_GO_BUSSINES_ADITIONAL_INFORMATION_BACK";
    public final static String EVENT_GO_BUSSINES_MONEY_LAUNDERING = "EVENT_GO_BUSSINES_MONEY_LAUNDERING";
    public final static String EVENT_GO_BUSSINES_MONEY_LAUNDERING_BACK = "EVENT_GO_BUSSINES_MONEY_LAUNDERING_BACK";
    private WalletEmisorInteractor interactor;
    private DataObtenerDomicilio domicilio;
    private List<Giros> girosComercio;
    private List<ColoniasResponse> listaColonias;
    private String numAgente = "0";

    private RegInteractor regInteractor;

    public static Intent createIntent(Context from, String numAgente) {
        return new Intent(from, BussinesActivity.class).putExtra(AGENT_NUMBER, numAgente);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_container);
        //presenterAccount = new AccountPresenterNew(this);
        //interactor = new WalletEmisorInteractor(this, this);
        regInteractor=new RegInteractor(this,this);
        setUpActionBar();
        setVisibilityPrefer(false);
        numAgente = getIntent().getExtras().getString(AGENT_NUMBER);


        int Idestatus = 5;
        boolean esAgente = App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false);
        if (esAgente) {
            try {
                Idestatus = new DatabaseManager().getIdEstatusAgente(numAgente);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (Idestatus != CRM_DOCTO_APROBADO && App.getInstance().getPrefs().loadDataInt(ESTATUS_DOCUMENTACION) != STATUS_DOCTO_PENDIENTE) {
                App.getInstance().getPrefs().saveDataBool(ADQ_PROCESS, true);
            } else {
                App.getInstance().getPrefs().saveDataBool(ADQ_PROCESS, false);
            }
        } else {
            App.getInstance().getPrefs().saveDataBool(ADQ_PROCESS, false);
        }
        if (esAgente && Idestatus == IdEstatus.I7.getId()) {
            loadFragment(StatusRegisterAdquirienteFragment.newInstance(), Direction.FORDWARD);
        } else if (esAgente && Idestatus == IdEstatus.I8.getId()) {
            loadFragment(StatusRegisterAdquirienteFragment.newInstance(), Direction.FORDWARD);
        } else if (esAgente && Idestatus == IdEstatus.I9.getId()) {
            loadFragment(StatusRegisterAdquirienteFragment.newInstance(), Direction.FORDWARD);
        } else if (esAgente && Idestatus == IdEstatus.I10.getId()) {
            loadFragment(StatusRegisterAdquirienteFragment.newInstance(), Direction.FORDWARD);
        } else if (esAgente && Idestatus == IdEstatus.I11.getId()) {
            loadFragment(StatusRegisterAdquirienteFragment.newInstance(), Direction.FORDWARD);
        } else if (esAgente && Idestatus == IdEstatus.I13.getId()) {
            loadFragment(StatusRegisterAdquirienteFragment.newInstance(), Direction.FORDWARD);
        } else if (App.getInstance().getPrefs().loadDataBoolean(ADQ_PROCESS, false)) {
            loadFragment(DocumentosFragment.newInstance(), Direction.FORDWARD);
            showBack(true);
        } else {
            loadFragment(BusinessDataFragment.newInstance(this), Direction.FORDWARD, true);
        }
        //pref = App.getInstance().getPrefs();
    }

    @Override
    public void onEvent(String event, Object o) {
        super.onEvent(event, o);
        switch (event) {
            case EVENT_ERROR_DOCUMENTS:
                loadFragment(DocumentosFragment.newInstance(), Direction.FORDWARD);
                break;
                case EVENT_SCAN_QR:
                    Intent intentqr = new Intent(this, ScannVisionActivity.class);
                    intentqr.putExtra(ScannVisionActivity.QRObject, true);
                    this.startActivityForResult(intentqr, BARCODE_READER_REQUEST_CODE_COMERCE);
                break;
                case EVENT_HAS_QR:
                loadFragment(PhysicalCodeAggregatorFragment.newInstance(), Direction.FORDWARD,false);
                break;
                case EVENT_NO_QR_LINKED:
                loadFragment(NameQRFragment.newInstance("",R.string.title_code_digital_fragment), Direction.FORDWARD,false);
                break;
                case EVENT_QR_LINKED:
                loadFragment(LinkedQRsFragment.newInstance(), Direction.FORDWARD,false);
                break;
                case EVENT_SUCCESS_AGGREGATOR:
                loadFragment(com.pagatodo.yaganaste.modules.registerAggregator.RegisterComplete.RegisterCompleteFragment.newInstance(), Direction.FORDWARD,false);
                break;
            case EVENT_GO_BUSSINES_DATA:
                loadFragment(DatosNegocioFragment.newInstance(girosComercio), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_PRE_ADDRESS:
                loadFragment(PreDomicilioNegocioFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_PRE_ADDRESS_BACK:
                loadFragment(DatosNegocioFragment.newInstance(girosComercio), Direction.BACK, true);
                RegisterAgent.resetBussinessAddress();
                listaColonias = null;
                break;
            case EVENT_GO_BUSSINES_DATA_BACK:
                loadFragment(PreDomicilioNegocioFragment.newInstance(), Direction.BACK, false);
                RegisterAgent.resetBussinessAddress();
                listaColonias = null;
                break;
            case EVENT_GO_BUSSINES_ADDRESS:
                loadFragment(DomicilioNegocioFragment.newInstance(domicilio, listaColonias, null), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_ADDRESS_BACK:
                //loadFragment(PreDomicilioNegocioFragment.newInstance(), Direction.BACK, false);
                resetRegisterData();
                setResult(TabActivity.RESULT_ADQUIRENTE_SUCCESS);
                finish();
                break;
            case EVENT_GO_BUSSINES_ADITIONAL_INFORMATION:
                loadFragment(AdditionalInformationFragment.newInstance(), Direction.FORDWARD, true);
                break;
            case EVENT_GO_BUSSINES_ADITIONAL_INFORMATION_BACK:
                RegisterAgent.getInstance().resetAditionalInformation();
                loadFragment(DomicilioNegocioFragment.newInstance(domicilio, listaColonias, null), Direction.BACK, false);
                break;
            case EVENT_GO_BUSSINES_MONEY_LAUNDERING:
                loadFragment(InformacionLavadoDineroFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_MONEY_LAUNDERING_BACK:
                loadFragment(AdditionalInformationFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_DOCUMENTS:
                loadFragment(DocumentosFragment.newInstance(), Direction.FORDWARD, false);
                showBack(true);
                break;
            case EVENT_GO_BUSSINES_COMPLETE:
                showBack(false);
                loadFragment(RegisterCompleteFragment.newInstance(ADQ_REVISION), Direction.FORDWARD, false);
                break;
            case EVENT_GO_MAINTAB:
                resetRegisterData();
                finish();
                break;
            case EVENT_DOC_CHECK:
                App.getInstance().getPrefs().saveDataInt(ESTATUS_DOCUMENTACION, STATUS_DOCTO_PENDIENTE);
                App.getInstance().getPrefs().saveDataBool(ES_AGENTE, true);
                setResult(TabActivity.RESULT_ADQUIRENTE_SUCCESS);
                finish();
                break;

            case EVENT_SET_ADDRESS:
                this.domicilio = (DataObtenerDomicilio) o;
                break;

            case EVENT_SET_BUSINESS_LIST:
                this.girosComercio = (List<Giros>) o;
                break;
            case EVENT_SET_COLONIES_LIST:
                this.listaColonias = (List<ColoniasResponse>) o;
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (!isLoaderShow) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof DatosNegocioFragment) {
                RegisterAgent.resetRegisterAgent();
                finish();
            } else if (currentFragment instanceof PreDomicilioNegocioFragment) {
                onEvent(EVENT_GO_BUSSINES_PRE_ADDRESS_BACK, null);
            } else if (currentFragment instanceof DomicilioNegocioFragment) {
                onEvent(EVENT_GO_BUSSINES_DATA_BACK, null);
            } else if (currentFragment instanceof AdditionalInformationFragment) {
                onEvent(EVENT_GO_BUSSINES_ADITIONAL_INFORMATION_BACK, null);
            } else if (currentFragment instanceof InformacionLavadoDineroFragment) {
                onEvent(EVENT_GO_BUSSINES_MONEY_LAUNDERING_BACK, null);
            } else if (currentFragment instanceof DocumentosFragment) {
                ((DocumentosFragment) currentFragment).onBtnBack();
            } else if (currentFragment instanceof RegisterCompleteFragment) {

            } else {
                RegisterAgent.resetRegisterAgent();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getCurrentFragment();
        fragment.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BARCODE_READER_REQUEST_CODE_COMERCE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    try {
                        Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                        JsonElement jelement = new JsonParser().parse(barcode.displayValue);
                        JsonObject jobject = jelement.getAsJsonObject();
                        jobject = jobject.getAsJsonObject("Aux");
                        String plate = jobject.get("Pl").getAsString();
                        regInteractor.onValidateQr(plate);
                        //interactor.valideteQR(plate);
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


    private void resetRegisterData() {
        RegisterAgent.resetRegisterAgent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }


    @Override
    public void onErrorService(String error) {

    }

    @Override
    public void onSuccessValidatePlate(String plate) {
        onEvent(EVENT_SUCCESS_AGGREGATOR,null);
    }

    public void onErrorValidatePlate(String error) {
        UI.showErrorSnackBar(this, error, Snackbar.LENGTH_SHORT);
    }

}

