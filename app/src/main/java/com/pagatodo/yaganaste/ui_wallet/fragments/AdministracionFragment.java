package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.ui_wallet.patterns.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCard;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_CARD_REPORT;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_NIP_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdministracionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdministracionFragment extends SupportFragment implements OnClickItemHolderListener,
        IMyCardView {

    public static final int BLOQUEO = 1;
    public static final int DESBLOQUEO = 2;

    @BindView(R.id.content_linearlayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.imgYaGanasteCard)
    YaGanasteCard card;
    @BindView(R.id.ico_bloqueado)
    AppCompatImageView ico;

    private View rootView;

    RadioButton radioButtonSi;
    RadioButton radioButtonNo;
    private PreferUserPresenter mPreferPresenter;
    private int statusBloqueo;

    public static AdministracionFragment newInstance() {
        return new AdministracionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPreferPresenter = new PreferUserPresenter();
        mPreferPresenter.setIView(this);
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

        ArrayList<OptionMenuItem.ViewHolderMenuSegurity> listComponent = ContainerBuilder.ADMINISTRACION(getContext(), mLinearLayout, this);
        OptionMenuItem.ViewHolderMenuSegurity viewHolder = listComponent.get(2);
        if (App.getInstance().getPrefs().loadData(CARD_NUMBER).isEmpty()) {
            viewHolder.relativeLayout.setVisibility(GONE);
        }
        radioButtonSi = viewHolder.radioButtonSi;
        radioButtonNo = viewHolder.radioButtonNo;
        card.setCardNumber(App.getInstance().getPrefs().loadData(CARD_NUMBER));
        card.setCardName(App.getInstance().getPrefs().loadData(FULL_NAME_USER));
        String statusId = SingletonUser.getInstance().getCardStatusId();
        if (statusId != null && !statusId.isEmpty()) {
            checkState(statusId);

        } else {
            checkState(App.getInstance().getStatusId());
        }
        setOnchangeListener();

    }

    private void checkState(String state) {
        switch (state) {
            case Recursos.ESTATUS_CUENTA_DESBLOQUEADA:
                //mycard_switch.setChecked(false);
                card.setImageResource(R.drawable.tarjeta_yg);
                ico.setVisibility(GONE);
                radioButtonNo.setChecked(true);
                break;
            case Recursos.ESTATUS_CUENTA_BLOQUEADA:
                //mycard_switch.setChecked(true);
                card.setImageResource(R.mipmap.main_card_zoom_gray);
                ico.setVisibility(View.VISIBLE);
                radioButtonSi.setChecked(true);
                break;
            default:
                if (BuildConfig.DEBUG)
                    Log.d("ESTAUS", state);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Object optionMenuItem) {
        OptionMenuItem item = (OptionMenuItem) optionMenuItem;
        switch (item.getIdItem()) {
            case 1:
                if (SingletonUser.getInstance().getCardStatusId().equals(Recursos.ESTATUS_CUENTA_DESBLOQUEADA)) {
                    onEventListener.onEvent(EVENT_GO_NIP_CHANGE, null);
                } else {
                    showDialogMesage(getString(R.string.change_nip_block_card));
                }
                break;
            case 2:
                onEventListener.onEvent(EVENT_GO_CARD_REPORT, null);
                break;

        }
    }

    @Override
    public void showLoader(String s) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, s);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
    }

    @Override
    public void sendSuccessBloquearCuentaToView(BloquearCuentaResponse response) {

        String messageStatus = "";
        if (statusBloqueo == BLOQUEO) {
            messageStatus = getResources().getString(R.string.card_locked_success);
            SingletonUser.getInstance().setCardStatusId(Recursos.ESTATUS_CUENTA_BLOQUEADA);
            checkState(Recursos.ESTATUS_CUENTA_BLOQUEADA);
        } else if (statusBloqueo == DESBLOQUEO) {
            messageStatus = getResources().getString(R.string.card_unlocked_success);
            SingletonUser.getInstance().setCardStatusId(Recursos.ESTATUS_CUENTA_DESBLOQUEADA);
            checkState(Recursos.ESTATUS_CUENTA_DESBLOQUEADA);
        }

        UI.showSuccessSnackBar(getActivity(), messageStatus, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void sendErrorBloquearCuentaToView(String mensaje) {
        UI.showErrorSnackBar(getActivity(), getResources().getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
    }

    private void setOnchangeListener() {
        final boolean isOnline = Utils.isDeviceOnline();
        radioButtonSi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (isOnline) {
                        mPreferPresenter.toPresenterBloquearCuenta(BLOQUEO);
                        statusBloqueo = BLOQUEO;
                        //checkState(Recursos.ESTATUS_CUENTA_BLOQUEADA);
                    } else {
                        UI.showErrorSnackBar(getActivity(), getResources().getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
                        radioButtonSi.setChecked(false);
                        // Toast.makeText(getContext(), "Deschecked 2", Toast.LENGTH_SHORT).show();
                        //mPreferPresenter.toPresenterBloquearCuenta(DESBLOQUEO);
                        //statusBloqueo = DESBLOQUEO;
                    }
                }
            }
        });

        radioButtonNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (isOnline) {
                        mPreferPresenter.toPresenterBloquearCuenta(DESBLOQUEO);
                        statusBloqueo = DESBLOQUEO;
                    } else {
                        UI.showErrorSnackBar(getActivity(), getResources().getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
                        radioButtonNo.setChecked(false);
                    }
                }
            }
        });


    }
}
