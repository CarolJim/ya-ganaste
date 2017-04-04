package com.pagatodo.yaganaste.ui.adquirente;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.NumberTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
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
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);

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

                }else UI.showToast("El monto tiene que ser mayor",getActivity());
            }catch (NumberFormatException e){
                UI.showToast("Ingresa un monto válido.",getActivity());
            }
        }else  UI.showToast("Por favor ingrese un monto.",getActivity());

    }

}
