package com.pagatodo.yaganaste.ui.maintabs.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.view_manager.components.HeadWallet;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendsFragment extends GenericFragment implements View.OnClickListener {
    private HeadWallet headWallet;
    private LinearLayout send_from_card, send_from_clabe, send_from_telephone, send_from_qr;
    private View rootView;

    public SendsFragment() {
        // Required empty public constructor
    }

    public static SendsFragment newInstance() {
        return new SendsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sends, container, false);
        send_from_card = (LinearLayout) rootView.findViewById(R.id.send_from_card);
        send_from_telephone = (LinearLayout) rootView.findViewById(R.id.send_from_telephone);
        send_from_clabe = (LinearLayout) rootView.findViewById(R.id.send_from_clabe);
        send_from_qr = (LinearLayout) rootView.findViewById(R.id.send_from_qr);
        headWallet= (HeadWallet) rootView.findViewById(R.id.headWallet);

        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        send_from_card.setOnClickListener(this::onClick);
        send_from_telephone.setOnClickListener(this::onClick);
        send_from_clabe.setOnClickListener(this::onClick);
        send_from_qr.setOnClickListener(this::onClick);
        headWallet.setAmount(App.getInstance().getPrefs().loadData(USER_BALANCE));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_from_card:
                break;
            case R.id.send_from_telephone:
                break;
            case R.id.send_from_clabe:
                break;
            case R.id.send_from_qr:
                break;
        }
    }
}
