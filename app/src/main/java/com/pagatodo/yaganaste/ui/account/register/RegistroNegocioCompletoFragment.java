package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_COUCHMARK;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class RegistroNegocioCompletoFragment extends GenericFragment implements View.OnClickListener{

    private View rootview;
    @BindView(R.id.btnCongratulationNext)
    StyleButton btnCongratulationNext;

    public RegistroNegocioCompletoFragment() {
    }

    public static RegistroNegocioCompletoFragment newInstance() {
        RegistroNegocioCompletoFragment fragmentRegister = new RegistroNegocioCompletoFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_register_complete, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnCongratulationNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCongratulationNext:

                onEventListener.onEvent(EVENT_COUCHMARK,null);

                break;
            default:
                break;
        }
    }
}
