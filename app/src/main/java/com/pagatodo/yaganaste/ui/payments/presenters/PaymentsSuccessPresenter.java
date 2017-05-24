package com.pagatodo.yaganaste.ui.payments.presenters;

import android.content.Context;

import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentSuccessFragment;
import com.pagatodo.yaganaste.ui.payments.interactors.PaymentsSuccessInteractor;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentsSuccessManaget;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsSuccessPresenter;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_TAEPDS;

/**
 * Created by jmhernandez on 22/05/2017.
 */

public class PaymentsSuccessPresenter implements IPaymentsSuccessPresenter ,PaymentsSuccessManaget {

    private PaymentsSuccessInteractor iteractor;
    Context context;
    EjecutarTransaccionResponse result;
    public PaymentsSuccessPresenter(Context ctx, EjecutarTransaccionResponse res) {
        context =ctx;
        result= res;
        iteractor =new PaymentsSuccessInteractor(this,ctx,result);
    }

    @Override
    public void validaEmail(String email) {
        iteractor.validateMail(email);
    }

    @Override
    public void goToNextStepAccount(String event, Object data) {

    }

    @Override
    public void onSucces(WebService ws, Object msgSuccess) {
        if(ws == ENVIAR_TICKET_TAEPDS){

        }else {

        }
    }

    @Override
    public void onError(WebService ws, Object error) {

    }

    @Override
    public void hideLoader() {

    }
}
