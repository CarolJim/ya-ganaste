package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.GetoperadoresResponse;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.interfaces.IChangeOperador;
import com.pagatodo.yaganaste.interfaces.View;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui_wallet.interactors.GetOperatorsIteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IGetOperators;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IgetlistOperatorsiteractor;

import java.util.List;

public class GetOperatorsPresenter implements IgetlistOperatorsiteractor {

    IGetOperators operators ;
    GetOperatorsIteractor getOperatorsIteractor;
Context context;


    public GetOperatorsPresenter(Context context) {
        this.context = context;
        getOperatorsIteractor = new GetOperatorsIteractor(this);
    }

    public void setIView(View accountView) {

        this.operators = (IGetOperators) accountView;

    }



    public void onSucces(WebService ws, List<Operadores> msgSuccess) {

        switch (ws) {
            case CHANGE_STATUS_OPERADOR:
                operators.succedGetOperador(msgSuccess);
                break;
        }
    }

    @Override
    public void onSucces(WebService ws, Object msgSuccess) {


        switch (ws) {
            case CHANGE_STATUS_OPERADOR:
                GetoperadoresResponse data = (GetoperadoresResponse) msgSuccess;
                List<Operadores> list = data.getData();
                operators.succedGetOperador(list);
                break;
        }
    }

    @Override
    public void onError(WebService ws, Object error) {

    }

    @Override
    public void getListOperators() {

        getOperatorsIteractor.getlistoperadores();



    }
}
