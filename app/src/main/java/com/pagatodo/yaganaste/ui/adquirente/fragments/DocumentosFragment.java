package com.pagatodo.yaganaste.ui.adquirente.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IUploadDocumentsView;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountAdqPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.utils.BitmapBase64Listener;
import com.pagatodo.yaganaste.utils.BitmapLoader;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_ADDRESS_BACK;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_COMPLETE;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.EVENT_DOCUMENT_APPROVED;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.EVENT_GO_HOME;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_PENDIENTE;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_DOM_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_DOM_FRONT;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_FRONT;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_PENDIENTE;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_RECHAZADO;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DocumentosFragment extends GenericFragment implements View.OnClickListener, IUploadDocumentsView,
        SwipeRefreshLayout.OnRefreshListener, IPreferUserGeneric {

    public static final int REQUEST_TAKE_PHOTO = 10; // Intent para Capturar fotografía
    public static final int SELECT_FILE_PHOTO = 20; // Intent para seleccionar fotografía
    private static final String TAG = DocumentosFragment.class.getSimpleName();
    private static final int IFE_FRONT = 1;
    private static final int IFE_BACK = 2;
    private static final int COMPROBANTE_FRONT = 3;
    private static final int COMPROBANTE_BACK = 4;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 101;
    @BindView(R.id.itemWeNeedSmFilesIFEfront)
    UploadDocumentView ifeFront;
    @BindView(R.id.itemWeNeedSmFilesIFEBack)
    UploadDocumentView ifeBack;
    @BindView(R.id.itemWeNeedSmFilesAddressFront)
    UploadDocumentView addressFront;
    @BindView(R.id.itemWeNeedSmFilesAddressBack)
    UploadDocumentView addressBack;
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
    private int documentProcessed = 0;
    private int documentPendientes = 0;
    private int documentApproved = 0;
    private BitmapLoader bitmapLoader;


    private String imgs[] = new String[4];
    private ArrayList<String> contador;
    private Map<Integer, DataDocuments> dataDocumnets;
    private ArrayList<DataDocuments> dataDocumnetsServer;
    private AccountAdqPresenter adqPresenter;
    private Boolean mExisteDocs = false;
    ArrayList<EstatusDocumentosResponse> dataStatusDocuments;
    private ImageView imageViewshare;

    public DocumentosFragment() {
    }

    public static DocumentosFragment newInstance() {
        DocumentosFragment fragmentRegister = new DocumentosFragment();
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

        adqPresenter = new AccountAdqPresenter(this);
        adqPresenter.setIView(this);
        dataStatusDocuments = new ArrayList<>();
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
        imageViewshare = (ImageView) getActivity().findViewById(R.id.deposito_Share);
        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Usamos un Map dataDocumnets para ir actualizando los documentos y evitar el problema de
         * escoger una imagen y luego cambiarla a otra diferente.
         */
        dataDocumnetsServer = new ArrayList<>();
        dataDocumnets = new HashMap<>();
        if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && SingletonUser.getInstance().getDataUser().getEstatusAgente() == CRM_PENDIENTE
                && SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == CRM_PENDIENTE) {  //si ya se hiso el proceso de envio de documentos
            mExisteDocs = true;
            // lnr_help.setVisibility(VISIBLE);
            initSetClickableStatusDocs();
            btnWeNeedSmFilesNext.setVisibility(View.INVISIBLE);

            refreshContent();
        } else {     // si no se han enviado los documentos
            initSetClickableDocs();
        }
        if (mExisteDocs) {
            btnRegresar.setVisibility(View.GONE);
            lnr_buttons.setVisibility(View.GONE);
        }

        hideStatusPhoto();

    }

    private void hideStatusPhoto() {
        /**
         * Se ocultan los circulos de estado de carga de las imagenes
         */
        ifeFront.setVisibilityStatus(false);
        ifeBack.setVisibilityStatus(false);
        addressFront.setVisibilityStatus(false);
        addressBack.setVisibilityStatus(false);
    }

    @Override
    public void onClick(View view) {
        /**
         * En cada clic hacemos una comprobacion, si el dataStatusDocuments tiene elementos del servidor
         * y dependiendo del estadfo, si es STATUS_DOCTO_RECHAZADO mostramos elPopup, si no, es el
         * primer envio y mostramos el proceso de tomar foto de manera normal
         */
        switch (view.getId()) {
            case R.id.itemWeNeedSmFilesIFEfront:
                if (dataStatusDocuments.size() > 0 && dataStatusDocuments.get(0).getIdEstatus() == STATUS_DOCTO_RECHAZADO) {
                    showDocumentRejected(dataStatusDocuments.get(0), 0);
                } else {
                    selectImageSource(IFE_FRONT);
                }
                break;
            case R.id.itemWeNeedSmFilesIFEBack:
                //selectImageSource(IFE_BACK);
                if (dataStatusDocuments.size() > 0 && dataStatusDocuments.get(1).getIdEstatus() == STATUS_DOCTO_RECHAZADO) {
                    showDocumentRejected(dataStatusDocuments.get(1), 1);
                } else {
                    selectImageSource(IFE_BACK);
                }
                break;

            case R.id.itemWeNeedSmFilesAddressFront:
                if (dataStatusDocuments.size() > 0 && dataStatusDocuments.get(2).getIdEstatus() == STATUS_DOCTO_RECHAZADO) {
                    showDocumentRejected(dataStatusDocuments.get(2), 2);
                } else {
                    selectImageSource(COMPROBANTE_FRONT);
                }
                break;

            case R.id.itemWeNeedSmFilesAddressBack:
                if (dataStatusDocuments.size() > 0 && dataStatusDocuments.get(3).getIdEstatus() == STATUS_DOCTO_RECHAZADO) {
                    showDocumentRejected(dataStatusDocuments.get(3), 3);
                } else {
                    selectImageSource(COMPROBANTE_BACK);
                }
                break;

            case R.id.btnWeNeedSmFilesNext:
                if (mExisteDocs) {
                    sendDocumentsPending();
                } else {
                    sendDocuments();
                }
                break;

            case R.id.btnRegresar:
                onBtnBack();
                break;

            default:
                break;
        }
    }

    public void onBtnBack() {
        if (mExisteDocs) {
            bacToHome();
        } else {
            backToRegister();
        }
    }

    public void initSetClickableStatusDocs() {
        ifeFront.setOnClickListener(this);
        ifeBack.setOnClickListener(this);
        addressFront.setOnClickListener(this);
        addressBack.setOnClickListener(this);
        ifeFront.setClickable(false);
        ifeBack.setClickable(false);
        addressFront.setClickable(false);
        addressBack.setClickable(false);
        btnRegresar.setOnClickListener(this);
        btnWeNeedSmFilesNext.setOnClickListener(this);
    }

    public void initSetClickableDocs() {
        swipeRefreshLayout.setEnabled(false);
        lnr_buttons.setVisibility(VISIBLE);
        lnr_help.setVisibility(GONE);
        ifeFront.setOnClickListener(this);
        ifeBack.setOnClickListener(this);
        addressFront.setOnClickListener(this);
        addressBack.setOnClickListener(this);
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
                public void onBegin() {
                    //TODO ShowLoader
                }

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
                    public void onBegin() {
                        //TODO showLoader
                    }

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
                UI.createSimpleCustomDialog("", getString(R.string.error_cargar_imagen), getActivity().getSupportFragmentManager(), null, true, false);
                adqPresenter.showGaleryError();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

    private void backToRegister() {
        backScreen(EVENT_GO_BUSSINES_ADDRESS_BACK, null);
    }

    private void bacToHome() {
        backScreen(EVENT_GO_HOME, null);
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
                    ifeFront.setImageBitmap(bitmap);
                    ifeFront.setVisibilityStatus(true);
                    ifeFront.setStatusImage(ContextCompat.getDrawable(getContext(), R.drawable.ic_status_upload));
                    ifeFront.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_ID_FRONT);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");
                    dataDocumnets.put(IFE_FRONT, dataDoc);
                    break;

                case IFE_BACK:
                    ifeBack.setImageBitmap(bitmap);
                    ifeBack.setVisibilityStatus(true);
                    ifeBack.setStatusImage(ContextCompat.getDrawable(getContext(), R.drawable.ic_status_upload));
                    ifeBack.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_ID_BACK);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");
                    dataDocumnets.put(IFE_BACK, dataDoc);
                    break;

                case COMPROBANTE_FRONT:
                    addressFront.setImageBitmap(bitmap);
                    addressFront.setVisibilityStatus(true);
                    addressFront.setStatusImage(ContextCompat.getDrawable(getContext(), R.drawable.ic_status_upload));
                    addressFront.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_DOM_FRONT);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");
                    dataDocumnets.put(COMPROBANTE_FRONT, dataDoc);

                    break;
                case COMPROBANTE_BACK:
                    addressBack.setImageBitmap(bitmap);
                    addressBack.setVisibilityStatus(true);
                    addressBack.setStatusImage(ContextCompat.getDrawable(getContext(), R.drawable.ic_status_upload));
                    addressBack.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_DOM_BACK);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");
                    dataDocumnets.put(COMPROBANTE_BACK, dataDoc);
                    break;
            }

            if (bitmap.isRecycled()) {
                bitmap.recycle();
            }
            bitmap = null;
            btnWeNeedSmFilesNext.setVisibility(VISIBLE);
            btnRegresar.setVisibility(GONE);
            lnr_buttons.setVisibility(VISIBLE);
            swipeRefreshLayout.setEnabled(false);
        }
        hideStatusPhoto();
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

    }

    private void takeGallery(int document) {
        documentProcessed = document;
        Intent intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentGallery.setType("image/*");
        getActivity().startActivityForResult(Intent.createChooser(intentGallery, getString(R.string.txt_selecciona_archivo)), SELECT_FILE_PHOTO);
        //enableItems(false);
    }

    /*Agregamos selección de carrete*/
    private void selectImageSource(final int documentId) {
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
    }

    /**
     * cambiamos de fragmento cuando se completo el proceso de registro adquirente
     *
     * @param message
     */
    @Override
    public void documentsUploaded(String message) {
        dataDocumnets.clear();
        nextScreen(EVENT_GO_BUSSINES_COMPLETE, null);
        SingletonUser.getInstance().getDataUser().setIdEstatus(7);
        onEventListener.onEvent(TabActivity.EVENT_CARGA_DOCUMENTS, null);

    }

    /***
     * cambiamos a tabactivity cunado los documentos fueron actualizados
     * @param s
     */
    @Override
    public void documentosActualizados(String s) {
        dataDocumnets.clear();
        refreshContent();
        SingletonUser.getInstance().getDataUser().setIdEstatus(7);
        onEventListener.onEvent(TabActivity.EVENT_CARGA_DOCUMENTS, null);

    }

    /**
     * Seteamos la imagen con el estatus de los documentos y contamos los documentos que fueron rechazados
     *
     * @param data
     */
    @Override
    public void setDocumentosStatus(List<EstatusDocumentosResponse> data) {
        documentPendientes = 0;
        documentApproved = 0;

        if (data != null && data.size() > 0) {
            // Borramos los elementos de nuestro array de apoyo
            dataStatusDocuments.clear();
            dataStatusDocuments = (ArrayList<EstatusDocumentosResponse>) data;
            setDocumentsStatusDrawables(data);
        }

        // Enviamos a la pantalla de documentos aprovados
        if (documentApproved == 4) {
            onEventListener.onEvent(EVENT_DOCUMENT_APPROVED, null);
        }

        int idStatus;
        int tipoDoc;
        int idDrawableStatus;
        Bitmap bitmap;

        for (EstatusDocumentosResponse estatusDocs : data) {
            idStatus = estatusDocs.getIdEstatus();
            tipoDoc = estatusDocs.getTipoDocumento();

            if (idStatus == STATUS_DOCTO_RECHAZADO) {
                idDrawableStatus = R.drawable.ic_alerta2;
            } else if (idStatus == STATUS_DOCTO_APROBADO) {
                documentApproved++;
                idDrawableStatus = R.drawable.ic_document_done2;
            } else if (idStatus == STATUS_DOCTO_PENDIENTE) {
                idDrawableStatus = R.drawable.ic_wait;
            } else {
                idDrawableStatus = R.drawable.ic_wait;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            options.inSampleSize = 1; // make sure pixels are 1:1
            options.inPreferQualityOverSpeed = true;
            bitmap = BitmapFactory.decodeResource(App.getContext().getResources(),
                    idDrawableStatus, options);

            switch (tipoDoc) {
                case DOC_ID_FRONT:
                    ifeFront.setImageBitmap(bitmap);

                    // ifeFront.getCircleImageView().setBackgroundColor(Color.TRANSPARENT);
                    //  ifeFront.setBackground(ContextCompat.getDrawable(App.getContext(), R.drawable.ic_usuario_azul));
                    break;
                case DOC_ID_BACK:
                    // ifeBack.setVisibilityStatus(true);
                    // ifeBack.setBackgroundResource(R.drawable.ic_alerta2);
                    ifeBack.setImageBitmap(bitmap);
                    break;
                case DOC_DOM_FRONT:
                    // addressFront.setVisibilityStatus(true);
                    // addressFront.setBackgroundResource(R.drawable.ic_alerta2);
                    addressFront.setImageBitmap(bitmap);
                    break;
                case DOC_DOM_BACK:
                    // addressBack.setVisibilityStatus(true);
                    // addressBack.setBackgroundResource(R.drawable.ic_alerta2);
                    addressBack.setImageBitmap(bitmap);
                    break;
            }
        }

        btnWeNeedSmFilesNext.setClickable(true);
    }

    private void setDocumentsStatusDrawables(List<EstatusDocumentosResponse> mListaDocumentos) {
        ifeFront.setStatusImage(null);
        ifeBack.setStatusImage(null);
        addressFront.setStatusImage(null);
        addressBack.setStatusImage(null);
        btnWeNeedSmFilesNext.setVisibility(View.INVISIBLE);
        btnWeNeedSmFilesNext.setClickable(false);
        int idStatus;
        int tipoDoc;
        int idDrawableStatus;
        boolean isClickable;

        for (EstatusDocumentosResponse estatusDocs : mListaDocumentos) {
            idStatus = estatusDocs.getIdEstatus();
            tipoDoc = estatusDocs.getTipoDocumento();
            isClickable = idStatus == STATUS_DOCTO_RECHAZADO;

            if (idStatus == STATUS_DOCTO_RECHAZADO) {
                documentPendientes++;
                idDrawableStatus = R.drawable.ic_alerta2;
            } else if (idStatus == STATUS_DOCTO_APROBADO) {
                documentApproved++;
                idDrawableStatus = R.drawable.ic_document_done2;
            } else if (idStatus == STATUS_DOCTO_PENDIENTE) {
                idDrawableStatus = R.drawable.ic_wait2;
            } else {
                idDrawableStatus = R.drawable.ic_wait2;
            }

            switch (tipoDoc) {
                case DOC_ID_FRONT:
                    ifeFront.setClickable(isClickable);
                    ifeFront.setVisibilityStatus(true);
                    ifeFront.setBackgroundResource(idDrawableStatus);
                    break;
                case DOC_ID_BACK:
                    ifeBack.setClickable(isClickable);
                    ifeBack.setVisibilityStatus(true);
                    ifeBack.setBackgroundResource(idDrawableStatus);
                    break;
                case DOC_DOM_FRONT:
                    addressFront.setClickable(isClickable);
                    addressFront.setVisibilityStatus(true);
                    addressFront.setBackgroundResource(idDrawableStatus);
                    break;
                case DOC_DOM_BACK:
                    addressBack.setClickable(isClickable);
                    addressBack.setVisibilityStatus(true);
                    addressBack.setBackgroundResource(idDrawableStatus);
                    break;
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
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        if ((getParentFragment() == null || getParentFragment().isMenuVisible()) && onEventListener != null) {
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

    public void showDocumentRejected(EstatusDocumentosResponse mData, final int mPosition) {
        UI.createSimpleCustomDialogError(mData.getMotivo(), mData.getComentario(), getActivity().getSupportFragmentManager(), new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                switch (mPosition) {
                    case 0:
                        selectImageSource(IFE_FRONT);
                        break;
                    case 1:
                        selectImageSource(IFE_BACK);
                        break;
                    case 2:
                        selectImageSource(COMPROBANTE_FRONT);
                        break;
                    case 3:
                        selectImageSource(COMPROBANTE_BACK);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void actionCancel(Object... params) {

            }
        }, true, false, getString(R.string.adq_upload_again));
    }

    /**
     * Enviamos los documentos que fueron rechazados
     */
    private void sendDocumentsPending() {
        updateListFromMapHash();
        if (dataDocumnets.size() < documentPendientes) {
            UI.createSimpleCustomDialog("", getString(R.string.txt_dialogo_subir_documentos_marcados), getActivity().getSupportFragmentManager(),
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
        adqPresenter.sendDocumentosPendientes(dataDocumnetsServer);
    }


    /**
     * Enviamos los documentos cuando se esta registrando el adquirente
     */
    private void sendDocuments() {
        updateListFromMapHash();

        for (String s : imgs)
            if (s == null || s.isEmpty()) {
                showError(getString(R.string.adq_must_upload_documents));
                return;
            }
        adqPresenter.sendDocumentos(dataDocumnetsServer);
    }

    /**
     * Se encarga de llenar el dataDocumnetsServer que se envia al servidor, con los datos del
     * dataDocumnets que es un Map<K,V> asi evitamos problemas de cambiar constantemente imagenes
     */
    private void updateListFromMapHash() {
        dataDocumnetsServer.clear();

        if (dataDocumnets.get(IFE_FRONT) != null && !dataDocumnets.get(IFE_FRONT).equals("")) {
            dataDocumnetsServer.add(dataDocumnets.get(IFE_FRONT));
        }
        if (dataDocumnets.get(IFE_BACK) != null && !dataDocumnets.get(IFE_BACK).equals("")) {
            dataDocumnetsServer.add(dataDocumnets.get(IFE_BACK));
        }
        if (dataDocumnets.get(COMPROBANTE_FRONT) != null && !dataDocumnets.get(COMPROBANTE_FRONT).equals("")) {
            dataDocumnetsServer.add(dataDocumnets.get(COMPROBANTE_FRONT));
        }
        if (dataDocumnets.get(COMPROBANTE_BACK) != null && !dataDocumnets.get(COMPROBANTE_BACK).equals("")) {
            dataDocumnetsServer.add(dataDocumnets.get(COMPROBANTE_BACK));
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        refreshContent();
        hideStatusPhoto();
    }

    private void refreshContent() {
        if (getParentFragment() == null || getParentFragment().isMenuVisible()) {
            showLoader(getString(R.string.recuperando_docs_estatus));
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }
        adqPresenter.getEstatusDocs();
        lnr_buttons.setVisibility(GONE);
        swipeRefreshLayout.setEnabled(true);
    }
}

