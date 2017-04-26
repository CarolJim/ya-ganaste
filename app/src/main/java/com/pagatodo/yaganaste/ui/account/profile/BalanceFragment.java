package com.pagatodo.yaganaste.ui.account.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.IAccountCardNIPView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.BorderTitleLayout;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.MainActivity.GO_TO_LOGIN;
import static com.pagatodo.yaganaste.ui._controllers.MainActivity.SELECTION;
import static com.pagatodo.yaganaste.ui._controllers.SessionActivity.EVENT_GO_OTP;
import static com.pagatodo.yaganaste.ui._controllers.SessionActivity.EVENT_TO_PAYMENTS;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class BalanceFragment extends GenericFragment implements View.OnClickListener {

    public static String PIN_TO_CONFIRM = "PIN_TO_CONFIRM";
    private static int PIN_LENGHT = 4;
    private View rootview;
    @BindView(R.id.imgArrowBalanceLeft)
    ImageView imgArrowBalanceLeft;
    @BindView(R.id.imgArrowBalanceRight)
    ImageView imgArrowBalanceRight;
    @BindView(R.id.btnLogin)
    StyleButton btnLogin;
    ProgressLayout progressLayout;

    public BalanceFragment() {
    }

    public static BalanceFragment newInstance() {
        BalanceFragment fragmentRegister = new BalanceFragment();
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
                SingletonUser.getInstance().getDataUser().getEstatusAgente() == CRM_DOCTO_APROBADO ?
                        VISIBLE : GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgArrowBalanceLeft:
                onEventListener.onEvent(EVENT_GO_OTP,null);
                break;
            case R.id.imgArrowBalanceRight:
                onEventListener.onEvent(EVENT_TO_PAYMENTS,null);
                break;

            case R.id.btnLogin:
                Intent intent = new Intent(getActivity(),AccountActivity.class);
                intent.putExtra(SELECTION,GO_TO_LOGIN);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}