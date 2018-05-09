package com.pagatodo.yaganaste.ui_wallet.holders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.customviews.CustomRadioButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RADIOBUTTON;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RAW;

public class ViewHolderMenuSegurity extends GenericHolder{

    public RelativeLayout relativeLayout;
    public ImageView raw;
    public StyleTextView title;
    public StyleTextView subtitle;
    public CustomRadioButton radioButtonNo;
    public CustomRadioButton radioButtonSi;
    public RadioGroup radioGroup;

    public ViewHolderMenuSegurity(View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        title = this.itemView.findViewById(R.id.title);
        subtitle = this.itemView.findViewById(R.id.subtitle);
        raw = this.itemView.findViewById(R.id.raw_item);
        relativeLayout = this.itemView.findViewById(R.id.item_menu);
        radioGroup = this.itemView.findViewById(R.id.radio_group);
        radioButtonNo = this.itemView.findViewById(R.id.radiobutton_no);
        radioButtonSi = this.itemView.findViewById(R.id.radiobutton_si);
        relativeLayout = this.itemView.findViewById(R.id.item_menu);

    }

    @Override
    public void bind(Object item, final OnClickItemHolderListener listener) {
        final OptionMenuItem itemOption = (OptionMenuItem) item;

        if (itemOption.getIndication() != null && itemOption.getIndication() == RAW) {
            title.setText(itemOption.getResourceTitle());
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(itemOption);
                }
            });
        }

        if (itemOption.getIndication() != null && itemOption.getIndication() == RADIOBUTTON) {
            subtitle.setVisibility(View.VISIBLE);
            title.setText(itemOption.getResourceTitle());
            subtitle.setText(itemOption.getSubtitle());
            raw.setVisibility(View.GONE);
            radioGroup.setVisibility(View.VISIBLE);
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
