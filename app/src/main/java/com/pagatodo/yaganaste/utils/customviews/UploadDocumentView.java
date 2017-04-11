package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
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
        li.inflate(R.layout.item_file, this, true);
        circleImageView = ButterKnife.findById(this, R.id.imgItemGalleryMark);
        circleImageStatus = ButterKnife.findById(this, R.id.imgItemGalleryStatus);
        imgCamera = ButterKnife.findById(this, R.id.imgItemGalleryPay);
    }

    public void setImageBitmap(Bitmap bitmap){
        imgCamera.setVisibility(GONE);
        circleImageView.setImageBitmap(bitmap);
        circleImageView.invalidate();
    }

    public void setStatusImage(Bitmap bitmap){

        circleImageStatus.setImageBitmap(bitmap);
    }

    public void setVisibilityStatus(boolean visibilityStatus){
        circleImageStatus.setVisibility(visibilityStatus ? VISIBLE : GONE);
        circleImageStatus.invalidate();
    }

}
