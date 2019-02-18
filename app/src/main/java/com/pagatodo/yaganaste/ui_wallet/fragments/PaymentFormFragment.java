package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.pagatodo.view_manager.components.HeadAccount;
import com.pagatodo.view_manager.components.HeadWallet;
import com.pagatodo.view_manager.controllers.dataholders.HeadAccountData;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.RecargasPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IRecargasPresenter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPaymentFromFragment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPresenterPaymentFragment;
import com.pagatodo.yaganaste.ui_wallet.presenter.PresenterPaymentFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.NumberReferenceTextWatcher;
import com.pagatodo.yaganaste.utils.NumberTagPase;
import com.pagatodo.yaganaste.utils.NumberTextWatcher;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;

import java.util.EventListener;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.RESUL_FAVORITES;
import static com.pagatodo.yaganaste.utils.Constants.AVON;
import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.CABLEV;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.IAVE_ID;
import static com.pagatodo.yaganaste.utils.Constants.IECISA;
import static com.pagatodo.yaganaste.utils.Constants.MAFER;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_RECARGAS;
import static com.pagatodo.yaganaste.utils.Constants.SKY;
import static com.pagatodo.yaganaste.utils.Constants.TELMEXSR;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link PaymentFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFormFragment extends GenericFragment implements PaymentsManager,
        IPaymentFromFragment, View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";

    private Comercio comercioResponse;
    private Favoritos favoritos;

    @BindView(R.id.head_account_payments)
    HeadAccount headAccount;

    @BindView(R.id.head_wallet_payments)
    HeadWallet headWallet;

    private View rootView;
    @BindView(R.id.txt_title_payment)
    StyleTextView txtTitleFragment;
    @BindView(R.id.imgPagosUserProfile)
    CircleImageView imgUserPhoto;
    @BindView(R.id.imgPagosServiceToPayRound)
    CircleImageView circuleDataPhoto;
    @BindView(R.id.imgItemGalleryPay)
    ImageView imageDataPhoto;
    @BindView(R.id.txt_username_payment)
    StyleTextView txtNameUser;
    @BindView(R.id.txt_data)
    StyleTextView txtData;
    @BindView(R.id.txt_saldo)
    MontoTextView txtSaldo;
    @BindView(R.id.txt_monto)
    MontoTextView txtMonto;
    @BindView(R.id.btn_continue_payment)
    StyleButton btnContinue;
    @BindView(R.id.textmontorecarga)
    StyleTextView textmontorecarga;


    /* RECARGAS BLOCK */
    @BindView(R.id.containerRecargaForm)
    LinearLayout lytContainerRecargas;
    @BindView(R.id.recargaNumber)
    EditText edtPhoneNumber;
    @BindView(R.id.imgMakePaymentContact)
    ImageView imgContacts;
    @BindView(R.id.sp_montoRecarga)
    Spinner spnMontoRecarga;
    @BindView(R.id.comisionTextRecarga)
    StyleTextView comisionTextRecarga;
    @BindView(R.id.layoutImageContact)
    RelativeLayout layoutImageContact;
    @BindView(R.id.layoutImageReferenceIAVE)
    RelativeLayout layoutImageReferenceIAVE;

    /***/

    /* SERVICIOS BLOCK */
    @BindView(R.id.containerServiciosForm)
    LinearLayout lytContainerServicios;
    @BindView(R.id.referenceNumber)
    EditText edtReferenceNumber;
    @BindView(R.id.imgMakePaymentRef)
    ImageView imgReferencePayment;
    @BindView(R.id.serviceImport)
    EditText edtServiceImport;
    @BindView(R.id.serviceConcept)
    EditText edtServiceConcept;
    @BindView(R.id.comisionTextServicio)
    StyleTextView txtComisionServicio;
    @BindView(R.id.txtComision)
    StyleTextView txtComision;

    @BindView(R.id.til_num_telefono)
    TextInputLayout til_num_telefono;

    @BindView(R.id.txt_num_telefono)
    LinearLayout txt_num_telefono;
    @BindView(R.id.txt_referenciaserv)
    LinearLayout txt_referenciaserv;
    @BindView(R.id.txt_monotserv)
    LinearLayout txt_monotserv;
    @BindView(R.id.txtconcepto)
    LinearLayout txtconcepto;

    @BindView(R.id.imgmonto)
    ImageView imgmonto;


    @BindView(R.id.til_num_telefono2)
    TextInputLayout til_num_telefono2;

    @BindView(R.id.txtInicialesFav)
    StyleTextView txtIniciales;

    boolean isRecarga = false;
    boolean isIAVE;
    boolean noCamara;
    private int maxLength;
    Double monto;
    String errorText;
    String referencia;
    boolean isValid = false;
    Payments payment;
    String concepto;

    private SpinnerArrayAdapter dataAdapter;
    private IRecargasPresenter recargasPresenter;
    private IPresenterPaymentFragment iPresenterPayment;
    private boolean isFavorite;


    public PaymentFormFragment() {
    }

    public static PaymentFormFragment newInstance(Comercio param1) {
        PaymentFormFragment fragment = new PaymentFormFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static PaymentFormFragment newInstance(Favoritos param1) {
        PaymentFormFragment fragment = new PaymentFormFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Creamos el presentes del favorito
        iPresenterPayment = new PresenterPaymentFragment(this);
        isFavorite = false;
        /**
         * Sin importar que sea un proceso de comercioResponse o favoritos, siempre trabaajreos con
         * ccomercioResponse. En el caso de favorito, accesamos a las propiedades del comercio y lo
         * asignamos
         */
        if (getArguments() != null) {
            // Verifiamos si es una recarga o un pds

            if (getArguments().getSerializable(ARG_PARAM1) instanceof Favoritos) {
                favoritos = (Favoritos) getArguments().getSerializable(ARG_PARAM1);
                if (favoritos != null) {
                    if (favoritos.getIdFavorito() >= 0) {
                        comercioResponse = iPresenterPayment.getComercioById(favoritos.getIdComercio());
                    }
                    if (favoritos.getIdTipoComercio() == 1) {
                        isRecarga = true;
                    }
                }
                isFavorite = true;
            } else {
                isFavorite = false;
                comercioResponse = (Comercio) getArguments().getSerializable(ARG_PARAM1);
                if (comercioResponse != null) {
                    if (comercioResponse.getIdTipoComercio() == 1) {
                        isRecarga = true;
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_form, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnContinue.setOnClickListener(this);
        // edtPhoneNumber.setCursorVisible(true);


        imgmonto.setOnClickListener(view -> spnMontoRecarga.performClick());

        edtPhoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                txt_num_telefono.setBackgroundResource(R.drawable.inputtext_active);

            } else {
                txt_num_telefono.setBackgroundResource(R.drawable.inputtext_normal);

            }
        });

        edtServiceConcept.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                txtconcepto.setBackgroundResource(R.drawable.inputtext_active);
            } else {
                txtconcepto.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });


        // Procesos para Recargas, sin importar si es carrier o favorito
        if (comercioResponse != null) {
            noCamara = (comercioResponse.getIdComercio() == IECISA || comercioResponse.getIdComercio() == AVON || comercioResponse.getIdComercio() == CABLEV || comercioResponse.getIdComercio() == SKY || comercioResponse.getIdComercio() == TELMEXSR || comercioResponse.getIdComercio() == MAFER);
            if (comercioResponse.getIdTipoComercio() == PAYMENT_RECARGAS) {
                btnContinue.setText(getResources().getString(R.string.btn_recharge_txt));

                txtTitleFragment.setText(getResources().getString(R.string.txt_recargas));
                headAccount.setTag(getResources().getString(R.string.txt_recargas));


                lytContainerRecargas.setVisibility(View.VISIBLE);

                edtPhoneNumber.setLongClickable(true);
                edtPhoneNumber.setSingleLine();
                /**
                 * Cargamos el nombre del Carrier, imagen y borde
                 * tipoPhoto 1 = Favorito 2 = Carrier
                 */

                int tipoPhoto;
                String nameRefer;
                if (favoritos != null) {
                    tipoPhoto = 1;
                    nameRefer = favoritos.getNombre();
                } else {
                    tipoPhoto = 2;
                    nameRefer = comercioResponse.getNombreComercio();
                }
                //setImagePicasoFav(imageDataPhoto, circuleDataPhoto, tipoPhoto);
                txtData.setText(nameRefer);

                isIAVE = comercioResponse.getIdComercio() == IAVE_ID;


                recargasPresenter = new RecargasPresenter(this, isIAVE);

                List<Double> montos = comercioResponse.getListaMontos();
                if (montos.get(0) != 0D) {
                    montos.add(0, 0D);
                }

                dataAdapter = new SpinnerArrayAdapter(getContext(), Constants.PAYMENT_RECARGAS, montos);

                if (comercioResponse.getFormato().equals("N")) {
                    edtPhoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edtPhoneNumber.setSingleLine();
                }

                int longitudReferencia = comercioResponse.getLongitudReferencia();
                if (longitudReferencia > 0 && longitudReferencia != 10) {
                    InputFilter[] fArray = new InputFilter[1];
                    maxLength = Utils.calculateFilterLength(longitudReferencia);
                    fArray[0] = new InputFilter.LengthFilter(maxLength);
                    edtPhoneNumber.setFilters(fArray);
                }

                if (isIAVE) {
                    til_num_telefono.setHint(getString(R.string.hint_tag) + " (" + longitudReferencia + " Dígitos)");
                    edtPhoneNumber.addTextChangedListener(new NumberTagPase(edtPhoneNumber, maxLength));
                    layoutImageContact.setVisibility(View.GONE);
                    layoutImageReferenceIAVE.setVisibility(View.VISIBLE);
                    layoutImageReferenceIAVE.setOnClickListener(this);
                } else {
                    edtPhoneNumber.addTextChangedListener(new PhoneTextWatcher(edtPhoneNumber));
                    // edtPhoneNumber.setHint(getString(R.string.numero_telefono));
                    layoutImageReferenceIAVE.setVisibility(View.GONE);
                    layoutImageContact.setOnClickListener(this);
                    layoutImageReferenceIAVE.setOnClickListener(null);
                }

                edtPhoneNumber.setSingleLine(true);
                edtPhoneNumber.setOnEditorActionListener((v, actionId, event) -> {
                    if (actionId == IME_ACTION_DONE) {
                        UI.hideKeyBoard(getActivity());
                    }
                    return false;
                });

                if (comercioResponse.getSobrecargo() > 0) {
                    comisionTextRecarga.setText(String.format(getString(R.string.comision_service_payment),
                            StringUtils.getCurrencyValue(comercioResponse.getSobrecargo())));
                } else {
                    comisionTextRecarga.setVisibility(View.GONE);
                }
                spnMontoRecarga.setAdapter(dataAdapter);


                if (favoritos != null) {
                    edtPhoneNumber.setText(favoritos.getReferencia());
                }
                //recargaNumber.setEnabled(false);
                /**
                 *
                 */
                spnMontoRecarga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            monto = (Double) spnMontoRecarga.getSelectedItem();
                            txtMonto.setText("" + Utils.getCurrencyValue(monto));
                            textmontorecarga.setVisibility(View.VISIBLE);
                        } else {
                            txtMonto.setText("" + Utils.getCurrencyValue(0));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } else {
                btnContinue.setText(getResources().getString(R.string.btn_payment_txt));

                txtTitleFragment.setText(getResources().getString(R.string.txt_pago_servicios));
                headAccount.setTag(getResources().getString(R.string.txt_pago_servicios));

                lytContainerServicios.setVisibility(View.VISIBLE);

                edtReferenceNumber.setLongClickable(true);
                edtReferenceNumber.setSingleLine();

                if (noCamara) {
                    imgReferencePayment.setVisibility(View.GONE);
                }


                String nameRefer;
                if (favoritos != null) {

                    nameRefer = favoritos.getNombre();
                } else {

                    nameRefer = comercioResponse.getNombreComercio();
                }
                //setImagePicasoFav(imageDataPhoto, circuleDataPhoto, tipoPhoto);
                txtData.setText(nameRefer);

                imgReferencePayment.setOnClickListener(this);
                edtServiceImport.setLongClickable(true);
                edtServiceImport.setSingleLine();
                NumberTextWatcher textWatcher = new NumberTextWatcher(edtServiceImport);
                edtServiceImport.addTextChangedListener(textWatcher);
                edtServiceImport.setOnKeyListener((v, keyCode, event) -> {
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        textWatcher.deleteText = true;
                    }
                    return false;
                });

                if (comercioResponse.getLongitudReferencia() > 0) {
                    InputFilter[] fArray = new InputFilter[1];
                    maxLength = Utils.calculateFilterLength(comercioResponse.getLongitudReferencia());
                    fArray[0] = new InputFilter.LengthFilter(maxLength);
                    edtReferenceNumber.setFilters(fArray);
                }

                if (comercioResponse.getFormato().equals("AN")) {
                    edtReferenceNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                edtReferenceNumber.addTextChangedListener(new NumberReferenceTextWatcher(edtReferenceNumber, maxLength));
                if (comercioResponse.getSobrecargo() > 0) {
                    txtComisionServicio.setText(StringUtils.getCurrencyValue(comercioResponse.getSobrecargo()));
                } else {
                    txtComision.setVisibility(View.INVISIBLE);
                    txtComisionServicio.setVisibility(View.INVISIBLE);
                }
                if (favoritos != null) {
                    edtReferenceNumber.setText(favoritos.getReferencia());
                    //edtReferenceNumber.setEnabled(false);
                }

                edtServiceConcept.setLongClickable(true);
                edtServiceConcept.setSingleLine();
                edtServiceImport.setOnEditorActionListener((textView, actionid, keyEvent) -> {
                    if (actionid == EditorInfo.IME_ACTION_NEXT) {
                        // si pasamos al siguiente item

                        edtServiceConcept.setFocusable(true);
                        edtServiceConcept.requestFocus();

                  /*      final ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                edtServiceConcept.setFocusable(true);
                                edtServiceConcept.requestFocus();
                            }
                        });
                        return true; // Focus will do whatever you put in the logic.)*/
                    }
                    return false;
                });

                // Agregamos un setOnFocusChangeListener a nuestro campo de importe, solo si es un favorito


                edtReferenceNumber.setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        txt_referenciaserv.setBackgroundResource(R.drawable.inputtext_active);
                    } else {
                        txt_referenciaserv.setBackgroundResource(R.drawable.inputtext_normal);

                    }
                });

                edtServiceImport.setOnFocusChangeListener((v, hasFocus) -> {
                    if (!hasFocus) {
                        // Toast.makeText(App.getContext(), "Foco fuera", Toast.LENGTH_SHORT).show();
                        txt_monotserv.setBackgroundResource(R.drawable.inputtext_normal);

                        if (edtServiceImport.getText().length() > 0) {
                            String serviceImportStr = edtServiceImport.getText().toString().substring(1).replace(",", "");

                            if (serviceImportStr != null && !serviceImportStr.isEmpty()) {
                                monto = Double.valueOf(serviceImportStr);
                                txtMonto.setText("" + Utils.getCurrencyValue(monto));
                            } else {
                                txtMonto.setText("" + Utils.getCurrencyValue(0.0));
                            }
                        }
                    } else {
                        txt_monotserv.setBackgroundResource(R.drawable.inputtext_active);
                    }

                });


            }
        }

        /**
         * Iniciamos el monto en cero, Mostramos la informacion del usuario, foto y saldo
         */

        headWallet.setAmount("" + StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)));

        String imgUrl = "";
        String labelTag = "";
        String labelName = "";
        String labelRef ="";
        if (favoritos != null){
            imgUrl = favoritos.getImagenURL();
            labelName = favoritos.getNombre();
            labelRef = favoritos.getReferencia();


        }else if (comercioResponse != null){
            imgUrl = comercioResponse.getImagenURL();
            labelName = comercioResponse.getNombreComercio();
        }

        headAccount.setImageURL(imgUrl);


        txtMonto.setText("" + Utils.getCurrencyValue(0));
        SingletonUser dataUser = SingletonUser.getInstance();
        txtNameUser.setText("" + dataUser.getDataUser().getCliente().getNombre());
        txtSaldo.setText("" + StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)));
        String imagenavatar = dataUser.getDataUser().getUsuario().getImagenAvatarURL();
        if (!imagenavatar.equals("")) {
            Picasso.get()
                    .load(imagenavatar)
                    .placeholder(R.mipmap.icon_user_fail)
                    .into(imgUserPhoto);
        }

        if (isFavorite) {
            if (!favoritos.getImagenURL().equals("")) {
                Picasso.get()
                        .load(favoritos.getImagenURL())
                        .placeholder(R.mipmap.icon_user_fail)
                        .into(circuleDataPhoto);
                txtIniciales.setVisibility(View.GONE);
            } else {
                txtIniciales.setVisibility(View.VISIBLE);
                txtIniciales.setText(getIniciales(favoritos.getNombre()));
                GradientDrawable gd = createCircleDrawable(android.graphics.Color.parseColor(favoritos.getColorMarca()),
                        android.graphics.Color.parseColor(favoritos.getColorMarca()));
                circuleDataPhoto.setBackground(gd);
            }



        } else {



            if (!comercioResponse.getLogoURLColor().equals("")) {
                Picasso.get()
                        .load(App.getContext().getString(R.string.url_images_logos) + comercioResponse.getLogoURLColor())
                        .placeholder(R.mipmap.icon_user_fail)
                        .into(imageDataPhoto);
            }
            txtIniciales.setVisibility(View.GONE);

            //setImagePicasoFav(imageDataPhoto, circuleDataPhoto, tipoPhoto);

        }

    }

    private String getIniciales(String fullName) {
        if (fullName.trim().length() == 1) {
            return fullName.substring(0, 1).toUpperCase();
        }

        String[] spliName = fullName.split(" ");
        String sIniciales = "";
        if (spliName.length > 1) {
            sIniciales = spliName[0].substring(0, 1) + spliName[1].substring(0, 1).toUpperCase();
            return sIniciales;
        }

        if (fullName.trim().length() > 1) {
            return fullName.substring(0, 2).toUpperCase();
        }

        return "";
    }

    private GradientDrawable createCircleDrawable(int colorBackground, int colorBorder) {
        // Creamos el circulo que mostraremos
        int strokeWidth = 2; // 3px not dp
        int roundRadius = 140; // 8px not dp
        int strokeColor = colorBorder;
        int fillColor = colorBackground;
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }


    /*private void setImagePicasoFav(ImageView imageDataPhoto, CircleImageView circuleDataPhoto, int mType) {
        if (mType == 1) {
            String mPhoto = favoritos.getImagenURL();
            if (!mPhoto.equals("")) {
                Picasso.with(App.getContext())
                        .load(mPhoto)
                        .placeholder(R.mipmap.icon_user_fail)
                        .into(circuleDataPhoto);
            }
            //  circuleDataPhoto.setBorderColor(Color.parseColor(favoritos.getColorMarca()));
        }

        if (mType == 2) {
            String mPhoto = comercioResponse.getLogoURLColor();
            if (!mPhoto.equals("")) {
                Picasso.with(App.getContext())
                        .load(App.getContext().getString(R.string.url_images_logos) + mPhoto)
                        .placeholder(R.mipmap.icon_user_fail)
                        .into(imageDataPhoto);
            }
            //  circuleDataPhoto.setBorderColor(Color.parseColor(comercioResponse.getColorMarca()));
        }
    }*/

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layoutImageContact) {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            Objects.requireNonNull(getActivity()).startActivityForResult(contactPickerIntent, CONTACTS_CONTRACT);
        }

        switch (v.getId()) {
            case R.id.btn_continue_payment:
                if (isRecarga) {
                    referencia = edtPhoneNumber.getText().toString().trim();
                    referencia = referencia.replaceAll(" ", "");
                    monto = (Double) spnMontoRecarga.getSelectedItem();
                    recargasPresenter.validateFields(referencia, monto, comercioResponse.getLongitudReferencia(), isIAVE);

                } else {
                    referencia = edtReferenceNumber.getText().toString().replaceAll(" ", "");
                    //concepto = txtComisionServicio.getText().toString().trim();
                    concepto = edtServiceConcept.getText().toString().trim();
                    iPresenterPayment.validateFieldsCarrier(referencia, edtServiceImport.getText().toString().trim(),
                            concepto, comercioResponse.getLongitudReferencia());
                }
                break;

            case R.id.imgMakePaymentRef:
            case R.id.layoutImageReferenceIAVE:
                Intent intent = new Intent(getActivity(), ScannVisionActivity.class);
                getActivity().startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
                break;
        }
    }

    /**
     * Obtenemos los resultados de onActivityResult desde PaymentActivity. LAs respuestas de los servicios
     * y tomar un contacto de la agenda
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CONTACTS_CONTRACT) {
                contactPicked(data);
            }
        } else if (requestCode == BACK_FROM_PAYMENTS) {
            if (resultCode == Constants.RESULT_CODE_OK_CLOSE || resultCode == RESUL_FAVORITES) {
                getActivity().setResult(RESUL_FAVORITES);
                getActivity().finish();
            }

        /* Eliminar este Codigo cuando ya no se usen en los errores
        else {

                // Mostramos los errores por medio de un dialogo, siempre resultCode 190
                try {
                    Bundle MBuddle = data.getExtras();
                    String MMessage = MBuddle.getString(MESSAGE);
                    String resultError = MBuddle.getString(RESULT_ERROR);
                    if (!resultError.equals(RESULT_ERROR)) {
                        //  UI.showErrorSnackBar(getActivity(), MMessage);
                        UI.createSimpleCustomDialog(
                                App.getInstance().getResources().getString(R.string.new_tittle_error_interno),
                                MMessage,
                                getActivity().getSupportFragmentManager(), getFragmentTag());
                    }
                } catch (Exception e) {
                }
            }*/
        } else if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    if (isIAVE) {
                        edtPhoneNumber.setText(barcode.displayValue);
                    } else {
                        edtReferenceNumber.setText(barcode.displayValue);
                    }
                }
            }
        }


    }

    private void contactPicked(Intent data) {
        Cursor cursor;
        String phoneNo = null;
        Uri uri = data.getData();
        cursor = getContext().getContentResolver().query(uri, null, null, null, null);
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
        edtPhoneNumber.setText(phoneNo);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
            if (errorText.equals(getString(R.string.txt_importe_error))) {
                errorTittle = App.getContext().getResources().getString(R.string.txt_importe_error);
                errorText = App.getContext().getResources().getString(R.string.txt_importe_error);
            } else if (errorText.equals(App.getContext().getString(R.string.new_body_IAVE_error))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_recarga_iave_error_empty);
                errorText = App.getContext().getResources().getString(R.string.new_body_recargas_iave_error_empty);
            } else if (errorText.equals(App.getContext().getString(R.string.new_body_phone_error))) {
                errorTittle = App.getContext().getResources().getString(R.string.numero_telefono_incorrecto);
                errorText = App.getContext().getResources().getString(R.string.new_body_phone_error);
            } else if (errorText.equals(App.getContext().getString(R.string.favor_selecciona_importe))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_envios_importe_empty_error);
                errorText = getString(R.string.favor_selecciona_importe);
            } else if (errorText.equals(App.getContext().getString(R.string.numero_iave_vacio))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_recarga_iave_error_empty);
                errorText = App.getContext().getResources().getString(R.string.new_body_recargas_iave_error_empty);
            } else if (errorText.equals(App.getContext().getString(R.string.numero_telefono_vacio))) {
                errorTittle = App.getContext().getResources().getString(R.string.phone_invalid);
                errorText = App.getContext().getResources().getString(R.string.new_body_recargas_phone_error);
            } else if (errorText.equals(App.getContext().getResources().getString(R.string.txt_referencia_empty))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_envios_refer_error);
                errorText = App.getContext().getResources().getString(R.string.txt_referencia_empty);
            } else if (errorText.equals(App.getContext().getResources().getString(R.string.mount_valid))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_body_envios_importe_empty_error);
                errorText = App.getContext().getResources().getString(R.string.mount_valid);
            } else if (errorText.equals(App.getContext().getResources().getString(R.string.txt_concept_empty))) {
                errorTittle = App.getContext().getResources().getString(R.string.txt_concept_empty);
                errorText = App.getContext().getResources().getString(R.string.txt_concept_empty);
            } else if (errorText.equals(getString(R.string.txt_referencia_errornuevo))) {
                errorTittle = App.getContext().getResources().getString(R.string.txt_referencia_errornuevo);
                errorText = App.getContext().getResources().getString(R.string.txt_referencia_errornuevo);
            }
            //UI.createSimpleCustomDialog(errorTittle, errorText, getActivity().getSupportFragmentManager(), getFragmentTag());
            UI.showErrorSnackBar(getActivity(), errorText, Snackbar.LENGTH_SHORT);
        }
    }

    /**
     * * Errores del servicio de Recargas
     *
     * @param error
     */
    @Override
    public void onError(String error) {
        //mySeekBar.setEnabled(false);
        isValid = false;
        errorText = error;
        showError();
    }

    /**
     * Errores del servicio de PDS
     *
     * @param error
     */
    @Override
    public void onErrorValidateService(String error) {
        //mySeekBar.setEnabled(false);
        isValid = false;
        errorText = error;
        showError();
    }

    /**
     * Success del presentes de PDS
     *
     * @param importe
     */
    @Override
    public void onSuccessValidateService(Double importe) {
        if (importe > 0) {
            isValid = true;
        }

        if (!isValid) {
            showError();
        } else {
            this.monto = importe;
            isValid = true;

            payment = new Servicios(referencia, monto, concepto, comercioResponse,
                    favoritos != null);
            sendPayment();
        }
    }

    /**
     * Success del presenter de  Recargas
     *
     * @param importe
     */
    @Override
    public void onSuccess(Double importe) {
        if (importe > 0) {
            isValid = true;
        }
        if (!isValid) {
            showError();
        } else {
            boolean isOnline = Utils.isDeviceOnline();
            if (isOnline) {
                this.monto = importe;
                isValid = true;

                payment = new Recarga(referencia, monto, comercioResponse, favoritos != null);
                sendPayment();
            } else {
                //UI.createSimpleCustomDialog("Error Interno", getResources().getString(R.string.no_internet_access), getActivity().getSupportFragmentManager(), getFragmentTag());
                UI.showAlertDialog(getContext(), "Error Interno", getResources().getString(R.string.no_internet_access),
                        R.string.title_aceptar, (dialogInterface, i) -> {
                        });
            }
        }
    }

    /**
     * Envio a la actividad PaymentsProcessingActivity del resultado, sea una Recarga o PDS
     */
    protected void sendPayment() {



        Intent intent = new Intent(getContext(), PaymentsProcessingActivity.class);
        intent.putExtra("pagoItem", payment);
        if (isRecarga) {
            intent.putExtra("TAB", Constants.PAYMENT_RECARGAS);
        } else {
            intent.putExtra("TAB", Constants.PAYMENT_SERVICIOS);
        }
        SingletonSession.getInstance().setFinish(false);//No cerramos la aplicación
        getActivity().startActivityForResult(intent, BACK_FROM_PAYMENTS);
        //getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}

