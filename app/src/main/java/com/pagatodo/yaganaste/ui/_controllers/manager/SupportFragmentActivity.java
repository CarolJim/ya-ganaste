package com.pagatodo.yaganaste.ui._controllers.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;


/**
 * Created by jguerras on 29/11/2016.
 * Updated by flima on 8/02/2017.
 */

public abstract class SupportFragmentActivity extends AppCompatActivity {

    protected FragmentManager fragmentManager;
    private
    @IdRes
    int containerID;


    private static ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentManager = getSupportFragmentManager();
        containerID = -1;
        setTitle("");
        /*Validamos Permisos*/
        checkPermissions();
    }

    protected void loadFragment(@NonNull GenericFragment fragment) {
        loadFragment(fragment, R.id.container, Direction.NONE, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer) {
        loadFragment(fragment, idContainer, Direction.NONE, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, boolean addToBackStack) {
        loadFragment(fragment, R.id.container, Direction.NONE, addToBackStack);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, boolean addToBackStack) {
        loadFragment(fragment, idContainer, Direction.NONE, addToBackStack);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @NonNull Direction Direction) {
        loadFragment(fragment, R.id.container, Direction, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, @NonNull Direction Direction) {
        loadFragment(fragment, idContainer, Direction, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @NonNull Direction Direction,
                                boolean addToBackStack) {
        loadFragment(fragment, R.id.container, Direction, addToBackStack);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, @NonNull Direction Direction,
                                boolean addToBackStack) {
        this.containerID = idContainer;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!Direction.equals(Direction.NONE)) {
            fragmentTransaction.setCustomAnimations(Direction.getEnterAnimation(), Direction.getExitAnimation());
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(idContainer, fragment, fragment.getFragmentTag()).commit();
    }

    protected void loadFragmentForward(@NonNull GenericFragment fragment, boolean addToBackStack) {
        this.containerID = R.id.container;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(Direction.FORDWARD.getEnterAnimation(), Direction.FORDWARD.getExitAnimation(),
                Direction.BACK.getEnterAnimation(), Direction.BACK.getExitAnimation());

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(this.containerID, fragment, fragment.getFragmentTag()).commit();
    }

    protected void loadFragment(@NonNull Fragment fragment, @IdRes int idContainer, @NonNull Direction direction,
                                boolean addToBackStack) {
        this.containerID = idContainer;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!direction.equals(Direction.NONE)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation());
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(idContainer, fragment, fragment.getClass().getSimpleName()).commit();
    }

    protected Fragment getCurrentFragment() {
        if (containerID != -1) {
            return getCurrentFragment(containerID);
        }
        return null;
    }

    protected Fragment getCurrentFragment(@IdRes int idContainer) {
        return fragmentManager.findFragmentById(idContainer);
    }

    protected void checkPermissions() {

        if (!ValidatePermissions.isAllPermissionsActives(this, ValidatePermissions.getPermissionsCheck())) {
            ValidatePermissions.checkPermissions(this, ValidatePermissions.getPermissionsCheck(), PERMISSION_GENERAL);
            //ValidatePermissions.showDialogPermission(this,getString(R.string.permission_request),getString(R.string.permission_request_desc),actionsDialog);
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

    private DialogDoubleActions actionsDialog = new DialogDoubleActions() {
        @Override
        public void actionConfirm(Object... params) {
            ValidatePermissions.openDetailsApp(SupportFragmentActivity.this, Constants.PERMISSION_GENERAL);
        }

        @Override
        public void actionCancel(Object... params) {

        }
    };


    protected static void showProgress(Context c, String title, String msg, ProgressDialog mProgressDialog) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            dismissProgress(mProgressDialog);

        mProgressDialog = ProgressDialog.show(c, title, msg);
    }

    protected static void dismissProgress(ProgressDialog mProgressDialog) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

}
