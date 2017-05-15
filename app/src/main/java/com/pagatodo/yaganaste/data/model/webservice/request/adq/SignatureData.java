package com.pagatodo.yaganaste.data.model.webservice.request.adq;

/**
 * Created by jvazquez on 27/10/2016.
 */

public class SignatureData {
    private Signature signature;
    private String signatureWidth;
    private String signatureHeight;
    public SignatureData(){
        signature = new Signature();
        setCurrentDims(signature);
    }
    private void setCurrentDims(Signature signature){
        if(signature.getSignatureStrokes() != null && signature.getSignatureStrokes().size() > 0 ){
            signatureWidth =  "";//Integer.valueOf(signature.signatureStrokes.get(0).signatureWidth);
            signatureHeight = "";//Integer.valueOf(signature.signatureStrokes.get(0).signatureHeight);
        }
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public void setSignatureHeight(String signatureHeight) {
        this.signatureHeight = signatureHeight;
    }

    public void setSignatureWidth(String signatureWidth) {
        this.signatureWidth = signatureWidth;
    }

    public Signature getSignature() {
        return signature;
    }

    public String getSignatureHeight() {
        return signatureHeight;
    }

    public String getSignatureWidth() {
        return signatureWidth;
    }
}