package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHelpContactanosCorreo  extends GenericFragment implements View.OnClickListener
        {
            View rootview;
            private String mensaje = "";
            @BindView(R.id.edtxtUserName)
            StyleEdittext editbodyemail;

            @BindView(R.id.btn_continue)
            StyleButton btncontinuee;

            String contenidoemail="";




    public MyHelpContactanosCorreo() {
        // Required empty public constructor
    }
    public static MyHelpContactanosCorreo newInstance() {

        MyHelpContactanosCorreo fragmentcontactanoscorreo = new MyHelpContactanosCorreo();
        return fragmentcontactanoscorreo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_help_contactanos_correo, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_continue:
                 contenidoemail = editbodyemail.getText().toString();
                valida();
                break;

        }

    }
            private void valida() {
                if (contenidoemail.isEmpty()) {
                    UI.createSimpleCustomDialog("Ya Ganaste", getString(R.string.correo_vacio), getFragmentManager(), getFragmentTag());
                }
                if (!contenidoemail.isEmpty()) {
                 //   onValidationSuccess();
                    UI.createSimpleCustomDialog("Ya Ganaste", "Enviando Correo:  "+contenidoemail, getFragmentManager(), getFragmentTag());
                }


            }


            @Override
    public void initViews() {

                ButterKnife.bind(this, rootview);
        btncontinuee.setOnClickListener(this);
    }
        }
