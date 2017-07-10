package com.pagatodo.yaganaste.ui._controllers.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;


/**
 * Created by jguerras on 29/11/2016.
 * Updated by flima on 8/02/2017.
 */

public abstract class SupportFragmentActivity extends AppCompatActivity implements OnEventListener {

    private SupportComponent mSupportComponent;
    public static final String EVENT_SESSION_EXPIRED = "EVENT_SESSION_EXPIRED";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSupportComponent = new SupportComponent(getSupportFragmentManager());
        setTitle("");
        /*Validamos Permisos*/
        checkPermissions();
    }

    protected void loadFragment(@NonNull GenericFragment fragment) {
        mSupportComponent.loadFragment(fragment, R.id.container, Direction.NONE, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer) {
        mSupportComponent.loadFragment(fragment, idContainer, Direction.NONE, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, boolean addToBackStack) {
        mSupportComponent.loadFragment(fragment, R.id.container, Direction.NONE, addToBackStack);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, boolean addToBackStack) {
        mSupportComponent.loadFragment(fragment, idContainer, Direction.NONE, addToBackStack);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @NonNull Direction Direction) {
        mSupportComponent.loadFragment(fragment, R.id.container, Direction, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, @NonNull Direction Direction) {
        mSupportComponent.loadFragment(fragment, idContainer, Direction, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @NonNull Direction Direction,
                                boolean addToBackStack) {
        mSupportComponent.loadFragment(fragment, R.id.container, Direction, addToBackStack);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, @NonNull Direction direction,
                                boolean addToBackStack) {
        mSupportComponent.loadFragment(fragment, idContainer, direction, addToBackStack);
    }

    protected void loadFragment(@NonNull Fragment fragment, @IdRes int idContainer, @NonNull Direction direction,
                                boolean addToBackStack) {
        mSupportComponent.loadFragment(fragment, idContainer, direction, addToBackStack);
    }

    protected Fragment getCurrentFragment() {
        return mSupportComponent.getCurrentFragment();
    }

    protected Fragment getCurrentFragment(@IdRes int idContainer) {
        return mSupportComponent.getCurrentFragment(idContainer);
    }

    protected void checkPermissions() {

        if (!ValidatePermissions.isAllPermissionsActives(this, ValidatePermissions.getPermissionsCheck())) {
            ValidatePermissions.checkPermissions(this, ValidatePermissions.getPermissionsCheck(), PERMISSION_GENERAL);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.PERMISSION_GENERAL) {

            if (!ValidatePermissions.isAllPermissionsActives(this, ValidatePermissions.getPermissionsCheck())) {
                checkPermissions();
            }
        }
    }

    @CallSuper
    @Override
    public void onEvent(String event, Object data) {
        switch (event) {
            case EVENT_SESSION_EXPIRED:
                Utils.sessionExpired();
                break;
        }
    }

    public void errorSessionExpired(DataSourceResult dataSourceResult) {
        GenericResponse response = (GenericResponse) dataSourceResult.getData();
        final String mensaje = response.getMensaje();
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

        UI.createSimpleCustomDialog("", mensaje, getSupportFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        onEvent(EVENT_SESSION_EXPIRED, 1);
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }
}
