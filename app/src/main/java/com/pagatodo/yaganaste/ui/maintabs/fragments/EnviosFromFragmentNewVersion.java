package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
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
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataTitular;
import com.pagatodo.yaganaste.interfaces.OnListServiceListener;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.EnviosManager;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IEnviosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui_wallet.adapters.Color;
import com.pagatodo.yaganaste.ui_wallet.adapters.DividerItemDecoration;
import com.pagatodo.yaganaste.ui_wallet.adapters.MaterialPaletteAdapter;
import com.pagatodo.yaganaste.ui_wallet.adapters.RecyclerViewOnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.presenter.EnviosPaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.IEnviosPaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.INewPaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.NewPaymentPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.NumberCardTextWatcher;
import com.pagatodo.yaganaste.utils.NumberClabeTextWatcher;
import com.pagatodo.yaganaste.utils.NumberTextWatcher;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ListServDialogFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;
import com.pagatodo.yaganaste.utils.customviews.carousel.CustomCarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;

import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_NEXT;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB3;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CLABE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.QR_CODE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.TYPE_PAYMENT;
import static com.pagatodo.yaganaste.utils.Constants.TYPE_RELOAD;
import static com.pagatodo.yaganaste.utils.Recursos.IDCOMERCIO_YA_GANASTE;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;
import static com.pagatodo.yaganaste.utils.StringUtils.getCreditCardFormat;

/**
 * Created by Armando Sandoval on 03/01/2018.
 */

public class EnviosFromFragmentNewVersion extends PaymentFormBaseFragment implements
        EnviosManager,TextView.OnEditorActionListener, View.OnClickListener, PaymentsCarrouselManager ,OnListServiceListener, AdapterView.OnItemSelectedListener

{
    List<String> tipoPago = new ArrayList<>();
    int idTipoComercio;
    int idComercio;
    int idTipoEnvio;
    private String formatoComercio;
    private int longitudRefer;
    @BindView(R.id.tipoEnvio)
    Spinner tipoEnvio;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;



    @BindView(R.id.montotosend)
    StyleTextView montotosend;
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
    @BindView(R.id.imgMakePaymentContact)
    ImageView imgMakePaymentContact;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    @BindView(R.id.add_favorites_list_serv)
    CustomValidationEditText editListServ;
    TransferType selectedType;
    IEnviosPresenter enviosPresenter;
    private String nombreDestinatario;
    private String referenciaNumber, referenceFavorite;
    int keyIdComercio;
    int maxLength;
    private static Float montoa;
    private boolean isCuentaValida = true;
    PaymentsTabFragment fragment;
    ArrayList<CustomCarouselItem> backUpResponse;
    ArrayList<CustomCarouselItem> backUpResponsefavo;
    int current_tab;
    IEnviosPaymentPresenter newPaymentPresenter;
    private List<Color> colors;


    public static EnviosFromFragmentNewVersion newInstance(float monto) {
        EnviosFromFragmentNewVersion fragment = new EnviosFromFragmentNewVersion();
        Bundle args = new Bundle();
        montoa=monto;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newPaymentPresenter = new EnviosPaymentPresenter(this, App.getContext());
        current_tab=3;
        backUpResponse = new ArrayList<>();
        backUpResponsefavo= new ArrayList<>();
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(this.current_tab, this, getContext(), false);
        paymentsCarouselPresenter.getCarouselItems();
        paymentsCarouselPresenter.getFavoriteCarouselItems();



    }
    private void obtenerfavoritos() {
        onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
        newPaymentPresenter.getFavoritesItems(TYPE_PAYMENT);
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
        montotosend.setText(String.format("%s", StringUtils.getCurrencyValue(montoa)));


        initColors();


        recyclerView.setAdapter(new MaterialPaletteAdapter(colors, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                //    Toast toast = Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT);
                //  int color = android.graphics.Color.parseColor(colors.get(position).getHex());
                // toast.getView().setBackgroundColor(color);
                // toast.show();
            }
        }));

        //VERTICAL
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        //HORIZONTAL
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        editListServ.setDrawableImage(R.drawable.menu_canvas);
        editListServ.imageViewIsGone(false);
        editListServ.setEnabled(false);
        editListServ.setFullOnClickListener(this);
        btnenviar.setOnClickListener(this);
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
                if (actionid== EditorInfo.IME_ACTION_NEXT) {
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

    }

    private void initColors() {
        colors = new ArrayList<Color>();


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
                ListServDialogFragment dialogFragment = ListServDialogFragment.newInstance(backUpResponse);
                dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                dialogFragment.setOnListServiceListener(this);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "FragmentDialog");
                break;

            case R.id.layoutImageContact :
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                getActivity().startActivityForResult(contactPickerIntent, CONTACTS_CONTRACT);
            break;

            default:
                break;
        }


    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public void onTextChanged() {

    }

    @Override
    public void onTextComplete() {

    }

    @Override
    public void showError() {

        if (errorText != null && !errorText.equals("")) {
            //Toast.makeText(getContext(), errorText, Toast.LENGTH_SHORT).show();
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

            } else if (errorText.equals(App.getContext().getString(R.string.new_body_envios_importe_error))) {
                errorTittle = App.getContext().getString(R.string.new_tittle_envios_importe_empty_error);

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

    }

    @Override
    public void onFailGetTitulaName() {

    }

    @Override
    public void showLoader(String text) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    protected void continuePayment() {
        if (!isCuentaValida) {
            Formatter formatter = new Formatter();
            showError(formatter.format(getString(R.string.error_cuenta_no_valida), tipoEnvio.getSelectedItem().toString()).toString());
            formatter.close();

        } else if (!isValid) {
            showError();
        } else {
            //Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
            //Se debe crear un objeto que se envía a la activity que realizará el pago
            payment = new Envios(selectedType, referencia, monto, nombreDestinatario, concepto, referenciaNumber, comercioItem,
                    favoriteItem != null);
            sendPayment();
        }

    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {
        setBackUpResponse(response);
    }

    @Override
    public void setCarouselDataFavoritos(ArrayList<CarouselItem> response) {
        setBackUpResponsefavo(response);


    }
    private void setBackUpResponsefavo(ArrayList<CarouselItem> mResponse) {
        for (CarouselItem carouselItem : mResponse) {
            if (carouselItem.getComercio() != null) {
                backUpResponsefavo.add(new CustomCarouselItem(

                        carouselItem.getComercio().getIdComercio(),
                        carouselItem.getComercio().getIdTipoComercio(),
                        carouselItem.getComercio().getNombreComercio(),
                        carouselItem.getComercio().getFormato(),
                        carouselItem.getComercio().getLongitudReferencia()
                ));
            }
        }
        Collections.sort(backUpResponse, new Comparator<CustomCarouselItem>() {
            @Override
            public int compare(CustomCarouselItem o1, CustomCarouselItem o2) {
                return o1.getNombreComercio().compareToIgnoreCase(o2.getNombreComercio());
            }
        });
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
        Collections.sort(backUpResponse, new Comparator<CustomCarouselItem>() {
            @Override
            public int compare(CustomCarouselItem o1, CustomCarouselItem o2) {
                return o1.getNombreComercio().compareToIgnoreCase(o2.getNombreComercio());
            }
        });
    }
    @Override
    public void showErrorService() {

    }

    @Override
    public void showFavorites() {



    }

    @Override
    public void onListServiceListener(CustomCarouselItem item) {
        //  Toast.makeText(this, "Item " + item.getNombreComercio(), Toast.LENGTH_SHORT).show();
        editListServ.setText(item.getNombreComercio());
        idTipoComercio = item.getIdTipoComercio();
        idComercio = item.getIdComercio();


        // Variables necesarioas para agregar el formato de captura de telefono o referencia
        formatoComercio = item.getFormatoComercio();
        longitudRefer = item.getLongitudRefer();

        /**
         * Mostramos el area de referencia que sea necesario al hacer Set en un servicio
         * Esto se controlar con la posicion del Tab que seleccionamos
         */
         if (current_tab == 3) {
             LinearLayout taeLL = (LinearLayout) getActivity().findViewById(R.id.add_favorites_list_serv);
             taeLL.setVisibility(View.VISIBLE);

          //  initEnviosPrefer();
        }
        if (idComercio==IDCOMERCIO_YA_GANASTE){
            SpinnerArrayAdapter dataAdapter;
            if (tipoPago.size()==5) {
                tipoPago.remove(NUMERO_TARJETA.getId());
                dataAdapter = new SpinnerArrayAdapter(getContext(), Constants.PAYMENT_ENVIOS, tipoPago);
                tipoEnvio.setAdapter(dataAdapter);
            }else {

            }

        }else {
            SpinnerArrayAdapter dataAdapter;

            if (tipoPago.size()==4) {

                tipoPago.add(NUMERO_TARJETA.getId(), NUMERO_TARJETA.getName(getContext()));

                dataAdapter = new SpinnerArrayAdapter(getContext(), Constants.PAYMENT_ENVIOS, tipoPago);
                tipoEnvio.setAdapter(dataAdapter);
            }else {


            }
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        layout_cardNumber.setVisibility(View.VISIBLE);
        cardNumber.setText("");
        cardNumber.removeTextChangedListener();
        InputFilter[] fArray = new InputFilter[1];

        if (position == NUMERO_TARJETA.getId()) {
            maxLength = 19;
            cardNumber.setHint(getString(R.string.card_number, String.valueOf(16)));
            NumberCardTextWatcher numberCardTextWatcher = new NumberCardTextWatcher(cardNumber, maxLength);
            if (keyIdComercio == IDCOMERCIO_YA_GANASTE) {
                numberCardTextWatcher.setOnITextChangeListener(this);
            }
            cardNumber.addTextChangedListener(numberCardTextWatcher);
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
            selectedType = NUMERO_TARJETA;
            if (favoriteItem != null && favoriteItem.getReferencia().length() == 16) {
                referenceFavorite = favoriteItem.getReferencia();
            }
        } else if (position == NUMERO_TELEFONO.getId()) {
            maxLength = 12;
            cardNumber.setHint(getString(R.string.transfer_phone_cellphone));
            layoutImageContact.setVisibility(View.VISIBLE);
            layoutImageContact.setOnClickListener(this);
            PhoneTextWatcher phoneTextWatcher = new PhoneTextWatcher(cardNumber);
            if (keyIdComercio == IDCOMERCIO_YA_GANASTE) {
                phoneTextWatcher.setOnITextChangeListener(this);
            }
            cardNumber.addTextChangedListener(phoneTextWatcher);
            selectedType = NUMERO_TELEFONO;
            if (favoriteItem != null && favoriteItem.getReferencia().length() == 10) {
                referenceFavorite = favoriteItem.getReferencia();
            }
        } else if (position == CLABE.getId() && keyIdComercio != IDCOMERCIO_YA_GANASTE) {
            maxLength = 22;
            cardNumber.setHint(getString(R.string.transfer_cable));
            NumberClabeTextWatcher textWatcher = new NumberClabeTextWatcher(cardNumber);
            cardNumber.addTextChangedListener(textWatcher);
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
            selectedType = CLABE;
            if (favoriteItem != null && favoriteItem.getReferencia().length() == 18) {
                referenceFavorite = favoriteItem.getReferencia();
            }
       /* } else if (position == QR_CODE.getId() && keyIdComercio == IDCOMERCIO_YA_GANASTE){
            Intent intent = new Intent(getActivity(), ScannVisionActivity.class);
            intent.putExtra(ScannVisionActivity.QRObject, true);
            getActivity().startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);*/
        } else {
            maxLength = 2;
            cardNumber.setHint("");
            layout_cardNumber.setVisibility(GONE);
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
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
                    Log.e(getString(R.string.app_name), "QRCode Value: "+barcode.displayValue);
                }
            }
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
        try{
            int oneNumbre = Integer.parseInt(nameDisplay.substring(0,2));
            receiverName.setText("");
        }catch (Exception e){
            receiverName.setText(nameDisplay);
        }
    }
}
