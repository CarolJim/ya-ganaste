package com.pagatodo.yaganaste.ui_wallet.dto;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DtoVideoTutorials {

    public String ID_Video, Title;

    public DtoVideoTutorials(){}

    public DtoVideoTutorials( String ID_Video, String Title) {
        this.ID_Video = ID_Video;
        this.Title = Title;

    }


}
