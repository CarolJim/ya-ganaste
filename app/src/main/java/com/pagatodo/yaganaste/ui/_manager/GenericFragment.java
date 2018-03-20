package com.pagatodo.yaganaste.ui._manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ISessionExpired;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.utils.UI;

import static com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity.EVENT_SESSION_EXPIRED;


public abstract class GenericFragment extends Fragment implements ISessionExpired {


    protected OnEventListener onEventListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListener) {
            this.onEventListener = (OnEventListener) context;
        }
    }

    @Override
    public void errorSessionExpired(DataSourceResult dataSourceResult) {
        GenericResponse response = (GenericResponse) dataSourceResult.getData();
        final String mensaje = response.getMensaje();
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        onEventListener.onEvent(EVENT_SESSION_EXPIRED, 1);
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onEventListener = null;
    }


    @NonNull
    public String getFragmentTag() {
        return getClass().getSimpleName();
    }

    /**
     * Método para inicializar los views del fragment
     */
    public abstract void initViews();
}
