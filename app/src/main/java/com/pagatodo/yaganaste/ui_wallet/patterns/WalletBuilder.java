package com.pagatodo.yaganaste.ui_wallet.patterns;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_STATUS;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ROL;

public class WalletBuilder {

    public static Wallet createWalletsEsencials(boolean error) {
        Wallet walletList = new Wallet();
        if (!error && SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol() != 129) {
            String statusCard = SingletonUser.getInstance().getCardStatusId();
            //if (statusCard != null) {
            if (App.getInstance().getPrefs().loadData(CARD_STATUS).equals(ESTATUS_CUENTA_BLOQUEADA) || App.getInstance().getPrefs().loadData(CARD_NUMBER).equals("")) {
                walletList.addWallet(ElementWallet.getCardyaganasteBloqueda());
            } else {
                walletList.addWallet(ElementWallet.getCardyaganaste());
            }
            /*} else {
                walletList.addWallet(ElementWallet.getCardyaganaste());
            }*/

        } else {
            if (App.getInstance().getPrefs().loadDataInt(ID_ROL) != 129) {
                walletList.addWallet(ElementWallet.getCardyaganaste());
            }
        }
        //Mis Negocios getCardMisNegocios
        //Adquiriente
        if (SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes() != null && !SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().isEmpty()) {

            if (SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().size() > 1) {
                walletList.addWallet(ElementWallet.getCardMisNegocios());
            }
            for (int i = 0; i < SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().size(); i++) {
                walletList.addWallet(ElementWallet.getCardLectorAdq(SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().get(i)));
            }
        } else {
            walletList.addWallet(ElementWallet.getCardLectorAdq(null));
        }

        //Starbucks
        if (SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol() != 129) {
            if (App.getInstance().getPrefs().loadDataBoolean(HAS_STARBUCKS, false)) {
                walletList.addWallet(ElementWallet.getCardStarbucks());
            }
            if (App.getInstance().getPrefs().loadDataBoolean(HAS_STARBUCKS, false)) {
                walletList.addWallet(ElementWallet.getCardSettings());
            }
        }
        return walletList;
    }

    public static Wallet createWalletsBalance() {
        Wallet walletList = new Wallet();
        if (App.getInstance().getPrefs().loadDataInt(ID_ROL) != 129) {
            if (App.getInstance().getPrefs().loadData(CARD_STATUS).equals(ESTATUS_CUENTA_BLOQUEADA)) {
                walletList.addWallet(ElementWallet.getCardBalanceEmiBloqueda());
                //balanceWalletAdpater.addCardItem(new ElementWallet().getCardBalanceEmiBloqueda());
            } else {
                walletList.addWallet(ElementWallet.getCardBalanceEmi());
                //balanceWalletAdpater.addCardItem(new ElementWallet().getCardBalanceEmi());
            }
        }
        List<Agentes> agentes = new ArrayList<>();
        try {
            agentes = new DatabaseManager().getAgentes();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (!RequestHeaders.getTokenAdq().isEmpty() && agentes.size() > 0) {
            for (Agentes agente : agentes) {
                if (agente.getOperadores().size() > 0)
                    walletList.addWallet(ElementWallet.getCardBalanceAdq(agente));
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
