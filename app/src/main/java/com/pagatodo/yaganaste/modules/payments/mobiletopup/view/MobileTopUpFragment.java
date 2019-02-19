package com.pagatodo.yaganaste.modules.payments.mobiletopup.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.PaymentFormFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MobileTopUpFragment extends GenericFragment {

    private static final String ARG_PARAM1 = "param1";
    private Favoritos favoritos;
    private boolean isFavorite;
    private Comercio comercioResponse;
    private boolean isRecarga;

    private View rootView;


    public MobileTopUpFragment() {
        // Required empty public constructor
    }

    public static GenericFragment newInstance(Object object){
        PaymentFormFragment fragment = new PaymentFormFragment();
        Bundle args = new Bundle();
        if (object instanceof Comercio)
            args.putSerializable(ARG_PARAM1, (Comercio)object);
        else if(object instanceof Favoritos)
            args.putSerializable(ARG_PARAM1, (Favoritos)object);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mobiletopup, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }
}
