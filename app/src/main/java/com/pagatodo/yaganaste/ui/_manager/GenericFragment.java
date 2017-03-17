package com.pagatodo.yaganaste.ui._manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.pagatodo.yaganaste.interfaces.OnEventListener;

/**
 * Created by jguerras on 10/11/2016.
 */

public abstract class GenericFragment<T> extends Fragment {

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

//    public abstract T getFragmentData();

    @NonNull
    public String getFragmentTag(){
        return getClass().getSimpleName();
    };

}
