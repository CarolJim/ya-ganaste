package com.pagatodo.yaganaste.ui_wallet.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class BoardIndicationsView extends LinearLayout {

    private Context context;
    private MontoTextView txtMonto;
    private StyleTextView tipoSaldo;
    private ImageView reload;

    public BoardIndicationsView(Context context) {
        super(context);
        init(context);
    }

    public BoardIndicationsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BoardIndicationsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        View view = inflate(getContext(), R.layout.board_indications_layout, this);
        this.txtMonto = view.findViewById(R.id.txt_monto);
        this.tipoSaldo = view.findViewById(R.id.tipo_saldo);
        this.reload = view.findViewById(R.id.img_reload);
    }

    public void setTextMonto(String txt){
        this.txtMonto.setText(txt);
    }

    public void setTextSaldo(int txt){
        this.tipoSaldo.setText(txt);
    }

    public void setReloadVisibility(int v){
        this.reload.setVisibility(v);
    }

    public void setreloadOnclicklistener(View.OnClickListener listener){
        this.reload.setOnClickListener(listener);
    }


}
