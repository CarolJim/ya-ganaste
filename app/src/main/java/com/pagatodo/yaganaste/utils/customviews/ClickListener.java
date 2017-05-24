package com.pagatodo.yaganaste.utils.customviews;

import android.view.View;

/**
 * Created by Jordan on 23/05/2017.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
