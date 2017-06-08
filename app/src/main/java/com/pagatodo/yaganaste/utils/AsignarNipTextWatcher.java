package com.pagatodo.yaganaste.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Handler;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.AlignmentSpan;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

/**
 * Created by flima on 09/03/2017.
 * Update Francisco Manzo
 * Encarga de hacer las operaciones de mostrar el numero y despues cambiarlo por un Bullet Azul.
 * Se utiliza un EditTExt oculto, y los numeros de nuestras en 4 TextView diferentes, que sirven de
 * vista al usuario, en el background, se usa el EditText oculto para procesar el valor
 */

public class AsignarNipTextWatcher implements TextWatcher {

    TextView tv1Num;
    TextView tv2Num;
    TextView tv3Num;
    TextView tv4Num;
    String newText = "";
    String oldText = "";
    CustomValidationEditText etGen;
    Bitmap bitmapBullet;
    ImageView asignar_iv1;

    private String TAG = getClass().getSimpleName();

    public AsignarNipTextWatcher(CustomValidationEditText etGen, TextView tv1Num, TextView tv2Num,
                                 TextView tv3Num, TextView tv4Num) {
        this.etGen = etGen;
        this.tv1Num = tv1Num;
        this.tv2Num = tv2Num;
        this.tv3Num = tv3Num;
        this.tv4Num = tv4Num;

        /*Log.d("ds","WNIP "+tv1Num.getWidth());
        float textSize = tv1Num.getTextSize();
        int textSizeInt = (int) textSize;
         Log.d("ds","textSizeInt "+ textSizeInt);*/

        bitmapBullet = BitmapFactory.decodeResource(App.getContext().getResources(),
                R.drawable.base_bullet_blue);
        int medidaTextSize = 0;

        /**
         * Obtenemos la resolucion de la pantalla y enviamos la medida necesaria
         */
        DisplayMetrics metrics = App.getContext().getResources().getDisplayMetrics();
        switch(metrics.densityDpi){
            case DisplayMetrics.DENSITY_LOW:
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                medidaTextSize = obtenerMedidas(tv1Num, 2);
                break;
            case DisplayMetrics.DENSITY_HIGH:
                medidaTextSize = obtenerMedidas(tv1Num, 3);
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                    medidaTextSize = obtenerMedidas(tv1Num, 4);
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                medidaTextSize = obtenerMedidas(tv1Num, 5);
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                medidaTextSize = obtenerMedidas(tv1Num, 5);
                break;
            default:
                medidaTextSize = obtenerMedidas(tv1Num, 5);
                break;
        }

        bitmapBullet = Bitmap.createScaledBitmap(bitmapBullet, medidaTextSize, medidaTextSize, true);
    }

    /**
     * Se encarga de obtener las medidas de la letra, y multiplicar por el tamaño asignado por pantalla
     * @param mTextView
     * @param mInt
     * @return
     */
    public int obtenerMedidas(TextView mTextView, int mInt) {
        Paint paint = new Paint();
        paint.setTextSize(mTextView.getTextSize());
        return ((int) (-paint.ascent() + paint.descent() * mInt));
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
                    ssb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
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
            }, 300);
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