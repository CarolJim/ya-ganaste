package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.LandingActivitiesEnum;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;

/**
 * Created by Jordan on 27/07/2017.
 */

public class LandingActivity extends SupportFragmentActivity implements Animation.AnimationListener {

    public static String LANDING_EXTRAS_ARRAY_DRAWABLE = "LANDING_EXTRAS_ARRAY_DRAWABLE";
    public static String LANDING_EXTRAS_BACKGROUND_IMAGE = "LANDING_EXTRAS_BACKGROUND_IMAGE";

    private int[] drawable;
    private int backImage;
    private Animation animFadeIn, animFadeOut;
    private int imagesIndex;
    private ImageView imageView;

    private Runnable runnable;
    private Handler handler;
    private static int HANDLER_TIME = 2000;
    boolean animaationEnd = true;
    int animationCounter = 1;

    public static Intent createIntent(Context context, LandingActivitiesEnum landingActivitiesEnum) {
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
        setContentView(R.layout.activity_landing);
        imageView = (ImageView) findViewById(R.id.imageLanding);
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
        imageView.setImageResource(drawable[0]);

        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animFadeOut.setAnimationListener(this);
        animFadeIn.setAnimationListener(this);

        startTimerNextAnimation();
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
}
