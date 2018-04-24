package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CardStarbucks;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.AdminStarbucksAdapter;
import com.pagatodo.yaganaste.ui_wallet.presenter.AdminStarbucksPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Recursos.NUMBER_CARD_STARBUCKS;

public class AdminStarbucksFragment extends GenericFragment implements View.OnClickListener, OnRecyclerItemClickListener {

    View rootView;
    @BindView(R.id.rcv_starbucks_cards)
    RecyclerView rcvCards;
    @BindView(R.id.btn_save_admin_starbucks)
    StyleButton btnSave;
    private AdminStarbucksPresenter presenter;
    private List<CardStarbucks> cardList;

    public static final AdminStarbucksFragment newInstance() {
        AdminStarbucksFragment fragment = new AdminStarbucksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AdminStarbucksPresenter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_admin_starbucks, container, false);
            initViews();
        }
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvCards.setLayoutManager(llm);
        rcvCards.setHasFixedSize(true);
        btnSave.setOnClickListener(this);
        cardList = presenter.getCardsOfUser();
        if (cardList.size() > 0) {
            rcvCards.setAdapter(new AdminStarbucksAdapter(this, cardList));
        } else {
            getActivity().finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_admin_starbucks:
                if (App.getInstance().getPrefs().loadData(NUMBER_CARD_STARBUCKS).equals("")) {
                    UI.showErrorSnackBar(getActivity(), getString(R.string.error_saving), Snackbar.LENGTH_SHORT);
                } else {
                    UI.showSuccessSnackBar(getActivity(), getString(R.string.success_saving), Snackbar.LENGTH_SHORT);
                    getActivity().finish();
                }
                break;
        }
    }

    @Override
    public void onRecyclerItemClick(View v, int position) {
        App.getInstance().getPrefs().saveData(NUMBER_CARD_STARBUCKS, cardList.get(position).getNumeroTarjeta());
    }
}
