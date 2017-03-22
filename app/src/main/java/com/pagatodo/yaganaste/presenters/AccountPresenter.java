package com.pagatodo.yaganaste.presenters;

import com.pagatodo.yaganaste.interfaces.IAccountIteractor;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAccountPresenter;
import com.pagatodo.yaganaste.interfaces.IAccountValidation;
import com.pagatodo.yaganaste.interfaces.IAccountView;
import com.pagatodo.yaganaste.iteractors.AccountIteractor;

import static com.pagatodo.yaganaste.ui.account.login.LetsStartFragment.EVENT_GET_CARD;
import static com.pagatodo.yaganaste.ui.account.login.LetsStartFragment.EVENT_LOGIN;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountPresenter implements IAccountPresenter, IAccountManager {

    private IAccountIteractor accountIteractor;
    private IAccountView accountView;

    public AccountPresenter(IAccountView accountView) {
        this.accountView = accountView;
        accountIteractor = new AccountIteractor(this);
    }

    @Override
    public void initValidationLogin(String usuario) {
        accountIteractor.validateUserStatus(usuario);
    }

    @Override
    public void setUserStatus(boolean isUser) {
        if(accountView instanceof IAccountValidation)
            ((IAccountValidation) accountView).eventTypeUser(isUser ? EVENT_LOGIN : EVENT_GET_CARD);
    }
}
