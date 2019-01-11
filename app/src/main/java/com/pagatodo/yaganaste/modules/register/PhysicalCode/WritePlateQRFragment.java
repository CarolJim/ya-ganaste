package com.pagatodo.yaganaste.modules.register.PhysicalCode;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WritePlateQRFragment extends GenericFragment implements View.OnFocusChangeListener {

    private View rootView;
    private RegActivity activity;

    @BindView(R.id.edit_code_wr)
    public EditText editTextWrQr;
    @BindView(R.id.btn_continue)
    public StyleButton btnContinue;
    @BindView(R.id.text_number_qr)
    public TextInputLayout inputQr;

    @BindView(R.id.asignar_edittext)
    public EditText asignar_edittext;


    @BindView(R.id.asignar_tv1)
    TextView tv1Num;
    @BindView(R.id.asignar_tv2)
    TextView tv2Num;
    @BindView(R.id.asignar_tv3)
    TextView tv3Num;
    @BindView(R.id.asignar_tv4)
    TextView tv4Num;
    @BindView(R.id.asignar_tv5)
    TextView tv5Num;
    @BindView(R.id.asignar_tv6)
    TextView tv6Num;
    @BindView(R.id.asignar_tv7)
    TextView tv7Num;
    @BindView(R.id.asignar_tv8)
    TextView tv8Num;
    @BindView(R.id.asignar_tv9)
    TextView tv9Num;
    @BindView(R.id.asignar_tv10)
    TextView tv10Num;
    @BindView(R.id.asignar_tv11)
    TextView tv11Num;
    @BindView(R.id.asignar_tv12)
    TextView tv12Num;

    String newText = "";
    String oldText = "";

    @BindView(R.id.llypass)
    public LinearLayout llypass;

    public static WritePlateQRFragment newInstance(){
        return new WritePlateQRFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (RegActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.write_plateqr_fragment,container,false);
        initViews();
        return rootView;

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        activity.hideStepBar();



        llypass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                asignar_edittext.requestFocusFromTouch();
                InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                lManager.showSoftInput(asignar_edittext, 0);


                return false;
            }
        });

        asignar_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void afterTextChanged(Editable s) {
                newText = s.toString();
                if (newText.length() > oldText.length()) {
                    final int countString = asignar_edittext.getText().toString().length();
                    switch (countString) {
                        case 1:
                            tv1Num.setText(s.toString().substring(0,1));
                            break;
                        case 2:
                            tv2Num.setText(s.toString().substring(1,2));
                            break;
                        case 3:
                            tv3Num.setText(s.toString().substring(2,3));
                            break;
                        case 4:
                            tv4Num.setText(s.toString().substring(3,4));
                            break;
                        case 5:
                            tv5Num.setText(s.toString().substring(4,5));
                            break;

                        case 6:
                            tv6Num.setText(s.toString().substring(5,6));
                            break;

                        case 7:
                            tv7Num.setText(s.toString().substring(6,7));
                            break;
                        case 8:
                            tv8Num.setText(s.toString().substring(7,8));
                            break;
                        case 9:
                            tv9Num.setText(s.toString().substring(8,9));
                            break;
                        case 10:
                            tv10Num.setText(s.toString().substring(9,10));
                            break;
                        case 11:
                            tv11Num.setText(s.toString().substring(10,11));
                            break;

                        case 12:
                            tv12Num.setText(s.toString().substring(11,12));
                            activity.getInteractor().onValidateQr(asignar_edittext.getText().toString());
                            InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            lManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                            break;
                    }
                    oldText = s.toString();


                }else {
                    // Proceso de borrado de numeros,
                    int countString = asignar_edittext.getText().toString().length();
                    switch (countString) {
                        case 0:
                            tv1Num.setText("");
                            tv2Num.setText("");
                            tv3Num.setText("");
                            tv4Num.setText("");
                            tv5Num.setText("");
                            tv6Num.setText("");
                            tv7Num.setText("");
                            tv8Num.setText("");
                            tv9Num.setText("");
                            tv10Num.setText("");
                            tv11Num.setText("");
                            tv12Num.setText("");

                            break;
                        case 1:
                            tv2Num.setText("");
                            tv3Num.setText("");
                            tv4Num.setText("");
                            tv5Num.setText("");
                            tv6Num.setText("");
                            tv7Num.setText("");
                            tv8Num.setText("");
                            tv9Num.setText("");
                            tv10Num.setText("");
                            tv11Num.setText("");
                            tv12Num.setText("");
                            break;
                        case 2:
                            tv3Num.setText("");
                            tv4Num.setText("");
                            tv5Num.setText("");
                            tv6Num.setText("");
                            tv7Num.setText("");
                            tv8Num.setText("");
                            tv9Num.setText("");
                            tv10Num.setText("");
                            tv11Num.setText("");
                            tv12Num.setText("");
                            break;
                        case 3:
                            tv4Num.setText("");
                            tv5Num.setText("");
                            tv6Num.setText("");
                            tv7Num.setText("");
                            tv8Num.setText("");
                            tv9Num.setText("");
                            tv10Num.setText("");
                            tv11Num.setText("");
                            tv12Num.setText("");
                            break;

                        case 4:
                            tv5Num.setText("");
                            tv6Num.setText("");
                            tv7Num.setText("");
                            tv8Num.setText("");
                            tv9Num.setText("");
                            tv10Num.setText("");
                            tv11Num.setText("");
                            tv12Num.setText("");
                            break;

                        case 5:
                            tv6Num.setText("");
                            tv7Num.setText("");
                            tv8Num.setText("");
                            tv9Num.setText("");
                            tv10Num.setText("");
                            tv11Num.setText("");
                            tv12Num.setText("");
                            break;
                            case 6:
                            tv7Num.setText("");
                            tv8Num.setText("");
                            tv9Num.setText("");
                            tv10Num.setText("");
                            tv11Num.setText("");
                            tv12Num.setText("");
                            break;case 7:

                            tv8Num.setText("");
                            tv9Num.setText("");
                            tv10Num.setText("");
                            tv11Num.setText("");
                            tv12Num.setText("");
                            break;case 8:

                            tv9Num.setText("");
                            tv10Num.setText("");
                            tv11Num.setText("");
                            tv12Num.setText("");
                            break;case 9:

                            tv10Num.setText("");
                            tv11Num.setText("");
                            tv12Num.setText("");
                            break;case 10:
                            tv11Num.setText("");
                            tv12Num.setText("");
                            break;
                            case 11:
                            tv12Num.setText("");
                            break;

                    }
                    oldText = s.toString();

                }
            }
        });




        btnContinue.setOnClickListener(view -> {
            if (validateData()){
                //activity.getRouter().showNewLinkedCode(editTextWrQr.getText().toString());
                activity.getInteractor().onValidateQr(asignar_edittext.getText().toString());
            } else {
                inputQr.setBackgroundResource(R.drawable.inputtext_error);
            }
        });
        asignar_edittext.setOnFocusChangeListener(this);
        asignar_edittext.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                if (validateData()){
                    //activity.getRouter().showNewLinkedCode(editTextWrQr.getText().toString());
                    activity.getInteractor().onValidateQr(asignar_edittext.getText().toString());
                } else {
                    inputQr.setBackgroundResource(R.drawable.inputtext_error);
                }
            }
            return false;
        });

        asignar_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (validateData()){
                    btnContinue.setBackgroundResource(R.drawable.button_yes);
                } else {
                    btnContinue.setBackgroundResource(R.drawable.btn_desactive);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean validateData(){
        boolean isValid = true;
        if (asignar_edittext.getText().toString().isEmpty()) {
            isValid = false;
        }
        if (asignar_edittext.getText().toString().length() < 12){
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            inputQr.setBackgroundResource(R.drawable.inputtext_active);
            InputMethodManager imm = (InputMethodManager)   activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else {
            inputQr.setBackgroundResource(R.drawable.inputtext_normal);
        }
    }
}
