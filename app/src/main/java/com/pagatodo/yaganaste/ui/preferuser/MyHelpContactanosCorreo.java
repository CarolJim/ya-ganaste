package com.pagatodo.yaganaste.ui.preferuser;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EnviarCorreoContactanosRequest;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._controllers.TarjetaActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyHelpMensajeContactanos;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.ui.tarjeta.TarjetaUserPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHelpContactanosCorreo extends GenericFragment implements View.OnClickListener,
        IMyHelpMensajeContactanos {

    TarjetaUserPresenter mPreferPresenter;
    PreferUserPresenter mPreferPresenter2;
    View rootview;
    private String mensaje = "";
    @BindView(R.id.edtxtUserName)
    StyleEdittext editbodyemail;

    @BindView(R.id.btn_continue)
    StyleButton btncontinuee;

    String contenidoemail = "";


    @Override
    public void onAttach(Context context) {
        if (getActivity() instanceof TarjetaActivity) {
            mPreferPresenter = ((TarjetaActivity) getActivity()).getmPreferPresenter();
            mPreferPresenter.setIView(this);
        } else {
            mPreferPresenter2 = ((PreferUserActivity) getActivity()).getPreferPresenter();
            mPreferPresenter2.setIView(this);
        }
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
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            if (contenidoemail.isEmpty()) {
                //UI.createSimpleCustomDialog("Ya Ganaste", getString(R.string.correo_vacio), getFragmentManager(), getFragmentTag());
                UI.showAlertDialog(getContext(), getString(R.string.app_name), getString(R.string.correo_vacio), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

            }
            if (!contenidoemail.isEmpty()) {
                EnviarCorreoContactanosRequest mensajeRequest = new EnviarCorreoContactanosRequest(contenidoemail);
                // Enviamos al presenter
                //   onValidationSuccess();
                showLoader(getString(R.string.enviando_mensaje));
                if (getActivity() instanceof TarjetaActivity) {
                    mPreferPresenter.enviarCorreoContactanosPresenter(mensajeRequest);
                } else {
                    mPreferPresenter2.enviarCorreoContactanosPresenter(mensajeRequest);
                }
            }
        } else {
            showDialogMesage(getString(R.string.no_internet_access), false);
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
        showDialogMesage(getString(R.string.email_report_error_text), false);
        hideLoader();
    }

    @Override
    public void sendSuccessMensaje(String mensaje) {
        showDialogMesage(mensaje, true);
        hideLoader();
        editbodyemail.setText("");
    }

    @Override
    public void showExceptionToView(String mMesage) {
        showDialogMesage(mMesage, false);
    }

    private void showDialogMesage(final String mensaje, final boolean success) {
        UI.createSimpleCustomDialog(getString(R.string.email_report_succes_title), mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        if (success) {
                            if (getActivity() instanceof TarjetaActivity) {
                                ((TarjetaActivity) getActivity()).onBackPressed();
                            } else {
                                ((PreferUserActivity) getActivity()).onBackPressed();
                            }
                        }
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }
}
