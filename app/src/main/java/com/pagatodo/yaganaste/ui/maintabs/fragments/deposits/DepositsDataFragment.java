package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.maintabs.managers.DepositsManager;
import com.pagatodo.yaganaste.utils.FontCache;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 17/05/2017.
 */

public class DepositsDataFragment extends SupportFragment implements View.OnClickListener {
    private View rootView;
    DepositsManager depositsManager;

    @BindView(R.id.imgYaGanasteCard)
    ImageView imgYaGanasteCard;
    @BindView(R.id.txtNameTitular)
    TextView txtNameTitular;
    @BindView(R.id.txtNumberCard)
    TextView txtNumberCard;
    @BindView(R.id.txtCableNumber)
    TextView txtCableNumber;
    @BindView(R.id.btnDepositar)
    Button btnDepositar;

    public static DepositsDataFragment newInstance() {
        DepositsDataFragment depositsDataFragment = new DepositsDataFragment();
        Bundle args = new Bundle();
        depositsDataFragment.setArguments(args);
        return depositsDataFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depositsManager = ((DepositsFragment)getParentFragment()).getDepositManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onEventListener.onEvent(ToolBarActivity.EVENT_CHANGE_TOOLBAR_VISIBILITY, true);
        return inflater.inflate(R.layout.fragment_deposito_datos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
        UsuarioClienteResponse usuario = SingletonUser.getInstance().getDataUser().getUsuario();
        txtNameTitular.setText(usuario.getNombre() + " " + usuario.getPrimerApellido() + " " + usuario.getSegundoApellido());
        CuentaResponse cuenta = null;
        String cardNumber = "";
        if (usuario.getCuentas() != null && usuario.getCuentas().size() >= 1) {
            cuenta = usuario.getCuentas().get(0);
            cardNumber = getCreditCardFormat(cuenta.getTarjeta());
            txtCableNumber.setText(cuenta.getCLABE());
            txtNumberCard.setText(cardNumber);
        }
        printCard(cardNumber);
    }

    private String getCreditCardFormat(String card){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < card.length(); i++) {
            if (i % 4 == 0 && i != 0) {
                result.append(" ");
            }
            result.append(card.charAt(i));
        }

        return result.toString();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnDepositar.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDepositar) {
            //Siguiente Fragment
            depositsManager.onTapButton();
        }
    }

    private void printCard(String cardNumber) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.main_card_zoom_blue);
        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        TextPaint textPaint = new TextPaint();
        Typeface typeface = FontCache.getTypeface("fonts/roboto/Roboto-Regular.ttf", getContext());
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(typeface);

        float heigth = canvas.getHeight();
        float width = canvas.getWidth();
        textPaint.setTextSize(heigth * 0.115f);

        canvas.drawText(cardNumber, width * 0.07f, heigth * 0.6f, textPaint);


        imgYaGanasteCard.setImageBitmap(bitmap);
    }
}
