package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataTitular;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnListServiceListener;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.EnvioFormularioWallet;
import com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.EnviosManager;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.EnviosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IEnviosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui_wallet.adapters.MaterialPaletteAdapter;
import com.pagatodo.yaganaste.ui_wallet.builder.Container;
import com.pagatodo.yaganaste.ui_wallet.builder.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.holders.PaletteViewHolder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IEnviosPaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.RecyclerViewOnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.presenter.EnviosPaymentPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.NumberCardTextWatcher;
import com.pagatodo.yaganaste.utils.NumberClabeTextWatcher;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.QrcodeGenerator;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.ListServDialogFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

import static android.view.View.GONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_NEXT;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CLABE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.QR_CODE;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.RESUL_FAVORITES;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.FAV_PROCESS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.CREDITCARD_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Recursos.IDCOMERCIO_YA_GANASTE;

/**
 * Created by Armando Sandoval on 03/01/2018.
 */

public class EnviosFromFragmentNewVersion extends GenericFragment implements
        EnviosManager, TextView.OnEditorActionListener, View.OnClickListener,
        PaymentsCarrouselManager, OnListServiceListener, AdapterView.OnItemSelectedListener,
        PaletteViewHolder.OnClickListener{

    private View rootview;
    @BindView(R.id.spnTypeSend)
    Spinner tipoEnvio;

    @BindView(R.id.txt_lyt_cardnumber)
    TextInputLayout txtLytCardNumber;
    @BindView(R.id.cardNumber)
    EditText cardNumber;
    @BindView(R.id.layout_cardNumber)
    LinearLayout layout_cardNumber;
    @BindView(R.id.btnenviar)
    StyleButton btnenviar;
    @BindView(R.id.receiverName)
    EditText receiverName;
    @BindView(R.id.concept)
    EditText concept;
    @BindView(R.id.numberReference)
    EditText numberReference;
    @BindView(R.id.fragment_envios_referencia_layout)
    LinearLayout referenciaLayout;
    @BindView(R.id.layoutImageContact)
    RelativeLayout layoutImageContact;
    @BindView(R.id.layoutScanQr)
    RelativeLayout layoutScanQr;
    @BindView(R.id.layoutScanCard)
    RelativeLayout layoutScanCard;
    @BindView(R.id.imgMakePaymentContact)
    ImageView imgMakePaymentContact;
    @BindView(R.id.txt_lyt_list_serv)
    TextInputLayout txtLytListServ;
    @BindView(R.id.add_favorites_list_serv)
    EditText editListServ;
    @BindView(R.id.envio_from_slide_view)
    LinearLayout slideView;
    @BindView(R.id.envio_from_slide_view_ll)
    LinearLayout slideViewLl;
    @BindView(R.id.iv_triangule_blue)
    ImageView triBlue;
    @BindView(R.id.txtEditarFavoritos)
    StyleTextView editarFavoritos;
    @BindView(R.id.txt_show_references)
    TextView txtShowReferences;
    @BindView(R.id.txt_lyt_receiver_name)
    TextInputLayout txt_lyt_receiver_name;
    @BindView(R.id.envio_from_slide_view_l1)
    TextInputLayout envio_from_slide_view_l1;

    @BindView(R.id.content_linearlayout)
    LinearLayout mLinearLayout;

    TransferType selectedType;
    List<String> tipoPago = new ArrayList<>();
    int idTipoComercio, idComercio, longitudRefer, keyIdComercio, maxLength;
    private String nombreDestinatario, referenciaNumber, referenceFavorite, myReferencia, errorText,
            referencia, formatoComercio, concepto;
    private boolean isCuentaValida = true, isUp, bancoselected = false, solicitabanco = true,
            isfavo = false, isValid = true;
    List<Favoritos> backUpResponseFavoritos;
    Favoritos favoriteItem;
    Comercio comercioItem;
    ArrayList<CarouselItem> backUpResponse, finalList, backUpResponsefinal, backUpResponsefavo;
    IEnviosPaymentPresenter newPaymentPresenter;
    IEnviosPresenter enviosPresenter;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    Payments payment;
    TextWatcher txtWatcherSetted;
    private boolean isEditable = false;
    private RecyclerViewOnItemClickListener adapterMaterialListener;
    private Container builder;

    public static EnviosFromFragmentNewVersion newInstance() {
        return new EnviosFromFragmentNewVersion();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newPaymentPresenter = new EnviosPaymentPresenter(this, App.getContext());
        backUpResponse = new ArrayList<>();
        enviosPresenter = new EnviosPresenter(this);
        backUpResponsefavo = new ArrayList<>();
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(Constants.PAYMENT_ENVIOS, this, getContext(), false);

        if (!UtilsNet.isOnline(getActivity())) {
            // UI.createSimpleCustomDialog("Error", getString(R.string.no_internet_access), getActivity().getSupportFragmentManager(), getFragmentTag());
            UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
        } else {
            onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
            paymentsCarouselPresenter.getCarouselItems();
            paymentsCarouselPresenter.getFavoriteCarouselItems();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_envios_from_new_version, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        builder = new Container(getContext(),mLinearLayout);

        //listView.setAdapter(adapterList);

        /**
         *
         * Parte de los focus change para manejar los edit text activados y normales
         */
        cardNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //hideValidationError(edtitConfirmEmail.getId());
                    // edtitConfirmEmail.imageViewIsGone(true);
                    layout_cardNumber.setBackgroundResource(R.drawable.inputtext_active);
                } else {

                    layout_cardNumber.setBackgroundResource(R.drawable.inputtext_normal);

                }
            }
        });



        receiverName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //hideValidationError(edtitConfirmEmail.getId());
                    // edtitConfirmEmail.imageViewIsGone(true);
                    txt_lyt_receiver_name.setBackgroundResource(R.drawable.inputtext_active);
                } else {

                    txt_lyt_receiver_name.setBackgroundResource(R.drawable.inputtext_normal);

                }
            }
        });

        concept.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //hideValidationError(edtitConfirmEmail.getId());
                    // edtitConfirmEmail.imageViewIsGone(true);
                    envio_from_slide_view_l1.setBackgroundResource(R.drawable.inputtext_active);
                } else {

                    envio_from_slide_view_l1.setBackgroundResource(R.drawable.inputtext_normal);

                }
            }
        });


        numberReference.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //hideValidationError(edtitConfirmEmail.getId());
                    // edtitConfirmEmail.imageViewIsGone(true);
                    referenciaLayout.setBackgroundResource(R.drawable.inputtext_active);
                } else {

                    referenciaLayout.setBackgroundResource(R.drawable.inputtext_normal);

                }
            }
        });
        /**/



        btnenviar.setOnClickListener(this);

        editListServ.setEnabled(true);
        editListServ.setFocusable(false);
        editListServ.setFocusableInTouchMode(false);
        editListServ.setOnClickListener(this);
        txtLytListServ.setHint(getString(R.string.details_bank));

        tipoPago.add(0, "");
        tipoPago.add(NUMERO_TELEFONO.getId(), NUMERO_TELEFONO.getName(getContext()));
        tipoPago.add(NUMERO_TARJETA.getId(), NUMERO_TARJETA.getName(getContext()));
        tipoPago.add(CLABE.getId(), CLABE.getName(getContext()));
        tipoPago.add(QR_CODE.getId(), QR_CODE.getName(getContext()));

        cardNumber.setLongClickable(true);
        cardNumber.setSingleLine();

        if (keyIdComercio == IDCOMERCIO_YA_GANASTE) {
            referenciaLayout.setVisibility(GONE);
            numberReference.setText("123456");
            //cardNumber.setOnFocusChangeListener(this);
            //amountToSend.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            //amountToSend.setOnEditorActionListener(this);
            concept.setImeOptions(IME_ACTION_DONE);
            concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
            //tipoPago.add(QR_CODE.getId(), QR_CODE.getName(getContext()));
        } else {
            receiverName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
            concept.setImeOptions(IME_ACTION_NEXT);
            concept.setText(App.getContext().getResources().getString(R.string.trans_spei_envio_txt));

        }

        concept.setSingleLine(true);
        concept.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_DONE) {
                    UI.hideKeyBoard(getActivity());
                } else if (actionId == IME_ACTION_NEXT) {
                    numberReference.requestFocus();
                }
                return false;
            }
        });

        numberReference.setText(DateUtil.getDayMonthYear());
        SpinnerArrayAdapter dataAdapter = new SpinnerArrayAdapter(getContext(), Constants.PAYMENT_ENVIOS, tipoPago);
        tipoEnvio.setAdapter(dataAdapter);

        tipoEnvio.setOnItemSelectedListener(this);
        receiverName.setSingleLine();
        if (favoriteItem != null) {
            receiverName.setText(favoriteItem.getNombre());
            switch (favoriteItem.getReferencia().length()) {
                case 10:
                    tipoEnvio.setSelection(NUMERO_TELEFONO.getId());
                    break;
                case 16:
                    tipoEnvio.setSelection(NUMERO_TARJETA.getId());
                    break;
                case 18:
                    tipoEnvio.setSelection(CLABE.getId());
                    break;
            }
        }
        concept.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionid, KeyEvent keyEvent) {
                if (actionid == EditorInfo.IME_ACTION_NEXT) {
                    // si pasamos al siguiente item

                    final ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            numberReference.setFocusable(true);
                            numberReference.requestFocus();
                        }
                    });
                    return true; // Focus will do whatever you put in the logic.)
                }
                return false;
            }
        });


        /**
         * Variables necesarias para que funcione el Slide Up-Down
         * isUp = true; indica que la vista esta "Down"
         * false = es "Up"
         */
        isUp = true;
        slideView.setOnClickListener(this);

        // Agregamos  Listener al campo de Editfavorites
        editarFavoritos.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //paymentsCarouselPresenter.getFavoriteCarouselItems();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnenviar:
                validateForms();
                continuePayment();
                break;
            case R.id.add_favorites_list_serv:
                /**
                 * 1 - Creamos nuestro Dialog Fragment Custom que mostrar la lista de Servicios
                 * 2 - HAcemos SET de la interfase OnListServiceListener, con e metodo setOnList...
                 * asi al hacer clic en algun elemento nos dara la respuesta
                 * 3 - Mostramos el dialogo
                 */

                if (bancoselected) {
                    ListServDialogFragment dialogFragment = ListServDialogFragment.newInstance(finalList);
                    dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                    dialogFragment.setOnListServiceListener(this);
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "FragmentDialog");
                }
                break;

            case R.id.layoutImageContact:
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                getActivity().startActivityForResult(contactPickerIntent, CONTACTS_CONTRACT);
                break;
            case R.id.layoutScanQr:
                Intent intent = new Intent(getActivity(), ScannVisionActivity.class);
                intent.putExtra(ScannVisionActivity.QRObject, true);
                getActivity().startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
                break;
            case R.id.layoutScanCard:
                Intent scanIntent = new Intent(getContext(), CardIOActivity.class);

                // customize these values to suit your needs.
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true);
                scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true);
                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true);
                scanIntent.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false);
                scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, true);
                scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, true);
                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, true);
                //scanIntent.putExtra(CardIOActivity.EXTRA_UNBLUR_DIGITS, 8);

                // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
                getActivity().startActivityForResult(scanIntent, CREDITCARD_READER_REQUEST_CODE);
                break;
            case R.id.envio_from_slide_view:
                onSlideViewButtonClick(slideViewLl);
                break;
            case R.id.txtEditarFavoritos:
                // Hacemos update del Adapter de Envio de favoritos
                if (isEditable) {
                    editarFavoritos.setTextColor(getResources().getColor(R.color.texthint));
                    isEditable = false;
                    ContainerBuilder.edition(false);
                    // adapterPagosClass.createRecycler(ITEM_FAVORITO_RECARGA, 1);
                } else {
                    editarFavoritos.setTextColor(getResources().getColor(R.color.colorTituloDialog));
                    isEditable = true;
                    ContainerBuilder.edition(true);
                    // adapterPagosClass.createRecycler(ITEM_FAVORITO_RECARGA, 2);
                }

                // Borramos el adaptar antterior, y volvemos a crearlo para mostrar las imagernes de editar
                /*if (adapterMaterialPalet != null) {
                    adapterMaterialPalet = null;
                    adapterMaterialPalet = new MaterialPaletteAdapter(backUpResponseFavoritos, isEditable, adapterMaterialListener);
                    recyclerView.setAdapter(adapterMaterialPalet);
                }*/

                break;
            default:
                break;
        }
    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            txtShowReferences.setText(getString(R.string.Ocultar_Referencia));
            final ScrollView scrollView = getActivity().findViewById(R.id.scrollView);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    numberReference.setFocusable(true);
                    numberReference.requestFocus();
                }
            });
            Matrix matrix = new Matrix();
            matrix.postRotate(180.0f);
            Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.triangule_blue_down);
            Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
            ImageView imgRotate = (ImageView) getActivity().findViewById(R.id.iv_triangule_blue);
            imgRotate.setImageBitmap(rotatedBitmap);
            view.setVisibility(View.VISIBLE);
        } else {
            txtShowReferences.setText(getString(R.string.Agregar_Referencia));
            Matrix matrix = new Matrix();
            matrix.postRotate(360.0f);
            Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.triangule_blue_down);
            Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
            ImageView imgRotate = (ImageView) getActivity().findViewById(R.id.iv_triangule_blue);
            imgRotate.setImageBitmap(rotatedBitmap);
            view.setVisibility(View.VISIBLE);
            view.setVisibility(View.GONE);
        }
        isUp = !isUp;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public void onTextChanged() {
        isCuentaValida = false;
        //   receiverName.setText("");
    }

    @Override
    public void onTextComplete() {
        //   enviosPresenter.getTitularName(cardNumber.getText().toString().trim());
    }

    @Override
    public void showError() {
        if (errorText != null && !errorText.equals("")) {
            /**
             * Comparamos la cadena que entrega el Servicio o el Presentes, con los mensajes que
             * tenemos en el archivo de Strings, dependiendo del mensaje, hacemos un set al errorTittle
             * para mostrarlo en el UI.createSimpleCustomDialog
             */
            String errorTittle = "";
            if (errorText.equals(App.getContext().getString(R.string.txt_tipo_envio_error))) {
                errorTittle = App.getContext().getResources().getString(R.string.type_send_invalid);

            } else if (errorText.equals(App.getContext().getString(R.string.txt_referencia_envio_empty))) {
                errorTittle = "Error";

            } else if (errorText.equals(App.getContext().getString(R.string.txt_referencia_envio_empty_clabe))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_envios_clabe_empty_error);
                errorText = App.getContext().getResources().getString(R.string.new_body_envios_clabe_empty_error);

            } else if (errorText.equals(App.getContext().getString(R.string.txt_referencia_envio_empty_creditc))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_envios_tdc_empty_error);
                errorText = App.getContext().getResources().getString(R.string.new_body_envios_tdc_empty_error);

            } else if (errorText.equals(App.getContext().getString(R.string.txt_referencia_envio_empty_telefono))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_envios_cell_empty_error);
                errorText = App.getContext().getResources().getString(R.string.new_body_envios_cell_empty_error);

            } else if (errorText.equals(App.getContext().getString(R.string.txt_referencia_envio_error))) {
                errorTittle = "Error";
            } else if (errorText.equals(App.getContext().getString(R.string.new_body_envios_clabe_error))) {
                errorTittle = App.getContext().getString(R.string.new_tittle_envios_clabe_error);

            } else if (errorText.equals(App.getContext().getString(R.string.new_body_envios_tdc_error))) {
                errorTittle = App.getContext().getString(R.string.new_tittle_envios_tdc_error);

            } else if (errorText.equals(App.getContext().getString(R.string.new_body_envios_cellphone_error))) {
                errorTittle = App.getContext().getString(R.string.new_tittle_envios_cellphone_error);

            } else if (errorText.equals(App.getContext().getString(R.string.txt_importe_empty))) {
                errorTittle = App.getContext().getString(R.string.new_tittle_envios_importe_empty_error);
                errorText = App.getContext().getString(R.string.new_body_envios_importe_empty_error);
            } else if (errorText.equals(App.getContext().getString(R.string.txt_name_empty))) {
                errorTittle = App.getContext().getString(R.string.destiny_invalid);
                errorText = App.getContext().getString(R.string.new_body_envios_destiny_error);

            } else if (errorText.equals(App.getContext().getString(R.string.txt_name_error))) {
                errorTittle = "Error";

            } else if (errorText.equals(App.getContext().getString(R.string.txt_concept_empty))) {
                errorTittle = App.getContext().getString(R.string.new_tittle_envios_concepto_error);
                errorText = App.getContext().getString(R.string.new_body_envios_concepto_error);

            } else if (errorText.equals(App.getContext().getString(R.string.txt_concept_error))) {
                errorTittle = "Error";
            } else if (errorText.equals(App.getContext().getString(R.string.txt_referencia_number_empty))) {
                errorTittle = App.getContext().getString(R.string.new_tittle_envios_refer_error);
                errorText = App.getContext().getString(R.string.new_body_envios_refer_error);

            } else if (errorText.equals(App.getContext().getString(R.string.txt_referencia_number_short))) {
                errorTittle = "Error";
            } else if (errorText.equals(App.getContext().getString(R.string.txt_referencia_number_invalid))) {
                errorTittle = "Error";
            } else if (errorText.equals(App.getContext().getString(R.string.txt_referencia_number_error))) {
                errorTittle = "Error";
            }
            UI.showErrorSnackBar(getActivity(), errorTittle, Snackbar.LENGTH_LONG);
        }

    }

    @Override
    public void onError(String error) {
        isValid = false;
        errorText = error;
    }

    @Override
    public void onSuccess(Double monto) {
        isValid = true;
    }

    @Override
    public void showError(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (!text.equals("Por favor verifica que la longitud de la referencia sea correcta")) {
                UI.showErrorSnackBar(getActivity(), text, Snackbar.LENGTH_SHORT);
            }
        }
    }

    @Override
    public void setTitularName(DataTitular dataTitular) {
        isCuentaValida = true;
        receiverName.setText(dataTitular.getNombre().concat(" ").concat(dataTitular.getPrimerApellido()).concat(" ").concat(dataTitular.getSegundoApellido()));
    }

    @Override
    public void onFailGetTitulaName() {
        isCuentaValida = false;
        clearTitularName();
    }

    @Override
    public void showLoader(String text) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, text);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    private void clearTitularName() {
        isCuentaValida = false;
        receiverName.setText("");
    }

    private void continuePayment() {
        referencia = cardNumber.getText().toString().trim();
        referencia = referencia.replaceAll(" ", "");

        concepto = concept.getText().toString().trim();
        nombreDestinatario = receiverName.getText().toString().trim();
        referenciaNumber = numberReference.getText().toString().trim();

        if (!isValid) {
            showError();
            //mySeekBar.setProgress(0);
        } else if (comercioItem == null) {
            UI.showErrorSnackBar(getActivity(), "Por favor elige un banco", Snackbar.LENGTH_SHORT);
        } else {
            if (!UtilsNet.isOnline(getActivity())) {
                UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
            } else {
                //Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
                //Se debe crear un objeto que se envía a la activity que realizará el pago
                referencia = cardNumber.getText().toString().trim();
                referencia = referencia.replaceAll(" ", "");
                nombreDestinatario = receiverName.getText().toString();
                concepto = concept.getText().toString().trim();
                nombreDestinatario = receiverName.getText().toString().trim();
                referenciaNumber = numberReference.getText().toString().trim();
                payment = new Envios(selectedType, referencia, 0D, nombreDestinatario, concepto, referenciaNumber, comercioItem,
                        favoriteItem != null);
                Intent intent = new Intent(getContext(), EnvioFormularioWallet.class);
                intent.putExtra("pagoItem", payment);
                intent.putExtra("favoritoItem", favoriteItem);
                startActivity(intent);
                comercioItem = null;
                favoriteItem = null;
                tipoEnvio.setSelection(0);
                editListServ.setText("");
                editListServ.clearFocus();
                receiverName.setText("");
                receiverName.clearFocus();
                concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
                numberReference.setText("123456");
            }
        }
    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {
        setBackUpResponse(response);
    }

    @Override
    public void setDataBank(String idcomercioresponse, String nombrebank) {
        hideLoader();
        solicitabanco = true;
        int myIdComercio = Integer.parseInt(idcomercioresponse);
        for (int x = 0; x < finalList.size(); x++) {
            if (finalList.get(x).getComercio().getIdComercio() == myIdComercio) {
                comercioItem = finalList.get(x).getComercio();
                editListServ.setText(finalList.get(x).getComercio().getNombreComercio());
                idTipoComercio = finalList.get(x).getComercio().getIdTipoComercio();
                idComercio = finalList.get(x).getComercio().getIdComercio();
            }
        }

        if (selectedType == CLABE && idComercio == IDCOMERCIO_YA_GANASTE) {
            String card = cardNumber.getText().toString();
            card = card.replaceAll(" ", "");
            if (card.length() == 18) {
                enviosPresenter.getTitularName(cardNumber.getText().toString().trim());
            }
            concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
        }
        if (selectedType == NUMERO_TELEFONO && idComercio == IDCOMERCIO_YA_GANASTE) {
            concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
            String card = cardNumber.getText().toString();
            card = card.replaceAll(" ", "");
            if (card.length() == 10) {
                enviosPresenter.getTitularName(cardNumber.getText().toString().trim());
            }
        }
        if (selectedType == NUMERO_TARJETA && idComercio == IDCOMERCIO_YA_GANASTE) {
            concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
            String card = cardNumber.getText().toString();
            card = card.replaceAll(" ", "");
            if (card.length() == 16) {
                enviosPresenter.getTitularName(cardNumber.getText().toString().trim());
            }
        }

    }

    @Override
    public void errorgetdatabank() {
        hideLoader();
        bancoselected = true;

        if (!solicitabanco) {



            UI.createSimpleCustomDialog("", "Selecciona tu Banco", getFragmentManager(),
                    new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {
                            solicitabanco = true;
                            editListServ.setText("");
                            txtLytListServ.setHint(getString(R.string.details_bank));
                            comercioItem = null;
                        }

                        @Override
                        public void actionCancel(Object... params) {
                            solicitabanco = true;
                        }
                    },
                    true, false);
        }
        solicitabanco = true;
    }

    public void idcomercioqr(int myIdTipoComercio) {
        for (int x = 0; x < backUpResponse.size(); x++) {
            if (finalList.get(x).getComercio().getIdComercio() == myIdTipoComercio) {
                comercioItem = finalList.get(x).getComercio();
                editListServ.setText(finalList.get(x).getComercio().getNombreComercio());
                idTipoComercio = finalList.get(x).getComercio().getIdTipoComercio();
                idComercio = finalList.get(x).getComercio().getIdComercio();
            }
        }

    }

    @Override
    public void setCarouselDataFavoritos(ArrayList<CarouselItem> response) {
        setBackUpResponseFav(response);
    }

    @Override
    public void setFavolist(List<Favoritos> lista) {
        backUpResponseFavoritos = new ArrayList<>();
        mLinearLayout.removeAllViews();
        ContainerBuilder.FAVORITOS(getContext(),mLinearLayout,lista,this);
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);

    }

    private void setBackUpResponseFav(ArrayList<CarouselItem> mResponse) {
        backUpResponsefavo = new ArrayList<>();
        backUpResponseFavoritos = new ArrayList<>();

        for (CarouselItem carouselItem : mResponse) {
            backUpResponsefavo.add(carouselItem);
        }
        Collections.sort(backUpResponsefavo, new Comparator<CarouselItem>() {
            @Override
            public int compare(CarouselItem o1, CarouselItem o2) {
                return o1.getFavoritos().getNombre().compareToIgnoreCase(o2.getFavoritos().getNombre());
            }
        });

    }

    private void setBackUpResponse(ArrayList<CarouselItem> mResponse) {
        backUpResponse = new ArrayList<>();
        ArrayList<Integer> orderBy = new ArrayList<>();
        finalList = new ArrayList<>();
        orderBy.add(8609);
        orderBy.add(785);
        orderBy.add(779);
        orderBy.add(787);
        orderBy.add(809);
        orderBy.add(790);
        orderBy.add(799);
        orderBy.add(796);
        orderBy.add(832);

        backUpResponsefinal = new ArrayList<>();

        mResponse = Utils.removeNullCarouselItem(mResponse);
        for (CarouselItem carouselItem : mResponse) {
            backUpResponse.add(carouselItem);
        }

        /**
         * Buscamos en nuestro orderBy cada elemento en un ciclo adicional de originalList, si el ID existe
         * lo agregamos a nuesta finalList. Y eliminamos ese elemnto de originalList
         */
        for (Integer miList : orderBy) {
            for (int x = 0; x < backUpResponse.size(); x++) {
                if (backUpResponse.get(x).getComercio().getIdComercio() == miList) {
                    finalList.add(backUpResponse.get(x));
                    backUpResponse.remove(x);
                }
            }
        }
        Collections.sort(backUpResponse, new Comparator<CarouselItem>() {
            @Override
            public int compare(CarouselItem o1, CarouselItem o2) {
                return o1.getComercio().getNombreComercio().compareToIgnoreCase(o2.getComercio().getNombreComercio());
            }

        });
        for (int x = 0; x < backUpResponse.size(); x++) {
            finalList.add(backUpResponse.get(x));
        }
    }

    @Override
    public void showErrorService() {
        hideLoader();
        bancoselected = true;
        if (!solicitabanco) {
            UI.createSimpleCustomDialog("", "Selecciona tu Banco", getFragmentManager(),
                    new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {
                            solicitabanco = true;
                            editListServ.setText("");
                            txtLytListServ.setHint(getString(R.string.details_bank));
                            comercioItem = null;
                        }

                        @Override
                        public void actionCancel(Object... params) {
                            solicitabanco = true;
                        }
                    },
                    true, false);
        }
        solicitabanco = true;

    }

    @Override
    public void onListServiceListener(CarouselItem item, int position) {
        //  Toast.makeText(this, "Item " + item.getNombreComercio(), Toast.LENGTH_SHORT).show();
        comercioItem = item.getComercio();
        editListServ.setText(item.getComercio().getNombreComercio());
        idTipoComercio = item.getComercio().getIdTipoComercio();
        idComercio = item.getComercio().getIdComercio();
        // Variables necesarioas para agregar el formato de captura de telefono o referencia
        formatoComercio = item.getComercio().getFormato();
        longitudRefer = item.getComercio().getLongitudReferencia();

        /**
         * Mostramos el area de referencia que sea necesario al hacer Set en un servicio
         * Esto se controlar con la posicion del Tab que seleccionamos
         */
        if (idComercio == IDCOMERCIO_YA_GANASTE) {
            referenciaLayout.setVisibility(GONE);
            numberReference.setText("123456");
            concept.setImeOptions(IME_ACTION_DONE);
            concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));

            if (selectedType == NUMERO_TELEFONO) {
                String card = cardNumber.getText().toString();
                card = card.replaceAll(" ", "");
                if (card.length() == 10) {
                    enviosPresenter.getTitularName(cardNumber.getText().toString().trim());
                }
            }
            if (selectedType == NUMERO_TARJETA) {
                String card = cardNumber.getText().toString();
                card = card.replaceAll(" ", "");
                if (card.length() == 16) {
                    enviosPresenter.getTitularName(cardNumber.getText().toString().trim());
                }
            }
            if (selectedType == CLABE) {
                String card = cardNumber.getText().toString();
                card = card.replaceAll(" ", "");
                if (card.length() == 18) {
                    enviosPresenter.getTitularName(cardNumber.getText().toString().trim());
                }
            }
        } else {
            receiverName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
            concept.setImeOptions(IME_ACTION_NEXT);
            concept.setText(App.getContext().getResources().getString(R.string.trans_spei_envio_txt));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        layout_cardNumber.setVisibility(View.VISIBLE);
        if (txtWatcherSetted != null) cardNumber.removeTextChangedListener(txtWatcherSetted);
        cardNumber.setText("");
        cardNumber.setHint("");
        cardNumber.clearFocus();
        if (!isfavo) {
            editListServ.setText("");
            txtLytListServ.setHint(getString(R.string.details_bank));
            receiverName.setText("");
            idComercio = 0;
        }
        InputFilter[] fArray = new InputFilter[1];

        if (position == NUMERO_TARJETA.getId()) {
            referenceFavorite = null;
            cardNumber.setEnabled(true);
            editListServ.setEnabled(true);
            cardNumber.setText("");
            bancoselected = false;
            maxLength = 19;
            txtLytCardNumber.setHint(getString(R.string.card_number, String.valueOf(16)));
            NumberCardTextWatcher numberCardTextWatcher = new NumberCardTextWatcher(cardNumber, maxLength);
            if (idComercio == IDCOMERCIO_YA_GANASTE) {

                if (selectedType == NUMERO_TARJETA) {
                    String card = cardNumber.getText().toString();
                    card = card.replaceAll(" ", "");
                    if (card.length() == 16) {
                        enviosPresenter.getTitularName(cardNumber.getText().toString().trim());
                    }
                }

            } else {
                receiverName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
                concept.setImeOptions(IME_ACTION_NEXT);
                concept.setText(App.getContext().getResources().getString(R.string.trans_spei_envio_txt));
            }
            txtWatcherSetted = numberCardTextWatcher;
            cardNumber.addTextChangedListener(numberCardTextWatcher);
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
            layoutScanQr.setVisibility(View.GONE);
            layoutScanQr.setOnClickListener(null);
            layoutScanCard.setVisibility(View.VISIBLE);
            layoutScanCard.setOnClickListener(this);
            selectedType = NUMERO_TARJETA;
            if (isfavo == true && !myReferencia.isEmpty()) {
                referenceFavorite = myReferencia;
                isfavo = false;
            }
            textchange();

        } else if (position == NUMERO_TELEFONO.getId()) {
            referenceFavorite = null;
            cardNumber.setEnabled(true);
            editListServ.setEnabled(true);
            cardNumber.setText("");
            bancoselected = true;
            maxLength = 12;
            txtLytCardNumber.setHint(getString(R.string.transfer_phone_cellphone));
            layoutImageContact.setVisibility(View.VISIBLE);
            layoutImageContact.setOnClickListener(this);
            layoutScanQr.setVisibility(View.GONE);
            layoutScanQr.setOnClickListener(null);
            layoutScanCard.setVisibility(View.GONE);
            layoutScanCard.setOnClickListener(null);
            PhoneTextWatcher phoneTextWatcher = new PhoneTextWatcher(cardNumber);
            if (idComercio == IDCOMERCIO_YA_GANASTE) {
                phoneTextWatcher.setOnITextChangeListener(this);
                concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
            } else {
                receiverName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
                concept.setImeOptions(IME_ACTION_NEXT);
                concept.setText(App.getContext().getResources().getString(R.string.trans_spei_envio_txt));
            }
            txtWatcherSetted = phoneTextWatcher;
            cardNumber.addTextChangedListener(phoneTextWatcher);
            selectedType = NUMERO_TELEFONO;
            if (isfavo == true && !myReferencia.isEmpty()) {
                referenceFavorite = myReferencia;
                isfavo = false;
            }
        } else if (position == CLABE.getId() && keyIdComercio != IDCOMERCIO_YA_GANASTE) {
            referenceFavorite = null;
            cardNumber.setEnabled(true);
            editListServ.setEnabled(true);
            maxLength = 22;
            bancoselected = false;
            cardNumber.setText("");
            txtLytCardNumber.setHint(getString(R.string.transfer_cable));
            NumberClabeTextWatcher textWatcher = new NumberClabeTextWatcher(cardNumber, maxLength);
            txtWatcherSetted = textWatcher;
            cardNumber.addTextChangedListener(textWatcher);
            textchangeclabe();
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
            layoutScanQr.setVisibility(View.GONE);
            layoutScanQr.setOnClickListener(null);
            layoutScanCard.setVisibility(View.GONE);
            layoutScanCard.setOnClickListener(null);
            selectedType = CLABE;
            if (idComercio != IDCOMERCIO_YA_GANASTE) {
                concept.setImeOptions(IME_ACTION_NEXT);
                concept.setText(App.getContext().getResources().getString(R.string.trans_spei_envio_txt));
            } else {
                concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
                String card = cardNumber.getText().toString();
                card = card.replaceAll(" ", "");
                if (card.length() == 18) {
                    enviosPresenter.getTitularName(cardNumber.getText().toString().trim());
                }

            }

            if (isfavo == true && !myReferencia.isEmpty()) {
                referenceFavorite = myReferencia;
                isfavo = false;
            }
        } else if (position == QR_CODE.getId()) {
            referenceFavorite = null;
            cardNumber.setText("");
            maxLength = 22;
            NumberClabeTextWatcher textWatcher = new NumberClabeTextWatcher(cardNumber, maxLength);
            txtWatcherSetted = textWatcher;
            cardNumber.addTextChangedListener(textWatcher);
            textchangeclabe();
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
            layoutScanQr.setVisibility(View.VISIBLE);
            layoutScanQr.setOnClickListener(this);
            layoutScanCard.setVisibility(View.GONE);
            layoutScanCard.setOnClickListener(null);
            txtLytCardNumber.setHint(getString(R.string.transfer_qr));
            cardNumber.setEnabled(false);
            editListServ.setText(getString(R.string.app_name));
            editListServ.setEnabled(false);
            idcomercioqr(IDCOMERCIO_YA_GANASTE);
            selectedType = CLABE;

            referenciaLayout.setVisibility(GONE);
            numberReference.setText("123456");
            //cardNumber.setOnFocusChangeListener(this);
            //amountToSend.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            //amountToSend.setOnEditorActionListener(this);
            concept.setImeOptions(IME_ACTION_DONE);
            concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));

        } else {
            cardNumber.setEnabled(true);
            editListServ.setEnabled(true);
            maxLength = 2;
            txtLytCardNumber.setHint(getString(R.string.hint_reference));
            layout_cardNumber.setVisibility(GONE);
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
            layoutScanQr.setVisibility(View.GONE);
            layoutScanQr.setOnClickListener(null);
            layoutScanCard.setVisibility(View.GONE);
            layoutScanCard.setOnClickListener(this);
            selectedType = null;
            referenceFavorite = null;
        }

        fArray[0] = new InputFilter.LengthFilter(maxLength);
        cardNumber.setFilters(fArray);
        if (referenceFavorite != null) {
            //cardNumber.setEnabled(false);
            cardNumber.setText(referenceFavorite);
        }
    }

    private void textchangeclabe() {
        cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String card = cardNumber.getText().toString();
                card = card.replaceAll(" ", "");

                if (card.length() == 18) {
                    //   Toast.makeText(getActivity(), "22 Digitos ingresados", Toast.LENGTH_SHORT).show();
                    solicitabanco = false;
                    onEventListener.onEvent(EVENT_SHOW_LOADER, "");
                    paymentsCarouselPresenter.getdatabank(cardNumber.getText().toString(), "clave");
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(cardNumber.getWindowToken(), 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void textchange() {
        cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String card = cardNumber.getText().toString();
                card = card.replaceAll(" ", "");
                if (card.length() == 16 && solicitabanco) {
                    //   Toast.makeText(getActivity(), "6 Digitos ingresados", Toast.LENGTH_SHORT).show();
                    solicitabanco = false;
                    onEventListener.onEvent(EVENT_SHOW_LOADER, "");
                    paymentsCarouselPresenter.getdatabank(card, "bin");
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(cardNumber.getWindowToken(), 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESUL_FAVORITES) {
            paymentsCarouselPresenter.getCarouselItems();
            paymentsCarouselPresenter.getFavoriteCarouselItems();
        }
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CONTACTS_CONTRACT) {
                contactPicked(data);
            }
        }
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    if (barcode.displayValue.contains("userName") && barcode.displayValue.contains("phoneNumber") &&
                            barcode.displayValue.contains("cardNumber") && barcode.displayValue.contains("clabe")) {
                        QrcodeGenerator.MyQr myQr = new Gson().fromJson(barcode.displayValue, QrcodeGenerator.MyQr.class);
                        cardNumber.setText(myQr.getClabe());
                        receiverName.setText(myQr.getUserName());
                    } else {
                        UI.showErrorSnackBar(getActivity(), getString(R.string.transfer_qr_invalid), Snackbar.LENGTH_SHORT);
                    }
                }
            }
        }
        if (requestCode == CREDITCARD_READER_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                resultDisplayStr = "Card Number: " + scanResult.getFormattedCardNumber();
                cardNumber.setText(scanResult.getFormattedCardNumber().trim());
            } else {
                resultDisplayStr = "Scan was canceled.";
            }
            Log.e(getActivity().getString(R.string.app_name), "@CreditCard Scanner: " + resultDisplayStr);
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor;
        String phoneNo = null;
        String nameDisplay = "";
        Uri uri = data.getData();
        cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //get column index of the Phone Number
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            // column index of the contact name
            //int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex).replaceAll("\\s", "").replaceAll("\\+", "").replaceAll("-", "").trim();
            nameDisplay = cursor.getString(nameIndex);
            if (phoneNo.length() > 10) {
                phoneNo = phoneNo.substring(phoneNo.length() - 10);
            }
        }
        cardNumber.setText(phoneNo);

        /**
         * Validacion de nombre vacio
         */
        try {
            int oneNumbre = Integer.parseInt(nameDisplay.substring(0, 2));
            receiverName.setText("");
        } catch (Exception e) {
            receiverName.setText(nameDisplay);
        }
    }

    private void validateForms() {
        isValid = true;
        switch (tipoEnvio.getSelectedItemPosition()) {
            case 0:
                isValid = false;
                errorText = App.getContext().getString(R.string.txt_tipo_envio_error);
                break;
            case 1:
                if (cardNumber.getText().toString().isEmpty()) {
                    isValid = false;
                    errorText = App.getContext().getString(R.string.txt_referencia_envio_empty_telefono);
                } else if (!ValidateForm.isValidCellPhone(cardNumber.getText().toString())) {
                    isValid = false;
                    errorText = App.getContext().getString(R.string.new_body_envios_cellphone_error);
                }
                break;
            case 2:
                if (cardNumber.getText().toString().isEmpty()) {
                    isValid = false;
                    errorText = App.getContext().getString(R.string.txt_referencia_envio_empty_creditc);
                } else if (cardNumber.getText().toString().length() < 16) {
                    isValid = false;
                    errorText = App.getContext().getString(R.string.new_body_envios_tdc_error);
                }
                break;
            case 3:
                if (cardNumber.getText().toString().isEmpty()) {
                    isValid = false;
                    errorText = App.getContext().getString(R.string.txt_referencia_envio_empty_clabe);
                } else if (ValidateForm.isValidCABLE(cardNumber.getText().toString())) {
                    isValid = false;
                    errorText = App.getContext().getString(R.string.new_body_envios_clabe_error);
                }
                break;
            case 4:
                if (cardNumber.getText().toString().isEmpty()) {
                    isValid = false;
                    errorText = App.getContext().getString(R.string.txt_referencia_envio_empty_qr);
                }
                break;
        }
        if (receiverName.getText().toString().isEmpty()) {
            isValid = false;
            errorText = App.getContext().getString(R.string.txt_name_empty);
            return;
        }
        if (numberReference.getText().toString().isEmpty()) {
            isValid = false;
            errorText = App.getContext().getString(R.string.txt_referencia_number_empty);
            return;
        } else if (numberReference.getText().toString().length() < 6) {
            isValid = false;
            errorText = App.getContext().getString(R.string.txt_referencia_number_short);
            return;
        }
        if (concept.getText().toString().isEmpty()) {
            isValid = false;
            errorText = App.getContext().getString(R.string.txt_concept_empty);
            return;
        }
    }

    @Override
    public void onClick(Favoritos favorito) {
        /**
         * Si es editable mandamos a Edittar, si no a proceso normal
         */

        if (isEditable) {
            if (favorito.getIdComercio() != 0) {
                Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                vibrator.vibrate(100);
                //Intent intentEditFav = new Intent(getActivity(), EditFavoritesActivity.class);
                Intent intentEditFav = new Intent(getActivity(), FavoritesActivity.class);
                intentEditFav.putExtra(getActivity().getString(R.string.favoritos_tag), favorito);
                intentEditFav.putExtra(PaymentsProcessingActivity.CURRENT_TAB_ID, Constants.PAYMENT_ENVIOS);
                intentEditFav.putExtra(FavoritesActivity.TYPE_FAV,
                        FavoritesActivity.TYPE_EDIT_FAV);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intentEditFav, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                } else {
                    startActivity(intentEditFav);
                }
            }
        } else {
            if (favorito.getIdComercio() == 0) { // Click en item Agregar
                //Intent intentAddFavorite = new Intent(getActivity(), AddToFavoritesActivity.class);
                Intent intentAddFavorite = new Intent(getContext(), FavoritesActivity.class);
                intentAddFavorite.putExtra(FAV_PROCESS, 2);
                intentAddFavorite.putExtra(CURRENT_TAB_ID, Constants.PAYMENT_ENVIOS);
                intentAddFavorite.putExtra(FavoritesActivity.TYPE_FAV,
                        FavoritesActivity.TYPE_NEW_FAV);
                startActivityForResult(intentAddFavorite,RESUL_FAVORITES);
            } else {
                // Toast.makeText(getActivity(), "Favorito: " + backUpResponseFavoritos.get(position).getNombre(), Toast.LENGTH_SHORT).show();

                idComercio = 0;
                isfavo = true;
                bancoselected = true;
                bancoselected = true;
                favoriteItem = favorito;
                long myIdComercio = favorito.getIdComercio();
                String myName = favorito.getNombre();

                myReferencia = favorito.getReferencia();

                switch (favorito.getReferencia().length()) {
                    case 10:
                        myReferencia = favorito.getReferencia();
                        tipoEnvio.setSelection(NUMERO_TELEFONO.getId());
                        receiverName.setText(myName);
                        cardNumber.setText("");
                        cardNumber.setText(myReferencia);
                        break;
                    case 16:
                        myReferencia = favorito.getReferencia();
                        tipoEnvio.setSelection(NUMERO_TARJETA.getId());
                        receiverName.setText(myName);
                        cardNumber.setText("");
                        cardNumber.setText(myReferencia);
                        break;
                    case 18:
                        myReferencia = favorito.getReferencia();
                        tipoEnvio.setSelection(CLABE.getId());
                        receiverName.setText(myName);
                        cardNumber.setText("");
                        cardNumber.setText(myReferencia);
                        break;
                }

                //backUpResponseFavoritos
                    /*1 - Con ese id myIdComercio buscamos en backUpResponseFavoritos. Por ejemplo la posicion5
                            2 - Guardar ese dato en una variable CarouselItem2 que solo tenga esa posicion
                            3 - IguLAS comercioItem CON CarouselItem2*/

                for (int x = 0; x < finalList.size(); x++) {
                    if (finalList.get(x).getComercio().getIdComercio() == myIdComercio) {
                        comercioItem = finalList.get(x).getComercio();
                        editListServ.setText(finalList.get(x).getComercio().getNombreComercio());
                        idTipoComercio = finalList.get(x).getComercio().getIdTipoComercio();
                        idComercio = finalList.get(x).getComercio().getIdComercio();
                        if (idComercio == IDCOMERCIO_YA_GANASTE) {
                            referenciaLayout.setVisibility(GONE);
                            concept.setImeOptions(IME_ACTION_DONE);
                            concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
                        } else {
                            referenciaLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }
    }

}
