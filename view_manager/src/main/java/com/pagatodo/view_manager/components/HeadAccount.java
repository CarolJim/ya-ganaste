package com.pagatodo.view_manager.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private LinearLayout circlePersonContent;
    private ImageView circlePerson;
    private TextView txtIniciales;

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

    private void initMain(AttributeSet attrs){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        rootView = inflater.inflate(R.layout.head_account,this,false);
        init();
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.HeadAccount,
                    0, 0);
            a.recycle();
        }
        addView(rootView);
    }

    @Override
    public void init() {
        circlePersonContent = rootView.findViewById(R.id.circle_person_content);
        circlePerson = rootView.findViewById(R.id.circle_person);
        txtIniciales = rootView.findViewById(R.id.txtIniciales);

    }

    @Override
    public void bind(HeadAccountData item, OnHolderListener<HeadAccountData> listener) {
        /*Picasso.get()
                .load("https://www.blogdigital.mx/images/2016/10/18/blog-digital-persona-pensando_large.jpg")
                .resize(50, 50)
                .centerCrop()
                .into(circlePerson);*/
        if (item.getUrlImage().isEmpty()){
            setImageURL(item.getUrlImage());
            circlePerson.setVisibility(VISIBLE);
            circlePersonContent.setVisibility(GONE);
        } else {
            setImageDrawable(item.getName(),item.getColorMarca());
            circlePerson.setVisibility(GONE);
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
                .transform(new CircleTransform()).into(circlePerson);
    }

    public void setImageDrawable(String name, String colorMarca){
        txtIniciales.setVisibility(View.VISIBLE);
        txtIniciales.setText(getIniciales(name));
        GradientDrawable gd = createCircleDrawable(android.graphics.Color.parseColor(colorMarca),
                android.graphics.Color.parseColor(colorMarca));
        circlePersonContent.setBackground(gd);
    }

    private GradientDrawable createCircleDrawable(int colorBackground, int colorBorder) {
        // Creamos el circulo que mostraremos
        int strokeWidth = 2; // 3px not dp
        int roundRadius = 140; // 8px not dp
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(colorBackground);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, colorBorder);
        return gd;
    }

    private String getIniciales(String fullName) {
        if (fullName.trim().length() == 1) {
            return fullName.substring(0, 1).toUpperCase();
        }

        String[] spliName = fullName.trim().split(" ");
        String sIniciales = "";
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
