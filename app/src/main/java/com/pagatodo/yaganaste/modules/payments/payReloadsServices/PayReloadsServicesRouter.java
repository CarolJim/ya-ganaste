package com.pagatodo.yaganaste.modules.payments.payReloadsServices;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.payments.payReloadsServices.allRecharges.AllRechargesFragment;

public class PayReloadsServicesRouter implements PayReloadsServicesContracts.Router {

    private PayReloadsServicesActivity activity;

    PayReloadsServicesRouter(PayReloadsServicesActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onShowAllRecharges(PayReloadsServicesActivity.Type type) {
       this.activity.loadFragment(AllRechargesFragment.newInstance(type),R.id.fragment_container,Direction.NONE,false);
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
