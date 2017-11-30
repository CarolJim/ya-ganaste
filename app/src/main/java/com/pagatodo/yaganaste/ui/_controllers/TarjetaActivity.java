package com.pagatodo.yaganaste.ui._controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextPaint;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.MyCardReportaTarjetaFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyChangeNip;
import com.pagatodo.yaganaste.ui.preferuser.MyHelpContactanosCorreo;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardViewHome;
import com.pagatodo.yaganaste.ui.tarjeta.TarjetaUserPresenter;
import com.pagatodo.yaganaste.utils.FontCache;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCard;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui.preferuser.MyCardFragment.BLOQUEO;
import static com.pagatodo.yaganaste.ui.preferuser.MyCardFragment.DESBLOQUEO;
import static com.pagatodo.yaganaste.utils.StringConstants.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;
import static com.pagatodo.yaganaste.utils.StringUtils.getCreditCardFormat;

public class TarjetaActivity extends LoaderActivity implements OnEventListener, IMyCardViewHome {

    private String nombreCompleto, cuentaUsuario, ultimaTransaccion;
    @BindView(R.id.imgYaGanasteCard)
    YaGanasteCard imgYaGanasteCard;
    @BindView(R.id.txtNameTitular)
    TextView txtNameTitular;
    @BindView(R.id.txtultimopago)
    TextView mLastTimeTV;
    @BindView(R.id.mycard_switch)
    SwitchCompat mycard_switch;
    @BindView(R.id.img_status)
    AppCompatImageView imgStatus;
    @BindView(R.id.lyt_princpal)
    LinearLayout lytPrincipal;
    @BindView(R.id.item_change_nip)
    LinearLayout lytChangeNIP;
    @BindView(R.id.item_report_card)
    LinearLayout lytReportCard;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.txtBlockCardDesc)
    StyleTextView txtBlockCardDesc;
    int statusBloqueo;
    boolean statusOperation = true;
    private TarjetaUserPresenter mPreferPresenter;
    private AccountPresenterNew presenterAccount;
    private String mTDC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);
        ButterKnife.bind(this);
        mPreferPresenter = new TarjetaUserPresenter(this);
        presenterAccount = new AccountPresenterNew(this);
        if (!App.getInstance().getPrefs().loadData(CARD_NUMBER).equals("")) {
            checkDataCard();
            txtNameTitular.setText(nombre());
            ultimaTransaccion();
            //printCard(cuenta());
            estadotarjeta();
            lytChangeNIP.setOnClickListener(this);
            lytReportCard.setOnClickListener(this);
        } else {
            lytPrincipal.setVisibility(View.GONE);
            loadFragment(MyCardReportaTarjetaFragment.newInstance(), Direction.FORDWARD, false);
            container.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoader(String message) {
        super.showLoader(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
        //  printCard(cuenta());
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

    private void checkState(String state) {
        switch (state) {
            case Recursos.ESTATUS_CUENTA_BLOQUEADA:
                mycard_switch.setChecked(true);
                imgStatus.setImageResource(R.drawable.ic_candado_closed);
                imgYaGanasteCard.setImageResource(R.mipmap.main_card_zoom_gray);
                txtBlockCardDesc.setText(getString(R.string.desbloquear_tarjeta));
                //  printCard(cuenta());
                break;
            case Recursos.ESTATUS_CUENTA_DESBLOQUEADA:
                mycard_switch.setChecked(false);
                imgStatus.setImageResource(R.drawable.ic_candado_open);
                imgYaGanasteCard.setImageResource(R.mipmap.main_card_zoom_blue);
                txtBlockCardDesc.setText(getString(R.string.bloquear_tarjeta));
                //   printCard(cuenta());
                break;
            default:
                mycard_switch.setChecked(false);
                imgStatus.setImageResource(R.drawable.ic_candado_open);
                imgYaGanasteCard.setImageResource(R.mipmap.main_card_zoom_blue);
                txtBlockCardDesc.setText(getString(R.string.bloquear_tarjeta));
                // printCard(cuenta());
                break;
        }
        String cardNumber = App.getInstance().getPrefs().loadData(CARD_NUMBER);
        imgYaGanasteCard.setCardNumber(getCreditCardFormat(cardNumber));
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

    public TarjetaUserPresenter getmPreferPresenter() {
        return mPreferPresenter;
    }

    private void ultimaTransaccion() {
        ultimaTransaccion = SingletonUser.getInstance().getUltimaTransaccion();
        if (ultimaTransaccion == null || ultimaTransaccion.isEmpty()) {
            mLastTimeTV.setText("   No Hay Fecha Del Ultimo Pago");
            mLastTimeTV.setSelected(true);
        } else {
            mLastTimeTV.setText(ultimaTransaccion);
        }
    }

    // public PreferUserPresenter getPreferPresenter() {
    //   return mPreferPresenter;
    // }
    @Override
    public boolean requiresTimer() {
        return false;
    }

    public String nombre() {
        UsuarioClienteResponse userData = SingletonUser.getInstance().getDataUser().getUsuario();

        String nombreprimerUser;

        String apellidoMostrarUser;
        if (userData.getPrimerApellido().isEmpty()) {
            apellidoMostrarUser = userData.getSegundoApellido();
        } else {
            apellidoMostrarUser = userData.getPrimerApellido();
        }
        nombreprimerUser = StringUtils.getFirstName(userData.getNombre());
        if (nombreprimerUser.isEmpty()) {
            nombreprimerUser = userData.getNombre();

        }

        //tv_name.setText(mName);
        //mNameTV.setText(nombreprimerUser+" "+apellidoMostrarUser);

        nombreCompleto = nombreprimerUser + " " + apellidoMostrarUser;

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
        cuentaUsuario = (StringUtils.ocultarCardNumberFormat(mTDC));
        //mCuentaTV.setText(getResources().getString(R.string.tarjeta) + ": " + StringUtils.ocultarCardNumberFormat(mTDC));
        return cuentaUsuario;
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

    public AccountPresenterNew getPresenterAccount() {
        return presenterAccount;
    }

    @Override
    public void sendSuccessBloquearCuentaToView(BloquearCuentaResponse response) {
        statusOperation = true;
        String messageStatus = "";
        if (statusBloqueo == BLOQUEO) {
            messageStatus = getResources().getString(R.string.card_locked_success);
            SingletonUser.getInstance().setCardStatusId(Recursos.ESTATUS_CUENTA_BLOQUEADA);
            checkState(Recursos.ESTATUS_CUENTA_BLOQUEADA);
        } else if (statusBloqueo == DESBLOQUEO) {
            messageStatus = getResources().getString(R.string.card_unlocked_success);
            SingletonUser.getInstance().setCardStatusId(Recursos.ESTATUS_CUENTA_DESBLOQUEADA);
            checkState(Recursos.ESTATUS_CUENTA_DESBLOQUEADA);
        }

        showDialogCustom(messageStatus +
                "\n" +
                getResources().getString(R.string.card_num_autorization) + " "
                + response.getData().getNumeroAutorizacion());
    }

    @Override
    public void sendErrorBloquearCuentaToView(String mensaje) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_change_nip:
                if (SingletonUser.getInstance().getCardStatusId().equals(Recursos.ESTATUS_CUENTA_DESBLOQUEADA)) {
                    lytPrincipal.setVisibility(View.GONE);
                    loadFragment(MyChangeNip.newInstance(), Direction.FORDWARD, false);
                    container.setVisibility(View.VISIBLE);
                } else {
                    showDialogMesage(getString(R.string.change_nip_block_card));
                }
                break;
            case R.id.item_report_card:
                lytPrincipal.setVisibility(View.GONE);
                loadFragment(MyCardReportaTarjetaFragment.newInstance(), Direction.FORDWARD, false);
                container.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_back:
                UI.hideKeyBoard(this);
                onBackPressed();
                break;
            default:
                break;
        }
    }

    public void onSendEmail() {
        loadFragment(MyHelpContactanosCorreo.newInstance(), Direction.FORDWARD, false);
    }

    @Override
    public void onBackPressed() {
        // Si el boton no esta deshabilitado realizamos las operaciones de back
        if (!isLoaderShow) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof  MyCardReportaTarjetaFragment &&
                    App.getInstance().getPrefs().loadData(CARD_NUMBER).equals("")) {
                super.onBackPressed();
            } else if (currentFragment instanceof MyChangeNip ||
                    currentFragment instanceof MyCardReportaTarjetaFragment) {
                removeLastFragment();
                lytPrincipal.setVisibility(View.VISIBLE);
                container.setVisibility(View.GONE);
            } else if (currentFragment instanceof MyHelpContactanosCorreo) {
                loadFragment(MyCardReportaTarjetaFragment.newInstance(), Direction.BACK, false);
            } else {
                super.onBackPressed();
            }
        }
    }
}
