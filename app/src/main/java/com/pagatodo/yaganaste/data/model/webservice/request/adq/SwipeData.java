package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class SwipeData implements Serializable {

    private static final long serialVersionUID = -7628328369292811451L;
    public String cvv;
    private String encCardData = "";
    private String ksn = "";
    private String track1Length = "";
    private String track2Length = "";
    private String track3Length = "";

    public SwipeData() {
    }

    public String getEncCardData() {
        return encCardData;
    }

    public void setEncCardData(String encCardData) {
        this.encCardData = encCardData;
    }

    public String getKsn() {
        return ksn;
    }

    public void setKsn(String ksn) {
        this.ksn = ksn;
    }

    public String getTrack1Length() {
        return track1Length;
    }

    public void setTrack1Length(String track1Length) {
        this.track1Length = track1Length;
    }

    public String getTrack2Length() {
        return track2Length;
    }

    public void setTrack2Length(String track2Length) {
        this.track2Length = track2Length;
    }

    public String getTrack3Length() {
        return track3Length;
    }

    public void setTrack3Length(String track3Length) {
        this.track3Length = track3Length;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
