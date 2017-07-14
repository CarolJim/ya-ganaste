package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;

import butterknife.ButterKnife;

/**
 * Created by flima on 03/04/2017.
 */

public class ErrorMessage extends RelativeLayout {

    //private ImageView imgError;
    private StyleTextView textMessage;

    public ErrorMessage(Context context) {
        super(context);
        init();
    }

    public ErrorMessage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ErrorMessage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.error_validation_message, this, true);
        textMessage = ButterKnife.findById(this, R.id.txtError);
        //textMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, App.getContext().getResources().getDimension(R.dimen.error_custom_validation_editext_size));
        //imgError = ButterKnife.findById(this, R.id.imgError);
    }

    /*public void setImageError(@DrawableRes int idResource){
        imgError.setVisibility(GONE);
        imgError.setImageResource(idResource);
    }*/

    public void setMessageText(String errorMessage) {
        textMessage.setText(errorMessage);
        setVisibilityImageError(true);
    }

    public void setVisibilityImageError(boolean isVisible) {
        //imgError.setVisibility(isVisible ? VISIBLE : INVISIBLE);
        textMessage.setVisibility(isVisible ? VISIBLE : INVISIBLE);
        if (!isVisible) textMessage.setText("");
    }

    public void alingCenter() {
        textMessage.setGravity(Gravity.CENTER);
    }
}
