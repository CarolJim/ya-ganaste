package com.pagatodo.yaganaste.modules.newsend.AllFavorites;

import android.view.View;

public interface IReciclerfavoritos {
    void onRecyclerItemClick(View v, int position);
    void onRecyclerItemClick(View v, int position,boolean edit, Object object);
}
