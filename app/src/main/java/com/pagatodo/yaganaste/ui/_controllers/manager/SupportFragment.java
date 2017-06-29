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

import java.util.List;

/**
 * Created by Jordan on 07/04/2017.
 * Updated by jguerras on 15/06/2017.
 */

public abstract class SupportFragment extends GenericFragment {

    private SupportComponent mSupportComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSupportComponent = new SupportComponent(getChildFragmentManager());
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

    protected void loadFragment(@NonNull GenericFragment fragment, @NonNull Direction direction) {
        mSupportComponent.loadFragment(fragment, R.id.container, direction, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, @NonNull Direction direction) {
        mSupportComponent.loadFragment(fragment, idContainer, direction, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @NonNull Direction Direction,
                                boolean addToBackStack) {
        mSupportComponent.loadFragment(fragment, R.id.container, Direction, addToBackStack);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, @NonNull Direction direction,
                                boolean addToBackStack) {

        mSupportComponent.loadFragment(fragment, idContainer, direction, addToBackStack);
    }

    protected void removeLastFragment() {
        mSupportComponent.removeLastFragment();
    }

    protected Fragment getCurrentFragment() {
        return mSupportComponent.getCurrentFragment();
    }

    protected Fragment getCurrentFragment(@IdRes int idContainer) {
        return mSupportComponent.getCurrentFragment(idContainer);
    }


    protected List<Fragment> getFragments() {
        return mSupportComponent.getFragments();

    }

}
