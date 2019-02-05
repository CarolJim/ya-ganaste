package com.pagatodo.yaganaste.modules.registerAggregator;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.registerAggregator.BusinessData.BusinessDataFragment;
import com.pagatodo.yaganaste.ui._controllers.BussinesActivity;

public class AggregatorRouter implements AggregatorContracts.Router {

    private BussinesActivity activity;

    public AggregatorRouter(BussinesActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showBusinessData(Direction direction) {
        activity.loadFragment(BusinessDataFragment.newInstance(activity), R.id.container_aggregator,Direction.FORDWARD,false);
    }
}
