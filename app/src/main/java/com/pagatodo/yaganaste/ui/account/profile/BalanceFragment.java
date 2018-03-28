package com.pagatodo.yaganaste.ui.account.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.SessionActivity.EVENT_GO_OTP;
import static com.pagatodo.yaganaste.ui._controllers.SessionActivity.EVENT_TO_PAYMENTS;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_LOGIN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_AGENTE;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class BalanceFragment extends GenericFragment implements View.OnClickListener {

    public static String PIN_TO_CONFIRM = "PIN_TO_CONFIRM";
    private static int PIN_LENGHT = 4;
    @BindView(R.id.imgArrowBalanceLeft)
    ImageView imgArrowBalanceLeft;
    @BindView(R.id.imgArrowBalanceRight)
    ImageView imgArrowBalanceRight;
    @BindView(R.id.btnLogin)
    StyleButton btnLogin;
    ProgressLayout progressLayout;
    private View rootview;

    public BalanceFragment() {
    }

    public static BalanceFragment newInstance() {
        BalanceFragment fragmentRegister = new BalanceFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.balance_fragment, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnLogin.setOnClickListener(this);
        imgArrowBalanceLeft.setOnClickListener(this);
        imgArrowBalanceRight.setOnClickListener(this);
        imgArrowBalanceRight.setVisibility(
                App.getInstance().getPrefs().loadDataInt(ESTATUS_AGENTE)==CRM_DOCTO_APROBADO ?
                        VISIBLE : GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgArrowBalanceLeft:
                onEventListener.onEvent(EVENT_GO_OTP, null);
                break;
            case R.id.imgArrowBalanceRight:
                onEventListener.onEvent(EVENT_TO_PAYMENTS, null);
                break;

            case R.id.btnLogin:
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                intent.putExtra(SELECTION, GO_TO_LOGIN);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}