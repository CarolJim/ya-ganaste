package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui_wallet.interactors.GetOperatorsIteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IGetOperators;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IgetlistOperatorsiteractor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.utils.Recursos.AGENTE_NUMBER;

public class GetOperatorsPresenter implements IgetlistOperatorsiteractor {

    IGetOperators operators;
    GetOperatorsIteractor getOperatorsIteractor;
    Context context;


    public GetOperatorsPresenter(Context context, IGetOperators operators) {
        this.context = context;
        getOperatorsIteractor = new GetOperatorsIteractor(this);
        this.operators = operators;
    }

    @Override
    public void onSucces(WebService ws, Object msgSuccess) {
        operators.hideLoader();
        switch (ws) {
            case GET_OPERADOR:
                List<Operadores> list = new ArrayList<>();
                try {
                    list = new DatabaseManager().getOperadoresByAgente(App.getInstance().getPrefs().loadData(AGENTE_NUMBER));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                operators.succedGetOperador(list);
                break;
        }
    }

    @Override
    public void onError(WebService ws, Object error) {
    operators.hideLoader();
    operators.failGetOperador("Error al consultar operadores");
    }

    @Override
    public void getListOperators() {
        operators.showLoader("Cargando ..");
        getOperatorsIteractor.getlistoperadores();
    }
}
