package com.pagatodo.yaganaste.net;

import com.android.volley.VolleyError;

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
        switch (error.networkResponse.statusCode){

            default:
                errorMessage = error.getMessage();
        }

        return errorMessage;

    }
}
