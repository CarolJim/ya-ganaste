package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataTitular;
import com.pagatodo.yaganaste.interfaces.OnListServiceListener;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.EnviosManager;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IEnviosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.NumberTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ListServDialogFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;
import com.pagatodo.yaganaste.utils.customviews.carousel.CustomCarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import static com.pagatodo.yaganaste.utils.Recursos.IDCOMERCIO_YA_GANASTE;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;
import static com.pagatodo.yaganaste.utils.StringUtils.getCreditCardFormat;

/**
 * Created by Armando Sandoval on 03/01/2018.
 */

public class EnviosFromFragmentNewVersion extends PaymentFormBaseFragment implements
        EnviosManager,TextView.OnEditorActionListener, View.OnClickListener, PaymentsCarrouselManager, IListaOpcionesView ,OnListServiceListener

{

    @BindView(R.id.tipoEnvio)
    Spinner tipoEnvio;
    @BindView(R.id.cardNumber)
    StyleEdittext cardNumber;
    @BindView(R.id.layout_cardNumber)
    LinearLayout layout_cardNumber;
    @BindView(R.id.amountToSend)
    EditText amountToSend;
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
    MovementsTab current_tab2;
    TransferType selectedType;
    IEnviosPresenter enviosPresenter;
    private String nombreDestinatario;
    private String referenciaNumber, referenceFavorite;
    int keyIdComercio;
    int maxLength;
    private boolean isCuentaValida = true;
    PaymentsTabFragment fragment;
    ArrayList<CustomCarouselItem> backUpResponse;
    int current_tab;


    public static EnviosFromFragmentNewVersion newInstance() {
        EnviosFromFragmentNewVersion fragment = new EnviosFromFragmentNewVersion();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.current_tab2 = MovementsTab.getMovementById(3);
        backUpResponse = new ArrayList<>();
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(this.current_tab2, this, getContext(), false);
        paymentsCarouselPresenter.getCarouselItems();
        current_tab=3;

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

        List<String> tipoPago = new ArrayList<>();

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
            amountToSend.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            amountToSend.setOnEditorActionListener(this);
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
        SpinnerArrayAdapter dataAdapter = new SpinnerArrayAdapter(getContext(), TAB3, tipoPago);
        tipoEnvio.setAdapter(dataAdapter);
        //tipoEnvio.setOnItemSelectedListener(this);
        amountToSend.addTextChangedListener(new NumberTextWatcher(amountToSend));
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


        // Agregamos un setOnFocusChangeListener a nuestro campo de importe, solo si es un favorito
        if (favoriteItem != null) {
            amountToSend.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            fragment.updateValueTabFrag(0.0);
            amountToSend.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        // Toast.makeText(App.getContext(), "Tiene foco", Toast.LENGTH_SHORT).show();
                    } else {
                        // Toast.makeText(App.getContext(), "Foco fuera", Toast.LENGTH_SHORT).show();
                        try {
                            String serviceImportStr = amountToSend.getText().toString().substring(1).replace(",", "");
                            if (serviceImportStr != null && !serviceImportStr.isEmpty()) {
                                monto = Double.valueOf(serviceImportStr);
                                fragment.updateValueTabFrag(monto);
                            } else {
                                fragment.updateValueTabFrag(0.0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
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
                dialogFragment.show(getFragmentManager(), "FragmentDialog");
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

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess(Double importe) {

    }

    @Override
    public void showError(String text) {

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

    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {
        setBackUpResponse(response);
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
    public void setPhotoToService(Bitmap bitmap) {

    }

    @Override
    public void showProgress(String mMensaje) {

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

    @Override
    public void onListServiceListener(CustomCarouselItem item) {

    }
}
