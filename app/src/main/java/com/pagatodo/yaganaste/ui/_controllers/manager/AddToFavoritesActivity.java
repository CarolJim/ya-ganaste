package com.pagatodo.yaganaste.ui._controllers.manager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosEditDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosNewDatosResponse;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.interfaces.ITextChangeListener;
import com.pagatodo.yaganaste.interfaces.OnListServiceListener;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IAddFavoritesActivity;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.otp.controllers.OtpView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.ICropper;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;
import com.pagatodo.yaganaste.utils.customviews.carousel.CustomCarouselItem;
import com.steelkiwi.cropiwa.image.CropIwaResultReceiver;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.TIPO_TAB;

public class AddToFavoritesActivity extends LoaderActivity implements IAddFavoritesActivity,
        IListaOpcionesView, ValidationForms, View.OnClickListener, OnListServiceListener,
        AdapterView.OnItemSelectedListener, ITextChangeListener, PaymentsCarrouselManager,
        ICropper, CropIwaResultReceiver.Listener ,OtpView {

    public static final String FAV_PROCESS = "FAV_PROCESS";
    public static final String TIPO_TAB = "TIPO_TAB";

    private int favoriteProcess;
    int tipoTab; // tipoTab current_tab

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_favorites);
        ButterKnife.bind(this);

        /**
         Identificar:
         - Cuando es un agregar Favoritos desde Pago exitoso
         - Cuando es un agregar Favoritos desde Cero
         - Cuando es un Editar Favorito
         */
        Intent intent = getIntent();
        favoriteProcess =  getIntent().getIntExtra(FAV_PROCESS, 1);
        tipoTab = intent.getIntExtra(TIPO_TAB, 96);

        Toast.makeText(this, "favoriteProcess " + favoriteProcess +
                " tipoTab " + tipoTab, Toast.LENGTH_SHORT).show();

        /**
         * Mostramos las vistas iniciales o las ocultamos
         */

        if(favoriteProcess == 1){

        }else if(favoriteProcess == 1){

        }
    }

    /**
     * EVENTOS OnClick de Butter Knife o Listener
     */

    // Disparamos el evento de Camara solo si tenemos intrnet
    @OnClick(R.id.add_favorites_camera)
    public void openCamera() {
        Toast.makeText(this, "Click Camera", Toast.LENGTH_SHORT).show();
    //    favoritesPresenter.openMenuPhoto(1, cameraManager);

    }


    /**
     MEtodos de INTERFASES O PRESENTERS
     */

    @Override
    public boolean requiresTimer() {
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onTextChanged() {

    }

    @Override
    public void onTextComplete() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess(Double importe) {

    }

    @Override
    public void onOtpGenerated(String otp) {

    }

    @Override
    public void showError(Errors error) {

    }

    @Override
    public void setPhotoToService(Bitmap bitmap) {

    }

    @Override
    public void showProgress(String mMensaje) {

    }

    @Override
    public void sendSuccessAvatarToView(String mensaje) {

    }

    @Override
    public void sendErrorAvatarToView(String mensaje) {

    }

    @Override
    public void onListServiceListener(CustomCarouselItem item) {

    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {

    }

    @Override
    public void showErrorService() {

    }

    @Override
    public void showFavorites() {

    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {

    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {

    }

    @Override
    public void getDataForm() {

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

    @Override
    public void toViewErrorServer(String mMensaje) {

    }

    @Override
    public void toViewSuccessAdd(FavoritosNewDatosResponse mensaje) {

    }

    @Override
    public void showExceptionToView(String s) {

    }

    @Override
    public void toViewSuccessAddFoto(String mensaje) {

    }

    @Override
    public void toViewSuccessAdd(FavoritosDatosResponse response) {

    }

    @Override
    public void toViewSuccessDeleteFavorite(String mensaje) {

    }

    @Override
    public void toViewSuccessEdit(FavoritosEditDatosResponse response) {

    }

    @Override
    public void onCropSuccess(Uri croppedUri) {

    }

    @Override
    public void onCropFailed(Throwable e) {

    }
}
