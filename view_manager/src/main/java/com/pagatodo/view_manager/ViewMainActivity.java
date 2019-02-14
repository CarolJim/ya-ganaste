package com.pagatodo.view_manager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.pagatodo.view_manager.components.HeadAccount;
import com.pagatodo.view_manager.controllers.dataholders.HeadAccountData;

public class ViewMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_main);
        HeadAccount headAccount = findViewById(R.id.headaccount);
        headAccount.bind(HeadAccountData.create("","#F0FAD0"
        ,"Ismael Cruz", "1234 12345 648"),null);
    }
}
