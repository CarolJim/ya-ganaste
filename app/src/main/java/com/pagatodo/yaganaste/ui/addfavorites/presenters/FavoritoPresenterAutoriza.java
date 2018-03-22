package com.pagatodo.yaganaste.ui.addfavorites.presenters;

import android.content.Context;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.otp.presenter.OtpPresenterAbs;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.manager.AddToFavoritesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.EditFavoritesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity;

/**
 * Created by Armando Sandoval on 07/11/2017.
 */

public class FavoritoPresenterAutoriza extends OtpPresenterAbs {

    AddToFavoritesActivity addNewFavoritesActivity;
    public FavoritoPresenterAutoriza(Context context, AddToFavoritesActivity addnewView) {
        super(context);
        this.addNewFavoritesActivity = addnewView;
    }

    EditFavoritesActivity editFavoritesActivity;
    public FavoritoPresenterAutoriza(Context context, EditFavoritesActivity addnewView) {
        super(context);
        this.editFavoritesActivity = addnewView;
    }

    FavoritesActivity favoritesActivity;
    public FavoritoPresenterAutoriza(Context context, FavoritesActivity favoritesActivity) {
        super(context);
        this.favoritesActivity = favoritesActivity;
    }


    @Override
    public void onError(Errors error) {

    }

    @Override
    public void onOtpGenerated(String otp) {
        RequestHeaders.setTokenFreja(otp);
    }
}
