package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneTiposReembolsoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.TiposReembolsoResponse;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.TypesRepaymentAdapter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ITimeRepaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ITimeRepaymentView;
import com.pagatodo.yaganaste.ui_wallet.presenter.TimeRepaymentPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_CONFIG_REPAYMENT_BACK;

/**
 * Created by Omar on 22/02/2018.
 */

public class TimeRepaymentFragment extends GenericFragment implements ITimeRepaymentView, OnRecyclerItemClickListener {

    private View rootView;
    @BindView(R.id.rcv_type_repayments)
    RecyclerView rcvTypeRepayments;
    @BindView(R.id.btn_save_type_repayment)
    StyleButton btnSave;

    private ITimeRepaymentPresenter timeRepaymentPresenter;
    private List<TiposReembolsoResponse> tiposReembolso;
    private TypesRepaymentAdapter adapter;
    private int idTypeServer;
    private static int idTypeLocal;

    public static String tipo_reeembolso;

    public static TimeRepaymentFragment newInstance() {
        TimeRepaymentFragment timeRepaymentFragment = new TimeRepaymentFragment();
        Bundle bundle = new Bundle();
        timeRepaymentFragment.setArguments(bundle);
        return timeRepaymentFragment;
    }

    public static TimeRepaymentFragment newInstance(String tipo ) {
        TimeRepaymentFragment timeRepaymentFragment = new TimeRepaymentFragment();
        Bundle bundle = new Bundle();
        timeRepaymentFragment.setArguments(bundle);
        tipo_reeembolso = tipo;
        //idTypeLocal = tipo;
        return timeRepaymentFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        timeRepaymentPresenter = new TimeRepaymentPresenter(this);
        tiposReembolso = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_repayment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    public void reembolso(){
        if (Utils.isDeviceOnline()) {
            if (idTypeLocal != idTypeServer) {
                timeRepaymentPresenter.updateTypeRepayment(idTypeLocal);
            } else {
                UI.showSuccessSnackBar(getActivity(), getString(R.string.success_time_repayment_save), Snackbar.LENGTH_SHORT);
            }
        } else {
            UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(App.getContext(), LinearLayoutManager.VERTICAL, false);
        rcvTypeRepayments.setLayoutManager(mLayoutManager);
        rcvTypeRepayments.setHasFixedSize(true);
        timeRepaymentPresenter.getTypePayments();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isDeviceOnline()) {
                    if (idTypeLocal != idTypeServer) {
                        timeRepaymentPresenter.updateTypeRepayment(idTypeLocal);
                    } else {
                        UI.showSuccessSnackBar(getActivity(), getString(R.string.success_time_repayment_save), Snackbar.LENGTH_SHORT);
                    }
                } else {
                    UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    public void onSuccessGetTypes(ObtieneTiposReembolsoResponse response) {
        tiposReembolso = response.getReembolsos();
        List<TiposReembolsoResponse> lstTmp = new ArrayList<>();
        for (int i = 0; i < tiposReembolso.size(); i++) {
            // Agregar a lista temporal solo los Objetos visibles
            if (tiposReembolso.get(i).isVisible()) {
                lstTmp.add(tiposReembolso.get(i));
            }
            // Obtener el idTipoReembolso Configurado en el Servidor
            if (tiposReembolso.get(i).isConfigurado()) {
                idTypeServer = tiposReembolso.get(i).getID_TipoReembolso();
            }
        }
        adapter = new TypesRepaymentAdapter(lstTmp, this);
        rcvTypeRepayments.setAdapter(adapter);
    }

    @Override
    public void onSuccessUpdateType() {
        idTypeServer = idTypeLocal;
        UI.showSuccessSnackBar(getActivity(), getString(R.string.success_time_repayment_save), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onError(String error) {
        UI.showErrorSnackBar(getActivity(), error, Snackbar.LENGTH_LONG);
    }

    @Override
    public void showLoader(String message) {
        if (onEventListener != null) {
            onEventListener.onEvent(EVENT_SHOW_LOADER, message);
        }
    }

    @Override
    public void hideLoader() {
        if (onEventListener != null) {
            onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        }
    }

    @Override
    public void onRecyclerItemClick(View v, int position) {
        idTypeLocal = (int) adapter.getItemId(position);
    }
}
