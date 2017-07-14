package com.pagatodo.yaganaste.data.model.webservice.request.adq;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jvazquez on 27/10/2016.
 */

public class SignatureStroke implements Serializable {

    public List<Point> points;
    public transient String signatureWidth = "";
    public transient String signatureHeight = "";

    public SignatureStroke() {
        points = new ArrayList<Point>();
    }

    public void setPoint(Float x, Float y) {
        points.add(new Point(x, y));
        return;
    }

    public void setWithAndHeight(String with, String height) {
        signatureWidth = with;
        signatureHeight = height;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public String getSignatureWidth() {
        return signatureWidth;
    }

    public void setSignatureWidth(String signatureWidth) {
        this.signatureWidth = signatureWidth;
    }

    public String getSignatureHeight() {
        return signatureHeight;
    }

    public void setSignatureHeight(String signatureHeight) {
        this.signatureHeight = signatureHeight;
    }
}