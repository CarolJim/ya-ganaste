package com.pagatodo.yaganaste.modules.management.response;

import com.pagatodo.yaganaste.modules.data.webservices.PlateResponse;

public class QrValidateResponse extends PlateResponse {
    private QrDataSimpleResponse data;

    public QrValidateResponse(QrDataSimpleResponse data) {
        this.data = data;
    }

    public QrDataSimpleResponse getData() {
        return data;
    }

    public void setData(QrDataSimpleResponse data) {
        this.data = data;
    }
}
