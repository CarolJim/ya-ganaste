package com.pagatodo.yaganaste.modules.payments.payReloadsServices;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.newsend.SendNewActivity;
import com.pagatodo.yaganaste.modules.payments.payReloadsServices.allRecharges.AllRechargesFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.utils.Constants;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.App.getContext;
import static com.pagatodo.yaganaste.modules.payments.payReloadsServices.PayReloadsServicesActivity.Type.ALL_RECHARGES_FAV;
import static com.pagatodo.yaganaste.modules.payments.payReloadsServices.PayReloadsServicesActivity.Type.ALL_SERVICES_FAV;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.RESUL_FAVORITES;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.FAVORITE_PROCESS;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE_FROM_CERO;
import static com.pagatodo.yaganaste.utils.Recursos.TYPEPAYMENT;

public class PayReloadsServicesActivity extends LoaderActivity implements View.OnClickListener {

    @BindView(R.id.btn_back_favos)
    AppCompatImageView btn_back_favos;
    @BindView(R.id.imgAddfavos)
    ImageView imgAddfavos;

    boolean recarga;

    private PayReloadsServicesRouter router;
    private static final String TAG_TYPE = "TAG_TYPE";

    public static Intent intentRecharges(Activity activity, Type type) {
        Intent intent = new Intent(activity, PayReloadsServicesActivity.class);
        intent.putExtra(TAG_TYPE, type);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_main);
        this.router = new PayReloadsServicesRouter(this);
        init();
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    public PayReloadsServicesRouter getRouter() {
        return router;
    }

    public void init() {
        ButterKnife.bind(this);
        imgAddfavos.setOnClickListener(this::onClick);
        btn_back_favos.setOnClickListener(this::onClick);
        if (getIntent().getSerializableExtra(TAG_TYPE) != null) {
            this.router.onShowAllRecharges(((Type) getIntent().getSerializableExtra(TAG_TYPE)));
        }

    }

    public void setVisibilityBack(boolean mBoolean) {
        btn_back_favos.setVisibility(mBoolean ? View.VISIBLE : View.GONE);
    }

    public void setrecarga(boolean mBoolean) {
        this.recarga = mBoolean;
    }

    public void setVisibilityaddFavo(boolean mBoolean) {
        imgAddfavos.setVisibility(mBoolean ? View.VISIBLE : View.GONE);
    }


    public enum Type {
        ALL_RECHARGES, ALL_RECHARGES_FAV, ALL_SERVICES, ALL_SERVICES_FAV
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAddfavos:
                Fragment fragment = getCurrentFragment();

                if (App.getInstance().getPrefs().loadDataBoolean(TYPEPAYMENT, true)) {

                    Intent intentAddFavorite = new Intent(PayReloadsServicesActivity.this, FavoritesActivity.class);
                    intentAddFavorite.putExtra(CURRENT_TAB_ID, Constants.PAYMENT_RECARGAS);
                    intentAddFavorite.putExtra(FAVORITE_PROCESS, NEW_FAVORITE_FROM_CERO);
                    startActivityForResult(intentAddFavorite, RESUL_FAVORITES);
                } else {

                    Intent intentAddFavorite = new Intent(PayReloadsServicesActivity.this, FavoritesActivity.class);
                    intentAddFavorite.putExtra(CURRENT_TAB_ID, Constants.PAYMENT_SERVICIOS);
                    intentAddFavorite.putExtra(FAVORITE_PROCESS, NEW_FAVORITE_FROM_CERO);
                    startActivityForResult(intentAddFavorite, RESUL_FAVORITES);

                }
                break;
            case R.id.btn_back_favos:
                onBackPressed();
                break;
        }
    }

}
