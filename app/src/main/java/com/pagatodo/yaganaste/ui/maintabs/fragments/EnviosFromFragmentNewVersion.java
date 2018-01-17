package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataTitular;
import com.pagatodo.yaganaste.interfaces.OnListServiceListener;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
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
import com.pagatodo.yaganaste.ui_wallet.adapters.RecyclerViewOnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.presenter.EnviosPaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.IEnviosPaymentPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.NumberCardTextWatcher;
import com.pagatodo.yaganaste.utils.NumberClabeTextWatcher;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ListServDialogFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;

import butterknife.BindView;

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
import static com.pagatodo.yaganaste.utils.Recursos.IDCOMERCIO_YA_GANASTE;
import static com.pagatodo.yaganaste.utils.ValidateForm.AMEX;
import static com.pagatodo.yaganaste.utils.ValidateForm.GENERIC;

/**
 * Created by Armando Sandoval on 03/01/2018.
 */

public class EnviosFromFragmentNewVersion extends PaymentFormBaseFragment implements
        EnviosManager, TextView.OnEditorActionListener, View.OnClickListener, PaymentsCarrouselManager, OnListServiceListener, AdapterView.OnItemSelectedListener

{
    List<String> tipoPago = new ArrayList<>();
    int idTipoComercio;
    int idComercio;
    int idTipoEnvio;
    private String formatoComercio;
    private int longitudRefer;
    @BindView(R.id.tipoEnvio)
    Spinner tipoEnvio;
    Boolean isfavo=false;
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
    ArrayList<CarouselItem> backUpResponsefavo;
    IEnviosPaymentPresenter newPaymentPresenter;
    private boolean isUp;
    String myReferencia;
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
        onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
        paymentsCarouselPresenter.getCarouselItems();
        paymentsCarouselPresenter.getFavoriteCarouselItems();


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
        editListServ.setDrawableImage(R.drawable.menu_canvas);
        editListServ.imageViewIsGone(false);
        editListServ.setEnabled(false);
        editListServ.setFullOnClickListener(this);
        editListServ.setHintText(getString(R.string.details_bank));

        tipoPago.add(0, "");
        tipoPago.add(NUMERO_TELEFONO.getId(), NUMERO_TELEFONO.getName(getContext()));
        tipoPago.add(NUMERO_TARJETA.getId(), NUMERO_TARJETA.getName(getContext()));
        tipoPago.add(CLABE.getId(), CLABE.getName(getContext()));
       // tipoPago.add(QR_CODE.getId(), QR_CODE.getName(getContext()));


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
        onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
        paymentsCarouselPresenter.getCarouselItems();
        paymentsCarouselPresenter.getFavoriteCarouselItems();
        // Ocultamos la vista del Slide en el Layout y hacemos SET Down para mostrar por primera vez
        // referenciaLayout slideViewL1
        slideDown(slideViewLl);
        //firstDown(slideViewLl);
        super.onResume();
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

            case R.id.layoutImageContact:
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                getActivity().startActivityForResult(contactPickerIntent, CONTACTS_CONTRACT);
                break;

            case R.id.envio_from_slide_view:
                onSlideViewButtonClick(slideViewLl);
                break;
            default:
                break;
        }
    }

    // slide the view from below itself to the current position
    public void slideUp(final View view){
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
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        triBlue.setImageResource(R.drawable.triangule_blue_up);

        view.setVisibility(View.GONE);
    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            slideDown(view);
            //myButton.setText("Slide up");
        } else {
            slideUp(view);
            //myButton.setText("Slide down");
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
        } else {



            //Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
            //Se debe crear un objeto que se envía a la activity que realizará el pago
            referencia = cardNumber.getText().toString().trim();
            referencia = referencia.replaceAll(" ", "");
            nombreDestinatario=receiverName.getText().toString();
            concepto = concept.getText().toString().trim();
            nombreDestinatario = receiverName.getText().toString().trim();
            referenciaNumber = numberReference.getText().toString().trim();
            payment = new Envios(selectedType, referencia, montoa, nombreDestinatario, concepto, referenciaNumber, comercioItem,
                    favoriteItem != null);
            sendPayment();
        }


        /*
            referencia = cardNumber.getText().toString().trim();
            referencia = referencia.replaceAll(" ", "");
            nombreDestinatario=receiverName.getText().toString();
            concepto = concept.getText().toString().trim();
            nombreDestinatario = receiverName.getText().toString().trim();
            referenciaNumber = numberReference.getText().toString().trim();
            payment = new Envios(selectedType, referencia, montoa, nombreDestinatario, concepto, referenciaNumber, comercioItem,
                    favoriteItem != null);
            sendPayment();
            */

    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {
        setBackUpResponse(response);
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
                    isfavo=true;
                    long myIdComercio = backUpResponseFavoritos.get(position).getIdComercio();
                    int myIdTipoComercio = backUpResponseFavoritos.get(position).getIdTipoComercio();
                    String myName = backUpResponseFavoritos.get(position).getNombre();
                    myReferencia = backUpResponseFavoritos.get(position).getReferencia();
                    receiverName.setText(myName);

                    //backUpResponseFavoritos
                    /*1 - Con ese id myIdComercio buscamos en backUpResponseFavoritos. Por ejemplo la posicion5
                            2 - Guardar ese dato en una variable CarouselItem2 que solo tenga esa posicion
                            3 - IguLAS comercioItem CON CarouselItem2*/

                            for(int x =0; x<backUpResponse.size(); x++){
                                if(backUpResponse.get(x).getComercio().getIdComercio() == myIdComercio){
                                    comercioItem = backUpResponse.get(x).getComercio();
                                    editListServ.setText(backUpResponse.get(x).getComercio().getNombreComercio());
                                    idTipoComercio = backUpResponse.get(x).getComercio().getIdTipoComercio();
                                    idComercio = backUpResponse.get(x).getComercio().getIdComercio();
                                }
                            }


                    switch (backUpResponseFavoritos.get(position).getReferencia().length()) {
                        case 10:
                            myReferencia = backUpResponseFavoritos.get(position).getReferencia();
                            tipoEnvio.setSelection(NUMERO_TELEFONO.getId());

                            break;
                        case 16:
                            myReferencia = backUpResponseFavoritos.get(position).getReferencia();
                            tipoEnvio.setSelection(NUMERO_TARJETA.getId());
                            break;
                        case 18:
                            myReferencia = backUpResponseFavoritos.get(position).getReferencia();
                            tipoEnvio.setSelection(CLABE.getId());
                            break;
                    }

                }

            }

            @Override
            public void onLongClick(View v, int position) {
                if (backUpResponseFavoritos.size() == 3) {
                    Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    vibrator.vibrate(100);
                    Intent intentEditFav = new Intent(getActivity(), EditFavoritesActivity.class);
                    intentEditFav.putExtra(getActivity().getString(R.string.favoritos_tag), backUpResponsefavo.get(position).getFavoritos());
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
        mResponse=Utils.removeNullCarouselItem(mResponse);
        for (CarouselItem carouselItem : mResponse) {
            backUpResponse.add(carouselItem);
        }

        Collections.sort(backUpResponse, new Comparator<CarouselItem>() {
            @Override
            public int compare(CarouselItem o1, CarouselItem o2) {
                return o1.getComercio().getNombreComercio().compareToIgnoreCase(o2.getComercio().getNombreComercio());
                /*if (o1.getComercio()!=null && o2.getComercio()!=null) {
                    return o1.getComercio().getNombreComercio().compareToIgnoreCase(o2.getComercio().getNombreComercio());
                }else {
                    return 0;
                }
                */
            }

        });

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
            if (isfavo==true && !myReferencia.isEmpty()) {
                referenceFavorite = myReferencia;
                isfavo=false;
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

            if (isfavo==true && !myReferencia.isEmpty()) {
                referenceFavorite = myReferencia;
                isfavo=false;
            }
        } else if (position == CLABE.getId() && keyIdComercio != IDCOMERCIO_YA_GANASTE) {
            maxLength = 22;
            cardNumber.setHint(getString(R.string.transfer_cable));
            NumberClabeTextWatcher textWatcher = new NumberClabeTextWatcher(cardNumber);
            cardNumber.addTextChangedListener(textWatcher);
            layoutImageContact.setVisibility(View.GONE);
            layoutImageContact.setOnClickListener(null);
            selectedType = CLABE;
            if (isfavo==true && !myReferencia.isEmpty()) {
                referenceFavorite = myReferencia;
                isfavo=false;
            }
       /* } else if (position == QR_CODE.getId() && keyIdComercio == IDCOMERCIO_YA_GANASTE){
            Intent intent = new Intent(getActivity(), ScannVisionActivity.class);
            intent.putExtra(ScannVisionActivity.QRObject, true);
            getActivity().startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);*/
        } else {
            maxLength = 2;
            //  cardNumber.setHint("");
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
                    Log.e(getString(R.string.app_name), "QRCode Value: " + barcode.displayValue);
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
        try {
            int oneNumbre = Integer.parseInt(nameDisplay.substring(0, 2));
            receiverName.setText("");
        } catch (Exception e) {
            receiverName.setText(nameDisplay);
        }
    }
}
