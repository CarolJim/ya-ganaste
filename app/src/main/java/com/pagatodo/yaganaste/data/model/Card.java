package com.pagatodo.yaganaste.data.model;

/**
 * @author flima
 */

public class Card {
    private static Card cardData;
    private boolean hasClient;
    private String alias = "";
    private String userName = "";
    private int IdAccount = 0;

    private Card() {

    }

    public static Card getInstance() {
        if (cardData == null) {
            cardData = new Card();
        }

        return cardData;
    }

    public static void resetCardData() {
        cardData = null;
    }

    public boolean isHasClient() {
        return hasClient;
    }

    public void setHasClient(boolean hasClient) {
        this.hasClient = hasClient;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIdAccount() {
        return IdAccount;
    }

    public void setIdAccount(int idAccount) {
        IdAccount = idAccount;
    }

}
