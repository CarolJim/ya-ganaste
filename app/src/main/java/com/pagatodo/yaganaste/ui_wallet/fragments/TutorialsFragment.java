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
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.DatosPersonalesFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoStates;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoVideoTutorials;
import com.pagatodo.yaganaste.ui_wallet.holders.LinearTextViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.FormBuilder;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.UtilsIntents;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_CALL;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_EMAIL;

public class TutorialsFragment extends GenericFragment implements OnClickItemHolderListener {

    private View rootView;
    private FormBuilder builder;

    @BindView(R.id.container_options)
    LinearLayout container_options;

    @BindView(R.id.container)
    LinearLayout container;



    public static TutorialsFragment newInstance(){
        return new TutorialsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new FormBuilder(getContext());
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



        builder.setButton(container_options, new ElementView(OPTION_EMAIL, R.drawable.ico_correo, R.string.correo), item -> {
            UtilsIntents.sendEmail(getActivity());
        }).inflate(container_options);

        builder.setButton(container_options, new ElementView(OPTION_CALL, R.drawable.ic_telefono, R.string.llamada), item -> {
            UtilsIntents.createCallIntent(getActivity());
        }).inflate(container_options);


        container.addView(builder.setViewLine());
        getList();
    }

    @Override
    public void onClick(Object item) {
        /*DtoVideoTutorials videotutorial = (DtoVideoTutorials) item;
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videotutorial.getIdVideo()));
        appIntent.putExtra("force_fullscreen",true);
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + videotutorial.getIdVideo()));
        webIntent.putExtra("force_fullscreen",true);
        startActivity(appIntent);*/
    }

    public void getList() {
        //final ArrayList<DtoVideoTutorials> videos  = new ArrayList<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference ref = database.child("Mexico").child("AppEngine").child("Media").child("Video/0/ID_Video");
        DatabaseReference ref = database.child("Mexico").child("AppEngine").child("Media").child("Video");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //String cad = dataSnapshot.getValue(String.class);

                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        //String cad = singleSnapshot.getValue(String.class);
                        DtoVideoTutorials item = singleSnapshot.getValue(DtoVideoTutorials.class);
                        //videos.add(item);

                        builder.setLineraText(container, new DtoVideoTutorials(), item1 -> {

                            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + item.ID_Video));
                            appIntent.putExtra("force_fullscreen",true);
                            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://www.youtube.com/watch?v=" + item.ID_Video));
                            webIntent.putExtra("force_fullscreen",true);
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


}
