package com.pagatodo.yaganaste.modules.management.request;

import java.io.Serializable;

public class UpdateTokenFirebase implements Serializable {

private  String app ;
private  String platform ;
private  String rgstrtnTkn ;


    public UpdateTokenFirebase(String app, String platform, String rgstrtnTkn) {
        this.app = app;
        this.platform = platform;
        this.rgstrtnTkn = rgstrtnTkn;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRgstrtnTkn() {
        return rgstrtnTkn;
    }

    public void setRgstrtnTkn(String rgstrtnTkn) {
        this.rgstrtnTkn = rgstrtnTkn;
    }
}
