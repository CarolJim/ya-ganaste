package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by asandovals on 26/04/2018.
 */

public class DatosPersonaRegistroStarbucks  implements Serializable {

    String CP="";
    String CalleYNumero="";
    String Colonia="";
    String Correo="";
    String FechaNacimiento="";
    String IdColoniaSepomex="";
    int IdGenero=0;
    String Nombre="";
    String PrimerApellido="";
    String SegundoApellido="";

    public DatosPersonaRegistroStarbucks() {
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getCalleYNumero() {
        return CalleYNumero;
    }

    public void setCalleYNumero(String calleYNumero) {
        CalleYNumero = calleYNumero;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public String getIdColoniaSepomex() {
        return IdColoniaSepomex;
    }

    public void setIdColoniaSepomex(String idColoniaSepomex) {
        IdColoniaSepomex = idColoniaSepomex;
    }

    public int getIdGenero() {
        return IdGenero;
    }

    public void setIdGenero(int idGenero) {
        IdGenero = idGenero;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrimerApellido() {
        return PrimerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        PrimerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return SegundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        SegundoApellido = segundoApellido;
    }
}
