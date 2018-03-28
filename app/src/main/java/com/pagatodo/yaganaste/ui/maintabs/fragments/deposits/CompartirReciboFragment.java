package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ShareEvent;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.adquirente.presenters.AdqPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.yaganaste.ui._controllers.DetailsActivity.EVENT_CLOSE_ACT;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.DEBUG;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_SESSION;

public class CompartirReciboFragment extends GenericFragment implements ValidationForms,
        INavigationView {


    @BindView(R.id.edtCorreoRegistrado)
    CustomValidationEditText edtCorreoRegistrado;
    @BindView(R.id.errorMessage)
    ErrorMessage errorMessage;

    private DataMovimientoAdq dataMovimientoAdq;
    private ImageView imageViewshare;
    private String emailToSend = "";
    private View rootview;
    private Preferencias prefs;
    private AdqPresenter adqPresenter;

    public CompartirReciboFragment() {
        // Required empty public constructor
    }

    public static CompartirReciboFragment newInstance(DataMovimientoAdq dataMovimientoAdq) {
        CompartirReciboFragment fragment = new CompartirReciboFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, dataMovimientoAdq);
        fragment.setArguments(args);
        return fragment;
    }

    public static GenericFragment newInstance() {
        CompartirReciboFragment fragment = new CompartirReciboFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageViewshare = (ImageView) getActivity().findViewById(R.id.deposito_Share);
        imageViewshare.setVisibility(View.GONE);
        adqPresenter = new AdqPresenter(this);

        Bundle args = getArguments();
        if (args != null) {
            dataMovimientoAdq = (DataMovimientoAdq) args.getSerializable(DetailsActivity.DATA);
        } else {
            throw new IllegalCallException(CompartirReciboFragment.class.getSimpleName() + "must be called by newInstance factory method");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_compartir_recibo, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        prefs = App.getInstance().getPrefs();

        edtCorreoRegistrado.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!edtCorreoRegistrado.isValidText()) {
                    hideValidationError(0);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @OnClick(R.id.btnLoginExistUser)
    public void sendMail() {
        validateForm();
    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        getDataForm();
        if (prefs.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty()) {
            if (emailToSend.isEmpty()) {
                showValidationError(edtCorreoRegistrado.getId(), getString(R.string.ingresa_correo_registrado_requerido));
                edtCorreoRegistrado.setIsInvalid();
            } else if (!edtCorreoRegistrado.isValidText()) {
                showValidationError(edtCorreoRegistrado.getId(), getString(R.string.datos_usuario_correo_formato));
                edtCorreoRegistrado.setIsInvalid();
            } else {
                onValidationSuccess();
            }
        }
    }

    @Override
    public void showValidationError(int id, Object error) {
        errorMessage.setMessageText(error.toString());
    }

    @Override
    public void hideValidationError(int id) {
        errorMessage.setVisibilityImageError(false);
    }

    @Override
    public void onValidationSuccess() {
        String description = dataMovimientoAdq.getNombre();
        //String description = dataMovimientoAdq.getConcepto();
        String getIdTransaction = dataMovimientoAdq.getIdTransaction();
        //  getIdTransaction = "";
        if (!DEBUG) {
            Answers.getInstance().logShare(new ShareEvent());
        }
        adqPresenter.sendTicketShare(emailToSend,
                "",
                getIdTransaction
        );
    }

    @Override
    public void getDataForm() {
        emailToSend = edtCorreoRegistrado.getText().trim();
    }

    @Override
    public void nextScreen(String event, Object data) {
        showDialogMesage(data.toString(), 1);
    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(Object error) {
        showDialogMesage(error.toString(), 0);
    }

    private void showDialogMesage(final String mensaje, final int mControl) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        if (mControl == 1) {
                            onEventListener.onEvent(EVENT_CLOSE_ACT, null);
                        }
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }
}
