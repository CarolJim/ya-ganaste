package com.pagatodo.view_manager.controllers.dataholders;

public class HeadAccountData {
    private String urlImage;
    private String colorMarca;
    private String name;
    private String reference;

    public HeadAccountData(String urlImage, String colorMarca, String name, String reference) {
        this.urlImage = urlImage;
        this.colorMarca = colorMarca;
        this.name = name;
        this.reference = reference;
    }

    public static HeadAccountData create(String urlImage, String colorMarca, String name, String reference){
        return new HeadAccountData(urlImage,colorMarca,name, reference);
    }
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getColorMarca() {
        return colorMarca;
    }

    public void setColorMarca(String colorMarca) {
        this.colorMarca = colorMarca;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
