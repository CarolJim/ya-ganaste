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
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.ui.preferuser.ListaLegalesFragment;
import com.pagatodo.yaganaste.ui.preferuser.ListaOpcionesFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.PRIVACIDAD;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;

public class PreferUserActivity extends ToolBarActivity implements OnEventListener {

    private boolean isEsAgente;
    private String mName, mEmail, mUserImage;

    public static String PREFER_USER_LISTA = "PREFER_USER_LISTA";
    public static String PREFER_USER_LEGALES = "PREFER_USER_LEGALES";
    public static String PREFER_USER_CLOSE = "PREFER_USER_CLOSE";
    public static String PREFER_USER_PRIVACIDAD = "PREFER_USER_PRIVACIDAD";
    public static String PREFER_USER_TERMINOS = "PREFER_USER_TERMINOS";

    private AccountPresenterNew presenterAccount;

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

        // CReamos las referencias al AcoountInteractot
       // AccountInteractorNew.
    }

    public AccountPresenterNew getPresenter() {

        return this.presenterAccount;
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

    DialogDoubleActions doubleActions = new DialogDoubleActions() {
        @Override
        public void actionConfirm(Object... params) {
            //    Toast.makeText(PreferUserActivity.this, "Click Cerrar Session", Toast.LENGTH_SHORT).show();
            getPresenter().logout();
        }

        @Override
        public void actionCancel(Object... params) {

        }
    };

    @Override
    public void onEvent(String event, Object data) {
        switch (event) {
            case "PREFER_USER_LEGALES":
                loadFragment(ListaLegalesFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case "PREFER_USER_CLOSE":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                UI.createSimpleCustomDialog("", "¿Desea realmente cerrar sesión?", getSupportFragmentManager(),
                        doubleActions, true, true);
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
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof ListaLegalesFragment) {
            onEvent(PREFER_USER_LISTA, null);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       CameraManager.getInstance().setOnActivityResult(requestCode, resultCode, data);
    }
    /*@Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        Toast.makeText(PreferUserActivity.this, "Sucess Cerrar Session", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(DataSourceResult error) {
        Toast.makeText(PreferUserActivity.this, "Fail Cerrar Session", Toast.LENGTH_SHORT).show();
    }*/
}
