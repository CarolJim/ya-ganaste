package com.pagatodo.yaganaste.modules.management.apis;

import com.pagatodo.yaganaste.App;

import java.util.HashMap;

import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE_SESSION;

public class FriggsHeaders {
    public static HashMap<String, String> getHeadersBasic(){
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Yg-" + App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));
        return headers;
    }
}
