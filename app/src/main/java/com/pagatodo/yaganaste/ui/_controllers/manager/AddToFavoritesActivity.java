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
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosEditDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosNewDatosResponse;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ITextChangeListener;
import com.pagatodo.yaganaste.interfaces.OnListServiceListener;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
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
import com.pagatodo.yaganaste.utils.Constants;
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
import com.steelkiwi.cropiwa.image.CropIwaResultReceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CLABE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.DESTINATARIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_TIPO_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_TIPO_ENVIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.NOMBRE_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.REFERENCIA;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.IAVE_ID;
import static com.pagatodo.yaganaste.utils.Recursos.IDCOMERCIO_YA_GANASTE;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;
import static com.pagatodo.yaganaste.utils.StringUtils.getCreditCardFormat;
import static com.pagatodo.yaganaste.utils.camera.CameraManager.CROP_RESULT;

public class AddToFavoritesActivity extends LoaderActivity implements IAddFavoritesActivity,
        IListaOpcionesView, ValidationForms, View.OnClickListener, OnListServiceListener,
        AdapterView.OnItemSelectedListener, ITextChangeListener, PaymentsCarrouselManager,
        ICropper, CropIwaResultReceiver.Listener, OtpView {


    public static final String TAG = AddToFavoritesActivity.class.getSimpleName();
    public static final int CONTACTS_CONTRACT_LOCAL = 51;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 101;
    public static final String FAV_PROCESS = "FAV_PROCESS";
    public static final String CURRENT_TAB_ID = "currentTabId";
    public static boolean LOADER_SHOWED = false;

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.add_favorites_alias)
    EditText editAlias;
    @BindView(R.id.add_favorites_alias_error)
    ErrorMessage editAliasError;
    @BindView(R.id.add_favorites_list_serv)
    EditText editListServ;
    @BindView(R.id.add_favorites_list_serv_error)
    ErrorMessage editListServError;
    @BindView(R.id.txt_lyt_list_serv)
    TextInputLayout txtLytListServ;
    @BindView(R.id.add_favorites_referencia)
    CustomValidationEditText editRefer;
    @BindView(R.id.add_favorites_referencia_error)
    ErrorMessage editReferError;
    @BindView(R.id.add_favorites_camera)
    UploadDocumentView imageViewCamera;
    @BindView(R.id.recargaNumber)
    EditText recargaNumber;
    @BindView(R.id.layoutImg)
    RelativeLayout relativefav;
    @BindView(R.id.add_favorites_tipo)
    CustomValidationEditText textViewTipo;
    @BindView(R.id.add_favorites_linear_tipo)
    LinearLayout linearTipo;

    @BindView(R.id.imgItemGalleryMark)
    CircleImageView circuloimage;


    @BindView(R.id.imgItemGalleryStatus)
    CircleImageView circuloimageupload;

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
    EditText cardNumber;
    @BindView(R.id.layout_cardNumber)
    LinearLayout layout_cardNumber;
    @BindView(R.id.add_favorites_spinner_et)
    CustomValidationEditText editSpinner;
    @BindView(R.id.add_favorites_error)
    ErrorMessage editSpinnerError;
    @BindView(R.id.add_favorites_foto_et)
    CustomValidationEditText editFoto;
    @BindView(R.id.add_favorites_foto_error)
    ErrorMessage editFotoError;

    IFavoritesPresenter favoritesPresenter;
    int idTipoComercio;
    int idComercio;
    int idTipoEnvio;
    String stringFoto = "";
    String mReferencia;
    String tabName;
    private String formatoComercio;
    private int longitudRefer;
    CameraManager cameraManager;
    private boolean errorIsShowed = false;
    ArrayList<CarouselItem> backUpResponse;
    int current_tab;
    boolean isIAVE;
    private int maxLength;
    int keyIdComercio;
    TransferType selectedType;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    private TextWatcher currentTextWatcher;
    AppCompatImageView btn_back;
    private FavoritoPresenterAutoriza favoritoPresenterAutoriza;
    private static Preferencias preferencias = App.getInstance().getPrefs();
    private int favoriteProcess;
    private String nombreComercio;
    private String nombreDest;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // cropResultReceiver.unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_favorites);
        ButterKnife.bind(this);
        favoritesPresenter = new FavoritesPresenter(this);
        favoritoPresenterAutoriza = new FavoritoPresenterAutoriza(this, this);

        /**
         Identificar:
         - Cuando es un agregar Favoritos desde Pago exitoso
         - Cuando es un agregar Favoritos desde Cero
         - Cuando es un Editar Favorito
         */
        Intent intent = getIntent();
        favoriteProcess = getIntent().getIntExtra(FAV_PROCESS, 1);
        current_tab = intent.getIntExtra(CURRENT_TAB_ID, 99);

        btn_back = (AppCompatImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        // Iniciamos el presentes del carrousel
        backUpResponse = new ArrayList<>();
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(this.current_tab, this, this, false);
        paymentsCarouselPresenter.getCarouselItems();

        /**
         * Formato para ciruclos de Tomar foto
         */
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthp = metrics.widthPixels; // ancho absoluto en pixels
        int paramentroT = widthp / 3;
        int paramentroimgc = paramentroT / 4;
        int distancia = paramentroT - paramentroimgc;
        imageViewCamera.setVisibilityStatus(false);
        imageViewCamera.setStatusImage(ContextCompat.getDrawable(this, R.drawable.camara_white_blue_canvas));
        //imageViewCamera.setImageDrawable(App.getContext().getDrawable(R.drawable.ic_camera_border_gray));
        //  circuloimage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_usuario_azul));
        RelativeLayout.LayoutParams paramsc = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsc.setMargins(distancia, 30, 0, 0);
        paramsc.width = paramentroimgc;
        paramsc.height = paramentroimgc;
        circuloimageupload.setLayoutParams(paramsc);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.width = paramentroT;
        params.height = paramentroT;
        relativefav.setLayoutParams(params);
        //imageViewCamera.setNewHW(300, 300);

        // Iniciamos la funcionalidad e la camara con su manager
        cameraManager = new CameraManager(this);
        cameraManager.initCameraUploadDocument(this, imageViewCamera, this);

        //Bloqueamos la edicion de referencia hasta que tengamos ya un servicio de la lista
        editRefer.setVisibility(View.GONE);
        editReferError.setVisibility(View.GONE);
        layout_cardNumber.setVisibility(View.GONE);
        editSpinnerError.setVisibility(View.GONE);

        // Hacemos Set de Reglas de validacion
        setValidationRules();

        // Hacemos Set de eventos ONCLICK
        layoutImageContact.setOnClickListener(this);
        layoutImageContact2.setOnClickListener(this);

        // Funcionalidad de Crop Mejorado
        CropIwaResultReceiver cropResultReceiver = new CropIwaResultReceiver();
        cropResultReceiver.setListener(this);
        cropResultReceiver.register(this);

        /**
         * PROCESOS BASICOS para cada tipo de EVENTO
         *
         * Mostramos las vistas iniciales o las ocultamos
         */
        if (favoriteProcess == 1) {
            // REcibimos los datos del Intent que viene desde PagoExitoso
            idComercio = intent.getIntExtra(ID_COMERCIO, 99);
            idTipoComercio = intent.getIntExtra(ID_TIPO_COMERCIO, 98);
            idTipoEnvio = intent.getIntExtra(ID_TIPO_ENVIO, 97);
            nombreComercio = intent.getStringExtra(NOMBRE_COMERCIO);
            mReferencia = intent.getStringExtra(REFERENCIA);
            formatoComercio = intent.getStringExtra(REFERENCIA);

            // Pago exitoso, mostramos los campos con sus datos si es necesario

            // Set NOMBRE Destinatorio
            nombreDest = intent.getStringExtra(DESTINATARIO);
            if (nombreDest != null) {
                editAlias.setText(nombreDest);
            }

            // Set NOMBRE COMERCION
            editListServ.setText(nombreComercio);
            String formatoPago = mReferencia;
            if (current_tab == 1) {
                if (idComercio != 7) {
                    formatoPago = StringUtils.formatoPagoMedios(formatoPago);
                }
                if (idComercio == 7) {
                    formatoPago = StringUtils.formatoPagoMediostag(formatoPago);
                }
                txtLytListServ.setHint(getString(R.string.details_compania));
                // editListServ.setHintText(getString(R.string.details_compania));
                recargaNumber.setText(mReferencia);
                LinearLayout taeLL = (LinearLayout) findViewById(R.id.add_favorites_tae_ll);
                taeLL.setVisibility(View.VISIBLE);
            } else if (current_tab == 2) {
                formatoPago = StringUtils.genericFormat(formatoPago, SPACE);
                txtLytListServ.setHint(getString(R.string.details_compania));
                referenceNumber.setText(mReferencia);

                LinearLayout taeLL = (LinearLayout) findViewById(R.id.add_favorites_serv_ll);
                taeLL.setVisibility(View.VISIBLE);
                // editListServ.setHintText(getString(R.string.details_compania));
            } else if (current_tab == 3) {
                if (formatoPago.length() == 16 || formatoPago.length() == 15) {
                    formatoPago = getCreditCardFormat(formatoPago);
                } else {
                    formatoPago = StringUtils.formatoPagoMedios(formatoPago);
                }
                cardNumber.setText(mReferencia);
                txtLytListServ.setHint(getString(R.string.details_bank));
                // editListServ.setHintText(getString(R.string.details_bank));
            }

            // Deshabilitamos la edicion de los CustomEditTExt para no modificarlos
            editListServ.setEnabled(false);
            editListServ.setFocusable(false);
            editListServ.setFocusableInTouchMode(false);
            editListServ.setOnClickListener(this);
            // txtLytListServ.setHint(getString(R.string.details_bank));

            //SET de la referencia, dependiendo del tipo de pestaña ponemos el formato
            editRefer.setText(formatoPago);
            editRefer.setVisibility(View.GONE);
            editRefer.setEnabled(false);

            // Localizamos el tipo de Tab con el que traajamos
            switch (current_tab) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    linearTipo.setVisibility(View.VISIBLE);
                    textViewTipo.setVisibility(View.VISIBLE);
                    switch (idTipoEnvio) {
                        case 1:
                            textViewTipo.setText(getResources().getString(R.string.transfer_phone));
                            break;
                        case 2:
                            textViewTipo.setText(getResources().getString(R.string.debit_card_number));
                            break;
                        case 3:
                            textViewTipo.setText(getResources().getString(R.string.transfer_cable));
                            break;
                    }

                    // Agregamos el tipo de envio al campo auxiliar de Spinner editSpinner
                    editSpinner.setText("" + idTipoEnvio);
                    break;
            }

        } else if (favoriteProcess == 2) {
            // Proceso para alta de favorito desde Cero mostramos los campos poco a poco


            if (current_tab == 1) {
                txtLytListServ.setHint(getString(R.string.details_compania));
                //    editListServ.setHintText(getString(R.string.details_compania));
            } else if (current_tab == 2) {
                txtLytListServ.setHint(getString(R.string.service_txt));
                //   editListServ.setHintText(getString(R.string.service_txt));
            } else if (current_tab == 3) {
                txtLytListServ.setHint(getString(R.string.details_bank));
                //  editListServ.setHintText(getString(R.string.details_bank));
            }
            layoutImageContact.setOnClickListener(this);
            layoutImageContact2.setOnClickListener(this);

            // Deshabilitamos la edicion de los CustomEditTExt para no modificarlos
            editListServ.setEnabled(true);
            editListServ.setFocusable(false);
            editListServ.setFocusableInTouchMode(false);
            editListServ.setOnClickListener(this);
        }
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
                    // editAlias.imageViewIsGone(true);
                } else {
                   /* if (editAlias.getText().isEmpty()) {
                        showValidationError(editAlias.getId(), getString(R.string.addFavoritesErrorAlias));
                        editAlias.setIsInvalid();
                    } else {
                        hideValidationError(editAlias.getId());
                        editAlias.setIsValid();
                    }*/
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
                            // showValidationError(editRefer.getId(), getString(R.string.addFavoritesErrorRefer));
                            // editRefer.setIsInvalid();

                            UI.showErrorSnackBar(AddToFavoritesActivity.this, getString(R.string.addFavoritesErrorRefer), Snackbar.LENGTH_SHORT);
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
                            //showValidationError(editRefer.getId(), getString(R.string.addFavoritesErrorRefer));
                            //editRefer.setIsInvalid();
                            UI.showErrorSnackBar(AddToFavoritesActivity.this, getString(R.string.addFavoritesErrorRefer), Snackbar.LENGTH_SHORT);
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
                            //showValidationError(editRefer.getId(), getString(R.string.addFavoritesErrorRefer));
                            //editRefer.setIsInvalid();
                            UI.showErrorSnackBar(AddToFavoritesActivity.this, getString(R.string.addFavoritesErrorRefer), Snackbar.LENGTH_SHORT);
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


    /**
     * EVENTOS OnClick de Butter Knife o Listener
     */

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

        if (isValid) {
            favoritesPresenter.openMenuPhoto(1, cameraManager);
        }
    }

    // Cerramos esta actividad de favoritos
    @OnClick(R.id.btnCloseAddFavorites)
    public void closeAddFavoritos() {
        finish();
    }

    //Disparamos la validacion, si es exitosa, entnces iniciamos el proceso de favoritos
    @OnClick(R.id.btnSendAddFavoritos)
    public void sendAddFavoritos() {
        // Toast.makeText(this, "Open Presenter", Toast.LENGTH_SHORT).show();
        validateForm();
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
                dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
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
        LOADER_SHOWED = false;
        showDialogMesage("", mMensaje, 0);
    }

    /**
     * Exito en agregar a Favoritos
     *
     * @param mResponse
     */
    @Override
    public void toViewSuccessAdd(FavoritosNewDatosResponse mResponse) {
        LOADER_SHOWED = false;
        showDialogMesage(getString(R.string.title_dialog_favorite),
                getString(R.string.respond_ok_add_new_favorite), 1);
    }

    @Override
    public void toViewSuccessAddFoto(String mMensaje) {
        showDialogMesage("", mMensaje, 1);
    }

    @Override
    public void toViewSuccessAdd(FavoritosDatosResponse response) {
        LOADER_SHOWED = false;
    }

    @Override
    public void toViewSuccessDeleteFavorite(String mensaje) {
        LOADER_SHOWED = false;
        Log.d(TAG, "toViewSuccessDeleteFavorite " + mensaje);
    }

    @Override
    public void toViewSuccessEdit(FavoritosEditDatosResponse response) {

    }

    @Override
    public void onFailGetTitulaName(String error) {
        Log.d(TAG, "onFailGetTitulaName " + error);
    }

    /**
     * Error al tener un numero que no tiene cuenta de YaGanaste
     *
     * @param mMensaje
     */
    @Override
    public void toViewErrorCuentaFail(String mMensaje) {
        showDialogMesage("", mMensaje, 0);
        showValidationError(editRefer.getId(), mMensaje);
        String myNumber = cardNumber.getText().toString();
        cardNumber.setHint(myNumber);
        cardNumber.setText("");
        //  editRefer.setText("");
        scrollView.fullScroll(View.FOCUS_DOWN);

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
            } else if (requestCode == CONTACTS_CONTRACT_LOCAL) {
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
            //imageViewCamera.setImageBitmap(bitmap);
            Glide.with(this)
                    .load(cameraManager.getUriImage())
                    .asBitmap()
                    .into(imageViewCamera.getCircleImageView());
            Bitmap bitmapAux = MediaStore.Images.Media.getBitmap(this.getContentResolver(), cameraManager.getUriImage());
            //cameraManager.setBitmap(bitmapAux);
            // Procesamos el Bitmap a Base64
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapAux.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            stringFoto = Base64.encodeToString(byteArray, Base64.DEFAULT);
            ;
            editFoto.setText(stringFoto);

            // Ocultamos el mensaje de error de foto
            editFotoError.setVisibilityImageError(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ActualizarAvatarRequest avatarRequest = new ActualizarAvatarRequest(encoded, "png");
        // Enviamos al presenter
        //mPreferPresenter.sendPresenterActualizarAvatar(avatarRequest);
        LOADER_SHOWED = false;
        hideLoader();
    }

    /**
     * Validador de formulario, aqui tenemos la logica para llamar a los metodos de mostrar los errores
     */
    @Override
    public void validateForm() {
        // onValidationSuccess();
        getDataForm();
        boolean isValid = true;

        //Validate format Email
        if (editAlias.getText().toString().isEmpty()) {
            showValidationError(editAlias.getId(), getString(R.string.addFavoritesErrorAlias));
            UI.showErrorSnackBar(this, getString(R.string.addFavoritesErrorAlias), Snackbar.LENGTH_SHORT);
            //  editAlias.setIsInvalid();
            isValid = false;
            //return;
        }

        //Validate format Servicios
        if (editListServ.getText().toString().isEmpty()) {
            if (current_tab == 3) {
                UI.showErrorSnackBar(this, getString(R.string.addFavoritesErrorBank), Snackbar.LENGTH_SHORT);
                //  showValidationError(editListServ.getId(), getString(R.string.addFavoritesErrorBank));
            } else {
                UI.showErrorSnackBar(this, getString(R.string.addFavoritesErrorServ), Snackbar.LENGTH_SHORT);
                //  showValidationError(editListServ.getId(), getString(R.string.addFavoritesErrorServ));
            }
            //editListServ.setIsInvalid();
            isValid = false;
            //return;
        }

        /** Hacemos set de la validacion de referencia dependiendo del tipo de TAB */

        if (current_tab == 1) {
            /**
             * Se toma la bandera de favoriteProcess porque AddFavorito tomaba un EditTExt estatico
             * como auxiliar, en cambio AddNewFavorito toma la referencia de campos dinamicos
             */
            if (favoriteProcess == 1) {
                editRefer.setText(editRefer.getText().toString());
            } else {
                editRefer.setText(recargaNumber.getText().toString());
            }
            //Validate format Referencia
            if (editRefer.getText().toString().isEmpty()) {
                // showValidationError(editRefer.getId(), getString(R.string.addFavoritesErrorRefer));
                // editRefer.setIsInvalid();

                UI.showErrorSnackBar(this, getString(R.string.addFavoritesErrorRefer), Snackbar.LENGTH_SHORT);
                isValid = false;
                //return;
            }
        } else if (current_tab == 2) {
            if (favoriteProcess == 1) {
                editRefer.setText(editRefer.getText().toString());
            } else {
                editRefer.setText(referenceNumber.getText().toString());
            }
            //Validate format Referencia
            if (editRefer.getText().toString().isEmpty()) {
                //showValidationError(editRefer.getId(), getString(R.string.addFavoritesErrorRefer));
                //editRefer.setIsInvalid();

                UI.showErrorSnackBar(this, getString(R.string.addFavoritesErrorRefer), Snackbar.LENGTH_SHORT);
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

            if (favoriteProcess == 1) {
                editRefer.setText(editRefer.getText().toString());
            } else {
                editRefer.setText(cardNumber.getText().toString());

            }

            //Validate format Referencia
            if (editRefer.getText().toString().isEmpty()) {
                // showValidationError(editRefer.getId(), getString(R.string.addFavoritesErrorRefer));
                // editRefer.setIsInvalid();

                UI.showErrorSnackBar(this, getString(R.string.addFavoritesErrorRefer), Snackbar.LENGTH_SHORT);
                isValid = false;
                //return;
            }
        }

        //Validate format Tipo Envio
       /* if (!editFoto.isValidText()) {
            showValidationError(editFoto.getId(), getString(R.string.addFavoritesErrorFoto));
            editFoto.setIsInvalid();
            isValid = false;
            //return;
        }*/


        //onValidationSuccess();
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
            case R.id.add_favorites_list_serv:
                editListServError.setVisibilityImageError(false);
                break;
            /*case R.id.add_favorites_tipo:
                editTipoError.setVisibilityImageError(false);
                break;*/
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
        //editTipoError.setVisibilityImageError(false);
        editReferError.setVisibilityImageError(false);

        String mAlias = editAlias.getText().toString();
        mReferencia = editRefer.getText().toString();
        String referService = StringUtils.formatCardToService(mReferencia);

        AddFavoritesRequest addFavoritesRequest = new AddFavoritesRequest(idTipoComercio, idTipoEnvio,
                idComercio, mAlias, referService, stringFoto.equals("") ? null : stringFoto, stringFoto.equals("") ? null : "png");

        /* Si no tiene un favorito guardado con la misma referencia entonces se permite subirlo*/
        if (!favoritesPresenter.alreadyExistFavorite(referService, idComercio)) {
            favoritoPresenterAutoriza.generateOTP(preferencias.loadData("SHA_256_FREJA"));
            favoritesPresenter.toPresenterAddNewFavorites(getString(R.string.loader_15), addFavoritesRequest);
            LOADER_SHOWED = true;
        } else {
             /*  En caso de que ya exista un favorito con la misma referencia entonces muestra un Diálogo */
            UI.createSimpleCustomDialog(getString(R.string.title_error), getString(R.string.error_favorite_exist), getSupportFragmentManager(),
                    "");
        }

        // Codigo para mostrar el llenado de la peticion
      /*  Toast.makeText(this, "Validacion exitosa, ver log para datos", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Alias " + mAlias
                + " idTipoComercio " + idTipoComercio
                + " idComercio " + idComercio
                + " idTipoEnvio " + idTipoEnvio
                + " mReferencia " + mReferencia
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
        LOADER_SHOWED = true;
    }

    @Override
    public void showExceptionToView(String mMesage) {
        //Log.d("TAG", "showExceptionToView ");
    }

    @Override
    public void sendSuccessAvatarToView(String mensaje) {
    }

    @Override
    public void sendErrorAvatarToView(String mensaje) {
        //Log.d("TAG", "sendErrorAvatarToView ");
    }

    /**
     * Listener que efectua varias tareas cuando se selecciona un servicio de la lista, dependiendo
     * del TAB realiza diversas funciones
     *
     * @param item
     */

    @Override
    public void onListServiceListener(CarouselItem item, int position) {
//  Toast.makeText(this, "Item " + item.getNombreComercio(), Toast.LENGTH_SHORT).show();
        editListServ.setText(item.getComercio().getNombreComercio());
        idTipoComercio = item.getComercio().getIdTipoComercio();
        idComercio = item.getComercio().getIdComercio();
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
        formatoComercio = item.getComercio().getFormato();
        longitudRefer = item.getComercio().getLongitudReferencia();

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

        // Cpodigo TEST HAstcode para pruebas
//        idTipoComercio = 2;
//        idComercio = 28;
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
            recargaNumber.setHint(getString(R.string.hint_phone_number));
            layoutImageContact.setVisibility(View.VISIBLE);
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
    }

    /**
     * Procedimientos especificos para la referencia por via PDS
     */
    private void initPDSRefer() {
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
        referenceNumber.addTextChangedListener(new NumberTagPase(referenceNumber, maxLength));
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
        SpinnerArrayAdapter dataAdapter = new SpinnerArrayAdapter(this, Constants.PAYMENT_ENVIOS, tipoPago);
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
        cardNumber.setText("");
        //cardNumber.removeTextChangedListener();

        // Hacemos el Set de la informacion del Spinner en un campo que servira como validador
        if (position == 0) {
            editSpinner.setText("");
            editReferError.setVisibility(View.INVISIBLE);
        } else {
            editSpinner.setText("" + position);

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
            cardNumber.addTextChangedListener(new NumberClabeTextWatcher(cardNumber, maxLength));
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
        favoritesPresenter.getTitularName(cardNumber.getText().toString().trim());
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
    }

    @Override
    public void setDataBank(String idcomercio, String nombrebank) {

    }

    @Override
    public void errorgetdatabank() {
    }

    @Override
    public void setCarouselDataFavoritos(ArrayList<CarouselItem> response) {
    }

    @Override
    public void setFavolist(List<DataFavoritos> lista) {
    }

    private void setBackUpResponse(ArrayList<CarouselItem> mResponse) {
        backUpResponse = new ArrayList<>();
        for (CarouselItem carouselItem : mResponse) {
            backUpResponse.add(carouselItem);
        }

        Collections.sort(backUpResponse, new Comparator<CarouselItem>() {
            @Override
            public int compare(CarouselItem o1, CarouselItem o2) {
                if (o1.getComercio() != null && o2.getComercio() != null) {
                    return o1.getComercio().getNombreComercio().compareToIgnoreCase(o2.getComercio().getNombreComercio());
                } else {
                    return 0;
                }
            }
        });
    }

    @Override
    public void showErrorService() {
    }

    /*
    // CROPPER
    */
    @Override
    public void onCropper(Uri uri) {
        showLoader(getString(R.string.load_photo_favorite));
        LOADER_SHOWED = true;
        startActivityForResult(CropActivity.callingIntent(this, uri), CROP_RESULT);
    }

    @Override
    public void onCropSuccess(Uri croppedUri) {
        hideLoader();
        LOADER_SHOWED = false;
        cameraManager.setCropImage(croppedUri);
    }

    @Override
    public void onCropFailed(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onHideProgress() {
        hideLoader();
        LOADER_SHOWED = false;
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
    public void onBackPressed() {
        if (!LOADER_SHOWED) {
            super.onBackPressed();
        }
    }

    @Override
    public void onOtpGenerated(String otp) {
        RequestHeaders.setTokenFreja(otp);
    }

    @Override
    public void showError(Errors error) {
    }

    @Override
    public void setLogOutSession() {

    }
}
