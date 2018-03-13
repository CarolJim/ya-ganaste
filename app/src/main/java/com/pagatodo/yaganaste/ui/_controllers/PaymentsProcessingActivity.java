package com.pagatodo.yaganaste.ui._controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ISessionExpired;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._controllers.manager.AddToFavoritesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarPositionActivity;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentAuthorizeFragment;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentSuccessFragment;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentsProcessingManager;
import com.pagatodo.yaganaste.ui.payments.presenters.PaymentsProcessingPresenter;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsProcessingPresenter;
import com.pagatodo.yaganaste.ui_wallet.fragments.PaymentAuthorizeFragmentWallwt;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.Direction.FORDWARD;
import static com.pagatodo.yaganaste.utils.Constants.MESSAGE;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_ENVIOS;
import static com.pagatodo.yaganaste.utils.Constants.RESULT;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_BACK_PRESS;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_FAIL;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_OK_CLOSE;


public class PaymentsProcessingActivity extends LoaderActivity implements PaymentsProcessingManager, ISessionExpired {

    public static final int IDCOMERCIO_YA_GANASTE = 8609, ACTION_SHARE = 1, MY_PERMISSIONS_REQUEST_STORAGE = 101;
    public static final String NOMBRE_COMERCIO = "nombreComercio";
    public static final String ID_COMERCIO = "idComercio";
    public static final String ID_TIPO_COMERCIO = "idTipoComercio";
    public static final String ID_TIPO_ENVIO = "idTipoEnvio";
    public static final String REFERENCIA = "referencia";
    public static final String TIPO_TAB = "tipoTab";
    public static final String CURRENT_TAB_ID = "currentTabId";
    public static final String DESTINATARIO = "destinatario";
    public static final int REQUEST_CODE_FAVORITES = 1;
    public static final String EVENT_SEND_PAYMENT = "EVENT_SEND_PAYMENT";
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.toolbarTest)
    Toolbar toolbar;
    AppCompatImageView btn_back;

    private View llMain;
    private Menu menu;

    IPaymentsProcessingPresenter presenter;
    Object pago, dataFavoritos;
    private boolean isAvailableToBack = false;
    //private MovementsTab tab;
    private String mensajeLoader = "";
    EjecutarTransaccionResponse response;
    private String nombreComercio = "";
    private int idComercio = 0;
    private int idTipoComercio = 0;
    private String referencia = "", nombreDest = "";
    private int idTipoEnvio = 0;
    private int tipoTab = 0;
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    int typeOperation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_generic_fragment_container);

        presenter = new PaymentsProcessingPresenter(this);
        pago = getIntent().getExtras().get("pagoItem");
        dataFavoritos = getIntent().getExtras().get("favoriteItem");
        typeOperation = (int) getIntent().getExtras().get("TAB");
        llMain = findViewById(R.id.ll_main);

        initViews();
        setSupportActionBar(toolbar);

        if (typeOperation != PAYMENT_ENVIOS) {
            changeToolbarVisibility(false);
            onEvent(EVENT_SEND_PAYMENT, pago);
        } else {
            hideLoader();
            isAvailableToBack = true;
            llMain.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gradient_bottom));
            loadFragment(PaymentAuthorizeFragment.newInstance((Payments) pago, (DataFavoritos) dataFavoritos), FORDWARD, true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.payment_processing_activity_menu, menu);
        menu.getItem(ACTION_SHARE).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add_to_favorite:
                break;
            case R.id.action_share:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == -1) {
                    ValidatePermissions.checkPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_STORAGE);
                } else {
                    ((PaymentSuccessFragment) getCurrentFragment()).takeScreenshot();
                }
                break;
            case R.id.action_delete:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_SEND_PAYMENT:
                try {
                    if (data instanceof Envios) {
                        // if (!nombreComercio.equals(YA_GANASTE)) {
                        int idComercio = ((Envios) data).getComercio().getIdComercio();
                        if (!(idComercio == IDCOMERCIO_YA_GANASTE)) {
                            mensajeLoader = getString(R.string.envio_interbancario, mensajeLoader);
                            showLoader(getString(R.string.procesando_envios_inter_loader));
                        }
                    }
                    showLoader(mensajeLoader);
                    presenter.sendPayment(typeOperation, data);
                } catch (OfflineException e) {
                    e.printStackTrace();
                    onError(getString(R.string.no_internet_access));
                }
                break;
        }
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

    private void initViews() {
        ButterKnife.bind(this);
        switch (typeOperation) {
            case Constants.PAYMENT_RECARGAS:
                mensajeLoader = getString(R.string.procesando_recarga_loader);
                break;
            case Constants.PAYMENT_SERVICIOS:
                mensajeLoader = getString(R.string.procesando_servicios_loader);
                break;
            case PAYMENT_ENVIOS:
                mensajeLoader = getString(R.string.procesando_envios_loader);
                break;
        }
        btn_back = (AppCompatImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public PaymentsProcessingManager getManager() {
        return this;
    }

    public IPaymentsProcessingPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onBackPressed() {
        if (!isLoaderShow) {
            Intent intent = new Intent();
            if (getCurrentFragment() instanceof PaymentAuthorizeFragment) {
                setResult(RESULT_CODE_BACK_PRESS, intent);
            } else {
                setResult(RESULT_CODE_FAIL, intent);
            }
            finish();
            //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    public void onSuccessPaymentRespone(DataSourceResult result) {
        isAvailableToBack = true;
        response = (EjecutarTransaccionResponse) result.getData();

        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            hideLoader();
            changeToolbarVisibility(true);
            setVisibilityPrefer(false);
            llMain.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gradient_bottom));
            /*Intent intent = new Intent();
            intent.putExtra(RESULT, Constants.RESULT_SUCCESS);*/
            setResult(RESULT_CODE_OK_CLOSE/*, intent*/);
            loadFragment(PaymentSuccessFragment.newInstance((Payments) pago, response), FORDWARD, true);
            showBack(false);
            menu.getItem(ACTION_SHARE).setVisible(true);
            saveDataResponse();
            UI.showSuccessSnackBar(this, response.getMensaje(), Snackbar.LENGTH_SHORT);
        } else {
            onFailPaimentResponse(result);
        }
    }

    @Override
    public void onFailPaimentResponse(DataSourceResult error) {
        String errorTxt = "";
        try {
            EjecutarTransaccionResponse response = (EjecutarTransaccionResponse) error.getData();
            if (response.getMensaje() != null)
                errorTxt = response.getMensaje();
        } catch (Exception ex) {
            ex.printStackTrace();
            errorTxt = error.toString();
        }
        onError(errorTxt);
    }

    private void onError(String message) {
        hideLoader();
        Intent intent = new Intent();
        intent.putExtra(RESULT, Constants.RESULT_ERROR);
        intent.putExtra(MESSAGE, message != null ? message : getString(R.string.error_respuesta));
        setResult(RESULT_CODE_FAIL, intent);
        UI.showErrorSnackBar(this, message != null ? message : getString(R.string.error_respuesta), Snackbar.LENGTH_SHORT);
        //finish();
        // showDialogMesage(message);

    }

    /**
     * Guardamos la informacion en variables que enviaremos a AddFavoritesActivity para procesar
     * los favoritos
     */
    private void saveDataResponse() {
        /**
         * Hacemos el Cast dependiendo de la Tab que estamos usando
         */
        if (pago instanceof Recarga) {
            nombreComercio = ((Recarga) pago).getComercio().getNombreComercio();
            idComercio = ((Recarga) pago).getComercio().getIdComercio();
            idTipoComercio = ((Recarga) pago).getComercio().getIdTipoComercio();
            referencia = ((Recarga) pago).getReferencia();
            idTipoEnvio = 0;
            tipoTab = 1;
        } else if (pago instanceof Servicios) {
            nombreComercio = ((Servicios) pago).getComercio().getNombreComercio();
            idComercio = ((Servicios) pago).getComercio().getIdComercio();
            idTipoComercio = ((Servicios) pago).getComercio().getIdTipoComercio();
            referencia = ((Servicios) pago).getReferencia();
            idTipoEnvio = 0;
            tipoTab = 2;
        } else if (pago instanceof Envios) {
            nombreComercio = ((Envios) pago).getComercio().getNombreComercio();
            idComercio = ((Envios) pago).getComercio().getIdComercio();
            idTipoComercio = ((Envios) pago).getComercio().getIdTipoComercio();
            referencia = ((Envios) pago).getReferencia();
            idTipoEnvio = ((Envios) pago).getTipoEnvio().getId();
            nombreDest = ((Envios) pago).getNombreDestinatario();
            tipoTab = 3;
        }
    }

    /**
     * Se encarga de enviar la informacion a AddFavoritesActivity en una actividad por resultado
     *
     * @param view
     */
    public void openAddFavoritos(View view) {
        if (!((Payments) pago).isFavorite()) {
           // Intent intent = new Intent(this, AddToFavoritesActivity.class);
            Intent intent = new Intent(this, FavoritesActivity.class);
            intent.putExtra(AddToFavoritesActivity.FAV_PROCESS, 1);
            intent.putExtra(NOMBRE_COMERCIO, nombreComercio);
            intent.putExtra(ID_COMERCIO, idComercio);
            intent.putExtra(ID_TIPO_COMERCIO, idTipoComercio);
            intent.putExtra(ID_TIPO_ENVIO, idTipoEnvio);
            intent.putExtra(REFERENCIA, referencia);
            intent.putExtra(CURRENT_TAB_ID, tipoTab);
            intent.putExtra(DESTINATARIO, nombreDest);
            intent.putExtra(FavoritesActivity.TYPE_FAV,
                    FavoritesActivity.TYPE_NEW_FAV_OPER);
            startActivityForResult(intent, REQUEST_CODE_FAVORITES);
        }
    }

    private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getSupportFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        finish();
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

    /**
     * Procesa el resultado de AddFavoritesActivity, localiza el fragmento en pantalla (PaymentSuccess)
     * y esconde el boton de agregar a favoritos porque el proccedimiento fue exitoso
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                // Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
                PaymentSuccessFragment fragment = (PaymentSuccessFragment)
                        getSupportFragmentManager().findFragmentById(R.id.container);
                fragment.hideAddFavorites();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Toast.makeText(this, "Fail Epic Fail", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * Creates a symmetric key in the Android Key Store which can only be used after the user has
     * authenticated with fingerprint.
     *
     * @param keyName                          the name of the key to be created
     * @param invalidatedByBiometricEnrollment if {@code false} is passed, the created key will not
     *                                         be invalidated even if a new fingerprint is enrolled.
     *                                         The default value is {@code true}, so passing
     *                                         {@code true} doesn't change the behavior
     *                                         (the key will be invalidated if a new fingerprint is
     *                                         enrolled.). Note that this parameter is only valid if
     *                                         the app works on Android N developer preview.
     */
    public void createKey(String keyName, boolean invalidatedByBiometricEnrollment) {
        // The enrolling flow for fingerprint. This is where you ask the user to set up fingerprint
        // for your flow. Use of keys is necessary if you need to know if the set of
        // enrolled fingerprints has changed.
        try {
            mKeyStore.load(null);
            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder

            KeyGenParameterSpec.Builder builder = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                builder = new KeyGenParameterSpec.Builder(keyName,
                        KeyProperties.PURPOSE_ENCRYPT |
                                KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        // Require the user to authenticate with a fingerprint to authorize every use
                        // of the key
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            }

            // This is a workaround to avoid crashes on devices whose API level is < 24
            // because KeyGenParameterSpec.Builder#setInvalidatedByBiometricEnrollment is only
            // visible on API level +24.
            // Ideally there should be a compat library for KeyGenParameterSpec.Builder but
            // which isn't available yet.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mKeyGenerator.init(builder.build());
            }
            mKeyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }


}
