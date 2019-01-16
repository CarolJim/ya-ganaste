package com.pagatodo.yaganaste.modules.onboarding.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.onboarding.OnboardingActivity;
import com.pagatodo.yaganaste.modules.onboarding.OnboardingContracts;
import com.pagatodo.yaganaste.modules.onboarding.OnboardingRouter;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_LOGIN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment implements OnboardingContracts.Presenter, View.OnClickListener {
    private static OnboardingActivity actv;
    private View rootView;
    ImageView onBack;
    private Button btn_init_session, create_account;

    public StartFragment() {
        // Required empty public constructor
    }

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    public static StartFragment newInstance(OnboardingActivity activity) {
        actv = activity;
        return new StartFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        actv = (OnboardingActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_start, container, false);
        onBack = (ImageView) rootView.findViewById(R.id.onBack);
        btn_init_session = (Button) rootView.findViewById(R.id.btn_init_session);
        create_account = (Button) rootView.findViewById(R.id.create_account);
        initViews();
        return rootView;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        onBack.setOnClickListener(this::onClick);
        btn_init_session.setOnClickListener(this::onClick);
        create_account.setOnClickListener(this::onClick);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.onBack:
                startActivity(OnboardingActivity.createIntent(getActivity()));
                actv.finish();
                actv.showNext(true);
                break;
            case R.id.btn_init_session:
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                intent.putExtra(SELECTION, GO_TO_LOGIN);
                startActivity(intent);
                break;
            case R.id.create_account:
                startActivity(RegActivity.createIntent(getActivity()));
                break;

        }
    }
}
