package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.interfaces.Command;

import java.io.Serializable;

/**
 * Created by flima on 18/04/2017.
 */

public class PageResult  implements Serializable{

    private int idResurceIcon;
    private String title = "";
    private String message = "";
    private String description = "";
    private Command actionBtnPrimary;
    private Command actionBtnSecondary;
    private String namerBtnPrimary;
    private String namerBtnSecondary;
    private boolean hasSecondaryAction = false;

    public PageResult() {

    }

    public PageResult(int idResurceIcon, String title, String message,boolean hasErrorAction) {
        this.idResurceIcon = idResurceIcon;
        this.title = title;
        this.message = message;
        this.hasSecondaryAction = hasErrorAction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Command getActionBtnPrimary() {
        return actionBtnPrimary;
    }

    public void setActionBtnPrimary(Command actionBtnPrimary) {
        this.actionBtnPrimary = actionBtnPrimary;
    }

    public Command getActionBtnSecondary() {
        return actionBtnSecondary;
    }

    public void setActionBtnSecondary(Command actionBtnSecondary) {
        this.actionBtnSecondary = actionBtnSecondary;
    }

    public boolean isHasSecondaryAction() {
        return hasSecondaryAction;
    }

    public void setHasSecondaryAction(boolean hasSecondaryAction) {
        this.hasSecondaryAction = hasSecondaryAction;
    }

    public int getIdResurceIcon() {
        return idResurceIcon;
    }

    public void setIdResurceIcon(int idResurceIcon) {
        this.idResurceIcon = idResurceIcon;
    }

    public String getNamerBtnPrimary() {
        return namerBtnPrimary;
    }

    public void setNamerBtnPrimary(String namerBtnPrimary) {
        this.namerBtnPrimary = namerBtnPrimary;
    }

    public String getNamerBtnSecondary() {
        return namerBtnSecondary;
    }

    public void setNamerBtnSecondary(String namerBtnSecondary) {
        this.namerBtnSecondary = namerBtnSecondary;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

