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
import android.view.animation.TranslateAnimation;
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
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataTitular;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnListServiceListener;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.AddToFavoritesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.EditFavoritesActivity;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.EnviosManager;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.EnviosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IEnviosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui_wallet.adapters.MaterialPaletteAdapter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.RecyclerViewOnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.presenter.EnviosPaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.IEnviosPaymentPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.NumberCardTextWatcher;
import com.pagatodo.yaganaste.utils.NumberClabeTextWatcher;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.QrcodeGenerator;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ListServDialogFragment;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;

import butterknife.BindView;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

import static android.view.View.GONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_NEXT;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CLABE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.QR_CODE;
import static com.pagatodo.yaganaste.ui._controllers.manager.AddToFavoritesActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.manager.AddToFavoritesActivity.FAV_PROCESS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.CREDITCARD_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Recursos.IDCOMERCIO_YA_GANASTE;
import static com.pagatodo.yaganaste.utils.ValidateForm.AMEX;
import static com.pagatodo.yaganaste.utils.ValidateForm.GENERIC;

/**
 * Created by Armando Sandoval on 03/01/2018.
 */

public class EnviosFromFragmentNewVersion extends PaymentFormBaseFragment implements
        EnviosManager, TextView.OnEditorActionListener, View.OnClickListener, PaymentsCarrouselManager, OnListServiceListener, AdapterView.OnItemSelectedListener {

    List<String> tipoPago = new ArrayList<>();
    int idTipoComercio;
    int idComercio;
    int idTipoEnvio;
    boolean bancoselected = false;
    private String formatoComercio;
    private int longitudRefer;
    @BindView(R.id.tipoEnvio)
    Spinner tipoEnvio;
    Boolean isfavo = false;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    boolean solicitabanco = true;
    @BindView(R.id.montotosend)
    MontoTextView montotosend;
    @BindView(R.id.cardNumber)
    StyleEdittext cardNumber;
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
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    @BindView(R.id.add_favorites_list_serv)
    CustomValidationEditText editListServ;
    @BindView(R.id.envio_from_slide_view)
    LinearLayout slideView;
    @BindView(R.id.envio_from_slide_view_ll)
    LinearLayout slideViewLl;
    @BindView(R.id.envio_from_slide_view_l1)
    LinearLayout slideViewL1;
    @BindView(R.id.iv_triangule_blue)
    ImageView triBlue;
    TransferType selectedType;
    IEnviosPresenter enviosPresenter;
    private String nombreDestinatario;
    private String referenciaNumber, referenceFavorite;
    int keyIdComercio;
    int maxLength;
    Double enviomonto;
    private static Double montoa;
    private boolean isCuentaValida = true;
    PaymentsTabFragment fragment;
    List<DataFavoritos> backUpResponseFavoritos;
    ArrayList<CarouselItem> backUpResponse;
    ArrayList<CarouselItem> finalList;
    ArrayList<CarouselItem> backUpResponsefinal;
    ArrayList<CarouselItem> backUpResponsefavo;
    IEnviosPaymentPresenter newPaymentPresenter;
    private boolean isUp;
    String myReferencia;
    boolean isfavoedit = false;
    private OnListServiceListener onListServiceListener;

    public static EnviosFromFragmentNewVersion newInstance(Double monto) {
        EnviosFromFragmentNewVersion fragment = new EnviosFromFragmentNewVersion();
        Bundle args = new Bundle();
        montoa = monto;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newPaymentPresenter = new EnviosPaymentPresenter(this, App.getContext());
        current_tab = Constants.PAYMENT_ENVIOS;
        backUpResponse = new ArrayList<>();
        enviosPresenter = new EnviosPresenter(this);
        backUpResponsefavo = new ArrayList<>();
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(current_tab, this, getContext(), false);

        if (!UtilsNet.isOnline(getActivity())) {
            UI.createSimpleCustomDialog("Error", getString(R.string.no_internet_access), getActivity().getSupportFragmentManager(), getFragmentTag());
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
        super.initViews();

        btnenviar.setOnClickListener(this);
        montotosend.setText(String.format("%s", StringUtils.getCurrencyValue(montoa)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        editListServ.imageViewIsGone(false);
        editListServ.setEnabled(false);
        editListServ.setFullOnClickListener(this);
        editListServ.setHintText(getString(R.string.details_bank));

        tipoPago.add(0, "");
        tipoPago.add(NUMERO_TELEFONO.getId(), NUMERO_TELEFONO.getName(getContext()));
        tipoPago.add(NUMERO_TARJETA.getId(), NUMERO_TARJETA.getName(getContext()));
        tipoPago.add(CLABE.getId(), CLABE.getName(getContext()));
        tipoPago.add(QR_CODE.getId(), QR_CODE.getName(getContext()));


        if (keyIdComercio == IDCOMERCIO_YA_GANASTE) {
            //receiverName.setVisibility(GONE);
            receiverName.setEnabled(false);
            receiverName.cancelLongPress();
            receiverName.setClickable(false);
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
        isUp = false;
        slideView.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isfavoedit)
            paymentsCarouselPresenter.getFavoriteCarouselItems();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnenviar:
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
                /*Intent scanIntent = new Intent(getActivity(), CardIOActivity.class);
                // customize these values to suit your needs.
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
                // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
                getActivity().startActivityForResult(scanIntent, CREDITCARD_READER_REQUEST_CODE);*/
                break;
            case R.id.envio_from_slide_view:
                onSlideViewButtonClick(slideViewLl);
                break;
            default:
                break;
        }
    }

    // slide the view from below itself to the current position
    public void slideUp(final View view) {
        // Mostramos las 3 capas antes de la animacion, esto de mostrar solo es necesario una vez
        slideViewL1.setVisibility(View.VISIBLE);
        referenciaLayout.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        triBlue.setImageResource(R.drawable.triangule_blue_down);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        triBlue.setImageResource(R.drawable.triangule_blue_up);
        slideViewL1.setVisibility(View.INVISIBLE);
        referenciaLayout.setVisibility(View.INVISIBLE);
        view.setVisibility(View.INVISIBLE);
    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            final ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
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
        receiverName.setText("");
    }

    @Override
    public void onTextComplete() {
        enviosPresenter.getTitularName(cardNumber.getText().toString().trim());
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
            UI.createSimpleCustomDialog(errorTittle, errorText, getActivity().getSupportFragmentManager(), getFragmentTag());
        }

    }

    @Override
    public void onError(String error) {
        isValid = false;
        errorText = error;
    }

    @Override
    public void onSuccess(Double monto) {
        this.monto = monto;
        isValid = true;
    }

    @Override
    public void showError(String text) {
        if (!TextUtils.isEmpty(text)) {
            UI.createSimpleCustomDialog("Error de Text", text, getActivity().getSupportFragmentManager(), getFragmentTag());
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

    @Override
    protected void continuePayment() {

        referencia = cardNumber.getText().toString().trim();
        referencia = referencia.replaceAll(" ", "");

        concepto = concept.getText().toString().trim();
        nombreDestinatario = receiverName.getText().toString().trim();
        referenciaNumber = numberReference.getText().toString().trim();

        enviosPresenter.validateForms(selectedType, referencia,
                maxLength == 19 ? GENERIC : AMEX,
                montotosend.getText().toString().trim(),
                nombreDestinatario,
                concepto,
                referenciaNumber);


        if (!isCuentaValida) {
            Formatter formatter = new Formatter();
            showError(formatter.format(getString(R.string.error_cuenta_no_valida), tipoEnvio.getSelectedItem().toString()).toString());
            formatter.close();
            //mySeekBar.setProgress(0);
        } else if (!isValid) {
            showError();
            //mySeekBar.setProgress(0);
        } else if (comercioItem == null) {
            UI.createSimpleCustomDialog("Error", "Por Favor Elige un Banco ", getActivity().getSupportFragmentManager(), getFragmentTag());
        } else {

            if (!UtilsNet.isOnline(getActivity())) {
                UI.createSimpleCustomDialog("Error", getString(R.string.no_internet_access), getActivity().getSupportFragmentManager(), getFragmentTag());
            } else {
                //Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
                //Se debe crear un objeto que se envía a la activity que realizará el pago
                referencia = cardNumber.getText().toString().trim();
                referencia = referencia.replaceAll(" ", "");
                nombreDestinatario = receiverName.getText().toString();
                concepto = concept.getText().toString().trim();
                nombreDestinatario = receiverName.getText().toString().trim();
                referenciaNumber = numberReference.getText().toString().trim();
                payment = new Envios(selectedType, referencia, montoa, nombreDestinatario, concepto, referenciaNumber, comercioItem,
                        favoriteItem != null);
                sendPayment();

                getActivity().finish();

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
        for (int x = 0; x < backUpResponse.size(); x++) {
            if (backUpResponse.get(x).getComercio().getIdComercio() == myIdComercio) {
                comercioItem = backUpResponse.get(x).getComercio();
                editListServ.setText(backUpResponse.get(x).getComercio().getNombreComercio());
                idTipoComercio = backUpResponse.get(x).getComercio().getIdTipoComercio();
                idComercio = backUpResponse.get(x).getComercio().getIdComercio();
            }
        }

    }

    @Override
    public void errorgetdatabank() {
        hideLoader();
        bancoselected = true;
        solicitabanco = true;
        editListServ.setDrawableImage(R.drawable.menu_canvas);
        UI.createSimpleCustomDialog("", "Selecciona tu Banco", getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        editListServ.setText("");
                        editListServ.setDrawableImage(R.drawable.menu_canvas);
                        editListServ.setHintText("Banco");
                        comercioItem = null;
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
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
    public void setFavolist(List<DataFavoritos> lista) {
        backUpResponseFavoritos = new ArrayList<>();

        DataFavoritos itemAdd = new DataFavoritos(0);
        itemAdd.setNombre("Agregar");
        backUpResponseFavoritos.add(itemAdd);

        for (DataFavoritos carouselItem : lista) {
            backUpResponseFavoritos.add(carouselItem);
        }

        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        recyclerView.setAdapter(new MaterialPaletteAdapter(backUpResponseFavoritos, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (backUpResponseFavoritos.get(position).getIdComercio() == 0) { // Click en item Agregar
                    Intent intentAddFavorite = new Intent(getActivity(), AddToFavoritesActivity.class);
                    intentAddFavorite.putExtra(FAV_PROCESS, 2);
                    intentAddFavorite.putExtra(CURRENT_TAB_ID, current_tab);
                    startActivity(intentAddFavorite);
                } else {
                    // Toast.makeText(getActivity(), "Favorito: " + backUpResponseFavoritos.get(position).getNombre(), Toast.LENGTH_SHORT).show();

                    // TODO Armando Estos son los datos que son necesario, solo queda hacer el Set en los campos
                    isfavo = true;
                    bancoselected = true;
                    long myIdComercio = backUpResponseFavoritos.get(position).getIdComercio();
                    String myName = backUpResponseFavoritos.get(position).getNombre();


                    myReferencia = backUpResponseFavoritos.get(position).getReferencia();

                    switch (backUpResponseFavoritos.get(position).getReferencia().length()) {
                        case 10:
                            myReferencia = backUpResponseFavoritos.get(position).getReferencia();
                            tipoEnvio.setSelection(NUMERO_TELEFONO.getId());
                            receiverName.setText(myName);
                            cardNumber.setText("");
                            cardNumber.setText(myReferencia);
                            if (idComercio == IDCOMERCIO_YA_GANASTE) {
                                referenciaLayout.setVisibility(GONE);
                                numberReference.setText("123456");
                                concept.setImeOptions(IME_ACTION_DONE);
                                concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
                            }
                            break;
                        case 16:
                            myReferencia = backUpResponseFavoritos.get(position).getReferencia();
                            tipoEnvio.setSelection(NUMERO_TARJETA.getId());
                            receiverName.setText(myName);
                            cardNumber.setText("");
                            cardNumber.setText(myReferencia);
                            break;
                        case 18:
                            myReferencia = backUpResponseFavoritos.get(position).getReferencia();
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

                        }
                    }
                }
            }
            @Override
            public void onLongClick(View v, int position) {
                if (backUpResponseFavoritos.get(position).getIdComercio() != 0) {
                    isfavoedit = true;
                    Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    vibrator.vibrate(100);
                    Intent intentEditFav = new Intent(getActivity(), EditFavoritesActivity.class);
                    intentEditFav.putExtra(getActivity().getString(R.string.favoritos_tag), backUpResponseFavoritos.get(position));
                    intentEditFav.putExtra(PaymentsProcessingActivity.CURRENT_TAB_ID, current_tab);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intentEditFav, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    } else {
                        startActivity(intentEditFav);
                    }
                }
            }
        }));
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
        if (current_tab == 3) {
            LinearLayout taeLL = (LinearLayout) getActivity().findViewById(R.id.add_favorites_list_serv);
            taeLL.setVisibility(View.VISIBLE);
        }
        if (idComercio == IDCOMERCIO_YA_GANASTE) {
            receiverName.setEnabled(false);
            receiverName.cancelLongPress();
            receiverName.setClickable(false);
            referenciaLayout.setVisibility(GONE);
            numberReference.setText("123456");
            concept.setImeOptions(IME_ACTION_DONE);
            concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));

        } else {
            receiverName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
            concept.setImeOptions(IME_ACTION_NEXT);
            concept.setText(App.getContext().getResources().getString(R.string.trans_spei_envio_txt));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        layout_cardNumber.setVisibility(View.VISIBLE);
        cardNumber.setText("");
        cardNumber.removeTextChangedListener();
        InputFilter[] fArray = new InputFilter[1];

        if (position == NUMERO_TARJETA.getId()) {
            referenceFavorite = null;
            cardNumber.setEnabled(true);
            editListServ.setEnabled(true);
            cardNumber.setText("");
            editListServ.cleanImage();
            bancoselected = false;
            maxLength = 19;
            cardNumber.setHint(getString(R.string.card_number, String.valueOf(16)));
            NumberCardTextWatcher numberCardTextWatcher = new NumberCardTextWatcher(cardNumber, maxLength);
            if (keyIdComercio == IDCOMERCIO_YA_GANASTE || idComercio == IDCOMERCIO_YA_GANASTE) {
                numberCardTextWatcher.setOnITextChangeListener(this);
            } else {
                receiverName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
                concept.setImeOptions(IME_ACTION_NEXT);
                concept.setText(App.getContext().getResources().getString(R.string.trans_spei_envio_txt));
            }
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
            editListServ.setDrawableImage(R.drawable.menu_canvas);
            cardNumber.setText("");
            bancoselected = true;
            maxLength = 12;
            cardNumber.setHint(getString(R.string.transfer_phone_cellphone));
            layoutImageContact.setVisibility(View.VISIBLE);
            layoutImageContact.setOnClickListener(this);
            layoutScanQr.setVisibility(View.GONE);
            layoutScanQr.setOnClickListener(null);
            layoutScanCard.setVisibility(View.GONE);
            layoutScanCard.setOnClickListener(null);
            PhoneTextWatcher phoneTextWatcher = new PhoneTextWatcher(cardNumber);
            if (keyIdComercio == IDCOMERCIO_YA_GANASTE || idComercio == IDCOMERCIO_YA_GANASTE) {
                phoneTextWatcher.setOnITextChangeListener(this);
                concept.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
            } else {
                receiverName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
                concept.setImeOptions(IME_ACTION_NEXT);
                concept.setText(App.getContext().getResources().getString(R.string.trans_spei_envio_txt));
            }
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
            editListServ.cleanImage();
            bancoselected = false;
            cardNumber.setText("");
            cardNumber.setHint(getString(R.string.transfer_cable));
            NumberClabeTextWatcher textWatcher = new NumberClabeTextWatcher(cardNumber);
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
                receiverName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
                concept.setImeOptions(IME_ACTION_NEXT);
                concept.setText(App.getContext().getResources().getString(R.string.trans_spei_envio_txt));
            }

            if (isfavo == true && !myReferencia.isEmpty()) {
                referenceFavorite = myReferencia;
                isfavo = false;
            }
        } else if (position == QR_CODE.getId()) {

            referenceFavorite = null;
            cardNumber.setText("");
            maxLength = 22;
            NumberClabeTextWatcher textWatcher = new NumberClabeTextWatcher(cardNumber);
            cardNumber.addTextChangedListener(textWatcher);
            textchangeclabe();
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
            layoutScanQr.setVisibility(View.VISIBLE);
            layoutScanQr.setOnClickListener(this);
            layoutScanCard.setVisibility(View.GONE);
            layoutScanCard.setOnClickListener(null);
            cardNumber.setHint(getString(R.string.transfer_qr));
            cardNumber.setEnabled(false);
            editListServ.setText(getString(R.string.app_name));
            editListServ.setEnabled(false);
            idcomercioqr(IDCOMERCIO_YA_GANASTE);


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
            //  cardNumber.setHint("");
            layout_cardNumber.setVisibility(GONE);
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
            layoutScanQr.setVisibility(View.GONE);
            layoutScanQr.setOnClickListener(null);
            layoutScanCard.setVisibility(View.GONE);
            layoutScanCard.setOnClickListener(null);
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

                if (card.length() == 22 && solicitabanco) {
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
                        UI.createSimpleCustomDialog(getString(R.string.title_error), getString(R.string.transfer_qr_invalid), getActivity().getSupportFragmentManager(), getFragmentTag());
                    }
                }
            }
        }
        if (requestCode == CREDITCARD_READER_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";
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
}
