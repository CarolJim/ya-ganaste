package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by flima on 03/04/2017.
 */

public class UploadDocumentView extends RelativeLayout {

    private CircleImageView circleImageView;
    private CircleImageView circleImageStatus;
    private ImageView imgCamera;

    public UploadDocumentView(Context context) {
        super(context);
        init();
    }

    public UploadDocumentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UploadDocumentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);

        /**
         * Se agrega una validacion para obtener la version de Android que usamos, para cargar un
         * layout alternativo en < Lolipop. Se hace el cambio porque el CircleImageView extiende de
         * ImageView y no de AppCompatImageView
         */
        // Check if we're running on Android 5.0 or higher
        if (Build.VERSION.SDK_INT >= 21) {
            li.inflate(R.layout.item_file, this, true);
        } else {
            // Layout alternativo para < Lolipop
            li.inflate(R.layout.item_file_4_4, this, true);
        }

        circleImageView = ButterKnife.findById(this, R.id.imgItemGalleryMark);
        circleImageStatus = ButterKnife.findById(this, R.id.imgItemGalleryStatus);
        imgCamera = ButterKnife.findById(this, R.id.imgItemGalleryPay);
    }

    /**
     * Centramos la imagen que recibimos. Para mostrar a corde a lo que necesitamos
     * @param mDrawable
     */
    public void setCenterDrawable(Drawable mDrawable){
        imgCamera.setImageDrawable(mDrawable);
    }

    public void setImageBitmap(Bitmap bitmap){
        imgCamera.setVisibility(GONE);
        circleImageView.setImageBitmap(bitmap);
        circleImageView.invalidate();
    }

    public void setStatusImage(Drawable bitmap){

        circleImageStatus.setImageDrawable(bitmap);
    }

    public void setVisibilityStatus(boolean visibilityStatus){
        circleImageStatus.setVisibility(visibilityStatus ? VISIBLE : GONE);
        circleImageStatus.invalidate();
    }

}
