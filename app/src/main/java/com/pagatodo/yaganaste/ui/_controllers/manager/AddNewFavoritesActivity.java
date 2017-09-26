package com.pagatodo.yaganaste.ui._controllers.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnListServiceListener;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.States;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IAddFavoritesActivity;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesPresenter;
import com.pagatodo.yaganaste.ui.addfavorites.presenters.FavoritesPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.ListServDialogFragment;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;
import com.pagatodo.yaganaste.utils.customviews.carousel.CustomCarouselItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_TIPO_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_TIPO_ENVIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.NOMBRE_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.REFERENCIA;

/**
 * Encargada de dar de alta Favoritos, pero capturando todos sus datos. primero en el servicio y luego en la base local
 */
public class AddNewFavoritesActivity extends LoaderActivity implements IAddFavoritesActivity,
        IListaOpcionesView, ValidationForms, View.OnClickListener, OnListServiceListener {

    @BindView(R.id.add_favorites_alias)
    CustomValidationEditText editAlias;
    @BindView(R.id.add_favorites_alias_error)
    ErrorMessage editAliasError;
    @BindView(R.id.add_favorites_list_serv)
    CustomValidationEditText editListServ;
    @BindView(R.id.add_favorites_list_serv_error)
    ErrorMessage editListServError;
    @BindView(R.id.add_favorites_linear_tipo)
    LinearLayout linearTipo;
    @BindView(R.id.add_favorites_tipo)
    CustomValidationEditText editTipo;
    @BindView(R.id.add_favorites_tipo_error)
    ErrorMessage editTipoError;
    @BindView(R.id.add_favorites_referencia)
    CustomValidationEditText editRefer;
    @BindView(R.id.add_favorites_referencia_error)
    ErrorMessage editReferError;
    @BindView(R.id.add_favorites_camera)
    UploadDocumentView imageViewCamera;

    IFavoritesPresenter favoritesPresenter;
    int idTipoComercio;
    int idComercio;
    int idTipoEnvio;
    String nombreComercio;
    String mReferencia;
    CameraManager cameraManager;
    private boolean errorIsShowed = false;
    ArrayList<CustomCarouselItem> backUpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_favorites);

        favoritesPresenter = new FavoritesPresenter(this);

        Intent intent = getIntent();
        int current_tab = intent.getIntExtra("currentTab", 99);
        backUpResponse = intent.getExtras().getParcelableArrayList("backUpResponse");

        ButterKnife.bind(this);
        imageViewCamera.setVisibilityStatus(false);

        /*textViewServ.setText(nombreComercio);
        textViewRef.setText(mReferencia);
        if (idTipoEnvio == 1) {
            linearTipo.setVisibility(View.VISIBLE);
            textViewTipo.setText(App.getContext().getResources().getString(R.string.transfer_phone_cellphone));
        } else if (idTipoEnvio == 2) {
            linearTipo.setVisibility(View.VISIBLE);
            textViewTipo.setText(App.getContext().getResources().getString(R.string.debit_card_number));
        }
        errorAliasMessage.setVisibilityImageError(false);

        // Agregar el escuchardor DoneOnEditor para procesar el clic de teclas
        editTextAlias.addCustomEditorActionListener(new DoneOnEditorActionListener());*/



        // Funcionalidad para agregar el Spinner
        editListServ.imageViewIsGone(false);
        editListServ.setEnabled(false);
        editListServ.setFullOnClickListener(this);

        // Iniciamos la funcionalidad e la camara
        cameraManager = new CameraManager();
        cameraManager.initCameraUploadDocument(this, imageViewCamera, this);
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
        //Toast.makeText(this, "Open Camera", Toast.LENGTH_SHORT).show();
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            favoritesPresenter.openMenuPhoto(1, cameraManager);
        } else {
            showDialogMesage(getResources().getString(R.string.no_internet_access), 0);
        }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_favorites_list_serv:
                /**
                 * 1 - Creamos nuestro Dialog Fragment Custom que mostrar la lista de Servicios
                 * 2 - HAcemos SET de la interfase OnListServiceListener, con e metodo setOnList...
                 * asi al hacer clic en algun elemento nos dara la respuesta
                 * 3 - Mostramos el dialogo
                 */
                ListServDialogFragment dialogFragment = ListServDialogFragment.newInstance(backUpResponse);
                dialogFragment.setOnListServiceListener(this);
                dialogFragment.show(getSupportFragmentManager(), "FragmentDialog");
                break;
            default:
                break;
        }
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
     * @param mMensaje
     */
    @Override
    public void toViewSuccessAdd(String mMensaje) {
        showDialogMesage(mMensaje, 1);
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
        /*getDataForm();
        boolean isValid = true;

        //Validate format Email
        if (!editTextAlias.isValidText()) {
            showValidationError(editTextAlias.getId(), getString(R.string.addFavoritesErrorAlias));
            editTextAlias.setIsInvalid();
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
        }*/
    }

    @Override
    public void showValidationError(int id, Object error) {

        switch (id) {
            case R.id.add_favorites_alias:
              //  errorAliasMessage.setMessageText(error.toString());
                break;
        }

        errorIsShowed = true;
    }

    @Override
    public void hideValidationError(int id) {
        switch (id) {
            case R.id.add_favorites_alias:
              //  errorAliasMessage.setVisibilityImageError(false);
                break;
        }
        //errorMessageView.setVisibilityImageError(false);
        errorIsShowed = false;
    }

    @Override
    public void onValidationSuccess() {
       /* errorAliasMessage.setVisibilityImageError(false);
        String mAlias = editTextAlias.getText().toString();
        AddFavoritesRequest addFavoritesRequest = new AddFavoritesRequest(idTipoComercio, idTipoEnvio,
                idComercio, mAlias, mReferencia);

        favoritesPresenter.toPresenterAddFavorites(addFavoritesRequest);*/
    }

    @Override
    public void getDataForm() {

    }

    /**
     * Encargada de reaccionar al codigo de pusacion KEYCODE_CALL=5 para cerrar el teclado
     *//*
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
    }*/


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

    @Override
    public void onListServiceListener(CustomCarouselItem item) {
        Toast.makeText(this, "Item " + item.getNombreComercio(), Toast.LENGTH_SHORT).show();
    }
}
