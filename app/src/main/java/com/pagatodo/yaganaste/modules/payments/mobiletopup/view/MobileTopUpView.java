package com.pagatodo.yaganaste.modules.payments.mobiletopup.view;

import android.os.Bundle;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.PaymentFormFragment;

public interface MobileTopUpView {
    void onError(String error);
    void nextView();
}
