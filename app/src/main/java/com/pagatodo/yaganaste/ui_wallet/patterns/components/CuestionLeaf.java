package com.pagatodo.yaganaste.ui_wallet.patterns.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Component;
import com.pagatodo.yaganaste.utils.customviews.CustomRadioButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class CuestionLeaf implements Component{

    private Context context;
    private View itemView;
    private boolean checkDefault;
    private RadioGroup.OnCheckedChangeListener listener;


    public CuestionLeaf(Context context, int resText, boolean checkDefault, RadioGroup.OnCheckedChangeListener listener) {
        this.context = context;
        this.checkDefault = checkDefault;
        this.listener = listener;
        setContent(resText);
    }

    @Override
    public void add(Component component) {

    }

    @Override
    public void setContent(Object item) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        itemView = inflater.inflate(R.layout.cuestion_layout, layout, false);
        CustomRadioButton radioButtonNo = itemView.findViewById(R.id.radioBtnPublicServantNo);
        CustomRadioButton radioButtonSi = itemView.findViewById(R.id.radioBtnPublicServantYes);
        StyleTextView textView = itemView.findViewById(R.id.textPublicServant);
        RadioGroup radioGroup = itemView.findViewById(R.id.radioPublicServant);
        radioGroup.setOnCheckedChangeListener(listener);

        textView.setText((int) item);
        if (this.checkDefault) {
            radioButtonSi.setChecked(true);
        } else {
            radioButtonNo.setChecked(true);
        }
    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(itemView);
    }
}
