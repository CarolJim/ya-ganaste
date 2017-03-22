package com.pagatodo.yaganaste.ui._controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.IAccountView;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.presenters.AccountPresenter;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.login.LetsStartFragment;
import com.pagatodo.yaganaste.ui.account.login.LoginFragment;
import com.pagatodo.yaganaste.ui.account.register.GetCardFragment;

import static com.pagatodo.yaganaste.ui.account.login.LetsStartFragment.EVENT_GET_CARD;
import static com.pagatodo.yaganaste.ui.account.login.LetsStartFragment.EVENT_LOGIN;


public class LoginActivity extends SupportFragmentActivity implements OnEventListener{
    private Preferencias pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);
        loadFragment(LetsStartFragment.newInstance(), DIRECTION.FORDWARD, true);
        pref = App.getInstance().getPrefs();
    }


    @Override
    public void onEvent(String event, Object o) {

        switch (event){

            case EVENT_GET_CARD:
                loadFragment(GetCardFragment.newInstance(), DIRECTION.FORDWARD, true);
                break;

            case EVENT_LOGIN:
                loadFragment(LoginFragment.newInstance(), DIRECTION.FORDWARD, true);
                break;
        }


    }
}
