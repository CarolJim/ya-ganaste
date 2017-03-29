package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_ADDRESS_DATA_BACK;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class TienesTarjetaFragment extends GenericFragment implements View.OnClickListener, ValidationForms,IAccountRegisterView {

    private static int LENGTH_CARD = 16;
    private View rootview;
    @BindView(R.id.radioHasCard)
    RadioGroup radioHasCard;
    @BindView(R.id.radioBtnYes)
    AppCompatRadioButton radioBtnYes;
    @BindView(R.id.radioBtnNo)
    AppCompatRadioButton radioBtnNo;
    @BindView(R.id.txtMessageCard)
    StyleTextView txtMessageCard;
    @BindView(R.id.editCardNumber)
    StyleEdittext editCardNumber;
    @BindView(R.id.btnBackTienesTarjeta)
    Button btnBackTienesTarjeta;
    @BindView(R.id.btnNextTienesTarjeta)
    Button btnNextTienesTarjeta;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;
    private String cardNumber = "";
    private boolean isCardAssigned;

    private AccountPresenterNew accountPresenter;

    public TienesTarjetaFragment() {
    }

    public static TienesTarjetaFragment newInstance() {
        TienesTarjetaFragment fragmentRegister = new TienesTarjetaFragment();
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
        accountPresenter = new AccountPresenterNew(this);
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
        rootview = inflater.inflate(R.layout.fragment_tienes_tarjeta, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        progressLayout.setVisibility(GONE);
        btnNextTienesTarjeta.setOnClickListener(this);
        btnBackTienesTarjeta.setOnClickListener(this);
        radioHasCard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioBtnYes.getId() == checkedId){ // Selecciona SI Tarjeta
                    editCardNumber.setEnabled(true);
                }else if(radioBtnNo.getId() == checkedId){ // Selecciona NO Tarjeta
                    editCardNumber.setText("");
                    editCardNumber.setEnabled(false);
                    accountPresenter.assignCard();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnBackTienesTarjeta:
                backStepRegister(EVENT_ADDRESS_DATA_BACK,null);
                break;

            case R.id.btnNextTienesTarjeta:

                if(radioBtnYes.isChecked()){
                    validateForm();
                }else if(radioBtnNo.isChecked()){
                    createAccount();
                }
                break;

            default:
                break;
        }
    }

    private void createAccount(){
        if(isCardAssigned){
             /*TODO Realizar el registro del usuario implementando la llamda al ( a los ) servicios necesarios.*/
            accountPresenter.createUser();
        }
    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        getDataForm();
        if(cardNumber.isEmpty() || cardNumber.length() < LENGTH_CARD){
            showValidationError("Verifica tu tarjeta");
            return;
        }
        onValidationSuccess();
    }

    @Override
    public void showValidationError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
    }

    @Override
    public void onValidationSuccess() {
        accountPresenter.checkCardAssigment(cardNumber);
    }

    @Override
    public void getDataForm() {
        cardNumber = editCardNumber.getText().toString().trim();
    }


    @Override
    public void userCreatedSuccess(String message) {
        progressLayout.setTextMessage(message);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                hideLoader();
                nextStepRegister(EVENT_GO_ASOCIATE_PHONE,null); // Mostramos la pantalla para obtener tarjeta.
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    @Override
    public void nextStepRegister(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void backStepRegister(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void showLoader(String message) {
        progressLayout.setTextMessage(message);
        progressLayout.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(GONE);
    }

    @Override
    public void showError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
    }


    @Override
    public void accountAvaliableAssigned(String result) {
        progressLayout.setTextMessage(result);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                isCardAssigned = true;
                hideLoader();
                txtMessageCard.setText(getString(R.string.tienes_tarjeta_no));
            }
        }, DELAY_MESSAGE_PROGRESS);


    }

    @Override
    public void accountConfirmed(String result) {
        hideLoader();
        isCardAssigned = true;
        createAccount();
    }
}

