package com.pagatodo.yaganaste.ui._controllers.manager;

import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui_wallet.NotificationActivity;
import com.pagatodo.yaganaste.utils.UI;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;

/**
 * Created by jguerras on 29/11/2016.
 * Updated by flima on 8/02/2017.
 */

public abstract class ToolBarActivity extends SupportFragmentActivity implements View.OnClickListener {

    public static final int CODE_LOG_OUT = 3124;
    public static final int RESULT_LOG_OUT = 3125;
    public static final String EVENT_CHANGE_TOOLBAR_VISIBILITY = "eventChangeToolbarVisibility";
    private View toolbarLayout;
    public String mUserImage;
    private ImageView imgNotif, imgToolbar, imgOk;
    //CircleImageView imageView;
    //ImageView imageView;
    //static CircleImageView imageViewdes;
    //static ImageView imageViewdes;
    private View toolbarShadow;
    private AppCompatImageView btnBack;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setUpActionBar();
    }

    public void setUpActionBar() {
        toolbarLayout = findViewById(R.id.toolbarLy);
        //imageView = (ImageView) findViewById(R.id.imgToRight_prefe);
        //imageViewdes= (ImageView) findViewById(R.id.imgToRight_prefe);
        btnBack = (AppCompatImageView) findViewById(R.id.btn_back);
        imgNotif = (ImageView) findViewById(R.id.imgNotifications);
        imgToolbar = (ImageView) findViewById(R.id.imgToolbar);
        imgOk = (ImageView) findViewById(R.id.btn_ok);
        toolbarShadow = (View) findViewById(R.id.toolbar_shadow);
        //if (imageView != null) {
        //     imageView.setOnClickListener(this);
        // }

        if (btnBack != null) {
            btnBack.setOnClickListener(this);
        }

        if (imgNotif != null) {
            imgNotif.setOnClickListener(this);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTest);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void showToolbarOk(boolean mBoolean) {
        imgOk.setVisibility(mBoolean ? View.VISIBLE : View.GONE);
    }

    public void showToolbarShadow(boolean mBoolean) {
        toolbarShadow.setVisibility(mBoolean ? View.VISIBLE : View.GONE);
    }

    public void showImageToolbar(boolean mBoolean) {
        imgToolbar.setVisibility(mBoolean ? View.VISIBLE : View.GONE);
    }

    public void showBack(boolean isBackShowing) {
        this.btnBack.setVisibility(isBackShowing ? View.VISIBLE : View.GONE);
    }

    /**
     * Metodo que heredamos, controla el que se muestre el icono con la foto como visible o no, en
     * todos sus descendientes
     *
     * @param mBoolean
     */
    public void setVisibilityPrefer(Boolean mBoolean) {
        if (mBoolean) {
            //imageView.setVisibility(View.VISIBLE);
        } else {
            // imageView.setVisibility(View.GONE);
        }
    }

    /**
     * POSIBLE metodo para mostrar el icono de notificaciones, por medio de su padre y no en la propia
     * actividad hija
     *
     * @param mBoolean
     */
    public void setVisibilityNotif(boolean mBoolean) {
        imgNotif.setVisibility(mBoolean ? View.VISIBLE : View.GONE);
    }

    public void setVisibilityBack(boolean mBoolean) {
        btnBack.setVisibility(mBoolean ? View.VISIBLE : View.GONE);
    }


    public static void setVisibleshare() {
        //imageViewdes.setVisibility(View.GONE);
    }


    /**
     * Codigo para hacer Set en la imagen de preferencias con la foto actual
     */
    private void updatePhoto() {
        /*mUserImage = SingletonUser.getInstance().getDataUser().getUsuario().getImagenAvatarURL();

        if (mUserImage != null && !mUserImage.isEmpty()) {
            Glide.with(this).load(mUserImage).placeholder(R.mipmap.icon_user).error(R.mipmap.icon_user)
                    .dontAnimate().into(imageView);
        }*/
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
        //   getMenuInflater().inflate(R.menu.my_account_menu, menu);
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
    protected void onResume() {
        super.onResume();
        setVisibilityPrefer(true);
        updatePhoto();
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

    /**
     * Abrimos la actividad de las preferencias
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                UI.hideKeyBoard(this);
                onBackPressed();
                break;
            case R.id.imgNotifications:
                // Toast.makeText(ToolBarActivity.this, "Click", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, NotificationActivity.class));
                break;

            default:
                Intent intent = new Intent(this, PreferUserActivity.class);
                startActivityForResult(intent, CODE_LOG_OUT);
                break;
        }
    }
}
