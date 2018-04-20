package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.AdminCardsAdapter;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoAdminCards;
import com.pagatodo.yaganaste.ui_wallet.presenter.AdminCardPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_TO_LOGIN_STARBUCKS;
import static com.pagatodo.yaganaste.ui_wallet.adapters.AdminCardsAdapter.TYPE_HEADER;
import static com.pagatodo.yaganaste.ui_wallet.adapters.AdminCardsAdapter.TYPE_ITEM;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;

public class AdminCardsFragment extends GenericFragment implements OnRecyclerItemClickListener {

    private View rootView;
    @BindView(R.id.rcv_admin_cards)
    RecyclerView rcvAdminCards;
    private List<DtoAdminCards> cardsList;
    private AdminCardPresenter presenter;

    public static final AdminCardsFragment newInstance() {
        AdminCardsFragment adminCardsFragment = new AdminCardsFragment();
        Bundle args = new Bundle();
        adminCardsFragment.setArguments(args);
        return adminCardsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AdminCardPresenter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_admin_cards, container, false);
            initViews();
        }
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvAdminCards.setLayoutManager(llm);
        rcvAdminCards.setHasFixedSize(true);
        updateCards();
    }

    @Override
    public void onRecyclerItemClick(View v, int position) {
        if (cardsList.get(position).getTypeView() == TYPE_ITEM) {
            switch (cardsList.get(position).getID()) {
                case TYPE_EMISOR:
                case TYPE_ADQ:
                    break;
                case TYPE_STARBUCKS:
                    if (cardsList.get(position).getStatus() == 1) {
                        Log.e("YG", "Abrir Log Out Starbucks");
                        App.getInstance().getPrefs().saveDataBool(HAS_STARBUCKS, false);
                    } else {
                        Log.e("YG", "Abrir Log In Starbucks");
                        onEventListener.onEvent(EVENT_GO_TO_LOGIN_STARBUCKS, null);
                    }
                    break;
            }
        }
    }

    private void updateCards() {
        cardsList = presenter.getCardsList();
        rcvAdminCards.setAdapter(new AdminCardsAdapter(cardsList, this));
    }
}
