package com.pagatodo.yaganaste.ui.account;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCardBack;

import static com.pagatodo.yaganaste.utils.StringConstants.USER_BALANCE;

/**
 * Created by Armando Sandoval on 25/09/2017.
 */


public class CardBack extends Fragment {

    private AccountPresenterNew presenter;
    private View rootView;
    private YaGanasteCardBack cardSaldo;
    MontoTextView txtSaldo;
    private static Preferencias preferencias = App.getInstance().getPrefs();

    private static final String STATUS = "STATUS";
    private String status;

    public void setPresenter(AccountPresenterNew presenter) {
        this.presenter = presenter;
    }

    /*public static CardBack newInstance(AccountPresenterNew presenter)
    {
        CardBack fragment = new CardBack();
        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);
        //Note : I tried using bundle.setSerializable but Presenter has too many non Serializable objects.
        fragment.setPresenter(presenter);
        return fragment;
    }*/

    public static CardBack newInstance(AccountPresenterNew presenter, String status) {
        CardBack fragment = new CardBack();
        Bundle bundle = new Bundle();
        bundle.putString(STATUS, status);
        fragment.setArguments(bundle);
        //Note : I tried using bundle.setSerializable but Presenter has too many non Serializable objects.
        fragment.setPresenter(presenter);
        return fragment;
    }
    /* *
     * @param savedInstanceState*/


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //status = "1";
        if (getArguments() != null) {
            status = (getArguments().getString(STATUS));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_view_card_back, container, false);
        txtSaldo = (MontoTextView) rootView.findViewById(R.id.txt_saldo);
        cardSaldo = (YaGanasteCardBack) rootView.findViewById(R.id.cardSaldo);

        switch (status) {
            case Recursos.ESTATUS_CUENTA_BLOQUEADA:
                cardSaldo.setImageResource(R.mipmap.card_back_backmara_2);
                break;
            case Recursos.ESTATUS_CUENTA_DESBLOQUEADA:
                cardSaldo.setImageResource(R.mipmap.card_back_backmara);
                break;

        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtSaldo.setText(Utils.getCurrencyValue(preferencias.loadData(USER_BALANCE)));
/*
        rootView.findViewById(R.id.llcardsaldo).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                presenter.flipCard(R.id.llcardsaldo, null);
                return false;
            }
        });

        */
    }
    /*

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rootView.findViewById(R.id.cardSaldo).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                presenter.flipCard(R.id.cardSaldo, null);
                return false;
            }
        });
    }
    */
}
