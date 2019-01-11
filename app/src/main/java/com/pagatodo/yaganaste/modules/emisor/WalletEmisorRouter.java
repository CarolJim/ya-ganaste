package com.pagatodo.yaganaste.modules.emisor;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.emisor.ActivatePhysicalCard.ActivatePhysicalCardFragment;
import com.pagatodo.yaganaste.modules.emisor.CardActivate.CardActivateFragment;
import com.pagatodo.yaganaste.modules.emisor.GeneratePIN.GeneratePINFragment;
import com.pagatodo.yaganaste.modules.emisor.VirtualCardAccount.MyVirtualCardAccountFragment;
import com.pagatodo.yaganaste.modules.emisor.ChangeNip.MyChangeNip;
import com.pagatodo.yaganaste.ui_wallet.fragments.DescargarEdoCuentaFragment;

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
    public void onShowCardActive() {
        this.activity.loadFragment(CardActivateFragment.newInstance(),this.IdContainer,Direction.FORDWARD,false);
    }
}
