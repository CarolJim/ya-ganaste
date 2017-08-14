package com.pagatodo.yaganaste.ui.adquirente;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;
import com.pagatodo.yaganaste.interfaces.IAccountPresenterNew;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.LandingApprovedActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.adquirente.interfases.IDocumentApproved;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DocumentApprovedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentApprovedFragment extends GenericFragment implements
        SwipeRefreshLayout.OnRefreshListener, IDocumentApproved {

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private View rootview;
    private AccountPresenterNew accountPresenter;

    public DocumentApprovedFragment() {
        // Required empty public constructor
    }

    public static DocumentApprovedFragment newInstance() {
        DocumentApprovedFragment fragment = new DocumentApprovedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  iAccountPresenterNew = new AccountPresenterNew(getContext());
        accountPresenter = new AccountPresenterNew(getContext());
        accountPresenter.setIView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_document_approved, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        refreshContent();
    }

    private void refreshContent() {
       // Toast.makeText(getContext(), "Update Fragment", Toast.LENGTH_SHORT).show();
       // showLoader("Verificando Estado");
        accountPresenter.updateUserInfo();
    }


    @Override
    public void nextScreen(String event, Object data) {

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
        hideLoader();
    }

    @Override
    public void dataUpdated(String mResult) {
        hideLoader();

        SingletonUser user = SingletonUser.getInstance();
        DataIniciarSesion dataUser = user.getDataUser();
        String myVar = dataUser.getUsuario().getTokenSesionAdquirente();
        if(myVar != null && !myVar.isEmpty()){
            getActivity().finish();
            Intent intent = new Intent(getContext(), LandingApprovedActivity.class);
            startActivity(intent);
        }
    }
}
