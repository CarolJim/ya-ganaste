package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

import com.pagatodo.yaganaste.data.model.Ventas;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.SaldoRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.GetResumenDiaResponse;
import com.pagatodo.yaganaste.interfaces.IVentasAdmin;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui_wallet.fragments.VentasDiariasFragment;
import com.pagatodo.yaganaste.ui_wallet.interactors.VentasDiariasIteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IVentasDiarias;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.GET_RESUMENDIA;

public class VentasDiariasPresenter implements IVentasDiarias {
    Context context ;
    VentasDiariasIteractor ventasDiariasIteractor;
    IVentasAdmin viewventas;
    public VentasDiariasPresenter(Context context, IVentasAdmin viewventas) {
        this.context = context;
        this.viewventas = viewventas;
        ventasDiariasIteractor = new VentasDiariasIteractor(this);
    }
    @Override
    public void onSucces(WebService ws, Object msgSuccess) {

        switch (ws) {
            case GET_RESUMENDIA:
                viewventas.hideLoader();
                viewventas.succedGetResumenDia("Succes");

                break;
        }

    }

    @Override
    public void onError(WebService ws, Object error) {
        viewventas.hideLoader();
        viewventas.failGetResumenDia(error.toString());
    }

    @Override
    public void obtenerSaldo() {

    }

    @Override
    public void obtenerResumendia(String fecha) {
        viewventas.showLoader("Cargando...");
        ventasDiariasIteractor.obtenerResumendia(fecha);
    }
}
