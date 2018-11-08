package com.pagatodo.yaganaste.utils.qrcode;

import com.google.gson.annotations.SerializedName;

public class InterbankQr {

    /* Tipo y subtipo de Operación */
    @SerializedName("Typ")
    private String type = "";

    /* Versión */
    @SerializedName("Ver")
    private String version = "";

    /* Campos Opcionales */
    @SerializedName("Opt")
    private Optional optionalData;

    /* Campos Auxiliares */
    @SerializedName("Aux")
    private Auxiliar auxiliarData;

    public InterbankQr() {
        optionalData = new Optional();
        auxiliarData = new Auxiliar();
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public Optional getOptionalData() {
        return optionalData;
    }

    public Auxiliar getAuxiliarData() {
        return auxiliarData;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public class Optional {
        /* Código País */
        @SerializedName("COU")
        public String countryCode = "";

        /* Referencia Numérica */
        @SerializedName("REF")
        public String referenceNumber = "";

        /* Código Divisa */
        @SerializedName("CUR")
        public String currencyCode = "";

        /* Código de Ciudad */
        @SerializedName("MCI")
        public String cityCode = "";

        /* Vigencia */
        @SerializedName("EXP")
        public String expiration = "";

        /* Código Postal */
        @SerializedName("COP")
        public String postalCode = "";

        /* Identificador del dispositivo o serie */
        @SerializedName("DEV")
        public String deviceId = "";

        /* Monto */
        @SerializedName("AMO")
        public String amount = "";

        /* Tipo de Cuenta */
        @SerializedName("TYC")
        public String typeOfAccount = "";

        /* RFU */
        @SerializedName("RFU")
        public String rfu = "";

        /* Propina */
        @SerializedName("TIP")
        public String tip = "";

        /* Pago de la comisión */
        @SerializedName("COM")
        public String comission = "";

        /* Clave del Banco */
        @SerializedName("BAN")
        public String bankId = "";

        /* Fecha y Hora de creación */
        @SerializedName("DAT")
        public String dateCreation = "";

        /* Criptograma */
        @SerializedName("CRY")
        public String cryptogram = "";

        /* Cuenta de transaccion en app */
        @SerializedName("ATC")
        public String applicationCount = "";

        /* Issuer application data */
        @SerializedName("IAD")
        public String issuerData = "";

        /* Cadena encriptada */
        @SerializedName("ENC")
        public String encryptedString = "";

        /* Identificador de cobro */
        @SerializedName("IDC")
        public String billingIdentifier = "";

        /* Nombre del beneficiario */
        @SerializedName("NAM")
        public String beneficiaryName = "";

        /* Cuenta beneficiario */
        @SerializedName("ACC")
        public String beneficiaryAccount = "";

        /* Concepto */
        @SerializedName("DES")
        public String description = "";
    }

    public class Auxiliar {

    }
}
