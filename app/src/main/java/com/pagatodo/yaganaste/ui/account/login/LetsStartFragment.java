package com.pagatodo.yaganaste.ui.account.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountView;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterOld;
import com.pagatodo.yaganaste.utils.Validations;
import com.pagatodo.yaganaste.utils.customviews.MaterialButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * A simple {@link GenericFragment} subclass.
 */
public class LetsStartFragment extends GenericFragment implements View.OnClickListener,IAccountView {

    private View rootview;
    @BindView(R.id.edtxtLetsStartMail)
    public StyleEdittext edtUserEmail;
    @BindView(R.id.btnLetsStartNext)
    public MaterialButton btnValidateUser;
    private AccountPresenterOld accountPresenter;

    public LetsStartFragment() {
    }

    public static LetsStartFragment newInstance() {
        LetsStartFragment fragmentLetsStart = new LetsStartFragment();
        Bundle args = new Bundle();
        fragmentLetsStart.setArguments(args);
        return fragmentLetsStart;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = new AccountPresenterOld(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_lets_start, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnValidateUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnLetsStartNext:
                try {
                    validarUsuario();
                } catch (OfflineException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void validarUsuario() throws OfflineException {
        if (!edtUserEmail.getText().toString().replaceAll("\\s", "").isEmpty()) {
            if (Validations.isMail(edtUserEmail)) {
                    accountPresenter.initValidationLogin(edtUserEmail.getText().toString().trim());
            } else {
                Toast.makeText(getActivity(),"El Correo no es Válido", Toast.LENGTH_SHORT).show();
            }
        } else {
            edtUserEmail.setText("");
            Toast.makeText(getActivity(),"Introduce un Correo Válido (*)", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void nextStepAccountFlow(String event) {
        onEventListener.onEvent(event,null);
    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showError(Object error) {

    }
}