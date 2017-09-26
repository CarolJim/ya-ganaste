package com.pagatodo.yaganaste.utils.customviews.carousel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Francisco Manzo on 22/09/2017.
 */

public class CustomCarouselItem implements Parcelable{
    private int idComercio;
    private int idTipoComercio;
    private String nombreComercio;

    public CustomCarouselItem (int idComercio, int idTipoComercio, String nombreComercio){
        this.idComercio = idComercio;
        this.idTipoComercio = idTipoComercio;
        this.nombreComercio = nombreComercio;
    }

    protected CustomCarouselItem(Parcel in) {
        idComercio = in.readInt();
        idTipoComercio = in.readInt();
        nombreComercio = in.readString();
    }

    public static final Creator<CustomCarouselItem> CREATOR = new Creator<CustomCarouselItem>() {
        @Override
        public CustomCarouselItem createFromParcel(Parcel in) {
            return new CustomCarouselItem(in);
        }

        @Override
        public CustomCarouselItem[] newArray(int size) {
            return new CustomCarouselItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idComercio);
        dest.writeInt(idTipoComercio);
        dest.writeString(nombreComercio);
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public int getIdTipoComercio() {
        return idTipoComercio;
    }

    public void setIdTipoComercio(int idTipoComercio) {
        this.idTipoComercio = idTipoComercio;
    }

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }
}
