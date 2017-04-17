package com.pagatodo.yaganaste.ui._controllers.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

/**
 * Created by Jordan on 07/04/2017.
 */

public abstract class SupportFragment extends GenericFragment {

    protected FragmentManager fragmentManager;
    private
    @IdRes
    int containerID;
    GenericFragment lastFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        containerID = -1;
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
        lastFragment = fragment;
        fragmentTransaction.replace(idContainer, fragment, fragment.getFragmentTag()).commit();
    }

    protected void removeLastFragment(){
        if(lastFragment != null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(lastFragment).commit();
        }
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
