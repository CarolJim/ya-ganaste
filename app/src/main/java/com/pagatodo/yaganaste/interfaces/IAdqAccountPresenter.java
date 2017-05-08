package com.pagatodo.yaganaste.interfaces;

import android.view.View;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqAccountPresenter {

    public void createAdq();
    public void getNeighborhoods(String zipCode);
    public void getEstatusDocs(View view);
    public void setListaDocs(View view);
    public void getClientAddress();

}
