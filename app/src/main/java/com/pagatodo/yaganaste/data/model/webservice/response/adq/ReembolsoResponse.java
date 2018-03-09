package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import java.io.Serializable;

/**
 * Created by icruz on 08/03/2018.
 */

public class ReembolsoResponse implements Serializable {

    private String id;
    private String message;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
