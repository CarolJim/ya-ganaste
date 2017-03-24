package com.pagatodo.yaganaste.ui.account.login;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class LoginFragment extends GenericFragment implements View.OnClickListener{

    private View rootview;

    @BindView(R.id.edtxtLoginExistUserWritePass)
    StyleEdittext edtxtPassword;
    @BindView(R.id.btnLoginExistUser)
    StyleButton btnLogin;
    @BindView(R.id.imgLoginExistProfile)
    CircleImageView imgLoginExistProfile;
    @BindView(R.id.txtLoginExistBalanceUsername)
    StyleButton txtLoginExistBalanceUsername;
    @BindView(R.id.txtLoginExistUserRecoverPass)
    StyleTextView txtLoginExistUserRecoverPass;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragmentRegister = new LoginFragment();
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
        UI.showToast("LoginFragment",getActivity());
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

        rootview = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnLogin.setOnClickListener(this);
        txtLoginExistUserRecoverPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLoginExistUser:

                break;
            case R.id.txtLoginExistUserRecoverPass:

                break;
            default:
                break;
        }
    }

}

