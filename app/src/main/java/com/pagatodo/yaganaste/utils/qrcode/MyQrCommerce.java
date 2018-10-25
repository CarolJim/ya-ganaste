package com.pagatodo.yaganaste.utils.qrcode;

public class MyQrCommerce {

    String reference, commerce, codevisivility;

    public MyQrCommerce(String reference, String commerce, String codevisivility) {
        this.reference = reference;
        this.commerce = commerce;
        this.codevisivility = codevisivility;
    }

    public String getCodevisivility() {
        return codevisivility;
    }

    public void setCodevisivility(String codevisivility) {
        this.codevisivility = codevisivility;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCommerce() {
        return commerce;
    }

    public void setCommerce(String commerce) {
        this.commerce = commerce;
    }
}
