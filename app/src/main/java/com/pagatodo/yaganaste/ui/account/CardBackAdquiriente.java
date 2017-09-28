package com.pagatodo.yaganaste.ui.account;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;

import static com.pagatodo.yaganaste.utils.StringConstants.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.StringConstants.USER_BALANCE;

/**
 * Created by Armando Sandoval on 25/09/2017.
 */


public class CardBackAdquiriente extends Fragment {

    private AccountPresenterNew presenter;
    private View rootView;
    MontoTextView txtSaldo;
    private Preferencias prefs = App.getInstance().getPrefs();

    public void setPresenter(AccountPresenterNew presenter) {
        this.presenter = presenter;
    }

    public static CardBackAdquiriente newInstance(AccountPresenterNew presenter)
    {
        CardBackAdquiriente fragment = new CardBackAdquiriente();
        //Note : I tried using bundle.setSerializable but Presenter has too many non Serializable objects.
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.fragment_view_card_back_adquiriente, container, false);
        txtSaldo =(MontoTextView)rootView.findViewById(R.id.txtSaldoPersonal);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtSaldo.setText(Utils.getCurrencyValue(prefs.loadData(USER_BALANCE)));
    }

}
