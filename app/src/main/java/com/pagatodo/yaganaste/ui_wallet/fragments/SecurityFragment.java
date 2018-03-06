package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.Builder.Container;
import com.pagatodo.yaganaste.ui_wallet.Builder.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.adapters.MenuAdapter;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomRadioButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.FINGERPRINT_SERVICE;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_NOTIFICACIONES;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_DESASOCIAR;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PASS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CCAMBIAR_PASS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_DESVINCULAR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_NOTIFICACIONES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RADIOBUTTON;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RAW;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecurityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecurityFragment extends SupportFragment implements OptionMenuItem.OnMenuItemClickListener {

    public static String MENU = "MENU";
    public static String MENSAJE = "MENSAJE";
    public static int MENU_SEGURIDAD = 1;
    public static int MENU_AJUSTES = 2;
    public static int MENU_TERMINOS = 3;
    public static int MENU_LOGOUT = 4;
    public static int MENU_CODE = 5;
    final public static int MENU_NOTIFICACIONES = 3;
    private int TYPE_MENU;

    @BindView(R.id.content_linearlayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.title_menu)
    StyleTextView titleMenu;
    @BindView(R.id.notific_discreption)
    StyleTextView notificDiscreption;
    private boolean useFingerprint = true;
    public CustomRadioButton radioButtonNo;
    public CustomRadioButton radioButtonSi;
    public String msj;

    protected OnEventListener onEventListener;

    public static SecurityFragment newInstance(int menu, String msj) {
        SecurityFragment securityFragment = new SecurityFragment();
        Bundle args = new Bundle();
        args.putInt(MENU, menu);
        args.putString(MENSAJE,msj);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
            if (!getArguments().getString(MENSAJE).isEmpty()){
                msj = getArguments().getString(MENSAJE);
                UI.showSuccessSnackBar(getActivity(),msj,Snackbar.LENGTH_SHORT);
            }
        }

        switch (TYPE_MENU) {
            case 1:
                initComponents(1);
                break;
            case 2:
                //listView.setAdapter(ContainerBuilder.SETTINGS_MENU(getContext(),this));
                titleMenu.setText(getContext().getResources().getString(R.string.navigation_drawer_menu_ajustes));
                ContainerBuilder.SETTINGS_MENU(getContext(),mLinearLayout,this);
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
    public void OnMenuItem(OptionMenuItem optionMenuItem) {
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
            default:
                break;
        }
    }

    private void initComponents(int type){
        switch (type) {
            case 1:
                useFingerprint = App.getInstance().getPrefs().loadDataBoolean(USE_FINGERPRINT, true);
                Container s = new Container(getContext(),this);
                s.addOptionMenuSegurity(mLinearLayout,new OptionMenuItem(ID_CCAMBIAR_PASS, R.string.change_your_pass,0, RAW));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    FingerprintManager fingerprintManager = (FingerprintManager) getActivity().getSystemService(FINGERPRINT_SERVICE);

                    if (fingerprintManager.isHardwareDetected()) {
                        s.addOptionMenuSegurity(mLinearLayout,new OptionMenuItem(-1, R.string.security_huella_option, R.string.security_huella_option_subtitle, RADIOBUTTON));
                        OptionMenuItem.ViewHolderMenuSegurity view = s.getArrayListOptionMenuSegurity().get(1);
                        radioButtonNo = view.radioButtonNo;
                        radioButtonSi = view.radioButtonSi;
                        setStates();
                        radioButtonSi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b){
                                    App.getInstance().getPrefs().saveDataBool(USE_FINGERPRINT, true);
                                }
                            }
                        });
                        radioButtonNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b){
                                    App.getInstance().getPrefs().saveDataBool(USE_FINGERPRINT, false);
                                }
                            }
                        });
                    }
                }
                break;
        }
    }

    private void setStates(){
        if (useFingerprint){
            radioButtonSi.setChecked(true);
        } else {
            radioButtonNo.setChecked(true);
        }
    }
}
