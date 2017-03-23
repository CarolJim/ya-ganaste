package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Genders;
import com.pagatodo.yaganaste.interfaces.enums.States;
import com.pagatodo.yaganaste.ui._adapters.EnumSpinnerAdapter;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class RegisterAddressFragment extends GenericFragment implements View.OnClickListener {

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


    ArrayAdapter<String>  adapterColonia;

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

        adapterColonia = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, R.id.textView_spinner, new String[]{" "});
        spRegisterAddressColonia.setAdapter(adapterColonia);

        btnRegisterAddressNext.setOnClickListener(this);

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

}

