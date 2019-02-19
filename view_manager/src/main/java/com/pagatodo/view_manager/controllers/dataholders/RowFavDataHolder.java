package com.pagatodo.view_manager.controllers.dataholders;

import java.io.Serializable;

public class RowFavDataHolder implements Serializable {
    private String imageUrl;
    private String name;
    private String ref;

    private RowFavDataHolder(String imageUrl, String name, String ref) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.ref = ref;
    }

    public static RowFavDataHolder create(String imageUrl, String name, String ref){
        return new RowFavDataHolder(imageUrl,name,ref);
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
}
