package com.pagatodo.yaganaste.ui_wallet.dto;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DtoSubGiro {

    public  int ID_Subgiro;

   public String Valor;

    public DtoSubGiro() {
    }

    public DtoSubGiro(int ID_Subgiro, String valor) {
        this.ID_Subgiro = ID_Subgiro;
        Valor = valor;
    }
}
