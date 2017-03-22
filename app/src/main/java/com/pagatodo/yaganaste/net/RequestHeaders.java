package com.pagatodo.yaganaste.net;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Gorro
 */
public class RequestHeaders {

    public static String IdOperacion = "IdOperacion";
    public static String IdComponente = "IdComponente";
    public static String NombreUsuario = "NombreUsuario";
    public static String IdDispositivo = "IdDispositivo";
    public static String TokenAutenticacion = "TokenAutenticacion";
    public static String TokenDispositivo = "TokenDispositivo";
    public static String TokenSesion = "TokenSesion";
    public static String IdCuenta = "IdCuenta";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;


    public static void initHeaders(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences("headers", Context.MODE_PRIVATE);
    }

    public static String getOperation() {
        return sharedPreferences.getString(IdOperacion, "");
    }

    public static void setOperation(String operation) {
        editor = sharedPreferences.edit();
        editor.putString(IdOperacion, operation);
        editor.apply();
    }

    public static String getComponent() {
        return sharedPreferences.getString(IdComponente, "");
    }

    public static void setComponent(String component) {
        editor = sharedPreferences.edit();
        editor.putString(IdComponente, component);
        editor.apply();
    }

    public static String getUsername() {
        return sharedPreferences.getString(NombreUsuario, "");
    }

    public static void setUsername(String username) {
        editor = sharedPreferences.edit();
        editor.putString(NombreUsuario, username);
        editor.apply();
    }

    public static String getDevice() {
        return sharedPreferences.getString(IdDispositivo, "");
    }

    public static void setDevice(String device) {
        editor = sharedPreferences.edit();
        editor.putString(IdDispositivo, device);
        editor.apply();
    }

    public static String getTokenauth() {
        return sharedPreferences.getString(TokenAutenticacion, "");
    }

    public static void setTokenauth(String tokenauth) {
        editor = sharedPreferences.edit();
        editor.putString(TokenAutenticacion, tokenauth);
        editor.apply();
    }

    public static String getTokendevice() {
        return sharedPreferences.getString(TokenDispositivo, "");
    }

    public static void setTokendevice(String tokendevice) {
        editor = sharedPreferences.edit();
        editor.putString(TokenDispositivo, tokendevice);
        editor.apply();
    }

    public static String getTokensesion() {
        return sharedPreferences.getString(TokenSesion, "");
    }

    public static void setTokensesion(String tokensesion) {
        editor = sharedPreferences.edit();
        editor.putString(TokenSesion, tokensesion);
        editor.apply();
    }
}
