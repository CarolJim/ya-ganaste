package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by jvazquez on 27/10/2016.
 */

public class Point implements Serializable {
    public Float x;
    public Float y;

    public Point(Float x, Float y) {
        this.x = x;
        this.y = y;
    }
}