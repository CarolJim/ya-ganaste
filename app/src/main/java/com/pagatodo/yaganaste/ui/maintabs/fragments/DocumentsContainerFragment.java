package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment;

/**
 * @author Juan Guerra on 07/08/2017.
 */

public class DocumentsContainerFragment extends SupportFragment {


    public static DocumentsContainerFragment newInstance() {
        DocumentsContainerFragment documentsContainerFragment = new DocumentsContainerFragment();
        Bundle args = new Bundle();
        documentsContainerFragment.setArguments(args);
        return documentsContainerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manager_login_container, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    public void initViews() {
        loadFragment(DocumentosFragment.newInstance());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DocumentosFragment.REQUEST_TAKE_PHOTO || requestCode == DocumentosFragment.SELECT_FILE_PHOTO) {
            getCurrentFragment().onActivityResult(requestCode, resultCode, data);
        }
    }
}
