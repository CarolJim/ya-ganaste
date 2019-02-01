package com.pagatodo.yaganaste.modules.registerAggregator.BusinessData;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.interfaces.IDatosNegView;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.modules.registerAggregator.AggregatorActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.DatosNegocioPresenter;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessDataFragment extends GenericFragment implements IOnSpinnerClick, IDatosNegView<ErrorObject>, ValidationForms {

    private static AggregatorActivity activity;
    private DatosNegocioPresenter datosNegocioPresenter;
    public static BusinessDataFragment newInstance(AggregatorActivity activity){
        activity=activity;
        return new BusinessDataFragment();
    }
    View rootView;
    public BusinessDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.datosNegocioPresenter=new DatosNegocioPresenter(getActivity(),this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_business_data, container, false);
        initViews();
        return rootView;

    }

    @Override
    public void setGiros(List<Giros> giros) {
        Collections.sort(giros, new Comparator<Giros>() {
            @Override
            public int compare(Giros o1, Giros o2) {
                return 0;
            }
        });
    }

    @Override
    public void onSpinnerClick() {
        InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        lManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void onSubSpinnerClick() {

    }

    @Override
    public void hideKeyBoard() {
        InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        lManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        if (onEventListener != null) {
            onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        }
    }

    @Override
    public void showError(ErrorObject error) {

    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {

    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {

    }

    @Override
    public void getDataForm() {

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
    }

    private void initValues(){
        datosNegocioPresenter.getGiros();
    }
}
