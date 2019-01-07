package com.pagatodo.yaganaste.modules.onboarding.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.squareup.picasso.Picasso;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ScreenSlidePageFragment extends Fragment {
    View rootView;
    private ImageView imageView;
    private String url;

    public ScreenSlidePageFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public ScreenSlidePageFragment(String url) {
        this.url=url;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        imageView= rootView.findViewById(R.id.image_slide);
        initViews();

        return rootView;
    }

    public void initViews() {
        //textImage.setText(list.get());
        ButterKnife.bind(this,rootView);
        fill();
    }

    void fill(){
        Picasso.with(App.getContext())
                .load(url)
                .placeholder(R.drawable.onboarding_00)
                .into(imageView);
    }
}
