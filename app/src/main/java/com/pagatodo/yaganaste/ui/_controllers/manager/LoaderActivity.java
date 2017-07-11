package com.pagatodo.yaganaste.ui._controllers.manager;

import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IProgressView;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;

/**
 * @author Juan Guerra on 09/05/2017.
 */

public class LoaderActivity extends ToolBarActivity implements OnEventListener, IProgressView<ErrorObject> {

    public static final String EVENT_SHOW_LOADER = "EVENT_SHOW_LOADER";
    public static final String EVENT_HIDE_LOADER = "EVENT_HIDE_LOADER";
    public static final String EVENT_SHOW_ERROR = "EVENT_SHOW_ERROR";

    private ProgressLayout progressLayout;
    public boolean isLoaderShow = false;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_loader);
        if (layoutResID != R.layout.activity_loader) {
            LinearLayout content = (LinearLayout) findViewById(R.id.ll_content);
            View view = getLayoutInflater().inflate(layoutResID, null);
            content.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }

        progressLayout = (ProgressLayout) findViewById(R.id.progress_view);
    }

    @Override
    public void showLoader(String message) {
        progressLayout.setTextMessage(message);
        progressLayout.setVisibility(View.VISIBLE);
        progressLayout.bringToFront();
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(ErrorObject error) {
        UI.createSimpleCustomDialog("", error.getErrorMessage(), getSupportFragmentManager(), error.getErrorActions(), true, false);
    }

    @Override
    @CallSuper
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_SHOW_LOADER:
                isLoaderShow = true;
                showLoader(data.toString());
                break;

            case EVENT_HIDE_LOADER:
                isLoaderShow = false;
                hideLoader();
                break;

            case EVENT_SHOW_ERROR:
                showError((ErrorObject) data);
                break;
          /*  case EVENT_SESSION_EXPIRED:
                Intent intent = new Intent(App.getContext(), MainActivity.class);
                intent.putExtra(SELECTION, MAIN_SCREEN);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getContext().startActivity(intent);
                break;*/
            default:
                break;
        }
    }

    @Override
    public void errorSessionExpired(DataSourceResult response) {
//        String mensaje = response.getData().toString();
//        super.onEvent(EVENT_SESSION_EXPIRED, mensaje);
    }
}
