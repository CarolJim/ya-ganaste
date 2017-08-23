package com.pagatodo.yaganaste.utils.customviews.yaganasteviews;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.DatosCupo;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

/**
 * Created by Jordan on 23/08/2017.
 */

public class CardCupoSelected extends TabViewElement {

    private SeekBar seekLineaCredito;
    private MontoTextView saldoDisponible;
    private MontoTextView montoTotalDepositar;
    private MontoTextView montoLineaCredito;
    private StyleTextView titleTotalDepositar;

    public CardCupoSelected(Context context) {
        this(context, null);
    }

    public CardCupoSelected(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardCupoSelected(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }

    private void setView() {
        View child = LayoutInflater.from(getContext()).inflate(R.layout.card_cupo_selected, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
        addView(child, params);

        seekLineaCredito = (SeekBar) findViewById(R.id.seekLineaCredito);
        saldoDisponible = (MontoTextView) findViewById(R.id.saldoDisponible);
        montoTotalDepositar = (MontoTextView) findViewById(R.id.montoTotalDepositar);
        montoLineaCredito = (MontoTextView) findViewById(R.id.montoLineaCredito);
        titleTotalDepositar = (StyleTextView) findViewById(R.id.titleTotalDepositar);

        seekLineaCredito.setEnabled(false);
        updateData();
    }

    @Override
    public void updateData() {
        DatosCupo datosCupo = SingletonUser.getInstance().getDatosCupo();
        double totalDepositar = Double.parseDouble(datosCupo.getTotalADepositar());
        double lineaCredito = Double.parseDouble(datosCupo.getLimiteDeCredito());
        double saldoDisp = Double.parseDouble(datosCupo.getSaldoDisponible());
        int progress;
        if (totalDepositar > 0) {
            if (lineaCredito > 0) {
                progress = (int) ((totalDepositar * 100) / lineaCredito);
            } else {
                progress = 100;
            }
        } else {
            int color = ContextCompat.getColor(getContext(), R.color.yellow);
            totalDepositar = Math.abs(totalDepositar);
            titleTotalDepositar.setText(getContext().getString(R.string.total_reembolsar));
            titleTotalDepositar.setTextColor(color);
            LayerDrawable ld = (LayerDrawable) seekLineaCredito.getProgressDrawable();
            ClipDrawable d1 = (ClipDrawable) ld.findDrawableByLayerId(R.id.progress);
            d1.setColorFilter(color, PorterDuff.Mode.SRC_IN);

            progress = (int) ((totalDepositar * 100) / saldoDisp);
        }

        seekLineaCredito.setProgress(progress);

        saldoDisponible.setText(StringUtils.getCurrencyValue(saldoDisp));
        montoTotalDepositar.setText(StringUtils.getCurrencyValue(totalDepositar));
        montoLineaCredito.setText(StringUtils.getCurrencyValue(lineaCredito));


    }
}
