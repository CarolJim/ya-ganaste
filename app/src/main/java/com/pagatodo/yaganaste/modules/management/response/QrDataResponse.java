package com.pagatodo.yaganaste.modules.management.response;

import java.io.Serializable;

public class QrDataResponse implements Serializable {

    private String name;
    private OnlyQrResponse qr;

    public QrDataResponse(String name, OnlyQrResponse qr) {
        this.name = name;
        this.qr = qr;
    }

    public QrDataResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OnlyQrResponse getQr() {
        return qr;
    }

    public void setQr(OnlyQrResponse qr) {
        this.qr = qr;
    }
}
