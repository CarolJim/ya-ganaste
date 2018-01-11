package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.RecargasPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IRecargasPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.IPaymentFromFragment;
import com.pagatodo.yaganaste.ui_wallet.presenter.IPresenterPaymentFragment;
import com.pagatodo.yaganaste.ui_wallet.presenter.PresenterPaymentFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.NumberTagPase;
import com.pagatodo.yaganaste.utils.NumberTextWatcher;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.IAVE_ID;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_RECARGAS;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link PaymentFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFormFragment extends GenericFragment implements PaymentsManager,
        IPaymentFromFragment, View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";

    private ComercioResponse comercioResponse;
    private DataFavoritos dataFavoritos;

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
    StyleTextView txtSaldo;
    @BindView(R.id.txt_monto)
    StyleTextView txtMonto;
    @BindView(R.id.btn_continue_payment)
    StyleButton btnContinue;

    /* RECARGAS BLOCK */
    @BindView(R.id.containerRecargaForm)
    LinearLayout lytContainerRecargas;
    @BindView(R.id.recargaNumber)
    StyleEdittext edtPhoneNumber;
    @BindView(R.id.imgMakePaymentContact)
    ImageView imgContacts;
    @BindView(R.id.sp_montoRecarga)
    Spinner spnMontoRecarga;
    @BindView(R.id.comisionTextRecarga)
    StyleTextView comisionTextRecarga;
    @BindView(R.id.layoutImageContact)
    RelativeLayout layoutImageContact;
    /***/

    /* SERVICIOS BLOCK */
    @BindView(R.id.containerServiciosForm)
    LinearLayout lytContainerServicios;
    @BindView(R.id.referenceNumber)
    StyleEdittext edtReferenceNumber;
    @BindView(R.id.imgMakePaymentRef)
    ImageView imgReferencePayment;
    @BindView(R.id.serviceImport)
    StyleEdittext edtServiceImport;
    @BindView(R.id.serviceConcept)
    StyleEdittext edtServiceConcept;
    @BindView(R.id.comisionTextServicio)
    StyleTextView txtComisionServicio;

    boolean isRecarga = false;
    boolean isFavorito = false;
    boolean isIAVE;
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

    /***/

    public PaymentFormFragment() {
    }

    public static PaymentFormFragment newInstance(ComercioResponse param1) {
        PaymentFormFragment fragment = new PaymentFormFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static PaymentFormFragment newInstance(DataFavoritos param1) {
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

        if (getArguments() != null) {
            if (getArguments().getSerializable(ARG_PARAM1) instanceof DataFavoritos) {
                dataFavoritos = (DataFavoritos) getArguments().getSerializable(ARG_PARAM1);
                if (dataFavoritos != null) {
                    if (dataFavoritos.getIdFavorito() >= 0) {
                        isFavorito = true;
                        comercioResponse = iPresenterPayment.getComercioById(dataFavoritos.getIdComercio());
                    }
                }
            } else {
                comercioResponse = (ComercioResponse) getArguments().getSerializable(ARG_PARAM1);
                if (comercioResponse != null) {
                    if (comercioResponse.getIdTipoComercio() == 1) {
                        isRecarga = true;
                        isFavorito = false;
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnContinue.setOnClickListener(this);
        if (comercioResponse != null) {
            if (comercioResponse.getIdTipoComercio() == PAYMENT_RECARGAS) {
                txtTitleFragment.setText(getResources().getString(R.string.txt_recargas));
                lytContainerRecargas.setVisibility(View.VISIBLE);

                // Cargamos el nombre del Carrier, imagen y borde
                int tipoPhoto;
                String nameRefer;
                if (dataFavoritos != null) {
                    tipoPhoto = 1;
                    nameRefer = dataFavoritos.getNombre();
                } else {
                    tipoPhoto = 2;
                    nameRefer = comercioResponse.getNombreComercio();
                }
                setImagePicasoFav(imageDataPhoto, circuleDataPhoto, tipoPhoto);
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
                    edtPhoneNumber.addTextChangedListener(new NumberTagPase(edtPhoneNumber, maxLength));
                    edtPhoneNumber.setHint(getString(R.string.tag_number) + " (" + longitudReferencia + " Dígitos)");
                    layoutImageContact.setVisibility(View.GONE);
                } else {
                    edtPhoneNumber.addTextChangedListener(new PhoneTextWatcher(edtPhoneNumber));
                    edtPhoneNumber.setHint(getString(R.string.phone_number_hint));

                    layoutImageContact.setOnClickListener(this);
                }

                edtPhoneNumber.setSingleLine(true);
                edtPhoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == IME_ACTION_DONE) {
                            UI.hideKeyBoard(getActivity());
                        }
                        return false;
                    }
                });

                if (comercioResponse.getSobrecargo() > 0) {
                    comisionTextRecarga.setText(String.format(getString(R.string.comision_service_payment),
                            StringUtils.getCurrencyValue(comercioResponse.getSobrecargo())));
                } else {
                    comisionTextRecarga.setVisibility(View.GONE);
                }
                spnMontoRecarga.setAdapter(dataAdapter);


                if (dataFavoritos != null) {
                    edtPhoneNumber.setText(dataFavoritos.getReferencia());
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
                        } else {
                            txtMonto.setText("" + Utils.getCurrencyValue(0));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } else {
                txtTitleFragment.setText(getResources().getString(R.string.txt_servicios));
                lytContainerServicios.setVisibility(View.VISIBLE);

                int tipoPhoto;
                String nameRefer;
                if (dataFavoritos != null) {
                    tipoPhoto = 1;
                    nameRefer = dataFavoritos.getNombre();
                } else {
                    tipoPhoto = 2;
                    nameRefer = comercioResponse.getNombreComercio();
                }
                setImagePicasoFav(imageDataPhoto, circuleDataPhoto, tipoPhoto);
                txtData.setText(nameRefer);

                imgReferencePayment.setOnClickListener(this);
                edtServiceImport.addTextChangedListener(new NumberTextWatcher(edtServiceImport));

                if (comercioResponse.getLongitudReferencia() > 0) {
                    InputFilter[] fArray = new InputFilter[1];
                    maxLength = Utils.calculateFilterLength(comercioResponse.getLongitudReferencia());
                    fArray[0] = new InputFilter.LengthFilter(maxLength);
                    edtReferenceNumber.setFilters(fArray);
                }

                if (comercioResponse.getFormato().equals("AN")) {
                    edtReferenceNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                edtReferenceNumber.addTextChangedListener(new NumberTagPase(edtReferenceNumber, maxLength));
                if (comercioResponse.getSobrecargo() > 0) {
                    txtComisionServicio.setText(String.format(getString(R.string.comision_service_payment),
                            StringUtils.getCurrencyValue(comercioResponse.getSobrecargo())));
                } else {
                    txtComisionServicio.setVisibility(View.INVISIBLE);
                }
                if (dataFavoritos != null) {
                    edtReferenceNumber.setText(dataFavoritos.getReferencia());
                    //edtReferenceNumber.setEnabled(false);
                }

                edtServiceImport.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionid, KeyEvent keyEvent) {
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
                    }
                });

                // Agregamos un setOnFocusChangeListener a nuestro campo de importe, solo si es un favorito

                //edtServiceImport.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                edtServiceImport.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            // Toast.makeText(App.getContext(), "Foco fuera", Toast.LENGTH_SHORT).show();
                            if (edtServiceImport.getText().length() > 0) {
                                String serviceImportStr = edtServiceImport.getText().toString().substring(1).replace(",", "");

                                if (serviceImportStr != null && !serviceImportStr.isEmpty()) {
                                    monto = Double.valueOf(serviceImportStr);
                                    txtMonto.setText("" + Utils.getCurrencyValue(monto));
                                } else {
                                    txtMonto.setText("" + Utils.getCurrencyValue(0.0));
                                }
                            }
                        }

                    }
                });


            }
        }

        /**
         * Iniciamos el monto en cero, Mostramos la informacion del usuario, foto y saldo
         */
        txtMonto.setText("" + Utils.getCurrencyValue(0));
        SingletonUser dataUser = SingletonUser.getInstance();
        txtNameUser.setText("" + dataUser.getDataUser().getUsuario().getNombre());
        txtSaldo.setText("" + Utils.getCurrencyValue(dataUser.getDatosSaldo().getSaldoEmisor()));
        String imagenavatar = dataUser.getDataUser().getUsuario().getImagenAvatarURL();
        if (!imagenavatar.equals("")) {
            Picasso.with(App.getContext())
                    .load(imagenavatar)
                    .placeholder(R.mipmap.icon_user)
                    .into(imgUserPhoto);
        }


    }

    private void setImagePicasoFav(ImageView imageDataPhoto, CircleImageView circuleDataPhoto, int mType) {
        if (mType == 1) {
            String mPhoto = dataFavoritos.getImagenURL();
            if (!mPhoto.equals("")) {
                Picasso.with(App.getContext())
                        .load(mPhoto)
                        .placeholder(R.mipmap.icon_user)
                        .into(circuleDataPhoto);
            }
            circuleDataPhoto.setBorderColor(Color.parseColor(dataFavoritos.getColorMarca()));
        }

        if (mType == 2) {
            String mPhoto = comercioResponse.getLogoURL();
            if (!mPhoto.equals("")) {
                Picasso.with(App.getContext())
                        .load(App.getContext().getString(R.string.url_images_logos) + mPhoto)
                        .into(imageDataPhoto);
            }
            circuleDataPhoto.setBorderColor(Color.parseColor(comercioResponse.getColorMarca()));
        }


        /*Glide.with(App.getContext()).load(urlLogo).placeholder(R.mipmap.icon_user)
                .error(R.mipmap.ic_launcher)
                .dontAnimate().into(imageView);*/
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layoutImageContact) {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            getActivity().startActivityForResult(contactPickerIntent, CONTACTS_CONTRACT);
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
                    concepto = txtComisionServicio.getText().toString().trim();
                    iPresenterPayment.validateFieldsCarrier(referencia, edtServiceImport.getText().toString().trim(),
                            concepto, comercioResponse.getLongitudReferencia());
                }
                break;

            case R.id.imgMakePaymentRef:
                Intent intent = new Intent(getActivity(), ScannVisionActivity.class);
                getActivity().startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CONTACTS_CONTRACT) {
                contactPicked(data);
            }
        } else if (requestCode == BACK_FROM_PAYMENTS) {

            if (resultCode == Constants.RESULT_CODE_OK_CLOSE) {
                getActivity().finish();
            } else {
                Toast.makeText(App.getContext(), App.getContext().getResources()
                        .getString(R.string.new_body_saldo_error), Toast.LENGTH_SHORT).show();

            }
        } else if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    edtReferenceNumber.setText(barcode.displayValue);
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
            if (errorText.equals(App.getContext().getString(R.string.new_body_IAVE_error))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_recarga_iave_error_empty);

            } else if (errorText.equals(App.getContext().getString(R.string.new_body_phone_error))) {
                errorTittle = App.getContext().getResources().getString(R.string.numero_telefono_incorrecto);

            } else if (errorText.equals(App.getContext().getString(R.string.favor_selecciona_importe))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_envios_importe_empty_error);

            } else if (errorText.equals(App.getContext().getString(R.string.numero_iave_vacio))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_recarga_iave_error_empty);
                errorText = App.getContext().getResources().getString(R.string.new_body_recargas_iave_error_empty);

            } else if (errorText.equals(App.getContext().getString(R.string.numero_telefono_vacio))) {
                errorTittle = App.getContext().getResources().getString(R.string.phone_invalid);
                errorText = App.getContext().getResources().getString(R.string.new_body_recargas_phone_error);
            }
            UI.createSimpleCustomDialog(errorTittle, errorText, getActivity().getSupportFragmentManager(), getFragmentTag());
        }
    }

    @Override
    public void onError(String error) {
        //mySeekBar.setEnabled(false);
        isValid = false;
        errorText = error;
        showError();
    }

    @Override
    public void onErrorValidateService(String error) {
        //mySeekBar.setEnabled(false);
        isValid = false;
        errorText = error;
        showError();
    }

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
                    dataFavoritos != null);
            sendPayment();
        }
    }

    @Override
    public void onSuccess(Double importe) {
        if (importe > 0) {
            isValid = true;
        }

        if (!isValid) {
            showError();
        } else {
            this.monto = importe;
            isValid = true;

            payment = new Recarga(referencia, monto, comercioResponse, dataFavoritos != null);
            sendPayment();
        }
    }

    protected void sendPayment() {
        Intent intent = new Intent(getContext(), PaymentsProcessingActivity.class);
        intent.putExtra("pagoItem", payment);
        if(isRecarga) {
            intent.putExtra("TAB", Constants.PAYMENT_RECARGAS);
        }else{
            intent.putExtra("TAB", Constants.PAYMENT_SERVICIOS);
        }
        SingletonSession.getInstance().setFinish(false);//No cerramos la aplicación
        getActivity().startActivityForResult(intent, BACK_FROM_PAYMENTS);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}

