package com.pagatodo.yaganaste.ui_wallet.dto;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DtoVideoTutorials {

    public String ID_Video, Title, Subtitle;

    public DtoVideoTutorials(){}

    public DtoVideoTutorials( String ID_Video, String Title, String Subtitle) {
        this.ID_Video = ID_Video;
        this.Title = Title;
        this.Subtitle = Subtitle;

    }


}
