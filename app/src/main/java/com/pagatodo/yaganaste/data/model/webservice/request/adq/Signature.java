package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jvazquez on 27/10/2016.
 */

public class Signature implements Serializable {

    private List<SignatureStroke> signatureStrokes;
    private transient SignatureStroke curentSignStroke;

    public Signature() {
        signatureStrokes = new ArrayList<SignatureStroke>();
    }


    public void newStroke() {
        curentSignStroke = new SignatureStroke();
        signatureStrokes.add(curentSignStroke);
    }


    public void setPoint(Float x, Float y) {
        curentSignStroke.setPoint(x, y);
    }

    public void setDimension(String width, String height) {
        curentSignStroke.signatureHeight = height;
        curentSignStroke.signatureWidth = width;
    }

    public int getNumberStrokes() {
        return signatureStrokes.size();
    }

    public List<SignatureStroke> getSignStrokes() {
        return signatureStrokes;
    }

    public void setSignStrokes(List<SignatureStroke> signStrokes) {
        this.signatureStrokes = signStrokes;
    }

    public List<SignatureStroke> getSignatureStrokes() {
        return signatureStrokes;
    }

    public void setSignatureStrokes(List<SignatureStroke> signatureStrokes) {
        this.signatureStrokes = signatureStrokes;
    }

    public SignatureStroke getCurentSignStroke() {
        return curentSignStroke;
    }

    public void setCurentSignStroke(SignatureStroke curentSignStroke) {
        this.curentSignStroke = curentSignStroke;
    }
}