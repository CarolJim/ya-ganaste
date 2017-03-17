package com.pagatodo.yaganaste.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by mariofernandezbermudez on 02/02/17.
 */

public class ScreenSlidePagefragment extends GenericFragment implements  View.OnClickListener{
    private static final String BACKGROUND_IMAGE = "BACKGROUND_IMAGE";
    private static final String TAG = "SignInActivity";
    private Context context;
    private ImageView img_background;
    private int idImageResourceBackground;


    public static ScreenSlidePagefragment newInstance(@DrawableRes int idImageResource) {
        ScreenSlidePagefragment transactionFragment = new ScreenSlidePagefragment();
        Bundle args = new Bundle();
        args.putInt(BACKGROUND_IMAGE,idImageResource);
        transactionFragment.setArguments(args);
        return transactionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        context = getApplicationContext();

        if (getArguments() != null) {
            Bundle arg = getArguments();
            idImageResourceBackground = arg.getInt(ScreenSlidePagefragment.BACKGROUND_IMAGE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_viewpager_fragment, container, false);
        img_background = (ImageView)view.findViewById(R.id.img_pager);
        img_background.setImageResource(idImageResourceBackground);

        return view;
    }


    @Override
    public void onClick(View view) {

    }
}
