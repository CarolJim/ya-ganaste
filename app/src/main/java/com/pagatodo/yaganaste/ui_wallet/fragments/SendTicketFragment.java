package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.adquirente.presenters.AdqPresenter;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsAdquirenteFragment.MovTab;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.modules.emisor.WalletMainActivity.EVENT_GO_TO_MOV_ADQ;


public class SendTicketFragment extends SupportFragment implements View.OnClickListener,
        INavigationView {

    public static String MOV_DET = "MOV_DET";
    private View rootview;
    private AdqPresenter adqPresenter;
    private DataMovimientoAdq itemMov;
    private MovTab movtab;
    @BindView(R.id.btnconfirmar)
    public StyleButton btnConfirmar;
    @BindView(R.id.txt_lyt_correo)
    public TextInputLayout lyt_correo;
    @BindView(R.id.edit_correo)
    public EditText editCorreo;
    @BindView(R.id.progress_details)
    ProgressLayout progressLayout;

    public static SendTicketFragment newInstance(MovTab mov) {
        SendTicketFragment fragment = new SendTicketFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MOV_DET, mov);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.adqPresenter = new AdqPresenter(this);
        if (getArguments() != null) {
            movtab = (MovTab) getArguments().getSerializable(MOV_DET);
            itemMov = movtab.getItemMov();
        } else {
            itemMov = null;
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootview = inflater.inflate(R.layout.fragment_sned_ticket, container, false);
        initViews();
        return this.rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, this.rootview);
        this.btnConfirmar.setOnClickListener(this);
        this.editCorreo.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                lyt_correo.setBackgroundResource(R.drawable.inputtext_active);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (editCorreo.getText().toString().isEmpty()) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.check_your_mail), Snackbar.LENGTH_SHORT);
        } else if (!ValidateForm.isValidEmailAddress(editCorreo.getText().toString())) {
            //showValidationError(getString(R.string.check_your_mail));
            lyt_correo.setBackgroundResource(R.drawable.inputtext_error);
            UI.showErrorSnackBar(getActivity(), getString(R.string.check_your_mail), Snackbar.LENGTH_SHORT);
        } else {
            //String idTransicion, String name, String email
            adqPresenter.sendTicket(itemMov.getIdTransaction(), itemMov.getNombre(), editCorreo.getText().toString(), false);
        }
    }

    @Override
    public void nextScreen(String event, Object data) {
        if (data.toString() == getString(R.string.recibo_enviado_line)){
            UI.showSuccessSnackBar(getActivity(), data.toString(), Snackbar.LENGTH_SHORT);
        } else {
            UI.showErrorSnackBar(getActivity(), data.toString(), Snackbar.LENGTH_SHORT);
        }
        this.onEventListener.onEvent(EVENT_GO_TO_MOV_ADQ, this.movtab);
    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void showLoader(String message) {
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(Object error) {
        UI.showErrorSnackBar(getActivity(), error.toString(), Snackbar.LENGTH_SHORT);
    }
}
