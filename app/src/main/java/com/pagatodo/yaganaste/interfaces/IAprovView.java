package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.dto.ErrorObject;

import org.json.JSONException;

/**
 * Created by flima on 20/02/2017.
 */

public interface IAprovView<T> extends IProgressView<T>, View {

    void showErrorAprov(ErrorObject error);

    void finishAssociation();

}
