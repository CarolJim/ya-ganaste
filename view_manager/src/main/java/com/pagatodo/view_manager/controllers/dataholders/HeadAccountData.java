package com.pagatodo.view_manager.controllers.dataholders;

public class HeadAccountData {
    private String urlImage;
    private String colorMarca;
    private String name;

    private HeadAccountData(String urlImage, String colorMarca, String name) {
        this.urlImage = urlImage;
        this.colorMarca = colorMarca;
        this.name = name;
    }

    public static HeadAccountData create(String urlImage, String colorMarca, String name){
        return new HeadAccountData(urlImage,colorMarca,name);
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
}
