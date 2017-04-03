package com.pagatodo.yaganaste.ui._controllers.manager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
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
    private @IdRes
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

    public enum DIRECTION{
        FORDWARD(R.anim.slide_from_right,R.anim.slide_to_left),
        BACK(R.anim.slide_from_left, R.anim.slide_to_right),
        NONE(0,0);

        private int enterAnimation;
        private int exitAnimation;
        private DIRECTION (@AnimRes int enterAnimation, @AnimRes int exitAnimation){
            this.enterAnimation = enterAnimation;
            this.exitAnimation = exitAnimation;
        }

        public int getEnterAnimation() {
            return enterAnimation;
        }
        public int getExitAnimation() {
            return exitAnimation;
        }
    }

    protected void loadFragment(@NonNull GenericFragment fragment){
        loadFragment(fragment, R.id.container, DIRECTION.NONE, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer){
        loadFragment(fragment, idContainer, DIRECTION.NONE, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, boolean addToBackStack){
        loadFragment(fragment, R.id.container, DIRECTION.NONE, addToBackStack);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, boolean addToBackStack){
        loadFragment(fragment, idContainer, DIRECTION.NONE, addToBackStack);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @NonNull DIRECTION direction){
        loadFragment(fragment, R.id.container, direction, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, @NonNull DIRECTION direction){
        loadFragment(fragment, idContainer, direction, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @NonNull DIRECTION direction,
                                boolean addToBackStack){
        loadFragment(fragment, R.id.container, direction, addToBackStack);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, @NonNull DIRECTION direction,
                                boolean addToBackStack){
        this.containerID = idContainer;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!direction.equals(DIRECTION.NONE)){
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation());
        }
        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(idContainer, fragment, fragment.getFragmentTag()).commit();
    }

    protected Fragment getCurrentFragment(){
        if(containerID != -1){
            return getCurrentFragment(containerID);
        }
        return null;
    }

    protected Fragment getCurrentFragment(@IdRes int idContainer){
        return fragmentManager.findFragmentById(idContainer);
    }

    protected void checkPermissions(){

        if(!ValidatePermissions.isAllPermissionsActives(this,ValidatePermissions.getPermissionsCheck())){
            ValidatePermissions.checkPermissions(this, ValidatePermissions.getPermissionsCheck(), PERMISSION_GENERAL);
            ValidatePermissions.showDialogPermission(this,getString(R.string.permission_request),getString(R.string.permission_request_desc),actionsDialog);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.PERMISSION_GENERAL) {

            if(!ValidatePermissions.isAllPermissionsActives(this,ValidatePermissions.getPermissionsCheck())){
                checkPermissions();
            }
        }
    }

    private DialogDoubleActions actionsDialog = new DialogDoubleActions() {
        @Override
        public void actionConfirm(Object... params) {
            ValidatePermissions.openDetailsApp(SupportFragmentActivity.this,Constants.PERMISSION_GENERAL);
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
