package com.pagatodo.yaganaste.ui.account.register;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 31/03/2017.
 */

public class LandingFragment extends SupportFragmentActivity implements Animation.AnimationListener {

    @BindView(R.id.tutorialPage1)
    View tutorialPage1;
    @BindView(R.id.tutorialPage2)
    View tutorialPage2;
    @BindView(R.id.tutorialPage3)
    View tutorialPage3;
    @BindView(R.id.tutorialPage4)
    View tutorialPage4;
    @BindView(R.id.tutorialPage5)
    View tutorialPage5;
    //@BindView(R.id.tutorialPage6)
    //RelativeLayout tutorialPage6;
    Animation animFadeIn, animFadeOut;
    int animationCounter = 0;
    boolean animaationEnd = true;
    private View rootview;
    private String imagePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_landing);
        ButterKnife.bind(this, this);

        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animFadeOut.setAnimationListener(this);
        animFadeIn.setAnimationListener(this);
        tutorialPage1.setVisibility(View.VISIBLE);

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
                    tutorialPage3.setVisibility(View.VISIBLE);
                    tutorialPage3.startAnimation(animFadeIn);
                    break;
                case 2:
                    tutorialPage3.setVisibility(View.GONE);
                    tutorialPage4.setVisibility(View.VISIBLE);
                    tutorialPage4.startAnimation(animFadeIn);
                    break;
                case 3:
                    tutorialPage4.setVisibility(View.GONE);
                    tutorialPage5.setVisibility(View.VISIBLE);
                    tutorialPage5.startAnimation(animFadeIn);
                    break;
                case 4:
                    //tutorialPage5.setVisibility(View.GONE);
                    //tutorialPage6.setVisibility(View.VISIBLE);
                    //tutorialPage6.startAnimation(animFadeIn);
                    break;
                case 5:
                    break;
                default:

                    break;
            }
        }

        if (animation == animFadeIn) {
            //tutorialPage2.setVisibility(View.VISIBLE);
            switch (animationCounter) {
                case 0:
                    tutorialPage2.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    tutorialPage3.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    tutorialPage4.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    tutorialPage5.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    //tutorialPage6.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    break;
                case 6:
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
                            break;
                        case 2:
                            tutorialPage3.startAnimation(animFadeOut);
                            break;
                        case 3:
                            tutorialPage4.startAnimation(animFadeOut);
                            break;
                        case 4:
                            //tutorialPage5.startAnimation(animFadeOut);
                            //break;
                        //case 5:
                            finishActivity();
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
        finishActivity();
    }

    private void finishActivity() {
        this.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
