package com.pagatodo.yaganaste.ui.cupo.view;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.interfaces.INavigationView;

import java.util.List;

/**
 * Created by Tato on 07/08/17.
 */

public interface IViewCupoComprobantes extends INavigationView {

    void documentsUploaded(String message);

    void setDocumentosStatus(List<EstatusDocumentosResponse> data);

    void documentosActualizados(String s);

}
