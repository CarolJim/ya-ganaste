package com.pagatodo.yaganaste.ui._controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.ui.account.register.LegalsFragment;
import com.pagatodo.yaganaste.ui.preferuser.ListaLegalesFragment;
import com.pagatodo.yaganaste.ui.preferuser.ListaOpcionesFragment;

import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.PRIVACIDAD;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;

public class PreferUserActivity extends ToolBarActivity implements OnEventListener {

    private boolean isEsAgente;

    public static String PREFER_USER_LISTA = "PREFER_USER_LISTA";
    public static String PREFER_USER_LEGALES = "PREFER_USER_LEGALES";
    public static String PREFER_USER_CLOSE = "PREFER_USER_CLOSE";
    public static String PREFER_USER_PRIVACIDAD = "PREFER_USER_PRIVACIDAD";
    public static String PREFER_USER_TERMINOS = "PREFER_USER_TERMINOS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefer_user);

        isEsAgente = SingletonUser.getInstance().getDataUser().isEsAgente();
        isEsAgente = false;

        loadFragment(ListaOpcionesFragment.newInstance(isEsAgente));

        // CReamos las referencias al AcoountInteractot
       // AccountInteractorNew.
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
        switch (event) {
            case "PREFER_USER_LEGALES":
                loadFragment(ListaLegalesFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case "PREFER_USER_CLOSE":
                //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
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
                loadFragment(ListaOpcionesFragment.newInstance(isEsAgente), Direction.BACK, false);
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
}
