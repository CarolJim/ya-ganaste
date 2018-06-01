package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.DetailsActivity.MY_PERMISSIONS_REQUEST_SEND_SMS;

/**
 * Created by asandovals on 22/05/2018.
 */

public class ContactoFragment extends SupportFragment implements View.OnClickListener {
    private View rootView;

    @BindView(R.id.view_element_correo)
    LinearLayout view_element_correo;
    @BindView(R.id.view_elementlllamar)
    LinearLayout view_elementlllamar;

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        view_element_correo.setOnClickListener(this);
        view_elementlllamar.setOnClickListener(this);


    }
    public  static  ContactoFragment newInstance(){
        return  new ContactoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragmentcontacto, container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.view_elementlllamar){

            int permissionCall = ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_PHONE_STATE);
            // Si no tenemos el permiso lo solicitamos, en cawso contrario entramos al proceso de envio del MSN
            if (permissionCall == -1) {
                ValidatePermissions.checkPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            } else {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0180050010000")));

            }

            }

        if(view.getId()==R.id.view_element_correo){

            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("text/html");
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"contacto@yaganaste.com"});
            startActivity(Intent.createChooser(email,"Send Email"));
        }
    }
}
