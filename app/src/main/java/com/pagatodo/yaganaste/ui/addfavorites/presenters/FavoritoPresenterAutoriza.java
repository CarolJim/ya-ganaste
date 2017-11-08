package com.pagatodo.yaganaste.ui.addfavorites.presenters;

import android.content.Context;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.otp.presenter.OtpPresenterAbs;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.manager.AddFavoritesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.AddNewFavoritesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.EditFavoritesActivity;

/**
 * Created by Armando Sandoval on 07/11/2017.
 */

public class FavoritoPresenterAutoriza extends OtpPresenterAbs {

    AddNewFavoritesActivity addNewFavoritesActivity;
    public FavoritoPresenterAutoriza(Context context, AddNewFavoritesActivity addnewView) {
        super(context);
        this.addNewFavoritesActivity = addnewView;
    }

    AddFavoritesActivity addFavoritesActivity;
    public FavoritoPresenterAutoriza(Context context, AddFavoritesActivity addnewView) {
        super(context);
        this.addFavoritesActivity = addnewView;
    }

    EditFavoritesActivity editFavoritesActivity;
    public FavoritoPresenterAutoriza(Context context, EditFavoritesActivity addnewView) {
        super(context);
        this.editFavoritesActivity = addnewView;
    }


    @Override
    public void onError(Errors error) {

    }

    @Override
    public void onOtpGenerated(String otp) {
        RequestHeaders.setTokenFreja(otp);
    }
}
