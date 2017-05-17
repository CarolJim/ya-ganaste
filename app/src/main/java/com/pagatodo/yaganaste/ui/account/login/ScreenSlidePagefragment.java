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

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by mariofernandezbermudez on 02/02/17.
 */

public class ScreenSlidePagefragment extends GenericFragment implements  View.OnClickListener{
    private static final String BACKGROUND_IMAGE = "BACKGROUND_IMAGE";
    private static final String INDEX = "INDEX";
    private static final String TITLE_PAGER = "TITLE_PAGER";
    private static final String MESSAGE_PAGER = "MESSAGE_PAGER";
    private static final String TAG = "SignInActivity";
    private Context context;
    private ImageView img_background;
    private int index;
    private int idImageResourceBackground;
    private String txtTitle;
    private  View view;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;


    public static ScreenSlidePagefragment newInstance(@DrawableRes int idImageResource,String title) {
        ScreenSlidePagefragment transactionFragment = new ScreenSlidePagefragment();
        Bundle args = new Bundle();
        args.putInt(BACKGROUND_IMAGE,idImageResource);
        args.putString(TITLE_PAGER,title);
        transactionFragment.setArguments(args);
        return transactionFragment;
    }

    public static ScreenSlidePagefragment newInstance(@DrawableRes int idImageResource,String title , int index) {
        ScreenSlidePagefragment transactionFragment = new ScreenSlidePagefragment();
        Bundle args = new Bundle();
        args.putInt(BACKGROUND_IMAGE,idImageResource);
        args.putString(TITLE_PAGER,title);
        args.putInt(INDEX,index);
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
            index = arg.getInt(ScreenSlidePagefragment.INDEX);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_roll_page, container, false);
        ButterKnife.bind(this,view);
        ImageView imgBackground = (ImageView) view.findViewById(R.id.imgBgRollPage);
        TextView txtViewHead = (TextView) view.findViewById(R.id.txtHeadRollPage);

        imgBackground.setImageResource(idImageResourceBackground);
        txtViewHead.setText(txtTitle);
        img1 = (ImageView) view.findViewById(R.id.indicator1);
        img2 = (ImageView) view.findViewById(R.id.indicator2);
        img3 = (ImageView) view.findViewById(R.id.indicator3);
        img4 = (ImageView) view.findViewById(R.id.indicator4);
        switch (index){
            case 1:
                img1.setImageResource(R.drawable.tab_indicator_default);
                img2.setImageResource(R.drawable.tab_inidicator_selected);
                img3.setImageResource(R.drawable.tab_indicator_default);
                img4.setImageResource(R.drawable.tab_indicator_default);
                break;
            case 2:
                img1.setImageResource(R.drawable.tab_indicator_default);
                img2.setImageResource(R.drawable.tab_indicator_default);
                img3.setImageResource(R.drawable.tab_inidicator_selected);
                img4.setImageResource(R.drawable.tab_indicator_default);
                break;
            case 3:
                img1.setImageResource(R.drawable.tab_indicator_default);
                img2.setImageResource(R.drawable.tab_indicator_default);
                img3.setImageResource(R.drawable.tab_indicator_default);
                img4.setImageResource(R.drawable.tab_inidicator_selected);
                break;
            case 4:
                img1.setImageResource(R.drawable.tab_indicator_default);
                img2.setImageResource(R.drawable.tab_indicator_default);
                img3.setImageResource(R.drawable.tab_indicator_default);
                img4.setImageResource(R.drawable.tab_indicator_default);
                break;
            default:
                break;
        }

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

