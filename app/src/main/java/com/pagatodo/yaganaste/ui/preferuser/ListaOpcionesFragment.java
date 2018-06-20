package com.pagatodo.yaganaste.ui.preferuser;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioResponse;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.ui._controllers.CropActivity;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.ICropper;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.squareup.picasso.Picasso;
import com.steelkiwi.cropiwa.image.CropIwaResultReceiver;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_CLOSE;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_HELP_LEGAL;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_MY_ACCOUNT;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_MY_CARD;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_MY_DONGLE;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_MY_USER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.camera.CameraManager.CROP_RESULT;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaOpcionesFragment extends SupportFragment implements View.OnClickListener,
        IListaOpcionesView, ICropper, CropIwaResultReceiver.Listener {

    private static final String TAG = DocumentosFragment.class.getSimpleName();
    public static String IS_ES_AGENTE = "IS_ES_AGENTE";
    public static String USER_NAME = "USER_NAME";
    public static String USER_EMAIL = "USER_EMAIL";
    public static String USER_IMAGE = "USER_IMAGE";
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 101;
    PreferUserPresenter mPreferPresenter;
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fragment_list_opciones_name)
    TextView tv_name;

    //    @BindView(R.id.progressLayout)
//    ProgressLayout progressLayout;
    @BindView(R.id.fragment_list_opciones_email)
    TextView tv_email;
    @BindView(R.id.fragment_lista_opciones_user)
    LinearLayout ll_usuario;
    @BindView(R.id.fragment_lista_opciones_account)
    LinearLayout ll_cuenta;
    /*@BindView(R.id.fragment_lista_opciones_card)
    LinearLayout ll_card;*/
    /*@BindView(R.id.fragment_lista_opciones_dongle)
    LinearLayout ll_dongle;*/
    @BindView(R.id.fragment_lista_opciones_help_legal)
    LinearLayout ll_help_legal;
    @BindView(R.id.fragment_lista_opciones_close)
    LinearLayout ll_close;
    @BindView(R.id.frag_lista_opciones_photo_item)
    CircleImageView iv_photo_item;
    @BindView(R.id.frag_lista_opciones_photo_status)
    CircleImageView iv_photo_item_status;
    @BindView(R.id.fragment_my_account_config_notify)
    LinearLayout config_notify;

    View rootview;
    CameraManager cameraManager;
    private boolean isEsAgente;
    private String mName, mEmail, mUserImage;
    private CropIwaResultReceiver cropResultReceiver;

    public ListaOpcionesFragment() {
        // Required empty public constructor
    }

    public static ListaOpcionesFragment newInstance(boolean isEsAgente, String mName, String mEmail, String mUserImage) {

        ListaOpcionesFragment fragmentRegister = new ListaOpcionesFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_ES_AGENTE, isEsAgente);
        args.putString(USER_NAME, mName);
        args.putString(USER_EMAIL, mEmail);
        args.putString(USER_IMAGE, mUserImage);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        mPreferPresenter = ((PreferUserActivity) getActivity()).getPreferPresenter();
        mPreferPresenter.setIView(this);
        showBack(true);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        isEsAgente = getArguments().getBoolean(IS_ES_AGENTE);
        mName = getArguments().getString(USER_NAME);
        mEmail = getArguments().getString(USER_EMAIL);
        mUserImage = getArguments().getString(USER_IMAGE);

        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_lista_opciones, container, false);

        initViews();

        cameraManager = new CameraManager(this);
        cameraManager.initCamera(getActivity(), iv_photo_item, this);

        cropResultReceiver = new CropIwaResultReceiver();
        cropResultReceiver.setListener(this);
        cropResultReceiver.register(getContext());

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        config_notify.setOnClickListener(this);
        ll_usuario.setOnClickListener(this);

        ll_cuenta.setOnClickListener(this);
        //ll_card.setOnClickListener(this);
        ll_help_legal.setOnClickListener(this);
        ll_close.setOnClickListener(this);
        iv_photo_item.setOnClickListener(this);

        // Hacemos SET de la infromacion del user
        // mName = "Mi Nombre";
        // mEmail = "mimail@micorreo.com";

        ClienteResponse userData = SingletonUser.getInstance().getDataUser().getCliente();

        String nombreprimerUser;
        String apellidoMostrarUser;
        if (userData.getPrimerApellido().isEmpty()) {
            apellidoMostrarUser = userData.getSegundoApellido();
        } else {
            apellidoMostrarUser = userData.getPrimerApellido();
        }
        nombreprimerUser = StringUtils.getFirstName(userData.getNombre());
        if (nombreprimerUser.isEmpty()) {
            nombreprimerUser = userData.getNombre();
        }

        //tv_name.setText(mName);
        tv_name.setText(nombreprimerUser + " " + apellidoMostrarUser);
        tv_email.setText(mEmail);
        // Hacemos Set de la version de codigo
        iv_photo_item_status.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.camara_white_blue_canvas));
        // updatePhoto();
    }

    private void updatePhoto() {

        // Mostramos la imagen del usuario o la pedimos al servicio en caso de que no exista
        if (mUserImage != null && !mUserImage.isEmpty()) {
            Picasso.with(getContext())
                    .load(mUserImage)
                    .placeholder(R.mipmap.icon_user).error(R.mipmap.icon_user)
                    .into(iv_photo_item);
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
        showDialogMesage(getString(R.string.msg_format_image_warning));
    }

    @Override
    public void onCropSuccess(Uri croppedUri) {
        cameraManager.setCropImage(croppedUri);
    }

    @Override
    public void onCropFailed(Throwable e) {
        e.printStackTrace();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_lista_opciones_user:
                //Toast.makeText(getContext(), "Click User", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_MY_USER, 1);
                break;
            case R.id.fragment_lista_opciones_account:
                //Toast.makeText(getContext(), "Click Cuenta", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_MY_ACCOUNT, 1);
                break;
            case R.id.fragment_lista_opciones_card:
                //Toast.makeText(getContext(), "Click Card", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_MY_CARD, 1);
                break;
            case R.id.fragment_lista_opciones_help_legal:
                onEventListener.onEvent(PREFER_USER_HELP_LEGAL, 1);
                // Toast.makeText(getContext(), "Click Help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_lista_opciones_dongle:
                onEventListener.onEvent(PREFER_USER_MY_DONGLE, 1);
                // Toast.makeText(getContext(), "Click Help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_lista_opciones_close:
                boolean isOnline2 = Utils.isDeviceOnline();
                if (isOnline2) {
                    onEventListener.onEvent(PREFER_USER_CLOSE, 1);
                } else {
                    showDialogMesage(getResources().getString(R.string.no_internet_access));
                }
                break;
            /**
             * Evento para Click de camara
             */
            case R.id.frag_lista_opciones_photo_item:
                boolean isOnline = Utils.isDeviceOnline();
                if (isOnline) {
                    boolean isValid = true;

                    int permissionCamera = ContextCompat.checkSelfPermission(App.getContext(),
                            Manifest.permission.CAMERA);
                    int permissionStorage = ContextCompat.checkSelfPermission(App.getContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                    // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
                    if (permissionCamera == -1) {
                        ValidatePermissions.checkPermissions(getActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                        isValid = false;
                    }

                    // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
                    if (permissionStorage == -1) {
                        ValidatePermissions.checkPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_STORAGE);
                        isValid = false;
                    }

                    if (isValid) {
                        //mPreferPresenter.openMenuPhoto(1, cameraManager);
                    }

                } else {
                    showDialogMesage(getResources().getString(R.string.no_internet_access));
                }

                break;
        }
    }

    /**
     * Metodo que entrega el BitMap para enviar al servicio
     *
     * @param bitmap
     */
    @Override
    public void setPhotoToService(Bitmap bitmap) {
        // Guardamos en bruto nuestro Bitmap.
        cameraManager.setBitmap(bitmap);

        // Procesamos el Bitmap a Base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // Creamos el objeto ActualizarAvatarRequest
        ActualizarAvatarRequest avatarRequest = new ActualizarAvatarRequest(encoded, "png");


        onEventListener.onEvent("DISABLE_BACK", true);

        // Enviamos al presenter
        //mPreferPresenter.sendPresenterActualizarAvatar(avatarRequest);


    }

    @Override
    public void showProgress(String mMensaje) {
        onEventListener.onEvent(EVENT_SHOW_LOADER,
                getString(R.string.listaopciones_load_image_wait));
    }


    @Override
    public void showExceptionToView(String mMesage) {
        showDialogMesage(mMesage);
    }

    @Override
    public void sendSuccessAvatarToView(String mMesage) {
        showDialogMesage(getString(R.string.change_avatar_success));
        mUserImage = SingletonUser.getInstance().getDataUser().getUsuario().getImagenAvatarURL();
        updatePhoto();
        CameraManager.cleanBitmap();
        hideLoader();
        onEventListener.onEvent("DISABLE_BACK", false);

    }

    @Override
    public void sendErrorAvatarToView(String mensaje) {
        hideLoader();
        onEventListener.onEvent("DISABLE_BACK", false);
        CameraManager.cleanBitmap();
        showDialogMesage(mensaje);
    }

    public void hideLoader() {
        // progressLayout.setVisibility(GONE);
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
    }

    /*private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }*/

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void errorSessionExpired(DataSourceResult dataSourceResult) {
        super.errorSessionExpired(dataSourceResult);
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePhoto();
    }

    @Override
    public void setLogOutSession() {

    }

}

