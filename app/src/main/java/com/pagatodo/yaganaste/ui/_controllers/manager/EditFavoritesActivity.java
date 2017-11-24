package com.pagatodo.yaganaste.ui._controllers.manager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.transition.Slide;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFotoFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DeleteFavoriteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EditFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosEditDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosNewDatosResponse;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ITextChangeListener;
import com.pagatodo.yaganaste.interfaces.OnListServiceListener;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.CropActivity;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IAddFavoritesActivity;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesPresenter;
import com.pagatodo.yaganaste.ui.addfavorites.presenters.FavoritesPresenter;
import com.pagatodo.yaganaste.ui.addfavorites.presenters.FavoritoPresenterAutoriza;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.otp.controllers.OtpView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.ICropper;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.utils.NumberCardTextWatcher;
import com.pagatodo.yaganaste.utils.NumberClabeTextWatcher;
import com.pagatodo.yaganaste.utils.NumberTagPase;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.ListServDialogFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;
import com.pagatodo.yaganaste.utils.customviews.carousel.CustomCarouselItem;
import com.squareup.picasso.Picasso;
import com.steelkiwi.cropiwa.image.CropIwaResultReceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB3;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CLABE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.IAVE_ID;
import static com.pagatodo.yaganaste.utils.Recursos.CURRENT_TAB;
import static com.pagatodo.yaganaste.utils.Recursos.IDCOMERCIO_YA_GANASTE;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;
import static com.pagatodo.yaganaste.utils.camera.CameraManager.CROP_RESULT;

/**
 * Encargada de dar de alta Favoritos, primero en el servicio y luego en la base local
 */
public class EditFavoritesActivity extends LoaderActivity implements IAddFavoritesActivity,
        IListaOpcionesView, ValidationForms, View.OnClickListener, OnListServiceListener,
        AdapterView.OnItemSelectedListener, ITextChangeListener, PaymentsCarrouselManager,
        ICropper, CropIwaResultReceiver.Listener, OtpView {

    public static final String TAG = EditFavoritesActivity.class.getSimpleName();
    public static final int CONTACTS_CONTRACT_LOCAL = 51;
    public static boolean BACK_STATE_EDITFAVORITE = true;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 101;

    @BindView(R.id.add_favorites_alias)
    CustomValidationEditText editAlias;
    @BindView(R.id.add_favorites_alias_error)
    ErrorMessage editAliasError;
    @BindView(R.id.add_favorites_list_serv)
    CustomValidationEditText editListServ;
    @BindView(R.id.add_favorites_list_serv_error)
    ErrorMessage editListServError;
    @BindView(R.id.add_favorites_linear_tipo)
    LinearLayout linearTipo;
    @BindView(R.id.add_favorites_tipo)
    CustomValidationEditText editTipo;
    @BindView(R.id.add_favorites_tipo_error)
    ErrorMessage editTipoError;
    @BindView(R.id.add_favorites_referencia)
    CustomValidationEditText editRefer;
    @BindView(R.id.add_favorites_referencia_error)
    ErrorMessage editReferError;
    @BindView(R.id.add_favorites_camera)
    UploadDocumentView imageViewCamera;
    @BindView(R.id.recargaNumber)
    EditText recargaNumber;
    @BindView(R.id.layoutImageContact)
    RelativeLayout layoutImageContact;
    @BindView(R.id.layoutImageContact2)
    RelativeLayout layoutImageContact2;
    @BindView(R.id.referenceNumber)
    EditText referenceNumber;
    @BindView(R.id.layoutImageReference)
    RelativeLayout layoutImageReference;
    @BindView(R.id.tipoEnvio)
    Spinner tipoEnvio;
    @BindView(R.id.cardNumber)
    StyleEdittext cardNumber;
    @BindView(R.id.layout_cardNumber)
    LinearLayout layout_cardNumber;
    @BindView(R.id.add_favorites_spinner_et)
    CustomValidationEditText editSpinner;
    @BindView(R.id.tipo_envio_error)
    ErrorMessage editSpinnerError;
    @BindView(R.id.add_favorites_foto_et)
    CustomValidationEditText editFoto;
    @BindView(R.id.add_favorites_foto_error)
    ErrorMessage editFotoError;
    @BindView(R.id.imgItemGalleryMark)
    CircleImageView circuloimage;
    @BindView(R.id.imgItemGalleryStatus)
    CircleImageView circuloimageupload;
    @BindView(R.id.layoutImg)
    RelativeLayout relativefav;
    AppCompatImageView btn_back;
    IFavoritesPresenter favoritesPresenter;
    DataFavoritos dataFavoritos;
    DataFavoritos dataFavoritosLimpiar;
    int idTipoComercio, idComercio, idFavorito, idTipoEnvio, tipoTab, longitudRefer, keyIdComercio,
            maxLength, current_tab, longRefer;
    String stringFoto, nombreDest, mReferencia, tabName, formatoComercio = "", nombreComercio;
    CameraManager cameraManager;
    private boolean errorIsShowed = false, showRefEnvio, isIAVE;
    ArrayList<CustomCarouselItem> backUpResponse;
    TransferType selectedType;
    MovementsTab current_tab2;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    private TextWatcher currentTextWatcher, currentTextWatcherPDS;
    private static Preferencias preferencias = App.getInstance().getPrefs();
    private FavoritoPresenterAutoriza favoritoPresenterAutoriza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide());
            getWindow().setExitTransition(new Slide());
        }
        setContentView(R.layout.activity_edit_favorites);

        favoritesPresenter = new FavoritesPresenter(this);
        btn_back = (AppCompatImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        favoritoPresenterAutoriza = new FavoritoPresenterAutoriza(this, this);


        dataFavoritos = (DataFavoritos) getIntent().getExtras().get(getString(R.string.favoritos_tag));

        idComercio = (int) dataFavoritos.getIdComercio();
        idTipoComercio = dataFavoritos.getIdTipoComercio();
        nombreComercio = dataFavoritos.getNombreComercio();
        mReferencia = dataFavoritos.getReferencia();
        nombreDest = dataFavoritos.getNombre();
        current_tab = getIntent().getIntExtra(CURRENT_TAB, 96);
        idFavorito = (int) dataFavoritos.getIdFavorito();

        // Iniciamos el presentes del carrousel
        this.current_tab2 = MovementsTab.getMovementById(current_tab);
        backUpResponse = new ArrayList<>();
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(this.current_tab2, this, this, false);
        paymentsCarouselPresenter.getCarouselItems();
        ButterKnife.bind(this);
        //  imageViewCamera.setVisibilityStatus(true);
        // imageViewCamera.setStatusImage(ContextCompat.getDrawable(this, R.drawable.camara_white_blue_canvas));

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthp = metrics.widthPixels; // ancho absoluto en pixels
        int paramentroT = widthp / 3;
        int paramentroimgc = paramentroT / 4;
        int distancia = paramentroT - paramentroimgc;

        ImageView deleteFav = (ImageView) findViewById(R.id.delete_fav);
        deleteFav.setVisibility(View.VISIBLE);
        deleteFav.setOnClickListener(this);

        imageViewCamera.setVisibilityStatus(true);
        imageViewCamera.setStatusImage(ContextCompat.getDrawable(this, R.drawable.camara_white_blue_canvas));
        circuloimage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_usuario_azul));
        RelativeLayout.LayoutParams paramsc = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsc.setMargins(distancia, 30, 0, 0);
        paramsc.width = paramentroimgc;
        paramsc.height = paramentroimgc;
        circuloimageupload.setLayoutParams(paramsc);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.width = paramentroT;
        params.height = paramentroT;
        relativefav.setLayoutParams(params);

        /**
         * Iniciamos los cambos de EditTExt Sencillos
         */
        editAlias.setText(nombreDest);
        editListServ.setText(nombreComercio);

        /**
         * Le damos formato al tipo de pago
         */
        String formatoPago = mReferencia;

        // Sacamos la logintud de la referencia antes del formato
        longRefer = mReferencia.length();

        // Boolean para mostrar la referencia por 1era vez en Envio
        showRefEnvio = true;

        if (current_tab == 1) {
            if (idComercio != 7) {
                formatoPago = StringUtils.formatoPagoMedios(formatoPago);
            }
            if (idComercio == 7) {
                formatoPago = StringUtils.formatoPagoMediostag(formatoPago);
            }
            // textViewServ.setHintText(getString(R.string.details_compania));
        } else if (current_tab == 2) {
            formatoPago = StringUtils.genericFormat(formatoPago, SPACE);
            //  textViewServ.setHintText(getString(R.string.details_compania));
        } else if (current_tab == 3) {
            if (formatoPago.length() == 16 || formatoPago.length() == 15) {
                formatoPago = StringUtils.maskReference(StringUtils.format(formatoPago, SPACE, 4, 4, 4, 4), '*', formatoPago.length() - 12);
            } else {
                formatoPago = StringUtils.formatoPagoMedios(formatoPago);
            }
            // textViewServ.setHintText(getString(R.string.details_bank));
        }

        /**
         * Mostramos el area de referencia que sea necesario al hacer Set en un servicio
         * Esto se controlar con la posicion del Tab que seleccionamos
         */
        if (current_tab == 1) {
            LinearLayout taeLL = (LinearLayout) findViewById(R.id.add_favorites_tae_ll);
            taeLL.setVisibility(View.VISIBLE);

            initFormatoLogitud();
            initTAERefer();
            recargaNumber.setText(formatoPago);
        } else if (current_tab == 2) {
            LinearLayout taeLL = (LinearLayout) findViewById(R.id.add_favorites_serv_ll);
            taeLL.setVisibility(View.VISIBLE);

            initFormatoLogitud();
            initPDSRefer();
            referenceNumber.setText(formatoPago);
        } else if (current_tab == 3) {
            //  LinearLayout taeLL = (LinearLayout) findViewById(R.id.add_favorites_envio_ll);
            //   taeLL.setVisibility(View.VISIBLE);

            //longRefer
            switch (longRefer) {
                case 10:
                    idTipoEnvio = 1;
                    break;
                case 16:
                    idTipoEnvio = 2;
                    break;
                case 18:
                    idTipoEnvio = 3;
                    break;
            }

            initEnviosPrefer();

            tipoEnvio.setSelection(idTipoEnvio);
            cardNumber.setText(formatoPago);
        }


        // Funcionalidad para agregar el Spinner
        editListServ.imageViewIsGone(false);
        editListServ.setEnabled(false);
        editListServ.setFullOnClickListener(this);
       /*
        REVISAR funcionalidad on servicios
       if(current_tab2.getId()==1){
            editListServ.setHintText(getString(R.string.details_compania));
        } else if (current_tab2.getId() ==2){
            editListServ.setHintText(getString(R.string.details_compania));
        } else if (current_tab2.getId() == 3){
            editListServ.setHintText(getString(R.string.details_bank));
        }*/

        // Iniciamos la funcionalidad e la camara
        cameraManager = new CameraManager(this);
        cameraManager.initCameraUploadDocument(this, imageViewCamera, this);

        //Bloqueamos la edicion de referencia hasta que tengamos ya un servicio de la lista
        editRefer.setVisibility(View.GONE);
        editReferError.setVisibility(View.VISIBLE);
        layout_cardNumber.setVisibility(GONE);
        //editSpinnerError.setVisibility(View.GONE);

        // Hacemos Set de Reglas de validacion
        setValidationRules();

        // Hacemos Set de eventos ONCLICK
        layoutImageContact.setOnClickListener(this);
        layoutImageContact2.setOnClickListener(this);

        // Test de SET de todos los EditTExt, eliminar cuando tengamos servicios funcional
        // editAlias.setText("MiReferencia");
        // editListServ.setText("TelMex");
        editTipo.setText("Envio por CLABE");
        // editRefer.setText("5534812287");

        // Agregamos Flecha de Shebrom
        editListServ.setEnabled(false);
        editListServ.setFullOnClickListener(this);
        editListServ.setDrawableImage(R.drawable.menu_canvas);

    /*    Glide.with(this)
                .load(dataFavoritos.getImagenURL())
                .asBitmap()
                .into(imageViewCamera.getCircleImageView());*/



        /**
         * Esta validacion es debido a que Piccaso marca un NullPoint si la URL esta vacia, pero
         * Glide permite falla y cargar un PlaceHolder
         */
        String url = dataFavoritos.getImagenURL();
        if(url != null && !url.isEmpty()) {
            Picasso.with(this)
                    .load(dataFavoritos.getImagenURL())
                    .error(R.drawable.ic_usuario_azul)
                    //.placeholder(R.drawable.user_placeholder)
                    //.error(R.drawable.user_placeholder_error)
                    .into(imageViewCamera.getCircleImageView());
        }else{
            Glide.with(this)
                    .load(dataFavoritos.getImagenURL())
                    .asBitmap()
                    .into(imageViewCamera.getCircleImageView());
        }

        CropIwaResultReceiver cropResultReceiver = new CropIwaResultReceiver();
        cropResultReceiver.setListener(this);
        cropResultReceiver.register(this);
    }

    /**
     * Hacemos SET de las validaciones en "vivo" cuando entramos al CustomEditTExt o Salimos del mismo
     */
    @Override
    public void setValidationRules() {
        editAlias.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editAlias.getId());
                    editAlias.imageViewIsGone(true);
                } else {
                    if (editAlias.getText().isEmpty()) {
                        showValidationError(editAlias.getId(), getString(R.string.addFavoritesErrorAlias));
                        editAlias.setIsInvalid();
                    } else {
                        hideValidationError(editAlias.getId());
                        editAlias.setIsValid();
                    }
                }
            }
        });

        /**
         * Asiganmos un FocusListenr dependiendo del Tab de referencia que tengamos listo
         */
        if (current_tab == 1) {
            recargaNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // Hacemos SET del contenido de recargaNumber a editRefer SIEMPRE que cambiemos foco
                    editRefer.setText(recargaNumber.getText().toString());
                    if (hasFocus) {
                        hideValidationError(editRefer.getId());
                        editRefer.imageViewIsGone(true);
                    } else {
                        if (editRefer.getText().isEmpty()) {
                            showValidationError(editRefer.getId(), getString(R.string.addFavoritesErrorRefer));
                            editRefer.setIsInvalid();
                        } else {
                            hideValidationError(editRefer.getId());
                            editRefer.setIsValid();
                        }
                    }
                }
            });
        } else if (current_tab == 2) {
            referenceNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // Hacemos SET del contenido de recargaNumber a editRefer SIEMPRE que cambiemos foco
                    editRefer.setText(referenceNumber.getText().toString());
                    if (hasFocus) {
                        hideValidationError(editRefer.getId());
                        editRefer.imageViewIsGone(true);
                    } else {
                        if (editRefer.getText().isEmpty()) {
                            showValidationError(editRefer.getId(), getString(R.string.addFavoritesErrorRefer));
                            editRefer.setIsInvalid();
                        } else {
                            hideValidationError(editRefer.getId());
                            editRefer.setIsValid();
                        }
                    }
                }
            });
        } else if (current_tab == 3) {
            cardNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // Hacemos SET del contenido de recargaNumber a editRefer SIEMPRE que cambiemos foco
                    editRefer.setText(referenceNumber.getText().toString());
                    if (hasFocus) {
                        hideValidationError(editRefer.getId());
                        editRefer.imageViewIsGone(true);
                    } else {
                        if (editRefer.getText().isEmpty()) {
                            showValidationError(editRefer.getId(), getString(R.string.addFavoritesErrorRefer));
                            editRefer.setIsInvalid();
                        } else {
                            hideValidationError(editRefer.getId());
                            editRefer.setIsValid();
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }


    // Disparamos el evento de Camara solo si tenemos intrnet
    @OnClick(R.id.add_favorites_camera)
    public void openCamera() {
        boolean isValid = true;

        int permissionCamera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int permissionStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
        if (permissionCamera == -1) {
            ValidatePermissions.checkPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
            isValid = false;
        }

        // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
        if (permissionStorage == -1) {
            ValidatePermissions.checkPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_STORAGE);
            isValid = false;
        }

        if(isValid){
            favoritesPresenter.openMenuPhoto(1, cameraManager);
        }
    }

    // Cerramos esta actividad de favoritos
    @OnClick(R.id.btnCloseAddFavorites)
    public void closeAddFavoritos() {
        finish();
    }

    //Disparamos la validacion, si es exitosa, entonces iniciamos el proceso de actualizar favoritos
    @OnClick(R.id.btnSendAddFavoritos)
    public void sendAddFavoritos() {
        // Toast.makeText(this, "Open Presenter", Toast.LENGTH_SHORT).show();
        validateForm();
        getIntent().removeExtra(getString(R.string.favoritos_tag));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_favorites_list_serv:
                /**
                 * 1 - Creamos nuestro Dialog Fragment Custom que mostrar la lista de Servicios
                 * 2 - HAcemos SET de la interfase OnListServiceListener, con e metodo setOnList...
                 * asi al hacer clic en algun elemento nos dara la respuesta
                 * 3 - Mostramos el dialogo
                 */
                ListServDialogFragment dialogFragment = ListServDialogFragment.newInstance(backUpResponse);
                dialogFragment.setOnListServiceListener(this);
                dialogFragment.show(getSupportFragmentManager(), "FragmentDialog");
                break;
            /**
             * Tomamos el telefono de la agenda para TAE
             */
            case R.id.layoutImageContact:
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                this.startActivityForResult(contactPickerIntent, CONTACTS_CONTRACT);
                break;
            /**
             * Tomamos el telefono de la agenda para Envios
             */
            case R.id.layoutImageContact2:
                Intent contactPickerIntent2 = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                this.startActivityForResult(contactPickerIntent2, CONTACTS_CONTRACT_LOCAL);
                break;

            case R.id.layoutImageReference:
                Intent intent = new Intent(this, ScannVisionActivity.class);
                this.startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
                break;
            case R.id.delete_fav:
                UI.createSimpleCustomDialog(getString(R.string.delete_fav_title), getString(R.string.delete_fav_text),
                        getSupportFragmentManager(), new DialogDoubleActions() {
                            @Override
                            public void actionConfirm(Object... params) {
                                DeleteFavoriteRequest deleteFavoriteRequest = new DeleteFavoriteRequest();
                                favoritesPresenter.toPresenterDeleteFavorite(deleteFavoriteRequest, idFavorito);
                            }

                            @Override
                            public void actionCancel(Object... params) {
                            }
                        }, true, true);
                break;
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * Se encarga de tomar el contacto seleccionado y hacer Set en el CustomEditTExt
     *
     * @param data
     * @param tipoNumero
     */
    private void contactPicked(Intent data, int tipoNumero) {
        Cursor cursor;
        String phoneNo = null;
        Uri uri = data.getData();
        cursor = this.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //get column index of the Phone Number
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            //int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex).replaceAll("\\s", "").replaceAll("\\+", "").replaceAll("-", "").trim();
            if (phoneNo.length() > 10) {
                phoneNo = phoneNo.substring(phoneNo.length() - 10);
            }
        }

        // Hacemos set en el elemento, ya sea de TAE o de Envios
        if (tipoNumero == 1) {
            recargaNumber.setText(phoneNo);
        } else {
            cardNumber.setText(phoneNo);
        }
    }


    /**
     * RESPUESTAS del Servidor
     */

    /**
     * Error de algun tipo, ya sea de proceso de servidor o de conexion
     *
     * @param mMensaje
     */
    @Override
    public void toViewErrorServer(String mMensaje) {
        showDialogMesage("", mMensaje, 0);
    }

    /**
     * Exito en agregar a Favoritos
     *
     * @param mResponse
     */
    @Override
    public void toViewSuccessAdd(FavoritosNewDatosResponse mResponse) {
    }

    @Override
    public void toViewSuccessAddFoto(String mMensaje) {
        //  showDialogMesage("",mMensaje, 1); Viejpo mensaje
        showDialogMesage(getString(R.string.title_dialog_edit_favorite),
                getString(R.string.respond_ok_edit_favorite), 1);
    }

    @Override
    public void toViewSuccessAdd(FavoritosDatosResponse response) {
    }

    @Override
    public void toViewSuccessDeleteFavorite(String mMensaje) {
        showDialogMesage(getString(R.string.title_dialog_delete_favorite), getString(R.string.respond_ok_delete_favorite), 1);
    }

    @Override
    public void toViewSuccessEdit(FavoritosEditDatosResponse response) {
        // showDialogMesage(mMensaje, 1);
        if (stringFoto != null) {
            AddFotoFavoritesRequest addFotoFavoritesRequest =
                    new AddFotoFavoritesRequest(stringFoto, "png");
            favoritesPresenter.toPresenterAddFotoFavorites(addFotoFavoritesRequest, idFavorito);

        } else {
            favoritesPresenter.updateLocalFavorite(dataFavoritos);
            showDialogMesage(getString(R.string.title_dialog_edit_favorite),
                    getString(R.string.respond_ok_edit_favorite), 1);
        }
    }

    private void showDialogMesage(final String title, final String mensaje, final int closeAct) {
        UI.createSimpleCustomDialog(title, mensaje, getSupportFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        if (closeAct == 1) {
                            /**
                             * Regresamos el exito como un OK a nuestra actividad anterior para
                             * ocultar el icono de agregar
                             */
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

    /**
     * Resultado de tomar una foto o escoger una de galeria, se envia el resultado al CameraManager
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraManager.setOnActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CONTACTS_CONTRACT) {
                contactPicked(data, 1);
                // Ocultamos el mensaje de error si esta visible
                editReferError.setVisibilityImageError(false);
            }
        }

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CONTACTS_CONTRACT_LOCAL) {
                contactPicked(data, 2);
                // Ocultamos el mensaje de error si esta visible
                editReferError.setVisibilityImageError(false);
            }
        }

        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    referenceNumber.setText(barcode.displayValue);
                    // Ocultamos el mensaje de error si esta visible
                    editReferError.setVisibilityImageError(false);
                }
            }
        }
    }

    /**
     * Resultado de procesar la imagen de la camara, aqui ya tenemos el Bitmap, y el codigo siguoente
     * es la BETA para poder darlo de alta en el servicio
     *
     * @param bitmap
     */
    @Override
    public void setPhotoToService(Bitmap bitmap) {
        // Log.d("TAG", "setPhotoToService ");
        try {
            /*Glide.with(this)
                    .load(cameraManager.getUriImage())
                    .asBitmap()
                    .into(imageViewCamera.getCircleImageView());*/
            Picasso.with(this)
                    .load(cameraManager.getUriImage())
                    .into(imageViewCamera.getCircleImageView());

            Bitmap bitmapAux = MediaStore.Images.Media.getBitmap(this.getContentResolver(), cameraManager.getUriImage());
            // Procesamos el Bitmap a Base64
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapAux.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            stringFoto = Base64.encodeToString(byteArray, Base64.DEFAULT);
            editFoto.setText(stringFoto);

            // Ocultamos el mensaje de error de foto
            editFotoError.setVisibilityImageError(false);

            //CODIGO ORIGINAL DE FOTO EN ListaOpcionesFRagment
            // Creamos el objeto ActualizarAvatarRequest
            // ActualizarAvatarRequest avatarRequest = new ActualizarAvatarRequest(encoded, "png");

            // onEventListener.onEvent("DISABLE_BACK", true);

            // Enviamos al presenter
            // mPreferPresenter.sendPresenterActualizarAvatar(avatarRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BACK_STATE_EDITFAVORITE = true;
        hideLoader();
    }

    /**
     * Validador de formulario, aqui tenemos la logica para llamar a los metodos de mostrar los errores
     */
    @Override
    public void validateForm() {
        boolean isValid = true;

        //Validate format Email
        if (!editAlias.isValidText()) {
            showValidationError(editAlias.getId(), getString(R.string.addFavoritesErrorAlias));
            editAlias.setIsInvalid();
            isValid = false;
            //return;
        }

        //Validate format Servicios
        if (!editListServ.isValidText()) {
            showValidationError(editListServ.getId(), getString(R.string.addFavoritesErrorServ));
            editListServ.setIsInvalid();
            isValid = false;
            //return;
        }

        //Validate format Tipo Envio
        if (!editTipo.isValidText()) {
            showValidationError(editTipo.getId(), getString(R.string.addFavoritesErrorEnvio));
            editTipo.setIsInvalid();
            isValid = false;
            //return;
        }

        /** Hacemos set de la validacion de referencia dependiendo del tipo de TAB */

        if (current_tab == 1) {
            editRefer.setText(recargaNumber.getText().toString());
            //Validate format Referencia
            if (!editRefer.isValidText()) {
                showValidationError(editRefer.getId(), getString(R.string.addFavoritesErrorRefer));
                editRefer.setIsInvalid();
                isValid = false;
                //return;
            }
        } else if (current_tab == 2) {
            editRefer.setText(referenceNumber.getText().toString());
            //Validate format Referencia
            if (!editRefer.isValidText()) {
                showValidationError(editRefer.getId(), getString(R.string.addFavoritesErrorRefer));
                editRefer.setIsInvalid();
                isValid = false;
                //return;
            }
        } else if (current_tab == 3) {
            //Validate format Spinner
            if (!editSpinner.isValidText()) {
                showValidationError(editSpinner.getId(), getString(R.string.addFavoritesErrorEnvio));
                editSpinner.setIsInvalid();
                isValid = false;
                //return;
            }

            editRefer.setText(cardNumber.getText().toString());
            //Validate format Referencia
            if (!editRefer.isValidText()) {
                showValidationError(editRefer.getId(), getString(R.string.addFavoritesErrorRefer));
                editRefer.setIsInvalid();
                isValid = false;
                //return;
            }
        }
/*
        //Validate format Tipo Envio
        if (!editFoto.isValidText()) {
            showValidationError(editFoto.getId(), getString(R.string.addFavoritesErrorFoto));
            editFoto.setIsInvalid();
            isValid = false;
            //return;
        }*/

        if (isValid) {
            boolean isOnline = Utils.isDeviceOnline();
            if (isOnline) {
                onValidationSuccess();
            } else {
                showDialogMesage("", getResources().getString(R.string.no_internet_access), 0);
            }
        }
    }

    /**
     * Se encarga de mostrar el error en el campo de Error necesario
     *
     * @param id
     * @param error
     */
    @Override
    public void showValidationError(int id, Object error) {

        switch (id) {
            case R.id.add_favorites_alias:
                editAliasError.setMessageText(error.toString());
                break;
            case R.id.add_favorites_list_serv:
                editListServError.setMessageText(error.toString());
                break;
            case R.id.add_favorites_tipo:
                editTipoError.setMessageText(error.toString());
                break;
            case R.id.add_favorites_referencia:
                editReferError.setMessageText(error.toString());
                break;
            case R.id.add_favorites_spinner_et:
                editSpinnerError.setMessageText(error.toString());
                break;
            case R.id.add_favorites_foto_et:
                editFotoError.setMessageText(error.toString());
                break;
        }

        errorIsShowed = true;
    }

    /**
     * Se encarga de ocultar el error en el campo necesario
     *
     * @param id
     */
    @Override
    public void hideValidationError(int id) {
        switch (id) {
            case R.id.add_favorites_alias:
                editAliasError.setVisibilityImageError(false);
                break;
            case R.id.add_favorites_servicio:
                editListServError.setVisibilityImageError(false);
                break;
            case R.id.add_favorites_tipo:
                editTipoError.setVisibilityImageError(false);
                break;
            case R.id.add_favorites_referencia:
                editReferError.setVisibilityImageError(false);
                break;
            case R.id.add_favorites_spinner_et:
                editSpinnerError.setVisibilityImageError(false);
                break;
            case R.id.add_favorites_foto_et:
                editFotoError.setVisibilityImageError(false);
                break;
        }
        //errorMessageView.setVisibilityImageError(false);
        errorIsShowed = false;
    }

    /**
     * Cuando la validacion es exitosa, enviamos el objeto Request a nuestra peticion
     */
    @Override
    public void onValidationSuccess() {
        editAliasError.setVisibilityImageError(false);
        editListServError.setVisibilityImageError(false);
        editTipoError.setVisibilityImageError(false);
        editReferError.setVisibilityImageError(false);

        String mAlias = editAlias.getText().toString();
        mReferencia = editRefer.getText().toString();
        String referService = StringUtils.formatCardToService(mReferencia);

        // stringFoto Poner el String de foto cuando el servicio no se muera
        EditFavoritesRequest addFavoritesRequest = new EditFavoritesRequest(idTipoComercio, idTipoEnvio,
                idComercio, mAlias, referService, "");
        dataFavoritos.setIdTipoComercio(idTipoComercio);
        dataFavoritos.setIdComercio(idComercio);
        dataFavoritos.setNombre(mAlias);
        dataFavoritos.setReferencia(referService);
        favoritoPresenterAutoriza.generateOTP(preferencias.loadData("SHA_256_FREJA"));
        favoritesPresenter.toPresenterEditNewFavorites(addFavoritesRequest, idFavorito);
        /* Si no tiene un favorito guardado con la misma referencia entonces se permite editarlo
        if (!favoritesPresenter.alreadyExistFavorite(referService)) {

        //} else {
        /*  En caso de que ya exista un favorito con la misma referencia entonces muestra un Diálogo
            UI.createSimpleCustomDialog(getString(R.string.title_error), getString(R.string.error_favorite_exist), getSupportFragmentManager(),
                    "");
        }/*

        // Codigo para mostrar el llenado de la peticion
    /*    Toast.makeText(this, "Validacion exitosa, ver log para datos", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Alias " + mAlias
                + " idTipoComercio " + idTipoComercio
                + " idComercio " + idComercio
                + " idTipoEnvio " + idTipoEnvio
                + " mReferencia " + referService
                + " stringFoto " + stringFoto);*/
    }

    @Override
    public void getDataForm() {
    }

    /**
     * Encargada de reaccionar al codigo de pusacion KEYCODE_CALL=5 para cerrar el teclado
     *//*
    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            // Toast.makeText(App.getContext(), "Click Code: " + actionId, Toast.LENGTH_SHORT).show();
            if (actionId == KeyEvent.KEYCODE_CALL) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextAlias.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                return true;

            }
            return false;
        }
    }*/
    @Override
    public void showProgress(String mMensaje) {
        showLoader(getString(R.string.load_photo_favorite));
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


    /**
     * Listener que efectua varias tareas cuando se selecciona un servicio de la lista, dependiendo
     * del TAB realiza diversas funciones
     *
     * @param item
     */
    @Override
    public void onListServiceListener(CustomCarouselItem item) {
        //  Toast.makeText(this, "Item " + item.getNombreComercio(), Toast.LENGTH_SHORT).show();
        editListServ.setText(item.getNombreComercio());
        idTipoComercio = item.getIdTipoComercio();
        idComercio = item.getIdComercio();
        editListServError.setVisibilityImageError(false);

        // Borramos los textos de los campos de refrencia de todos los tispos
        recargaNumber.setText("");
        referenceNumber.setText("");

        /**
         * HAbilitamos el que se muestre el mensaje de error en la referencia, si es necesiario,
         * pero solo en TAB 1 y 2, para TAB 3 se muestra en otro proceso
         */
        if (current_tab != 3) {
            editReferError.setVisibility(View.VISIBLE);
        }

        // Variables necesarioas para agregar el formato de captura de telefono o referencia
        formatoComercio = item.getFormatoComercio();
        longitudRefer = item.getLongitudRefer();

        /**
         * Mostramos el area de referencia que sea necesario al hacer Set en un servicio
         * Esto se controlar con la posicion del Tab que seleccionamos
         */
        if (current_tab == 1) {
            LinearLayout taeLL = (LinearLayout) findViewById(R.id.add_favorites_tae_ll);
            taeLL.setVisibility(View.VISIBLE);

            initTAERefer();
        } else if (current_tab == 2) {
            LinearLayout taeLL = (LinearLayout) findViewById(R.id.add_favorites_serv_ll);
            taeLL.setVisibility(View.VISIBLE);

            initPDSRefer();
        } else if (current_tab == 3) {
            //  LinearLayout taeLL = (LinearLayout) findViewById(R.id.add_favorites_envio_ll);
            //   taeLL.setVisibility(View.VISIBLE);
            initEnviosPrefer();
        }
    }

    /**
     * Procedimientos especificos para la referencia por via TAE
     */
    private void initTAERefer() {
        isIAVE = idComercio == IAVE_ID;

        if (formatoComercio.equals("N")) {
            recargaNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
            recargaNumber.setSingleLine();
        } else {
            recargaNumber.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            recargaNumber.setSingleLine();
        }

        int longitudReferencia = longitudRefer != 0 ? longitudRefer : 10;

        InputFilter[] fArray = new InputFilter[2];
        maxLength = Utils.calculateFilterLength(longitudReferencia);
        fArray[0] = new InputFilter.LengthFilter(maxLength);

        if (currentTextWatcher != null) {
            recargaNumber.removeTextChangedListener(currentTextWatcher);
        }

        if (isIAVE) {
            currentTextWatcher = new NumberTagPase(recargaNumber, maxLength);
            recargaNumber.setHint(getString(R.string.tag_number) + " (" + longitudReferencia + " Dígitos)");
            layoutImageContact.setVisibility(View.GONE);
        } else {
            currentTextWatcher = new PhoneTextWatcher(recargaNumber);
            recargaNumber.setHint(getString(R.string.phone_number_hint));

            layoutImageContact.setOnClickListener(this);
        }


        fArray[1] = new InputFilter() {
            public CharSequence filter(CharSequence src, int start,
                                       int end, Spanned dst, int dstart, int dend) {
                if (src.toString().matches("[a-zA-Z0-9 ]+")) {
                    return src;
                }
                return "";
            }
        };
        recargaNumber.setFilters(fArray);

        recargaNumber.addTextChangedListener(currentTextWatcher);

        recargaNumber.setSingleLine(true);
        recargaNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_DONE) {
                    //   UI.hideKeyBoard(getActivity());
                }
                return false;
            }
        });
    }

    /**
     * Procedimientos especificos para la referencia por via PDS
     */
    private void initPDSRefer() {

        /**
         * Eliminamos el TExt Watcher de PDS si es diferente de null
         */
        if (currentTextWatcherPDS != null) {
            referenceNumber.removeTextChangedListener(currentTextWatcherPDS);
        }

        layoutImageReference.setOnClickListener(this);
        if (longitudRefer > 0) {
            InputFilter[] fArray = new InputFilter[1];
            maxLength = Utils.calculateFilterLength(longitudRefer);
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            referenceNumber.setFilters(fArray);
        }

        if (formatoComercio.equals("AN")) {
            referenceNumber.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        /**
         * Creamos el nuevo TextWatcher de currentTextWatcherPDS y lo agregamos
         * a referenceNumber
         */
        currentTextWatcherPDS = new NumberTagPase(referenceNumber, maxLength);
        referenceNumber.addTextChangedListener(currentTextWatcherPDS);
    }

    /**
     * Procedimientos para inicializar el FormatoComercio y logitudRefer
     */
    private void initFormatoLogitud() {

        // backUpResponse
        for (CustomCarouselItem customCarouselItem : backUpResponse) {
            if (customCarouselItem.getNombreComercio().equals(nombreComercio)) {
                formatoComercio = customCarouselItem.getFormatoComercio();
                longitudRefer = customCarouselItem.getLongitudRefer();
            }
        }
        Log.d(TAG, "Log");


       /* layoutImageReference.setOnClickListener(this);
        if (longitudRefer > 0) {
            InputFilter[] fArray = new InputFilter[1];
            maxLength = Utils.calculateFilterLength(longitudRefer);
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            referenceNumber.setFilters(fArray);
        }

        if (formatoComercio.equals("AN")) {
            referenceNumber.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        referenceNumber.addTextChangedListener(new NumberTagPase(referenceNumber, maxLength));*/
    }

    /**
     * Procedimientos especificos para la referencia por via Envios
     */
    private void initEnviosPrefer() {
        tipoEnvio.setVisibility(View.VISIBLE);
        keyIdComercio = idComercio;

        List<String> tipoPago = new ArrayList<>();

        tipoPago.add(0, "");
        tipoPago.add(NUMERO_TELEFONO.getId(), NUMERO_TELEFONO.getName(this));
        tipoPago.add(NUMERO_TARJETA.getId(), NUMERO_TARJETA.getName(this));

        if (keyIdComercio != IDCOMERCIO_YA_GANASTE) {
            tipoPago.add(CLABE.getId(), CLABE.getName(this));
        }

        /**
         * Agregamos el Adapter especial al Spinner para tener los 2 o 3 estados dependiendo del
         * keyIdComercio
         */
        SpinnerArrayAdapter dataAdapter = new SpinnerArrayAdapter(this, TAB3, tipoPago);
        tipoEnvio.setAdapter(dataAdapter);
        tipoEnvio.setOnItemSelectedListener(this);

        // Mostramos el campo de error aunque este vacio, para futura validacion
        editSpinnerError.setVisibility(View.VISIBLE);
    }

    /**
     * Evento que sucede cuando escogemos un Item del Spinner. Recuerda que al iniciar la actividad
     * para por default una vez en este onItemSelected
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        layout_cardNumber.setVisibility(View.VISIBLE);
        cardNumber.removeTextChangedListener();

        // Hacemos el Set de la informacion del Spinner en un campo que servira como validador
        if (position == 0) {
            editSpinner.setText("");
            editReferError.setVisibility(View.INVISIBLE);
        } else {
            editSpinner.setText("" + position);

            /*
             Si showRefEnvio es true mostramos una vez la referencia original, en elecciones
             posterioes, cambiamos a false y siempre borramos el campo de referencia            // despues en
            */
            if (showRefEnvio == true) {
                showRefEnvio = false;
            } else {
                cardNumber.setText("");
            }

            // Ocultamos el mensaje de error si ya escogimos un vaor de Envio
            editSpinnerError.setVisibilityImageError(false);

            // Mostramos el error de referencia para notificarle al usuario
            editReferError.setVisibility(View.VISIBLE);

            // Hacemos Set en el tipo de Envio
            idTipoEnvio = position;
        }

        InputFilter[] fArray = new InputFilter[1];

        if (position == NUMERO_TARJETA.getId()) {
            maxLength = idComercio == 814 ? 18 : 19;
            cardNumber.setHint(getString(R.string.card_number, String.valueOf(
                    idComercio == 814 ? 15 : 16
            )));
            NumberCardTextWatcher numberCardTextWatcher = new NumberCardTextWatcher(cardNumber, maxLength);
            if (keyIdComercio == IDCOMERCIO_YA_GANASTE) {
                numberCardTextWatcher.setOnITextChangeListener(this);
            }
            cardNumber.addTextChangedListener(numberCardTextWatcher);
            layoutImageContact2.setVisibility(View.GONE);
            layoutImageContact2.setOnClickListener(null);
            selectedType = NUMERO_TARJETA;
        } else if (position == NUMERO_TELEFONO.getId()) {
            maxLength = 12;
            cardNumber.setHint(getString(R.string.transfer_phone_cellphone));
            layoutImageContact2.setVisibility(View.VISIBLE);
            layoutImageContact2.setOnClickListener(this);
            PhoneTextWatcher phoneTextWatcher = new PhoneTextWatcher(cardNumber);
            if (keyIdComercio == IDCOMERCIO_YA_GANASTE) {
                phoneTextWatcher.setOnITextChangeListener(this);
            }
            cardNumber.addTextChangedListener(phoneTextWatcher);
            selectedType = NUMERO_TELEFONO;
        } else if (position == CLABE.getId()) {
            maxLength = 22;
            cardNumber.setHint(getString(R.string.transfer_cable));
            cardNumber.addTextChangedListener(new NumberClabeTextWatcher(cardNumber));
            layoutImageContact2.setVisibility(View.GONE);
            layoutImageContact2.setOnClickListener(null);
            selectedType = CLABE;
        } else {
            maxLength = 2;
            cardNumber.setHint("");
            layout_cardNumber.setVisibility(GONE);
            layoutImageContact2.setVisibility(View.GONE);
            layoutImageContact2.setOnClickListener(null);
            selectedType = null;
        }

        fArray[0] = new InputFilter.LengthFilter(maxLength);
        cardNumber.setFilters(fArray);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onTextChanged() {

    }

    @Override
    public void onTextComplete() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess(Double importe) {

    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {
        setBackUpResponse(response);

        // Actualizamos el formato de la referencia solo si es el TAB de PDS
        if (current_tab == 2) {
            //  initFormatoLogitud();
        }
    }

    private void setBackUpResponse(ArrayList<CarouselItem> mResponse) {
        for (CarouselItem carouselItem : mResponse) {
            if (carouselItem.getComercio() != null) {
                backUpResponse.add(new CustomCarouselItem(
                        carouselItem.getComercio().getIdComercio(),
                        carouselItem.getComercio().getIdTipoComercio(),
                        carouselItem.getComercio().getNombreComercio(),
                        carouselItem.getComercio().getFormato(),
                        carouselItem.getComercio().getLongitudReferencia()
                ));
            }
        }
    }

    @Override
    public void showErrorService() {

    }

    @Override
    public void showFavorites() {

    }

    @Override
    public void onCropper(Uri uri) {
        showLoader(getString(R.string.load_photo_favorite));
        BACK_STATE_EDITFAVORITE = false;
        startActivityForResult(CropActivity.callingIntent(this, uri), CROP_RESULT);
    }

    @Override
    public void onCropSuccess(Uri croppedUri) {
        hideLoader();
        cameraManager.setCropImage(croppedUri);
    }

    @Override
    public void onCropFailed(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onBackPressed() {
        if (BACK_STATE_EDITFAVORITE) {
            super.onBackPressed();
        }
    }

    @Override
    public void onHideProgress() {
        hideLoader();
        BACK_STATE_EDITFAVORITE = true;
    }

    @Override
    public void failLoadJpg() {
        showDialogMesage(getString(R.string.msg_format_image),
                getString(R.string.msg_format_image_warning), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
    }

    @Override
    public void onOtpGenerated(String otp) {
        RequestHeaders.setTokenFreja(otp);
    }

    @Override
    public void showError(Errors error) {

    }
}
