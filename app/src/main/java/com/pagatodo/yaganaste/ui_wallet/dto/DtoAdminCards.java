package com.pagatodo.yaganaste.ui_wallet.dto;

public class DtoAdminCards {

    private int ID, resource, order, status, typeView;
    private String title, description;

    public DtoAdminCards(int ID, int resource, int order, int status, int typeView, String title, String description) {
        this.ID = ID;
        this.resource = resource;
        this.order = order;
        this.typeView = typeView;
        this.status = status;
        this.title = title;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public int getResource() {
        return resource;
    }

    public int getOrder() {
        return order;
    }

    public int getStatus() {
        return status;
    }

    public int getTypeView() {
        return typeView;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
