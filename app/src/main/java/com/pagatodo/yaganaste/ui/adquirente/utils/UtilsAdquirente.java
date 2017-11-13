package com.pagatodo.yaganaste.ui.adquirente.utils;

import android.location.Location;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.AccountDepositData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.EnviarTicketCompraRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.FirmaDeVoucherRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ImplicitData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.SignatureData;
import com.pagatodo.yaganaste.utils.MyLocation;
import com.pagatodo.yaganaste.utils.Utils;

/**
 * Created by jvazquez on 12/05/2017.
 */

public class UtilsAdquirente {
    public static AccountDepositData getCurrentDatesAccountDepositData(String reference) {
        SingletonUser currentUser = SingletonUser.getInstance();
        AccountDepositData data = new AccountDepositData();
        if (currentUser.getDataUser().getUsuario().getCuentas().size() > 0) {
            data.setAccount(String.valueOf(currentUser.getDataUser().getUsuario().getCuentas().get(0).getIdCuenta()));
        }
        data.setReference(reference);
        return data;
    }

    public static ImplicitData getImplicitData() {
        Location location = MyLocation.getLocation(App.getInstance());
        ImplicitData data = new ImplicitData();
        if (location != null) {
            Double tempLat = location.getLatitude();
            Double tempLng = location.getLongitude();
            data.setLat(tempLat != null ? tempLat.toString() : "19.4340");
            data.setLon(tempLng != null ? tempLng.toString() : "-99.1329");
        } else {
            data.setLat("19.4340");
            data.setLon("-99.1329");
        }
        data.setUdid(Utils.getUdid(App.getInstance()));
        return data;
    }

    public static FirmaDeVoucherRequest buildSignatureRequest(String idTransaction, SignatureData signatureData) {
        FirmaDeVoucherRequest request = new FirmaDeVoucherRequest();
        request.setIdTransaction(idTransaction);
        request.setSignaruteData(signatureData);
        return request;
    }

    public static EnviarTicketCompraRequest buildTicketRequest(String idTransaction, String name, String email, boolean applyAgent) {
        EnviarTicketCompraRequest request = new EnviarTicketCompraRequest();
        request.setEmail(email);
        request.setImplicitData(getImplicitData());
        request.setName(name);
        request.setIdTransaction(idTransaction);
        request.setAplicaAgente(applyAgent);
        return request;
    }
}
