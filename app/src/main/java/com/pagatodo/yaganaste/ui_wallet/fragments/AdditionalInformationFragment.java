package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.InformacionAdicionalFragment;
import com.pagatodo.yaganaste.ui.adquirente.managers.InformationAdicionalManager;
import com.pagatodo.yaganaste.ui.adquirente.presenters.InfoAdicionalPresenter;
import com.pagatodo.yaganaste.ui.adquirente.presenters.interfaces.IinfoAdicionalPresenter;
import com.pagatodo.yaganaste.ui_wallet.patterns.FormBuilder;
import com.pagatodo.yaganaste.utils.UI;

import static com.pagatodo.yaganaste.utils.UtilsGraphics.Dp;

public class AdditionalInformationFragment extends GenericFragment implements RadioGroup.OnCheckedChangeListener{

    private IinfoAdicionalPresenter infoAdicionalPresenter;
    private LinearLayout layout;

    public static AdditionalInformationFragment newInstance() {
        return new AdditionalInformationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoAdicionalPresenter = new InfoAdicionalPresenter(null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        );
        layout.setPadding(Dp(10,layout), Dp(27,layout), Dp(10,layout), Dp(40,layout));
        initViews();
        return layout;
    }

    @Override
    public void initViews() {
        FormBuilder builder = new FormBuilder(getContext());
        //builder.infoAditionalCuestion(this).inflate(layout);
        builder.setTitle(R.string.title_informacion_adicional).inflate(layout);
        builder.setTitle(R.string.sub_titulo_info_adic).inflate(layout);
        builder.setQuestion(R.string.publicServantQuestion,false,this).inflate(layout);
        builder.setQuestion(R.string.mexicanQuestion,true,this).inflate(layout);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        UI.hideKeyBoard(getActivity());
        switch (checkedId) {
            case R.id.radioBtnPublicServantNo:
                //onHasFamiliarNoCheck();
                break;
            case R.id.radioBtnPublicServantYes:
                //onHasFamiliarYesCheck();
                break;
            case R.id.radioBtnEresMexaNo:
                //onIsMexaNoCheck();
                break;
            case R.id.radioBtnEresMexaYes:
                //onIsMexaYesCheck();
                break;
        }
    }
}
