package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.R;

/**
 * Created by Jordan on 22/08/2017.
 */

public enum LandingActivitiesEnum {


    PANTALLA_PRINCIPAL_EMISOR(1, R.drawable.img_couch_em_back,
            R.drawable.img_couch_em_1, R.drawable.img_couch_em_2,
            R.drawable.img_couch_em_3, R.drawable.img_couch_em_4,
            R.drawable.img_couch_em_5),

    INICIO_SESION_ADQUIRENTE(2, R.drawable.img_couch_quick_adq_1, R.drawable.img_couch_quick_adq_1),

    INICIO_SESION_EMISOR(3, R.drawable.img_couch_quick_em_1, R.drawable.img_couch_quick_em_1),

    PANTALLA_COBROS(4, R.drawable.img_couch_em_back,
            R.drawable.coachmark_adquirente_1,
            R.drawable.coachmark_adquirente_2,
            R.drawable.coachmark_adquirente_3,
            R.drawable.coachmark_adquirente_4,
            R.drawable.coachmark_adquirente_5),

    INICIO_SESION_EMISOR_2(5, R.drawable.img_couch_qem_back, R.drawable.img_couch_quick_em_3),

    INICIO_EDITAR_FAVORITOS(6, R.drawable.img_couch_edit_favorites_1, R.drawable.img_couch_edit_favorites_1);


    private int id;
    private int backImage;
    private int[] drawable;

    LandingActivitiesEnum(int id, int backImage, int... drawable) {
        this.id = id;
        this.backImage = backImage;
        this.drawable = drawable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBackImage() {
        return backImage;
    }

    public void setBackImage(int backImage) {
        this.backImage = backImage;
    }

    public int[] getDrawable() {
        return drawable;
    }

    public void setDrawable(int[] drawable) {
        this.drawable = drawable;
    }
}
