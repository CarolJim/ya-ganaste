package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.pagatodo.view_manager.components.HeadAccount;
import com.pagatodo.view_manager.components.HeadWallet;
import com.pagatodo.view_manager.controllers.dataholders.HeadAccountData;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.interfaces.EditTextImeBackListener;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.NumberCalcTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

public class SendWalletFragment extends GenericFragment implements EditTextImeBackListener {

    public static final String MONTO = "Monto";
    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;
    @BindView(R.id.crlImageFavorite)
    CircleImageView crlImageFavorite;
    @BindView(R.id.et_amount)
    EditText et_amount;
    @BindView(R.id.txt_receiver_name)
    StyleTextView txtReceiverName;
    @BindView(R.id.saldoDisponible)
    MontoTextView saldoDisponible;
    @BindView(R.id.txtInicialesFav)
    TextView txtInicialesFav;
    @BindView(R.id.enviar)
    StyleTextView btnEnviar;
    @BindView(R.id.edit_conc)
    EditText edit_conc;

    @BindView(R.id.headWallet)
    HeadWallet headWallet;


    View view;


    private StyleTextView tvMontoEntero, tvMontoDecimal;

    private float MIN_AMOUNT = 1.0f, current_mount;
    Double monto;
    Envios payments;
    Favoritos favoritos;

    public static SendWalletFragment newInstance(Envios payments, Favoritos favoritos) {
        SendWalletFragment fragment = new SendWalletFragment();
        Bundle args = new Bundle();
        args.putSerializable("payments", payments);
        args.putSerializable("favorites", favoritos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        payments = (Envios) getArguments().getSerializable("payments");
        favoritos = (Favoritos) getArguments().getSerializable("favorites");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_send_wallet, container, false);
        initViews();
        return view;

    }
    @Override
    public void initViews() {
        ButterKnife.bind(this, view);
        tvMontoEntero = (StyleTextView) view.findViewById(R.id.tv_monto_entero);
        tvMontoDecimal = (StyleTextView) view.findViewById(R.id.tv_monto_decimal);
        HeadAccount headAccount = view.findViewById(R.id.head_account);
        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);
        headWallet.setAmount(App.getInstance().getPrefs().loadData(USER_BALANCE));
        headWallet.setTitle("Saldo actual");







        SingletonUser dataUser = SingletonUser.getInstance();
        saldoDisponible.setText("" + StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)));
        txtReceiverName.setText(payments.getNombreDestinatario());
        if (favoritos != null) {





            txtInicialesFav.setVisibility(View.GONE);
            if (!favoritos.getImagenURL().equals("")) {
                favoritos.setImagenURL(favoritos.getImagenURL());
                Picasso.get()
                        .load(favoritos.getImagenURL())
                        .placeholder(R.mipmap.icon_user_fail)
                        .into(crlImageFavorite);
            } else {
                favoritos.setImagenURL("");
                txtInicialesFav.setVisibility(View.VISIBLE);
                GradientDrawable gd = createCircleDrawable(android.graphics.Color.parseColor(payments.getComercio().getColorMarca()),
                        android.graphics.Color.parseColor(payments.getComercio().getColorMarca()));
                crlImageFavorite.setBackground(gd);
                String sIniciales = getIniciales(payments.getNombreDestinatario());
                txtInicialesFav.setText(sIniciales);
            }

            favoritos.setColorMarca(payments.getComercio().getColorMarca());
            favoritos.setNombre(payments.getNombreDestinatario());
            favoritos.setReferencia(payments.getReferenciaNumerica());


            headAccount.bind(HeadAccountData.create(favoritos.getImagenURL(),
                    favoritos.getColorMarca(),
                    favoritos.getNombre(),
                    favoritos.getReferencia()),null);


        } else {
            txtInicialesFav.setVisibility(View.VISIBLE);
            GradientDrawable gd = createCircleDrawable(android.graphics.Color.parseColor(payments.getComercio().getColorMarca()),
                    android.graphics.Color.parseColor(payments.getComercio().getColorMarca()));
            crlImageFavorite.setBackground(gd);
            String sIniciales = getIniciales(payments.getNombreDestinatario());
            txtInicialesFav.setText(sIniciales);
        }
        et_amount.addTextChangedListener(new NumberCalcTextWatcher(et_amount, tvMontoEntero, tvMontoDecimal, null,null));
        et_amount.requestFocus();
        et_amount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                keyboardView.showCustomKeyboard(v);
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        et_amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    keyboardView.showCustomKeyboard(view);
                } else {
                  //  keyboardView.hideCustomKeyboard();
                }
            }
        });

        btnEnviar.setOnClickListener(view -> continueSendPayment());

    }

    public void continueSendPayment() {
        if (actionCharge()) {
            if (!UtilsNet.isOnline(getActivity())) {
                //UI.createSimpleCustomDialog("Error", getString(R.string.no_internet_access), getActivity().getSupportFragmentManager(), getFragmentTag());
                UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
            } else {
                if (edit_conc.getText().toString().trim().length()>0){
                    payments.setConcepto(edit_conc.getText().toString().trim());
                }
                payments.setMonto(monto);
                if(payments.getReferencia().length()==22){
                    payments.setReferencia(payments.getReferencia().replace(" ",""));
                }
                Intent intent = new Intent(getContext(), PaymentsProcessingActivity.class);
                intent.putExtra("pagoItem", payments);
                intent.putExtra("favoriteItem", favoritos);
                intent.putExtra("TAB", Constants.PAYMENT_ENVIOS);
                SingletonSession.getInstance().setFinish(false);//No cerramos la aplicación
                getActivity().startActivityForResult(intent, BACK_FROM_PAYMENTS);
                //getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //getActivity().finish();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        NumberCalcTextWatcher.cleanData();
        et_amount.requestFocus();
        /*SingletonUser dataUser = SingletonUser.getInstance();
        saldoDisponible.setText("" + Utils.getCurrencyValue(dataUser.getDatosSaldo().getSaldoEmisor()));
        tvMontoEntero.setText("00");
        tvMontoDecimal.setText("00");
        et_amount.setText("$00.00");*/

    }

    @Override
    public void onImeBack() {
        et_amount.requestFocus();
    }

    private boolean actionCharge() {
        String valueAmount = et_amount.getText().toString().trim();
        // Limpiamos del "," que tenemos del EditText auxiliar
        int positionQuote = valueAmount.indexOf(",");
        if (positionQuote > 0) {
            String[] valueAmountArray = valueAmount.split(",");
            valueAmount = valueAmountArray[0] + valueAmountArray[1];
        }
        if (valueAmount.length() > 0 && !valueAmount.equals(getString(R.string.mount_cero))) {
            try {
                StringBuilder cashAmountBuilder = new StringBuilder(valueAmount);
                // Limpiamos del caracter $ en caso de tenerlo
                int positionMoney = valueAmount.indexOf("$");
                if (positionMoney == 0) {
                    valueAmount = cashAmountBuilder.deleteCharAt(0).toString();
                }
                monto = Double.parseDouble(valueAmount);
                current_mount = Float.parseFloat(valueAmount);
                if (current_mount >= MIN_AMOUNT) {
                    return true;
                } else showValidationError(getString(R.string.new_body_envios_importe_error));
            } catch (NumberFormatException e) {
                showValidationError(getString(R.string.mount_valid));
            }
        } else showValidationError(getString(R.string.enter_mount));
        return false;
    }

    private void showValidationError(String error) {
        //UI.createSimpleCustomDialog("Error", error, getActivity().getSupportFragmentManager(), getFragmentTag());
        UI.showErrorSnackBar(getActivity(), error, Snackbar.LENGTH_SHORT);
        //mySeekBar.setProgress(0);
    }

    /*@Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            et_amount.requestFocus();
        } else if (et_amount != null) {
            NumberCalcTextWatcher.cleanData();
        }
    }*/

    private String getIniciales(String fullName) {
        if (fullName.trim().length() == 1){
            return fullName.substring(0, 1).toUpperCase();
        }

        String[] spliName = fullName.split(" ");
        String sIniciales = "";
        if (spliName.length > 1) {
            sIniciales = spliName[0].substring(0, 1) + spliName[1].substring(0, 1).toUpperCase();
            return sIniciales;
        }

        if (fullName.trim().length() > 1){
            return fullName.substring(0, 2).toUpperCase();
        }
        return "";
    }

    // Se encarga de crear el circulo Drawable que usaremos para mostrar las imagenes o los textos
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
}
