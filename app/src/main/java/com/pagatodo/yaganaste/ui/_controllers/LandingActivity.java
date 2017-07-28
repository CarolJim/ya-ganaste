package com.pagatodo.yaganaste.ui._controllers;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;

/**
 * Created by Jordan on 27/07/2017.
 */

public class LandingActivity extends SupportFragmentActivity implements Animation.AnimationListener {

    public static String LANDING_EXTRAS_ARRAY_DRAWABLE = "LANDING_EXTRAS_ARRAY_DRAWABLE";

    private int[] drawable;
    private Animation animFadeIn, animFadeOut;
    private int imagesIndex;
    private ImageView imageView;

    private Runnable runnable;
    private Handler handler;
    private static int HANDLER_TIME = 2000;
    boolean animaationEnd = true;
    int animationCounter = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_landing);
        imageView = (ImageView) findViewById(R.id.imageLanding);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            drawable = (int[]) extras.get(LANDING_EXTRAS_ARRAY_DRAWABLE);
        }

        /*drawable = new Drawable[5];
        Resources res = getResources();
        drawable[0] = res.getDrawable(R.drawable.img_couch_em_1);
        drawable[1] = res.getDrawable(R.drawable.img_couch_em_2);
        drawable[2] = res.getDrawable(R.drawable.img_couch_em_3);
        drawable[3] = res.getDrawable(R.drawable.img_couch_em_4);
        drawable[4] = res.getDrawable(R.drawable.img_couch_em_5);*/


        imagesIndex = drawable.length;
        imageView.setImageResource(drawable[0]);

        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animFadeOut.setAnimationListener(this);
        animFadeIn.setAnimationListener(this);

        startTimerNextAnimation();
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
                //imageView.setVisibility(View.INVISIBLE);
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
        finishActivity();
    }
}
