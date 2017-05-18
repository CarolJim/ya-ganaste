package com.pagatodo.yaganaste.ui._controllers;

import android.os.Bundle;
import android.view.Menu;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.preferuser.ListaLegalesFragment;
import com.pagatodo.yaganaste.ui.preferuser.ListaOpcionesFragment;

public class PreferUserActivity extends ToolBarActivity implements OnEventListener {

    private boolean isEsAgente;

    public static String PREFER_USER_LEGALES = "preferUserLegales";
    public static String PREFER_USER_CLOSE = "preferUserClose";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefer_user);

        isEsAgente = SingletonUser.getInstance().getDataUser().isEsAgente();
        isEsAgente = false;

        loadFragment(ListaOpcionesFragment.newInstance(isEsAgente));
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
                loadFragment(ListaLegalesFragment.newInstance());
                break;

            case "PREFER_USER_CLOSE":

                break;
        }
    }
}
