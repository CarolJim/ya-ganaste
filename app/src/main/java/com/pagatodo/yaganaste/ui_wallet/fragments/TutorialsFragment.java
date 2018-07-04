package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.holders.LinearTextViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.FormBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorialsFragment extends GenericFragment implements OnClickItemHolderListener {

    private View rootView;

    @BindView(R.id.container)
    LinearLayout container;

    public static TutorialsFragment newInstance(){
        return new TutorialsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tutorials_layout, container, false);
        initViews();
        return rootView;
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        FormBuilder builder = new FormBuilder(getContext());
        LinearTextViewHolder.Videotutorial item = new LinearTextViewHolder.Videotutorial("Tutorial bienvenida", "http://www.youtube.com/watch?v=9Jg1D07NgeI");
        builder.setLineraText(container,item,this).inflate(container);

    }

    @Override
    public void onClick(Object item) {
        LinearTextViewHolder.Videotutorial videotutorial = (LinearTextViewHolder.Videotutorial) item;
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:9Jg1D07NgeI"));
        appIntent.putExtra("force_fullscreen",true);
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(videotutorial.getUrl()));
        webIntent.putExtra("force_fullscreen",true);
        startActivity(appIntent);
    }
}
