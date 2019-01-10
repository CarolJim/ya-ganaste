package com.pagatodo.yaganaste.ui._controllers;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.account.login.MainFragment;
import com.pagatodo.yaganaste.utils.ForcedUpdateChecker;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.Objects;

import static android.os.Process.killProcess;
import static android.os.Process.myPid;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_LOGIN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.IS_FROM_TIMER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;

public class MainActivity extends ToolBarActivity implements ForcedUpdateChecker.OnUpdateNeededListener {

    View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.activity_fragment_container);
        initViews();
        String action = Objects.requireNonNull(getIntent().getExtras()).getString(SELECTION);

        assert action != null;

        if (action!=null) {
            if (action.equals(MAIN_SCREEN)) {
                Preferencias prefs = App.getInstance().getPrefs();

                if ((prefs.containsData(IS_OPERADOR)) || (prefs.containsData(HAS_SESSION) || !RequestHeaders.getTokenauth().isEmpty())) {
                    Intent intent = new Intent(this, AccountActivity.class);
                    intent.putExtra(SELECTION, GO_TO_LOGIN);
                    intent.putExtra(IS_FROM_TIMER, getIntent().getExtras().getBoolean(IS_FROM_TIMER, false));
                    setResult(RESULT_OK);
                    startActivity(intent);
                    finish();
                } else {
                    ForcedUpdateChecker.with(this).onUpdateNeeded(this).check();
                    //loadFragment(MainFragment.newInstance(), true);
                    Intent intent = new Intent(this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                    if (getIntent().getExtras().getBoolean(IS_FROM_TIMER, false)) {
                        //UI.createSimpleCustomDialog(getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo), this.getSupportFragmentManager(), CustomErrorDialog.class.getSimpleName());
                        UI.showAlertDialog(this, getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo),
                                R.string.title_aceptar, (dialogInterface, i) -> {
                                });
                    }
                }
            }
        }else{
            ForcedUpdateChecker.with(this).onUpdateNeeded(this).check();
            //loadFragment(MainFragment.newInstance(), true);
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
            if (getIntent().getExtras().getBoolean(IS_FROM_TIMER, false)) {
                //UI.createSimpleCustomDialog(getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo), this.getSupportFragmentManager(), CustomErrorDialog.class.getSimpleName());
                UI.showAlertDialog(this, getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo),
                        R.string.title_aceptar, (dialogInterface, i) -> {
                        });
            }
        }
    }

    private void initViews() {
        rootLayout = findViewById(R.id.root_layout);
        changeToolbarVisibility(false);
    }

    @Override
    public void onBackPressed() {
        //setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }

    @Override
    public void onUpdateNeeded() {
        App.getDatabaseReference().child("Version/Size_ADT").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    long size_app = dataSnapshot.getValue(Long.class);
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setCancelable(false)
                            .setTitle(getString(R.string.title_update))
                            .setMessage(getString(R.string.text_update_forced))
                            .setPositiveButton("Actualizar",
                                    (dialog1, which) -> {
                                        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                                            Log.e(getString(R.string.app_name), "Tamaño App: "+ (size_app * 1024)
                                                    + "  Tamaño Disponible: "+Long.valueOf(Utils.getAvailableInternalMemorySize()));
                                        }
                                        /* Validar el tamaño necesario para actualizar la App */
                                        if ((size_app * 1024) < Long.valueOf(Utils.getAvailableInternalMemorySize())) {
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse("market://details?id=" + App.getContext().getPackageName()));
                                            startActivity(i);
                                            killProcess(myPid());
                                        } else {
                                            showDialogUninstallApps();
                                        }
                                    })
                            .setNegativeButton("No gracias",
                                    (dialog12, which) -> killProcess(myPid())).create();
                    dialog.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showDialogUninstallApps() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(getString(R.string.title_free_space))
                .setMessage(getString(R.string.desc_free_space))
                .setPositiveButton("Liberar espacio",
                        (dialog1, which) -> {
                            Intent intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
                            startActivity(intent);
                            killProcess(myPid());
                        })
                .setNegativeButton("Cancelar",
                        (dialog12, which) -> killProcess(myPid())).create();
        dialog.show();
    }
}

