package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by icruz on 07/03/2018.
 */

public class ReembolsoDataRequest extends AdqRequestNoTag implements Serializable {

    private List<DataMovimientoAdq> request  = new ArrayList<>();

    public ReembolsoDataRequest(DataMovimientoAdq data) {
        addData(data);
    }

    public void addData(DataMovimientoAdq dataMovimientoAdq){
        this.request.add(dataMovimientoAdq);
    }
}
