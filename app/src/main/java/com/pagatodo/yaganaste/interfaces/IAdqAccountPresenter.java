package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqAccountPresenter {

    public void createAdq();
    public void login();
    public void getNeighborhoods(String zipCode);

    public void validateDongle(String zipCode);
    public void initTransaction(String zipCode);
    public void sendTicket(String zipCode);
}