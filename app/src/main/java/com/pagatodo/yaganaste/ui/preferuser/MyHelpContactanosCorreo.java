package com.pagatodo.yaganaste.ui.preferuser;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EnviarCorreoContactanosRequest;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IProgressView;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyHelpMensajeContactanos;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.R.id.progressLayout;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHelpContactanosCorreo extends GenericFragment implements View.OnClickListener,
        IMyHelpMensajeContactanos {

    PreferUserPresenter mPreferPresenter;
    View rootview;
    private String mensaje = "";
    @BindView(R.id.edtxtUserName)
    StyleEdittext editbodyemail;

    @BindView(R.id.btn_continue)
    StyleButton btncontinuee;

    String contenidoemail = "";


    @Override
    public void onAttach(Context context) {
        mPreferPresenter = ((PreferUserActivity) getActivity()).getPreferPresenter();
        mPreferPresenter.setIView(this);
        super.onAttach(context);
    }

    public MyHelpContactanosCorreo() {
        // Required empty public constructor
    }

    public static MyHelpContactanosCorreo newInstance() {

        MyHelpContactanosCorreo fragmentcontactanoscorreo = new MyHelpContactanosCorreo();
        return fragmentcontactanoscorreo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_help_contactanos_correo, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                contenidoemail = editbodyemail.getText().toString();
                valida();

                break;

        }

    }

    private void valida() {
        if (contenidoemail.isEmpty()) {
            UI.createSimpleCustomDialog("Ya Ganaste", getString(R.string.correo_vacio), getFragmentManager(), getFragmentTag());
        }
        if (!contenidoemail.isEmpty()) {

            EnviarCorreoContactanosRequest mensajeRequest = new EnviarCorreoContactanosRequest(contenidoemail);
            // Enviamos al presenter
            //   onValidationSuccess();
            showLoader(getString(R.string.enviando_mensaje));
            mPreferPresenter.enviarCorreoContactanosPresenter(mensajeRequest);


            //  UI.createSimpleCustomDialog("Ya Ganaste", "Enviando Correo:  "+contenidoemail, getFragmentManager(), getFragmentTag());
        }


    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btncontinuee.setOnClickListener(this);
    }

    @Override
    public void showLoader(String title) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, title);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void sendErrorEnvioCorreoContactanos(String mensaje) {
        showDialogMesage(mensaje);
        hideLoader();
    }

    @Override
    public void sendSuccessMensaje(String mensaje) {
        showDialogMesage(mensaje);
        hideLoader();
        editbodyemail.setText("");
    }

    @Override
    public void showExceptionToView(String mMesage) {
        showDialogMesage(mMesage);
    }

    private void showDialogMesage(final String mensaje) {
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
