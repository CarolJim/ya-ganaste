package com.pagatodo.yaganaste.ui._controllers.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.pagatodo.yaganaste.R;
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

    private SupportComponent mSupportComponent;

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

}
