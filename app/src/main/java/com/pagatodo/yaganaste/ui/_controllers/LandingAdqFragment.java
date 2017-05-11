package com.pagatodo.yaganaste.ui._controllers;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jmhernandez on 09/05/2017.
 */

public class LandingAdqFragment extends Activity implements Animation.AnimationListener{


    @BindView(R.id.tutorialPage1)
    RelativeLayout tutorialPage1;
    @BindView(R.id.tutorialPage2)
    RelativeLayout tutorialPage2;
    Animation animFadeIn, animFadeOut;
    int animationCounter = 0;
    boolean animaationEnd = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_adq_landing);
        ButterKnife.bind(this, this);

        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animFadeOut.setAnimationListener(this);
        animFadeIn.setAnimationListener(this);
        tutorialPage1.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (animation == animFadeIn) {

        }
        if (animation == animFadeOut) {
            animaationEnd = false;
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animFadeOut) {
            animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            animFadeIn.setAnimationListener(this);
            switch (animationCounter) {
                case 0:
                    tutorialPage1.setVisibility(View.GONE);
                    tutorialPage2.setVisibility(View.VISIBLE);
                    tutorialPage2.startAnimation(animFadeIn);
                    break;
                case 1:
                    tutorialPage2.setVisibility(View.GONE);
                  break;
                default:
                    break;
            }
        }

        if (animation == animFadeIn) {
            //tutorialPage2.setVisibility(View.VISIBLE);
            switch (animationCounter) {
                case 0:
                    tutorialPage1.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    tutorialPage2.setVisibility(View.VISIBLE);
                    break;
            }
            animaationEnd = true;
            animationCounter++;
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (animaationEnd) {
                    animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
                    animFadeOut.setAnimationListener(this);
                    switch (animationCounter) {
                        case 0:
                            tutorialPage1.startAnimation(animFadeOut);
                            break;
                        case 1:
                            tutorialPage2.startAnimation(animFadeOut);
                            finishActivity();
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            break;
                        case 2:
                            break;
                        default:

                            break;
                    }
                }
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishActivity();
    }
    private void finishActivity() {
        this.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}
