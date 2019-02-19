package com.pagatodo.yaganaste.modules.usernovalid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;

public class UserValidActivity extends LoaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_valid);
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }




}
