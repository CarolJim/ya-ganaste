package com.pagatodo.yaganaste.ui.account.register;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_DATA_USER;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_LOGIN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_LOGIN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_REGISTER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class PermisosFragment extends GenericFragment implements View.OnClickListener, INavigationView {


    @BindView(R.id.btnPermissions)
    Button btnPermissions;
    @BindView(R.id.btnNextPermisos)
    Button btnNextPermisos;
    private View rootview;
    private String action = "";

    public PermisosFragment() {
    }

    public static PermisosFragment newInstance(String action) {
        PermisosFragment fragmentRegister = new PermisosFragment();
        Bundle args = new Bundle();
        args.putString(SELECTION, action);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
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
        rootview = inflater.inflate(R.layout.fragment_permisos, container, false);
        initViews();

             /*Validamos Permisos*/
        checkPermissions();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnPermissions.setOnClickListener(this);
        btnNextPermisos.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnPermissions:
                ValidatePermissions.openDetailsApp(getActivity(), PERMISSION_GENERAL);
                break;

            case R.id.btnNextPermisos:

                switch (action) {
                    case GO_TO_LOGIN:
                        nextScreen(EVENT_GO_LOGIN, null);
                        break;

                    case GO_TO_REGISTER:
                        nextScreen(EVENT_DATA_USER, null);
                        break;
                }
                break;
            default:
                break;
        }
    }

    public void setBtnPermissionsEnable(boolean isEnable) {
        btnPermissions.setEnabled(isEnable);
    }

    public void checkPermissions() {

        if (!ValidatePermissions.isAllPermissionsActives(getActivity(), ValidatePermissions.getPermissionsCheck())) {
            btnNextPermisos.setVisibility(GONE);
            btnPermissions.setText("Activar Servicios");
            btnPermissions.setBackgroundColor(getResources().getColor(R.color.transparent));

            ValidatePermissions.checkPermissions(getActivity(), new String[]{
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_GENERAL);
            //ValidatePermissions.showDialogPermission(getActivity(),getString(R.string.permission_request),getString(R.string.permission_request_desc),actionsDialog);
        } else {
            btnNextPermisos.setVisibility(VISIBLE);
            btnPermissions.setText("Servicios Activados");
            btnPermissions.setEnabled(false);
            btnPermissions.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public void nextScreen(String event, Object data) {

        onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {

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