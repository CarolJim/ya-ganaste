package com.pagatodo.yaganaste.ui_wallet.dto;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class DtoGioElement {
    public String ID_Giro,Valor;
    public ArrayList<DtoSubGiro> Subgiros;
    public DtoGioElement() {
    }

    public DtoGioElement(String ID_Giro, String valor, ArrayList<DtoSubGiro> subGiros) {
        this.ID_Giro = ID_Giro;
        Valor = valor;
        this.Subgiros = subGiros;
    }
}
