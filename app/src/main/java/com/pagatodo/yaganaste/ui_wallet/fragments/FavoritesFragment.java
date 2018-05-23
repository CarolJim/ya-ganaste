package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui_wallet.adapters.TypeSpinnerAdapter;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.builders.BuildFavorite;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.builders.FavoriteBuilder;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions.InputDataNode;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions.SpinnerNode;
import com.pagatodo.yaganaste.ui_wallet.holders.InputDataViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.SpinnerHolder;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.TransferType.CLABE;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.NUMERO_TELEFONO;
import static com.pagatodo.yaganaste.interfaces.enums.TransferType.QR_CODE;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ADDFAVORITE_PAYMENT;

public class FavoritesFragment extends SupportFragment implements AdapterView.OnItemSelectedListener {

    public static String OPERATION = "OPERATION";
    private View rootView;

    @BindView(R.id.container)
    LinearLayout container;
    BuildFavorite builder;
    SpinnerNode spinnerNode;
    InputDataNode inputDataNode;

    private int idOperation;

    public static FavoritesFragment newInstance(int idOperation) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(OPERATION, idOperation);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        initViews();
        return this.rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, this.rootView);
        //BuildFavorite builder = new BuildFavorite();
        FavoriteBuilder builderF = new FavoriteBuilder(getContext(),container);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        builder = new BuildFavorite(getContext());
        if (getArguments() != null) {
            this.idOperation = getArguments().getInt(OPERATION);
        }

        if (this.idOperation != 0) {
            /*InputDataViewHolder inputDataViewHolder = new InputDataViewHolder(inflater.inflate(R.layout.inputdata, container, false));
            inputDataViewHolder.getEditText().getText();
            BuildFavorite.build(builderF,getString(R.string.favorte_name),inputDataViewHolder,null);
            BuildFavorite.build(builderF,getDataAdapter(),new SpinnerHolder(inflater.inflate(R.layout.spinner_holder, container, false)),null);
            BuildFavorite.build(builderF,getString(R.string.txt_referencia_servicio),new InputDataViewHolder(inflater.inflate(R.layout.inputdata, container, false)),null);*/
            if (this.idOperation == OPTION_ADDFAVORITE_PAYMENT){

                //builder.setText(R.string.addFavorites).inflate(container);
                InputDataNode namFavorite = builder.setInputData(R.string.favorte_name,0);
                namFavorite.inflate(container);
                EditText editText = namFavorite.getEditText();
                editText.setText("Hola");
                spinnerNode = builder.setSpinnerNode(1);
                spinnerNode.inflate(container);
                spinnerNode.getSpinner().setOnItemSelectedListener(this);
                inputDataNode = builder.setInputData(R.string.tipo_de_envio,0);
                inputDataNode.inflate(container);
                inputDataNode.getContainer().setVisibility(View.GONE);
                //builder.addFavoriteLayout().inflate(container);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != 0) {
            inputDataNode.getInputLayout().setHint(getContext().getResources().getString(spinnerNode.getRes(i)));
            inputDataNode.getContainer().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
