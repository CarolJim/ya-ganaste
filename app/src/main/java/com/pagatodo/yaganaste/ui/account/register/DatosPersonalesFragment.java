package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.States;
import com.pagatodo.yaganaste.ui._adapters.EnumSpinnerAdapter;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DatosPersonalesFragment extends GenericFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private View rootview;
    @BindView(R.id.radioGender)
    RadioGroup radioGroupGender;
    @BindView(R.id.radioBtnFemale)
    AppCompatRadioButton radioBtnFemale;
    @BindView(R.id.radioBtnMale)
    AppCompatRadioButton radioBtnMale;
    @BindView(R.id.editNames)
    CustomValidationEditText editNames;
    @BindView(R.id.editFirstLastName)
    CustomValidationEditText editFirstLastName;
    @BindView(R.id.editSecoundLastName)
    CustomValidationEditText editSecoundLastName;
    @BindView(R.id.editBirthDay)
    CustomValidationEditText editBirthDay;
    @BindView(R.id.spinnerBirthPlace)
    AppCompatSpinner spinnerBirthPlace;
    StatesSpinnerAdapter adapterBirthPlace;

    public DatosPersonalesFragment() {
    }

    public static DatosPersonalesFragment newInstance() {
        DatosPersonalesFragment fragmentRegister = new DatosPersonalesFragment();
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

        rootview = inflater.inflate(R.layout.fragment_datos_personales, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        editBirthDay.setOnClickListener(this);

        adapterBirthPlace = new StatesSpinnerAdapter(getContext(), R.layout.spinner_layout, States.values());
        spinnerBirthPlace.setAdapter(adapterBirthPlace);
        spinnerBirthPlace.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editBirthDay:
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, date);
                        editBirthDay.setText(DateUtil.getBirthDateString(newDate));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position > 0){
            Toast.makeText(getActivity(), adapterBirthPlace.getItemName(position), Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

