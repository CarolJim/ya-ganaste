package com.pagatodo.yaganaste.ui.adquirente;


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
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IUploadDocumentsView;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountAdqPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.utils.BitmapBase64Listener;
import com.pagatodo.yaganaste.utils.BitmapLoader;
import com.pagatodo.yaganaste.utils.UI;
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
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
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
public class Documentos extends GenericFragment implements View.OnClickListener, IUploadDocumentsView,
        SwipeRefreshLayout.OnRefreshListener, IPreferUserGeneric {

    public static final int REQUEST_TAKE_PHOTO = 10; // Intent para Capturar fotografía
    public static final int SELECT_FILE_PHOTO = 20; // Intent para seleccionar fotografía
    private static final String TAG = Documentos.class.getSimpleName();
    private static final int IFE_FRONT = 1;
    private static final int IFE_BACK = 2;
    private static final int COMPROBANTE_FRONT = 3;
    private static final int COMPROBANTE_BACK = 4;
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
    private View rootview;
    //@BindView(R.id.progressLayout)
    //ProgressLayout progressLayout;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contador = new ArrayList<>();
        adqPresenter = new AccountAdqPresenter(this, getContext());
        adqPresenter.setIView(this);
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
            btnWeNeedSmFilesNext.setVisibility(View.INVISIBLE);
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
                if (mExisteDocs) {
                    sendDocumentsPending();
                } else {
                    sendDocuments();
                }
                break;

            case R.id.btnRegresar:
                if (mExisteDocs) {
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
                UI.createSimpleCustomDialog("", "Error al cargar imágen", getActivity().getSupportFragmentManager(), null, true, false);
                adqPresenter.showGaleryError();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else {
            UI.createSimpleCustomDialog("", "Error al cargar imágen", getActivity().getSupportFragmentManager(), null, true, false);
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
            UI.createSimpleCustomDialogNoCancel("", getString(R.string.imagen_duplicada), getActivity().getSupportFragmentManager(), null);
        } else {
            switch (documentProcessed) {
                case IFE_FRONT:
                    itemWeNeedSmFilesIFEfront.setImageBitmap(bitmap);
                    itemWeNeedSmFilesIFEfront.setVisibilityStatus(true);
                    itemWeNeedSmFilesIFEfront.setStatusImage(ContextCompat.getDrawable(getContext(), R.drawable.upload_canvas_blue));
                    itemWeNeedSmFilesIFEfront.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_ID_FRONT);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");
                    break;

                case IFE_BACK:
                    itemWeNeedSmFilesIFEBack.setImageBitmap(bitmap);
                    itemWeNeedSmFilesIFEBack.setVisibilityStatus(true);
                    itemWeNeedSmFilesIFEBack.setStatusImage(ContextCompat.getDrawable(getContext(), R.drawable.upload_canvas_blue));
                    itemWeNeedSmFilesIFEBack.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_ID_BACK);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");
                    break;

                case COMPROBANTE_FRONT:
                    itemWeNeedSmFilesAddressFront.setImageBitmap(bitmap);
                    itemWeNeedSmFilesAddressFront.setVisibilityStatus(true);
                    itemWeNeedSmFilesAddressFront.setStatusImage(ContextCompat.getDrawable(getContext(), R.drawable.upload_canvas_blue));
                    itemWeNeedSmFilesAddressFront.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_DOM_FRONT);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");

                    break;
                case COMPROBANTE_BACK:
                    itemWeNeedSmFilesAddressBack.setImageBitmap(bitmap);
                    itemWeNeedSmFilesAddressBack.setVisibilityStatus(true);
                    itemWeNeedSmFilesAddressBack.setStatusImage(ContextCompat.getDrawable(getContext(), R.drawable.upload_canvas_blue));
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
            btnWeNeedSmFilesNext.setVisibility(VISIBLE);
        }
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

    private void takeGallery(int document) {
        documentProcessed = document;
        Intent intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentGallery.setType("image/*");
        getActivity().startActivityForResult(Intent.createChooser(intentGallery, "Selecciona Archivo"), SELECT_FILE_PHOTO);
        //enableItems(false);
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
                if (mExisteDocs) {
                    getEstatusDocs();
                }
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
        showLoader(s);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoader();
                getEstatusDocs();
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
        documentPendientes = 0;
        for (EstatusDocumentosResponse docs : data) {
            if (docs.getIdEstatus() == STATUS_DOCTO_RECHAZADO) {
                documentPendientes++;
            }
        }

        // Coigo SOLO para probar la carga correcta de estado al cargar imagen
        //itemWeNeedSmFilesIFEfront.setStatusImage(getResources().getDrawable(R.drawable.upload_canvas_blue));
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
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
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

    /**
     * Enviamos los documentos que fueron rechazados
     */
    private void sendDocumentsPending() {
        if (dataDocumnets.size() < documentPendientes) {
            UI.createSimpleCustomDialog("", "Debes de Subir los documentos marcados con el signo de admiración", getActivity().getSupportFragmentManager(),
                    new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {

                        }

                        @Override
                        public void actionCancel(Object... params) {

                        }
                    }, true, false);
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
                showError(App.getContext().getResources().getString(R.string.adq_must_upload_documents));
                return;
            }
        adqPresenter.sendDocumentos(dataDocumnets);
    }

    @Override
    public void onRefresh() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        swipeRefreshLayout.destroyDrawingCache();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshContent();
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    private void refreshContent() {
        swipeRefreshLayout.setRefreshing(false);
        adqPresenter.getEstatusDocs();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        swipeRefreshLayout.destroyDrawingCache();
        if (mExisteDocs) {
            adqPresenter.getEstatusDocs();
        }
    }

}

