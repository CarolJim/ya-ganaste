package com.pagatodo.yaganaste.interfaces.enums;

import androidx.annotation.AnimRes;

import com.pagatodo.yaganaste.R;

/**
 * Created by Jordan on 10/04/2017.
 */

public enum Direction {
    FORDWARD(R.anim.slide_from_right, R.anim.slide_to_left),
    BACK(R.anim.slide_from_left, R.anim.slide_to_right),
    NONE(0, 0);

    private int enterAnimation;
    private int exitAnimation;

    Direction(@AnimRes int enterAnimation, @AnimRes int exitAnimation) {
        this.enterAnimation = enterAnimation;
        this.exitAnimation = exitAnimation;
    }

    public int getEnterAnimation() {
        return enterAnimation;
    }

    public int getExitAnimation() {
        return exitAnimation;
    }
}
