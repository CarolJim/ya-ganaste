package com.pagatodo.yaganaste.modules.sidebar.About;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutInfoFragment extends GenericFragment {

    public static int ABOUT_NOTICE_PRIVACY = 100;
    public static int ABOUT_lEGAL = 200;
    private static String TAG_ABOUT = "TAG_ABOUT";
    private View rootView;
    private int idAbout;
    private PreferUserActivity activity;

    @BindView(R.id.txt_content_legal)
    StyleTextView txtContent;
    @BindView(R.id.title_fragment)
    StyleTextView titleFragment;

    public static AboutInfoFragment newInstance(int tagAbout){
        AboutInfoFragment fragment = new AboutInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG_ABOUT,tagAbout);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (PreferUserActivity)  context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null){
            idAbout = getArguments().getInt(TAG_ABOUT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.info_acerca_de_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        //if (idAbout == ABOUT_NOTICE_PRIVACY)
        titleFragment.setText(idAbout == ABOUT_NOTICE_PRIVACY ?
                Objects.requireNonNull(getContext()).getResources().getString(R.string.aviso_privacidad):
                Objects.requireNonNull(getContext()).getResources().getString(R.string.termin_condiciones)
        );
        getFirebaseLegals();
    }

    private void getFirebaseLegals() {
        activity.showLoader("");
        DatabaseReference ref = App.getDatabaseReference()
                .child("Ya-Ganaste-5_0/STTNGS/Url/Banking/YG_EMISOR")
                .child(idAbout == ABOUT_NOTICE_PRIVACY?"CPrvd":"CTrmns");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                activity.hideLoader();
                if (dataSnapshot.exists()) {
                    txtContent.setText(Objects.requireNonNull(dataSnapshot.getValue()).toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                activity.hideLoader();
            }
        });
    }
}
