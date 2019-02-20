package com.pagatodo.view_manager.wallet.components;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class CardData {

    private String parent;
    private String child;
    private boolean active;
    private String email;
    private String key;
    private String UrlIMG;
    private Drawable resIconCard;
    //private BoardIndication boardIndication;
    //private ArrayList<Operation> listOoperations;
    private TypeCard typeCard;
    private boolean isReload;


    public CardData(String parent, String child, boolean isReload) {
        this.parent = parent;
        this.child = child;
        this.isReload = isReload;
    }


    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrlIMG() {
        return UrlIMG;
    }

    public void setUrlIMG(String urlIMG) {
        UrlIMG = urlIMG;
    }

    /*public ArrayList<Operation> getListOoperations() {
        return listOoperations;
    }

    public void setListOoperations(ArrayList<Operation> listOoperations) {
        this.listOoperations = listOoperations;
    }*/

    public Drawable getResIconCard() {
        return resIconCard;
    }

    public void setResIconCard(Drawable resIconCard) {
        this.resIconCard = resIconCard;
    }

    /*public BoardIndication getBoardIndication() {
        return boardIndication;
    }

    public void setBoardIndication(BoardIndication boardIndication) {
        this.boardIndication = boardIndication;
    }*/

    public enum TypeCard implements Serializable {
        BANK_CARD,LOCAL_CARD,LOYALTY_CARD
    }

    public TypeCard getTypeCard() {
        return typeCard;
    }

    public void setTypeCard(TypeCard typeCard) {
        this.typeCard = typeCard;
    }

    public boolean isReload() {
        return isReload;
    }

    public void setReload(boolean reload) {
        isReload = reload;
    }
}
