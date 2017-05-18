package com.pagatodo.yaganaste.ui._controllers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.preferuser.ListaOpcionesFragment;

public class PreferUserActivity extends ToolBarActivity {

    private boolean isEsAgente;

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
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return false;
    }


}
