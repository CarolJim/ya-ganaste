package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 07/02/17.
 * <p>
 * Interfaz para establecer la acción de cada una de las dos opciones de un Dialog simple.
 */

public interface DialogDoubleActions {

    void actionConfirm(Object... params);

    void actionCancel(Object... params);
}
