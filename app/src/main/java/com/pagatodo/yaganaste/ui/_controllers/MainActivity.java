
package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.PageResult;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.provisioning.manager.ProvisioningManager;
import com.pagatodo.yaganaste.freja.provisioning.presenter.ProvisioningPresenter;
import com.pagatodo.yaganaste.freja.provisioning.presenter.ProvisioningPresenterAbs;
import com.pagatodo.yaganaste.interfaces.Command;
import com.pagatodo.yaganaste.net.FirebaseInstanceServer;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui._manager.PagerAdapter;
import com.pagatodo.yaganaste.ui.account.login.MainFragment;
import com.pagatodo.yaganaste.ui.account.login.ScreenSlidePagefragment;
import com.pagatodo.yaganaste.ui.account.register.LegalsDialog;
import com.pagatodo.yaganaste.ui.adquirente.TransactionResultFragment;
import com.pagatodo.yaganaste.ui.otp.activities.OtpCodeActivity;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.ViewPagerCustomDuration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.NO_SIM_CARD;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.PRIVACIDAD;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;

public class MainActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_conainer);
        initViews();
        String action = getIntent().getExtras().getString(SELECTION);
        if(action.equals(NO_SIM_CARD)) {
            loadFragment(TransactionResultFragment.newInstance(getPageResultNiSIM()));
        }else if(action.equals(MAIN_SCREEN)) {
            loadFragment(MainFragment.newInstance(), true);
        }
    }

    private void initViews() {
        changeToolbarVisibility(false);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * Construimos el PageResult a mostrar en caso de no tener tarjeta SIM
     */
    private PageResult getPageResultNiSIM() {
        PageResult pageResult = new PageResult();
        pageResult.setIdResurceIcon(R.drawable.warning_canvas);
        pageResult.setTitle(getString(R.string.no_sim_titulo));
        pageResult.setMessage(getString(R.string.no_sim_mensaje));
        pageResult.setDescription(getString(R.string.no_sim_desc));
        pageResult.setNamerBtnPrimary(getString(R.string.entendido_titulo));
        pageResult.setActionBtnPrimary(new Command() {
            @Override
            public void action(Context context, Object[] params) {
                finish();
            }
        });

        return pageResult;
    }

}

