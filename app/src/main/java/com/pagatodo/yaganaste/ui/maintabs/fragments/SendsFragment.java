package com.pagatodo.yaganaste.ui.maintabs.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.emisor.PaymentToQR.operations.QrOperationActivity;
import com.pagatodo.yaganaste.modules.newsend.SendNewActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import static com.pagatodo.yaganaste.modules.newsend.SendNewActivity.ID_ALL_FAVO;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendsFragment extends GenericFragment implements View.OnClickListener {

    View rootView;

    @BindView(R.id.btn_show_fav)
    StyleButton btn_show_fav;



    public SendsFragment() {
        // Required empty public constructor
    }
    public static SendsFragment newInstance(){
        return new SendsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sends, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        btn_show_fav.setOnClickListener(this::onClick);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_show_fav:
                startActivity(SendNewActivity.createIntent(getActivity(),ID_ALL_FAVO));
                break;

        }


    }
}
