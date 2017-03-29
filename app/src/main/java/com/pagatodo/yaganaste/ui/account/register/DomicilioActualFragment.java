package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DomicilioActualFragment extends GenericFragment implements View.OnClickListener {

    private View rootview;

    @BindView(R.id.editStreet)
    CustomValidationEditText editStreet;
    @BindView(R.id.editExtNumber)
    CustomValidationEditText editExtNumber;
    @BindView(R.id.editIntNumber)
    CustomValidationEditText editIntNumber;
    @BindView(R.id.editZipCode)
    CustomValidationEditText editZipCode;
    @BindView(R.id.editState)
    CustomValidationEditText editState;
    @BindView(R.id.spColonia)
    AppCompatSpinner spColonia;
    @BindView(R.id.btnBackDomicilioActual)
    Button btnBackDomicilioActual;
    @BindView(R.id.btnNextDomicilioActual)
    Button btnNextDomicilioActual;

    public DomicilioActualFragment() {
    }

    public static DomicilioActualFragment newInstance() {
        DomicilioActualFragment fragmentRegister = new DomicilioActualFragment();
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

        rootview = inflater.inflate(R.layout.fragment_domicilio_actual, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }
    }

}

