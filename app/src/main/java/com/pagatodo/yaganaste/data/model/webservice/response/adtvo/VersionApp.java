package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

/**
 * Created by Omar on 07/02/2018.
 */

public class VersionApp {

    boolean ForcedUpdate;
    String Version;

    public boolean isForcedUpdate() {
        return ForcedUpdate;
    }

    public void setForcedUpdate(boolean forcedUpdate) {
        ForcedUpdate = forcedUpdate;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }
}
