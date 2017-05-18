package com.pagatodo.yaganaste.ui._controllers.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;


/**
 * Created by jguerras on 29/11/2016.
 * Updated by flima on 8/02/2017.
 */

public abstract class ToolBarActivity extends SupportFragmentActivity {


    private View toolbarLayout;
    public static final String EVENT_CHANGE_TOOLBAR_VISIBILITY = "eventChangeToolbarVisibility";

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        toolbarLayout = findViewById(R.id.toolbar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTest);
        setSupportActionBar(toolbar);
    }

    public void changeToolbarVisibility(boolean visibility) {
        if (toolbarLayout != null) {
            if (visibility) {
                toolbarLayout.setVisibility(View.VISIBLE);
            } else {
                toolbarLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_account_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.configUser) {
            Intent intent = new Intent(this, PreferUserActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
