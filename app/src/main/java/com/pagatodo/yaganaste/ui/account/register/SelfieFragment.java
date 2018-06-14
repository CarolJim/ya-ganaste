package com.pagatodo.yaganaste.ui.account.register;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.airbnb.lottie.LottieAnimationView;
import com.pagatodo.yaganaste.App;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.CropActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.ICropper;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;
import com.steelkiwi.cropiwa.image.CropIwaResultReceiver;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_ADDRESS_DATA;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_SELFIE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.camera.CameraManager.CROP_RESULT;

/**
 * Created by Armando Sandoval on 22/02/2018.
 */

public class SelfieFragment extends GenericFragment implements View.OnClickListener, ICropper, CropIwaResultReceiver.Listener, IListaOpcionesView, INavigationView {

    private View rootview;
    CameraManager cameraManager;
    private CropIwaResultReceiver cropResultReceiver;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 101;

    private AccountPresenterNew accountPresenter;
    private String mUserImage;
    @BindView(R.id.btntomarfoto)
    StyleButton btntomarfoto;

    @BindView(R.id.btnseleccionarfoto)
    StyleButton btnSeleccionarfoto;

    @BindView(R.id.btnreintentar)
    StyleButton btnreintentar;

    @BindView(R.id.btnconfirmar)
    StyleButton btnconfirmar;
    @BindView(R.id.frag_lista_opciones_photo_item)
    CircleImageView iv_photo_item;

    @BindView(R.id.layoutBottom)
    LinearLayout layoutBottom;
    @BindView(R.id.layoutBottomconfirm)
    LinearLayout layoutBottomconfirm;

    @BindView(R.id.imgusuario)
    LinearLayout imgusuario;

    @BindView(R.id.scv_img_usuario)
    ScrollView scvImgUsuario;

    @BindView(R.id.tituloselfie)
    StyleTextView tituloselfie;

    @BindView(R.id.anim_selfie)
    LottieAnimationView animSelfie;
    Uri fotoperfil;
    StyleTextView omitir;

    boolean foto = false;
    boolean img = false;

    public static SelfieFragment newInstance() {
        SelfieFragment fragmentRegister = new SelfieFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        accountPresenter = (((AccountActivity) getActivity()).getPresenter());
        rootview = inflater.inflate(R.layout.fragment_selfie, container, false);
        cameraManager = new CameraManager(this);
        cameraManager.initCamera(getActivity(), iv_photo_item, this);
        omitir = (StyleTextView) getActivity().findViewById(R.id.omitir);

        omitir.setVisibility(View.VISIBLE);
        omitir.setOnClickListener(this);
        cropResultReceiver = new CropIwaResultReceiver();
        cropResultReceiver.setListener(this);
        cropResultReceiver.register(getContext());


        initViews();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootview;
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btntomarfoto.setOnClickListener(this);
        btnSeleccionarfoto.setOnClickListener(this);
        btnreintentar.setOnClickListener(this);
        btnconfirmar.setOnClickListener(this);
        tituloselfie.setText(getString(R.string.tituloselfie));


        /**
         * Parte para poner la animacion
         */
        RegisterUser registerUser = RegisterUser.getInstance();

        if (registerUser.getGenero().equals("H")) {
            animSelfie.setAnimation(R.raw.selfie_male);
        } else if (registerUser.getGenero().equals("M")) {
            animSelfie.setAnimation(R.raw.selfie_female);
        }
        animSelfie.playAnimation();

    }

    @Override
    public void onResume() {
        super.onResume();
        cropResultReceiver.setListener(this);
        cropResultReceiver.register(getContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btntomarfoto:
                foto = true;
                solicitarpermisos();
                break;
            case R.id.btnseleccionarfoto:

                img = true;
                solicitarpermisos();
                break;

            case R.id.btnreintentar:
                layoutBottom.setVisibility(View.VISIBLE);
                layoutBottomconfirm.setVisibility(View.GONE);
                tituloselfie.setText(getString(R.string.tituloselfie));
                imgusuario.setVisibility(View.GONE);
                scvImgUsuario.setVisibility(View.GONE);
                animSelfie.setVisibility(View.VISIBLE);
                animSelfie.playAnimation();
                break;
            case R.id.btnconfirmar:
                RegisterUser registerUser = RegisterUser.getInstance();
                registerUser.setUrifotoperfil(mUserImage);
                registerUser.setPerfirlfoto(fotoperfil);
                nextScreen(EVENT_ADDRESS_DATA, null);//Mostramos siguiente pantalla de registro.
                omitir.setVisibility(View.GONE);
                break;
            case R.id.omitir:
                nextScreen(EVENT_ADDRESS_DATA, null);//Mostramos siguiente pantalla de regis
                omitir.setVisibility(View.GONE);
                mUserImage = "";
                break;
            default:
                break;
        }

    }


    private void updatePhoto() {
        // Mostramos la imagen del usuario o la pedimos al servicio en caso de que no exista
        if (mUserImage != null && !mUserImage.isEmpty()) {
            Picasso.with(getActivity())
                    .load(mUserImage)
                    .placeholder(R.mipmap.icon_user).error(R.mipmap.icon_user)
                    .into(iv_photo_item);

        }

    }

    private void solicitarpermisos() {
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            boolean isValid = true;

            int permissionCamera = ContextCompat.checkSelfPermission(App.getContext(),
                    android.Manifest.permission.CAMERA);
            int permissionStorage = ContextCompat.checkSelfPermission(App.getContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE);

            // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
            if (permissionCamera == -1) {
                ValidatePermissions.checkPermissions(getActivity(),
                        new String[]{android.Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
                isValid = false;
            }

            // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
            if (permissionStorage == -1) {
                ValidatePermissions.checkPermissions(getActivity(),
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
                isValid = false;
            }

            if (isValid) {
                //  mPreferPresenter.openMenuPhoto(1, cameraManager);

                if (foto) {
                    accountPresenter.Photo(1, cameraManager);
                } else {
                    accountPresenter.picture(1, cameraManager);

                }
                foto = false;
                img = false;

            }

        } else {
            //  showDialogMesage(getResources().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onCropper(Uri uri) {
        showProgress("");
        startActivityForResult(CropActivity.callingIntent(getContext(), uri), CROP_RESULT);
    }

    @Override
    public void onHideProgress() {
        hideLoader();
    }


    @Override
    public void failLoadJpg() {
        // showDialogMesage(getString(R.string.msg_format_image_warning));

    }

    @Override
    public void onCropSuccess(Uri croppedUri) {

        mUserImage = croppedUri.toString();
        fotoperfil = croppedUri;
        updatePhoto();
        layoutBottom.setVisibility(View.GONE);
        layoutBottomconfirm.setVisibility(View.VISIBLE);
        tituloselfie.setText(getString(R.string.tituloselfietegusta));
        imgusuario.setVisibility(View.VISIBLE);
        scvImgUsuario.setVisibility(View.VISIBLE);
        animSelfie.setVisibility(View.GONE);
        animSelfie.pauseAnimation();
        hideLoader();


        // cameraManager.setCropImage(croppedUri);

    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void showLoader(String message) {

    }

    public void hideLoader() {
        // progressLayout.setVisibility(GONE);
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
    }

    @Override
    public void showError(Object error) {

    }

    @Override
    public void onCropFailed(Throwable e) {

    }

    @Override
    public void setPhotoToService(Bitmap bitmap) {


    }

    @Override
    public void showProgress(String mMensaje) {
        onEventListener.onEvent(EVENT_SHOW_LOADER,
                getString(R.string.listaopciones_load_image_wait));

    }

    @Override
    public void showExceptionToView(String mMesage) {

    }

    @Override
    public void sendSuccessAvatarToView(String mensaje) {

    }

    @Override
    public void sendErrorAvatarToView(String mensaje) {

    }

    @Override
    public void setLogOutSession() {

    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {
        omitir.setVisibility(View.GONE);
        mUserImage = "";
    }
}
