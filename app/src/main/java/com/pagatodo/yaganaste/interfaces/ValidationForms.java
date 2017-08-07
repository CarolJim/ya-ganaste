package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 09/02/17.
 * <p>
 * Interfaz que proporciona los métodos básicos para el procesamiento y validación de un formulario simple.
 */


public interface ValidationForms<Error> {

    /*Método para establecer reglas especiales de validación, RFC, CURP, etc*/
    void setValidationRules();

    /*Método para disparar la validación de un formulario*/
    void validateForm();

    /*Método para mostrar errores en la validación*/
    void showValidationError(int id, Error error);

    void hideValidationError(int id);

    /*Método para disparar la acción posterior a la validación satisfactoria del formulario*/
    void onValidationSuccess();

    /*Método para obtener la información introducida en un formulario*/
    void getDataForm();
}
