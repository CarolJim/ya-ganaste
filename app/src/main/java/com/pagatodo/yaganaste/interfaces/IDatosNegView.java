package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.SubGiro;

import java.util.List;

/**
 * @author Juan Guerra on 05/05/2017.
 */

public interface IDatosNegView<E> extends IProgressView<E> {
    public void setGiros(List<SubGiro> giros);
}
