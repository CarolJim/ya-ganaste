package com.pagatodo.yaganaste.ui.preferuser;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;

/**
 * Encargada de gestionar el cambio de contraseña, los elementos graficos de la vista y enviar al MVP
 */
public class MyPassFragment extends GenericFragment implements IMyPassView, View.OnClickListener {

    @BindView(R.id.fragment_myemail_btn)
    StyleButton sendButton;

    View rootview;

    PreferUserPresenter mPreferPresenter;

    public MyPassFragment() {
        // Required empty public constructor
    }

    public static MyPassFragment newInstance() {
        MyPassFragment fragment = new MyPassFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        mPreferPresenter = ((PreferUserActivity) getActivity()).getPreferPresenter();
        mPreferPresenter.setIView(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_pass, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_myemail_btn:
                onValidationSuccess();
                break;
        }
    }

    // TODO Pendiente todo el proceso de validaciones de la Pass
    private void onValidationSuccess() {
        // Deshabilitamos el backButton
        //getActivity().onBackPressed();
        onEventListener.onEvent("DISABLE_BACK", true);

        //   mPreferPresenter.sendChangePassToPresenter();
        mPreferPresenter.changePassToPresenter("oldmail@g.com", "newmail@g.com");
    }

    public void hideLoader() {
        // progressLayout.setVisibility(GONE);
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
    }

    /**
     * Exito en la peticion de servidor y exito en el cambio de Pass
     * @param mensaje
     */
    @Override
    public void sendSuccessPassToView(String mensaje) {
        showDialogMesage(mensaje);
        hideLoader();
        onEventListener.onEvent("DISABLE_BACK", false);
    }

    /**
     *  Exito en la peticion de servidor y Fail en el cambio de Pass.
     *  Tambien se usa para mostrar un error de conexion al servidor, desde el Presenter para no tener
     *  un tercer metodo
     * @param mensaje
     */
    @Override
    public void sendErrorPassToView(String mensaje) {
        showDialogMesage(mensaje);
        hideLoader();
        onEventListener.onEvent("DISABLE_BACK", false);
    }

    /**
     * Mostramos un mensaje simple con el String que necesitemos, sin acciones, solo aceptar
     * @param mensaje
     */
    private void showDialogMesage(String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {

                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }
}
