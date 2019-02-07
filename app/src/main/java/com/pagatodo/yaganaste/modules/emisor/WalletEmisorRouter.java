package com.pagatodo.yaganaste.modules.emisor;

import android.content.Intent;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.emisor.ActivatePhysicalCard.ActivatePhysicalCardFragment;
import com.pagatodo.yaganaste.modules.emisor.CardActivate.CardActivateFragment;
import com.pagatodo.yaganaste.modules.emisor.GeneratePIN.CardSuccessFragment;
import com.pagatodo.yaganaste.modules.emisor.GeneratePIN.GeneratePINFragment;
import com.pagatodo.yaganaste.modules.emisor.VirtualCardAccount.MyVirtualCardAccountFragment;
import com.pagatodo.yaganaste.modules.emisor.ChangeNip.MyChangeNip;
import com.pagatodo.yaganaste.ui._controllers.EnvioFormularioWallet;
import com.pagatodo.yaganaste.ui_wallet.fragments.DescargarEdoCuentaFragment;

import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;

public class WalletEmisorRouter implements WalletEmisorContracts.Router{

    /**
     * id container
     * R.id.fragment_container
     */
    private WalletMainActivity activity;
    private int IdContainer;

    WalletEmisorRouter(WalletMainActivity activity) {
        this.activity = activity;
        this.IdContainer = R.id.fragment_container;
    }

    @Override
    public void onShowActivatePhysicalCard() {
        this.activity.loadFragment(ActivatePhysicalCardFragment.newInstance(),this.IdContainer,Direction.FORDWARD,false);
    }

    @Override
    public void onShowGeneratePIN() {
        this.activity.loadFragment(GeneratePINFragment.newInstance(),this.IdContainer,Direction.FORDWARD, false);
    }

    @Override
    public void onshowAccountStatus() {
        this.activity.loadFragment(DescargarEdoCuentaFragment.newInstance(),this.IdContainer,Direction.FORDWARD,false);
    }

    @Override
    public void onShowMyVirtualCardAccount() {
        this.activity.loadFragment(MyVirtualCardAccountFragment.newInstance(),this.IdContainer,Direction.NONE,false);
    }

    @Override
    public void onShowMyChangeNip() {
        this.activity.loadFragment(MyChangeNip.newInstance(), this.IdContainer, Direction.FORDWARD, false);
    }

    @Override
    public void onShowBlockCard() {
        //this.activity.loadFragment(BlockCardFragment.newInstance(),this.IdContainer,Direction.FORDWARD,false);
    }

    @Override
    public void onShowTemporaryBlock() {

    }

    @Override
    public void onShowCardActiveSuccess() {
        //this.activity.loadFragment(CardActivateFragment.newInstance(),this.IdContainer,Direction.FORDWARD,false);
        this.activity.loadFragment(CardSuccessFragment.newInstance(),this.IdContainer,Direction.FORDWARD,false);
    }

    @Override
    public void onShowEnvioFormulario(Envios envio) {
        Intent intent = new Intent(activity, EnvioFormularioWallet.class);
        intent.putExtra("pagoItem", envio);
        //intent.putExtra("favoritoItem", null);
        activity.startActivityForResult(intent, BACK_FROM_PAYMENTS);

    }
}
