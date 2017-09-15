package com.pagatodo.yaganaste.ui._controllers.manager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IAddFavoritesActivity;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesPresenter;
import com.pagatodo.yaganaste.ui.addfavorites.presenters.FavoritesPresenter;
import com.pagatodo.yaganaste.ui.preferuser.ListaOpcionesFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.utils.BitmapBase64Listener;
import com.pagatodo.yaganaste.utils.BitmapLoader;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.yaganaste.App.getContext;

public class AddFavoritesActivity extends LoaderActivity implements IAddFavoritesActivity,
        IListaOpcionesView {

    @BindView(R.id.add_favorites_alias)
    CustomValidationEditText textViewAlias;
    @BindView(R.id.add_favorites_servicio)
    CustomValidationEditText textViewServ;
    @BindView(R.id.add_favorites_referencia)
    CustomValidationEditText textViewRef;
    @BindView(R.id.add_favorites_camera)
    UploadDocumentView imageViewCamera;
    IFavoritesPresenter favoritesPresenter;
    int idTipoComercio;
    int idComercio;
    String nombreComercio;
    String mReferencia;
    CameraManager cameraManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorites);

        favoritesPresenter = new FavoritesPresenter(this);

        Intent intent = getIntent();
        idComercio = intent.getIntExtra("idComercio", 99);
        idTipoComercio = intent.getIntExtra("idTipoComercio", 98);
        nombreComercio = intent.getStringExtra("nombreComercio");
        mReferencia = intent.getStringExtra("referencia");

        ButterKnife.bind(this);
        imageViewCamera.setVisibilityStatus(false);
        textViewServ.setText(intent.getStringExtra("nombreComercio"));
        textViewRef.setText(intent.getStringExtra("referencia") + " " + idComercio + " " + idTipoComercio);

        // Deshabilitamos la edicion de los CustomEditTExt
        textViewServ.setFocusable(false);
        textViewRef.setFocusable(false);

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
        // Toast.makeText(this, "Open Presenter", Toast.LENGTH_SHORT).show();

        boolean isOnline = Utils.isDeviceOnline();
        if(isOnline) {
            String mAlias = textViewAlias.getText().toString();
            AddFavoritesRequest addFavoritesRequest = new AddFavoritesRequest(idTipoComercio, idComercio,
                    mAlias, mReferencia);

            favoritesPresenter.toPresenterAddFavorites(addFavoritesRequest);
        }else{
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
        Log.d("TAG", "showProgress ");
    }

    @Override
    public void showExceptionToView(String mMesage) {
        Log.d("TAG", "showExceptionToView ");
    }

    @Override
    public void sendSuccessAvatarToView(String mensaje) {
        Log.d("TAG", "sendSuccessAvatarToView ");
    }

    @Override
    public void sendErrorAvatarToView(String mensaje) {
        Log.d("TAG", "sendErrorAvatarToView ");
    }
}
