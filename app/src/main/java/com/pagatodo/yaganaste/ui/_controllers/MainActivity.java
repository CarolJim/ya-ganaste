package com.pagatodo.yaganaste.ui._controllers;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.account.login.MainFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_LOGIN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.IS_FROM_TIMER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_SESSION;

public class MainActivity extends ToolBarActivity {

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    private int revealX, revealY;
    View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        initViews();

        String action = getIntent().getExtras().getString(SELECTION);
        revealX = getIntent().getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
        revealY = getIntent().getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);

        if (action.equals(MAIN_SCREEN)) {
            Preferencias prefs = App.getInstance().getPrefs();
            if (prefs.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty()) {
                Intent intent = new Intent(this, AccountActivity.class);
                intent.putExtra(SELECTION, GO_TO_LOGIN);
                intent.putExtra(IS_FROM_TIMER, getIntent().getExtras().getBoolean(IS_FROM_TIMER, false));
                intent.putExtra(MainActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
                intent.putExtra(MainActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);
                setResult(RESULT_OK);
                startActivity(intent);
                finish();
            } else {
                loadFragment(MainFragment.newInstance(), true);
                if (getIntent().getExtras().getBoolean(IS_FROM_TIMER, false)) {
                    UI.createSimpleCustomDialog(getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo),
                            this.getSupportFragmentManager(), CustomErrorDialog.class.getSimpleName());
                }
                /*if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                        getIntent().hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                        getIntent().hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
                    ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
                    if (viewTreeObserver.isAlive()) {
                        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                revealActivity(revealX, revealY);
                                rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        });
                    }
                }*/
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

    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(1000);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        }
    }
}

