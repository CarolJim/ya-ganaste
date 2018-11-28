package com.pagatodo.yaganaste.ui_wallet.patterns.builders;

import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;

import java.util.ArrayList;

/**
 * Administrador de listas de Wallets
 */
public class Wallet {

    private ArrayList<ElementWallet> list = new ArrayList<>();

    public void addWallet(ElementWallet wallet){
        list.add(wallet);
    }

    public ArrayList<ElementWallet> getList() {
        return list;
    }
}
