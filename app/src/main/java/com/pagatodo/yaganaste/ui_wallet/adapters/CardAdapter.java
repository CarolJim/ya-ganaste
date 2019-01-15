package com.pagatodo.yaganaste.ui_wallet.adapters;

import androidx.cardview.widget.CardView;

/**
 * Created by icruz on 14/12/2017.
 */

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
