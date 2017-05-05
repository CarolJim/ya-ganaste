package com.pagatodo.yaganaste.ui._manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.pagatodo.yaganaste.interfaces.IAccountPresenterNew;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui.account.AccountInteractorNew;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;

/**
 * @author jguerras on 10/11/2016.
 */

public abstract class GenericFragment extends Fragment{


    protected OnEventListener onEventListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListener){
            this.onEventListener = (OnEventListener)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onEventListener = null;
    }


    @NonNull
    public String getFragmentTag(){
        return getClass().getSimpleName();
    }

    /**
     * Método para inicializar los views del fragment
     * */
    public abstract void initViews();
}
