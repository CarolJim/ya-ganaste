package com.pagatodo.yaganaste.modules.onboarding;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ElementOnboarding {

    public String url_img;

    public ElementOnboarding(String url_img) {
        this.url_img = url_img;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }
}
