package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.Container;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomRadioButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.FINGERPRINT_SERVICE;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_NOTIFICACIONES;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_CANCELACION;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_DESASOCIAR;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PASS;
import static com.pagatodo.yaganaste.ui_wallet.patterns.builders.ContainerBuilder.SETTINGS_MENU;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CANCELACION;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CCAMBIAR_PASS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_DESVINCULAR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_NOTIFICACIONES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RADIOBUTTON;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RAW;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecurityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecurityFragment extends SupportFragment implements OnClickItemHolderListener {

    public static String MENU = "MENU";
    public static String MENSAJE = "MENSAJE";
    public static final int MENU_SEGURIDAD = 1;
    public static final int MENU_AJUSTES = 2;
    public static final int MENU_TERMINOS = 3;
    public static final int MENU_LOGOUT = 4;
    public static final int MENU_CODE = 5;
    public static final int MENU_CONTACTO = 6;
    final public static int MENU_NOTIFICACIONES = 3;
    private int TYPE_MENU;

    @BindView(R.id.content_linearlayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.title_menu)
    StyleTextView titleMenu;
    @BindView(R.id.notific_discreption)
    StyleTextView notificDiscreption;
    private boolean useFingerprint = true, showBalance = true;
    private Container container;
    public CustomRadioButton radioButtonNo;
    public CustomRadioButton radioButtonSi;
    public String msj;

    protected OnEventListener onEventListener;

    public static SecurityFragment newInstance(int menu, String msj) {
        SecurityFragment securityFragment = new SecurityFragment();
        Bundle args = new Bundle();
        args.putInt(MENU, menu);
        args.putString(MENSAJE, msj);
        securityFragment.setArguments(args);
        return securityFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListener) {
            this.onEventListener = (OnEventListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_security, container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        if (getArguments() != null) {
            TYPE_MENU = getArguments().getInt(MENU);
            if (!getArguments().getString(MENSAJE).isEmpty()) {
                msj = getArguments().getString(MENSAJE);
                UI.showSuccessSnackBar(getActivity(), msj, Snackbar.LENGTH_SHORT);
            }
        }

        switch (TYPE_MENU) {
            case MENU_SEGURIDAD:
                initComponents(TYPE_MENU);
                break;
            case MENU_AJUSTES:
                //listView.setAdapter(ContainerBuilder.SETTINGS_MENU(getContext(),this));
                titleMenu.setText(getContext().getResources().getString(R.string.navigation_drawer_menu_ajustes));
                container = ContainerBuilder.builder(getContext(), mLinearLayout, this, SETTINGS_MENU);
                initComponents(TYPE_MENU);
                break;
            case 3:
                titleMenu.setText(getContext().getResources().getString(R.string.navigation_drawer_menu_acerca));
                break;
            default:
                Toast.makeText(getContext(), "Intentar Mas Tarde", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(Object item) {
        OptionMenuItem optionMenuItem = (OptionMenuItem) item;
        switch (optionMenuItem.getIdItem()) {
            case ID_CCAMBIAR_PASS:
                onEventListener.onEvent(PREFER_USER_PASS, null);
                break;
            case ID_NOTIFICACIONES:
                onEventListener.onEvent(PREFER_NOTIFICACIONES, null);
                break;
            case ID_DESVINCULAR:
                onEventListener.onEvent(PREFER_USER_DESASOCIAR, null);
                break;
            case ID_CANCELACION:
                onEventListener.onEvent(PREFER_USER_CANCELACION, null);
                break;
            default:
                break;
        }
    }

    private void initComponents(int type) {
        switch (type) {
            case MENU_SEGURIDAD:
                useFingerprint = App.getInstance().getPrefs().loadDataBoolean(USE_FINGERPRINT, true);
                container = new Container(getContext(), this);
                container.addOptionMenuSegurity(mLinearLayout, new OptionMenuItem(ID_CCAMBIAR_PASS, R.string.change_your_pass, 0, RAW));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    FingerprintManager fingerprintManager = (FingerprintManager) getActivity().getSystemService(FINGERPRINT_SERVICE);

                    if (fingerprintManager.isHardwareDetected() &&
                            SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol() != 129) {
                        container.addOptionMenuSegurity(mLinearLayout, new OptionMenuItem(-1, R.string.security_huella_option, R.string.security_huella_option_subtitle, RADIOBUTTON));
                        OptionMenuItem.ViewHolderMenuSegurity view = container.getArrayListOptionMenuSegurity().get(1);
                        radioButtonNo = view.radioButtonNo;
                        radioButtonSi = view.radioButtonSi;
                        setStates(type);
                        radioButtonSi.setOnCheckedChangeListener((compoundButton, b) -> {
                            if (b) {
                                App.getInstance().getPrefs().saveDataBool(USE_FINGERPRINT, true);
                            }
                        });
                        radioButtonNo.setOnCheckedChangeListener((compoundButton, b) -> {
                            if (b) {
                                App.getInstance().getPrefs().saveDataBool(USE_FINGERPRINT, false);
                            }
                        });
                    }
                }
                break;
            case MENU_AJUSTES:
                showBalance = App.getInstance().getPrefs().loadDataBoolean(SHOW_BALANCE, true);
                OptionMenuItem.ViewHolderMenuSegurity view = container.getArrayListOptionMenuSegurity().get(1);
                radioButtonNo = view.radioButtonNo;
                radioButtonSi = view.radioButtonSi;
                setStates(type);
                radioButtonSi.setOnCheckedChangeListener((compoundButton, b) -> {
                    if (b) {
                        App.getInstance().getPrefs().saveDataBool(SHOW_BALANCE, true);
                    }
                });
                radioButtonNo.setOnCheckedChangeListener((compoundButton, b) -> {
                    if (b) {
                        App.getInstance().getPrefs().saveDataBool(SHOW_BALANCE, false);
                    }
                });
                break;
        }
    }

    private void setStates(int type) {
        switch (type) {
            case MENU_SEGURIDAD:
                if (useFingerprint) {
                    radioButtonSi.setChecked(true);
                } else {
                    radioButtonNo.setChecked(true);
                }
                break;
            case MENU_AJUSTES:
                if (showBalance) {
                    radioButtonSi.setChecked(true);
                } else {
                    radioButtonNo.setChecked(true);
                }
                break;
        }
    }
}
