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

    private CardAdq cardAdq;
    private TabViewElement cardAdqSel;
    private CardEmisor cardEmisor;
    private CardEmisorSelected cardEmisorSelected;

    private Context contextcard;

    private InviteAdquirenteCallback inviteAdquirenteCallback;
    private onBlockCard onBlockCard;
    private clikbloquear clickbloquear;
    private clikdongle clickdongle;

    public TabLayoutEmAd(Context context) {
        this(context, null);
    }

    public TabLayoutEmAd(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayoutEmAd(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.contextcard=context;
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
        cardAdq = new CardAdq(getContext());
        DataIniciarSesion dataUser = SingletonUser.getInstance().getDataUser();
        if (dataUser.isEsAgente() && dataUser.getEstatusDocumentacion() == CRM_DOCTO_APROBADO) {
            if (dataUser.getIdEstatus() == IdEstatus.CUPO.getId()) {
                cardAdqSel = new CardCupoSelected(getContext());
            } else {
                cardAdqSel = new CardAdqSelected(getContext());
            }
            //cardAdqSel = new CardAdqSelected(getContext());
            //cardAdqSel = new CardAdqCredSelected(getContext());

        }
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
        animateTabs(positionOffset, mViewPager.getCurrentItem());
    }

    @Override
    public void onPageSelected(int position) {
        //No-OP
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //No-op
    }

    private void animateTabs(float offset, int position) {

        if (offset <= 0.0) {
            offset = position;
        }

        LinearLayout.LayoutParams llEmisorBorederContainerParams = (LinearLayout.LayoutParams) llEmisorBorderContainer.getLayoutParams();
        llEmisorBorederContainerParams.weight = 2.5f - offset;
        llEmisorBorederContainerParams.rightMargin = (int) (getContext().getResources().getDimension(R.dimen.size_margin_custom_tabs) * offset);
        llEmisorBorderContainer.setLayoutParams(llEmisorBorederContainerParams);

        if (offset <= 0.0) {
            viewEmisorBorder.setBackgroundResource(R.drawable.tab_selected_em_ad);
            viewEmisorBorder.setRotationY(0);
            llMaterialEmisorContainer.removeAllViews();
            llMaterialEmisorContainer.addView(cardEmisorSelected,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else if (offset >= 1.0) {
            viewEmisorBorder.setBackgroundResource(R.drawable.tab_unselected_em_ad);
            viewEmisorBorder.setRotationY(180);
            llMaterialEmisorContainer.removeAllViews();
            llMaterialEmisorContainer.addView(cardEmisor,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        }

        LinearLayout.LayoutParams llAdquirenteBorderContainerParams = (LinearLayout.LayoutParams) llAdquirenteBorderContainer.getLayoutParams();
        llAdquirenteBorderContainerParams.weight = 1.5f + offset;
        llAdquirenteBorderContainerParams.leftMargin = (int) (getContext().getResources().getDimension(R.dimen.size_margin_custom_tabs) * (1 - offset));
        llAdquirenteBorderContainer.setLayoutParams(llAdquirenteBorderContainerParams);

        if (offset <= 0) {
            viewAdquirenteBorder.setBackgroundResource(R.drawable.tab_unselected_em_ad);
            viewAdquirenteBorder.setRotationY(0);
            llMaterialAdquirenteContainer.removeAllViews();
            llMaterialAdquirenteContainer.addView(cardAdq,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else if (offset >= 1) {
            viewAdquirenteBorder.setBackgroundResource(R.drawable.tab_selected_em_ad);
            viewAdquirenteBorder.setRotationY(180);
            llMaterialAdquirenteContainer.removeAllViews();
            llMaterialAdquirenteContainer.addView(cardAdqSel,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        }

        LinearLayout.LayoutParams llContentParentEmisorParams = (LinearLayout.LayoutParams) llContentParentEmisor.getLayoutParams();
        llContentParentEmisorParams.weight = 2.5f - offset;
        llContentParentEmisorParams.leftMargin = (int) (getContext().getResources().getDimension(R.dimen.size_margin_custom_tabs) * offset);
        llContentParentEmisor.setLayoutParams(llContentParentEmisorParams);

        LinearLayout.LayoutParams llMaterialEmisorContainerParams = (LinearLayout.LayoutParams) llMaterialEmisorContainer.getLayoutParams();
        llMaterialEmisorContainerParams.weight = 1.25f + 0.75f * (1 - offset);
        llMaterialEmisorContainerParams.leftMargin = (int) (getContext().getResources().getDimension(R.dimen.size_margin_custom_tabs) * offset);
        llMaterialEmisorContainer.setLayoutParams(llMaterialEmisorContainerParams);

        LinearLayout.LayoutParams llContentParentAdquirenteParams = (LinearLayout.LayoutParams) llContentParentAdquirente.getLayoutParams();
        llContentParentAdquirenteParams.weight = 1.5f + offset;
        llContentParentAdquirenteParams.leftMargin = (int) (getContext().getResources().getDimension(R.dimen.size_margin_custom_tabs) * offset);
        llContentParentAdquirente.setLayoutParams(llContentParentAdquirenteParams);

        LinearLayout.LayoutParams llMaterialAdquirenteContainerParams = (LinearLayout.LayoutParams) llMaterialAdquirenteContainer.getLayoutParams();
        llMaterialAdquirenteContainerParams.weight = 1.25f + 0.75f * offset;
        llMaterialAdquirenteContainerParams.leftMargin = (int) (getContext().getResources().getDimension(R.dimen.size_margin_custom_tabs) * (1 - offset));
        llMaterialAdquirenteContainer.setLayoutParams(llMaterialAdquirenteContainerParams);
        llMaterialAdquirenteContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickdongle.longclickdongle();
                //Toast.makeText(getContext(), "fewfwefwef", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    public void setInviteAdquirenteCallback(InviteAdquirenteCallback inviteAdquirenteCallback) {
        this.inviteAdquirenteCallback = inviteAdquirenteCallback;
    }



    /*Delegated Method*/
    public void updateData() {
        cardEmisor.updateData();
        cardEmisorSelected.updateData();
        cardAdq.updateData();
        if (cardAdqSel != null) {
            cardAdqSel.updateData();
        }
    }

    public void updatestatusCard(){
        cardEmisorSelected.updateData();
    }

    public interface InviteAdquirenteCallback {
        void onInviteAdquirente();
    }

    /** Interfaz para bloquear abrir bloquear tarjeta desde el HomeTabFragment
     * */
    public  interface onBlockCard {
        void onLongClickBlockCard();
    }
    public  interface clikdongle{
        void longclickdongle();
    }

}
