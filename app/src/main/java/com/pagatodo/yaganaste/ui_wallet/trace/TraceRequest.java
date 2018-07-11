package com.pagatodo.yaganaste.ui_wallet.trace;

import com.pagatodo.yaganaste.data.DataSourceResult;

public interface TraceRequest {
    void Start();
    void End();
    void getStatus(DataSourceResult result);
    void getStatusError();
}
