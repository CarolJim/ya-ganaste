package com.pagatodo.yaganaste.data.model.webservice.request.adq;

/**
 * Created by jvazquez on 27/10/2016.
 */

public class SignatureData {
    public Signature signature;
    public String signatureWidth;
    public String signatureHeight;
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
}