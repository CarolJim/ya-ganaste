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

    public class Optional {
        /* Código País */
        @SerializedName("Cou")
        public String countryCode = "";

        /* Referencia Numérica */
        @SerializedName("Ref")
        public String referenceNumber = "";

        /* Código Divisa */
        @SerializedName("Cur")
        public String currencyCode = "";

        /* Código de Ciudad */
        @SerializedName("Mci")
        public String cityCode = "";

        /* Vigencia */
        @SerializedName("Exp")
        public String expiration = "";

        /* Código Postal */
        @SerializedName("Cop")
        public String postalCode = "";

        /* Identificador del dispositivo o serie */
        @SerializedName("Dev")
        public String deviceId = "";

        /* Monto */
        @SerializedName("Amo")
        public String amount = "";

        /* Tipo de Cuenta */
        @SerializedName("Tyc")
        public String typeOfAccount = "";

        /* RFU */
        @SerializedName("Rfu")
        public String rfu = "";

        /* Propina */
        @SerializedName("Tip")
        public String tip = "";

        /* Pago de la comisión */
        @SerializedName("Com")
        public String comission = "";

        /* Clave del Banco */
        @SerializedName("Ban")
        public String bankId = "";

        /* Fecha y Hora de creación */
        @SerializedName("Dat")
        public String dateCreation = "";

        /* Criptograma */
        @SerializedName("Cry")
        public String cryptogram = "";

        /* Cuenta de transaccion en app */
        @SerializedName("Atc")
        public String applicationCount = "";

        /* Issuer application data */
        @SerializedName("Iad")
        public String issuerData = "";

        /* Cadena encriptada */
        @SerializedName("Enc")
        public String encryptedString = "";

        /* Identificador de cobro */
        @SerializedName("Idc")
        public String billingIdentifier = "";

        /* Nombre del beneficiario */
        @SerializedName("Nam")
        public String beneficiaryName = "";

        /* Cuenta beneficiario */
        @SerializedName("Acc")
        public String beneficiaryAccount = "";

        /* Concepto */
        @SerializedName("Des")
        public String description = "";
    }

    public class Auxiliar {
        /* ID del QR */
        @SerializedName("Plt")
        public String plateQr = "";
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
}
