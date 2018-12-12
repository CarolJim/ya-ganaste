package com.pagatodo.yaganaste.modules.holders;

import android.view.View;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.data.QrItem;
import com.pagatodo.yaganaste.modules.patterns.GenericHolder;
import com.pagatodo.yaganaste.modules.patterns.OnHolderListener;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class QrHolder extends GenericHolder<QrItem> {

    private StyleTextView nameQr;
    private StyleTextView plateQr;
    private ImageView imageQr;

    public QrHolder(View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.imageQr = itemView.findViewById(R.id.image_qr);
        this.nameQr = itemView.findViewById(R.id.text_name_qr);
        this.plateQr = itemView.findViewById(R.id.text_qr_plate);
    }

    @Override
    public void bind(QrItem item, OnHolderListener<QrItem> listener) {
        if(item.getResImage() == R.drawable.qr_code) {
            this.imageQr.setImageResource(item.getResImage());
            this.nameQr.setText(item.getQr().getAlias());
            if (!item.getQr().getPlate().isEmpty()) {
                this.plateQr.setText(item.getQr().getPlate().substring(8));
            }
        } else {
            this.imageQr.setImageResource(item.getResImage());
            this.imageQr.setScaleType(ImageView.ScaleType.CENTER);
            this.imageQr.setBackgroundResource(R.drawable.border_cutter);
            this.plateQr.setText(item.getQr().getPlate());
        }
        if (listener != null){
            this.itemView.setOnClickListener(view -> listener.onClickItem(item));
        }
    }

}
