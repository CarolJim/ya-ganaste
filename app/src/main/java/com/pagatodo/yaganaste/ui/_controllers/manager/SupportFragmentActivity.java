package com.pagatodo.yaganaste.ui._controllers.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.appsee.Appsee;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.SIMActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;


/**
 * Created by jguerras on 29/11/2016.
 * Updated by flima on 8/02/2017.
 */

public abstract class SupportFragmentActivity extends AppCompatActivity implements OnEventListener {

    public static final String EVENT_SESSION_EXPIRED = "EVENT_SESSION_EXPIRED";
    private SupportComponent mSupportComponent;
    private boolean isFromActivityForResult = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSupportComponent = new SupportComponent(getSupportFragmentManager());
        setTitle("");
        /*Validamos Permisos*/
        checkPermissions();
        Appsee.start();
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


    public void setIsFromActivityForResult(boolean b) {
        isFromActivityForResult = b;
    }

    public boolean isFromActivityForResult() {
        return isFromActivityForResult;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        isFromActivityForResult = true;
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        isFromActivityForResult = true;
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFromActivityForResult = false;
         /* Validar que el celular cuente con SIM */
        if ((!(this instanceof SIMActivity)) && !ValidatePermissions.validateSIMCard(this)) {
            startActivity(new Intent(this, SIMActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
    }

    public void removeLastFragment() {
        mSupportComponent.removeLastFragment();
    }

    @Override
    public void onUserInteraction() {
        Log.e(SupportFragmentActivity.class.getSimpleName(), "Reset From: " + getClass().getSimpleName());
        App.getInstance().resetTimer(this);
    }

    @Override
    public void finish() {
        Log.e(SupportFragmentActivity.class.getSimpleName(), "Stop From: " + getClass().getSimpleName());
        App.getInstance().stopTimer();
        App.getInstance().removeFromQuee(this);
        super.finish();
    }

    public abstract boolean requiresTimer();
}
