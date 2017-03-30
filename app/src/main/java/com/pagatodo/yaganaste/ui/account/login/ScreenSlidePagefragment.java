package com.pagatodo.yaganaste.ui.account.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;


/**
 * Created by mariofernandezbermudez on 02/02/17.
 */

public class ScreenSlidePagefragment extends GenericFragment implements  View.OnClickListener{
    private static final String BACKGROUND_IMAGE = "BACKGROUND_IMAGE";
    private static final String TITLE_PAGER = "TITLE_PAGER";
    private static final String MESSAGE_PAGER = "MESSAGE_PAGER";
    private static final String TAG = "SignInActivity";
    private Context context;
    private ImageView img_background;
    private int idImageResourceBackground;
    private String txtTitle;
    private String txtMessage;
    private  View view;


    public static ScreenSlidePagefragment newInstance(@DrawableRes int idImageResource,String title,String message) {
        ScreenSlidePagefragment transactionFragment = new ScreenSlidePagefragment();
        Bundle args = new Bundle();
        args.putInt(BACKGROUND_IMAGE,idImageResource);
        args.putString(TITLE_PAGER,title);
        args.putString(MESSAGE_PAGER,message);
        transactionFragment.setArguments(args);
        return transactionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        context = getContext();

        if (getArguments() != null) {
            Bundle arg = getArguments();
            idImageResourceBackground = arg.getInt(ScreenSlidePagefragment.BACKGROUND_IMAGE);
            txtTitle= arg.getString(ScreenSlidePagefragment.TITLE_PAGER);
            txtMessage= arg.getString(ScreenSlidePagefragment.MESSAGE_PAGER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_roll_page, container, false);

        ImageView imgBackground = (ImageView) view.findViewById(R.id.imgBgRollPage);
        TextView txtViewHead = (TextView) view.findViewById(R.id.txtHeadRollPage);
        TextView txtViewBoth = (TextView) view.findViewById(R.id.txtBothRollPage);

        imgBackground.setImageResource(idImageResourceBackground);
        txtViewHead.setText(txtTitle);
        txtViewBoth.setText(txtMessage);

        return view;
    }

    @Override
    public void initViews() {
        img_background = (ImageView)view.findViewById(R.id.img_pager);
        img_background.setImageResource(idImageResourceBackground);
    }

    @Override
    public void onClick(View view) {

    }
}

