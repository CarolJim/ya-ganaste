package com.pagatodo.yaganaste.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

/**
 * Created by flima on 09/03/2017.
 * Update Francisco Manzo
 * Encarga de hacer las operaciones de mostrar el numero y despues cambiarlo por un Bullet Azul.
 * Se utiliza un EditTExt oculto, y los numeros de nuestras en 4 TextView diferentes, que sirven de
 * vista al usuario, en el background, se usa el EditText oculto para procesar el valor*/

public class AsignarNipTextWatcher implements TextWatcher {

    TextView tv1Num;
    TextView tv2Num;
    TextView tv3Num;
    TextView tv4Num;
    String newText = "";
    String oldText = "";
    CustomValidationEditText etGen;
    Bitmap bitmapBullet;

    private String TAG = getClass().getSimpleName();

    public AsignarNipTextWatcher(CustomValidationEditText etGen, TextView tv1Num, TextView tv2Num,
                                 TextView tv3Num, TextView tv4Num) {
        this.etGen = etGen;
        this.tv1Num = tv1Num;
        this.tv2Num = tv2Num;
        this.tv3Num = tv3Num;
        this.tv4Num = tv4Num;
        bitmapBullet = BitmapFactory.decodeResource(App.getContext().getResources(),
                R.drawable.asignar_nip_bullet);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(final Editable s) {
        newText = s.toString();
        if (newText.length() > oldText.length()) {
            final int countString = etGen.getText().toString().length();
            switch (countString) {
                case 1:
                    tv1Num.setText("" + s.charAt(s.toString().length() - 1));
                    break;
                case 2:
                    tv2Num.setText("" + s.charAt(s.toString().length() - 1));
                    break;
                case 3:
                    tv3Num.setText("" + s.charAt(s.toString().length() - 1));
                    break;
                case 4:
                    tv4Num.setText("" + s.charAt(s.toString().length() - 1));
                    break;
            }
            oldText = s.toString();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    SpannableStringBuilder ssb = new SpannableStringBuilder(" "); // 20
                    ssb.setSpan(new ImageSpan(bitmapBullet), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    switch (countString) {
                        case 1:
                            tv1Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            break;
                        case 2:
                            tv2Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            break;
                        case 3:
                            tv3Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            break;
                        case 4:
                            tv4Num.setText(ssb, TextView.BufferType.SPANNABLE);
                            break;
                    }
                }
            }, 500);
        } else {
            // Proceso de borrado de numeros,
            int countString = etGen.getText().toString().length();
            switch (countString) {
                case 0:
                    tv1Num.setText("");
                    break;
                case 1:
                    tv2Num.setText("");
                    break;
                case 2:
                    tv3Num.setText("");
                    break;
                case 3:
                    tv4Num.setText("");
                    break;
            }
            oldText = s.toString();
        }
    }
}