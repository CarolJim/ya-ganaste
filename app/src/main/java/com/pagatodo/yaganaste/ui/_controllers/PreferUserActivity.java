package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.ui.preferuser.DesasociarPhoneFragment;
import com.pagatodo.yaganaste.ui.preferuser.ListaLegalesFragment;
import com.pagatodo.yaganaste.ui.preferuser.ListaOpcionesFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyAccountFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyChangeNip;
import com.pagatodo.yaganaste.ui.preferuser.MyEmailFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyPassFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyUserFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyCardFragment;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.PRIVACIDAD;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;

public class PreferUserActivity extends LoaderActivity implements OnEventListener {

    public static String PREFER_USER_LISTA = "PREFER_USER_LISTA";
    public static String PREFER_USER_LEGALES = "PREFER_USER_LEGALES";
    public static String PREFER_USER_CLOSE = "PREFER_USER_CLOSE";
    public static String PREFER_USER_PRIVACIDAD = "PREFER_USER_PRIVACIDAD";
    public static String PREFER_USER_TERMINOS = "PREFER_USER_TERMINOS";
    public static String PREFER_USER_DESASOCIAR = "PREFER_USER_DESASOCIAR";
    public static String PREFER_USER_DESASOCIAR_BACK = "PREFER_USER_DESASOCIAR_BACK";
    public static String PREFER_USER_MY_USER = "PREFER_USER_MY_USER";
    public static String PREFER_USER_MY_ACCOUNT = "PREFER_USER_MY_ACCOUNT";
    public static String PREFER_USER_MY_CARD = "PREFER_USER_MY_CARD";
    public static String PREFER_USER_MY_USER_BACK = "PREFER_USER_MY_USER_BACK";
    public static String PREFER_USER_EMAIL = "PREFER_USER_EMAIL";
    public static String PREFER_USER_PASS = "PREFER_USER_PASS";
    public static String PREFER_USER_CHANGE_NIP = "PREFER_USER_CHANGE_NIP";
    public static String PREFER_USER_CHANGE_NIP_BACK = "PREFER_USER_CHANGE_NIP_BACK";
    /**
     * Acciones para dialogo de confirmacion en cerrar session
     */
    DialogDoubleActions doubleActions = new DialogDoubleActions() {
        @Override
        public void actionConfirm(Object... params) {
            setResult(ToolBarActivity.RESULT_LOG_OUT);
            //mPreferPresenter.closeSession(mContext);
            finish();
        }

        @Override
        public void actionCancel(Object... params) {

        }
    };
    private boolean isEsAgente;
    private String mName, mEmail, mUserImage;
    private boolean disableBackButton = false;
    private AccountPresenterNew presenterAccount;
    private PreferUserPresenter mPreferPresenter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefer_user);

        isEsAgente = SingletonUser.getInstance().getDataUser().isEsAgente();
        isEsAgente = false;

        mName = SingletonUser.getInstance().getDataUser().getUsuario().getNombre() + " " +
                SingletonUser.getInstance().getDataUser().getUsuario().getPrimerApellido();
        mEmail = SingletonUser.getInstance().getDataUser().getUsuario().getNombreUsuario();
        mUserImage = SingletonUser.getInstance().getDataUser().getUsuario().getImagenAvatarURL();


        loadFragment(ListaOpcionesFragment.newInstance(isEsAgente, mName, mEmail, mUserImage));

        presenterAccount = new AccountPresenterNew(this);
        mPreferPresenter = new PreferUserPresenter(this);

        mContext = this;
        // CReamos las referencias al AcoountInteractot
        // AccountInteractorNew.

        //  mPreferPresenter.testToast();

        System.gc();
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
        // Este metodo hace referencia al padre para ocultar el icono de preferencias de la ToolBar
        setVisibilityPrefer(false);
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
            case "PREFER_USER_LEGALES":
                loadFragment(ListaLegalesFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case "PREFER_USER_DESASOCIAR":
                loadFragment(DesasociarPhoneFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case "PREFER_USER_DESASOCIAR_BACK":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyAccountFragment.newInstance(), Direction.BACK, false);
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
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                LegalsDialog legalsDialog = LegalsDialog.newInstance(PRIVACIDAD);
                legalsDialog.show(this.getFragmentManager(), LegalsDialog.TAG);
                break;

            case "PREFER_USER_TERMINOS":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                LegalsDialog legalsTerminosDialog = LegalsDialog.newInstance(TERMINOS);
                legalsTerminosDialog.show(this.getFragmentManager(), LegalsDialog.TAG);
                break;

            case "PREFER_USER_MY_USER":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyUserFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case "PREFER_USER_MY_USER_BACK":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyUserFragment.newInstance(), Direction.BACK, false);
                break;

            case "PREFER_USER_MY_ACCOUNT":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyAccountFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case "PREFER_USER_MY_CARD":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyCardFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case "PREFER_USER_CHANGE_NIP":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyChangeNip.newInstance(), Direction.FORDWARD, false);
                break;

            case "PREFER_USER_CHANGE_NIP_BACK":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyCardFragment.newInstance(), Direction.BACK, false);
                break;

            case "PREFER_USER_EMAIL":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                // loadFragment(MyEmailFragment.newInstance(),Direction.FORDWARD, false);
                break;

            case "PREFER_USER_PASS":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                loadFragment(MyPassFragment.newInstance(), Direction.FORDWARD, false);
                break;

            /** Eventos BACK **/
            case "PREFER_USER_LISTA":
                loadFragment(ListaOpcionesFragment.newInstance(isEsAgente, mName, mEmail, mUserImage), Direction.BACK, false);
                break;

            case "DISABLE_BACK":
                if (data.toString().equals("true")) {
                    disableBackButton = true;
                } else {
                    disableBackButton = false;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // Si el boton no esta deshabilitado realizamos las operaciones de back
        if (!disableBackButton && !isLoaderShow) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof ListaLegalesFragment) {
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
                if (((MyChangeNip) currentFragment).isCustomKeyboardVisible()) {
                    ((MyChangeNip) currentFragment).hideKeyboard();
                } else {
                    onEvent(PREFER_USER_CHANGE_NIP_BACK, null);
                }
            } else if (currentFragment instanceof MyEmailFragment) {
                onEvent(PREFER_USER_MY_USER_BACK, null);
            } else if (currentFragment instanceof MyPassFragment) {
                onEvent(PREFER_USER_MY_USER_BACK, null);
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
}
