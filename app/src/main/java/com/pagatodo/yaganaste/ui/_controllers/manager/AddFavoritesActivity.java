package com.pagatodo.yaganaste.ui._controllers.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IAddFavoritesActivity;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesPresenter;
import com.pagatodo.yaganaste.ui.addfavorites.presenters.FavoritesPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_TIPO_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.NOMBRE_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.REFERENCIA;

public class AddFavoritesActivity extends LoaderActivity implements IAddFavoritesActivity,
        IListaOpcionesView, ValidationForms {

    @BindView(R.id.add_favorites_alias)
    CustomValidationEditText editTextAlias;
    @BindView(R.id.add_favorites_servicio)
    CustomValidationEditText textViewServ;
    @BindView(R.id.add_favorites_referencia)
    CustomValidationEditText textViewRef;
    @BindView(R.id.add_favorites_camera)
    UploadDocumentView imageViewCamera;
    @BindView(R.id.errorAliasMessage)
    ErrorMessage errorAliasMessage;
    IFavoritesPresenter favoritesPresenter;
    int idTipoComercio;
    int idComercio;
    String nombreComercio;
    String mReferencia;
    CameraManager cameraManager;
    private boolean errorIsShowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorites);

        favoritesPresenter = new FavoritesPresenter(this);

        Intent intent = getIntent();
        idComercio = intent.getIntExtra(ID_COMERCIO, 99);
        idTipoComercio = intent.getIntExtra(ID_TIPO_COMERCIO, 98);
        nombreComercio = intent.getStringExtra(NOMBRE_COMERCIO);
        mReferencia = intent.getStringExtra(REFERENCIA);

        ButterKnife.bind(this);
        imageViewCamera.setVisibilityStatus(false);
        textViewServ.setText(nombreComercio);
        textViewRef.setText(mReferencia);

        // Deshabilitamos la edicion de los CustomEditTExt
        textViewServ.setFocusable(false);
        textViewRef.setFocusable(false);

        errorAliasMessage.setVisibilityImageError(false);

        editTextAlias.addCustomEditorActionListener(new DoneOnEditorActionListener());

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

    @OnClick(R.id.add_favorites_camera)
    public void openCamera() {
        //Toast.makeText(this, "Open Camera", Toast.LENGTH_SHORT).show();
        boolean isOnline = Utils.isDeviceOnline();
        if(isOnline) {
            favoritesPresenter.openMenuPhoto(1, cameraManager);
        }else{
            showDialogMesage(getResources().getString(R.string.no_internet_access));
        }
    }

    @OnClick(R.id.btnCloseAddFavorites)
    public void closeAddFavoritos() {
        finish();
    }

    @OnClick(R.id.btnSendAddFavoritos)
    public void sendAddFavoritos() {
        boolean isOnline = Utils.isDeviceOnline();
        if(isOnline) {
            validateForm();
        } else {
            showDialogMesage(getResources().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void toViewResult() {
        Toast.makeText(this, "VMP Exitoso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toViewErrorServer(String mMensaje) {
        showDialogMesage(mMensaje);
    }

    @Override
    public void toViewSuccessAdd(String mMensaje) {
        showDialogMesage(mMensaje);
    }

    private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getSupportFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
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

        //onValidationSuccess();
        if (isValid) {
            onValidationSuccess();
        }
    }

    @Override
    public void showValidationError(int id, Object error) {

        switch (id) {
            case R.id.add_favorites_alias:
                errorAliasMessage.setMessageText(error.toString());
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
        }
        //errorMessageView.setVisibilityImageError(false);
        errorIsShowed = false;
    }

    @Override
    public void onValidationSuccess() {
        errorAliasMessage.setVisibilityImageError(false);
        String mAlias = editTextAlias.getText();
        AddFavoritesRequest addFavoritesRequest = new AddFavoritesRequest(idTipoComercio, idComercio,
                mAlias, mReferencia);
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
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextAlias.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                return true;

            }
            return false;
        }
    }
}
