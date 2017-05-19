package com.pagatodo.yaganaste.ui.adquirente;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.interfaces.IUploadDocumentsView;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountAdqPresenter;
import com.pagatodo.yaganaste.utils.BitmapBase64Listener;
import com.pagatodo.yaganaste.utils.BitmapLoader;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
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
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_ADDRESS_BACK;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_COMPLETE;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.EVENT_GO_HOME;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_PENDIENTE;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_DOM_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_DOM_FRONT;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_FRONT;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_RECHAZADO;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class Documentos extends GenericFragment implements View.OnClickListener, IUploadDocumentsView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = Documentos.class.getSimpleName();
    public static final int REQUEST_TAKE_PHOTO = 10; // Intent para Capturar fotografía
    public static final int SELECT_FILE_PHOTO = 20; // Intent para seleccionar fotografía
    private static final int IFE_FRONT = 1;
    private static final int IFE_BACK = 2;
    private static final int COMPROBANTE_FRONT = 3;
    private static final int COMPROBANTE_BACK = 4;
    private View rootview;

    @BindView(R.id.itemWeNeedSmFilesIFEfront)
    UploadDocumentView itemWeNeedSmFilesIFEfront;
    @BindView(R.id.itemWeNeedSmFilesIFEBack)
    UploadDocumentView itemWeNeedSmFilesIFEBack;
    @BindView(R.id.itemWeNeedSmFilesAddressFront)
    UploadDocumentView itemWeNeedSmFilesAddressFront;
    @BindView(R.id.itemWeNeedSmFilesAddressBack)
    UploadDocumentView itemWeNeedSmFilesAddressBack;
    @BindView(R.id.btnWeNeedSmFilesNext)
    Button btnWeNeedSmFilesNext;
    @BindView(R.id.btnRegresar)
    Button btnRegresar;
    @BindView(R.id.lnr_buttons)
    LinearLayout lnr_buttons;
    @BindView(R.id.lnr_help)
    LinearLayout lnr_help;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;
    private int documentProcessed = 0;
    private int documentPendientes = 0;
    private BitmapLoader bitmapLoader;

    private String imgs[] = new String[4];
    private ArrayList<String> contador;
    private ArrayList<DataDocuments> dataDocumnets;
    private AccountAdqPresenter adqPresenter;
    private Boolean mExisteDocs = false;

    public Documentos() {
    }

    public static Documentos newInstance() {
        Documentos fragmentRegister = new Documentos();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contador = new ArrayList<>();
        adqPresenter = new AccountAdqPresenter(this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragments_documents, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(false);
        dataDocumnets = new ArrayList<>();

        if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && SingletonUser.getInstance().getDataUser().getEstatusAgente() == CRM_PENDIENTE
                && SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == CRM_PENDIENTE) {  //si ya se hiso el proceso de envio de documentos
            mExisteDocs = true;
            lnr_help.setVisibility(VISIBLE);
            getEstatusDocs();
            initSetClickableStatusDocs();
        } else {     // si no se han enviado los documentos

            initSetClickableDocs();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.itemWeNeedSmFilesIFEfront:
                selectImageSource(IFE_FRONT);
                break;
            case R.id.itemWeNeedSmFilesIFEBack:
                selectImageSource(IFE_BACK);
                break;

            case R.id.itemWeNeedSmFilesAddressFront:
                selectImageSource(COMPROBANTE_FRONT);
                break;

            case R.id.itemWeNeedSmFilesAddressBack:
                selectImageSource(COMPROBANTE_BACK);
                break;

            case R.id.btnWeNeedSmFilesNext:
                if (mExisteDocs == true) {
                    sendDocumentsPending();
                } else {
                    sendDocuments();
                }
                break;

            case R.id.btnRegresar:
                if (mExisteDocs == true) {
                    bacToHome();
                } else {
                    backToRegister();
                }
                break;

            default:
                break;
        }
    }

    public void initSetClickableStatusDocs() {
        itemWeNeedSmFilesIFEfront.setOnClickListener(this);
        itemWeNeedSmFilesIFEBack.setOnClickListener(this);
        itemWeNeedSmFilesAddressFront.setOnClickListener(this);
        itemWeNeedSmFilesAddressBack.setOnClickListener(this);
        itemWeNeedSmFilesIFEfront.setClickable(false);
        itemWeNeedSmFilesIFEBack.setClickable(false);
        itemWeNeedSmFilesAddressFront.setClickable(false);
        itemWeNeedSmFilesAddressBack.setClickable(false);
        btnRegresar.setOnClickListener(this);
        btnWeNeedSmFilesNext.setOnClickListener(this);
    }

    public void initSetClickableDocs() {
        lnr_buttons.setVisibility(VISIBLE);
        lnr_help.setVisibility(GONE);
        itemWeNeedSmFilesIFEfront.setOnClickListener(this);
        itemWeNeedSmFilesIFEBack.setOnClickListener(this);
        itemWeNeedSmFilesAddressFront.setOnClickListener(this);
        itemWeNeedSmFilesAddressBack.setOnClickListener(this);
        btnWeNeedSmFilesNext.setOnClickListener(this);
        btnRegresar.setOnClickListener(this);
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
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode != RESULT_OK) {
            // enableItems(true);
        } else if (requestCode == SELECT_FILE_PHOTO && resultCode == RESULT_OK && null != data) {
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
                        hideLoader();
                    }
                });
                bitmapLoader.execute();
            } catch (Exception e) {
                e.printStackTrace();
                adqPresenter.showGaleryError();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if (requestCode == SELECT_FILE_PHOTO && resultCode != RESULT_OK && data == null) {

        }
    }

    private void backToRegister() {
        backScreen(EVENT_GO_BUSSINES_ADDRESS_BACK, null);
    }

    private void bacToHome() {
        backScreen(EVENT_GO_HOME, null);
    }

    private void getEstatusDocs() {
        adqPresenter.getEstatusDocs();
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
            UI.showToast("Imagen duplicada , seleccione una imagen diferente ", getContext());
        } else {
            switch (documentProcessed) {
                case IFE_FRONT:
                    itemWeNeedSmFilesIFEfront.setImageBitmap(bitmap);
                    itemWeNeedSmFilesIFEfront.setVisibilityStatus(false);
                    itemWeNeedSmFilesIFEfront.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_ID_FRONT);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");
                    break;

                case IFE_BACK:
                    itemWeNeedSmFilesIFEBack.setImageBitmap(bitmap);
                    itemWeNeedSmFilesIFEBack.setVisibilityStatus(false);
                    itemWeNeedSmFilesIFEBack.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_ID_BACK);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");
                    break;

                case COMPROBANTE_FRONT:
                    itemWeNeedSmFilesAddressFront.setImageBitmap(bitmap);
                    itemWeNeedSmFilesAddressFront.setVisibilityStatus(false);
                    itemWeNeedSmFilesAddressFront.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_DOM_FRONT);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");

                    break;
                case COMPROBANTE_BACK:
                    itemWeNeedSmFilesAddressBack.setImageBitmap(bitmap);
                    itemWeNeedSmFilesAddressBack.setVisibilityStatus(false);
                    itemWeNeedSmFilesAddressBack.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_DOM_BACK);
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

    private void takeGallery(int document) {
        documentProcessed = document;
        Intent intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentGallery.setType("image/*");
        getActivity().startActivityForResult(Intent.createChooser(intentGallery, "Selecciona Archivo"), SELECT_FILE_PHOTO);
        //enableItems(false);
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

    /**
     * cambiamos de fragmento cuando se completo el proceso de registro adquirente
     *
     * @param message
     */
    @Override
    public void documentsUploaded(String message) {
        showLoader(message);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                hideLoader();
                nextScreen(EVENT_GO_BUSSINES_COMPLETE, null);
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    /***
     * cambiamos a tabactivity cunado los documentos fueron actualizados
     * @param s
     */
    @Override
    public void documentosActualizados(String s) {
        Log.e(TAG, " cambiamos de fragmento");
        showLoader(s);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextScreen(EVENT_GO_HOME, null);
            }
        }, DELAY_MESSAGE_PROGRESS);

    }

    /**
     * Seteamos la imagen con el estatus de los documentos y contamos los documentos que fueron rechazados
     *
     * @param data
     */
    @Override
    public void setDocumentosStatus(List<EstatusDocumentosResponse> data) {
        adqPresenter.setEstatusDocs(rootview, data);
        // Contamos los documentos pendientes
        for (EstatusDocumentosResponse docs : data) {
            if (docs.getIdEstatus() == STATUS_DOCTO_RECHAZADO) {
                documentPendientes++;
            }
        }
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
        progressLayout.setTextMessage(message);
        progressLayout.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(GONE);
    }

    @Override
    public void showError(Object error) {
        UI.showToastShort(error.toString(), getActivity());
    }

    /**
     * Enviamos los documentos que fueron rechazados
     */
    private void sendDocumentsPending() {
        if (dataDocumnets.size() < documentPendientes) {
            showError("Debes de Subir los documentos marcados con el signo de admiración");
            return;
        }
        adqPresenter.sendDocumentosPendientes(dataDocumnets);
    }

    /**
     * Enviamos los documentos cuando se esta registrando el adquirente
     */
    private void sendDocuments() {
        for (String s : imgs)
            if (s == null || s.isEmpty()) {
                showError("Debes de Subir Todos los Documentos.");
                return;
            }
        adqPresenter.sendDocumentos(dataDocumnets);
    }

    @Override
    public void onRefresh() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //    imgs = new String[documentPendientes-1];
                refreshContent();
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    private void refreshContent() {
        getEstatusDocs();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}

