package com.pagatodo.yaganaste.modules.management.response;

import java.io.Serializable;

public class CAuxResponse implements Serializable {

    private String Pl;

    public CAuxResponse(String pl) {
        Pl = pl;
    }

    public CAuxResponse() {
    }

    public String getPl() {
        return Pl;
    }

    public void setPl(String pl) {
        Pl = pl;
    }
}
