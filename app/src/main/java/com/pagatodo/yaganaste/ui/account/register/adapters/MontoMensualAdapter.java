package com.pagatodo.yaganaste.ui.account.register.adapters;

import android.content.Context;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.ui.account.register.interfaces.IAdaptadores;

import java.util.List;

/**
 * Created by icruz on 02/11/2017.
 */

public class MontoMensualAdapter extends ArrayAdapter<String> implements IAdaptadores {

    public MontoMensualAdapter(@NonNull Context context, @LayoutRes int resource,
                               @NonNull List<String> objects, IOnSpinnerClick iOnSpinnerClick) {
        super(context, resource, objects);
    }

    @Override
    public String name() {
        return "MontoMensual";
    }
}
