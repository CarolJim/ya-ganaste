package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.ui._controllers.AdqActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.modules.emisor.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.holders.GenericHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.TYPE_TRANSACTION;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.PICK_WALLET_TAB_REQUEST;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.RESULT_CODE_SELECT_DONGLE;
import static com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment.ITEM_OPERATION;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_BALANCE_CLOSED_LOOP;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;

public class OperadorTabFragment extends SupportFragment implements OnClickItemHolderListener {

    private View viewRoot;
    @BindView(R.id.container_elements_otro)
    LinearLayout conteainerElementsOtro;
    @BindView(R.id.container_elements)
    LinearLayout conteainerElements;
    @BindView(R.id.wallet)
    ImageView wallet;
    @BindView(R.id.title_negocio)
    StyleTextView titleNegocio;
    private ElementView element;

    public static OperadorTabFragment newInstance() {
        return new OperadorTabFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.operador_tab_layout, container, false);
        initViews();
        return viewRoot;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, viewRoot);
        createWallet();
    }

    private void createWallet() {
        Agentes agentes = getAgenete();
        boolean isBluetooth = App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal();
        this.wallet.setImageResource(isBluetooth ? R.drawable.chip_pin : R.mipmap.lector_front);
        this.titleNegocio.setText(agentes.getNombreNegocio());
        this.titleNegocio.setVisibility(isBluetooth ? View.VISIBLE : View.GONE);

        ArrayList<ElementView> operadores = ElementView.getListLectorOperador(agentes.getIdEstatus(),
                agentes.getOperadores(), agentes.getNombreNegocio(), agentes.getNumeroAgente(),
                "" + agentes.getIdComercio(), agentes.isEsComercioUYU());

        for (int i = 0; i < operadores.size() - 1; i++) {
            addViewHolder(operadores.get(i));
            if (i != operadores.size() - 1)
                addView();
        }
        addViewHolderOtro(operadores.get(operadores.size() - 1));
    }

    private Agentes getAgenete() {
        Agentes agentes = null;
        if (SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes() != null && !SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().isEmpty()) {
            for (int i = 0; i < SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().size(); i++) {
                try {
                    agentes = new DatabaseManager().getAgenteByComercio(SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().get(i).getIdComercio());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    agentes = SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().get(i);
                }
            }
        }
        return agentes;
    }

    private void addViewHolderOtro(ElementView elementView) {
        GenericHolder holder = ContainerBuilder.getViewHolder(getActivity(), conteainerElements, -1);
        holder.bind(elementView, this);
        conteainerElementsOtro.addView(holder.getView());
    }

    private void addViewHolder(ElementView elementView) {
        GenericHolder holder = ContainerBuilder.getViewHolder(getActivity(), conteainerElements, -1);
        holder.bind(elementView, this);
        conteainerElements.addView(holder.getView());
    }

    private void addView() {
        View view = new View(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                4, 1);
        view.setLayoutParams(params);
        conteainerElements.addView(view);
    }

    @Override
    public void onItemClick(Object item) {
        ElementView elementView = (ElementView) item;
        this.element = elementView;
        if (elementView.getIdOperacion() == OPTION_BALANCE_CLOSED_LOOP) {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            if (!adapter.isEnabled()) {
                Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enabler);
            } else {
                /*if (App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()
                        && App.getInstance().getPrefs().loadData(BT_PAIR_DEVICE).equals("")) {
                    element.setIdOperacion(OPTION_CONFIG_DONGLE);
                    UI.showErrorSnackBar(getActivity(), getString(R.string.please_config_dongle), Snackbar.LENGTH_SHORT);
                    goToWalletMainActivity();
                } else {*/
                    UI.showAlertDialog(getContext(), getString(R.string.consultar_saldo_uyu_title), getString(R.string.consultar_saldo_uyu_desc),
                            getString(R.string.consultar_saldo_uyu_btn), (dialogInterface, i) -> {
                                TransactionAdqData.getCurrentTransaction().setAmount("");
                                TransactionAdqData.getCurrentTransaction().setDescription("");
                                Intent intentAdq = new Intent(getContext(), AdqActivity.class);
                                intentAdq.putExtra(TYPE_TRANSACTION, QPOSService.TransactionType.INQUIRY.ordinal());
                                startActivity(intentAdq);
                            });
                //}
            }
        } else {
            goToWalletMainActivity();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PICK_WALLET_TAB_REQUEST || resultCode == RESULT_CODE_SELECT_DONGLE) {
            this.conteainerElements.removeAllViews();
            this.conteainerElementsOtro.removeAllViews();
            createWallet();
        }
    }

    private void goToWalletMainActivity() {
        Intent intent = new Intent(getContext(), WalletMainActivity.class);
        intent.putExtra(ITEM_OPERATION, element);
        startActivityForResult(intent, PICK_WALLET_TAB_REQUEST);
    }
}
