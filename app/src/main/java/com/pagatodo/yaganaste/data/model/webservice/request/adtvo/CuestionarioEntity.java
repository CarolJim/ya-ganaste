package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class CuestionarioEntity implements Serializable {

    private int PreguntaId;
    private boolean Valor;

    public CuestionarioEntity() {
    }

    public CuestionarioEntity(int preguntaId, boolean valor) {
        PreguntaId = preguntaId;
        Valor = valor;
    }

    public int getPreguntaId() {
        return PreguntaId;
    }

    public void setPreguntaId(int preguntaId) {
        PreguntaId = preguntaId;
    }

    public boolean isValor() {
        return Valor;
    }

    public void setValor(boolean valor) {
        Valor = valor;
    }
}
