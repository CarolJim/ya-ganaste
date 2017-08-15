package com.pagatodo.yaganaste.freja;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.verisec.freja.mobile.core.exceptions.FmcCodeException;
import com.verisec.freja.mobile.core.exceptions.FmcInternalException;

/**
 * @author Juan Guerra on 03/04/2017.
 */

public enum  Errors {


    /*FmcInternalException – an unknown internal error has occurred
    IllegalStateException – a method has been called outside of the proper sequence
    FmcCodeException – a known error has occurred  (El error puede venir del siguiente catalogo)*/


    NO_ACTIVATION_CODE(-31, false, "Ocurrió un Error Con el Código de Activación"),
    NO_PIN_POLICY(-4, false, "Las Reglas Para Asignación de PIN Son Incorrectas"),
    BAD_CHANGE_POLICY(-6, false, "Las Reglas Para Cambio de PIN Son Incorrectas"),
    NO_PENDING_TRANSACTIONS(-7, false, "No Hay Transacciones Por Aprovar"),
    NO_OFFLINE_TOKEN(-8, false, "No Cuentas Con Generación de Token Offline"),

    INTERNAL(-1, true, "An unknown internal error has occurred "),
    ILLEGAL_STATE(-2, false, "A method has been called outside of the proper sequence"),
    E1(5002, true, "Unspecified exception occurred when trying to connect to MASS"),
    E2(5005, true, App.getInstance().getString(R.string.no_internet_access)),
    E3(5006, true, "Connection with MASS timed out since the server took too long to respond"),
    E4(5007, false, "Certificate validation failed"),
    E5(5008, true, "SSL connection cannot be established"),
    E6(500, false, "Internal server error – please contact Verisec support"),
    E7(600, true, "The REST message received has an invalid format and MASS is unable to parse it"),
    E8(1001, false, "The current version of Freja Mobile Core is out of date and no longer supported. The user should be prompted to update the application before proceeding"),
    E9(1002, false, "MASS has received a request related to an unknown token serial number"),
    E10(1004, true, "An irregularity occurred in the session between Freja Mobile Core and MASS"),
    E11(1005, true, "Session has expired"),
    E12(1020, false, "Online token has been locked due to too many failed authentication attempts. If this occurs, the token is no longer usable until a PIN reset is performed"),
    E13(1029, false, "None of the provided protocol versions are supported. This error is shown if mobile " +
            "application and MASS do not use the same protocol for communication. One or both components should be upgraded"),

    E14(1000, false, "MASS has received an activation code which does not exist in its database"),
    E15(1003, false, "MASS has received a provisioning request for both an online and an offline token. However, their respective serial numbers are not associated with the same activation code"),
    E16(1006, true, "Activation code has expired"),
    E17(1007, false, "A provisioning method has been called, but it cannot be applied because the provisioning process is currently in a state which does not allow it"),
    E18(1008, false, "MASS has received a request to confirm the completion of provisioning. However, provisioning has already been completed"),
    E19(2000, false, "Client code does not exist"),
    E20(1036, true, "(Notification) Failed to set mobile token info"),
    E21(1009, false, "(Transaction) Another transaction is already in progress"),
    E22(1010, false, "(Transaction) MASS has received a message inquiring about an ongoing transaction for " +
               "a given serial number, but the token in question does not currently have a transaction associated with it"),
    E23(1011, false, "(Transaction) Transaction has expired"),
    E24(1012, false, "(Transaction) A transaction-related method has been called, but it cannot be applied " +
               "because the transaction process is currently in a state which does not allow it"),
    E25(1013, true, "(Transaction) A transaction could not be confirmed"),
    E26(1015, true, "PIN change was initiated and failed"),

    E27(1022, false, "Reset PIN) There is no RPC or RPC expired."),
    E28(1023, false, "Reset PIN) Invalid RPC hash or value."),
    E29(1024, false, "Reset PIN) Remaining try attempts for given reset PIN code is 0."),
    E30(1025, true, "Reset PIN) Calling provisioning method getResetPinPinPolicy failed."),
    E31(1026, true, "Reset PIN) Calling provisioning method resetPin4RpcCode failed."),
    E32(1027, false, "Reset PIN) One or more parameters are missing."),
    E33(1028, false, "Reset PIN) getResetPinPinPolicy must be called first."),
    E34(1033, false, "Key renewal) Failed to change token key. All errors regarding changing token key can appear if token key renewal is enabled on MASS."),
    E35(1034, false, "Key renewal) Making this call is not allowed since key renewal is not required."),
    E36(1035, false, "Key renewal) Making this call is not allowed, key renewal is required."),
    E37(1042, false, "Invalid PIN. The most likely cause is that the PIN length does not match the PIN policy set on MASS."),
    UNEXPECTED(-100, true, "Ocurrió un Error, Por Favor Intenta Más Tarde");


    private int errorCode;
    private String message;
    private boolean allowReintent;

    Errors (int code, boolean allowReintent, String message) {
        this.errorCode = code;
        this.allowReintent = allowReintent;
        this.message = message;
    }

    public static Errors cast(Exception ex) {
        if (ex instanceof FmcInternalException) {
            return INTERNAL;
        } else if (ex instanceof IllegalStateException) {
            return ILLEGAL_STATE;
        } else if (ex instanceof FmcCodeException) {
            FmcCodeException fmcCodeException = (FmcCodeException) ex;
            for (Errors current : values()) {
                if (fmcCodeException.getCode() == current.errorCode) {
                    return current;
                }
            }
        }
        return UNEXPECTED;
    }

    public static Errors find(int code) {
        for (Errors current : values()) {
            if (current.errorCode == code) {
                return current;
            }
        }
        return null;
    }

    public String getMessage() {
        return message;
    }

    public boolean allowsReintent() {
        return this.allowReintent;
    }

/*
    public enum GENERIC {

        P1(5002, "Unspecified exception occurred when trying to connect to MASS"),
        P2(5005, "The user’s smartphone does not have Internet access, possibly due to Airplane mode being activated"),
        P3(5006, "Connection with MASS timed out since the server took too long to respond"),
        P4(5007, "Certificate validation failed"),
        P5(5008, "SSL connection cannot be established"),
        P6(500, "Internal server error – please contact Verisec support"),
        P7(600, "The REST message received has an invalid format and MASS is unable to parse it"),
        P8(1001, "The current version of Freja Mobile Core is out of date and no longer supported. The user should be prompted to update the application before proceeding"),
        P9(1002, "MASS has received a request related to an unknown token serial number"),
        P10(1004, "An irregularity occurred in the session between Freja Mobile Core and MASS"),
        P11(1005, "Session has expired"),
        P12(1020, "Online token has been locked due to too many failed authentication attempts. If this occurs, the token is no longer usable until a PIN reset is performed"),
        P13(1029, "None of the provided protocol versions are supported. This error is shown if mobile " +
                "application and MASS do not use the same protocol for communication. One or both components should be upgraded");

        private int errorCode;
        private String message;

        GENERIC (int code, String message) {
            this.errorCode = code;
            this.message = message;
        }

        public static GENERIC find(int code) {
            for (GENERIC current : values()) {
                if (current.errorCode == code) {
                    return current;
                }
            }
            return null;
        }
    }

    public enum PROVISIONING {
        P1(1000,"MASS has received an activation code which does not exist in its database"),
        P2(1003,"MASS has received a provisioning request for both an online and an offline token. However, their respective serial numbers are not associated with the same activation code"),
        P3(1006,"Activation code has expired"),
        P4(1007,"A provisioning method has been called, but it cannot be applied because the provisioning process is currently in a state which does not allow it"),
        P5(1008,"MASS has received a request to confirm the completion of provisioning. However, provisioning has already been completed"),
        P6(2000,"Client code does not exist"),
        P7(1036,"(Notification) Failed to set mobile token info");

        private int errorCode;
        private String message;

        PROVISIONING (int code, String message) {
            this.errorCode = code;
            this.message = message;
        }
    }


    public enum TRANSACTION {
        P1(1009,"(Transaction) Another transaction is already in progress"),
        P2(1010,"(Transaction) MASS has received a message inquiring about an ongoing transaction for " +
                "a given serial number, but the token in question does not currently have a transaction associated with it"),
        P3(1011,"(Transaction) Transaction has expired"),
        P4(1012,"(Transaction) A transaction-related method has been called, but it cannot be applied " +
                "because the transaction process is currently in a state which does not allow it"),
        P5(1013,"(Transaction) A transaction could not be confirmed"),
        P6(1015,"PIN change was initiated and failed");

        private int errorCode;
        private String message;

        TRANSACTION (int code, String message) {
            this.errorCode = code;
            this.message = message;
        }
    }

    public enum PIN {
        P1(1022,"Reset PIN) There is no RPC or RPC expired."),
        P2(1023,"Reset PIN) Invalid RPC hash or value."),
        P3(1024,"Reset PIN) Remaining try attempts for given reset PIN code is 0."),
        P4(1025,"Reset PIN) Calling provisioning method getResetPinPinPolicy failed."),
        P5(1026,"Reset PIN) Calling provisioning method resetPin4RpcCode failed."),
        P6(1027,"Reset PIN) One or more parameters are missing."),
        P7(1028,"Reset PIN) getResetPinPinPolicy must be called first."),
        P8(1033,"Key renewal) Failed to change token key. All errors regarding changing token key can appear if token key renewal is enabled on MASS."),
        P9(1034,"Key renewal) Making this call is not allowed since key renewal is not required."),
        P10(1035,"Key renewal) Making this call is not allowed, key renewal is required."),
        P11(1042,"Invalid PIN. The most likely cause is that the PIN length does not match the PIN policy set on MASS.");

        private int errorCode;
        private String message;

        PIN (int code, String message) {
            this.errorCode = code;
            this.message = message;
        }
    }
*/

}