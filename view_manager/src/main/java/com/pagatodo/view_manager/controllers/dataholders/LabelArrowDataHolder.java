package com.pagatodo.view_manager.controllers.dataholders;

public class LabelArrowDataHolder {

    private String labelTitle;
    private String labelSubtitle;

    public LabelArrowDataHolder(String labelTitle, String labelSubtitle) {
        this.labelTitle = labelTitle;
        this.labelSubtitle = labelSubtitle;
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
}
