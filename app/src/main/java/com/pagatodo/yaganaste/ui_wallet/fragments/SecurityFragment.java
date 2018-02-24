package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.Builder.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.adapters.MenuAdapter;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_NOTIFICACIONES;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_DESASOCIAR;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PASS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CCAMBIAR_PASS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_DESVINCULAR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_NOTIFICACIONES;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecurityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecurityFragment extends SupportFragment implements OptionMenuItem.OnMenuItemClickListener {

    public static String MENU = "MENU";
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
        if (getArguments() != null) {
            TYPE_MENU = getArguments().getInt(MENU);
        }

        switch (TYPE_MENU) {
            case 1:
                ContainerBuilder.SECURITY_MENU(getContext(),mLinearLayout,this);
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
            case -1:
                break;
            default:

                break;
        }
    }

    /*
    @Override
    public void onItemClick(OptionMenuItem optionMenuItem) {

    }
    */
}
