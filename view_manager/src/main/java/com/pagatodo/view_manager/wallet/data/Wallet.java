package com.pagatodo.view_manager.wallet.data;

public class Wallet {

    private Object object;
    private WalletType walletType;

    Wallet(Object object, WalletType walletType) {
        this.object = object;
        this.walletType = walletType;
    }

    public static Wallet create(Object object, WalletType walletType){
        return new Wallet(object,walletType);
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public WalletType getWalletType() {
        return walletType;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }
}
