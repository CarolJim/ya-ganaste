package com.pagatodo.yaganaste.utils.customviews.yaganasteviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.utils.customviews.MaterialLinearLayout;
import com.pagatodo.yaganaste.utils.customviews.NoSwipeViewPager;

import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;

/**
 * @author Juan Guerra on 05/04/2017.
 */

public class TabLayoutEmAd extends LinearLayoutCompat implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private LinearLayout llEmisorBorderContainer;
    private View viewEmisorBorder;
    private LinearLayout llAdquirenteBorderContainer;
    private View viewAdquirenteBorder;
    private LinearLayout llContentParentEmisor;
    private MaterialLinearLayout llMaterialEmisorContainer;
    private LinearLayout llContentParentAdquirente;
    private MaterialLinearLayout llMaterialAdquirenteContainer;
    private NoSwipeViewPager mViewPager;

    private TabViewElement cardAdqSel;
    private CardEmisor cardEmisor;
    private CardEmisorSelected cardEmisorSelected;

    private Context contextcard;

    private InviteAdquirenteCallback inviteAdquirenteCallback;
    private onBlockCard onBlockCard;
    private clikdongle clickdongle;

    public TabLayoutEmAd(Context context) {
        this(context, null);
    }

    public TabLayoutEmAd(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayoutEmAd(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.contextcard = context;
        setView();
        init();
    }

    private void setView() {
        inflate(getContext(), R.layout.tab_em_adq, this);

        llEmisorBorderContainer = (LinearLayout) findViewById(R.id.ll_emisor_border_container);
        viewEmisorBorder = findViewById(R.id.view_emisor_border);
        llAdquirenteBorderContainer = (LinearLayout) findViewById(R.id.ll_adquirente_border_container);
        viewAdquirenteBorder = findViewById(R.id.view_adquirente_border);
        llContentParentEmisor = (LinearLayout) findViewById(R.id.ll_content_parent_emisor);
        llMaterialEmisorContainer = (MaterialLinearLayout) findViewById(R.id.ll_material_emisor_container);
        llContentParentAdquirente = (LinearLayout) findViewById(R.id.ll_content_parent_adquirente);
        llMaterialAdquirenteContainer = (MaterialLinearLayout) findViewById(R.id.ll_material_adquirente_container);
        llMaterialEmisorContainer.setOnClickListener(this);
        llMaterialAdquirenteContainer.setOnClickListener(this);

    }

    private void init() {
        cardEmisor = new CardEmisor(getContext());
        cardEmisorSelected = new CardEmisorSelected(getContext());
        DataIniciarSesion dataUser = SingletonUser.getInstance().getDataUser();
        updateData();


        cardEmisorSelected.setOnLongClickListener(new View.OnLongClickListener() {


            @Override
            public boolean onLongClick(View view) {
                onBlockCard.onLongClickBlockCard();
                return false;
            }
        });


    }

    public void setUpWithViewPager(NoSwipeViewPager viewPager) {
        if (viewPager != null) {
            mViewPager = viewPager;
            viewPager.addOnPageChangeListener(this);
        }
    }

    public void setOnBlockCard(onBlockCard onBlockCard) {
        this.onBlockCard = onBlockCard;
    }


    public void setClickdongle(clikdongle clickdongle) {
        this.clickdongle = clickdongle;
    }

    @Override
    public void onClick(View v) {
        if (mViewPager != null) {
            switch (v.getId()) {
                case R.id.ll_material_emisor_container:
                    mViewPager.setCurrentItem(0);
                    break;

                case R.id.ll_material_adquirente_container:
                    clickAdquirente();
                    break;

                default:
                    break;
            }
        }
    }

    private void clickAdquirente() {
        if (SingletonUser.getInstance().getDataUser().isEsAgente() && SingletonUser.getInstance().getDataUser().getEstatusAgente() == CRM_DOCTO_APROBADO) {
            mViewPager.setCurrentItem(1);
        } else if (inviteAdquirenteCallback != null) {
            inviteAdquirenteCallback.onInviteAdquirente();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        //No-OP
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //No-op
    }


    public void setInviteAdquirenteCallback(InviteAdquirenteCallback inviteAdquirenteCallback) {
        this.inviteAdquirenteCallback = inviteAdquirenteCallback;
    }


    /*Delegated Method*/
    public void updateData() {
        cardEmisor.updateData();
        cardEmisorSelected.updateData();
        if (cardAdqSel != null) {
            cardAdqSel.updateData();
        }
    }

    public void updatestatusCard() {
        cardEmisorSelected.updateData();
    }

    public interface InviteAdquirenteCallback {
        void onInviteAdquirente();
    }

    public interface onBlockCard {
        void onLongClickBlockCard();
    }

    public interface clikbloquear {
        void longclick();
    }

    public interface clikdongle {
        void longclickdongle();
    }

}
