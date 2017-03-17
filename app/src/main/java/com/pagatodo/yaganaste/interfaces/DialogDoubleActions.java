package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 07/02/17.
 *
 * Interfaz para establecer la acción de cada una de las dos opciones de un Dialog simple.
 */

public interface DialogDoubleActions {

    public void actionConfirm(Object... params);
    public void actionCancel(Object... params);
}
