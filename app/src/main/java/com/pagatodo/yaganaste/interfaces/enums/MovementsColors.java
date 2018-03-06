package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.R;

/**
 * Created by Jordan on 21/06/2017.
 */

public enum MovementsColors {

    CARGO(1, R.color.redColorNegativeMovements),
    APROBADO(2, R.color.greenColorPositiveMovements), //No debe ser cargo, pero estará aprobado
    PENDIENTE(4, R.color.colorAccent),
    CANCELADO(3, R.color.redColorNegativeMovementsCancel);

    private int color;
    private int type;

    MovementsColors(int movementType, int color) {
        this.color = color;
        this.type = movementType;
    }

    public static MovementsColors getMovementColorByType(int type) {
        for (MovementsColors color : values()) {
            if (color.type == type) {
                return color;
            }
        }
        return APROBADO;
    }

    public int getColor() {
        return color;
    }
}
