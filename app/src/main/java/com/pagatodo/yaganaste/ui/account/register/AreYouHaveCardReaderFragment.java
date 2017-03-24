package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class AreYouHaveCardReaderFragment extends GenericFragment implements View.OnClickListener{

    private View rootview;
    @BindView(R.id.btnHaveReaderIHaveIt)
    StyleButton btnHaveReaderIHaveIt;
    @BindView(R.id.btnHaveReaderIdontHave)
    StyleButton btnHaveReaderIdontHave;
    @BindView(R.id.txtHaveCardReaderWhereBuy)
    StyleTextView txtHaveCardReaderWhereBuy;

    public AreYouHaveCardReaderFragment() {
    }

    public static AreYouHaveCardReaderFragment newInstance() {
        AreYouHaveCardReaderFragment fragmentRegister = new AreYouHaveCardReaderFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_are_you_have_card_reader, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnHaveReaderIHaveIt.setOnClickListener(this);
        btnHaveReaderIdontHave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHaveReaderIHaveIt:

                break;
            case R.id.btnHaveReaderIdontHave:

                break;
            default:
                break;
        }
    }

}

