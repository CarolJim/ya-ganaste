package com.pagatodo.yaganaste.ui_wallet.patterns;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.utils.Recursos;

import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;

public class WalletBuilder {

    public static Wallet createWalletsEsencials(boolean error){
        Wallet walletList = new Wallet();
        if (!error && SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol()!=129) {
            String statusCard = SingletonUser.getInstance().getCardStatusId();
            if (statusCard != null) {
                if (statusCard.equalsIgnoreCase(Recursos.ESTATUS_CUENTA_BLOQUEADA) || App.getInstance().getPrefs().loadData(CARD_NUMBER).equals("")) {
                    walletList.addWallet(ElementWallet.getCardyaganasteBloqueda());
                } else {
                    walletList.addWallet(ElementWallet.getCardyaganaste());
                }
            } else {
                walletList.addWallet(ElementWallet.getCardyaganaste());
            }

        } else {

            if (SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol()!=129) {
                walletList.addWallet(ElementWallet.getCardyaganaste());
            }
        }

        for (int i = 0; i < SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().size(); i++){
            walletList.addWallet(ElementWallet.getCardLectorAdq(SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().get(i)));
        }

        if (SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol()!=129) {
            if (App.getInstance().getPrefs().loadDataBoolean(HAS_STARBUCKS, false)) {
                walletList.addWallet(ElementWallet.getCardStarbucks());
            }
        }

        if (SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol()!=129) {
            walletList.addWallet(ElementWallet.getCardSettings());
        }

        return walletList;
    }

    public static Wallet build(Wallet s, ElementWallet i){
        s.addWallet(i);
        return s;
    }


}
