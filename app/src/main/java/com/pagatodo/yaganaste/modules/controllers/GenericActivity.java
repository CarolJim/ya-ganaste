package com.pagatodo.yaganaste.modules.controllers;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.security.KeyStore;
import java.util.List;

import javax.crypto.KeyGenerator;


@SuppressLint("Registered")
public class GenericActivity extends AppCompatActivity implements IGenericActivity {

    protected FragmentManager fragmentManager;
    private Fragment lastFragment;
    private Toolbar toolbar;
    private ProgressLayout progressLayout;


    private static final String SECRET_MESSAGE = "Very secret message";
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_generic);
        if (layoutResID != R.layout.activity_generic) {
            LinearLayout content = findViewById(R.id.content);
            View view = getLayoutInflater().inflate(layoutResID, null);
            content.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            setUpActionBar();
        }
        //progressLayout = findViewById(R.id.progress_view);
    }

    private void setUpActionBar() {
        toolbar = findViewById(R.id.toolbar_wallet);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            /* Mostrar el icono de back en la esquina del Toolbar */
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            /* Ocultar título de la Toolbar */
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void showLoader(String message) {
        if (progressLayout != null) {
            progressLayout.setTextMessage(message != null ? message : "");
            progressLayout.setVisibility(View.VISIBLE);
            progressLayout.bringToFront();
        }
    }

    @Override
    public void hideLoader() {
        if (progressLayout != null) {
            progressLayout.setVisibility(View.GONE);
        }
    }

    /**
     * Elementos de la interfaz
     **/
    @Override
    public void loadFragment(@NonNull Fragment fragment, int idContainer, @NonNull Direction direction, boolean addToBackStack) {
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

        fragmentTransaction.replace(idContainer, fragment, fragment.getClass().getSimpleName()).commitAllowingStateLoss();
    }

    @Override
    public void loadFragment(@NonNull Fragment fragment, int idContainer, @NonNull Direction direction, boolean addToBackStack, View shareElement, String transitionName) {
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
        fragmentTransaction.addSharedElement(shareElement, transitionName);
        fragmentTransaction.replace(idContainer, fragment, fragment.getClass().getSimpleName()).commitAllowingStateLoss();
    }

    @Override
    public void removeLastFragment() {
        if (lastFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(lastFragment).commit();
        }
    }

    @Override
    public Fragment getCurrentFragment(int idContainer) {
        return fragmentManager.findFragmentById(idContainer);
    }

    @Override
    public List<Fragment> getFragments() {
        return fragmentManager.getFragments();
    }

}
