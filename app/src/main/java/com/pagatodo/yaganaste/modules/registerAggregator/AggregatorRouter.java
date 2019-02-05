package com.pagatodo.yaganaste.modules.registerAggregator;

import android.content.Intent;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.registerAggregator.BusinessData.BusinessDataFragment;
import com.pagatodo.yaganaste.modules.registerAggregator.LinkedQRs.LinkedQRsFragment;
import com.pagatodo.yaganaste.modules.registerAggregator.NameQR.NameQRFragment;
import com.pagatodo.yaganaste.modules.registerAggregator.PhysicalCodeAggregator.PhysicalCodeAggregatorFragment;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;

import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;

public class AggregatorRouter implements AggregatorContracts.Router {

    private AggregatorActivity activity;

    public AggregatorRouter(AggregatorActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showBusinessData(Direction direction) {
        activity.loadFragment(BusinessDataFragment.newInstance(activity),
                R.id.container_aggregator, Direction.FORDWARD, false);
    }

    @Override
    public void showPhysicalCodeAggregator(Direction direction) {
        activity.loadFragment(PhysicalCodeAggregatorFragment.newInstance(),
                R.id.container_aggregator, Direction.FORDWARD, false);
    }

    @Override
    public void showScanQR(Direction direction) {
        Intent intent = new Intent(activity, ScannVisionActivity.class);
        intent.putExtra(ScannVisionActivity.QRObject, true);
        activity.startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }

    @Override
    public void showDigitalCode(Direction direction) {

    }

    @Override
    public void showAssignNameQR(Direction direction) {
        activity.loadFragment(NameQRFragment.newInstance("", R.string.title_code_digital_fragment),
                R.id.container_aggregator, Direction.FORDWARD, false);
    }

    @Override
    public void showLinkedQRs(Direction direction) {
        activity.loadFragment(LinkedQRsFragment.newInstance(),R.id.container_aggregator,
                Direction.FORDWARD, false);
    }

}
