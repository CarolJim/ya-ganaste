package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.dto.ErrorObject;

import org.json.JSONException;

public interface IAprovView<T> extends IProgressView<T>, View {

    void showErrorAprov(ErrorObject error);

    void finishAssociation();

}
