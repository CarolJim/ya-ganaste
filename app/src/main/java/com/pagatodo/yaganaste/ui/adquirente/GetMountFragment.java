package com.pagatodo.yaganaste.ui.adquirente;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.AdqActivity;
import com.pagatodo.yaganaste.ui._controllers.BussinesActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.NumberTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import butterknife.BindView;
import butterknife.ButterKnife;
public class GetMountFragment extends GenericFragment implements View.OnClickListener {

    private  View rootView;
    @BindView(R.id.edtMount)
    StyleEdittext edtMount;
    @BindView(R.id.edtConcept)
    StyleEdittext edtConcept;
    @BindView(R.id.btnCharge)
    StyleButton btnCharge;
    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;
    private String amount = "";
    private String concept = "";

    private float MIN_AMOUNT = 1.0f;

    public GetMountFragment() {
        // Required empty public constructor
    }
    public static GetMountFragment newInstance() {
        return new GetMountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_monto, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        edtMount.addTextChangedListener(new NumberTextWatcher(edtMount));
        btnCharge.setOnClickListener(this);
        keyboardView.setKeyBoard(getActivity(),R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);

        edtMount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length() == 4){
                    keyboardView.hideCustomKeyboard();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Make the custom keyboard appear
        edtMount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    keyboardView.showCustomKeyboard(v);
                }else{
                    keyboardView.hideCustomKeyboard();}
            }
        });

        edtMount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardView.showCustomKeyboard(v);
            }
        });

        edtMount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                keyboardView.showCustomKeyboard(v);
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCharge:
                actionCharge();
                break;
            default:
                break;
        }

    }

    private void actionCharge(){
        String valueAmount = edtMount.getText().toString().trim();
        if(valueAmount.length() > 0 && !valueAmount.equals("$0.00")){
            try{
                StringBuilder cashAmountBuilder = new StringBuilder(valueAmount);
                valueAmount = cashAmountBuilder.deleteCharAt(0).toString();
                float current_mount = Float.parseFloat(valueAmount);
                String current_concept = edtConcept.getText().toString().trim();//Se agrega Concepto opcional
                if(current_mount >= MIN_AMOUNT){

                    Intent intent = new Intent(getActivity(), AdqActivity.class);
                    startActivity(intent);

                }else UI.showToast("El monto tiene que ser mayor",getActivity());
            }catch (NumberFormatException e){
                UI.showToast("Ingresa un monto válido.",getActivity());
            }
        }else  UI.showToast("Por favor ingrese un monto.",getActivity());

    }

}
