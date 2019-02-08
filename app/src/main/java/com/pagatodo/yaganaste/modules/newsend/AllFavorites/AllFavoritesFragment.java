package com.pagatodo.yaganaste.modules.newsend.AllFavorites;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFavoritesFragment extends Fragment implements   View.OnClickListener{


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
}
