package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ChangeStatusOperadorRequest;
import com.pagatodo.yaganaste.interfaces.IChangeOperador;
import com.pagatodo.yaganaste.interfaces.IChangeStatusOperador;
import com.pagatodo.yaganaste.interfaces.View;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui_wallet.fragments.DetalleOperadorFragment;
import com.pagatodo.yaganaste.ui_wallet.interactors.ChangeStatusOperadorIteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.Iloginstarbucks;

public class ChangeStatusOperadorPresenter implements IChangeStatusOperador {

    IChangeOperador iChangeOperador;

    ChangeStatusOperadorIteractor changeStatusOperadorIteractor;

    private Context context;



    public ChangeStatusOperadorPresenter(Context context) {
        this.context = context;
        changeStatusOperadorIteractor= new ChangeStatusOperadorIteractor(this);
    }



    @Override
    public void onSucces(WebService ws, Object msgSuccess) {
        iChangeOperador.succedoperador(msgSuccess.toString());
        iChangeOperador.hideLoader();
    }

    @Override
    public void onError(WebService ws, Object error) {
        iChangeOperador.failoperador(error.toString());
        iChangeOperador.hideLoader();
    }

    @Override
    public void change(String usuario, int newstatus) {
        iChangeOperador.showLoader(" ");
        ChangeStatusOperadorRequest request  = new ChangeStatusOperadorRequest();
        request.setNombreUsuario(usuario);
        request.setIdEstatus(newstatus);
        changeStatusOperadorIteractor.changeStatus(request);



    }


    public void setIView(View accountView) {

        this.iChangeOperador = (IChangeOperador) accountView;

    }
}
