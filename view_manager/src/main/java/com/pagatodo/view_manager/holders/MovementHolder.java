package com.pagatodo.view_manager.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.components.CurrencyFormat;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.MovementDataHolder;

import androidx.annotation.NonNull;

public class MovementHolder extends GenericHolder<MovementDataHolder> {

    private TextView textDay;
    private TextView textMonth;
    private TextView textMovment;
    private TextView textReference;
    private CurrencyFormat textAmount;
    private ImageView imageState;

    public MovementHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.textDay = this.itemView.findViewById(R.id.text_day);
        this.textMonth = this.itemView.findViewById(R.id.text_month);
        this.textMovment = this.itemView.findViewById(R.id.text_movement);
        this.textReference = this.itemView.findViewById(R.id.text_reference);
        this.textAmount = this.itemView.findViewById(R.id.text_amount);
        this.imageState = this.itemView.findViewById(R.id.image_state);
    }

    @Override
    public void bind(MovementDataHolder item, OnHolderListener<MovementDataHolder> listener) {
        this.textDay.setText(item.getDay());
        this.textMonth.setText(item.getMonth());
        this.textMovment.setText(item.getMovement());
        this.textReference.setText(item.getReferencia());
        this.textAmount.setMoney(item.getAmount());
        this.imageState.setImageDrawable(item.getResImage());

        if (listener != null){
            itemView.setOnClickListener(v -> listener.onClickView(item,itemView));
        }
    }
}
