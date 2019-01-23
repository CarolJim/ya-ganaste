package com.pagatodo.view_manager.controllers.dataholders;

import android.widget.CheckBox;

public class LabelArrowCheckboxDataHolder {

    private String labelTitle;
    private String labelSubtitle;
    private Boolean checkbox;

    public LabelArrowCheckboxDataHolder(String labelTitle, String labelSubtitle, boolean checkbox) {
        this.labelTitle = labelTitle;
        this.labelSubtitle = labelSubtitle;
        this.checkbox = checkbox;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public String getLabelSubtitle() {
        return labelSubtitle;
    }

    public void setLabelSubtitle(String labelSubtitle) {
        this.labelSubtitle = labelSubtitle;

    }

    public Boolean getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(Boolean checkbox) {
        this.checkbox = checkbox;
    }
}


