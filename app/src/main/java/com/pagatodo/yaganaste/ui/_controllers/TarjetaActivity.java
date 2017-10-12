package com.pagatodo.yaganaste.ui._controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardViewHome;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.ui.tarjeta.TarjetaUserPresenter;
import com.pagatodo.yaganaste.utils.FontCache;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import static com.pagatodo.yaganaste.ui.preferuser.MyCardFragment.BLOQUEO;
import static com.pagatodo.yaganaste.ui.preferuser.MyCardFragment.DESBLOQUEO;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

public class TarjetaActivity extends LoaderActivity implements OnEventListener, IMyCardViewHome {

    private String nombreCompleto,cuentaUsuario,ultimaTransaccion;
    private ImageView imgYaGanasteCard;
    private TextView txtNameTitular,mLastTimeTV;
    private TarjetaUserPresenter mPreferPresenter;
    private SwitchCompat mycard_switch;
    private AppCompatImageView imgStatus;
    int statusBloqueo;
    boolean statusOperation = true;

    protected OnEventListener onEventListener;
    private String  mTDC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferPresenter = new TarjetaUserPresenter(this);
        setContentView(R.layout.activity_tarjeta);


        checkDataCard();
        imgYaGanasteCard=(ImageView) findViewById(R.id.imgYaGanasteCard);
        txtNameTitular=(TextView) findViewById(R.id.txtNameTitular);
        mLastTimeTV=(TextView) findViewById(R.id.txtultimopago);
        mycard_switch=(SwitchCompat) findViewById(R.id.mycard_switch);
        imgStatus=(AppCompatImageView) findViewById(R.id.img_status);
        txtNameTitular.setText(nombre());
        ultimaTransaccion();
        printCard(cuenta());
        estadotarjeta();
    }

    @Override
    public void showLoader(String message) {
        super.showLoader(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
    }

    private void estadotarjeta() {
        String statusId = SingletonUser.getInstance().getCardStatusId();
        if (statusId != null && !statusId.isEmpty()) {
            checkState(statusId);
        } else {
            checkState(App.getInstance().getStatusId());
        }

        //Agregamos un Listener al Switch
        mycard_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                /**
                 * 1 - Validacion para operaciones con internet, en caso contrario regresamos los botones a
                 * su estado natural
                 * 2 - Validacion de statusOperation, si es True realizamos las oepraciones, si es False nos
                 * brincamos este paso, se usa cuando regresamos los botones a su estado natural, porque al
                 * regresarlos se actuva de nuevo el onCheckedChanged, con False evitamos un doble proceso
                 * 3 - If isChecked operaciones para realizar el Bloqueo, else operaciones de Desbloqueo
                 */

                imgStatus.setImageResource(isChecked ? R.drawable.ic_candado_closed : R.drawable.ic_candado_open);

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
        });

    }

    private void checkState(String state){
        switch (state){
            case Recursos.ESTATUS_CUENTA_BLOQUEADA:
                mycard_switch.setChecked(true);
                imgStatus.setImageResource(R.drawable.ic_candado_closed);
                imgYaGanasteCard.setImageResource(R.mipmap.main_card_zoom_gray);
                break;
            case Recursos.ESTATUS_CUENTA_DESBLOQUEADA:
                mycard_switch.setChecked(false);
                imgStatus.setImageResource(R.drawable.ic_candado_open);
                imgYaGanasteCard.setImageResource(R.mipmap.main_card_zoom_blue);
                break;
            default:
                mycard_switch.setChecked(false);
                imgStatus.setImageResource(R.drawable.ic_candado_open);
                imgYaGanasteCard.setImageResource(R.mipmap.main_card_zoom_blue);
                break;
        }
    }
    /**
     * Adminsitrador de mensaje que no tienen acciones, solo informativos, usados comunmente en errores
     *
     * @param mensaje
     */
    public void showDialogCustom(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getSupportFragmentManager(),
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
    private void ultimaTransaccion() {
        ultimaTransaccion = SingletonUser.getInstance().getUltimaTransaccion();
        mLastTimeTV.setText(ultimaTransaccion);
    }
   // public PreferUserPresenter getPreferPresenter() {
   //   return mPreferPresenter;
   // }
    @Override
    public boolean requiresTimer() {
        return false;
    }

    public String nombre(){
        UsuarioClienteResponse userData = SingletonUser.getInstance().getDataUser().getUsuario();

        String nombreprimerUser;

        String apellidoMostrarUser;
        if (userData.getPrimerApellido().isEmpty()){
            apellidoMostrarUser=userData.getSegundoApellido();
        }else {
            apellidoMostrarUser=userData.getPrimerApellido();
        }
        nombreprimerUser= StringUtils.getFirstName(userData.getNombre());
        if (nombreprimerUser.isEmpty()){
            nombreprimerUser=userData.getNombre();

        }

        //tv_name.setText(mName);
        //mNameTV.setText(nombreprimerUser+" "+apellidoMostrarUser);

        nombreCompleto=nombreprimerUser+" "+apellidoMostrarUser;

        return nombreCompleto;
    }
    private void checkDataCard() {
        /**
         * Si tenemos Internet consumos el servicio para actualizar la informacion de la ceunta,
         * consulamos el estado de bloquero y la informacion de ultimo acceso
         * else mostramos la unformacion que traemos desde sl Singleton
         */
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            // Verificamos el estado de bloqueo de la Card
            String f = SingletonUser.getInstance().getCardStatusId();
            if (f == null || f.isEmpty() || f.equals("0")) {
                UsuarioClienteResponse usuarioClienteResponse = SingletonUser.getInstance().getDataUser().getUsuario();
                mTDC = usuarioClienteResponse.getCuentas().get(0).getTarjeta();
                mPreferPresenter.toPresenterEstatusCuenta(mTDC);
            }

            // Obtenemos el estado de la Card actual
            // Creamos el objeto ActualizarAvatarRequest
            // TODO Frank se comento este metodo debido a cambios, borrar en versiones posteriores
            /*ActualizarDatosCuentaRequest datosCuentaRequest = new ActualizarDatosCuentaRequest();
            mPreferPresenter.sendPresenterUpdateDatosCuenta(datosCuentaRequest);*/
        } else {
            showDialogMesage(getResources().getString(R.string.no_internet_access));
        }
    }
    private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getSupportFragmentManager(),
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
    private String cuenta() {
        UsuarioClienteResponse usuarioClienteResponse = SingletonUser.getInstance().getDataUser().getUsuario();
        mTDC = usuarioClienteResponse.getCuentas().get(0).getTarjeta();
        cuentaUsuario=(StringUtils.ocultarCardNumberFormat(mTDC));
        //mCuentaTV.setText(getResources().getString(R.string.tarjeta) + ": " + StringUtils.ocultarCardNumberFormat(mTDC));
        return cuentaUsuario;
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
        Typeface typeface = FontCache.getTypeface("fonts/roboto/Roboto-Regular.ttf", this);
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(typeface);

        float heigth = canvas.getHeight();
        float width = canvas.getWidth();
        textPaint.setTextSize(heigth * 0.115f);

        canvas.drawText(StringUtils.format(cardNumber, SPACE, 4, 4, 4, 4), width * 0.07f, heigth * 0.6f, textPaint);

        imgYaGanasteCard.setImageBitmap(bitmap);
    }

    @Override
    public void sendSuccessEstatusCuentaToView(EstatusCuentaResponse response) {
        String statusId = response.getData().getStatusId();
        SingletonUser.getInstance().setCardStatusId(statusId);
        if (statusId != null && !statusId.isEmpty()) {
            checkState(statusId);
        } else {
            checkState(App.getInstance().getStatusId());
        }
    }
    public void sendErrorEstatusCuentaToView(String mensaje) {
        showDialogMesage(mensaje);
        //onEventListener.onEvent("DISABLE_BACK", false);
    }

    @Override
    public void sendSuccessBloquearCuentaToView(BloquearCuentaResponse response) {
        statusOperation = true;
        String messageStatus = "";
        if (statusBloqueo == BLOQUEO) {
            messageStatus = getResources().getString(R.string.card_locked_success);
            SingletonUser.getInstance().setCardStatusId(Recursos.ESTATUS_CUENTA_DESBLOQUEADA);
        } else if (statusBloqueo == DESBLOQUEO) {
            messageStatus = getResources().getString(R.string.card_unlocked_success);
            SingletonUser.getInstance().setCardStatusId(Recursos.ESTATUS_CUENTA_BLOQUEADA);

        }

        showDialogCustom(messageStatus +
                "\n" +
                getResources().getString(R.string.card_num_autorization) + ": "
                + response.getData().getNumeroAutorizacion());
    }

    @Override
    public void sendErrorBloquearCuentaToView(String mensaje) {

    }


}
