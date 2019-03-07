package com.pagatodo.yaganaste.testHandling.DataTest;

public class LoginData {
    private String email;
    private String hide;

    public LoginData(String email, String hide) {
        this.email = email;
        this.hide = hide;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHide() {
        return hide;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }
}
