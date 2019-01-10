package com.pagatodo.view_manager.controllers.dataholders;

public class LabelArrowCheckboxDataHolder {

    private String labelTitle;
    private String labelSubtitle;
    private boolean checkbox;

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

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }
}
