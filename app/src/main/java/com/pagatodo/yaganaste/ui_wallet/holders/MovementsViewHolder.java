package com.pagatodo.yaganaste.ui_wallet.holders;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.TransaccionesSbResponse;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MovementsViewHolder extends RecyclerView.ViewHolder {

    private StyleTextView day;
    private StyleTextView month;
    private StyleTextView desc;
    private StyleTextView subDesc;
    private StyleTextView money;
    private ConstraintLayout constraintLayout;

    public MovementsViewHolder(View itemView) {
        super(itemView);
        this.constraintLayout = itemView.findViewById(R.id.item);
        this.day = itemView.findViewById(R.id.day_txt);
        this.month = itemView.findViewById(R.id.month_txt);
        this.desc = itemView.findViewById(R.id.description_txt);
        this.subDesc = itemView.findViewById(R.id.sub_title);
        this.money = itemView.findViewById(R.id.monto_txt);
    }

    @SuppressLint("SetTextI18n")
    public void bind(TransaccionesSbResponse item, boolean isPar){
        if (isPar){
            this.constraintLayout.setBackgroundResource(R.color.backgraund_wallet);
        }
        this.day.setText(converFormt(item.getFecha()));
        this.month.setText(getMonth(item.getFecha()));
        this.desc.setText(item.getDetalle());
        this.subDesc.setText(getFormatCard(item.getTarjeta()));
        this.money.setText(addZeros("" + item.getMonto()));
    }

    private String addZeros(String money){

        if (!money.contains("\\.")){
            return money + ".00";
        } else {
            return money;
        }
    }

    private String converFormt(String date){
        String[] oldstring = date.split("/");
        return oldstring[0];
    }


    private String getMonth(String date) {
        String[] strings = date.split("/");
        int month = Integer.parseInt(strings[1]);
        String mes = new DateFormatSymbols().getMonths()[month-1];
        String inicial = String.valueOf(mes.charAt(0));
        return inicial.toUpperCase() + mes.substring(1,3);
    }

    private String getFormatCard(String cad){
        char[] cadL;
        StringBuilder cadres = new StringBuilder();
        if (cad.length() == 16){
            cadL = cad.toCharArray();
            for (int i = 0; i<cadL.length; i++){
                cadres.append(cadL[i]);
                if (i == 3 || i == 7 || i == 11)
                cadres.append("-");
            }
            return cadres.toString();
        } else {
            return cad;
        }
    }


}
