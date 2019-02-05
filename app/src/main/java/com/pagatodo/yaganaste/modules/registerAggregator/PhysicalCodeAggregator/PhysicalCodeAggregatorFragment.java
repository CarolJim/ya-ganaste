package com.pagatodo.yaganaste.modules.registerAggregator.PhysicalCodeAggregator;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.registerAggregator.AggregatorActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhysicalCodeAggregatorFragment extends GenericFragment implements View.OnClickListener {
    private View rootView;
    private AggregatorActivity activity;
    private StyleButton button_no,button_yes;
    public static PhysicalCodeAggregatorFragment newInstance(){
        return new PhysicalCodeAggregatorFragment();
    }

    public PhysicalCodeAggregatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity=(AggregatorActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_physical_code_aggregator, container, false);
        button_no=(StyleButton)rootView.findViewById(R.id.button_no);
        button_yes=(StyleButton)rootView.findViewById(R.id.button_yes);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        button_no.setOnClickListener(this);
        button_yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_yes:
                activity.getRouter().showScanQR(Direction.FORDWARD);
                break;
            case R.id.button_no:
                activity.getRouter().showAssignNameQR(Direction.FORDWARD);
        }
    }
}
