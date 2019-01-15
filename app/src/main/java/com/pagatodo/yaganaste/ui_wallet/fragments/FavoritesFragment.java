package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.builders.BuildFavorite;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions.InputDataNode;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions.SpinnerNode;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        if (getArguments() != null) {
            this.idOperation = getArguments().getInt(OPERATION);
        }
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
        builder = new BuildFavorite(getContext());

        if (this.idOperation == OPTION_ADDFAVORITE_PAYMENT){
            InputDataNode namFavorite = builder.setInputData(R.string.favorte_name,0);
            namFavorite.inflate(container);
            spinnerNode = builder.setSpinnerNode(1);
            spinnerNode.inflate(container);
            spinnerNode.getSpinner().setOnItemSelectedListener(this);
            inputDataNode = builder.setInputData(R.string.tipo_de_envio,0);
            inputDataNode.inflate(container);
            inputDataNode.getContainer().setVisibility(View.GONE);
            }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != 0) {
            inputDataNode.getInputLayout().setHint(getContext().getResources().getString(spinnerNode.getRes(i)));
            inputDataNode.getContainer().setVisibility(View.VISIBLE);
        } else {
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
