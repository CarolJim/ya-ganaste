package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;

/**
 * Created by Francisco Manzo on 06/07/2017.
 * Nos permite procesar el objeto DataSourceResult desde el servicio, este controlara el cerrado de
 * la app al teenr un codigo 16, que indica que la session se ha terminado
 */

public interface ISessionExpired{
    void errorSessionExpired(DataSourceResult response);
}
