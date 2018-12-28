package com.pagatodo.yaganaste.modules.qr.QRWallet.EcribeQRPlate;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.qr.QrManagerRouter;
import com.pagatodo.yaganaste.modules.qr.operations.QrOperationActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ErcribePlateFragment extends GenericFragment{


    @BindView(R.id.btn_continue)
    StyleButton btn_continue;

    @BindView(R.id.edit_code_wr)
    EditText edit_code_wr;
public  static QrOperationActivity activityf;
    View rootView;

    boolean isValid = false;
    private QrManagerRouter router;

    public static ErcribePlateFragment newInstance(){
        return new ErcribePlateFragment();
    }
   public static ErcribePlateFragment newInstance(QrOperationActivity activity){
        activityf=activity;
        return new ErcribePlateFragment();
    }



    public ErcribePlateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_ercribe_plate, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
        imm.showSoftInput(edit_code_wr, InputMethodManager.SHOW_IMPLICIT);
        edit_code_wr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit_code_wr.getText().length()==12){
                    btn_continue.setBackgroundResource(R.drawable.button_rounded_blue);
                    isValid = true;
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                }else {
                    btn_continue.setBackgroundResource(R.drawable.button_rounded_gray);
                    isValid = false;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid){
                 //   activityf.showaliasfragment(edit_code_wr.getText().toString().trim());
                }
            }
        });


    }
}
