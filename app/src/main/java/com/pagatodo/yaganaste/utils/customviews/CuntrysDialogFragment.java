package com.pagatodo.yaganaste.utils.customviews;

import android.app.DialogFragment;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;

public class CuntrysDialogFragment extends DialogFragment  implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
