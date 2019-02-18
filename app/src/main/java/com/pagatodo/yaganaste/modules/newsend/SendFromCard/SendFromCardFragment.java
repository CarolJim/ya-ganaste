package com.pagatodo.yaganaste.modules.newsend.SendFromCard;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.card.payment.CardIOActivity;

import android.provider.ContactsContract;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.pagatodo.view_manager.components.HeadWallet;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataTitular;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.interfaces.OnListServiceListener;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.EnvioFormularioWallet;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.managers.EnviosManager;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.EnviosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IEnviosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.NumberCardTextWatcher;
import com.pagatodo.yaganaste.utils.NumberClabeTextWatcher;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.customviews.ListServDialogFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.card.payment.CreditCard;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CLABE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.modules.newsend.SendNewActivity.PAYMENT_CARD;
import static com.pagatodo.yaganaste.modules.newsend.SendNewActivity.PAYMENT_CLABE;
import static com.pagatodo.yaganaste.modules.newsend.SendNewActivity.PAYMENT_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.CONTACTS_CONTRACT_LOCAL;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;
import static com.pagatodo.yaganaste.utils.Constants.CREDITCARD_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Recursos.IDCOMERCIO_YA_GANASTE;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendFromCardFragment extends GenericFragment implements View.OnClickListener, OnListServiceListener,
        PaymentsCarrouselManager, EnviosManager {
    @BindView(R.id.headWallet)
    HeadWallet headWallet;
    @BindView(R.id.HeadAccount)
    HeadWallet HeadAccount;

    @BindView(R.id.number_card_edta)
    EditText number_card_edt;
    @BindView(R.id.number_card)
    LinearLayout number_card;
    @BindView(R.id.send_type_card)
    View send_type_card;
    @BindView(R.id.send_type_telephone)
    View send_type_telephone;
    @BindView(R.id.send_type_clabe)
    View send_type_clabe;
    @BindView(R.id.contactsPhone)
    ImageView contactsPhone;
    @BindView(R.id.camera_icon)
    ImageView camera_icon;
    @BindView(R.id.bankLinear)
    LinearLayout bankLinear;
    @BindView(R.id.bank_edtx)
    EditText bank_edt;
    @BindView(R.id.reference_edtx)
    EditText reference_edtx;
    @BindView(R.id.dest_edtx)
    EditText dest_edtx;
    @BindView(R.id.bank_card_edtx)
    EditText bank_card_edtx;
    @BindView(R.id.reference_card_edtx)
    EditText reference_card_edtx;
    @BindView(R.id.dest)
    TextInputLayout dest;

    @BindView(R.id.referencianumber_clabe_edtx)
    EditText referencianumber_clabe;
    @BindView(R.id.dest_clabe_edtx)
    EditText dest_clabe_edtx;
    @BindView(R.id.bank_clabe_edtx)
    EditText bank_clabe_edtx;
    @BindView(R.id.reference_clabe_edtx)
    EditText reference_clabe_edtx;


    @BindView(R.id.bank_clabe)
    TextInputLayout bank_clabe;
    @BindView(R.id.btnSendPayment)
    StyleButton btnSendPayment;

    @BindView(R.id.dest_card_edtx)
    EditText dest_card_edtx;
    @BindView(R.id.dest_card)
    TextInputLayout dest_card;
    @BindView(R.id.referencianumber_edtx)
    EditText referencianumber_edtx;
    @BindView(R.id.bank_card)
    TextInputLayout bank_card;
    @BindView(R.id.bank)
    TextInputLayout bank_telephone;
    @BindView(R.id.dest_clabe)
    TextInputLayout dest_clabe;
    @BindView(R.id.linear_number_card)
    LinearLayout linear_number_card;
    @BindView(R.id.linear_number_clabe)
    LinearLayout linear_number_clabe;

    TextWatcher txtWatcherSetted;
    private static int type;
    CameraManager cameraManager;
    Payments payment;
    TransferType selectedType;

    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    ArrayList<CarouselItem> backUpResponse;
    IEnviosPresenter enviosPresenter;
    int favoriteProcess, current_tab, idTipoComercio, idComercio, longitudRefer,
            maxLength, keyIdComercio, idTipoEnvio, idFavorito, longRefer;
    String stringFoto = "", formatoComercio, mReferencia, nombreComercio, nombreDest;
    private String nombreDestinatario, referenciaNumber, referenceFavorite, myReferencia, errorText,
            referencia, formatoComerciox, concepto;
    Comercio comercioItem;
    Favoritos favoriteItem;
    private View rootView;
    String refff = "";
    String dateref = "";
    private boolean isValid;

    public SendFromCardFragment() {
        // Required empty public constructor
    }

    public static SendFromCardFragment newInstance(int types) {
        SendFromCardFragment sendFromCardFragment = new SendFromCardFragment();
        type = types;
        return sendFromCardFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_send_from_card, container, false);
        backUpResponse = new ArrayList<>();
        enviosPresenter = new EnviosPresenter(this);
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(Constants.PAYMENT_ENVIOS, this, getContext(), false);
        if (!UtilsNet.isOnline(getActivity())) {
            UI.createSimpleCustomDialog("Error", getString(R.string.no_internet_access), getActivity().getSupportFragmentManager(), getFragmentTag());
            UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
        } else {
            onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
            paymentsCarouselPresenter.getCarouselItems();
            onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        }
        initViews();
        return rootView;
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);

        referencianumber_edtx.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                number_card.setBackgroundResource(R.drawable.input_text_active);
            } else {
                number_card.setBackgroundResource(R.drawable.inputtext_normal);

            }
        });
        dest_card_edtx.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dest_card.setBackgroundResource(R.drawable.input_text_active);
            } else {
                dest_card.setBackgroundResource(R.drawable.inputtext_normal);

            }
        });
        bank_card_edtx.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                bank_card.setBackgroundResource(R.drawable.input_text_active);
            } else {
                bank_card.setBackgroundResource(R.drawable.inputtext_normal);

            }
        });

        number_card_edt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                linear_number_card.setBackgroundResource(R.drawable.input_text_active);
            } else {
                linear_number_card.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
        dest_edtx.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dest.setBackgroundResource(R.drawable.input_text_active);
            } else {
                dest.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
        bank_edt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                bank_telephone.setBackgroundResource(R.drawable.input_text_active);
            } else {
                bank_telephone.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });

        referencianumber_clabe.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                linear_number_clabe.setBackgroundResource(R.drawable.input_text_active);
            } else {
                linear_number_clabe.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
        dest_clabe_edtx.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dest_clabe.setBackgroundResource(R.drawable.input_text_active);
            } else {
                dest_clabe.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
        bank_clabe_edtx.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                bank_clabe.setBackgroundResource(R.drawable.input_text_active);
            } else {
                bank_clabe.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });

        headWallet.setAmount(App.getInstance().getPrefs().loadData(USER_BALANCE));
        btnSendPayment.setOnClickListener(this::onClick);
        //isValid = true;
        switch (type) {
            case PAYMENT_CARD:
                camera_icon.setOnClickListener(this::onClick);
                send_type_card.setVisibility(View.VISIBLE);
                HeadAccount.setAmount("Número de tarjeta");
                HeadAccount.setResImage(getResources().getDrawable(R.drawable.ic_ico_ventas_tarjeta));
                bank_card_edtx.setOnClickListener(this::onClick);
                bank_card_edtx.setFocusable(false);
                bank_card_edtx.setEnabled(true);
                bank_card_edtx.setFocusableInTouchMode(false);
                reference_card_edtx.setFocusable(false);
                reference_card_edtx.setFocusableInTouchMode(false);
                reference_card_edtx.setEnabled(false);
                maxLength = 19;
                NumberCardTextWatcher numberCardTextWatcher = new NumberCardTextWatcher(referencianumber_edtx, maxLength);
                txtWatcherSetted = numberCardTextWatcher;
                referencianumber_edtx.addTextChangedListener(numberCardTextWatcher);
                selectedType = NUMERO_TARJETA;
                break;
            case PAYMENT_PHONE:
                send_type_telephone.setVisibility(View.VISIBLE);
                HeadAccount.setAmount("Número de telefono");
                HeadAccount.setResImage(getResources().getDrawable(R.drawable.ic_ico_telefono));
                contactsPhone.setOnClickListener(this::onClick);
                bankLinear.setOnClickListener(this::onClick);
                bank_edt.setFocusable(false);
                bank_edt.setFocusableInTouchMode(false);
                bank_edt.setEnabled(false);
                reference_edtx.setFocusable(false);
                reference_edtx.setFocusableInTouchMode(false);
                reference_edtx.setEnabled(false);
                maxLength = 12;
                PhoneTextWatcher phoneTextWatcher = new PhoneTextWatcher(number_card_edt);
                txtWatcherSetted = phoneTextWatcher;
                number_card_edt.addTextChangedListener(phoneTextWatcher);
                selectedType = NUMERO_TELEFONO;
                break;
            case PAYMENT_CLABE:
                send_type_clabe.setVisibility(View.VISIBLE);
                HeadAccount.setAmount("Cuenta CLABE");
                HeadAccount.setResImage(getResources().getDrawable(R.drawable.ic_ico_clabe));
                bank_clabe.setOnClickListener(this::onClick);
                bank_clabe_edtx.setFocusable(false);
                bank_clabe_edtx.setFocusableInTouchMode(false);
                bank_clabe_edtx.setEnabled(false);
                reference_clabe_edtx.setFocusable(false);
                reference_clabe_edtx.setFocusableInTouchMode(false);
                reference_clabe_edtx.setEnabled(false);
                maxLength = 22;
                NumberClabeTextWatcher clabeTextWatcher = new NumberClabeTextWatcher(referencianumber_clabe, maxLength);
                txtWatcherSetted = clabeTextWatcher;
                referencianumber_clabe.addTextChangedListener(clabeTextWatcher);
                selectedType = CLABE;
                break;
        }

    }

    private void showdialogBank() {
        ListServDialogFragment dialogFragment = ListServDialogFragment.newInstance(backUpResponse);
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        dialogFragment.setOnListServiceListener(this);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "FragmentDialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CONTACTS_CONTRACT_LOCAL) {
                contactPicked(data, 1);
            }
        }
        if (requestCode == CREDITCARD_READER_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                resultDisplayStr = "Card Number: " + scanResult.getFormattedCardNumber();
                referencianumber_edtx.setText(scanResult.getFormattedCardNumber().trim());
                referencianumber_edtx.setImeOptions(IME_ACTION_DONE);
            } else {
                resultDisplayStr = "Scan was canceled.";
            }
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.e(getActivity().getString(R.string.app_name), "CreditCard Scanner: " + resultDisplayStr);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_icon:
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
            case R.id.contactsPhone:
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                getActivity().startActivityForResult(contactPickerIntent, CONTACTS_CONTRACT_LOCAL);

                break;

            case R.id.btnSendPayment:

                for (CarouselItem carouselItem : backUpResponse) {
                    if (carouselItem.getComercio().getIdComercio() == idComercio) {
                        comercioItem = carouselItem.getComercio();
                        idTipoComercio = carouselItem.getComercio().getIdTipoComercio();
                        idComercio = carouselItem.getComercio().getIdComercio();
                    }
                }
                switch (type) {
                    case PAYMENT_CARD:
                        referencia = referencianumber_edtx.getText().toString().trim().replaceAll(" ", "");
                        refff = reference_card_edtx.getText().toString();
                        nombreDestinatario = dest_card_edtx.getText().toString().trim();
                        break;
                    case PAYMENT_PHONE:
                        referencia = number_card_edt.getText().toString().trim().replaceAll(" ", "");
                        refff = reference_card_edtx.getText().toString();
                        nombreDestinatario = dest_edtx.getText().toString().trim();
                        break;
                    case PAYMENT_CLABE:
                        referencia = referencianumber_clabe.getText().toString().trim().replaceAll(" ", "");
                        refff = reference_card_edtx.getText().toString();
                        nombreDestinatario = dest_clabe_edtx.getText().toString().trim();
                        break;

                }

                concepto = " ";
                referenciaNumber = referencia;

                validateForm();

                    //clearInputs();

                break;
            case R.id.bankLinear:
                ListServDialogFragment dialogFragment = ListServDialogFragment.newInstance(backUpResponse);
                dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                dialogFragment.setOnListServiceListener(this);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "FragmentDialog");
                break;
            case R.id.bank_card_edtx:
                showdialogBank();
                break;
            case R.id.bank_card:
                showdialogBank();
                break;
            case R.id.bank_clabe:
                showdialogBank();
                break;
        }
    }

    private void contactPicked(Intent data, int tipoNumero) {
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

        // Hacemos set en el elemento, ya sea de TAE o de Envios
        if (tipoNumero == 1) {
            number_card_edt.setText(phoneNo);
        } else {
            number_card_edt.setText(phoneNo);
        }
    }

    @Override
    public void onListServiceListener(CarouselItem item, int position) {

        switch (selectedType) {
            case NUMERO_TARJETA:
                bank_card_edtx.setText(item.getComercio().getNombreComercio());
                idTipoComercio = item.getComercio().getIdTipoComercio();
                idComercio = item.getComercio().getIdComercio();
                // Variables necesarioas para agregar el formato de captura de telefono o referencia
                formatoComercio = item.getComercio().getFormato();
                longitudRefer = item.getComercio().getLongitudReferencia();
                if (idComercio != IDCOMERCIO_YA_GANASTE) {
                    reference_card_edtx.setImeOptions(IME_ACTION_DONE);
                    reference_card_edtx.setText(App.getContext().getResources().getString(R.string.trans_spei_envio_txt));
                    dateref = ("123456");
                } else {
                    dateref = (DateUtil.getDayMonthYear());
                    reference_card_edtx.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
                    String card = dest_card_edtx.getText().toString();
                    card = card.replaceAll(" ", "");
                    if (card.length() == 18) {
                        enviosPresenter.getTitularName(dest_card_edtx.getText().toString().trim());
                    }
                }


                break;
            case NUMERO_TELEFONO:
                bank_edt.setText(item.getComercio().getNombreComercio());
                idTipoComercio = item.getComercio().getIdTipoComercio();
                idComercio = item.getComercio().getIdComercio();

                // Borramos los textos de los campos de refrencia de todos los tispos
                //recargaNumber.setText("");
                //referenceNumber.setText("");

                // Variables necesarioas para agregar el formato de captura de telefono o referencia
                formatoComercio = item.getComercio().getFormato();
                longitudRefer = item.getComercio().getLongitudReferencia();
                if (idComercio != IDCOMERCIO_YA_GANASTE) {
                    reference_edtx.setImeOptions(IME_ACTION_DONE);
                    reference_edtx.setText(App.getContext().getResources().getString(R.string.trans_spei_envio_txt));
                } else {
                    reference_edtx.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
                    String card = dest_edtx.getText().toString();
                    card = card.replaceAll(" ", "");
                    if (card.length() == 18) {
                        enviosPresenter.getTitularName(dest_edtx.getText().toString().trim());
                    }
                }
                break;
            case CLABE:
                bank_clabe_edtx.setText(item.getComercio().getNombreComercio());
                idTipoComercio = item.getComercio().getIdTipoComercio();
                idComercio = item.getComercio().getIdComercio();

                // Borramos los textos de los campos de refrencia de todos los tispos
                //recargaNumber.setText("");
                //referenceNumber.setText("");

                // Variables necesarioas para agregar el formato de captura de telefono o referencia
                formatoComercio = item.getComercio().getFormato();
                longitudRefer = item.getComercio().getLongitudReferencia();
                if (idComercio != IDCOMERCIO_YA_GANASTE) {
                    reference_clabe_edtx.setImeOptions(IME_ACTION_DONE);
                    reference_clabe_edtx.setText(App.getContext().getResources().getString(R.string.trans_spei_envio_txt));
                } else {
                    reference_clabe_edtx.setText(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
                    String card = dest_clabe_edtx.getText().toString();
                    card = card.replaceAll(" ", "");
                    if (card.length() == 18) {
                        enviosPresenter.getTitularName(dest_clabe_edtx.getText().toString().trim());
                    }
                }
                break;
        }
    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {
        backUpResponse = new ArrayList<>();
        for (CarouselItem carouselItem : response) {
            backUpResponse.add(carouselItem);
        }

        Collections.sort(backUpResponse, (o1, o2) -> {
            if (o1.getComercio() != null && o2.getComercio() != null) {
                return o1.getComercio().getNombreComercio().compareToIgnoreCase(o2.getComercio().getNombreComercio());
            } else {
                return 0;
            }
        });
    }

    private void validateForm() {
        isValid = true;
        switch (type) {
            case PAYMENT_CARD:
                if (referencianumber_edtx.getText().toString().isEmpty()) {
                    isValid = false;
                    number_card.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), "Ingresa un numero de tarjeta", Snackbar.LENGTH_LONG);
                }
                if (dest_card_edtx.getText().toString().isEmpty()) {
                    isValid = false;
                    dest_card.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), "Ingresa un numero un destinatario", Snackbar.LENGTH_LONG);
                }
                if (bank_card_edtx.getText().toString().isEmpty()) {
                    isValid = false;
                    bank_card.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), "Debes seleccionar un banco", Snackbar.LENGTH_LONG);
                }
                break;
            case PAYMENT_PHONE:
                if (number_card_edt.getText().toString().isEmpty()) {
                    isValid = false;
                    linear_number_card.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), "Ingresa un numero de tarjeta", Snackbar.LENGTH_LONG);
                }
                if (dest_edtx.getText().toString().isEmpty()) {
                    isValid = false;
                    dest.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), "Ingresa un numero un destinatario", Snackbar.LENGTH_LONG);
                }
                if (bank_edt.getText().toString().isEmpty()) {
                    isValid = false;
                    bank_telephone.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), "Debes seleccionar un banco", Snackbar.LENGTH_LONG);
                }
                break;
            case PAYMENT_CLABE:
                if (referencianumber_clabe.getText().toString().isEmpty()) {
                    isValid = false;
                    linear_number_clabe.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), "Ingresa un numero de tarjeta", Snackbar.LENGTH_LONG);
                }
                if (dest_clabe_edtx.getText().toString().isEmpty()) {
                    isValid = false;
                    dest_clabe.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), "Ingresa un numero un destinatario", Snackbar.LENGTH_LONG);
                }
                if (bank_clabe_edtx.getText().toString().isEmpty()) {
                    isValid = false;
                    bank_clabe.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), "Debes seleccionar un banco", Snackbar.LENGTH_LONG);
                }
                break;
        }

        if (isValid){
            payment = new Envios(selectedType, referenciaNumber, 0D, nombreDestinatario, concepto, dateref, comercioItem, false);
            Intent intent = new Intent(getContext(), EnvioFormularioWallet.class);
            intent.putExtra("pagoItem", payment);
            intent.putExtra("favoritoItem", favoriteItem);
            startActivityForResult(intent, BACK_FROM_PAYMENTS);
        }
    }

    private void clearInputs() {
        referencianumber_edtx.setText("");
        dest_card_edtx.setText("");
        bank_card_edtx.setText("");
        reference_card_edtx.setText("");
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
    public void setFavolist(List<Favoritos> lista) {

    }

    @Override
    public void showErrorService() {

    }

    @Override
    public void showError() {
        if (errorText != null && !errorText.equals("")) {
            String errorTittle = "";
            if (errorText.equals(App.getContext().getString(R.string.txt_tipo_envio_error))) {
                errorTittle = App.getContext().getResources().getString(R.string.type_send_invalid);
            }
            UI.showErrorSnackBar(getActivity(), errorTittle, Snackbar.LENGTH_LONG);
        }
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
    public void onTextChanged() {

    }

    @Override
    public void onTextComplete() {

    }

}
