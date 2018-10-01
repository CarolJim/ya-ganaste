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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoVideoTutorials;
import com.pagatodo.yaganaste.ui_wallet.holders.ButtonSimpleViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.LinearTextViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.UtilsIntents;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_CALL;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_EMAIL;

public class TutorialsFragment extends SupportFragment {

    private View rootView;
    private LayoutInflater inflater;
    private ViewGroup parent;

    @BindView(R.id.container_options)
    LinearLayout container_options;

    @BindView(R.id.container)
    LinearLayout container;

    public static TutorialsFragment newInstance() {
        return new TutorialsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(getContext());
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
        parent = container_options;

        /*setButton(new ElementView(OPTION_CHAT, R.drawable.icon_chat, R.string.chat), item -> {
            onEventListener.onEvent(EVENT_GO_CHAT,null);
        }).inflate(container_options);
        container_options.addView(setSpaceVertical());*/
        setButton(new ElementView(OPTION_EMAIL, R.drawable.ico_correo, R.string.correo), item -> {
            UtilsIntents.sendEmail(getActivity());
        }).inflate(container_options);
        container_options.addView(setSpaceVertical());
        setButton(new ElementView(OPTION_CALL, R.drawable.ic_telefono, R.string.llamada), item -> {
            UtilsIntents.createCallIntent(getActivity());
        }).inflate(container_options);
        DatabaseReference ref = App.getDatabaseReference().child("Mexico").child("AppEngine").child("Media").child("Video");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    container.removeAllViews();
                    container.addView(setViewLine());
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        DtoVideoTutorials item = singleSnapshot.getValue(DtoVideoTutorials.class);
                        setLineraText(container, item, item1 -> {
                            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + item.ID_Video));
                            appIntent.putExtra("force_fullscreen", true);
                            startActivity(appIntent);
                        }).inflate(container);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private ButtonSimpleViewHolder setButton(ElementView elementView, OnClickItemHolderListener listener){

        View layout = inflater.inflate(R.layout.view_element, parent, false);
        ButtonSimpleViewHolder buttonSimpleViewHolder = new ButtonSimpleViewHolder(layout);
        buttonSimpleViewHolder.bind(elementView,listener);
        return buttonSimpleViewHolder;
    }

    private View setSpaceVertical(){
        View space = new View(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                10,
                10);
        space.setLayoutParams(params);
        return space;
    }

    public LinearTextViewHolder setLineraText(ViewGroup parent,
                                              DtoVideoTutorials item,
                                              OnClickItemHolderListener listener){
        View layout = inflater.inflate(R.layout.linear_text_layout, parent, false);
        LinearTextViewHolder linearTextViewHolder = new LinearTextViewHolder(layout);
        linearTextViewHolder.bind(item,listener);
        return linearTextViewHolder;
    }

    public View setViewLine(){
        View space = new View(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                1);
        space.setLayoutParams(params);
        space.setBackgroundColor(App.getContext().getResources().getColor(R.color.gray_text_wallet_4));
        return space;
    }
}
