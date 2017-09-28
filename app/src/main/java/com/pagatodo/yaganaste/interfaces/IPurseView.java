package com.pagatodo.yaganaste.interfaces;

import android.app.Fragment;

import java.util.List;

/**
 * Created by Armando Sandoval on 25/09/2017.
 */

public interface IPurseView {
    void flipCard(int container, Fragment fragment, boolean isBackShown);
    void loadCardCover(int container, Fragment fragment);
    void changeBGVisibility(boolean isBackShown);
    boolean isAnimationAble();


}
