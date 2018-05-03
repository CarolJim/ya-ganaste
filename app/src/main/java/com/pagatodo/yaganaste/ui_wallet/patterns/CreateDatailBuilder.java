package com.pagatodo.yaganaste.ui_wallet.patterns;

import android.content.Context;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE;
import com.pagatodo.yaganaste.ui_wallet.pojos.TextData;
import com.pagatodo.yaganaste.utils.StringUtils;

import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.DEVOLUCION;

public class CreateDatailBuilder {

    public static void createByType(Context context, ViewGroup container, MovimientosResponse response){
        DetailBulder builder = new DetailBulder(context,container);

        switch (TipoTransaccionPCODE.getTipoTransaccionById(response.getIdTipoTransaccion())){
            case RECARGA:
                if (response.getIdComercio() == 7){
                    builder.createLeaf(new TextData(R.string.details_comision,StringUtils.getCurrencyValue(response.getComision())));
                    builder.createLeaf(new TextData(R.string.details_iva,StringUtils.getCurrencyValue(response.getIVA())));
                    builder.createLeaf(new TextData(R.string.details_iave_pase, response.getReferencia()));
                } else {
                    builder.createLeaf(new TextData(R.string.txt_phone, response.getReferencia()));
                }
                break;
            case PAGO_DE_SERVICIO:
                builder.createLeaf(new TextData(R.string.details_cargo_servicio,StringUtils.getCurrencyValue(response.getComision())));
                builder.createLeaf(new TextData(R.string.details_iva,StringUtils.getCurrencyValue(response.getIVA())));
                builder.createLeaf(new TextData(R.string.details_ferencia,response.getReferencia()));
                break;
            case TRASPASO_MISMO_BANCO_CARGO:
                builder.createLeaf(new TextData(getReferencuaTitleType(response.getReferencia()),response.getReferencia()));
                builder.createLeaf(new TextData(R.string.details_concepto,response.getConcepto()));
                break;
            case SPEI_CARGO:
                builder.createLeaf(new TextData(R.string.details_comision,StringUtils.getCurrencyValue(response.getComision())));
                builder.createLeaf(new TextData(R.string.details_iva,StringUtils.getCurrencyValue(response.getIVA())));
                if (!response.getReferencia().equals("")) {
                    builder.createLeaf(new TextData(getReferencuaTitleType(response.getReferencia()),response.getReferencia()));
                }
                builder.createLeaf(new TextData(R.string.details_concepto,response.getConcepto()));
                if (!response.getClaveRastreo().equals("")) {
                    builder.createLeaf(new TextData(R.string.details_clave_rastreo,response.getClaveRastreo()));
                }
                if (!response.getReferenciaNum().trim().equals("")) {
                    builder.createLeaf(new TextData(R.string.detals_referencia_num,response.getReferenciaNum()));
                }
                break;
            case TRASPASO_MISMO_BANCO_ABONO:
                builder.createLeaf(new TextData(R.string.details_concepto,response.getConcepto()));
                break;
            case SPEI_ABONO:
                builder.createLeaf(new TextData(R.string.details_concepto,response.getConcepto()));
                builder.createLeaf(new TextData(R.string.details_clave_rastreo,response.getClaveRastreo()));
                builder.createLeaf(new TextData(R.string.detals_referencia_num,response.getReferenciaNum()));

                break;
            case RETIRO_DE_DINERO_ATM:
                builder.createLeaf(new TextData(R.string.details_monto_retiro,StringUtils.getCurrencyValue(response.getImporte())));
                builder.createLeaf(new TextData(R.string.details_comision,StringUtils.getCurrencyValue(response.getComision())));
                builder.createLeaf(new TextData(R.string.details_iva,StringUtils.getCurrencyValue(response.getIVA())));
                break;
            case CASH_BACK:
                builder.createLeaf(new TextData(R.string.details_monto_retiro,StringUtils.getCurrencyValue(response.getImporte())));
                break;
            case COMISION:
                builder.createLeaf(new TextData(R.string.details_comision,StringUtils.getCurrencyValue(response.getComision())));
                builder.createLeaf(new TextData(R.string.details_iva,StringUtils.getCurrencyValue(response.getIVA())));
                break;
            case REEMBOLSO_ADQUIRIENTE:
                Double total = response.getTotal() + response.getComision() + response.getIVA();
                builder.createLeaf(new TextData(R.string.details_ventas_totales, StringUtils.getCurrencyValue(total)));
                builder.createLeaf(new TextData(R.string.details_comision,StringUtils.getCurrencyValue(response.getComision())));
                builder.createLeaf(new TextData(R.string.details_iva,StringUtils.getCurrencyValue(response.getIVA())));
                break;
            case DEVOLUCION:
                builder.createLeaf(new TextData(R.string.details_concepto,response.getConcepto()));
                break;
            case SIETE://7

                break;
            case COMPRA:

                break;
            case CONSULTA_ATM://10

                break;
            case COBRO_CON_TARJETA_DISPERSION_ADQ://13

                break;
        }

        if (TipoTransaccionPCODE.getTipoTransaccionById(response.getIdTipoTransaccion()) != DEVOLUCION){
            builder.createLeaf(new TextData(R.string.details_fecha,response.getFechaMovimiento()));
            builder.createLeaf(new TextData(R.string.details_hora,response.getHoraMovimiento().concat(" hrs")));
            builder.createLeaf(new TextData(R.string.details_autorizacion,response.getNumAutorizacion().trim()));
        }

    }

    public static void creatHeaderMovDetail(Context context, ViewGroup container, ItemMovements item){
        DetailBulder bulder = new DetailBulder(context, container);
        bulder.createDetailMov(item);
    }

    private static int getReferencuaTitleType(String ref) {
        String referencia = ref.replaceAll(" ", "").trim();
        int longitud = referencia.length();

        if (longitud == 10) {
            return R.string.txt_phone;
        } else if (longitud == 16) {
            return R.string.details_tarjeta;
        } else if (longitud == 11) {
            return R.string.details_cuenta;
        } else if (longitud == 18) {
            return R.string.details_cable;
        } else {
            return R.string.details_ferencia;
        }
    }
}
