package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class RegisterBasicInfoFragment extends GenericFragment implements View.OnClickListener {

    private View rootview;
    @BindView(R.id.edtxtRegisterBasicName)
    StyleEdittext edtxtRegisterBasicName;
    @BindView(R.id.edtxtRegisterBasicLastName)
    StyleEdittext edtxtRegisterBasicLastName;
    @BindView(R.id.edtxtRegisterBasicSecondLastName)
    StyleEdittext edtxtRegisterBasicSecondLastName;
    @BindView(R.id.edtxtRegisterBasicPass)
    StyleEdittext edtxtRegisterBasicPass;
    @BindView(R.id.txtRegisterBasicPassMessage)
    StyleTextView txtRegisterBasicPassMessage;
    @BindView(R.id.btnRegisterBasicNext)
    StyleButton btnRegisterBasicNext;
    @BindView(R.id.txtRegisterBasicMail)
    StyleTextView txtRegisterBasicMail;
    @BindView(R.id.imgRegisterBasicPass)
    ImageView imgRegisterBasicPass;


    public RegisterBasicInfoFragment() {
    }

    public static RegisterBasicInfoFragment newInstance() {
        RegisterBasicInfoFragment fragmentRegister = new RegisterBasicInfoFragment();
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

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

        rootview = inflater.inflate(R.layout.fragment_register_basic_info, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnRegisterBasicNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }
    }

}

