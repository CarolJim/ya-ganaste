package com.pagatodo.yaganaste.modules.qr;

public class MyQrData {
    String nameQR, numberQR;
    Integer QR;

    public MyQrData(String nameQR, String numberQR, Integer QR) {
        this.nameQR = nameQR;
        this.numberQR = numberQR;
        this.QR = QR;
    }

    public String getNameQR() {
        return nameQR;
    }

    public void setNameQR(String nameQR) {
        this.nameQR = nameQR;
    }

    public String getNumberQR() {
        return numberQR;
    }

    public void setNumberQR(String numberQR) {
        this.numberQR = numberQR;
    }

    public Integer getQR() {
        return QR;
    }

    public void setQR(Integer QR) {
        this.QR = QR;
    }
}
