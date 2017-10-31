package com.pagatodo.yaganaste.utils.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.ui.account.AccountAdqPresenter;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.ICropper;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.utils.BitmapBase64Listener;
import com.pagatodo.yaganaste.utils.BitmapLoader;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.pagatodo.yaganaste.App.getContext;
import static com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment.checkDuplicate;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_FRONT;

/**
 * Created by Francisco Manzo on 22/05/2017.
 * Manager que se encarga de Crear, hacer Set y regresar una foto de galeria o de camara
 */

public class CameraManager {
  /*  private static CameraManager ourInstance;

    public static CameraManager getInstance() {
        if(ourInstance == null){
            ourInstance = new CameraManager();

        }
        return ourInstance;
    }*/

    public static final int REQUEST_TAKE_PHOTO = 10; // Intent para Capturar fotografía
    public static final int SELECT_FILE_PHOTO = 20; // Intent para seleccionar fotografía
    public static final int CROP_RESULT = 30;
    private static final String TAG = DocumentosFragment.class.getSimpleName();
    private static final int USER_PHOTO = 1;
    private static final int IFE_BACK = 2;
    private static final int COMPROBANTE_FRONT = 3;
    private static final int COMPROBANTE_BACK = 4;
    private static Bitmap bitmapValue;
    CircleImageView iv_photo_item;
    Activity mContext;
    IListaOpcionesView mView;
    //    @BindView(R.id.progressLayout)
//    ProgressLayout progressLayout;
    private int documentProcessed = 0;
    private BitmapLoader bitmapLoader;
    private String imgs[] = new String[4];
    private ArrayList<String> contador;
    private ArrayList<DataDocuments> dataDocumnets;
    private AccountAdqPresenter adqPresenter;
    private Drawable mDrawable = null;
    private Preferencias pref;
    private Boolean mExisteDocs = false;
    private ICropper listener;
    private String path;
    private Uri uriImage;

    public CameraManager() {
    }

    public CameraManager(ICropper listener) {
        this.listener = listener;
    }

    public static Bitmap getBitmap() {
        return bitmapValue;
    }

    public static void setBitmap(Bitmap bitmap) {
        bitmapValue = bitmap;
    }

    public static void cleanBitmap() {
        bitmapValue = null;
    }

    /**
     * @param mContext      Recibe el contexto (getActivity)
     * @param iv_photo_item La imagen donde se procesa el resultado
     * @param mView         El Listener que recibira nuestra imagen para envia al servicio
     */
    public void initCamera(Activity mContext, CircleImageView iv_photo_item,
                           IListaOpcionesView mView) {
        this.mContext = mContext;
        contador = new ArrayList<>();
        dataDocumnets = new ArrayList<>();
        this.iv_photo_item = iv_photo_item;
        this.mView = mView;
    }


    public void initCameraUploadDocument(Activity mContext, UploadDocumentView imageViewCamera,
                                         IListaOpcionesView mView) {
        this.mContext = mContext;
        contador = new ArrayList<>();
        dataDocumnets = new ArrayList<>();
        this.iv_photo_item = imageViewCamera.getCircleImageView();
        this.mView = mView;
    }

    /**
     * Metodo principal que procesa la imagen
     *
     * @param intIntent
     */
    public void createPhoto(int intIntent) {

        selectImageSource(intIntent);
    }


    /*Agregamos selección de carrete*/
    private void selectImageSource(final int documentId) {
        final CharSequence[] items = {mContext.getString(R.string.action_take_picture),
                mContext.getString(R.string.action_select_picture),
                mContext.getString(R.string.action_select_picture_cancel)};
        LayoutInflater inflater = mContext.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_camera, null);

        LayoutInflater titleInflater = mContext.getLayoutInflater();
        View dialogTittle = titleInflater.inflate(R.layout.tittle_dialog, null);
        dialogTittle.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setCustomTitle(dialogTittle);
        dialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
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
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();
//        AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.setTitle("Seleccionar Fotografia");
//        alertDialog.setCancelable(false);
//        alertDialog.show();
    }

    private void takeGallery(int document) {
        documentProcessed = document;
        /*Intent intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentGallery.setType("image/*");
        mContext.startActivityForResult(Intent.createChooser(intentGallery, "Selecciona Archivo"), SELECT_FILE_PHOTO);
        */
        //enableItems(false);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent = Intent.createChooser(intent, "open");
        //startActivityForResult(intent, 1101);
        //mView.showProgress("");
        mContext.startActivityForResult(Intent.createChooser(intent, "Selecciona Archivo"), SELECT_FILE_PHOTO);
    }

   /* private void takeDocumentPicture(int document){
        documentProcessed = document;
        CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/" + "Camera/"+
                "file"+String.valueOf(System.currentTimeMillis())+".jpg");
        //uri = Uri.fromFile(file);
        uri = FileProvider.getUriForFile(MainActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                file);

        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        CamIntent.putExtra("return-data",true);
        startActivityForResult(CamIntent,0);
    }*/

    private void takeDocumentPicture(int document) {
        documentProcessed = document;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                //ex.printStackTrace();
                mView.showExceptionToView(ex.toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.e(TAG, "dispatchTakePictureIntent: " + photoFile);
                Uri photoURI = FileProvider.getUriForFile(mContext,
                        "com.pagatodo.yaganaste.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                List<ResolveInfo> resolvedIntentActivities = mContext.getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                    String packageName = resolvedIntentInfo.activityInfo.packageName;
                    mContext.grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }


                mContext.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
        // enableItems(false);
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
        mContext.sendBroadcast(mediaScanIntent);
    }


    public void setCropImage(Uri uriimage) {
        this.uriImage = uriimage;
        bitmapLoader = new BitmapLoader(mContext, uriimage.getPath(), new BitmapBase64Listener() {
            @Override
            public void onBegin() {
                mView.showProgress(getContext().getString(R.string.load_set_image));
            }

            @Override
            public void OnBitmap64Listener(Bitmap bitmap, String imgbase64) {
                saveBmpImgUser(bitmap, imgbase64);
            }
        });
        bitmapLoader.execute();

    }

    private void
    saveBmpImgUser(Bitmap bitmap, String imgBase64) {
        Boolean validateDuplicado;
        contador.add(imgBase64);
        validateDuplicado = checkDuplicate(contador);
        DataDocuments dataDoc = new DataDocuments();
        if (!validateDuplicado) {
            contador.remove(imgBase64);
            UI.showToast(App.getContext().getString(R.string.error_take_photo), getContext());
        } else {
            switch (documentProcessed) {
                case USER_PHOTO:
                    //iv_photo_item.setImageBitmap(bitmap);
                    //iv_photo_item.setVisibilityStatus(true);
                    //iv_photo_item.invalidate();
                    imgs[documentProcessed - 1] = imgBase64;
                    dataDoc.setTipoDocumento(DOC_ID_FRONT);
                    dataDoc.setImagenBase64(imgBase64);
                    dataDoc.setExtension("jpg");

                    //mView.showProgress(getContext().getString(R.string.load_set_image));
                    mView.setPhotoToService(bitmap);
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
     * Metodo que procesa la respuesta desde la actividad que es llamada. Se tiene que
     * hacer una llaamda a este metodo y enviar los parametros necesarios
     * PreferUserActivity -> ListaOpcionesFragment -> CameraManager
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void setOnActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            path = SingletonUser.getInstance().getPathPictureTemp();
            File f = new File(path);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            mContext.sendBroadcast(mediaScanIntent);

            // String path = SingletonUser.getInstance().getPathPictureTemp();
            bitmapLoader = new BitmapLoader(mContext, path, new BitmapBase64Listener() {
                @Override
                public void onBegin() {
                    mView.showProgress(getContext().getString(R.string.load_set_image));
                }

                @Override
                public void OnBitmap64Listener(Bitmap bitmap, String imgbase64) {
                    //enableItems(true);
                    saveBmpImgUser(bitmap, imgbase64);
                    //  hideLoader();
                }
            });

            //mView.showProgress(getContext().getString(R.string.load_set_image));
            this.listener.onCropper(contentUri);
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode != RESULT_OK) {
            // enableItems(true);
        } else if (requestCode == SELECT_FILE_PHOTO && resultCode == RESULT_OK && null != data) {
            Cursor cursor = null;
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            try {

                if (new File(selectedImage.getPath()).exists()) {
                    path = selectedImage.getPath();
                } else {
                    cursor = getContext().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndexOrThrow(filePathColumn[0]);
                    path = cursor.getString(columnIndex);
                }

                //startCropActivity(data.getData());

                //Proceso para obtener la imagen por medio de un contentResolver
                /*bitmapLoader = new BitmapLoader(mContext, path, new BitmapBase64Listener() {
                    @Override
                    public void OnBitmap64Listener(Bitmap bitmap, String imgbase64) {
                        saveBmpImgUser(bitmap, imgbase64);
                    }
                });
                bitmapLoader.execute();*/

                this.listener.onCropper(selectedImage);

            } catch (Exception e) {
                //e.printStackTrace();
                mView.showExceptionToView(e.toString());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if (requestCode == SELECT_FILE_PHOTO && resultCode != RESULT_OK && data == null) {

        } else if (resultCode == CROP_RESULT){

            this.listener.onHideProgress();
        }
    }


    public IListaOpcionesView getView(){
        return this.mView;
    }

    public Uri getUriImage() {
        return this.uriImage;
    }
}
