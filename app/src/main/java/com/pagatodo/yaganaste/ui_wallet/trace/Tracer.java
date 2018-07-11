package com.pagatodo.yaganaste.ui_wallet.trace;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;

public class Tracer extends WSTracer {

    private final static String TAG_LOG = "WSTRACER";
    private String WSName;
    private String email;
    private Long startTime;
    private Long finalTime;
    private String statusHttp;
    private String statusWS; //Codigo de respuesta
    private String contecction;
    private Long timeTaken;


    public Tracer(String WSNama, String email) {
        this.WSName = WSNama;
        this.email = email;
    }

    public String getWSNama() {
        return WSName;
    }

    public void setWSNama(String WSNama) {
        this.WSName = WSNama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(Long finalTime) {
        this.finalTime = finalTime;
    }

    public String getStatusHttp() {
        return statusHttp;
    }

    public void setStatusHttp(String statusHttp) {
        this.statusHttp = statusHttp;
    }

    public String getStatusWS() {
        return statusWS;
    }

    public void setStatusWS(String statusWS) {
        this.statusWS = statusWS;
    }

    public String getContecction() {
        return contecction;
    }

    public void setContecction(String contecction) {
        this.contecction = contecction;
    }

    public Long getTiemTaken() {
        return timeTaken;
    }

    public void setTiemTaken(Long tiemTaken) {
        this.timeTaken = tiemTaken;
    }

    @Override
    public void Start() {
        this.startTime = System.nanoTime();
    }

    @Override
    public void End() {
        this.finalTime = System.nanoTime();
    }

    @Override
    public void getStatus(DataSourceResult result) {
        GenericResponse genericResponse  = (GenericResponse) result.getData();
        if (!BuildConfig.DEBUG && (genericResponse.getCodigoRespuesta() != Recursos.CODE_OK))  {
            Log.d(TAG_LOG, this.WSName);
            Log.d(TAG_LOG, this.email);
            Log.d(TAG_LOG, "" + this.startTime);
            Log.d(TAG_LOG, "" + this.finalTime);
            //Log.d(TAG_LOG,this.statusHttp);
            Log.d(TAG_LOG, this.statusWS);
            Log.d(TAG_LOG, Utils.getTypeConnection());
            this.timeTaken = this.startTime - this.finalTime;
            Log.d(TAG_LOG, "" + TimeUnit.SECONDS.convert(this.timeTaken, TimeUnit.NANOSECONDS));
        }


    }




}
