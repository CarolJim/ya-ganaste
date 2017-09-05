package com.pagatodo.yaganaste.ui.cupo.view;

import com.pagatodo.yaganaste.data.model.webservice.response.cupo.DataEstadoSolicitud;
import com.pagatodo.yaganaste.interfaces.INavigationView;

/**
 * Created by Dell on 26/07/2017.
 */

public interface IViewStatusRegisterCupo extends INavigationView {

    //todo CUPO registro agregar Datos correctos a mostrar
    public void showStatusRegister();

    void setResponseEstadoCupo(DataEstadoSolicitud msgSuccess);

}
