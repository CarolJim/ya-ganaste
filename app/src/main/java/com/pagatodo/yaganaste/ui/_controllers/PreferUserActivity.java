package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.Menu;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarDatosCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.sidebar.HelpYaGanaste.HelpYaGanasteFragment;
import com.pagatodo.yaganaste.modules.sidebar.Settings.SettingsFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.ui.preferuser.AvisoPrivacidadFragment;
import com.pagatodo.yaganaste.ui.preferuser.CuentaReembolsoFragment;
import com.pagatodo.yaganaste.ui.preferuser.DesasociarPhoneFragment;
import com.pagatodo.yaganaste.ui.preferuser.ListaAyudaLegalesFragment;
import com.pagatodo.yaganaste.ui.preferuser.ListaOpcionesFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyAccountFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyCardFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyCardReportaTarjetaFragment;
import com.pagatodo.yaganaste.modules.emisor.ChangeNip.MyChangeNip;
import com.pagatodo.yaganaste.ui.preferuser.MyEmailFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyHelpAcercaApp;
import com.pagatodo.yaganaste.ui.preferuser.MyHelpContactanos;
import com.pagatodo.yaganaste.ui.preferuser.MyHelpContactanosCorreo;
import com.pagatodo.yaganaste.ui.preferuser.MyPassFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyUserFragment;
import com.pagatodo.yaganaste.ui.preferuser.TerminosyCondicionesFragment;
import com.pagatodo.yaganaste.ui.preferuser.presenters.MyDongleFragment;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.ui_wallet.fragments.CancelAccountFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.CancelResultFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.NotificacionesPrefFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.QRFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment;
import com.pagatodo.yaganaste.modules.sidebar.SettingsOfSecurity.SecuritySettignsFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.TutorialsFragment;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.PRIVACIDAD;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_AJUSTES;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_CODE;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_CONTACTO;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_LOGOUT;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_TERMINOS;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.NOTIF_COUNT;

public class PreferUserActivity extends LoaderActivity implements OnEventListener {

    public static String PREFER_USER_LISTA = "PREFER_USER_LISTA";
    public static String PREFER_USER_CLOSE = "PREFER_USER_CLOSE";
    public static String PREFER_USER_PRIVACIDAD = "PREFER_USER_PRIVACIDAD";
    public static String PREFER_USER_TERMINOS = "PREFER_USER_TERMINOS";
    public static String PREFER_USER_PRIVACIDAD_BACK = "PREFER_USER_PRIVACIDAD_BACK";
    public static String PREFER_USER_TERMINOS_BACK = "PREFER_USER_TERMINOS_BACK";
    public static String PREFER_USER_PRIVACIDAD_CUENTA_YA = "PREFER_USER_PRIVACIDAD_CUENTA_YA";
    public static String PREFER_USER_TERMINOS_CUENTA_YA = "PREFER_USER_TERMINOS_CUENTA_YA";
    public static String PREFER_USER_CUENTA_REEMBOLSO = "PREFER_USER_CUENTA_REEMBOLSO";
    public static String PREFER_USER_CUENTA_REEMBOLSO_BACK = "PREFER_USER_CUENTA_REEMBOLSO_BACK";
    public static String PREFER_USER_DESASOCIAR = "PREFER_USER_DESASOCIAR";
    public static String PREFER_USER_CANCELACION = "PREFER_USER_CANCELACION";
    public static String PREFER_USER_DESASOCIAR_BACK = "PREFER_USER_DESASOCIAR_BACK";
    public static String PREFER_USER_MY_USER = "PREFER_USER_MY_USER";
    public static String PREFER_USER_HELP_CONTACT = "PREFER_USER_HELP_CONTACT";
    public static String PREFER_USER_HELP_CONTACT_BACK = "PREFER_USER_HELP_CONTACT_BACK";
    public static String PREFER_USER_HELP_ABOUT = "PREFER_USER_HELP_ABOUT";
    public static String PREFER_USER_HELP_CORREO = "PREFER_USER_HELP_CORREO";
    public static String PREFER_USER_HELP_CORREO_REPORTA_TARJETA = "PREFER_USER_HELP_CORREO_REPORTA_TARJETA";
    public static String PREFER_USER_HELP_CORREO_REPORTA_TARJETA_BACK = "PREFER_USER_HELP_CORREO_REPORTA_TARJETA_BACK";
    public static String PREFER_USER_REPORTA_TARJETA = "PREFER_USER_REPORTA_TARJETA";
    public static String PREFER_USER_REPORTA_TARJETA_BACK = "PREFER_USER_REPORTA_TARJETA_BACK";
    public static String PREFER_USER_HELP_LEGAL = "PREFER_USER_HELP_LEGAL";
    public static String PREFER_USER_HELP_BACK = "PREFER_USER_HELP_BACK";
    public static String PREFER_USER_MY_ACCOUNT = "PREFER_USER_MY_ACCOUNT";
    public static String PREFER_USER_MY_CARD = "PREFER_USER_MY_CARD";
    public static String PREFER_USER_MY_USER_BACK = "PREFER_USER_MY_USER_BACK";
    public static String PREFER_USER_EMAIL = "PREFER_USER_EMAIL";
    public static String PREFER_USER_PASS = "PREFER_USER_PASS";
    public static String PREFER_USER_CHANGE_NIP = "PREFER_USER_CHANGE_NIP";
    public static String PREFER_USER_CHANGE_NIP_BACK = "PREFER_USER_CHANGE_NIP_BACK";
    public static String PREFER_USER_MY_DONGLE = "PREFER_USER_MY_DONGLE";
    public static String PREFER_USER_MY_DONGLE_BACK = "PREFER_USER_MY_DONGLE_BACK";

    public static String PREFER_NOTIFICACIONES = "PREFER_NOTIFICACIONES";
    public static String PREFER_NOTIFICACIONES_BACK = "PREFER_NOTIFICACIONES_BACK";
    public static String PREFER_DESVINCULAR = "PREFER_DESVINCULAR";
    public static String PREFER_SECURITY_SUCCESS_PASS = "PREFER_SECURITY_SUCCESS_PASS";

    public static String PREFER_CANCEL = "PREFER_CANCEL";
    public static String PREFER_CANCEL_RESULT = "PREFER_CANCEL_RESULT";
    /**
     * Acciones para dialogo de confirmacion en cerrar session
     */
    DialogDoubleActions doubleActions = new DialogDoubleActions() {
        @Override
        public void actionConfirm(Object... params) {
            setResult(ToolBarActivity.RESULT_LOG_OUT);
            //mPreferPresenter.closeSession(mContext);
            presenterAccount.logout();
            // finish();
        }

        @Override
        public void actionCancel(Object... params) {

        }
    };
    private boolean isEsAgente = false;
    private String mName;
    private String mEmail;
    private String mUserImage;
    private String mTDC;
    private String mClabe;
    private String mLastTime;
    private boolean disableBackButton = false;
    private AccountPresenterNew presenterAccount;
    private PreferUserPresenter mPreferPresenter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.activity_prefer_user);


        UsuarioResponse usuarioClienteResponse = SingletonUser.getInstance().getDataUser().getUsuario();
        ClienteResponse ClienteResponse = SingletonUser.getInstance().getDataUser().getCliente();
        mName = ClienteResponse.getNombre() + " " +
                ClienteResponse.getPrimerApellido();
        mEmail = usuarioClienteResponse.getNombreUsuario();
        mUserImage = usuarioClienteResponse.getImagenAvatarURL();

        // Creamos la variables que tendran los datos de Tarjeta y Clabe
        //mTDC = "1234567890123456";
        if (SingletonUser.getInstance().getDataUser().getEmisor()
                .getCuentas().get(0).getTarjetas() != null
                && !SingletonUser.getInstance().getDataUser().getEmisor()
                .getCuentas().get(0).getTarjetas().isEmpty()) {
            mTDC = SingletonUser.getInstance().getDataUser().getEmisor()
                    .getCuentas().get(0).getTarjetas().get(0).getNumero();
        } else {
            mTDC = "Error Con Tarjeta";
        }
        //mClabe = "123456789012345678";
        mClabe = SingletonUser.getInstance().getDataUser().getEmisor()
                .getCuentas().get(0).getCLABE();
        mLastTime = "";

        presenterAccount = new AccountPresenterNew(this);
        mPreferPresenter = new PreferUserPresenter(this);

        //loadFragment(ListaOpcionesFragment.newInstance(isEsAgente, mName, mEmail, mUserImage));
        //loadFragment(MyUserFragment.newInstance());
        if (getIntent().getIntExtra(MENU, 0) == MENU_TERMINOS) {
            loadFragment(ListaAyudaLegalesFragment.newInstance());
        } else if (getIntent().getIntExtra(MENU, 0) == MENU_LOGOUT) {
            onEvent(PREFER_USER_CLOSE, null);
        } else if (getIntent().getIntExtra(MENU, 0) == MENU_CODE) {
            loadFragment(QRFragment.newInstance());
        } else if (getIntent().getIntExtra(MENU, 0) == MENU_CONTACTO) {
            //loadFragment(ContactoFragment.newInstance());
            //loadFragment(TutorialsFragment.newInstance());
            loadFragment(HelpYaGanasteFragment.newInstance());
        } else {
            loadFragment(SecurityFragment.newInstance(getIntent().getIntExtra(MENU, 0), ""));
            //loadFragment(SecuritySettignsFragment.newInstance(getIntent().getIntExtra(MENU, 0), ""));

            //loadFragment(SettingsFragment.newInstance());
        }

        mContext = this;

        int notifPendents = App.getInstance().getPrefs().loadDataInt(NOTIF_COUNT);
        /*if (notifPendents == 0) {
            App.setBadge(notifPendents);
        } else {
            notifPendents--;
            App.setBadge(notifPendents);
        }*/
        App.getInstance().getPrefs().saveDataInt(NOTIF_COUNT, notifPendents);
    }

    public AccountPresenterNew getPresenterAccount() {
        return presenterAccount;
    }

    public PreferUserPresenter getPreferPresenter() {
        return mPreferPresenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        //showBack(!(fragment instanceof ListaOpcionesFragment));
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    /**
     * Sobre escribimos el metodo del PAdre ToolBar para no tener el boton que nos abre esta+
     * activitydad
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);

        /**
         * Eventos desde Fragmentos
         */

        switch (event) {

            case "PREFER_USER_MY_DONGLE":
                loadFragment(MyDongleFragment.newInstance(App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE)),
                        Direction.FORDWARD, false);
                break;
            case "PREFER_USER_REPORTA_TARJETA":
                loadFragment(MyCardReportaTarjetaFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case "PREFER_USER_REPORTA_TARJETA_BACK":
                loadFragment(MyCardFragment.newInstance(mName, mTDC, mLastTime), Direction.BACK, false);
                break;
            case "EVENT_GO_CHANGE_NIP_SUCCESS":
                loadFragment(MyCardFragment.newInstance(mName, mTDC, mLastTime), Direction.BACK, false);
                break;
            case "PREFER_USER_HELP_CORREO_REPORTA_TARJETA":
                loadFragment(MyHelpContactanosCorreo.newInstance(), Direction.FORDWARD, false);
                break;
            case "PREFER_USER_HELP_CORREO":
                loadFragment(MyHelpContactanosCorreo.newInstance(), Direction.FORDWARD, false);
                break;
            case "PREFER_USER_HELP_ABOUT":
                loadFragment(MyHelpAcercaApp.newInstance(), Direction.FORDWARD, false);
                break;
            case "PREFER_USER_HELP_CONTACT":
                loadFragment(MyHelpContactanos.newInstance(), Direction.FORDWARD, false);
                break;
            case "PREFER_USER_HELP_CONTACT_BACK":
                loadFragment(MyHelpContactanos.newInstance(), Direction.BACK, false);
                break;
            case "PREFER_USER_HELP_LEGAL":
                loadFragment(ListaAyudaLegalesFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case "PREFER_USER_HELP_BACK":
                loadFragment(ListaAyudaLegalesFragment.newInstance(), Direction.BACK, false);
                break;
            case "PREFER_USER_DESASOCIAR":
                loadFragment(DesasociarPhoneFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case "PREFER_USER_CUENTA_REEMBOLSO":
                loadFragment(CuentaReembolsoFragment.newInstance(mName, mTDC, mClabe), Direction.FORDWARD, false);
                break;
            case "PREFER_USER_CUENTA_REEMBOLSO_BACK":
                loadFragment(MyAccountFragment.newInstance(), Direction.BACK, false);
                break;

            case "PREFER_USER_DESASOCIAR_BACK":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                //loadFragment(MyUserFragment.newInstance(), Direction.BACK, false);
                loadFragment(SecurityFragment.newInstance(MENU_AJUSTES, ""), Direction.BACK, false);
                break;
            case "PREFER_USER_MY_DONGLE_BACK":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyUserFragment.newInstance(), Direction.BACK, false);
                break;

            case "PREFER_USER_CLOSE":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                UI.createSimpleCustomDialog("",
                        App.getContext().getResources().getString(R.string.desea_cerrar_sesion),
                        getSupportFragmentManager(),
                        doubleActions, true, true);
                break;

            case "DESASOCIAR_CLOSE_SESSION":
                setResult(ToolBarActivity.RESULT_LOG_OUT);
                finish();
                break;
            case "PREFER_USER_PRIVACIDAD":
                loadFragment(AvisoPrivacidadFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case "PREFER_USER_TERMINOS":
                loadFragment(TerminosyCondicionesFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case "PREFER_USER_TERMINOS_BACK":
                loadFragment(ListaAyudaLegalesFragment.newInstance(), Direction.BACK, false);
                break;
            case "PREFER_USER_PRIVACIDAD_BACK":
                loadFragment(ListaAyudaLegalesFragment.newInstance(), Direction.BACK, false);
                break;

            case "PREFER_USER_PRIVACIDAD_CUENTA_YA":
                boolean isOnline = Utils.isDeviceOnline();
                if (isOnline) {
                    //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                    LegalsDialog legalsDialog = LegalsDialog.newInstance(PRIVACIDAD);
                    legalsDialog.show(this.getFragmentManager(), LegalsDialog.TAG);
                    //loadFragment(LegalsDialog.newInstance(PRIVACIDAD));
                } else {
                    // Toast.makeText(this, "Is OffLine Privacidad", Toast.LENGTH_SHORT).show();
                    showDialogMesage(getResources().getString(R.string.no_internet_access));
                }
                break;
            case "PREFER_USER_TERMINOS_CUENTA_YA":
                boolean isOnline2 = Utils.isDeviceOnline();
                if (isOnline2) {
                    LegalsDialog legalsTerminosDialog = LegalsDialog.newInstance(TERMINOS);
                    legalsTerminosDialog.show(this.getFragmentManager(), LegalsDialog.TAG);
                    //loadFragment(LegalsDialog.newInstance(TERMINOS));
                } else {
                    showDialogMesage(getResources().getString(R.string.no_internet_access));
                    //Toast.makeText(this, "Is OffLine Terminos", Toast.LENGTH_SHORT).show();
                }
                break;
            case "PREFER_USER_MY_USER":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyUserFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case "PREFER_USER_MY_USER_BACK":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                //loadFragment(SecurityFragment.newInstance(MENU_SEGURIDAD), Direction.BACK, false);
                //loadFragment(SecurityFragment.newInstance(getIntent().getIntExtra(MENU, 0), ""), Direction.BACK, false);
                //loadFragment(SecuritySettignsFragment.newInstance(getIntent().getIntExtra(MENU, 0), ""), Direction.BACK, false);
                loadFragment(SecuritySettignsFragment.newInstance(), Direction.BACK, false);

                break;

            case "PREFER_USER_MY_ACCOUNT":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyAccountFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case "PREFER_USER_MY_CARD":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyCardFragment.newInstance(mName, mTDC, mLastTime), Direction.FORDWARD, false);
                break;

            case "PREFER_USER_CHANGE_NIP":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyChangeNip.newInstance(), Direction.FORDWARD, false);
                break;
            case "PREFER_USER_HELP_CORREO_REPORTA_TARJETA_BACK":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyCardReportaTarjetaFragment.newInstance(), Direction.BACK, false);
                break;
            case "PREFER_USER_CHANGE_NIP_BACK":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyCardFragment.newInstance(mName, mTDC, mLastTime), Direction.BACK, false);
                break;

            case "PREFER_USER_EMAIL":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                // loadFragment(MyEmailFragment.newInstance(),Direction.FORDWARD, false);
                break;

            case "PREFER_USER_PASS":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyPassFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case "PREFER_NOTIFICACIONES":
                loadFragment(NotificacionesPrefFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case "PREFER_NOTIFICACIONES_BACK":
                //loadFragment(SecurityFragment.newInstance(MENU_AJUSTES, ""), Direction.BACK, false);
                //loadFragment(SecuritySettignsFragment.newInstance(MENU_AJUSTES, ""), Direction.BACK, false);
                loadFragment(SecuritySettignsFragment.newInstance(),Direction.BACK, false);
                break;
            /** Eventos BACK **/
            case "PREFER_USER_LISTA":
                //loadFragment(ListaOpcionesFragment.newInstance(isEsAgente, mName, mEmail, mUserImage), Direction.BACK, false);
                finish();
                break;
            case "PREFER_SECURITY_SUCCESS_PASS":
                //loadFragment(SecurityFragment.newInstance(getIntent().getIntExtra(MENU, 0), Recursos.MESSAGE_CHANGE_PASS), Direction.BACK, false);
                //loadFragment(SecuritySettignsFragment.newInstance(getIntent().getIntExtra(MENU, 0), Recursos.MESSAGE_CHANGE_PASS), Direction.BACK, false);
                loadFragment(SecuritySettignsFragment.newInstance(), Direction.BACK, false);
                break;
            case "DISABLE_BACK":
                if (data.toString().equals("true")) {
                    disableBackButton = true;
                } else {
                    disableBackButton = false;
                }
                break;
            case "PREFER_USER_CANCELACION":
                String desc = getResources().getString(R.string.cacncel_desc);
                if (App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false)/* &&
                        App.getInstance().getPrefs().loadDataInt(ID_ESTATUS_EMISOR) == IdEstatus.ADQUIRENTE.getId()*/) {
                    desc = getResources().getString(R.string.cacncel_desc_adq);
                }
                loadFragment(CancelAccountFragment.newInstance(getResources().getString(R.string.cancel_title), desc), Direction.FORDWARD, false);
                break;
            case "PREFER_CANCEL_RESULT":
                loadFragment(CancelResultFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case "PREFER_CANCEL":
                finish();
                Intent intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // Si el boton no esta deshabilitado realizamos las operaciones de back
        if (!disableBackButton && !isLoaderShow) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof ListaAyudaLegalesFragment) {
                onEvent(PREFER_USER_LISTA, null);
            } else if (currentFragment instanceof DesasociarPhoneFragment) {
                onEvent(PREFER_USER_DESASOCIAR_BACK, null);
            } else if (currentFragment instanceof MyUserFragment) {
                onEvent(PREFER_USER_LISTA, null);
            } else if (currentFragment instanceof MyAccountFragment) {
                onEvent(PREFER_USER_LISTA, null);
            } else if (currentFragment instanceof MyCardFragment) {
                onEvent(PREFER_USER_LISTA, null);
            } else if (currentFragment instanceof MyChangeNip) {
                onEvent(PREFER_USER_CHANGE_NIP_BACK, null);
                if (((MyChangeNip) currentFragment).isCustomKeyboardVisible()) {
                    // ((MyChangeNip) currentFragment).hideKeyboard();
                } else {
                    onEvent(PREFER_USER_CHANGE_NIP_BACK, null);
                }
            } else if (currentFragment instanceof MyEmailFragment) {
                onEvent(PREFER_USER_MY_USER_BACK, null);
            } else if (currentFragment instanceof MyPassFragment) {
                onEvent(PREFER_USER_MY_USER_BACK, null);
            } else if (currentFragment instanceof ListaAyudaLegalesFragment) {
                onEvent(PREFER_USER_MY_USER_BACK, null);
            } else if (currentFragment instanceof MyHelpContactanos) {
                onEvent(PREFER_USER_HELP_BACK, null);
            } else if (currentFragment instanceof MyCardReportaTarjetaFragment) {
                onEvent(PREFER_USER_LISTA, null);
            } else if (currentFragment instanceof MyHelpAcercaApp) {
                onEvent(PREFER_USER_HELP_BACK, null);
            } else if (currentFragment instanceof MyHelpContactanosCorreo) {
                onEvent(PREFER_USER_HELP_CONTACT_BACK, null);
            } else if (currentFragment instanceof AvisoPrivacidadFragment) {
                onEvent(PREFER_USER_PRIVACIDAD_BACK, null);
            } else if (currentFragment instanceof MyDongleFragment) {
                onEvent(PREFER_USER_LISTA, null);
            } else if (currentFragment instanceof TerminosyCondicionesFragment) {
                onEvent(PREFER_USER_TERMINOS_BACK, null);
            } else if (currentFragment instanceof CuentaReembolsoFragment) {
                onEvent(PREFER_USER_CUENTA_REEMBOLSO_BACK, null);
            //} else if (currentFragment instanceof SecurityFragment) {
            } else if (currentFragment instanceof SecuritySettignsFragment) {
                onEvent(PREFER_USER_LISTA, null);
            } else if (currentFragment instanceof NotificacionesPrefFragment) {
                onEvent(PREFER_NOTIFICACIONES_BACK, null);
            } else {
                super.onBackPressed();
            }
        }
    }


    /**
     * Resultado de tomar una foto o escoger una de galeria, se envia el resultado al CameraManager
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ListaOpcionesFragment mFragment = (ListaOpcionesFragment) getSupportFragmentManager().findFragmentById(R.id.container);

        CameraManager cameraManager = mFragment.getCameraManager();
        // Enviamos datos recibidos al CameraManager
        cameraManager.setOnActivityResult(requestCode, resultCode, data);

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

    /**
     * Se encarga de actualizar el campo de cardStatusId del SingletonUser con el statusId
     * de servicio
     *
     * @param response
     */
    public void sendSuccessEstatusCuentaToView(EstatusCuentaResponse response) {
        String statusId = response.getData().getStatusId();
        SingletonUser.getInstance().setCardStatusId(statusId);

    }

    public void sendErrorEstatusCuentaToView(String mensaje) {
        showDialogMesage(mensaje);
        //onEventListener.onEvent("DISABLE_BACK", false);
    }

    public void sendSuccessDatosCuentaToView(ActualizarDatosCuentaResponse response) {
        // mName
        // mTDC
        // mLastTime
        mTDC = response.getData().get(0).getTarjeta();
        mLastTime = response.getData().get(0).getUltimoInicioSesion();
        SingletonUser.getInstance().setUltimaTransaccion(mLastTime);

        // Eliminar despues de pruebas
        //        mTDC = response.getData().get(0).getTarjeta();
//        mLastTime = response.getData().get(0).getUltimoInicioSesion();
//
//        mNameTV.setText(mName);
//        mCuentaTV.setText(getResources().getString(R.string.tarjeta) + ": " +
//                        StringUtils.maskReference(StringUtils.format(mTDC, SPACE, 4,4,4,4), '*', 4));
//        mLastTimeTV.setText(getResources().getString(R.string.used_card_last_time) + ": \n" + mLastTime);
//
//        printCard(mTDC);
//        SingletonUser.getInstance().setUltimaTransaccion(mLastTime);
    }

    public void sendErrorDatosCuentaToView(String mensaje) {
        showDialogMesage(mensaje);
        // onEventListener.onEvent("DISABLE_BACK", false);
    }


}
