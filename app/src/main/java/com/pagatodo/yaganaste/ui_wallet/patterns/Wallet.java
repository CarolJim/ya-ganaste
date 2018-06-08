package com.pagatodo.yaganaste.ui_wallet.patterns;

import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;

import java.util.ArrayList;

public class Wallet {

    private ArrayList<ElementWallet> list = new ArrayList<>();

    public void addWallet(ElementWallet wallet){
        list.add(wallet);
    }

    public ArrayList<ElementWallet> getList() {
        return list;
    }
}
