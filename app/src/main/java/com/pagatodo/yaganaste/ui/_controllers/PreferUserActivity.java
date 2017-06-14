package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;

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
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserPresenter;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.PRIVACIDAD;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;

public class PreferUserActivity extends LoaderActivity implements OnEventListener {

    private boolean isEsAgente;
    private String mName, mEmail, mUserImage;
    private boolean disableBackButton = false;

    public static String PREFER_USER_LISTA = "PREFER_USER_LISTA";
    public static String PREFER_USER_LEGALES = "PREFER_USER_LEGALES";
    public static String PREFER_USER_CLOSE = "PREFER_USER_CLOSE";
    public static String PREFER_USER_PRIVACIDAD = "PREFER_USER_PRIVACIDAD";
    public static String PREFER_USER_TERMINOS = "PREFER_USER_TERMINOS";
    public static String PREFER_USER_DESASOCIAR = "PREFER_USER_DESASOCIAR";

    private AccountPresenterNew presenterAccount;
    private PreferUserPresenter mPreferPresenter;

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
        // CReamos las referencias al AcoountInteractot
        // AccountInteractorNew.

      //  mPreferPresenter.testToast();
    }

    public IPreferUserPresenter getUserPresenter() {
        return this.mPreferPresenter;
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

    /**
     * Acciones para dialogo de confirmacion en cerrar session
     */
    DialogDoubleActions doubleActions = new DialogDoubleActions() {
        @Override
        public void actionConfirm(Object... params) {
            setResult(ToolBarActivity.RESULT_LOG_OUT);
            finish();
        }

        @Override
        public void actionCancel(Object... params) {

        }
    };

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

            case "PREFER_USER_CLOSE":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                UI.createSimpleCustomDialog("", "¿Desea realmente cerrar sesión?", getSupportFragmentManager(),
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

            /** Eventos BACK **/
            case "PREFER_USER_LISTA":
                loadFragment(ListaOpcionesFragment.newInstance(isEsAgente, mName, mEmail, mUserImage), Direction.BACK, false);
                break;

            case "DISABLE_BACK":
                if (data.toString().equals("true")) {
                    disableBackButton = true;
                }else{
                    disableBackButton = false;
                }
                break;
        }
    }

    public PreferUserPresenter getPreferPresenter() {
        return mPreferPresenter;
    }

    @Override
    public void onBackPressed() {
        // Si el boton no esta deshabilitado realizamos las operaciones de back
        if (!disableBackButton) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof ListaLegalesFragment) {
                onEvent(PREFER_USER_LISTA, null);
            }else if (currentFragment instanceof DesasociarPhoneFragment) {
                onEvent(PREFER_USER_LISTA, null);
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
