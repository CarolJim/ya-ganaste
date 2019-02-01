package com.pagatodo.yaganaste.modules.registerAggregator;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.registerAggregator.BusinessData.BusinessDataFragment;

public class AggregatorRouter implements AggregatorContracts.Router {

    private AggregatorActivity activity;

    public AggregatorRouter(AggregatorActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showBusinessData(Direction direction) {
        activity.loadFragment(BusinessDataFragment.newInstance(activity), R.id.container_aggregator,Direction.FORDWARD,false);
    }
}
