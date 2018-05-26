package com.pagatodo.yaganaste.ui._controllers;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.account.login.MainFragment;
import com.pagatodo.yaganaste.utils.ForcedUpdateChecker;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;

import static android.os.Process.killProcess;
import static android.os.Process.myPid;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_LOGIN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.IS_FROM_TIMER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ROL;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;

public class MainActivity extends ToolBarActivity implements ForcedUpdateChecker.OnUpdateNeededListener {

    View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        initViews();
        String action = getIntent().getExtras().getString(SELECTION);

        if (action.equals(MAIN_SCREEN)) {
            Preferencias prefs = App.getInstance().getPrefs();

            if ((prefs.containsData(IS_OPERADOR))||(prefs.containsData(HAS_SESSION) || !RequestHeaders.getTokenauth().isEmpty())) {
                Intent intent = new Intent(this, AccountActivity.class);
                intent.putExtra(SELECTION, GO_TO_LOGIN);
                intent.putExtra(IS_FROM_TIMER, getIntent().getExtras().getBoolean(IS_FROM_TIMER, false));
                setResult(RESULT_OK);
                startActivity(intent);
                finish();
            } else {
                ForcedUpdateChecker.with(this).onUpdateNeeded(this).check();
                loadFragment(MainFragment.newInstance(), true);
                if (getIntent().getExtras().getBoolean(IS_FROM_TIMER, false)) {
                    //UI.createSimpleCustomDialog(getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo), this.getSupportFragmentManager(), CustomErrorDialog.class.getSimpleName());

                    UI.showAlertDialog(this, getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                }
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
    public void onUpdateNeeded(String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(getString(R.string.title_update))
                .setMessage(getString(R.string.text_update_forced))
                .setPositiveButton("Actualizar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse("market://details?id=" + App.getContext().getPackageName()));
                                startActivity(i);
                                killProcess(myPid());
                            }
                        })
                .setNegativeButton("No gracias",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                killProcess(myPid());
                            }
                        }).create();
        dialog.show();
    }
}

