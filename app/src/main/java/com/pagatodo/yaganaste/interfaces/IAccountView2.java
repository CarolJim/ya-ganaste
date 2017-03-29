package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountView2 <T> extends IProgressView {
    public void nextStepRegister(String event,T data);
    public void backStepRegister(String event,T data);
}
