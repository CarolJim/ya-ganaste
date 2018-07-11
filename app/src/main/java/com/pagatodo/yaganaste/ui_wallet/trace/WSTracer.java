package com.pagatodo.yaganaste.ui_wallet.trace;

import com.pagatodo.yaganaste.data.DataSourceResult;

public abstract class WSTracer implements TraceRequest {

    @Override
    public abstract void Start();

    @Override
    public abstract void End();

    @Override
    public abstract void getStatus(DataSourceResult result);

    @Override
    public abstract void getStatusError();
}
