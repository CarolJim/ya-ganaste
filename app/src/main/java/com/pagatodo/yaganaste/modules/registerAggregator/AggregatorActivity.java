package com.pagatodo.yaganaste.modules.registerAggregator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterAggregatorNew;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;

import java.util.ArrayList;

public class AggregatorActivity extends LoaderActivity implements
        AggregatorContracts.Presenter, AggregatorContracts.Listener {

    private AggregatorRouter router;

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, AggregatorActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggregator);
        RegisterAggregatorNew.getInstance().setqRs(new ArrayList<>());
        router = new AggregatorRouter(this);
        initViews();
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }

    @Override
    public void initViews() {
        router.showBusinessData(Direction.FORDWARD);
    }

    @Override
    public void nextStep() {

    }

    @Override
    public void backStep() {

    }

    public AggregatorRouter getRouter() {
        return router;
    }
}
