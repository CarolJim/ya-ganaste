
package com.pagatodo.yaganaste.ui.adquirente;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesionUYU;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.adquirente.interfases.IDocumentApproved;
import com.pagatodo.yaganaste.utils.UI;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_APPROVED;

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

        if (!UtilsNet.isOnline(getActivity())) {
            swipeRefreshLayout.setRefreshing(false);
            UI.showAlertDialog(getActivity(), getResources().getString(R.string.app_name), getResources().getString(R.string.no_internet_access),
                    R.string.title_aceptar, (dialogInterface, i) -> {
                    });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            refreshContent();
        }
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
        DataIniciarSesionUYU dataUser = user.getDataUser();
        String tokenSesionAdquirente = dataUser.getUsuario().getTokenSesionAdquirente();

        Preferencias prefs = App.getInstance().getPrefs();
        boolean isAdquirente = prefs.containsData(ADQUIRENTE_APPROVED);

        // Lineas de prueba, comentar al tener version lista para pruebas
        //tokenSesionAdquirente = "MiSuperTokenAdquirente";
        // isAdquirente = "";

        if (tokenSesionAdquirente != null && !tokenSesionAdquirente.isEmpty() && !isAdquirente) {
            Intent intent = new Intent(getActivity(), TabActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
