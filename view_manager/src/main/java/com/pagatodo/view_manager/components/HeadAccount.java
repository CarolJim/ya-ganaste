package com.pagatodo.view_manager.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.CircleTransform;
import com.pagatodo.view_manager.controllers.LauncherHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.HeadAccountData;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;

public class HeadAccount extends LinearLayout implements LauncherHolder<HeadAccountData> {

    private View rootView;
    private RelativeLayout circlePersonContent;
    private ImageView circlePersonUrl;
    private ImageView circlePersonImage;
    private TextView txtIniciales;
    private TextView txtName;
    private TextView txtReference;
    private TextView txtLabelTag;

    public HeadAccount(Context context) {
        super(context);
        initMain(null);
    }

    public HeadAccount(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initMain(attrs);
    }

    public HeadAccount(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMain(attrs);
    }

    @SuppressLint("SetTextI18n")
    private void initMain(AttributeSet attrs){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        rootView = inflater.inflate(R.layout.head_account,this,false);
        init();
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.HeadAccount,
                    0, 0);

            try {
                String resText = a.getString(R.styleable.HeadAccount_labelHeadAccount);
                boolean isVisible = a.getBoolean(R.styleable.HeadAccount_labeVisibility,true);
                if (isVisible){
                    txtLabelTag.setVisibility(VISIBLE);
                } else {
                    txtLabelTag.setVisibility(GONE);
                }
                if (resText != null){
                    txtLabelTag.setText(resText);
                }
            }finally {
                a.recycle();
            }


        }
        addView(rootView);
    }

    @Override
    public void init() {
        circlePersonContent = rootView.findViewById(R.id.circle_person_content);
        circlePersonImage = rootView.findViewById(R.id.circle_person_image);
        circlePersonUrl = rootView.findViewById(R.id.circle_person);
        txtIniciales = rootView.findViewById(R.id.txtIniciales);
        txtName = rootView.findViewById(R.id.label_name);
        txtReference = rootView.findViewById(R.id.label_reference);
        txtLabelTag = rootView.findViewById(R.id.label_tag);
    }

    @Override
    public void bind(HeadAccountData item, OnHolderListener<HeadAccountData> listener) {
        txtName.setText(item.getName());
        txtReference.setText(item.getReference());
        if (!item.getUrlImage().isEmpty()){
            setImageURL(item.getUrlImage());
            circlePersonUrl.setVisibility(VISIBLE);
            circlePersonContent.setVisibility(GONE);
        } else {
            setImageDrawable(item.getName(),item.getColorMarca());
            circlePersonUrl.setVisibility(GONE);
            circlePersonContent.setVisibility(VISIBLE);
        }
    }

    @Override
    public void inflate(ViewGroup layout) {
        this.addView(layout);
    }

    @Override
    public View getView() {
        return this.rootView;
    }



    public void setImageURL(String url){
        Picasso.get().load(url)
                .transform(new CircleTransform()).into(circlePersonUrl);
    }

    public void setImageDrawable(String name, String colorMarca){
        txtIniciales.setVisibility(View.VISIBLE);
        txtIniciales.setText(getIniciales(name));
        GradientDrawable gd = createCircleDrawable(colorMarca);
        circlePersonImage.setImageDrawable(gd);
    }

    private GradientDrawable createCircleDrawable(String colorBackground) {
        int fillColor = Color.parseColor(colorBackground);
        GradientDrawable gD = new GradientDrawable();
        gD.setColor(fillColor);
        gD.setShape(GradientDrawable.OVAL);
        //circlePersonImage.setBackground(gD);
        return gD;
    }

    /*public static float dpToPx(View view, float dipValue) {
        DisplayMetrics metrics = view.getContext().getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }*/

    private String getIniciales(String fullName) {
        if (fullName.trim().length() == 1) {
            return fullName.substring(0, 1).toUpperCase();
        }

        String[] spliName = fullName.trim().split(" ");
        String sIniciales;
        if (spliName.length > 1) {
            sIniciales = spliName[0].substring(0, 1) + spliName[1].substring(0, 1).toUpperCase();
            return sIniciales;
        }

        if (fullName.trim().length() > 1) {
            return fullName.substring(0, 2).toUpperCase();
        }
        return "";
    }

}
