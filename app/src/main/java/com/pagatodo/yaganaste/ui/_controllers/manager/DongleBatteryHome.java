package com.pagatodo.yaganaste.ui._controllers.manager;

import android.os.Bundle;

import com.pagatodo.yaganaste.R;

import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui.preferuser.presenters.MyDongleFragment;

public class DongleBatteryHome extends LoaderActivity implements OnEventListener{
    public final static String EVENT_GO_INSERT_DONGLE = "EVENT_GO_INSERT_DONGLE";
    private Preferencias pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_container);
        pref = App.getInstance().getPrefs();
        onEvent(EVENT_GO_INSERT_DONGLE, null);
    }


    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_GO_INSERT_DONGLE:
                loadFragment(MyDongleFragment.newInstance(), Direction.FORDWARD, false);
                break;
        }
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }
}
