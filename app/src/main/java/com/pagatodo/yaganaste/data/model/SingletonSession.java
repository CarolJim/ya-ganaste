package com.pagatodo.yaganaste.data.model;


/**
 * @author flima
 */

public class SingletonSession {

    public static SingletonSession session;

    private boolean active = false;
    private boolean finish = false;
    private String nameUser = "";

    private SingletonSession(){
    }

    public static synchronized SingletonSession getInstance(){
        if(session == null)
            session = new SingletonSession();
        return session;
    }


    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}
