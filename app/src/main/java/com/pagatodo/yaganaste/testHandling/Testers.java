package com.pagatodo.yaganaste.testHandling;

import com.pagatodo.yaganaste.testHandling.DataTest.LoginData;

public class Testers {
    //Pantalla Registro Correo

    public final static boolean TEST_ON = true;

    static void registerEmail(){

    }

    public static LoginData loginVin(){
        String email = "ismaTest300@yaganaste.com";
        String hide = "123456";
        LoginData loginData = new LoginData(email,hide);
        return loginData;
    }
}
