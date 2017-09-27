package com.pagatodo.yaganaste.interfaces;

import android.app.Fragment;
import android.graphics.Bitmap;

/**
 * Created by Armando Sandoval on 25/09/2017.
 */

public interface IPursePresenter {
    void flipCard(int container, Fragment fragment);
    void loadCardCover(int container, Fragment fragment);

    void setPurseReference(IPurseView view);

}
