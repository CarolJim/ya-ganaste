package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.interfaces.EditTextImeBackListener;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.EnvioFormularioWallet;
import com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.RequestPaymentActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdapter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.NumberCalcTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.ID_ENVIAR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.ID_SOLICITAR;
import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;

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
    @BindView(R.id.tv_monto_entero)
    StyleTextView tvMontoEntero;
    @BindView(R.id.tv_monto_decimal)
    StyleTextView tvMontoDecimal;
    @BindView(R.id.saldoDisponible)
    MontoTextView saldoDisponible;
    @BindView(R.id.txtInicialesFav)
    TextView txtInicialesFav;

    private float MIN_AMOUNT = 1.0f, current_mount;
    Double monto;
    Envios payments;
    DataFavoritos dataFavoritos;

    public static SendWalletFragment newInstance(Envios payments, DataFavoritos dataFavoritos) {
        SendWalletFragment fragment = new SendWalletFragment();
        Bundle args = new Bundle();
        args.putSerializable("payments", payments);
        args.putSerializable("favorites", dataFavoritos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        payments = (Envios) getArguments().getSerializable("payments");
        dataFavoritos = (DataFavoritos) getArguments().getSerializable("favorites");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_wallet, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;

    }

    @Override
    public void initViews() {
        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);
        SingletonUser dataUser = SingletonUser.getInstance();
        saldoDisponible.setText("" + Utils.getCurrencyValue(dataUser.getDatosSaldo().getSaldoEmisor()));
        txtReceiverName.setText(payments.getNombreDestinatario());
        if (dataFavoritos != null) {
            txtInicialesFav.setVisibility(View.GONE);
            if (!dataFavoritos.getImagenURL().equals("")) {
                Picasso.with(App.getContext()).load(dataFavoritos.getImagenURL()).placeholder(R.mipmap.icon_user)
                        .error(R.mipmap.icon_user).into(crlImageFavorite);
            } else {
                txtInicialesFav.setVisibility(View.VISIBLE);
                GradientDrawable gd = createCircleDrawable(android.graphics.Color.parseColor(payments.getComercio().getColorMarca()),
                        android.graphics.Color.parseColor(payments.getComercio().getColorMarca()));
                crlImageFavorite.setBackground(gd);
                String sIniciales = getIniciales(payments.getNombreDestinatario());
                txtInicialesFav.setText(sIniciales);
            }
        } else {
            txtInicialesFav.setVisibility(View.VISIBLE);
            GradientDrawable gd = createCircleDrawable(android.graphics.Color.parseColor(payments.getComercio().getColorMarca()),
                    android.graphics.Color.parseColor(payments.getComercio().getColorMarca()));
            crlImageFavorite.setBackground(gd);
            String sIniciales = getIniciales(payments.getNombreDestinatario());
            txtInicialesFav.setText(sIniciales);
        }
        et_amount.addTextChangedListener(new NumberCalcTextWatcher(et_amount, tvMontoEntero, tvMontoDecimal, null));

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
                    keyboardView.hideCustomKeyboard();
                }
            }
        });
        et_amount.requestFocus();
    }

    public void continueSendPayment() {
        if (actionCharge()) {
            if (!UtilsNet.isOnline(getActivity())) {
                //UI.createSimpleCustomDialog("Error", getString(R.string.no_internet_access), getActivity().getSupportFragmentManager(), getFragmentTag());
                UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
            } else {
                payments.setMonto(monto);
                Intent intent = new Intent(getContext(), PaymentsProcessingActivity.class);
                intent.putExtra("pagoItem", payments);
                intent.putExtra("TAB", Constants.PAYMENT_ENVIOS);
                SingletonSession.getInstance().setFinish(false);//No cerramos la aplicación
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                getActivity().finish();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        NumberCalcTextWatcher.cleanData();
        et_amount.requestFocus();
        SingletonUser dataUser = SingletonUser.getInstance();
        saldoDisponible.setText("" + Utils.getCurrencyValue(dataUser.getDatosSaldo().getSaldoEmisor()));
        tvMontoEntero.setText("00");
        tvMontoDecimal.setText("00");
        et_amount.setText("$00.00");

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

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            et_amount.requestFocus();
        } else if (et_amount != null) {
            NumberCalcTextWatcher.cleanData();
        }
    }

    private String getIniciales(String fullName) {
        String[] spliName = fullName.split(" ");
        String sIniciales = "";
        if (spliName.length > 1) {
            sIniciales = spliName[0].substring(0, 1) + spliName[1].substring(0, 1).toUpperCase();
        } else {
            sIniciales = fullName.substring(0, 2).toUpperCase();
        }
        return sIniciales;
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
