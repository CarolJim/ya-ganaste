package com.pagatodo.yaganaste.data.model.webservice.response.cupo;

import com.google.gson.annotations.SerializedName;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tato on 17/08/17.
 */

public class DataEstadoSolicitud  implements Serializable{


    private String CodigoColor;
    private String ComentarioRechazo;
    //private String DomicilioPersonal;
    private String EstatusPaso;
    private String FolioAfiliacion;
    private int IdEstadoCivil;
    private int IdEstatusPaso;
    private int IdPaso;
    private int LineaCredito;
    private String NumeroCliente;
    private int NumeroHijos;
    private String Paso;

    private Boolean TieneCreditoAuto;
    private Boolean TieneCreditoBancario;
    private Boolean TieneTarjetaCredito;


    private List<RefereciasResponse> Referencias;

    public DataEstadoSolicitud() {
        Referencias = new ArrayList<RefereciasResponse>();
    }

    public String getCodigoColor() {
        return CodigoColor;
    }

    public void setCodigoColor(String codigoColor) {
        CodigoColor = codigoColor;
    }

    public List<RefereciasResponse> getReferencias() {
        return Referencias;
    }

    public void setReferencias(List<RefereciasResponse> referencias) {
        Referencias = referencias;
    }

    public String getComentarioRechazo() {
        return ComentarioRechazo;
    }

    public void setComentarioRechazo(String comentarioRechazo) {
        ComentarioRechazo = comentarioRechazo;
    }

    /*
    public String getDomicilioPersonal() {
        return DomicilioPersonal;
    }

    public void setDomicilioPersonal(String domicilioPersonal) {
        DomicilioPersonal = domicilioPersonal;
    }
    */

    public String getEstatusPaso() {
        return EstatusPaso;
    }

    public void setEstatusPaso(String estatusPaso) {
        EstatusPaso = estatusPaso;
    }

    public String getFolioAfiliacion() {
        return FolioAfiliacion;
    }

    public void setFolioAfiliacion(String folioAfiliacion) {
        FolioAfiliacion = folioAfiliacion;
    }

    public int getIdEstadoCivil() {
        return IdEstadoCivil;
    }

    public void setIdEstadoCivil(int idEstadoCivil) {
        IdEstadoCivil = idEstadoCivil;
    }

    public int getIdEstatusPaso() {
        return IdEstatusPaso;
    }

    public void setIdEstatusPaso(int idEstatusPaso) {
        IdEstatusPaso = idEstatusPaso;
    }

    public int getIdPaso() {
        return IdPaso;
    }

    public void setIdPaso(int idPaso) {
        IdPaso = idPaso;
    }

    public int getLineaCredito() {
        return LineaCredito;
    }

    public void setLineaCredito(int lineaCredito) {
        LineaCredito = lineaCredito;
    }

    public String getNumeroCliente() {
        return NumeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        NumeroCliente = numeroCliente;
    }

    public int getNumeroHijos() {
        return NumeroHijos;
    }

    public void setNumeroHijos(int numeroHijos) {
        NumeroHijos = numeroHijos;
    }

    public String getPaso() {
        return Paso;
    }

    public void setPaso(String paso) {
        Paso = paso;
    }

    public Boolean getTieneCreditoAuto() {
        return TieneCreditoAuto;
    }

    public void setTieneCreditoAuto(Boolean tieneCreditoAuto) {
        TieneCreditoAuto = tieneCreditoAuto;
    }

    public Boolean getTieneCreditoBancario() {
        return TieneCreditoBancario;
    }

    public void setTieneCreditoBancario(Boolean tieneCreditoBancario) {
        TieneCreditoBancario = tieneCreditoBancario;
    }

    public Boolean getTieneTarjetaCredito() {
        return TieneTarjetaCredito;
    }

    public void setTieneTarjetaCredito(Boolean tieneTarjetaCredito) {
        TieneTarjetaCredito = tieneTarjetaCredito;
    }
}
