package com.pagatodo.yaganaste.modules.management.response;

import com.pagatodo.yaganaste.modules.data.webservices.PlateResponse;

import java.util.ArrayList;

public class QrsResponse extends PlateResponse{

    private ArrayList<QrDataResponse> data;

    public QrsResponse(ArrayList<QrDataResponse> data) {
        this.data = data;
    }

    public QrsResponse() {
    }

    public ArrayList<QrDataResponse> getData() {
        return data;
    }

    public void setData(ArrayList<QrDataResponse> data) {
        this.data = data;
    }
}
