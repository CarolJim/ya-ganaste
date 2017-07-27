package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.R;

/**
 * Created by Jordan on 26/07/2017.
 */

public enum CupoSpinnerTypes {
    ESTADO_CIVIL(0, R.string.cual_estado_civil),
    HIJOS(1, R.string.cuantos_hijos),
    RELACION(2, R.string.relacion);

    private int id;
    private int hint;

    CupoSpinnerTypes(int id, int hint){
        this.id = id;
        this.hint = hint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHint() {
        return hint;
    }

    public void setHint(int hint) {
        this.hint = hint;
    }
}
