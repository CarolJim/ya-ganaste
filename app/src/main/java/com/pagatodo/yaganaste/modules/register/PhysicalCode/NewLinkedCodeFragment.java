package com.pagatodo.yaganaste.modules.register.PhysicalCode;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.ButterKnife;

public class NewLinkedCodeFragment extends GenericFragment  {

    private View rootView;
    private RegActivity activity;

    public static NewLinkedCodeFragment newInstance(String textDisplay, int restext){
        NewLinkedCodeFragment fragment = new NewLinkedCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DISPLAY",textDisplay);
        bundle.putInt("TITLE",restext);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (RegActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.new_linked_ode_fragment,container,false);
        initViews();
        return rootView;

    }


    public void initViews() {
        ButterKnife.bind(this, rootView);
        if (getArguments() != null){
            ((StyleTextView)rootView.findViewById(R.id.plate)).setText(getArguments().getString("DISPLAY"));
            ((StyleTextView)rootView.findViewById(R.id.title_fragment)).setText(getArguments().getInt("TITLE"));
        }

        rootView.findViewById(R.id.button_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getRouter().showLinkedCodes();
            }
        });
    }


}
