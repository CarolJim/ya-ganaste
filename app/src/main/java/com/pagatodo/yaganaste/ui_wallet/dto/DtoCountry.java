package com.pagatodo.yaganaste.ui_wallet.dto;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class DtoCountry {

    public String ID_Pais,IdPaisFirebase,Nombre,Valor;
    public boolean Valido;

    public DtoCountry() {
    }

    public DtoCountry(String ID_Pais, String nombre, boolean valido) {
        this.ID_Pais = ID_Pais;
        Nombre = nombre;
        Valido = valido;
    }
}
