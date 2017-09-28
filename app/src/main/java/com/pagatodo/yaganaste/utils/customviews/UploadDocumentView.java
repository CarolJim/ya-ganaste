package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by flima on 03/04/2017.
 */

public class UploadDocumentView extends RelativeLayout {

//    @BindView(R.id.fragment_list_opciones_name)
//    TextView tv_name;

    private CircleImageView circleImageView;
    private CircleImageView circleImageStatus;
    private ImageView imgCamera;
    private int isStatus;

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

    private void init() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);

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

        circleImageView = ButterKnife.findById(this, R.id.imgItemGalleryMark); // Icono total
        circleImageStatus = ButterKnife.findById(this, R.id.imgItemGalleryStatus); // Icono Status
        imgCamera = ButterKnife.findById(this, R.id.imgItemGalleryPay); // Icono central
    }

    /**
     * Centramos la imagen que recibimos. Para mostrar a corde a lo que necesitamos
     *
     * @param mDrawable
     */
    public void setCenterDrawable(int mDrawable) {
        imgCamera.setImageResource(0);
        imgCamera.setImageResource(mDrawable);
    }

    public void setStatusVisibility(boolean mVisibility) {
        if (mVisibility) {
            imgCamera.setVisibility(VISIBLE);
        } else {
            imgCamera.setVisibility(GONE);
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        imgCamera.setVisibility(GONE);
        circleImageView.setImageBitmap(bitmap);
        circleImageView.invalidate();
    }

    public void setImageDrawable(Drawable mDrawable) {
        // imgCamera.setVisibility(GONE);
        circleImageView.setImageDrawable(mDrawable);
        circleImageView.invalidate();
    }

    public void setBackgroundResource(int id) {
        circleImageView.setBackgroundResource(id);
        circleImageView.invalidate();
    }

    public void setStatusImage(Drawable bitmap) {

        circleImageStatus.setImageDrawable(bitmap);
    }

    public void setVisibilityStatus(boolean visibilityStatus) {
        circleImageStatus.setVisibility(visibilityStatus ? VISIBLE : GONE);
        circleImageStatus.invalidate();
    }

    public int getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(int isStatus) {
        this.isStatus = isStatus;
    }

    public CircleImageView getCircleImageView() {
        return circleImageView;
    }

    public void setNewHW(int mWidth, int mHeight) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layoutImg);
        LayoutParams params = (LayoutParams) relativeLayout.getLayoutParams();
        params.width = mWidth;
        params.height = mHeight;
        relativeLayout.setLayoutParams(params);
    }
}
