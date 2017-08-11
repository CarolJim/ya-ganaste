package com.pagatodo.yaganaste.data.dto;

import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

/**
 * @author Juan Guerra on 10/05/2017.
 */

public class ErrorObject {

    private boolean hasConfirm;
    private boolean hasCancel;
    private DialogDoubleActions errorActions;
    private String errorMessage;
    private WebService webService;

    public ErrorObject() {

    }

    public ErrorObject(String errorMessage,
                       WebService webService) {
        this.errorMessage = errorMessage;
        this.webService = webService;
    }

    public void setHasConfirm(boolean hasConfirm) {
        this.hasConfirm = hasConfirm;
    }

    public void setHasCancel(boolean hasCancel) {
        this.hasCancel = hasCancel;
    }

    public boolean hasConfirm() {
        return hasConfirm;
    }

    public boolean hasCancel() {
        return hasCancel;
    }

    public DialogDoubleActions getErrorActions() {
        return errorActions;
    }

    public void setErrorActions(DialogDoubleActions errorActions) {
        this.errorActions = errorActions;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public WebService getWebService() {
        return webService;
    }
}
