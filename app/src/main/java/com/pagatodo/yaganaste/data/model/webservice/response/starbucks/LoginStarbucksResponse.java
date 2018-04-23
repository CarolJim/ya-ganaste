package com.pagatodo.yaganaste.data.model.webservice.response.starbucks;

import com.google.gson.annotations.SerializedName;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DatosMiembro;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.Tarjetas;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponseStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.InfoRewardsStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.RespuestaStarbucks;
import com.pagatodo.yaganaste.data.room_db.entities.Rewards;

import java.util.List;

/**
 * Created by asandovals on 20/04/2018.
 */

public class LoginStarbucksResponse  {


    @SerializedName("beneficiosRewards")
    private List<Rewards> beneficiosRewards;
    @SerializedName("datosMiembro")
    private DatosMiembro datosMiembro;

    @SerializedName("id_Miembro")
    private int id_Miembro;

    @SerializedName("infoRewards")
    private InfoRewardsStarbucks infoRewardsStarbucks;

    @SerializedName("numeroMiembro")
    private String numeroMiembro;

    @SerializedName("respuesta")
    private RespuestaStarbucks respuestaStarbucks;

    @SerializedName("tarjetas")
    private List<Tarjetas> tarjetas;

    public void setTarjetas(List<Tarjetas> tarjetas) {
        this.tarjetas = tarjetas;
    }

    @SerializedName("tokenSeguridad")
    private String tokenSeguridad;

    public RespuestaStarbucks getData() {
        return respuestaStarbucks;
    }
    public void setData(RespuestaStarbucks data) {
        respuestaStarbucks = data;
    }


    public int getId_Miembro() {
        return id_Miembro;
    }

    public void setId_Miembro(int id_Miembro) {
        this.id_Miembro = id_Miembro;
    }

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
