package com.pagatodo.yaganaste.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.pagatodo.yaganaste.interfaces.Command;

/**
 * Created by flima on 18/04/2017.
 */

public class PageResult implements Parcelable {

    public static final String BTN_DIRECTION_NEXT = "direction_next";
    public static final String BTN_DIRECTION_BACK = "direction_back";
    public static final String BTN_ACTION_OK = "action_ok";
    public static final String BTN_ACTION_ERROR = "action_error";
    public static final Creator<PageResult> CREATOR = new Creator<PageResult>() {
        @Override
        public PageResult createFromParcel(Parcel in) {
            return new PageResult(in);
        }

        @Override
        public PageResult[] newArray(int size) {
            return new PageResult[size];
        }
    };
    private int idResurceIcon;
    private String title = "";
    private String message = "";
    private String description = "";
    private Command actionBtnPrimary;
    private Command actionBtnSecondary;
    private String namerBtnPrimary = "";
    private String namerBtnSecondary = "";
    private boolean hasSecondaryAction = false;
    private String btnPrimaryType = "";
    private String btnSecundaryType = "";

    public PageResult() {

    }

    public PageResult(int idResurceIcon, String title, String message, boolean hasErrorAction) {
        this.idResurceIcon = idResurceIcon;
        this.title = title;
        this.message = message;
        this.hasSecondaryAction = hasErrorAction;
    }

    protected PageResult(Parcel in) {
        idResurceIcon = in.readInt();
        title = in.readString();
        message = in.readString();
        description = in.readString();
        namerBtnPrimary = in.readString();
        namerBtnSecondary = in.readString();
        hasSecondaryAction = in.readByte() != 0;
        btnPrimaryType = in.readString();
        btnSecundaryType = in.readString();
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

    public String getBtnPrimaryType() {
        return btnPrimaryType;
    }

    public void setBtnPrimaryType(String style) {
        this.btnPrimaryType = style;
    }

    public String getBtnSecundaryType() {
        return btnSecundaryType;
    }

    public void setBtnSecundaryType(String style) {
        this.btnSecundaryType = style;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idResurceIcon);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(description);
        dest.writeString(namerBtnPrimary);
        dest.writeString(namerBtnSecondary);
        dest.writeByte((byte) (hasSecondaryAction ? 1 : 0));
        dest.writeString(btnPrimaryType);
        dest.writeString(btnSecundaryType);
    }
}

