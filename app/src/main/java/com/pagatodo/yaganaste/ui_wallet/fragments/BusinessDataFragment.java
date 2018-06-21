package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.DatosNegocioPresenter;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DatosNegocioFragment;
import com.pagatodo.yaganaste.ui_wallet.patterns.FormBuilder;

import java.io.Serializable;
import java.util.List;

import static com.pagatodo.yaganaste.utils.UtilsGraphics.Dp;

public class BusinessDataFragment extends GenericFragment {

    private static final String GIROS = "1";
    private List<Giros> girosComercio;
    //private View rootview;
    private LinearLayout layout;


    public static BusinessDataFragment newInstance(){
        return new BusinessDataFragment();
    }


    public static BusinessDataFragment newInstance(List<Giros> girosComercio) {
        BusinessDataFragment fragmentRegister = new BusinessDataFragment();
        Bundle args = new Bundle();
        args.putSerializable(GIROS, (Serializable) girosComercio);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.datosNegocioPresenter = new DatosNegocioPresenter(getActivity(), this);
        Bundle args = getArguments();
        /*if (args != null) {
            Serializable subgiros = args.getSerializable(GIROS);
            if (subgiros != null) {
                this.girosComercio = (List<Giros>) subgiros;
            }
        }*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        );
        layout.setPadding(0, Dp(27,layout), 0, Dp(40,layout));
        initViews();
        return layout;
    }

    @Override
    public void initViews() {
        FormBuilder builder = new FormBuilder(getContext());
        builder.formBusinessData().inflate(layout);
    }
}
