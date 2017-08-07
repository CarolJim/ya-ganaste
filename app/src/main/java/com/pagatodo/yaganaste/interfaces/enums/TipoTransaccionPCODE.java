package com.pagatodo.yaganaste.interfaces.enums;

/**
 * Created by Jordan on 01/08/2017.
 */

public enum TipoTransaccionPCODE {
    ERROR("Error", 0),
    RECARGA("Recarga", 1),
    PAGO_DE_SERVICIO("Pago de Servicio", 2),
    ENVIO_DE_DINERO_MISMO_BANCO("Envío de Dinero (Mismo Banco)", 3),
    ENVIO_DE_DINERO_OTRO_BANCO("Envío de Dinero (Otro Banco)", 4),
    RECEPCION_DE_DINERO_MISMO_BANCO("Recepción de Dinero (Mismo Banco)", 5),
    RECEPCION_DE_DINERO_OTRO_BANCO("Recepción de Dinero (Otro Banco)", 6),
    YA_GANASTE_PROMO_CODES("Ya Ganaste", 7),
    COMPRA("Compra", 8),
    RETIRO_DE_DINERO_ATM("Retiro de Dinero", 9),
    CONSULTA_ATM("Consulta ATM", 10),
    CASH_BACK("Cash Back", 11),
    COMISION("Comisión", 12),
    COBRO_CON_TARJETA_DISPERSION_ADQ("Cobro con Tarjeta", 13);

    private String name;
    private int id;

    TipoTransaccionPCODE(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static TipoTransaccionPCODE getTipoTransaccionById(int id){
        for(TipoTransaccionPCODE tipo : values()){
            if(tipo.getId() == id){
                return tipo;
            }
        }
        return ERROR;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
