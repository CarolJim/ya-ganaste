package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.view.View;

import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

/**
 * Created by asandovals on 25/04/2018.
 */

public class RegisterCompleteStarbucksFragment extends GenericFragment implements View.OnClickListener, ValidationForms {



    public static RegisterCompleteStarbucksFragment newInstance() {
        RegisterCompleteStarbucksFragment fragmentRegisterComplete = new RegisterCompleteStarbucksFragment();
        Bundle args = new Bundle();
        fragmentRegisterComplete.setArguments(args);
        return fragmentRegisterComplete;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {

    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {

    }

    @Override
    public void getDataForm() {

    }

    @Override
    public void initViews() {

    }
}
