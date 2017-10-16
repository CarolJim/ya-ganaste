package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CuestionarioEntity;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_ERES_MEXICANO_NATURALIZADO;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_FAMILIAR;

/**
 * Created by flima on 27/03/2017.
 */

public class RegisterAgent {

    private static RegisterAgent registerUser;
    //Dato de usuario
    private int tipoAgente;
    private String nombre = "";
    private String telefono = "";
    private String razonSocial = "";
    private Giros giro;
    private SubGiro subGiros;
    private String latitud = "";
    private String longitud = "";
    private List<CuestionarioEntity> cuestionario;
    //Datos Domicilio Actual
    private String calle = "";
    private String numExterior = "";
    private String numInterior = "";
    private String codigoPostal = "";
    private String estadoDomicilio = "";
    private String colonia = "";
    private String idColonia = "";
    private String idEstado = "";

    private RegisterAgent() {
        cuestionario = new ArrayList<>();
    }

    public static synchronized RegisterAgent getInstance() {

        if (registerUser == null)
            registerUser = new RegisterAgent();

        return registerUser;

    }

    public static RegisterAgent getRegisterUser() {
        return registerUser;
    }

    public static void setRegisterUser(RegisterAgent registerUser) {
        RegisterAgent.registerUser = registerUser;
    }

    public static void resetRegisterAgent() {
        registerUser = null;
    }

    public static void resetBussinessAddress() {
        if (registerUser != null) {
            registerUser.calle = "";
            registerUser.numExterior = "";
            registerUser.numInterior = "";
            registerUser.codigoPostal = "";
            registerUser.estadoDomicilio = "";
            registerUser.colonia = "";
            registerUser.idColonia = "";
            registerUser.idEstado = "";
        }

    }

    public void resetAditionalInformation() {
        if (this.cuestionario.size() > 0) {
            for (CuestionarioEntity cuestionario : this.cuestionario) {
                if (cuestionario.getPreguntaId() == PREGUNTA_FAMILIAR) {
                    this.cuestionario.remove(cuestionario);
                } else if (cuestionario.getPreguntaId() == PREGUNTA_ERES_MEXICANO_NATURALIZADO) {
                    this.cuestionario.remove(cuestionario);
                }
            }
        }
    }

    public int getTipoAgente() {
        return tipoAgente;
    }

    public void setTipoAgente(int tipoAgente) {
        this.tipoAgente = tipoAgente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public List<CuestionarioEntity> getCuestionario() {
        return cuestionario;
    }

    public void setCuestionario(List<CuestionarioEntity> cuestionario) {
        this.cuestionario = cuestionario;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumExterior() {
        return numExterior;
    }

    public void setNumExterior(String numExterior) {
        this.numExterior = numExterior;
    }

    public String getNumInterior() {
        return numInterior;
    }

    public void setNumInterior(String numInterior) {
        this.numInterior = numInterior;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getEstadoDomicilio() {
        return estadoDomicilio;
    }

    public void setEstadoDomicilio(String estadoDomicilio) {
        this.estadoDomicilio = estadoDomicilio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getIdColonia() {
        return idColonia;
    }

    public void setIdColonia(String idColonia) {
        this.idColonia = idColonia;
    }

    public Giros getGiro() {
        return giro;
    }

    public void setGiro(Giros giro) {
        this.giro = giro;
    }

    public SubGiro getSubGiros() {
        return subGiros;
    }

    public void setSubGiros(SubGiro subGiros) {
        this.subGiros = subGiros;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }
}
