package com.pagatodo.yaganaste.ui_wallet.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Omar on 15/01/2018.
 */

public class DtoRequestPayment implements Parcelable {

    private Long idFavorite = 0L;
    private float amount;
    private String name, reference, colorBank, urlImage, message, headMessage, footMessage;

    public DtoRequestPayment(long idFavorite, String name, String reference, String colorBank, String urlImage) {
        this.idFavorite = idFavorite;
        this.name = name;
        this.reference = reference;
        this.colorBank = colorBank;
        this.urlImage = urlImage;
    }

    public long getIdFavorite() {
        return idFavorite;
    }

    public void setIdFavorite(long idFavorite) {
        this.idFavorite = idFavorite;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getColorBank() {
        return colorBank;
    }

    public void setColorBank(String colorBank) {
        this.colorBank = colorBank;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHeadMessage() {
        return headMessage;
    }

    public void setHeadMessage(String headMessage) {
        this.headMessage = headMessage;
    }

    public String getFootMessage() {
        return footMessage;
    }

    public void setFootMessage(String footMessage) {
        this.footMessage = footMessage;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;
        if (v instanceof DtoRequestPayment) {
            DtoRequestPayment ptr = (DtoRequestPayment) v;
            retVal = ptr.idFavorite.longValue() == this.idFavorite;
        }
        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.idFavorite != null ? this.idFavorite.hashCode() : 0);
        return hash;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.idFavorite);
        dest.writeFloat(this.amount);
        dest.writeString(this.name);
        dest.writeString(this.reference);
        dest.writeString(this.colorBank);
        dest.writeString(this.urlImage);
    }

    protected DtoRequestPayment(Parcel in) {
        this.idFavorite = (Long) in.readValue(Long.class.getClassLoader());
        this.amount = in.readFloat();
        this.name = in.readString();
        this.reference = in.readString();
        this.colorBank = in.readString();
        this.urlImage = in.readString();
    }

    public static final Parcelable.Creator<DtoRequestPayment> CREATOR = new Parcelable.Creator<DtoRequestPayment>() {
        @Override
        public DtoRequestPayment createFromParcel(Parcel source) {
            return new DtoRequestPayment(source);
        }

        @Override
        public DtoRequestPayment[] newArray(int size) {
            return new DtoRequestPayment[size];
        }
    };
}
