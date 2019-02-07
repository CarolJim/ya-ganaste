package com.pagatodo.yaganaste.modules.emisor.GeneratePIN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.view_manager.buttons.ButtonContinue;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CardSuccessFragment extends GenericFragment implements View.OnClickListener{

    private View rootView;

    @BindView(R.id.btn_continue)
    ButtonContinue btnContinue;

    public static CardSuccessFragment newInstance(){
        return new CardSuccessFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.card_success_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnContinue.active();
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Objects.requireNonNull(getActivity()).finish();
    }
}
