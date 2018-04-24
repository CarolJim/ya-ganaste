package com.pagatodo.yaganaste.data.model.webservice.response.starbucks;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosSbResponse;

import java.util.ArrayList;
import java.util.List;

public class ConsultaMovimientosSBResponse extends RespuestaStarbucks {

    private List<MovimientosSbResponse> Movimientos;

    public ConsultaMovimientosSBResponse(){
        this.Movimientos = new ArrayList<>();
    }

    public List<MovimientosSbResponse> getMovimientos() {
        return Movimientos;
    }

    public void setMovimientos(List<MovimientosSbResponse> movimientos) {
        Movimientos = movimientos;
    }
}
