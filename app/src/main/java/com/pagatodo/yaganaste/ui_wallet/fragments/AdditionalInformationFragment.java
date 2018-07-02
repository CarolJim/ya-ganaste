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
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.enums.Parentescos;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.EnumSpinnerAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui.adquirente.fragments.InformacionAdicionalFragment;
import com.pagatodo.yaganaste.ui.adquirente.managers.InformationAdicionalManager;
import com.pagatodo.yaganaste.ui.adquirente.presenters.InfoAdicionalPresenter;
import com.pagatodo.yaganaste.ui.adquirente.presenters.interfaces.IinfoAdicionalPresenter;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoStates;
import com.pagatodo.yaganaste.ui_wallet.holders.InputDataViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.QuestionViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.SpinnerHolder;
import com.pagatodo.yaganaste.ui_wallet.patterns.FormBuilder;
import com.pagatodo.yaganaste.utils.UI;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.UtilsGraphics.Dp;

public class AdditionalInformationFragment extends GenericFragment implements IOnSpinnerClick {

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
        ArrayList<DtoStates> listParentesco = new ArrayList<>(); //

        for (Parentescos parentescos: Parentescos.values()){
            listParentesco.add(new DtoStates(String.valueOf(parentescos.getId()),parentescos.getName()));
        }

        SpinnerHolder parentesco = builder.setSpinner(layout,R.string.parentesco,new StatesSpinnerAdapter(getContext(),
                R.layout.spinner_layout, listParentesco, this));

        SpinnerHolder states = builder.setSpinner(layout,R.string.parentesco,new StatesSpinnerAdapter(getContext(),
                R.layout.spinner_layout, listParentesco, this));

        InputDataViewHolder inputDataViewHolder = builder.setInputText(layout,getContext().getResources().getString(R.string.txt_cargo));

        QuestionViewHolder qCargoPublico = builder.setQuest(layout, R.string.publicServantQuestion, false, (radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.radioBtnPublicServantNo:
                    parentesco.getView().setVisibility(View.GONE);
                    inputDataViewHolder.getView().setVisibility(View.GONE);
                    break;
                case R.id.radioBtnPublicServantYes:
                    parentesco.getView().setVisibility(View.VISIBLE);
                    inputDataViewHolder.getView().setVisibility(View.VISIBLE);
                    break;

            }
        });



        QuestionViewHolder qNacionalidad =
        builder.setQuest(layout, R.string.mexicanQuestion, true, (radioGroup, i) -> {
            UI.hideKeyBoard(getActivity());

        });

        qCargoPublico.inflate(layout);
        parentesco.inflate(layout);
        parentesco.getView().setVisibility(View.GONE);
        inputDataViewHolder.inflate(layout);
        inputDataViewHolder.getView().setVisibility(View.GONE);
        qNacionalidad.inflate(layout);
        states.inflate(layout);
        states.getView().setVisibility(View.GONE);

    }

    @Override
    public void onSpinnerClick() {

    }

    @Override
    public void onSubSpinnerClick() {

    }

    @Override
    public void hideKeyBoard() {

    }

/*
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
    */
}
