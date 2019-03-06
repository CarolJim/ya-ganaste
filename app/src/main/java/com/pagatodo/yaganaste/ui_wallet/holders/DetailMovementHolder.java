package com.pagatodo.yaganaste.ui_wallet.holders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.concurrent.ExecutionException;

public class DetailMovementHolder extends GenericHolder {

    private StyleTextView day;
    private StyleTextView month;
    private StyleTextView title;
    private StyleTextView subtitle;
    private MontoTextView monto;
    private ImageView upDown;
    private boolean isAdq;

    public DetailMovementHolder(View itemView, boolean isAdq) {
        super(itemView);
        this.isAdq = isAdq;
        init();
    }

    @Override
    public void init() {
        this.day = this.itemView.findViewById(R.id.txt_item_mov_date);
        this.month = this.itemView.findViewById(R.id.txt_item_mov_month);
        this.title = this.itemView.findViewById(R.id.txtTituloDescripcion);
        this.subtitle = this.itemView.findViewById(R.id.txtSubTituloDetalle);
        this.monto = this.itemView.findViewById(R.id.txt_monto);
        this.upDown = this.itemView.findViewById(R.id.up_down);
    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {
        ItemMovements response = (ItemMovements) item;
        this.title.setSelected(true);
        this.day.setText(response.getDate());
        this.month.setText(response.getMonth());
        this.title.setText(response.getTituloDescripcion());
        this.subtitle.setText(response.getSubtituloDetalle());
        this.monto.setText(StringUtils.getCurrencyValue(response.getMonto()));
        boolean isComerioUyU = false;
        try {
            isComerioUyU = new DatabaseManager().isComercioUyU(RequestHeaders.getIdCuentaAdq());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.getColor() == R.color.redColorNegativeMovements){
            if(!isAdq) {
                if (!isComerioUyU)
                upDown.setBackgroundResource(R.drawable.down_red);
            } else {
                upDown.setBackgroundResource(R.drawable.down);
            }
        }


        if (isAdq){
            if (response.getColor() == R.color.greenColorPositiveMovements) {
                if (!isComerioUyU)
                upDown.setBackgroundResource(R.drawable.upadq);
            }
        }else {
            if (response.getColor() == R.color.greenColorPositiveMovements) {
                if (!isComerioUyU)
                upDown.setBackgroundResource(R.drawable.up);
            }
        }

        if (response.getColor() == R.color.colorAccent){
            if (!isComerioUyU)
            upDown.setBackgroundResource(R.drawable.ico_idle);
        }

        if (response.getColor() == R.color.redColorNegativeMovementsCancel){
            if (!isComerioUyU)
            upDown.setBackgroundResource(R.drawable.down);
        }

    }

    @Override
    public View getView() {
        return this.itemView;
    }

    @Override
    public void inflate(ViewGroup layout) {

    }
}
