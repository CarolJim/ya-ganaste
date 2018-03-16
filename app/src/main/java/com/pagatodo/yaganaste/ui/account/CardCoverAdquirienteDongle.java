package com.pagatodo.yaganaste.ui.account;

/**
 * Created by Armando Sandoval on 27/09/2017.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;

/**
 * Created by Armando Sandoval on 26/09/2017.
 */

public class CardCoverAdquirienteDongle extends Fragment {
    private AccountPresenterNew presenter;
    private View rootView;

    private static Preferencias preferencias = App.getInstance().getPrefs();
    public void setPresenter(AccountPresenterNew presenter) {
        this.presenter = presenter;
    }

    public static CardCoverAdquirienteDongle newInstance(AccountPresenterNew presenter)
    {
        CardCoverAdquirienteDongle fragment = new CardCoverAdquirienteDongle();
        //Note : I tried using bundle.setSerializable but Presenter has too many non Serializable objects.
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_view_card_cover_adquiriente_dongle, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
