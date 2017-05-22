package com.pagatodo.yaganaste.ui.preferuser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountAdqPresenter;
import com.pagatodo.yaganaste.ui.adquirente.Documentos;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.ListaOpcionesPresenter;
import com.pagatodo.yaganaste.utils.BitmapBase64Listener;
import com.pagatodo.yaganaste.utils.BitmapLoader;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.R.id.progressLayout;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_CLOSE;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_LEGALES;
import static com.pagatodo.yaganaste.ui.adquirente.Documentos.checkDuplicate;
import static com.pagatodo.yaganaste.ui.adquirente.TransactionResultFragment.KEY_PAGE_RESULT;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_DOM_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_DOM_FRONT;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_FRONT;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaOpcionesFragment extends GenericFragment implements View.OnClickListener,
        IListaOpcionesView {

    public static String IS_ES_AGENTE = "IS_ES_AGENTE";
    public static String USER_NAME = "USER_NAME";
    public static String USER_EMAIL = "USER_EMAIL";
    private boolean isEsAgente;
    private String mName, mEmail;

    private static final String TAG = Documentos.class.getSimpleName();

//    @BindView(R.id.progressLayout)
//    ProgressLayout progressLayout;

    SwipeRefreshLayout swipeRefreshLayout;
    IListaOpcionesPresenter mPresenter;
    private ArrayList<String> contador ;
    private ArrayList<DataDocuments> dataDocumnets;
    private Drawable mDrawable = null;

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
    @BindView(R.id.frag_lista_opciones_photo_item)
    UploadDocumentView iv_photo_item;
    @BindView(R.id.fragment_lista_opciones_version)
    TextView tv_version_code;

    View rootview;

    public ListaOpcionesFragment() {
        // Required empty public constructor
    }

    public static ListaOpcionesFragment newInstance(boolean isEsAgente, String mName, String mEmail) {

        ListaOpcionesFragment fragmentRegister = new ListaOpcionesFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_ES_AGENTE, isEsAgente);
        args.putString(USER_NAME, mName);
        args.putString(USER_EMAIL, mEmail);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        isEsAgente = getArguments().getBoolean(IS_ES_AGENTE);
        mName = getArguments().getString(USER_NAME);
        mEmail = getArguments().getString(USER_EMAIL);

        contador = new ArrayList<>();
        dataDocumnets = new ArrayList<>();
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_lista_opciones, container, false);
        initViews();

        mPresenter =  new ListaOpcionesPresenter(this);

        CameraManager.getInstance().initCamera(getActivity(), iv_photo_item, this);

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

        // Hacemos SET de la infromacion del user
        // mName = "Mi Nombre";
        // mEmail = "mimail@micorreo.com";
        tv_name.setText(mName);
        tv_email.setText(mEmail);

        /*
        Hacemos un Set de la Imagen que queremos como inicial en el centro.
         */
        mDrawable = ContextCompat.getDrawable(getActivity(), android.R.drawable.ic_menu_add);
       // iv_photo_item.setCenterDrawable(mDrawable);

        // Hacemos Set de la version de codigo
        tv_version_code.setText("YaGanaste Versión: " + BuildConfig.VERSION_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_lista_opciones_user:
                Toast.makeText(getContext(), "Click User", Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(getContext(), "Click Legales", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_LEGALES, 1);
                break;
            case R.id.fragment_lista_opciones_close:
                // Toast.makeText(getContext(), "Click Close Session", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_CLOSE, 1);
                break;

            /**
             * Evento para Click de camara
             */
            case R.id.frag_lista_opciones_photo_item:
                //selectImageSource(USER_PHOTO);
                mPresenter.openMenuPhoto(1);
                break;
        }
    }

    /**
     * Metodo que entrega el BitMap para enviar al servicio
     * @param bitmap
     */
    @Override
    public void setPhotoToService(Bitmap bitmap) {

    }


/*    @Override
    public void showLoader(String message) {
        progressLayout.setTextMessage(message);
        progressLayout.setVisibility(VISIBLE);
    }*/
}
