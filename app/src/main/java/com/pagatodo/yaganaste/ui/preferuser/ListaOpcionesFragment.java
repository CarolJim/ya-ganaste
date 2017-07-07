package com.pagatodo.yaganaste.ui.preferuser;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.adquirente.Documentos;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_CLOSE;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_DESASOCIAR;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_LEGALES;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_MY_USER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity.EVENT_SESSION_EXPIRED;
import static com.pagatodo.yaganaste.utils.Constants.MESSAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaOpcionesFragment extends GenericFragment implements View.OnClickListener,
        IListaOpcionesView {

    public static String IS_ES_AGENTE = "IS_ES_AGENTE";
    public static String USER_NAME = "USER_NAME";
    public static String USER_EMAIL = "USER_EMAIL";
    public static String USER_IMAGE = "USER_IMAGE";
    private boolean isEsAgente;
    private String mName, mEmail, mUserImage;
    PreferUserPresenter mPreferPresenter;

    private static final String TAG = Documentos.class.getSimpleName();

//    @BindView(R.id.progressLayout)
//    ProgressLayout progressLayout;

    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.fragment_list_opciones_name)
    TextView tv_name;
    @BindView(R.id.fragment_list_opciones_email)
    TextView tv_email;
    @BindView(R.id.fragment_lista_opciones_user)
    LinearLayout ll_usuario;
    @BindView(R.id.fragment_lista_opciones_account)
    LinearLayout ll_cuenta;
    @BindView(R.id.fragment_lista_opciones_card)
    LinearLayout ll_card;
    @BindView(R.id.fragment_lista_opciones_help)
    LinearLayout ll_help;
    @BindView(R.id.fragment_lista_opciones_legal)
    LinearLayout ll_legal;
    @BindView(R.id.fragment_lista_opciones_close)
    LinearLayout ll_close;
    @BindView(R.id.fragment_lista_opciones_desasociar)
    LinearLayout ll_desasociar;
    @BindView(R.id.frag_lista_opciones_photo_item)
    CircleImageView iv_photo_item;
    @BindView(R.id.frag_lista_opciones_photo_status)
    CircleImageView iv_photo_item_status;
    @BindView(R.id.fragment_lista_opciones_version)
    TextView tv_version_code;
    @BindView(R.id.testIV)
    ImageView testIV;

    View rootview;
    CameraManager cameraManager;

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

        cameraManager = new CameraManager();
        cameraManager.initCamera(getActivity(), iv_photo_item, this);

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        ll_usuario.setOnClickListener(this);

        if (isEsAgente) {
            ll_cuenta.setVisibility(View.VISIBLE);
            View view_cuenta = rootview.findViewById(R.id.content_prefer_view_acount);
            view_cuenta.setVisibility(View.VISIBLE);

            ll_cuenta.setOnClickListener(this);
        }

        ll_card.setOnClickListener(this);
        ll_help.setOnClickListener(this);
        ll_legal.setOnClickListener(this);
        ll_close.setOnClickListener(this);
        iv_photo_item.setOnClickListener(this);
        ll_desasociar.setOnClickListener(this);

        // Hacemos SET de la infromacion del user
        // mName = "Mi Nombre";
        // mEmail = "mimail@micorreo.com";
        tv_name.setText(mName);
        tv_email.setText(mEmail);

        // Hacemos Set de la version de codigo
        tv_version_code.setText("YaGanaste Versión: ".concat(String.valueOf(BuildConfig.VERSION_NAME)));

        iv_photo_item_status.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.camara_white_blue_canvas));

        /**
         * Mostramos la imagen del usuario o la pedimos al servicio en caso de que no exista
         */
        if (mUserImage != null && !mUserImage.isEmpty()) {
            try {
                // Pedimos la imagen por internet y generamos el Bitmap
                mPreferPresenter.getImagenURLToPresenter(mUserImage);
            } catch (Exception e) {
                // Hacemos algo si falla por no tener internet
                showDialogMesage(e.toString());
            }

        } else {
            // iv_photo_item.setStatusImage(getResources().getDrawable(R.drawable.add_photo_canvas, null));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_lista_opciones_user:
                //Toast.makeText(getContext(), "Click User", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_MY_USER, 1);
                break;
            case R.id.fragment_lista_opciones_account:
                Toast.makeText(getContext(), "Click Cuenta", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_lista_opciones_card:
                Toast.makeText(getContext(), "Click Card", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_lista_opciones_help:
                Toast.makeText(getContext(), "Click Help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_lista_opciones_legal:
                onEventListener.onEvent(PREFER_USER_LEGALES, 1);
                break;
            case R.id.fragment_lista_opciones_desasociar:
                onEventListener.onEvent(PREFER_USER_DESASOCIAR, 1);
                break;
            case R.id.fragment_lista_opciones_close:
                onEventListener.onEvent(PREFER_USER_CLOSE, 1);
                break;

            /**
             * Evento para Click de camara
             */
            case R.id.frag_lista_opciones_photo_item:
                mPreferPresenter.openMenuPhoto(1, cameraManager);
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

        // Deshabilitamos el backButton
        //getActivity().onBackPressed();
        onEventListener.onEvent("DISABLE_BACK", true);

        // Enviamos al presenter
        mPreferPresenter.sendPresenterActualizarAvatar(avatarRequest);


    }

    @Override
    public void showProgress(String mMensaje) {
        onEventListener.onEvent(EVENT_SHOW_LOADER,
                getString(R.string.listaopciones_load_image_wait));
    }

    /**
     * Hacemos Set de la imagen que viene del servidor en la vista final
     *
     * @param bitmap
     */
    @Override
    public void sendImageBitmapToView(Bitmap bitmap) {
        iv_photo_item.setImageBitmap(bitmap);
    }

    @Override
    public void showExceptionToView(String mMesage) {
        showDialogMesage(mMesage);
    }

    @Override
    public void sendSuccessAvatarToView(String mMesage) {
         /* Glide.with(this).load(SingletonUser.getInstance().getDataUser().getUsuario().getImagenAvatarURL())
                .placeholder(R.mipmap.ic_background_pago).error(R.mipmap.ic_background_pago).into(testIV);*/
        showDialogMesage(mMesage);
        iv_photo_item.setImageBitmap(CameraManager.getBitmap());
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

    private void showDialogMesage(final String mensaje) {
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
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void errorSessionExpired(DataSourceResult dataSourceResult) {
        super.errorSessionExpired(dataSourceResult);
    }
}
