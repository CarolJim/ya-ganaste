package com.pagatodo.yaganaste.ui_wallet.dto;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class DtoGiro {

    public  int ID_Giro;
   public String Valor;
   public ArrayList<DtoSubGiro> Subgiros;
    public DtoGiro() {
    }

    public DtoGiro(int ID_Giro, String valor, ArrayList<DtoSubGiro> subGiros) {
        this.ID_Giro = ID_Giro;
        Valor = valor;
        this.Subgiros = subGiros;
    }
}
