package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface INavigationView<T> extends IProgressView {
    public void nextScreen(String event, T data);
    public void backScreen(String event, T data);
}
