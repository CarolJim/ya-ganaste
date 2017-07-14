package com.pagatodo.yaganaste.interfaces;

import android.content.Context;

/**
 * Created by flima on 18/04/2017.
 */

public interface Command<T> {
    void action(Context context, T... params);
}
