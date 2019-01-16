package com.pagatodo.yaganaste.interfaces.enums;

import androidx.annotation.StringRes;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;

import java.lang.reflect.Type;

/**
 * @author Juan Guerra on 09/08/2017.
 */

public enum  OnlineTypes  {
    ENVIO1(1, R.string.envio_dinero),
    ENVIO2(2, R.string.envio_dinero),
    ENVIO3(3, R.string.envio_dinero),
    ENVIO4(4, R.string.envio_dinero);

    private int idType;
    private String name;

    OnlineTypes(int idType, @StringRes int name){
        this.idType = idType;
        this.name = App.getInstance().getString(name);
    }

    public int getIdType() {
        return idType;
    }

    public String getName() {
        return name;
    }



    public static class Deserializer implements JsonDeserializer<OnlineTypes> {

        @Override
        public OnlineTypes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            for (OnlineTypes onlineTypes : values()) {
                if (onlineTypes.idType == json.getAsInt()) {
                    return onlineTypes;
                }
            }
            return null;
        }
    }
}
