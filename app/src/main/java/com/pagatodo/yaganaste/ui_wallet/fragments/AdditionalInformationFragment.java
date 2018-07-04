package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CuestionarioEntity;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.interfaces.IDatosPersonalesManager;
import com.pagatodo.yaganaste.interfaces.enums.Parentescos;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.ui_wallet.holders.InputDataViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.holders.QuestionViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.SpinnerHolder;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.FormBuilder;
import com.pagatodo.yaganaste.utils.customviews.CountriesDialogFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_MONEY_LAUNDERING;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_ERES_MEXICANO_NATURALIZADO;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_FAMILIAR;

public class AdditionalInformationFragment extends GenericFragment implements IDatosPersonalesManager,
        View.OnClickListener, OnClickItemHolderListener {

    private InputDataViewHolder states;
    private Paises country;
    private QuestionViewHolder qNacionalidad;
    private QuestionViewHolder qCargoPublico;
    private SpinnerHolder parentesco;
    private Giros textParentesco;
    private InputDataViewHolder inputDataViewHolder;
    private View rootView;

    @BindView(R.id.container)
    LinearLayout layout;
    @BindView(R.id.btn_next)
    StyleButton btnNext;

    public static AdditionalInformationFragment newInstance() {
        return new AdditionalInformationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.additional_information_fragment_layout, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        FormBuilder builder = new FormBuilder(getContext());
        parentesco = builder.setSpinner(layout,R.string.parentesco,new BussinesLineSpinnerAdapter(getContext(),
                R.layout.spinner_layout, getList(), null),this);
        states = builder.setInputText(layout,"");
        inputDataViewHolder = builder.setInputText(layout,getContext().getResources().getString(R.string.txt_cargo));
        qCargoPublico = builder.setQuest(layout, R.string.publicServantQuestion, false, (radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.radioBtnPublicServantNo:
                    parentesco.getView().setVisibility(View.GONE);
                    inputDataViewHolder.getView().setVisibility(View.GONE);
                    break;
                case R.id.radioBtnPublicServantYes:
                    parentesco.getView().setVisibility(View.VISIBLE);
                    inputDataViewHolder.getView().setVisibility(View.VISIBLE);
                    break;

            }
        });
        qNacionalidad = builder.setQuest(layout, R.string.mexicanQuestion, true, (radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.radioBtnPublicServantNo:
                    states.getView().setVisibility(View.VISIBLE);
                    showDialogList(getListPaises());
                    break;
                case R.id.radioBtnPublicServantYes:
                    states.getView().setVisibility(View.GONE);
                    break;
            }
        });

        qCargoPublico.inflate(layout);
        layout.addView(builder.setSpace(10));
        parentesco.inflate(layout);
        parentesco.getView().setVisibility(View.GONE);
        layout.addView(builder.setSpace(10));
        inputDataViewHolder.inflate(layout);
        inputDataViewHolder.getView().setVisibility(View.GONE);
        layout.addView(builder.setSpace(10));
        qNacionalidad.inflate(layout);
        layout.addView(builder.setSpace(10));
        states.inflate(layout);
        states.getView().setVisibility(View.GONE);
        states.getEditText().setFocusable(false);
        states.getEditText().setOnClickListener(view -> showDialogList(getListPaises()));


        btnNext.setOnClickListener(this);
    }

    private List<Giros> getList(){
        List<Giros> list = new ArrayList<>();
        for (Parentescos parentescos: Parentescos.values()){
            Giros giro = new Giros();
            giro.setIdGiro(parentescos.getId());
            giro.setGiro(parentescos.getName());
            list.add(giro);
        }
        return list;

    }

    private void validateForm(){
        boolean isValid = true;
        if (SingletonUser.getInstance().getDataUser().getUsuario().isEsExtranjero() && !qNacionalidad.getResponseYes().isChecked()){
            if (country == null) {
                states.getInputLayout().setBackgroundResource(R.drawable.inputtext_error);
                isValid = false;
            }
        }
        if (qCargoPublico.getResponseYes().isChecked()) {
            if (textParentesco == null || textParentesco.getIdGiro() == -1) {
                parentesco.getView().setBackgroundResource(R.drawable.inputtext_error);
                isValid = false;
            }

            if (TextUtils.isEmpty(inputDataViewHolder.getEditText().getText())) {
                inputDataViewHolder.getInputLayout().setBackgroundResource(R.drawable.inputtext_error);
                isValid = false;
            }
        }

        if (isValid) {
            onValidationSuccess();
        }
    }

    private void onValidationSuccess(){
        RegisterAgent registerAgent = RegisterAgent.getInstance();

        if (registerAgent.getCuestionario().size() > 0) {
            for (CuestionarioEntity cuestionario : registerAgent.getCuestionario()) {
                if (cuestionario.getPreguntaId() == PREGUNTA_FAMILIAR) {
                    registerAgent.getCuestionario().remove(cuestionario);
                } else if (cuestionario.getPreguntaId() == PREGUNTA_ERES_MEXICANO_NATURALIZADO) {
                    registerAgent.getCuestionario().remove(cuestionario);
                }
            }
        }

        addNewCuestionarios(registerAgent);
        onEventListener.onEvent(EVENT_GO_BUSSINES_MONEY_LAUNDERING, null);
    }

    private void addNewCuestionarios(RegisterAgent registerAgent) {
        if (qCargoPublico.getResponseYes().isChecked()) {
            registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_FAMILIAR,
                    qCargoPublico.getResponseYes().isChecked(),
                    textParentesco.getIdGiro(),
                    inputDataViewHolder.getEditText().getText().toString()));
        } else {
            registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_FAMILIAR, qCargoPublico.getResponseNo().isChecked()));
        }

        if (SingletonUser.getInstance().getDataUser().getUsuario().isEsExtranjero()) {
            if (qNacionalidad.getResponseYes().isChecked()) {
                registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_ERES_MEXICANO_NATURALIZADO, qNacionalidad.getResponseYes().isChecked()));
            } else {
                registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_ERES_MEXICANO_NATURALIZADO, qNacionalidad.getResponseNo().isChecked(), country.getId()));
            }
        }
    }

    private void showDialogList(List<Paises> paises) {
        CountriesDialogFragment dialogFragment = CountriesDialogFragment.newInstance(paises);
        dialogFragment.setOnCountrySelectedListener(this);
        dialogFragment.show(getChildFragmentManager(), "FragmentDialog");
    }


    @Override
    public void onCountrySelectedListener(Paises item) {
        country = item;
        states.getEditText().setText(country.getPais());
        states.getInputLayout().setBackgroundResource(R.drawable.inputtext_normal);
    }

    @Override
    public void onClick(View view) {
        validateForm();
    }

    //spinner
    @Override
    public void onClick(Object item) {
        textParentesco = (Giros) item;
        parentesco.getView().setBackgroundResource(R.drawable.inputtext_normal);
    }

    //Lista
    private ArrayList<Paises> getListPaises() {
        try {
            return (ArrayList<Paises>) new DatabaseManager().getPaisesList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
