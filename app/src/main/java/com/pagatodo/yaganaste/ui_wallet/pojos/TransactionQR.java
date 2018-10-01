package com.pagatodo.yaganaste.ui_wallet.pojos;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class TransactionQR {
    public  int Monto;
    public String Status;
    public String TimeStamp;

    public TransactionQR() {
    }

    public TransactionQR(int monto, String status, String timeStamp) {
        this.Monto = monto;
        this.Status = status;
        this.TimeStamp = timeStamp;
    }

}
