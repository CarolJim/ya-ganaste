package com.pagatodo.yaganaste.ui_wallet.trace;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import static com.pagatodo.yaganaste.utils.ForcedUpdateChecker.TRACE_SUCCESS_WS;

public class Tracer extends WSTracer {

    private final static String TAG_EVENT = "ws_tracer";
    public final static String SUCESS = "SUCESS";
    public final static String FAILED = "FAILED";

    //Tag pra el evento
    private final static String EMAIL = "email";
    private final static String WSNAME = "ws_name";
    private final static String START_TIME = "start_time";
    private final static String FINAL_TIME = "final_time";
    private final static String ST_REQUEST = "st_request";
    private final static String ST_WS = "st_ws";
    private final static String CONNECTION = "connection";
    private final static String DURATION = "duration";



    private String email;
    private String WSName;
    private Long startTime;
    private Long finalTime;
    private String statusRequest;
    private String statusWS; //Codigo de respuesta
    private String connection;
    private Long duration;


    public Tracer(String email, String WSName) {
        this.email = email;
        this.WSName = WSName;
        this.Start();
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

    public String getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(String statusRequest) {
        this.statusRequest = statusRequest;
    }

    public String getStatusWS() {
        return statusWS;
    }

    public void setStatusWS(String statusWS) {
        this.statusWS = statusWS;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS);
    }

    @Override
    public void Start() {
        this.setStartTime(System.nanoTime());
    }

    @Override
    public void End() {
        this.setFinalTime(System.nanoTime());
    }

    @SuppressLint("InvalidAnalyticsName")
    @Override
    public void getTracerSucess() {
        //if (App.getInstance().getPrefs().loadDataBoolean(TRACE_SUCCESS_WS, false)) {
            this.sendEvent();
        //}
    }

    @Override
    public void getTracerError() {
        //if (App.getInstance().getPrefs().loadDataBoolean(TRACE_SUCCESS_WS, false)) {
            this.sendEvent();
        //}
    }

    private void sendEvent(){
        Bundle params = new Bundle();
        params.putString(EMAIL,this.email);
        params.putString(WSNAME,this.WSName);
        params.putString(START_TIME,String.valueOf(TimeUnit.SECONDS.convert(this.startTime,TimeUnit.NANOSECONDS)));
        params.putString(FINAL_TIME,String.valueOf(TimeUnit.SECONDS.convert(this.finalTime,TimeUnit.NANOSECONDS)));
        params.putString(ST_REQUEST,this.statusRequest);
        params.putString(ST_WS,this.statusWS);
        params.putString(CONNECTION,this.connection);
        SimpleDateFormat dateFormat = new SimpleDateFormat("00:mm:ss:SSSSSSS");

        params.putString(DURATION,String.valueOf(this.duration));
        FirebaseAnalytics.getInstance(App.getContext()).logEvent(TAG_EVENT,params);
    }
}
