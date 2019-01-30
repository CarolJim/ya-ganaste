package com.pagatodo.yaganaste.modules.charge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetMountFragment;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;

public class ChargeActivity extends LoaderActivity {

    public static Intent createIntent(Activity activity){
        return new Intent(activity,ChargeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);

        loadFragment(GetMountFragment.newInstance(SingletonUser.getInstance().getDataUser()
                .getAdquirente().getAgentes().get(0).getNombreNegocio()),R.id.fragment_container,false);
        //init();
    }

    private void init(){
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }
}
