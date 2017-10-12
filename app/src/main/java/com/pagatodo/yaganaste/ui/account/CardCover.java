package com.pagatodo.yaganaste.ui.account;

import android.app.Fragment;
import android.content.Intent;
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
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCard;

import butterknife.BindView;

import static com.pagatodo.yaganaste.utils.StringConstants.CARD_NUMBER;

/**
 * Created by Armando Sandoval on 26/09/2017.
 */

public class CardCover extends Fragment {
    private AccountPresenterNew presenter;
    private View rootView;
    private YaGanasteCard cardSaldo;
    private static Preferencias preferencias = App.getInstance().getPrefs();
    public void setPresenter(AccountPresenterNew presenter) {
        this.presenter = presenter;
    }

    private static final String STATUS = "STATUS";
    private String status;

    public static CardCover newInstance(AccountPresenterNew presenter,String status)
    {
        CardCover fragment = new CardCover();
        Bundle bundle = new Bundle();
        bundle.putString(STATUS,status);
        fragment.setArguments(bundle);
        //Note : I tried using bundle.setSerializable but Presenter has too many non Serializable objects.
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        status = "2";
        if(getArguments() != null){
            status = (getArguments().getString(STATUS));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_view_card_cover, container, false);
        cardSaldo = (YaGanasteCard) rootView.findViewById(R.id.cardSaldocover);

        switch (status){
            case Recursos.ESTATUS_CUENTA_BLOQUEADA:
                cardSaldo.setImageResource(R.mipmap.main_card_zoom_gray);
                break;
            case Recursos.ESTATUS_CUENTA_DESBLOQUEADA:
                cardSaldo.setImageResource(R.mipmap.main_card_zoom_blue);
                break;
            default:
                cardSaldo.setImageResource(R.mipmap.main_card_zoom_blue);
                break;

        }

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
