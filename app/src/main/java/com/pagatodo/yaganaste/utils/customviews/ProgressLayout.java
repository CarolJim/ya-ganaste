package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;

import butterknife.ButterKnife;

/**
 * Created by flima on 24/03/2017.
 */

public class ProgressLayout extends LinearLayout implements View.OnClickListener {

    private ImageView imgBlur;
    private TextView txtMessage;
    private FrameLayout frameProgresGif;

    public ProgressLayout(Context context) {
        super(context);
        init();
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.progress_layout, this, true);
        imgBlur = ButterKnife.findById(this, R.id.img_blur_progress);
        txtMessage = ButterKnife.findById(this, R.id.txtMessage);
        frameProgresGif = ButterKnife.findById(this, R.id.frameProgresGif);

       /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            BitmapDrawable drawable = (BitmapDrawable) imgBlur.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            Bitmap blurred = null;//second parametre is radius
            blurred = blurRenderScript(bitmap, 25);
            imgBlur.setImageBitmap(blurred);
        }*/

        setOnClickListener(this);
    }

    public void setTextMessage(String message) {
        txtMessage.setText(message);
    }

    /*public void setBackgroundColor(@DrawableRes int color) {
        setBackgroundResource(getResources().getColor(color));
    }*/

    public void setVisivilityImage(int visibility) {
        frameProgresGif.setVisibility(visibility);
    }


    @Override
    public void onClick(View v) {
        //No action to perform
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private Bitmap blurRenderScript(Bitmap smallBitmap, int radius) {

        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(App.getContext());

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;
    }

    private Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }
}
