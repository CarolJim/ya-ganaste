package com.pagatodo.yaganaste.ui.cupo.presenters.interfaces;

/**
 * Created by Tato on 02/08/17.
 */

public interface IViewDomicilioPersonalPresenter {

        void getNeighborhoods(String zipCode);

        void getClientAddress();

        public abstract void showGaleryError();

        void getEstatusDocs();

}
