package com.pagatodo.view_manager.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.annotation.Nullable;

public class ImageInitials extends LinearLayout {

    private ImageView imageBackgraund;
    private TextView initials;

    public ImageInitials(Context context) {
        super(context);
        init(null);
    }

    public ImageInitials(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ImageInitials(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.initial_images,this,false);
        imageBackgraund = rootView.findViewById(R.id.circle_person_image);
        initials = rootView.findViewById(R.id.txtIniciales);
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.ImageInitials,
                    0, 0);
            try {
                String resText = a.getString(R.styleable.ImageInitials_nameComplete);
                if (resText != null) {
                    this.setInitials(resText);
                }
                int color = a.getColor(R.styleable.ImageInitials_color, 0);
                setDrawableCircle("#00a1e1");
            } finally {
                a.recycle();
                //this.setDrawableCircle("#00a1e1");
                //this.setInitials("Ya Ganaste");
            }
        } else {
            this.setDrawableCircle("#00a1e1");
            this.setInitials("Ya Ganaste");
        }
        this.addView(rootView);
    }

    public void setDrawableCircle(String color){
        GradientDrawable gd = createCircleDrawable(color);
        imageBackgraund.setImageDrawable(gd);
    }

    public void setInitials(String name){
        this.initials.setVisibility(VISIBLE);
        this.initials.setText(getIniciales(name));
    }

    private GradientDrawable createCircleDrawable(String colorBackground) {
        int fillColor = Color.parseColor(colorBackground);
        GradientDrawable gD = new GradientDrawable();
        gD.setColor(fillColor);
        gD.setShape(GradientDrawable.OVAL);
        //circlePersonImage.setBackground(gD);
        return gD;
    }

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

    public void setImageURL(String imageURL){
        Picasso.get().load(imageURL).into(imageBackgraund);
        this.initials.setVisibility(GONE);
    }
}
