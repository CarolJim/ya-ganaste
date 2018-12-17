package com.pagatodo.yaganaste.modules.data;

import com.pagatodo.yaganaste.data.model.QRUser;

import java.io.Serializable;

public class QrItems implements Serializable {
    private QRUser qrUser;
    private int resImage;

    public QrItems(QRUser qrUser, int resImage) {
        this.qrUser = qrUser;
        this.resImage = resImage;
    }

    public QRUser getQrUser() {
        return qrUser;
    }

    public void setQrUser(QRUser qrUser) {
        this.qrUser = qrUser;
    }

    public int getResImage() {
        return resImage;
    }

    public void setResImage(int resImage) {
        this.resImage = resImage;
    }
}
