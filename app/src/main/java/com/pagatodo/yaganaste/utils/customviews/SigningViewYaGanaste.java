package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;


/**
 * Created by jvazquez on 27/10/2016.
 */

public class SigningViewYaGanaste extends SigningView {

    private Context context;
    private StyleButton btnSendSignature;
    private boolean hasSignature = false;

    public SigningViewYaGanaste(Context context) {
        super(context);
        this.context = context;
    }

    public SigningViewYaGanaste(Context context, TextView tvFirmaAqui, StyleButton btnSendSignature) {
        super(context, tvFirmaAqui);
        this.context = context;
        this.btnSendSignature = btnSendSignature;

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (getSign() != null && getSign().getCurentSignStroke().getPoints().size() > 2) { // Si ya se inicio la firma
                    btnSendSignature.setBackgroundResource(R.drawable.button_rectangle_blue_selector);
                    hasSignature = true;
                }
                break;
            default:
                break;
        }
        return true;
    }

    public boolean hasSignature() {
        return hasSignature;
    }

    public void setHasSignature(boolean hasSignature) {
        this.hasSignature = hasSignature;
    }
}
