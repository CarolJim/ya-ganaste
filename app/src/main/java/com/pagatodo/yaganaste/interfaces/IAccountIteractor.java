package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.exceptions.OfflineException;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountIteractor {
    public void validateUserStatus(String user) throws OfflineException;
}
