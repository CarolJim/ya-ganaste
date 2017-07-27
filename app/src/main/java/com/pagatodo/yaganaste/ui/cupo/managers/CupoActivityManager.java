package com.pagatodo.yaganaste.ui.cupo.managers;

/**
 * Created by Jordan on 25/07/2017.
 */

public interface CupoActivityManager {
    void onBtnBackPress();

    void callEvent(String event, Object data);

    void hideToolBar();

    void showToolBar();
}
