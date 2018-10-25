package com.pagatodo.yaganaste.utils.qrcode;

public class MyQr {

    String userName, phoneNumber, cardNumber, clabe;

    public MyQr(String username, String phoneNumber, String cardNumber, String clabe) {
        this.userName = username;
        this.phoneNumber = phoneNumber;
        this.cardNumber = cardNumber;
        this.clabe = clabe;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getClabe() {
        return clabe;
    }

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }
}
