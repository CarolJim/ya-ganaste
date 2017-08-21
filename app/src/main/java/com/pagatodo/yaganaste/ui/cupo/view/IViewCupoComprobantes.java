package com.pagatodo.yaganaste.ui.cupo.view;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.cupo.DataEstadoDocumentos;
import com.pagatodo.yaganaste.interfaces.INavigationView;

import java.util.List;

/**
 * Created by Horacio on 07/08/17.
 */

public interface IViewCupoComprobantes extends INavigationView {

    void setResponseDocuments();

    void obtieneEstadoDeDocumentos();

    void setResponseEstadoDocumentos(List<DataEstadoDocumentos> msgSuccess);
}
