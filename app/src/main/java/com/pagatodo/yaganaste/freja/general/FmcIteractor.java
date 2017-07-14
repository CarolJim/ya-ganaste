package com.pagatodo.yaganaste.freja.general;

import android.content.Context;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public interface FmcIteractor {

    void init(Context context);

    void init(Context context, long connectionTimeout, long readTimeout);

    void throwInitException(Exception e);
}