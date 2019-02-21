package com.pagatodo.view_manager.controllers.dataholders;

import java.io.Serializable;

public class RowFavDataHolder implements Serializable {
    private String imageUrl;
    private String name;
    private String ref;
    private String color;
    private Serializable object;
    private boolean isEdit;


    RowFavDataHolder(String imageUrl, String name, String ref, String color, Serializable object, boolean isEdit) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.ref = ref;
        this.color = color;
        this.object = object;
        this.isEdit = isEdit;
    }

    public static RowFavDataHolder create(String imageUrl, String name, String ref,
                                          String color, Serializable object, boolean isEdit){
        return new RowFavDataHolder(imageUrl,name,ref,color,object,isEdit);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Serializable getObject() {
        return object;
    }

    public void setObject(Serializable object) {
        this.object = object;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }
}
