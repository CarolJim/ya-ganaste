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


    public static class Builder {
        private ErrorObject errorObject;
        public Builder() {
            errorObject = new ErrorObject();
            errorObject.hasConfirm = true;
        }


        public Builder setHasCancel(boolean hasCancel) {
            errorObject.hasCancel = hasCancel;
            return this;
        }

        public Builder setActions (DialogDoubleActions actions) {
            errorObject.errorActions = actions;
            return this;
        }

        public Builder setMessage(String message) {
            errorObject.errorMessage = message;
            return this;
        }

        public Builder setWebService (WebService webService) {
            errorObject.webService = webService;
            return this;
        }

        public ErrorObject build() {
            return this.errorObject;
        }
    }
}
