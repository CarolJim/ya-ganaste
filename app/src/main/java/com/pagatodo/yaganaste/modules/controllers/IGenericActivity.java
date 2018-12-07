package com.pagatodo.yaganaste.modules.controllers;


import android.app.Fragment;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import android.view.View;

import com.pagatodo.yaganaste.interfaces.enums.Direction;

import java.util.List;

public interface IGenericActivity {

    void showLoader(String message);

    void hideLoader();

    void loadFragment(@NonNull Fragment fragment, @IdRes int idContainer, @NonNull Direction direction,
                      boolean addToBackStack);

    void loadFragment(@NonNull Fragment fragment, @IdRes int idContainer, @NonNull Direction direction,
                      boolean addToBackStack, View shareElement, String transitionName);

    void removeLastFragment();

    Fragment getCurrentFragment(@IdRes int idContainer);

    List<Fragment> getFragments();
}
