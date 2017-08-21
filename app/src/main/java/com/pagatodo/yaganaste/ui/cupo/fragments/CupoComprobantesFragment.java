package com.pagatodo.yaganaste.ui.cupo.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.RegisterCupo;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;
import com.pagatodo.yaganaste.ui.cupo.presenters.CupoDomicilioPersonalPresenter;
import com.pagatodo.yaganaste.ui.cupo.view.IViewCupoComprobantes;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.utils.BitmapBase64Listener;
import com.pagatodo.yaganaste.utils.BitmapLoader;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.BorderTitleLayout;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity.CUPO_PASO;
import static com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity.CUPO_PASO_DOCUMENTOS_ENVIADOS;
import static com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity.CUPO_PASO_REGISTRO_ENVIADO;
import static com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity.ESTADO_REENVIAR_DOCUMENTOS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_CUPO_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_CUPO_FRONT;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_DOM_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_DOM_FRONT;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_FRONT;

/**
 * Created by Jordan on 25/07/2017.
 */

public class CupoComprobantesFragment extends GenericFragment implements View.OnClickListener , IViewCupoComprobantes , IPreferUserGeneric, SwipeRefreshLayout.OnRefreshListener {

    protected View rootview;
    @BindView(R.id.first_cube)
    BorderTitleLayout layoutIdentificacion;
    @BindView(R.id.lnr_help)
    LinearLayout layoutHelp;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private int estado = 0;

    private Boolean mExisteDocs = false;

    private ArrayList<DataDocuments> dataDocuments;

    private String imgs[] = new String[2];

    private CupoActivityManager cupoActivityManager;

    public static final int REQUEST_TAKE_PHOTO = 10; // Intent para Capturar fotografía
    public static final int SELECT_FILE_PHOTO = 20; // Intent para seleccionar fotografía

    private static final int COMPROBANTE_FRONT = 1;
    private static final int COMPROBANTE_BACK = 2;

    @BindView(R.id.itemWeNeedSmFilesAddressFront)
    UploadDocumentView itemWeNeedSmFilesAddressFront;
    @BindView(R.id.itemWeNeedSmFilesAddressBack)
    UploadDocumentView itemWeNeedSmFilesAddressBack;

    @BindView(R.id.btnWeNeedSmFilesNext)
    Button btnNext;
    @BindView(R.id.btnRegresar)
    Button btnBack;

    private int documentProcessed = 0;
    private int documentPendientes = 0;
    private BitmapLoader bitmapLoader;

    private ArrayList<String> contador;


    private CupoDomicilioPersonalPresenter presenter;

    public static CupoComprobantesFragment newInstance(int index) {
        CupoComprobantesFragment fragment = new CupoComprobantesFragment();
        Bundle args = new Bundle();
        args.putInt("paso", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contador = new ArrayList<>();
        cupoActivityManager = ((RegistryCupoActivity) getActivity()).getCupoActivityManager();
        presenter = new CupoDomicilioPersonalPresenter(this, getContext());
        presenter.setIView(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragments_documents, container, false);

        Bundle args = getArguments();
        estado = args.getInt("paso", 0);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        swipeRefreshLayout.setOnRefreshListener(this);
        dataDocuments = new ArrayList<>();
        layoutIdentificacion.setVisibility(View.GONE);
        layoutHelp.setVisibility(View.GONE);

        initSetClickableDocs();
        if (estado == ESTADO_REENVIAR_DOCUMENTOS ) {
            obtieneEstadoDeDocumentos();
        }
    }


    public void initSetClickableDocs() {

        itemWeNeedSmFilesAddressFront.setOnClickListener(this);
        itemWeNeedSmFilesAddressBack.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegresar:
                //cupoActivityManager.onBtnBackPress();
                cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_INICIO, null);
                break;
            /*
            case R.id.btnWeNeedSmFilesNext:
                cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_COMPLETE, null);
                //getActivity().finish();
                break;
            */

            case R.id.itemWeNeedSmFilesAddressFront:
                selectImageSource(COMPROBANTE_FRONT);
                break;

            case R.id.itemWeNeedSmFilesAddressBack:
                selectImageSource(COMPROBANTE_BACK);
                break;

            case R.id.btnWeNeedSmFilesNext:
                if (mExisteDocs) {
                    //sendDocumentsPending();
                    Log.e("Test", "Reenvia Documentos");
                } else {
                    sendDocuments();
                }
                break;

        }
    }



    /*Agregamos selección de carrete*/
    private void selectImageSource(final int documentId) {

        final CharSequence[] items = {getString(R.string.action_take_picture), getString(R.string.action_select_picture), getString(R.string.action_select_picture_cancel)};

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_camera, null);
        LayoutInflater titleInflater = getActivity().getLayoutInflater();
        View dialogTittle = titleInflater.inflate(R.layout.tittle_dialog, null);
        dialogView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setCustomTitle(dialogTittle);
        dialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
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
        AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.setCancelable(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();

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
                Uri photoURI;
                /*try {

                } catch (Exception ex) {
                    ex.printStackTrace();
                    photoURI = Uri.parse(photoFile.getAbsolutePath());
                }*/
                photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.pagatodo.yaganaste.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                List<ResolveInfo> resolvedIntentActivities = getActivity()
                        .getPackageManager()
                        .queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                    String packageName = resolvedIntentInfo.activityInfo.packageName;
                    getActivity().grantUriPermission(packageName,
                            photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                getActivity().startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
        // enableItems(false);

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

    private void takeGallery(int document) {
        documentProcessed = document;
        Intent intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentGallery.setType("image/*");
        getActivity().startActivityForResult(Intent.createChooser(intentGallery, "Selecciona Archivo"), SELECT_FILE_PHOTO);
        //enableItems(false);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            showLoader("");
            galleryAddPic();
            String path = SingletonUser.getInstance().getPathPictureTemp();
            bitmapLoader = new BitmapLoader(getActivity(), path, new BitmapBase64Listener() {
                @Override
                public void OnBitmap64Listener(Bitmap bitmap, String imgbase64) {
                    //enableItems(true);
                    saveBmpImgUser(bitmap, imgbase64);
                    hideLoader();
                }
            });
            bitmapLoader.execute();
        } else if (requestCode == SELECT_FILE_PHOTO && resultCode == RESULT_OK && null != data) {
            Cursor cursor = null;
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            try {
                String path;
                if (new File(selectedImage.getPath()).exists()) {
                    path = selectedImage.getPath();
                } else {
                    cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndexOrThrow(filePathColumn[0]);
                    path = cursor.getString(columnIndex);
                }
                bitmapLoader = new BitmapLoader(getActivity(), path, new BitmapBase64Listener() {
                    @Override
                    public void OnBitmap64Listener(Bitmap bitmap, String imgbase64) {
                        //enableItems(true);
                        saveBmpImgUser(bitmap, imgbase64);
                        hideLoader();
                    }
                });
                bitmapLoader.execute();
            } catch (Exception e) {
                e.printStackTrace();
                UI.createSimpleCustomDialog("", "Error al Cargar Imagen", getActivity().getSupportFragmentManager(), null, true, false);
                presenter.showGaleryError();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else {
            UI.createSimpleCustomDialog("", "Error al Cargar Imagen", getActivity().getSupportFragmentManager(), null, true, false);
        }

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
        Boolean validateDuplicado;
        contador.add(imgBase64);
        validateDuplicado = checkDuplicate(contador);
        DataDocuments dataDoc = new DataDocuments();
        if (!validateDuplicado) {
            contador.remove(imgBase64);
            UI.createSimpleCustomDialogNoCancel("", getString(R.string.imagen_duplicada), getActivity().getSupportFragmentManager(), null);
        } else {
            switch (documentProcessed) {
                case COMPROBANTE_FRONT:
                    itemWeNeedSmFilesAddressFront.setImageBitmap(bitmap);
                    itemWeNeedSmFilesAddressFront.setVisibilityStatus(true);
                    itemWeNeedSmFilesAddressFront.setStatusImage(ContextCompat.getDrawable(getContext(), R.drawable.ic_status_upload));
                    itemWeNeedSmFilesAddressFront.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_CUPO_FRONT);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");

                    break;
                case COMPROBANTE_BACK:
                    itemWeNeedSmFilesAddressBack.setImageBitmap(bitmap);
                    itemWeNeedSmFilesAddressBack.setVisibilityStatus(true);
                    itemWeNeedSmFilesAddressBack.setStatusImage(ContextCompat.getDrawable(getContext(), R.drawable.ic_status_upload));
                    itemWeNeedSmFilesAddressBack.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_CUPO_BACK);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");
                    break;
            }
            dataDocuments.add(dataDoc);
            if (bitmap.isRecycled()) {
                bitmap.recycle();
            }
            bitmap = null;
            btnNext.setVisibility(VISIBLE);
        }
    }

    /**
     * Enviamos los documentos cuando se esta registrando el adquirente
     */
    private void sendDocuments() {
        for (String s : imgs)
            if (s == null || s.isEmpty()) {
                showError(App.getContext().getResources().getString(R.string.adq_must_upload_documents));
                return;
            }
        presenter.sendDocumentos(dataDocuments);
    }


    private void reenviaDocumentos() {
        for (String s : imgs)
            if (s == null || s.isEmpty()) {
                showError(App.getContext().getResources().getString(R.string.adq_must_upload_documents));
                return;
            }
        presenter.reenviaDocumentos(dataDocuments);
    }

    /**
     * Validamos que no se repitan las fotos obtenidas de la galeria
     *
     * @param list
     * @return
     */

    public static boolean checkDuplicate(ArrayList list) {
        HashSet set = new HashSet();
        for (int i = 0; i < list.size(); i++) {
            boolean val = set.add(list.get(i));
            if (val == false) {
                return val;
            }
        }
        return true;
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        if (isMenuVisible() && onEventListener != null) {
            onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(Object error) {
        final String message = error instanceof ErrorObject ? ((ErrorObject) error).getErrorMessage() : error.toString();
        UI.createSimpleCustomDialog("", message, getActivity().getSupportFragmentManager(), new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                // Toast.makeText(getContext(), "Click CERRAR SESSION", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void actionCancel(Object... params) {

            }
        }, true, false);
    }

    @Override
    public void setResponseDocuments() {
        App.getInstance().getPrefs().saveData( CUPO_PASO , CUPO_PASO_DOCUMENTOS_ENVIADOS);
        cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_COMPLETE, null);
    }

    @Override
    public void obtieneEstadoDeDocumentos() {
        presenter.getEstatusDocs();
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        refreshContent();
    }

    private void refreshContent() {
        if (isMenuVisible()) {
            showLoader(getString(R.string.recuperando_docs_estatus));
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }
        presenter.getEstatusDocs();
    }
}
