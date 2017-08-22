package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CuentaReembolsoFragment extends GenericFragment implements View.OnClickListener {

    @BindView(R.id.cuenta_reembolso_name_user)
    TextView mNameTV;
    @BindView(R.id.cuenta_reembolso_num_cuenta)
    TextView mCuentaTV;
    @BindView(R.id.cuenta_reembolso_num_clabe)
    TextView mClabeTV;
    public static final String M_NAME = "mName";
    public static final String M_TDC = "mTDC";
    public static final String M_CLABE = "mClabe";
    public String mName;
    public String mTDC;
    public String mClabe;
    View rootview;

    public static CuentaReembolsoFragment newInstance(String mName, String mTDC, String mClabe) {

        CuentaReembolsoFragment fragment = new CuentaReembolsoFragment();
        Bundle args = new Bundle();
        args.putString(M_NAME, mName);
        args.putString(M_TDC, mTDC);
        args.putString(M_CLABE, mClabe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        mName = getArguments().getString(M_NAME);
        mTDC = getArguments().getString(M_TDC);
        mClabe = getArguments().getString(M_CLABE);

        mNameTV.setText(mName);
        mCuentaTV.setText("Tarjeta: " + StringUtils.ocultarCardNumberFormat(mTDC));
        mClabeTV.setText("CLABE: " + StringUtils.formatoPagoMedios(mClabe));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_cuenta_reembolso, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void onClick(View view) {

    }
}
