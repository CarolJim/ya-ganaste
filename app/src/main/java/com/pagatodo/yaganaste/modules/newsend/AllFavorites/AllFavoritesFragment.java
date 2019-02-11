package com.pagatodo.yaganaste.modules.newsend.AllFavorites;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFavoritesFragment extends GenericFragment implements   View.OnClickListener{

    @BindView(R.id.text_email)
    TextInputLayout text_email;


    public  static  AllFavoritesFragment newInstance(){
        return new AllFavoritesFragment();
    }




    public AllFavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_favorites, container, false);
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public void initViews() {

    }
}
