package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.dto.ErrorObject;

/**
 * Created by Juan on 17/08/2017.
 */

public interface IChangeNipView extends IProgressView, View {

    void onFrejaNipChanged();

    void showErrorNip(ErrorObject error);
}
