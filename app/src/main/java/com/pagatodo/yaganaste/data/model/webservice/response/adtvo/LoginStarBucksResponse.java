package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.google.gson.annotations.SerializedName;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponseStarbucks;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Rewards;

import java.util.List;

/**
 * Created by asandovals on 20/04/2018.
 */

public class LoginStarBucksResponse extends GenericResponseStarbucks {


    @SerializedName("beneficiosRewards")
    private List<Rewards> beneficiosRewards;
    @SerializedName("datosMiembro")
    private DatosMiembro datosMiembro;

    @SerializedName("id_Miembro")
    private String id_Miembro;

    @SerializedName("infoRewards")
    private InfoRewardsStarbucks infoRewardsStarbucks;

    @SerializedName("numeroMiembro")
    private String numeroMiembro;

    @SerializedName("tarjetas")
    private List<Tarjetas> tarjetas;

    public void setTarjetas(List<Tarjetas> tarjetas) {
        this.tarjetas = tarjetas;
    }

    @SerializedName("tokenSeguridad")
    private String tokenSeguridad;


    public List<Rewards> getBeneficiosRewards() {
        return beneficiosRewards;
    }

    public void setBeneficiosRewards(List<Rewards> beneficiosRewards) {
        this.beneficiosRewards = beneficiosRewards;
    }

    public DatosMiembro getDatosMiembro() {
        return datosMiembro;
    }

    public void setDatosMiembro(DatosMiembro datosMiembro) {
        this.datosMiembro = datosMiembro;
    }

    public String getId_Miembro() {
        return id_Miembro;
    }

    public void setId_Miembro(String id_Miembro) {
        this.id_Miembro = id_Miembro;
    }

    public InfoRewardsStarbucks getInfoRewardsStarbucks() {
        return infoRewardsStarbucks;
    }

    public void setInfoRewardsStarbucks(InfoRewardsStarbucks infoRewardsStarbucks) {
        this.infoRewardsStarbucks = infoRewardsStarbucks;
    }

    public String getNumeroMiembro() {
        return numeroMiembro;
    }

    public void setNumeroMiembro(String numeroMiembro) {
        this.numeroMiembro = numeroMiembro;
    }

    public String getTokenSeguridad() {
        return tokenSeguridad;
    }

    public void setTokenSeguridad(String tokenSeguridad) {
        this.tokenSeguridad = tokenSeguridad;
    }
}
