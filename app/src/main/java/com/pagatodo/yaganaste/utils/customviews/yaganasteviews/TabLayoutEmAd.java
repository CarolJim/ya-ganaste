package com.pagatodo.yaganaste.utils.customviews.yaganasteviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.utils.DimUtils;
import com.pagatodo.yaganaste.utils.customviews.MaterialLinearLayout;

/**
 * @author Juan Guerra on 05/04/2017.
 */

public class TabLayoutEmAd extends LinearLayoutCompat implements View.OnClickListener, ViewPager.OnPageChangeListener{

    private LinearLayout llEmisorBorderContainer;
    private View viewEmisorBorder;
    private LinearLayout llAdquirenteBorderContainer;
    private View viewAdquirenteBorder;
    private LinearLayout llContentParentEmisor;
    private MaterialLinearLayout llMaterialEmisorContainer;
    private LinearLayout llContentParentAdquirente;
    private MaterialLinearLayout llMaterialAdquirenteContainer;
    private ViewPager mViewPager;

    private CardAdq cardAdq;
    private CardAdqCredSelected cardAdqCredSelected;
    private CardAdqSelected cardAdqSelected;
    private CardEmisor cardEmisor;
    private CardEmisorSelected cardEmisorSelected;

    public TabLayoutEmAd(Context context) {
        this(context, null);
    }

    public TabLayoutEmAd(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayoutEmAd(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
        init();
    }

    private void setView() {
        inflate(getContext(), R.layout.tab_em_adq, this);

        llEmisorBorderContainer = (LinearLayout) findViewById(R.id.ll_emisor_border_container);
        viewEmisorBorder = findViewById(R.id.view_emisor_border);
        llAdquirenteBorderContainer = (LinearLayout) findViewById(R.id.ll_adquirente_border_container);
        viewAdquirenteBorder = findViewById(R.id.view_adquirente_border);
        llContentParentEmisor = (LinearLayout)findViewById(R.id.ll_content_parent_emisor);
        llMaterialEmisorContainer = (MaterialLinearLayout)findViewById(R.id.ll_material_emisor_container);
        llContentParentAdquirente = (LinearLayout)findViewById(R.id.ll_content_parent_adquirente);
        llMaterialAdquirenteContainer = (MaterialLinearLayout)findViewById(R.id.ll_material_adquirente_container);

        llMaterialEmisorContainer.setOnClickListener(this);
        llMaterialAdquirenteContainer.setOnClickListener(this);
    }

    private void init() {
        cardEmisor = new CardEmisor(getContext());
        cardEmisorSelected = new CardEmisorSelected(getContext());
    }

    public void setUpWithViewPager(ViewPager viewPager) {
        if (viewPager != null) {
            mViewPager = viewPager;
            viewPager.addOnPageChangeListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_material_emisor_container:
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(0);
                }
                //configEmisorSelected();
                break;

            case R.id.ll_material_adquirente_container:
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(1);
                }
                //configAdquirenteSelected();
                break;

            default:
                break;
        }
    }

    private void configEmisorSelected() {

        LinearLayout.LayoutParams llEmisorBorederContainerParams = (LinearLayout.LayoutParams)llEmisorBorderContainer.getLayoutParams();
        llEmisorBorederContainerParams.weight = 2.5f;
        llEmisorBorederContainerParams.rightMargin = 0;
        llEmisorBorderContainer.setLayoutParams(llEmisorBorederContainerParams);

        viewEmisorBorder.setBackgroundResource(R.drawable.adq_em_big_tab);
        viewEmisorBorder.setRotationY(0);

        LinearLayout.LayoutParams llAdquirenteBorderContainerParams = (LinearLayout.LayoutParams)llAdquirenteBorderContainer.getLayoutParams();
        llAdquirenteBorderContainerParams.weight = 1.5f;
        llAdquirenteBorderContainerParams.leftMargin = DimUtils.convertDpToPixels(-3);
        llAdquirenteBorderContainer.setLayoutParams(llAdquirenteBorderContainerParams);

        viewAdquirenteBorder.setBackgroundResource(R.drawable.adq_em_small_tab);

        LinearLayout.LayoutParams llContentParentEmisorParams = (LinearLayout.LayoutParams)llContentParentEmisor.getLayoutParams();
        llContentParentEmisorParams.weight = 2.5f;
        llContentParentEmisorParams.leftMargin = 0;
        llContentParentEmisor.setLayoutParams(llContentParentEmisorParams);

        LinearLayout.LayoutParams llMaterialEmisorContainerParams = (LinearLayout.LayoutParams)llMaterialEmisorContainer.getLayoutParams();
        llMaterialEmisorContainerParams.weight = 2f;
        llMaterialEmisorContainerParams.leftMargin = 0;
        llMaterialEmisorContainer.setLayoutParams(llMaterialEmisorContainerParams);

        LinearLayout.LayoutParams llContentParentAdquirenteParams = (LinearLayout.LayoutParams)llContentParentAdquirente.getLayoutParams();
        llContentParentAdquirenteParams.weight = 1.5f;
        llContentParentAdquirenteParams.leftMargin = DimUtils.convertDpToPixels(-3);
        llContentParentAdquirente.setLayoutParams(llContentParentAdquirenteParams);

        LinearLayout.LayoutParams llMaterialAdquirenteContainerParams = (LinearLayout.LayoutParams)llMaterialAdquirenteContainer.getLayoutParams();
        llMaterialAdquirenteContainerParams.weight = 1.25f;
        llMaterialAdquirenteContainerParams.leftMargin = DimUtils.convertDpToPixels(-3);
        llMaterialAdquirenteContainer.setLayoutParams(llMaterialAdquirenteContainerParams);
    }

    private void configAdquirenteSelected() {

        LinearLayout.LayoutParams llEmisorBorederContainerParams = (LinearLayout.LayoutParams)llEmisorBorderContainer.getLayoutParams();
        llEmisorBorederContainerParams.weight = 1.5f;
        llEmisorBorederContainerParams.rightMargin = DimUtils.convertDpToPixels(-3);
        llEmisorBorderContainer.setLayoutParams(llEmisorBorederContainerParams);

        viewEmisorBorder.setBackgroundResource(R.drawable.adq_em_small_tab);
        viewEmisorBorder.setRotationY(180);

        LinearLayout.LayoutParams llAdquirenteBorderContainerParams = (LinearLayout.LayoutParams)llAdquirenteBorderContainer.getLayoutParams();
        llAdquirenteBorderContainerParams.weight = 2.5f;
        llAdquirenteBorderContainerParams.leftMargin = 0;
        llAdquirenteBorderContainer.setLayoutParams(llAdquirenteBorderContainerParams);

        viewAdquirenteBorder.setBackgroundResource(R.drawable.adq_em_big_tab);

        LinearLayout.LayoutParams llContentParentEmisorParams = (LinearLayout.LayoutParams)llContentParentEmisor.getLayoutParams();
        llContentParentEmisorParams.weight = 1.5f;
        llContentParentEmisorParams.leftMargin = DimUtils.convertDpToPixels(-3);
        llContentParentEmisor.setLayoutParams(llContentParentEmisorParams);

        LinearLayout.LayoutParams llMaterialEmisorContainerParams = (LinearLayout.LayoutParams)llMaterialEmisorContainer.getLayoutParams();
        llMaterialEmisorContainerParams.weight = 1.25f;
        llMaterialEmisorContainerParams.leftMargin = DimUtils.convertDpToPixels(-3);
        llMaterialEmisorContainer.setLayoutParams(llMaterialEmisorContainerParams);

        LinearLayout.LayoutParams llContentParentAdquirenteParams = (LinearLayout.LayoutParams)llContentParentAdquirente.getLayoutParams();
        llContentParentAdquirenteParams.weight = 2.5f;
        llContentParentAdquirenteParams.leftMargin = 0;
        llContentParentAdquirente.setLayoutParams(llContentParentAdquirenteParams);

        LinearLayout.LayoutParams llMaterialAdquirenteContainerParams = (LinearLayout.LayoutParams)llMaterialAdquirenteContainer.getLayoutParams();
        llMaterialAdquirenteContainerParams.weight = 2f;
        llMaterialAdquirenteContainerParams.leftMargin = DimUtils.convertDpToPixels(0);
        llMaterialAdquirenteContainer.setLayoutParams(llMaterialAdquirenteContainerParams);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //No-op
        Log.d("Hola","Page selected: " + mViewPager.getCurrentItem() +  "    Position Offset: " + positionOffset);
        animateTabs(positionOffset, mViewPager.getCurrentItem());
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Hola", "Page Selected");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //No-op

    }


    private void animateTabs(float offset, int position) {

        if ( offset <= 0.0 ) {
            offset = position;
        }

        LinearLayout.LayoutParams llEmisorBorederContainerParams = (LinearLayout.LayoutParams)llEmisorBorderContainer.getLayoutParams();
        llEmisorBorederContainerParams.weight = 2.5f - offset;
        llEmisorBorederContainerParams.rightMargin = DimUtils.convertDpToPixels(-3 * offset);
        llEmisorBorderContainer.setLayoutParams(llEmisorBorederContainerParams);

        //LinearLayout.LayoutParams viewEmisorBorderParams = (LinearLayout.LayoutParams)viewEmisorBorder.getLayoutParams();

        if (offset <= 0.0) {
            viewEmisorBorder.setBackgroundResource(R.drawable.adq_em_big_tab);
            viewEmisorBorder.setRotationY(0);
            //viewEmisorBorderParams.weight = 1.25f;

            llMaterialEmisorContainer.removeAllViews();
            llMaterialEmisorContainer.addView(cardEmisorSelected,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else if (offset >= 1.0){
            viewEmisorBorder.setBackgroundResource(R.drawable.adq_em_small_tab);
            viewEmisorBorder.setRotationY(180);
            //viewEmisorBorderParams.weight = 1.25f;

            llMaterialEmisorContainer.removeAllViews();
            llMaterialEmisorContainer.addView(cardEmisor,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            /*if (position == 1) {
                viewEmisorBorderParams.weight = 1.25f + 0.75f*(1 - offset);
            } else {
                viewEmisorBorderParams.weight = 1.25f + 0.75f* offset;
            }*/
        }

        //viewEmisorBorder.setLayoutParams(viewEmisorBorderParams);

        LinearLayout.LayoutParams llAdquirenteBorderContainerParams = (LinearLayout.LayoutParams)llAdquirenteBorderContainer.getLayoutParams();
        llAdquirenteBorderContainerParams.weight = 1.5f + offset;
        llAdquirenteBorderContainerParams.leftMargin = DimUtils.convertDpToPixels(-3 * (1 - offset));
        llAdquirenteBorderContainer.setLayoutParams(llAdquirenteBorderContainerParams);


        //LinearLayout.LayoutParams viewAdquirenteBorderParams = (LinearLayout.LayoutParams)viewAdquirenteBorder.getLayoutParams();

        if (offset <= 0) {
            viewAdquirenteBorder.setBackgroundResource(R.drawable.adq_em_small_tab);
            //viewAdquirenteBorderParams.weight = 1.25f;

            llMaterialAdquirenteContainer.removeAllViews();
            llMaterialAdquirenteContainer.addView(new CardAdq(getContext()),
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else if (offset >= 1){
            viewAdquirenteBorder.setBackgroundResource(R.drawable.adq_em_big_tab);
            //viewAdquirenteBorderParams.weight = 1.25f;

            llMaterialAdquirenteContainer.removeAllViews();
            llMaterialAdquirenteContainer.addView(new CardAdqCredSelected(getContext()),
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }/* else {
            if (position == 0) {
                viewAdquirenteBorderParams.weight = 1.25f + 0.75f * offset;
            } else {
                viewAdquirenteBorderParams.weight = 1.25f + 0.75f * (1 - offset);
            }
        }
        viewAdquirenteBorder.setLayoutParams(viewAdquirenteBorderParams);*/



        LinearLayout.LayoutParams llContentParentEmisorParams = (LinearLayout.LayoutParams)llContentParentEmisor.getLayoutParams();
        llContentParentEmisorParams.weight = 2.5f - offset;
        llContentParentEmisorParams.leftMargin = DimUtils.convertDpToPixels(-3 * offset);
        llContentParentEmisor.setLayoutParams(llContentParentEmisorParams);

        LinearLayout.LayoutParams llMaterialEmisorContainerParams = (LinearLayout.LayoutParams)llMaterialEmisorContainer.getLayoutParams();
        llMaterialEmisorContainerParams.weight = 1.25f + 0.75f * (1 - offset);
        llMaterialEmisorContainerParams.leftMargin = DimUtils.convertDpToPixels(-3 * offset);
        llMaterialEmisorContainer.setLayoutParams(llMaterialEmisorContainerParams);

        LinearLayout.LayoutParams llContentParentAdquirenteParams = (LinearLayout.LayoutParams)llContentParentAdquirente.getLayoutParams();
        llContentParentAdquirenteParams.weight = 1.5f + offset;
        llContentParentAdquirenteParams.leftMargin = DimUtils.convertDpToPixels(-3 * offset);
        llContentParentAdquirente.setLayoutParams(llContentParentAdquirenteParams);

        LinearLayout.LayoutParams llMaterialAdquirenteContainerParams = (LinearLayout.LayoutParams)llMaterialAdquirenteContainer.getLayoutParams();
        llMaterialAdquirenteContainerParams.weight = 1.25f + 0.75f * offset;
        llMaterialAdquirenteContainerParams.leftMargin = DimUtils.convertDpToPixels(-3 * (1 - offset));
        llMaterialAdquirenteContainer.setLayoutParams(llMaterialAdquirenteContainerParams);

    }

}
