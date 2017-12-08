package com.pagatodo.yaganaste.interfaces.enums;

/**
 * Created by Jordan on 01/08/2017.
 */

public enum TipoTransaccionPCODE {
    ERROR("Error", 0),
    RECARGA("Recarga", 1),
    PAGO_DE_SERVICIO("Pago de Servicio", 2),
    TRASPASO_MISMO_BANCO_CARGO("Traspaso Mismo Banco [Cargo]", 3),
    SPEI_CARGO("SPEI [Cargo]", 4),
    TRASPASO_MISMO_BANCO_ABONO("Traspaso Mismo Banco [Abono] - Por Tarjeta", 5),
    SPEI_ABONO("SPEI [Abono]", 6),
    SIETE("", 7),
    COMPRA("Compra", 8),
    RETIRO_DE_DINERO_ATM("Retiro de Dinero", 9),
    CONSULTA_ATM("Consulta ATM", 10),
    CASH_BACK("Cash Back", 11),
    COMISION("Comisión", 12),
    COBRO_CON_TARJETA_DISPERSION_ADQ("Cobro con Tarjeta", 13),
    REEMBOLSO_ADQUIRIENTE("Reembolso Adquiriente", 14),
    DEVOLUCION("Devolucion", 14);

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
