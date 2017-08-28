package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;

import butterknife.ButterKnife;

/**
 * Created by Tato on 28/08/17.
 */

public class ReferenciaView extends RelativeLayout {

    private ImageView imagenStatus;

    public static final int ESTADO_REVISION  = 0;
    public static final int ESTADO_APROVADO  = 1;
    public static final int ESTADO_RECHAZADO = 2;

    public ReferenciaView(Context context) {
        super(context);
        init();
    }

    public ReferenciaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReferenciaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.referencia_view, this, true);

        imagenStatus = ButterKnife.findById(this, R.id.imgStatus);

        /*
        circleImageView = ButterKnife.findById(this, R.id.imgItemGalleryMark); // Icono total
        circleImageStatus = ButterKnife.findById(this, R.id.imgItemGalleryStatus); // Icono Status
        imgCamera = ButterKnife.findById(this, R.id.imgItemGalleryPay); // Icono central
        */
    }


    public void setStatus(int status){
        switch (status) {
            case ESTADO_REVISION:
                imagenStatus.setBackgroundResource(R.drawable.ic_usuario_azul);
                break;
            case ESTADO_APROVADO:
                imagenStatus.setBackgroundResource(R.drawable.ic_usuario_verde);
                break;
            case ESTADO_RECHAZADO:
                imagenStatus.setBackgroundResource(R.drawable.ic_usuario_rojo);
                break;
        }

    }

}
