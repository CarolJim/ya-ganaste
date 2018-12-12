package com.pagatodo.yaganaste.modules.data;

import com.pagatodo.yaganaste.data.model.QRs;

import java.io.Serializable;

public class QrItem implements Serializable {
    private QRs qr;
    private int resImage;

    public QrItem(QRs qr, int resImage) {
        this.qr = qr;
        this.resImage = resImage;
    }

    public QRs getQr() {
        return qr;
    }

    public void setQr(QRs qr) {
        this.qr = qr;
    }

    public int getResImage() {
        return resImage;
    }

    public void setResImage(int resImage) {
        this.resImage = resImage;
    }
}
