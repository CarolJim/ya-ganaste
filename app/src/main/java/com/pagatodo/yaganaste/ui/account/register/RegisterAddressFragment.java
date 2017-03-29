package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.IAccountAddressRegisterView;
import com.pagatodo.yaganaste.interfaces.enums.Genders;
import com.pagatodo.yaganaste.interfaces.enums.States;
import com.pagatodo.yaganaste.ui._adapters.EnumSpinnerAdapter;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterOld;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class RegisterAddressFragment extends GenericFragment implements View.OnClickListener,IAccountAddressRegisterView {

    private View rootview;

    @BindView(R.id.spinnerGender)
    AppCompatSpinner spinnerGender;
    @BindView(R.id.edtxtRegisterAddressBirthDay)
    StyleEdittext edtxtRegisterAddressBirthDay;
    @BindView(R.id.spinnerBirthPlace)
    AppCompatSpinner spinnerBirthPlace;
    @BindView(R.id.edtxtRegisterAddressCP)
    StyleEdittext edtxtRegisterAddressCP;
    @BindView(R.id.spRegisterAddressColonia)
    AppCompatSpinner spRegisterAddressColonia;
    @BindView(R.id.edtxtRegisterAddressStreet)
    StyleEdittext edtxtRegisterAddressStreet;
    @BindView(R.id.edtxtRegisterAddressNoExt)
    StyleEdittext edtxtRegisterAddressNoExt;
    @BindView(R.id.edtxtRegisterAddressNoInt)
    StyleEdittext edtxtRegisterAddressNoInt;
    @BindView(R.id.btnRegisterAddressNext)
    StyleButton btnRegisterAddressNext;


    private ArrayAdapter<String>  adapterColonia;
    private List<ColoniasResponse> listaColonias;
    private List<String> coloniasNombre;

    private AccountPresenterOld accountPresenter;


    public RegisterAddressFragment() {
    }

    public static RegisterAddressFragment newInstance() {
        RegisterAddressFragment fragmentRegister = new RegisterAddressFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //accountPresenter = new AccountPresenterOld(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_register_address, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        edtxtRegisterAddressBirthDay.setOnClickListener(this);

        EnumSpinnerAdapter adapterBirthPlace = new EnumSpinnerAdapter(getContext(), R.layout.spinner_layout, States.values());
        spinnerBirthPlace.setAdapter(adapterBirthPlace);
        EnumSpinnerAdapter adapterGender = new EnumSpinnerAdapter(getContext(), R.layout.spinner_layout, Genders.values());
        spinnerGender.setAdapter(adapterGender);
        coloniasNombre = new ArrayList<String>();
        adapterColonia = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, R.id.textView_spinner,coloniasNombre);
        spRegisterAddressColonia.setAdapter(adapterColonia);

        btnRegisterAddressNext.setOnClickListener(this);


        edtxtRegisterAddressCP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s!= null && s.toString().length() > 4) {
                    accountPresenter.getNeighborhoods(s.toString().trim());//Buscamos por CP
                } else {
                    if(listaColonias != null) {
                        listaColonias.clear();
                        fillAdapter();
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.edtxtRegisterAddressBirthDay:
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, date);
                        edtxtRegisterAddressBirthDay.setText(DateUtil.getBirthDateString(newDate));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.btnRegisterAddressNext:

                break;

            default:
                break;
        }
    }

    @Override
    public void setNeighborhoodsAvaliables(List<ColoniasResponse> listaColonias) {

       this.listaColonias = listaColonias;
        fillAdapter();
    }

    private void fillAdapter(){
        coloniasNombre.clear();
        for(ColoniasResponse coloniasResponse : this.listaColonias){
            coloniasNombre.add(coloniasResponse.getColonia());
        }

        adapterColonia.notifyDataSetChanged();
    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void nextStepRegister(String event, Object data) {

    }

    @Override
    public void backStepRegister(String event, Object data) {

    }

    @Override
    public void showError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
    }
}

