package com.pagatodo.yaganaste.ui.preferuser;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarDatosCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarDatosCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.FontCache;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MaterialLinearLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.yaganasteviews.CardEmisorSelected;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_CHANGE_NIP;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_MY_USER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.PRIVACIDAD;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCardFragment extends GenericFragment implements View.OnClickListener,
        IMyCardView, CompoundButton.OnCheckedChangeListener {

    public static final int BLOQUEO = 1;
    public static final int DESBLOQUEO = 2;
    @BindView(R.id.fragment_my_card_change_nip)
    StyleTextView changeNip;
    @BindView(R.id.my_card_name_user)
    TextView mNameTV;
    @BindView(R.id.my_card_num_cuenta)
    TextView mCuentaTV;
    @BindView(R.id.my_card_last_time)
    TextView mLastTimeTV;
    @BindView(R.id.imgYaGanasteCard)
    ImageView imgYaGanasteCard;
    @BindView(R.id.mycard_switch)
    SwitchCompat mycard_switch;

    private CardEmisorSelected cardEmisorSelected;
    private MaterialLinearLayout llMaterialEmisorContainer;
    public static final String M_NAME = "mName";
    public static final String M_TDC = "mTDC";
    public static final String M_LASTTIME = "mLastTime";
    public String mName;
    public String mTDC;
    public String mLastTime;
    PreferUserPresenter mPreferPresenter;
    View rootview;
    int statusBloqueo;
    boolean statusOperation = true;
    boolean statusInternetOperation = true;

    public MyCardFragment() {
        // Required empty public constructor
    }

    public static MyCardFragment newInstance(String mName, String mTDC, String mLastTime) {
        MyCardFragment fragment = new MyCardFragment();
        Bundle args = new Bundle();
        args.putString(M_NAME, mName);
        args.putString(M_TDC, mTDC);
        args.putString(M_LASTTIME, mLastTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        mPreferPresenter = ((PreferUserActivity) getActivity()).getPreferPresenter();
        mPreferPresenter.setIView(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_card, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        mName = getArguments().getString(M_NAME);
        mNameTV.setText(mName);

        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            // Creamos el objeto ActualizarAvatarRequest
            ActualizarDatosCuentaRequest datosCuentaRequest = new ActualizarDatosCuentaRequest();
            mPreferPresenter.sendPresenterUpdateDatosCuenta(datosCuentaRequest);
        } else {
            showDialogCustom(getResources().getString(R.string.no_internet_access));
            mTDC = getArguments().getString(M_TDC);
            mCuentaTV.setText("Tarjeta: " + StringUtils.ocultarCardNumberFormat(mTDC));

            mLastTimeTV.setText("Utilizada Por Ultima Vez: \n" + "");
            printCard(mTDC);
        }

        /*mTDC = getArguments().getString(M_TDC);
        mLastTime = getArguments().getString(M_LASTTIME);

        mCuentaTV.setText("Tarjeta: " + StringUtils.ocultarCardNumberFormat(mTDC));
        mLastTimeTV.setText("Utilizada Por Ultima Vez: " + mLastTime);*/

        cardEmisorSelected = new CardEmisorSelected(getContext());
        llMaterialEmisorContainer = (MaterialLinearLayout) rootview.findViewById(R.id.ll_material_emisor_container);

        changeNip.setOnClickListener(this);

        //Agregamos un Listener al Switch
        mycard_switch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_my_card_change_nip:
                onEventListener.onEvent(PREFER_USER_CHANGE_NIP, 1);
                break;
          /*  case R.id.fragment_lista_opciones_account:
                //Toast.makeText(getContext(), "Click Cuenta", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_MY_ACCOUNT, 1);
                break;
            case R.id.fragment_lista_opciones_card:
                //Toast.makeText(getContext(), "Click Card", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_MY_CARD, 1);
                break;*/
        }
    }

    @Override
    public void sendSuccessDatosCuentaToView(ActualizarDatosCuentaResponse response) {
        mTDC = response.getData().get(0).getCuenta();
        mLastTime = response.getData().get(0).getUltimoInicioSesion();

        mNameTV.setText(mName);
        mCuentaTV.setText("Tarjeta: " + StringUtils.ocultarCardNumberFormat(mTDC));
        mLastTimeTV.setText("Utilizada Por Ultima Vez: \n" + mLastTime);

        printCard(mTDC);

        Toast.makeText(getContext(), "MyCard Update", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoader(String title) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, title);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void sendErrorDatosCuentaToView(String mensaje) {
        showDialogCustom(mensaje);
        onEventListener.onEvent("DISABLE_BACK", false);
    }

    /**
     * Manejamos la respuesta Success del servicio, mostrando el mensaje de Bloqueo o Desbloqueo,
     * dependiendo del caso
     *
     * @param response
     */
    @Override
    public void sendSuccessBloquearCuentaToView(BloquearCuentaResponse response) {
        statusOperation = true;
        if (statusBloqueo == BLOQUEO) {
            showDialogCustom("La Tarjeta Fue Bloqueada Con Éxito. \nNum. Autorización: "
                    + response.getData().getNumeroAutorizacion());
        } else if (statusBloqueo == DESBLOQUEO) {
            showDialogCustom("La Tarjeta Fue Desbloqueada Con Éxito. \nNum. Autorización: "
                    + response.getData().getNumeroAutorizacion());
        }
    }

    /**
     * Manejo de Errores diversos, ya sea de Success Server With Code Error o Error Server.
     * 1 - Deshabilitamos statusOperation para que al hacer el cambio del Checked no realicemos de nuevo
     * las operaciones, porque siempre pasa por el onCheckedChanged, despues realizamos la operacioon
     * corespondiente para para regresar el boton a su estado original antes de intentar consumir
     * el servicio
     *
     * @param mensaje
     */
    @Override
    public void sendErrorBloquearCuentaToView(String mensaje) {
        statusOperation = false;
        if (statusBloqueo == BLOQUEO) {
            showDialogCustom(mensaje);
            statusBloqueo = DESBLOQUEO;
            mycard_switch.setChecked(false);
            statusOperation = true;
        } else if (statusBloqueo == DESBLOQUEO) {
            showDialogCustom(mensaje);
            statusBloqueo = BLOQUEO;
            mycard_switch.setChecked(true);
            statusOperation = true;
        }
    }

    /**
     * Adminsitrador de mensaje que no tienen acciones, solo informativos, usados comunmente en errores
     *
     * @param mensaje
     */
    public void showDialogCustom(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
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

        //canvas.drawText(StringUtils.formatoPagoMedios(cardNumber), width * 0.07f, heigth * 0.6f, textPaint);
        canvas.drawText(cardNumber, width * 0.07f, heigth * 0.6f, textPaint);


        imgYaGanasteCard.setImageBitmap(bitmap);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        /**
         * 1 - Validacion para operaciones con internet, en caso contrario regresamos los botones a
         * su estado natural
         * 2 - Validacion de statusOperation, si es True realizamos las oepraciones, si es False nos
         * brincamos este paso, se usa cuando regresamos los botones a su estado natural, porque al
         * regresarlos se actuva de nuevo el onCheckedChanged, con False evitamos un doble proceso
         * 3 - If isChecked operaciones para realizar el Bloqueo, else operaciones de Desbloqueo
         */
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            if (statusOperation) {
                if (isChecked) {
                    // Toast.makeText(getContext(), "checked 1", Toast.LENGTH_SHORT).show();
                    mPreferPresenter.toPresenterBloquearCuenta(BLOQUEO);
                    statusBloqueo = BLOQUEO;
                } else {
                    // Toast.makeText(getContext(), "Deschecked 2", Toast.LENGTH_SHORT).show();
                    mPreferPresenter.toPresenterBloquearCuenta(DESBLOQUEO);
                    statusBloqueo = DESBLOQUEO;
                }
            }
        } else {
            showDialogCustom(getResources().getString(R.string.no_internet_access));
            if (statusOperation) {
                if (isChecked) {
                    statusOperation = false;
                    mycard_switch.setChecked(false);
                    statusOperation = true;
                } else {
                    statusOperation = false;
                    mycard_switch.setChecked(true);
                    statusOperation = true;
                }
            }
        }
    }

}
