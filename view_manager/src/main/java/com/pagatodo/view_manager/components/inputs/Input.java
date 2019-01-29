package com.pagatodo.view_manager.components.inputs;

import android.util.AttributeSet;

public interface Input{
    void initView(AttributeSet attrs);
    void active();
    void desactive();
    void setHint(String hintText);
    void isError();
    void setInputSecretListener(InputSecretListener listener);
    void setText(String text);
    String getText();
}
