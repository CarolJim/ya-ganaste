package com.pagatodo.yaganaste.interfaces;

/**
 * Created by jvazquez on 13/02/2017.
 */

public interface OnEventListener<Data>{
    void onEvent(String event, Data data);
}

