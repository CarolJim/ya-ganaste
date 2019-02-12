package com.pagatodo.yaganaste.modules.newsend.SendFromCard;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendFromCardFragment extends Fragment {


    public SendFromCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_from_card, container, false);
    }

}
