package com.pagatodo.yaganaste.ui._controllers.manager;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import java.util.List;

/**
 * @author Juan Guerra on 15/06/2017.
 */

public class SupportComponent {

    protected FragmentManager fragmentManager;
    GenericFragment lastFragment;
    @IdRes
    private int containerID;

    public SupportComponent(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        containerID = -1;
    }

    /*
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

    protected void loadFragment(@NonNull GenericFragment fragment, @NonNull Direction direction) {
        loadFragment(fragment, R.id.container, direction, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, @NonNull Direction direction) {
        loadFragment(fragment, idContainer, direction, false);
    }

    protected void loadFragment(@NonNull GenericFragment fragment, @NonNull Direction direction,
                                boolean addToBackStack) {
        loadFragment(fragment, R.id.container, direction, addToBackStack);
    }*/

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, @NonNull Direction direction,
                                boolean addToBackStack) {

        this.containerID = idContainer;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (direction.equals(Direction.FORDWARD)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation(),
                    Direction.BACK.getEnterAnimation(), Direction.BACK.getExitAnimation());
        } else if (direction.equals(Direction.BACK)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation(),
                    Direction.FORDWARD.getEnterAnimation(), Direction.FORDWARD.getExitAnimation());
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        lastFragment = fragment;
        fragmentTransaction.replace(idContainer, fragment, fragment.getFragmentTag()).commitAllowingStateLoss();

    }

    protected void loadFragment(@NonNull GenericFragment fragment, @IdRes int idContainer, @NonNull Direction direction,
                                boolean addToBackStack, View shareElement, String transitionName) {

        this.containerID = idContainer;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (direction.equals(Direction.FORDWARD)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation(),
                    Direction.BACK.getEnterAnimation(), Direction.BACK.getExitAnimation());
        } else if (direction.equals(Direction.BACK)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation(),
                    Direction.FORDWARD.getEnterAnimation(), Direction.FORDWARD.getExitAnimation());
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        lastFragment = fragment;
        fragmentTransaction.addSharedElement(shareElement,transitionName);
        fragmentTransaction.replace(idContainer, fragment, fragment.getFragmentTag()).commitAllowingStateLoss();

    }


    protected void loadFragment(@NonNull Fragment fragment, @IdRes int idContainer, @NonNull Direction direction,
                                boolean addToBackStack) {

        this.containerID = idContainer;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (direction.equals(Direction.FORDWARD)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation(),
                    Direction.BACK.getEnterAnimation(), Direction.BACK.getExitAnimation());
        } else if (direction.equals(Direction.BACK)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation(),
                    Direction.FORDWARD.getEnterAnimation(), Direction.FORDWARD.getExitAnimation());
        }

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(idContainer, fragment, fragment.getClass().getSimpleName()).commitAllowingStateLoss();

    }

    protected void removeLastFragment() {
        if (lastFragment != null) {
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

    protected List<Fragment> getFragments() {
        return fragmentManager.getFragments();
    }


}