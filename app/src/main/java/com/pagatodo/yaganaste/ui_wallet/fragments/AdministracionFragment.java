package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyCardReportaTarjetaFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyChangeNip;
import com.pagatodo.yaganaste.ui_wallet.Builder.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.adapters.MenuAdapter;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_CARD_REPORT;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_NIP_CHANGE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdministracionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdministracionFragment extends SupportFragment implements OptionMenuItem.OnMenuItemClickListener{

    @BindView(R.id.content_linearlayout)
    LinearLayout mLinearLayout;

    private View rootView;
    private MenuAdapter menuAdapter;

    public static AdministracionFragment newInstance() {
        return new AdministracionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_administracion_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, this.rootView);
        //menuAdapter = ContainerBuilder.ADMINISTRACION(getContext(),this);
        ContainerBuilder.ADMINISTRACION(getContext(),mLinearLayout,this);
        //listView.setAdapter(menuAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void OnMenuItem(OptionMenuItem optionMenuItem) {
        switch (optionMenuItem.getIdItem()){
            case 1:
                if (SingletonUser.getInstance().getCardStatusId().equals(Recursos.ESTATUS_CUENTA_DESBLOQUEADA)) {
                    onEventListener.onEvent(EVENT_GO_NIP_CHANGE,null);
                    //loadFragment(MyChangeNip.newInstance(), R.id.fragment_container, Direction.FORDWARD, false);
                } else {
                    showDialogMesage(getString(R.string.change_nip_block_card));
                }
                break;
            case 2:
                onEventListener.onEvent(EVENT_GO_CARD_REPORT,null);
                break;
            case 3:
                /**
                 * 1 - Validacion para operaciones con internet, en caso contrario regresamos los botones a
                 * su estado natural
                 * 2 - Validacion de statusOperation, si es True realizamos las oepraciones, si es False nos
                 * brincamos este paso, se usa cuando regresamos los botones a su estado natural, porque al
                 * regresarlos se actuva de nuevo el onCheckedChanged, con False evitamos un doble proceso
                 * 3 - If isChecked operaciones para realizar el Bloqueo, else operaciones de Desbloqueo
                 */

                //imgStatus.setImageResource(isChecked ? R.drawable.ic_candado_closed : R.drawable.ic_candado_open);

                /*boolean isOnline = Utils.isDeviceOnline();
                if (isOnline) {
                    if (statusOperation) {
                        if (optionMenuItem.isStatusSwtich()) {
                            // Toast.makeText(getContext(), "checked 1", Toast.LENGTH_SHORT).show();
                            mPreferPresenter.toPresenterBloquearCuenta(BLOQUEO);
                            statusBloqueo = BLOQUEO;
                        } else {
                            // Toast.makeText(getContext(), "Deschecked 2", Toast.LENGTH_SHORT).show();
                            mPreferPresenter.toPresenterBloquearCuenta(DESBLOQUEO);
                            statusBloqueo = DESBLOQUEO;
                        }
                    }
                } else {
                    showDialogCustom(getResources().getString(R.string.no_internet_access));
                    if (statusOperation) {
                        if (optionMenuItem.isStatusSwtich()) {
                            statusOperation = false;

                            menuAdapter.setStatus(false);
                            statusOperation = true;
                        } else {
                            statusOperation = false;

                            menuAdapter.setStatus(true);
                            statusOperation = true;
                        }
                    }
                }
                break;*/

        }
    }
}
