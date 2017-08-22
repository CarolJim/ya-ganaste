package com.pagatodo.yaganaste.freja.reset.managers;

import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.interfaces.IProgressView;
import com.pagatodo.yaganaste.interfaces.View;

/**
 * @author Juan Guerra on 21/08/2017.
 */

public interface IResetNIPView<T> extends IProgressView<T>, View {

    void showErrorReset(ErrorObject error);

    void finishReseting();

    void onResetingFailed();
}
