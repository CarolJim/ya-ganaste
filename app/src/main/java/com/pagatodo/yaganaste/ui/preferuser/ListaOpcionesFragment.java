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
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountAdqPresenter;
import com.pagatodo.yaganaste.ui.adquirente.Documentos;
import com.pagatodo.yaganaste.utils.BitmapBase64Listener;
import com.pagatodo.yaganaste.utils.BitmapLoader;
import com.pagatodo.yaganaste.utils.UI;
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
public class ListaOpcionesFragment extends GenericFragment implements View.OnClickListener {

    public static String IS_ES_AGENTE = "IS_ES_AGENTE";
    public static String USER_NAME = "USER_NAME";
    public static String USER_EMAIL = "USER_EMAIL";
    private boolean isEsAgente;
    private String mName, mEmail;

    private static final String TAG = Documentos.class.getSimpleName();
    public static final int REQUEST_TAKE_PHOTO = 10; // Intent para Capturar fotografía
    public static final int SELECT_FILE_PHOTO = 20; // Intent para seleccionar fotografía
    private static final int USER_PHOTO = 1;
    private static final int IFE_BACK = 2;
    private static final int COMPROBANTE_FRONT = 3;
    private static final int COMPROBANTE_BACK = 4;

//    @BindView(R.id.progressLayout)
//    ProgressLayout progressLayout;
    private int documentProcessed = 0;
    private BitmapLoader bitmapLoader;
    private String imgs[] = new String[4];
    private ArrayList<String> contador ;
    private ArrayList<DataDocuments> dataDocumnets;
    private AccountAdqPresenter adqPresenter;
    private Drawable mDrawable = null;
    private Preferencias pref;
    private Boolean mExisteDocs = false;

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
    @BindView(R.id.frag_lista_opciones_photo_item)
    UploadDocumentView iv_photo_item;

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
                selectImageSource(USER_PHOTO);
                break;
        }
    }

    /*Agregamos selección de carrete*/
    private void selectImageSource(final int documentId) {
        final CharSequence[] items = {getString(R.string.action_take_picture), getString(R.string.action_select_picture),
                getString(R.string.action_select_picture_cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.MultiChoiceDialog));
        builder.setTitle(getString(R.string.action_picture));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                switch (item) {
                    case 0:
                        takeDocumentPicture(documentId);
                        break;
                    case 1:
                        takeGallery(documentId);
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.show();
    }

    private void takeDocumentPicture(int document) {
        documentProcessed = document;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.e(TAG, "dispatchTakePictureIntent: " + photoFile);
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.pagatodo.yaganaste.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                List<ResolveInfo> resolvedIntentActivities = getActivity().getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                    String packageName = resolvedIntentInfo.activityInfo.packageName;
                    getActivity().grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }


                getActivity().startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
        // enableItems(false);

    }

    private  void takeGallery(int document){
        documentProcessed = document;
        Intent intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentGallery.setType("image/*");
        getActivity().startActivityForResult(Intent.createChooser(intentGallery, "Selecciona Archivo"), SELECT_FILE_PHOTO);
        //enableItems(false);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        /*Guardamos el path temporal*/
        SingletonUser.getInstance().setPathPictureTemp(image.getAbsolutePath());
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        String path = SingletonUser.getInstance().getPathPictureTemp();
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    private void saveBmpImgUser(Bitmap bitmap, String imgBase64) {
        Boolean validateDuplicado ;
        contador.add(imgBase64);
        validateDuplicado = checkDuplicate(contador);
        DataDocuments dataDoc = new DataDocuments();
        if(!validateDuplicado){
            contador.remove(imgBase64);
            UI.showToast("Imagen duplicada , seleccione una imagen diferente ",getContext());
        }else {
            switch (documentProcessed) {
                case USER_PHOTO:
                    iv_photo_item.setImageBitmap(bitmap);
                    iv_photo_item.setVisibilityStatus(false);
                    iv_photo_item.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_ID_FRONT);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");
                    break;
            }
            dataDocumnets.add(dataDoc);
            if (bitmap.isRecycled()) {
                bitmap.recycle();
            }

            bitmap = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //  showLoader("");
            galleryAddPic();
            String path = SingletonUser.getInstance().getPathPictureTemp();
            bitmapLoader = new BitmapLoader(getActivity(), path, new BitmapBase64Listener() {
                @Override
                public void OnBitmap64Listener(Bitmap bitmap, String imgbase64) {
                    //enableItems(true);
                    saveBmpImgUser(bitmap, imgbase64);
                    //  hideLoader();
                }
            });
            bitmapLoader.execute();
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode != RESULT_OK) {
            // enableItems(true);
        } else  if (requestCode == SELECT_FILE_PHOTO && resultCode == RESULT_OK && null != data) {
            Cursor cursor = null;
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            try {
                // Get the cursor
                cursor = getContext().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndexOrThrow(filePathColumn[0]);
                String path = cursor.getString(columnIndex);
                bitmapLoader = new BitmapLoader(getActivity(), path, new BitmapBase64Listener() {
                    @Override
                    public void OnBitmap64Listener(Bitmap bitmap, String imgbase64) {
                        //enableItems(true);
                        saveBmpImgUser(bitmap, imgbase64);
                        //   hideLoader();
                    }
                });
                bitmapLoader.execute();
            }catch (Exception e){
                e.printStackTrace();
                adqPresenter.showGaleryError();
            }finally {
                if(cursor != null){
                    cursor.close();
                }
            }
        }else if(requestCode == SELECT_FILE_PHOTO && resultCode != RESULT_OK && data == null ){

        }
    }


/*    @Override
    public void showLoader(String message) {
        progressLayout.setTextMessage(message);
        progressLayout.setVisibility(VISIBLE);
    }*/
}
