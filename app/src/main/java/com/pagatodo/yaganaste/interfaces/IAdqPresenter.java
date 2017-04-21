package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqPresenter {
    public void validateDongle(String serial);
    public void initTransaction();
    public void sendSignature();
    public void sendTicket();
}
