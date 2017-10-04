package com.pagatodo.yaganaste.ui._controllers.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFotoFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosNewDatosResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IAddFavoritesActivity;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesPresenter;
import com.pagatodo.yaganaste.ui.addfavorites.presenters.FavoritesPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.DESTINATARIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_TIPO_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_TIPO_ENVIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.NOMBRE_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.REFERENCIA;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.TIPO_TAB;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

/**
 * Encargada de dar de alta Favoritos, primero en el servicio y luego en la base local
 */
public class AddFavoritesActivity extends LoaderActivity implements IAddFavoritesActivity,
        IListaOpcionesView, ValidationForms {

    @BindView(R.id.add_favorites_alias)
    CustomValidationEditText editTextAlias;
    @BindView(R.id.add_favorites_servicio)
    CustomValidationEditText textViewServ;
    @BindView(R.id.add_favorites_referencia)
    CustomValidationEditText textViewRef;
    @BindView(R.id.add_favorites_tipo)
    CustomValidationEditText textViewTipo;
    @BindView(R.id.add_favorites_camera)
    UploadDocumentView imageViewCamera;
    @BindView(R.id.errorAliasMessage)
    ErrorMessage errorAliasMessage;
    @BindView(R.id.add_favorites_linear_tipo)
    LinearLayout linearTipo;
    @BindView(R.id.add_favorites_foto_et)
    CustomValidationEditText editFoto;
    @BindView(R.id.add_favorites_foto_error)
    ErrorMessage editFotoError;

    IFavoritesPresenter favoritesPresenter;
    int idTipoComercio;
    int idComercio;
    String nombreComercio;
    String mReferencia;
    String formatoComercio;
    String stringFoto, nombreDest;
    int longitudRefer;
    int tipoTab;
    CameraManager cameraManager;
    private boolean errorIsShowed = false;
    private int idTipoEnvio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorites);

        favoritesPresenter = new FavoritesPresenter(this);

        Intent intent = getIntent();
        idComercio = intent.getIntExtra(ID_COMERCIO, 99);
        idTipoComercio = intent.getIntExtra(ID_TIPO_COMERCIO, 98);
        idTipoEnvio = intent.getIntExtra(ID_TIPO_ENVIO, 97);
        nombreComercio = intent.getStringExtra(NOMBRE_COMERCIO);
        mReferencia = intent.getStringExtra(REFERENCIA);
        formatoComercio = intent.getStringExtra(REFERENCIA);
        tipoTab = intent.getIntExtra(TIPO_TAB, 96);
        nombreDest = intent.getStringExtra(DESTINATARIO);

        ButterKnife.bind(this);
        imageViewCamera.setVisibilityStatus(true);
        imageViewCamera.setStatusImage(ContextCompat.getDrawable(this, R.drawable.ic_status_upload));
        //imageViewCamera.setNewHW(300, 300);

        textViewServ.setText(nombreComercio);

        String formatoPago = mReferencia;

        if (tipoTab == 1) {
            if (idComercio != 7) {
                formatoPago = StringUtils.formatoPagoMedios(formatoPago);
            }
            if (idComercio == 7) {
                formatoPago = StringUtils.formatoPagoMediostag(formatoPago);
            }
            textViewServ.setHintText(getString(R.string.details_compania));
        } else if (tipoTab == 2) {
            formatoPago = StringUtils.genericFormat(formatoPago, SPACE);
            textViewServ.setHintText(getString(R.string.details_compania));
        } else if (tipoTab == 3) {
            if (formatoPago.length() == 16 || formatoPago.length() == 15) {
                formatoPago = StringUtils.maskReference(StringUtils.format(formatoPago, SPACE, 4, 4, 4, 4), '*', formatoPago.length() - 12);
            } else {
                formatoPago = StringUtils.formatoPagoMedios(formatoPago);
            }
            textViewServ.setHintText(getString(R.string.details_bank));
        }

        textViewRef.setText(formatoPago);

        if (idTipoEnvio == 1) {
            linearTipo.setVisibility(View.VISIBLE);
            textViewTipo.setText(App.getContext().getResources().getString(R.string.transfer_phone_cellphone));
        } else if (idTipoEnvio == 2) {
            linearTipo.setVisibility(View.VISIBLE);
            textViewTipo.setText(App.getContext().getResources().getString(R.string.debit_card_number));
        }

        // Deshabilitamos la edicion de los CustomEditTExt para no modificarlos
        textViewServ.setFocusable(false);
        textViewRef.setFocusable(false);
        textViewTipo.setFocusable(false);

        errorAliasMessage.setVisibilityImageError(false);

        // Agregar el escuchardor DoneOnEditor para procesar el clic de teclas
        editTextAlias.addCustomEditorActionListener(new DoneOnEditorActionListener());
        if (nombreDest != null) {
            editTextAlias.setText(nombreDest);
        }

        // Iniciamos la funcionalidad e la camara
        cameraManager = new CameraManager();
        cameraManager.initCameraUploadDocument(this, imageViewCamera, this);


        // Agregamos Flecha de Shebrom
        textViewServ.setEnabled(false);
        textViewServ.setFullOnClickListener(this);
        textViewServ.setDrawableImage(R.drawable.menu_canvas);
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
    }

    // Disparamos el evento de Camara
    @OnClick(R.id.add_favorites_camera)
    public void openCamera() {
        favoritesPresenter.openMenuPhoto(1, cameraManager);
    }

    // Cerramos esta actividad de favoritos
    @OnClick(R.id.btnCloseAddFavorites)
    public void closeAddFavoritos() {
        finish();
    }

    //Disparamos la validacion, si es exitosa, entnces iniciamos el proceso de favoritos
    @OnClick(R.id.btnSendAddFavoritos)
    public void sendAddFavoritos() {
        // Toast.makeText(this, "Open Presenter", Toast.LENGTH_SHORT).show();
        validateForm();
    }

    /**
     * Error de algun tipo, ya sea de proceso de servidor o de conexion
     *
     * @param mMensaje
     */
    @Override
    public void toViewErrorServer(String mMensaje) {
        showDialogMesage(mMensaje, 0);
    }

    /**
     * Exito en agregar a Favoritos
     *
     * @param mResponse
     */
    @Override
    public void toViewSuccessAdd(FavoritosNewDatosResponse mResponse) {

    }

    @Override
    public void toViewSuccessAddFoto(String mMensaje) {
        showDialogMesage(mMensaje, 1);
    }

    @Override
    public void toViewSuccessAdd(FavoritosDatosResponse mResponse) {
        //  showDialogMesage(mMensaje, 1);
        int idFavorito = mResponse.getData().getIdFavorito();
        /**
         * Camino para enviar la foto al servicio
         */
        AddFotoFavoritesRequest addFotoFavoritesRequest =
                new AddFotoFavoritesRequest(stringFoto, "png");

        favoritesPresenter.toPresenterAddFotoFavorites(addFotoFavoritesRequest, idFavorito);
    }

    private void showDialogMesage(final String mensaje, final int closeAct) {
        UI.createSimpleCustomDialog("", mensaje, getSupportFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        if (closeAct == 1) {
                            /**
                             * Regresamos el exito como un OK a nuestra actividad anterior para
                             * ocultar el icono de agregar
                             */
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

    /**
     * Resultado de tomar una foto o escoger una de galeria, se envia el resultado al CameraManager
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraManager.setOnActivityResult(requestCode, resultCode, data);
    }

    /**
     * Resultado de procesar la imagen de la camara, aqui ya tenemos el Bitmap, y el codigo siguoente
     * es la BETA para poder darlo de alta en el servicio
     *
     * @param bitmap
     */
    @Override
    public void setPhotoToService(Bitmap bitmap) {
        // Log.d("TAG", "setPhotoToService ");

        imageViewCamera.setImageBitmap(bitmap);
        cameraManager.setBitmap(bitmap);

        // Procesamos el Bitmap a Base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        stringFoto = encoded;
        editFoto.setText(stringFoto);

        // Ocultamos el mensaje de error de foto
        editFotoError.setVisibilityImageError(false);
       /*
        cameraManager.setBitmap(bitmap);

        // Procesamos el Bitmap a Base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);


       CODIGO ORIGINAL DE FOTO EN ListaOpcionesFRagment
       // Creamos el objeto ActualizarAvatarRequest
        ActualizarAvatarRequest avatarRequest = new ActualizarAvatarRequest(encoded, "png");


        onEventListener.onEvent("DISABLE_BACK", true);

        // Enviamos al presenter
        mPreferPresenter.sendPresenterActualizarAvatar(avatarRequest);*/
    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;

        //Validate format Email
        if (!editTextAlias.isValidText()) {
            showValidationError(editTextAlias.getId(), getString(R.string.addFavoritesErrorAlias));
            editTextAlias.setIsInvalid();
            isValid = false;
            //return;
        }

        //Validate format Tipo Envio
        if (!editFoto.isValidText()) {
            showValidationError(editFoto.getId(), getString(R.string.addFavoritesErrorFoto));
            editFoto.setIsInvalid();
            isValid = false;
            //return;
        }

        //onValidationSuccess();
        if (isValid) {
            boolean isOnline = Utils.isDeviceOnline();
            if (isOnline) {
                onValidationSuccess();
            } else {
                showDialogMesage(getResources().getString(R.string.no_internet_access), 0);
            }
        }
    }

    @Override
    public void showValidationError(int id, Object error) {

        switch (id) {
            case R.id.add_favorites_alias:
                errorAliasMessage.setMessageText(error.toString());
                break;
            case R.id.add_favorites_foto_et:
                editFotoError.setMessageText(error.toString());
                break;
        }

        errorIsShowed = true;
    }

    @Override
    public void hideValidationError(int id) {
        switch (id) {
            case R.id.add_favorites_alias:
                errorAliasMessage.setVisibilityImageError(false);
                break;
            case R.id.add_favorites_foto_et:
                editFotoError.setVisibilityImageError(false);
                break;
        }
        //errorMessageView.setVisibilityImageError(false);
        errorIsShowed = false;
    }

    @Override
    public void onValidationSuccess() {
        errorAliasMessage.setVisibilityImageError(false);

        String mAlias = editTextAlias.getText();
        mReferencia = textViewRef.getText().toString();
        String referService = StringUtils.formatCardToService(mReferencia);

        AddFavoritesRequest addFavoritesRequest = new AddFavoritesRequest(idTipoComercio, idTipoEnvio,
                idComercio, mAlias, referService, "");

        favoritesPresenter.toPresenterAddFavorites(addFavoritesRequest);
    }

    @Override
    public void getDataForm() {

    }

    /**
     * Encargada de reaccionar al codigo de pusacion KEYCODE_CALL=5 para cerrar el teclado
     */
    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            // Toast.makeText(App.getContext(), "Click Code: " + actionId, Toast.LENGTH_SHORT).show();
            if (actionId == KeyEvent.KEYCODE_CALL) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextAlias.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                return true;

            }
            return false;
        }
    }


    @Override
    public void showProgress(String mMensaje) {
        //Log.d("TAG", "showProgress ");
    }

    @Override
    public void showExceptionToView(String mMesage) {
        //Log.d("TAG", "showExceptionToView ");
    }

    @Override
    public void sendSuccessAvatarToView(String mensaje) {
        //Log.d("TAG", "sendSuccessAvatarToView ");
    }

    @Override
    public void sendErrorAvatarToView(String mensaje) {
        //Log.d("TAG", "sendErrorAvatarToView ");
    }

    @Override
    public void setValidationRules() {

    }
}
