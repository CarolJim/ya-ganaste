package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;
import java.util.ArrayList;

public class SaldoRequest extends AdqRequest implements Serializable{

    private List<DataMovimientoAdq> data  = new ArrayList<>();
}
