package com.pagatodo.yaganaste.data.model;

public class Ventas {

  private static   Ventas ventas;

    private String  montoventas;

    private String  ticketp;

    private String  cobrosr;
    public static synchronized Ventas getInstance() {
        if (ventas == null)
            ventas = new Ventas();
        return ventas;
    }
    public Ventas() {
    }
    public static void resetventas() {
        ventas = null;
    }

    public Ventas(String montoventas, String ticketp, String cobrosr) {
        this.montoventas = montoventas;
        this.ticketp = ticketp;
        this.cobrosr = cobrosr;
    }

    public String getMontoventas() {
        return montoventas;
    }

    public void setMontoventas(String montoventas) {
        this.montoventas = montoventas;
    }

    public String getTicketp() {
        return ticketp;
    }

    public void setTicketp(String ticketp) {
        this.ticketp = ticketp;
    }

    public String getCobrosr() {
        return cobrosr;
    }

    public void setCobrosr(String cobrosr) {
        this.cobrosr = cobrosr;
    }
}
