package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 09/02/17.
 *
 * Interfaz que proporciona los métodos básicos para el procesamiento y validación de un formulario simple.
 */


public interface ValidationForms<Error> {

    /*Método para establecer reglas especiales de validación, RFC, CURP, etc*/
    public void setValidationRules();

    /*Método para disparar la validación de un formulario*/
    public void validateForm();

    /*Método para mostrar errores en la validación*/
    public void showValidationError(Error error);

    /*Método para disparar la acción posterior a la validación satisfactoria del formulario*/
    public void onValidationSuccess();

    /*Método para obtener la información introducida en un formulario*/
    public void getDataForm();
}
