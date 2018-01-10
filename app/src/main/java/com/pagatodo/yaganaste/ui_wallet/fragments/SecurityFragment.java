package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.MenuAdapter;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.FINGERPRINT_SERVICE;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_NOTIFICACIONES;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_DESASOCIAR;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_EMAIL;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PASS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CCAMBIAR_PASS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_DESVINCULAR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_NOTIFICACIONES;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecurityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecurityFragment extends SupportFragment implements MenuAdapter.OnItemClickListener{

    public static String MENU = "MENU";
    public static int MENU_SEGURIDAD = 1;
    public static int MENU_AJUSTES = 2;
    public static int MENU_TERMINOS = 3;
    final public static int MENU_NOTIFICACIONES = 3;
    private int TYPE_MENU;

    @BindView(R.id.security_item)
    ListView listView;
    @BindView(R.id.title_menu)
    StyleTextView titleMenu;
    @BindView(R.id.notific_discreption)
    StyleTextView notificDiscreption;

    protected OnEventListener onEventListener;

    public static SecurityFragment newInstance(int menu) {
        SecurityFragment securityFragment = new SecurityFragment();
        Bundle args = new Bundle();
        args.putInt(MENU, menu);
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
        if (getArguments() != null){
            TYPE_MENU = getArguments().getInt(MENU);
        }

        switch (TYPE_MENU){
            case 1:
                listView.setAdapter(new MenuAdapter(getContext(),new OptionMenuItem(getContext()).SECURITY_MENU(),this));
                //listView.setOnItemClickListener(this);
                break;
            case 2:
                listView.setAdapter(new MenuAdapter(getContext(),new OptionMenuItem(getContext()).SETTINGS_MENU(),this));
                titleMenu.setText(getContext().getResources().getString(R.string.navigation_drawer_menu_ajustes));
                break;
            case 3:
                titleMenu.setText(getContext().getResources().getString(R.string.navigation_drawer_menu_acerca));
                //listView.setAdapter(new MenuAdapter(getContext(),new OptionMenuItem(getContext()).SETTINGS_NOTIFICACIONES(),this));
                break;

                default:
                    Toast.makeText(getContext(),"Intentar Mas Tarde",Toast.LENGTH_SHORT).show();
                    break;
        }
    }


    @Override
    public void onItemClick(OptionMenuItem optionMenuItem) {
        switch (optionMenuItem.getIdItem()){
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
}
