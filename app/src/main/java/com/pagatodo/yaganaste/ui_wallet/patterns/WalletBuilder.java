package com.pagatodo.yaganaste.ui_wallet.patterns;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.AdquirienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.AgentesRespose;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.utils.Recursos;

import static com.pagatodo.yaganaste.utils.Recursos.ADQRESPONSE;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;

public class WalletBuilder {

    public static Wallet createWalletsEsencials(boolean error) {
        Wallet walletList = new Wallet();
        if (!error && SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol() != 129) {
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
            if (SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol() != 129) {
                walletList.addWallet(ElementWallet.getCardyaganaste());
            }
        }
        if (SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes() != null) {
            for (int i = 0; i < SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().size(); i++) {
                walletList.addWallet(ElementWallet.getCardLectorAdq(SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().get(i)));
            }
        } else {
            walletList.addWallet(ElementWallet.getCardLectorAdq(new AgentesRespose()));
        }

        if (SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol() != 129) {
            if (App.getInstance().getPrefs().loadDataBoolean(HAS_STARBUCKS, false)) {
                walletList.addWallet(ElementWallet.getCardStarbucks());
            }
            walletList.addWallet(ElementWallet.getCardSettings());
        }
        return walletList;
    }

    public static Wallet createWalletsBalance() {
        Wallet walletList = new Wallet();
        if (App.getInstance().getStatusId().equals(ESTATUS_CUENTA_BLOQUEADA)) {
            walletList.addWallet(ElementWallet.getCardBalanceEmiBloqueda());
            //balanceWalletAdpater.addCardItem(new ElementWallet().getCardBalanceEmiBloqueda());
        } else {
            walletList.addWallet(ElementWallet.getCardBalanceEmi());
            //balanceWalletAdpater.addCardItem(new ElementWallet().getCardBalanceEmi());
        }
        if (!RequestHeaders.getTokenAdq().isEmpty() && App.getInstance().getPrefs().loadAdquirienteResponse(ADQRESPONSE) != null) {
            AdquirienteResponse response = App.getInstance().getPrefs().loadAdquirienteResponse(ADQRESPONSE).getAdquirente();
            for (AgentesRespose agentesRespose : response.getAgentes()) {
                walletList.addWallet(ElementWallet.getCardBalanceAdq(agentesRespose));
            }
            /*for (int i = 0; i < response.getAgentes().size(); i++){
                walletList.addWallet(ElementWallet.getCardBalanceAdq(response.getAgentes().get(i)));
            }*/
            //walletList.addWallet(ElementWallet.getCardBalanceAdq());
            //balanceWalletAdpater.addCardItem(new ElementWallet().getCardBalanceAdq());
        }
        if (App.getInstance().getPrefs().loadDataBoolean(HAS_STARBUCKS, false)) {
            walletList.addWallet(ElementWallet.getCardBalanceStarbucks());
            //balanceWalletAdpater.addCardItem(new ElementWallet().getCardBalanceStarbucks());
        }
        return walletList;
    }

    public static Wallet build(Wallet s, ElementWallet i) {
        s.addWallet(i);
        return s;
    }


}
