package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 27/03/2017.
 */

public interface IViewRegister <T>  extends IAccountView{

    public void nextStepRegister(String event,T data);
    public void backStepRegister(String event,T data);
}
