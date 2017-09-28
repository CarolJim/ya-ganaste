package com.pagatodo.yaganaste.ui.account;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCard;

import butterknife.BindView;

import static com.pagatodo.yaganaste.utils.StringConstants.CARD_NUMBER;

/**
 * Created by Armando Sandoval on 26/09/2017.
 */

public class CardCoverAdquiriente extends Fragment {
    private AccountPresenterNew presenter;
    private View rootView;
    YaGanasteCard cardSaldo;
    private static Preferencias preferencias = App.getInstance().getPrefs();
    public void setPresenter(AccountPresenterNew presenter) {
        this.presenter = presenter;
    }

    public static CardCoverAdquiriente newInstance(AccountPresenterNew presenter)
    {
        CardCoverAdquiriente fragment = new CardCoverAdquiriente();
        //Note : I tried using bundle.setSerializable but Presenter has too many non Serializable objects.
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_view_card_cover_adquiriente, container, false);
        cardSaldo = (YaGanasteCard) rootView.findViewById(R.id.cardBalanceAdq);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String cardNumber = preferencias.loadData(CARD_NUMBER);
//                    Utils.getCurrencyValue(cardNumber))
        cardSaldo.setCardNumber(StringUtils.ocultarCardNumberFormat(cardNumber));
        /*
        rootView.findViewById(R.id.fragment_purse_ll_card_view).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                presenter.flipCard(R.id.llcardsaldo, CardBack.newInstance(presenter));
                return false;
            }
        });
        */
    }
}