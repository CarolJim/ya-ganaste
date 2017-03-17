package com.pagatodo.yaganaste.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DummyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DummyFragment extends GenericFragment {


    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

    private TextView txt ;
    private int n;

    public DummyFragment() {

    }

    /**
     * Se crean una instancia del fragmento con
     * el parámetro que indica que tipo de información
     * debe mostrar.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static DummyFragment newInstance(int n) {
        DummyFragment fragment = new DummyFragment();
        Bundle args = new Bundle();
        args.putInt("N",n);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;

        if (context instanceof Activity){
            activity = (Activity) context;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         n = getArguments().getInt("N");

//        asistancePresenter = new AsistancePresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dummy, container, false);


        SetupToolbar(rootView);
//        asistancePresenter.loadData();

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause(){
        super.onPause();
    }

    private void SetupToolbar(View rootView) {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
//            getActivity().
//            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
//            getActivity().getActionBar().setDisplayShowTitleEnabled(false);
        }
    }









}