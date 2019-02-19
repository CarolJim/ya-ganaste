package com.pagatodo.yaganaste.modules.payReloadsServices;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;

import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.pagatodo.view_manager.recyclers.RechargesRecycler;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.payReloadsServices.allRecharges.AllRechargesFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.FAVORITE_PROCESS;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE_FROM_CERO;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_RECARGAS;

public class PayReloadsServicesRouter implements PayReloadsServicesContracts.Router {

    private PayReloadsServicesActivity activity;

    PayReloadsServicesRouter(PayReloadsServicesActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onShowAllRecharges(ArrayList<IconButtonDataHolder> list) {
       this.activity.loadFragment(AllRechargesFragment.newInstance(list),R.id.fragment_container,Direction.NONE,false);
    }

    @Override
    public void onShowAllFavRefills() {
        ///fragment_container
        //this.activity.loadFragment(null);

    }

    @Override
    public void onShowAddFavoriteRecharge() {

    }
}
