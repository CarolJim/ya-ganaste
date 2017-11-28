package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.LandingActivitiesEnum;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 27/07/2017.
 */

public class LandingActivity extends SupportFragmentActivity implements Animation.AnimationListener, View.OnClickListener{

    public static String LANDING_EXTRAS_ARRAY_DRAWABLE = "LANDING_EXTRAS_ARRAY_DRAWABLE";
    public static String LANDING_EXTRAS_BACKGROUND_IMAGE = "LANDING_EXTRAS_BACKGROUND_IMAGE";

    @BindView(R.id.ll_omitir)
    LinearLayout ll_omitir;

    private int[] drawable;
    private int backImage;
    private Animation animFadeIn, animFadeOut;
    private int imagesIndex;
    private ImageView imageView;

    private Runnable runnable;
    private Handler handler;
    private static int HANDLER_TIME = 3000;
    boolean animaationEnd = true;
    int animationCounter = 1;
    public static String nameCouchMark = "";
    private int currentApiVersion;

    public static Intent createIntent(Context context, LandingActivitiesEnum landingActivitiesEnum) {
        // Guardamos el nombre del CouckMark que mostramos
        nameCouchMark = landingActivitiesEnum.name();
        return createIntent(context, landingActivitiesEnum.getBackImage(), landingActivitiesEnum.getDrawable());
    }

    public static Intent createIntent(Context context, int backImage, int... drawable) {
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra(LANDING_EXTRAS_BACKGROUND_IMAGE, backImage);
        intent.putExtra(LANDING_EXTRAS_ARRAY_DRAWABLE, drawable);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);
        imageView = (ImageView) findViewById(R.id.imageLanding);
        imageView.setOnClickListener(this);
        ImageView imageViewBack = (ImageView) findViewById(R.id.imageBack);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            drawable = (int[]) extras.get(LANDING_EXTRAS_ARRAY_DRAWABLE);
            backImage = (int) extras.get(LANDING_EXTRAS_BACKGROUND_IMAGE);
        }
        imagesIndex = drawable.length;

        if (backImage != 0) {
            imageViewBack.setImageResource(backImage);
        }

//        if (drawable.length == 0) {
//            imageViewBack.setImageResource(backImage);
//        } else {
//            imageView.setImageResource(drawable[0]);
//        }

        /**
         * Si el nombre del Couchmark es el mismo que PANTALLA_PRINCIPAL_EMISOR, entonces hacemos un
         * setOnClick en el area auxiliar que tenemos en Omitir. Al dar Click terminamos la actividad
         * y quitamos los runnable que controlan la animacion
         */
        if(nameCouchMark.equals(LandingActivitiesEnum.PANTALLA_PRINCIPAL_EMISOR.name()) ||
                nameCouchMark.equals(LandingActivitiesEnum.PANTALLA_COBROS.name())){
            ll_omitir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // handler.removeCallbacks(runnable);
                    finishActivity();
                }
            });
        }

        imageView.setImageResource(drawable[0]);
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animFadeOut.setAnimationListener(this);
        animFadeIn.setAnimationListener(this);

        //startTimerNextAnimation();

        //        Ocultamos la navegacion inferior
//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        decorView.setSystemUiVisibility(uiOptions);


//        // Obtenemos la version de Android
         currentApiVersion = Build.VERSION.SDK_INT;

        // Haemos set de las banderas que necesitamos
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // Solo funciona para versiones de 4.4 superiores
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (animation == animFadeOut) {
            animaationEnd = false;
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animFadeOut) {
            imageView.setImageResource(drawable[animationCounter]);
            imageView.clearAnimation();
            imageView.startAnimation(animFadeIn);
        }

        if (animation == animFadeIn) {
            animaationEnd = true;
            animationCounter++;
            startTimerNextAnimation();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handler.removeCallbacks(runnable);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                startNextAnimation();
                break;
        }
        return true;
    }

    private void startNextAnimation() {
        if (animaationEnd) {
            if (animationCounter < imagesIndex) {
                imageView.clearAnimation();
                imageView.startAnimation(animFadeOut);
            } else {
                handler.removeCallbacks(runnable);
                finishActivity();
            }
        }
    }

    private void startTimerNextAnimation() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                startNextAnimation();
            }
        };
        handler.postDelayed(runnable, HANDLER_TIME);
    }

    private void finishActivity() {
        this.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        if (animationCounter < drawable.length) {
            imageView.setImageResource(drawable[animationCounter]);
            animationCounter++;
        } else {
            finishActivity();
        }

    }
}
