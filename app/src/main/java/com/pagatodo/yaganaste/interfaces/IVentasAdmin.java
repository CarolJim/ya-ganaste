package com.pagatodo.yaganaste.interfaces;



import java.util.List;

public interface IVentasAdmin extends INavigationView {

    void succedGetResumenDia(String mensaje);

    void failGetResumenDia(String mensaje);

    void succedGetSaldo(String mensaje);

    void failGetSaldo(String mensaje);


}
