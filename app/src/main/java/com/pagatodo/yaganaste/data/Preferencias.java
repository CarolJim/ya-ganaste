package com.pagatodo.yaganaste.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesionUYU;
import com.pagatodo.yaganaste.utils.Utils;

import java.io.Serializable;

public class Preferencias {

    private SharedPreferences preferences;
    private Context context;

    public Preferencias(Context context) {
        this.context = context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveData(int key, String data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString(context.getString(key), data);
        editor.commit();
    }

    public void saveData(String key, String data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString(key, data);
        editor.commit();
    }

    public void saveData(int key, Serializable data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString(String.valueOf(key), Utils.objectToString(data));
        editor.commit();
    }

    public void saveDataBool(String key, boolean data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putBoolean(key, data);
        editor.commit();
    }

    public boolean containsData(String key) {
        return this.preferences.getBoolean(key, false);
    }

    public boolean loadDataBoolean(String key, boolean defValue) {
        return this.preferences.getBoolean(key, defValue);
    }

    public Serializable loadData(String key, Boolean isObject) {
        return Utils.stringToObject(this.preferences.getString(key, null));
    }

    public <T extends Serializable> T loadData(String key, Class<T> type) {
        return type.cast(Utils.stringToObject(this.preferences.getString(key, null)));
    }

    public String loadData(String key) {
        return this.preferences.getString(key, "");
    }

    public String getDataString(int key) {
        return this.preferences.getString(context.getString(key), context.getString(R.string.catalogo_version_init));
    }


    public void clearPreferences() {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.clear();
        editor.commit();
        return;
    }

    public void clearPreference(String key) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.remove(key);
        editor.commit();
        return;
    }

    public void saveDataInt(String key, int data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putInt(key, data);
        editor.commit();
    }

    public int loadDataInt(String key) {
        return this.preferences.getInt(key, -1);
    }

}

