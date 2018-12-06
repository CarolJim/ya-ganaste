package com.pagatodo.yaganaste.modules.components.stepbar;

import android.graphics.drawable.Drawable;

public class StepBarItem {

    private State state;
    private String numText;
    private Drawable resource;

    public StepBarItem(State state, String numText) {
        this.state = state;
        this.numText = numText;
    }

    public StepBarItem(State state, String numText, Drawable resource) {
        this.state = state;
        this.numText = numText;
        this.resource = resource;
    }

    public Drawable getResource() {
        return resource;
    }

    public void setResource(Drawable resource) {
        this.resource = resource;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getNumText() {
        return numText;
    }

    public void setNumText(String numText) {
        this.numText = numText;
    }

    public enum State {
    inactive,active,check}
}
