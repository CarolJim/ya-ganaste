package com.pagatodo.yaganaste.modules.onboarding.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;

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
    private int index;

    public ScreenSlidePageFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public ScreenSlidePageFragment(String url,int index) {
        this.url=url;
        this.index=index;
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

        switch(index){

            case 0:
                Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.onboarding_00)
                        .into(imageView);
                break;
                case 1:
                    Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.onboarding_01)
                        .into(imageView);
                break;
                case 2:
                    Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.onboarding_02)
                        .into(imageView);
                break;
                case 3:
                    Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.onboarding_03)
                        .into(imageView);
                break;
                case 4:
                    Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.onboarding_04)
                        .into(imageView);
                break;
                case 5:
                    Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.onboarding_05)
                        .into(imageView);
                break;
            case 6:
                Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.onboarding_06)
                        .into(imageView);
                break;
        }




    }
}
