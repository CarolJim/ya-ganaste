package com.pagatodo.yaganaste.ui.account.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IAccountView;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterOld;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class GetCardFragment extends GenericFragment implements View.OnClickListener,IAccountView{
    private String TAG = GetCardFragment.class.getName();
    private View rootview;
    @BindView(R.id.edtxtGetCard)
    public StyleEdittext edtxtGetCard;
    @BindView(R.id.btnNoCard)
    public StyleButton btnNoCard;
    @BindView(R.id.btnGetCardNext)
    public StyleButton btnGetCardNext;


    private AccountPresenterOld accountPresenter;



    public GetCardFragment() {
    }

    public static GetCardFragment newInstance() {
        GetCardFragment fragmentRegister = new GetCardFragment();
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
        accountPresenter = new AccountPresenterOld(this);
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

        rootview = inflater.inflate(R.layout.fragment_get_card, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnNoCard.setOnClickListener(this);
        btnGetCardNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNoCard:
                noCardAction();
                break;
            case R.id.btnGetCardNext:
                getCardData();
                break;
            default:
                break;
        }
    }

    private void noCardAction(){

        accountPresenter.selectStepNoCard();
    }

    private void getCardData(){
        if (edtxtGetCard.getText().toString().trim().length() == 16)
            if (UtilsNet.isOnline(getActivity()))
                accountPresenter.checkCardAssigment(edtxtGetCard.getText().toString().trim()); // Validamos la tarjeta
            else
                Toast.makeText(getActivity(), R.string.no_internet_access, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "Verifica el Número de tu Tarjeta", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void nextStepAccountFlow(String event) {
        onEventListener.onEvent(event,null);
    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
    }
}

