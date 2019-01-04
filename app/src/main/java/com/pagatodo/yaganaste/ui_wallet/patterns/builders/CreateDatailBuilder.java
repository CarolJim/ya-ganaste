package com.pagatodo.yaganaste.ui_wallet.patterns.builders;

import android.content.Context;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui_wallet.pojos.TextData;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.StringUtils;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.COMISION;
import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.COMPRA;
import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.DEVOLUCION;
import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.RETIRO_DE_DINERO_ATM;
import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.SPEI_ABONO;
import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.TRASPASO_MISMO_BANCO_ABONO;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CANCELADO;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_POR_REMBOLSAR;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_REMBOLSADO;

/**

 */

public class CreateDatailBuilder {


    public static void createByType(Context context, ViewGroup container, MovimientosResponse response) {
        DetailBulder builder = new DetailBulder(context, container);
        boolean isspei = false;
        switch (TipoTransaccionPCODE.getTipoTransaccionById(response.getIdTipoTransaccion())) {
            case RECARGA:
                if (response.getIdComercio() == 7) {
                    builder.createLeaf(new TextData(R.string.details_comision, StringUtils.getCurrencyValue(response.getComision())));
                    builder.createLeaf(new TextData(R.string.details_iva, StringUtils.getCurrencyValue(response.getIVA())));
                    builder.createLeaf(new TextData(R.string.details_iave_pase, response.getReferencia()));
                } else {
                    builder.createLeaf(new TextData(R.string.txt_phone, response.getReferencia()));
                }
                break;
            case PAGO_DE_SERVICIO:
                builder.createLeaf(new TextData(R.string.details_cargo_servicio, StringUtils.getCurrencyValue(response.getComision())));
                builder.createLeaf(new TextData(R.string.details_iva, StringUtils.getCurrencyValue(response.getIVA())));
                builder.createLeaf(new TextData(R.string.details_ferencia, response.getReferencia()));
                break;
            case TRASPASO_MISMO_BANCO_CARGO:
                builder.createLeaf(new TextData(getReferencuaTitleType(response.getReferencia()), response.getReferencia()));
                builder.createLeaf(new TextData(R.string.details_concepto, response.getConcepto()));
                break;
            case SPEI_CARGO:
                //  builder.createLeaf(new TextData(R.string.details_comision,StringUtils.getCurrencyValue(response.getComision())));
                //  builder.createLeaf(new TextData(R.string.details_iva,StringUtils.getCurrencyValue(response.getIVA())));

                builder.createLeaf(new TextData(R.string.details_bank, response.getComercio()));
                builder.createLeaf(new TextData(R.string.details_destinatario, response.getBeneficiario()));
                if (!response.getReferencia().equals("")) {
                    builder.createLeaf(new TextData(getReferencuaTitleType(response.getReferencia()), response.getReferencia()));
                }

                builder.createLeaf(new TextData(R.string.details_concepto, response.getConcepto()));
                if (!response.getClaveRastreo().equals("")) {
                    builder.createLeaf(new TextData(R.string.details_clave_rastreo, response.getClaveRastreo()));
                }
                if (!response.getReferenciaNum().trim().equals("")) {
                    builder.createLeaf(new TextData(R.string.detals_referencia_num, response.getReferenciaNum()));
                }
                isspei = true;
                break;
            case TRASPASO_MISMO_BANCO_ABONO:
                builder.createLeaf(new TextData(R.string.details_concepto, response.getConcepto()));
                break;
            case SPEI_ABONO:
                builder.createLeaf(new TextData(R.string.details_concepto, response.getConcepto()));
                //builder.createLeaf(new TextData(R.string.details_clave_rastreo, response.getClaveRastreo()));
                //builder.createLeaf(new TextData(R.string.detals_referencia_num, response.getReferenciaNum()));

                break;
            case RETIRO_DE_DINERO_ATM:
                builder.createLeaf(new TextData(R.string.details_monto_retiro, StringUtils.getCurrencyValue(response.getImporte())));
                //builder.createLeaf(new TextData(R.string.details_comision, StringUtils.getCurrencyValue(response.getComision())));
                //builder.createLeaf(new TextData(R.string.details_iva, StringUtils.getCurrencyValue(response.getIVA())));
                break;
            case CASH_BACK:
                builder.createLeaf(new TextData(R.string.details_monto_retiro, StringUtils.getCurrencyValue(response.getImporte())));
                break;
            case COMISION:
            case COMISION_ADMIN_CUENTA:
                builder.createLeaf(new TextData(R.string.details_comision, StringUtils.getCurrencyValue(response.getComision())));
                builder.createLeaf(new TextData(R.string.details_iva, StringUtils.getCurrencyValue(response.getIVA())));
                break;
            case REEMBOLSO_ADQUIRIENTE:
                Double total = response.getTotal() + response.getComision() + response.getIVA();
                builder.createLeaf(new TextData(R.string.details_ventas_totales, StringUtils.getCurrencyValue(total)));
                break;
            case DEVOLUCION:
                builder.createLeaf(new TextData(R.string.details_concepto, response.getConcepto()));
                break;
            case SIETE://7

                break;
            case COMPRA:
                builder.createLeaf(new TextData(R.string.details_monto, StringUtils.getCurrencyValue(response.getImporte())));
                break;
            case CONSULTA_ATM://10

                break;
            case COBRO_CON_TARJETA_DISPERSION_ADQ://13

                break;
        }

        if (TipoTransaccionPCODE.getTipoTransaccionById(response.getIdTipoTransaccion()) != DEVOLUCION) {
            if (!isspei && TipoTransaccionPCODE.getTipoTransaccionById(response.getIdTipoTransaccion()) != COMISION
                    && TipoTransaccionPCODE.getTipoTransaccionById(response.getIdTipoTransaccion()) != COMPRA
                    && TipoTransaccionPCODE.getTipoTransaccionById(response.getIdTipoTransaccion()) != SPEI_ABONO
                    && TipoTransaccionPCODE.getTipoTransaccionById(response.getIdTipoTransaccion()) != TRASPASO_MISMO_BANCO_ABONO
                    && TipoTransaccionPCODE.getTipoTransaccionById(response.getIdTipoTransaccion()) != RETIRO_DE_DINERO_ATM) {
                builder.createLeaf(new TextData(R.string.details_comision, StringUtils.getCurrencyValue(response.getComision())));
                builder.createLeaf(new TextData(R.string.details_iva, StringUtils.getCurrencyValue(response.getIVA())));
            }
            builder.createLeaf(new TextData(R.string.details_fecha, response.getFechaMovimiento()));
            builder.createLeaf(new TextData(R.string.details_hora, response.getHoraMovimiento().concat(" hrs")));
            builder.createLeaf(new TextData(R.string.details_autorizacion, response.getNumAutorizacion().trim()));
        }
        isspei = false;
    }

    public static void createByTypeAdq(Context context, ViewGroup container, DataMovimientoAdq response) {
        boolean isComerioUyU = false;
        try {
            isComerioUyU = new DatabaseManager().isComercioUyU(RequestHeaders.getIdCuentaAdq());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        DetailBulder builder = new DetailBulder(context, container);
        //txtComisionDescripcion.setText(StringUtils.getCurrencyValue(ticket.getComision()).replace("$", "$ "));
        builder.createLeaf(new TextData(R.string.details_comision, StringUtils.getCurrencyValue(response.getComision())));
        //txtIVADescripcion.setText(StringUtils.getCurrencyValue(ticket.getComisionIva()).replace("$", "$ "));
        builder.createLeaf(new TextData(R.string.details_iva, StringUtils.getCurrencyValue(response.getComisionIva())));
        //txtRefernciaDescripcion.setText(ticket.getReferencia());
        builder.createLeaf(new TextData(R.string.details_tarjeta, response.getReferencia()));
        //txtConceptoDescripcion.setText(ticket.getConcepto());
        builder.createLeaf(new TextData(R.string.details_concepto, response.getConcepto()));
        //txtFechaDescripcion.setText(DateUtil.getBirthDateCustomString(calendar));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getAdquirenteMovementDate(response.getFecha()));
        builder.createLeaf(new TextData(R.string.details_fecha, DateUtil.getBirthDateCustomString(calendar)));
        ////txtHoraDescripcion.setText(fecha[1] + " hrs");
        String[] fecha = response.getFecha().split(" ");
        builder.createLeaf(new TextData(R.string.details_hora, fecha[1] + " hrs"));
        //txtAutorizacionDescripcion.setText(ticket.getNoAutorizacion().trim());
        builder.createLeaf(new TextData(R.string.details_autorizacion, response.getNoAutorizacion().trim()));

        switch (response.getEstatus()) {
            case ESTATUS_CANCELADO:
                builder.createLeaf(new TextData(R.string.details_status, context.getString(R.string.status_cancelado)));
                break;
            case ESTATUS_POR_REMBOLSAR:
                if (!isComerioUyU)
                    builder.createLeaf(new TextData(R.string.details_status, context.getString(R.string.status_por_rembolsar)));
                //txtEstatusDescripcion.setText(context.getString(R.string.status_por_rembolsar));
                break;
            case ESTATUS_REMBOLSADO:
                if (!isComerioUyU)
                    builder.createLeaf(new TextData(R.string.details_status, context.getString(R.string.status_rembolsado)));
                //txtEstatusDescripcion.setText(context.getString(R.string.status_rembolsado));
                break;
        }
    }

    public static void creatHeaderMovDetail(Context context, ViewGroup container,
                                            ItemMovements item, boolean isAdq) {
        DetailBulder bulder = new DetailBulder(context, container);
        bulder.createDetailMov(item, isAdq);
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
