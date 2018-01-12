package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.view.View;

/**
 * Created by Armando Sandoval on 09/01/2018.
 */

public interface  RecyclerViewOnItemClickListener {

    void onClick(View v, int position);

    void onLongClick(View v, int position);
}
