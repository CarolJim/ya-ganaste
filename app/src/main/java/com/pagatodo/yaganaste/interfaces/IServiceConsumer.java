package com.pagatodo.yaganaste.interfaces;


import com.pagatodo.yaganaste.net.WsRequest;

/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 *          Invoker de servicios web .
 */
public interface IServiceConsumer {

    void sendJsonPost(final WsRequest request);
}
