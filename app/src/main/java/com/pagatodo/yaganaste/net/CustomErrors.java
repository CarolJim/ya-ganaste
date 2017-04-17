package com.pagatodo.yaganaste.net;

import com.android.volley.VolleyError;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;

/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 *
 *
 * Clase para definir los errores que serán manejados por la aplicación.
 *
 */
public class CustomErrors {

    public static String getError(VolleyError error){
        String errorMessage ="";

        if(error != null && error.networkResponse != null) {
            switch (error.networkResponse.statusCode) {

                case 404:
                    errorMessage = App.getInstance().getString(R.string.message_generic_ws);
                    break;

                default:
                    if(error.getMessage() != null)
                        errorMessage = error.getMessage();
            }

        }else{
            errorMessage = App.getInstance().getString(R.string.message_generic_ws);
        }

        return errorMessage;

    }
}
