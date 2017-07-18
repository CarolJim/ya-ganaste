package com.pagatodo.yaganaste.ui._controllers.manager;

import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;


/**
 * Created by jguerras on 29/11/2016.
 * Updated by flima on 8/02/2017.
 */

public abstract class ToolBarActivity extends SupportFragmentActivity {

    public static final int CODE_LOG_OUT = 3124;
    public static final int RESULT_LOG_OUT = 3125;
    public static final String EVENT_CHANGE_TOOLBAR_VISIBILITY = "eventChangeToolbarVisibility";
    private View toolbarLayout;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        toolbarLayout = findViewById(R.id.toolbarLy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTest);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
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
            startActivityForResult(intent, CODE_LOG_OUT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
    }

    @Override
    @CallSuper
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_LOG_OUT && resultCode == RESULT_LOG_OUT) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(SELECTION, MAIN_SCREEN);
            startActivity(intent);
            finish();
        }
    }
}
