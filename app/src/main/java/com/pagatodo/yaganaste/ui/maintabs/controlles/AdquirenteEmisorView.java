package com.pagatodo.yaganaste.ui.maintabs.controlles;

import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.interfaces.IProgressView;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public interface AdquirenteEmisorView<T extends IEnumTab> extends TabsView<T>, MovementsView, IProgressView<String>{

}
