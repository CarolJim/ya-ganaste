package com.pagatodo.yaganaste.ui_wallet.adapters;


import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ICardBalance;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.utils.QrcodeGenerator;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flipview.FlipView;

import static com.pagatodo.yaganaste.utils.Recursos.DEBUG;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.PHONE_NUMBER;

/**
 * Created by icruz on 11/12/2017.
 */

public class BalanceWalletAdpater extends PagerAdapter implements CardAdapter {


    private ArrayList<ElementWallet> elementViewList;
    private float mBaseElevation;
    private List<CardView> mViews;
    private List<FlipView> mFlippers;
    private ICardBalance cardBalance;
    private Preferencias prefs = App.getInstance().getPrefs();

    public BalanceWalletAdpater(ICardBalance cardBalance) {
        FlipView.enableLogs(DEBUG);
        elementViewList = new ArrayList<>();
        mViews = new ArrayList<CardView>();
        mFlippers = new ArrayList<>();
        this.cardBalance = cardBalance;
    }

    public void addCardItem(ElementWallet item) {
        mViews.add(null);
        mFlippers.add(null);
        elementViewList.add(item);
    }

    @Override
    public int getCount() {
        return this.elementViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.card_balance_element, container, false);
        container.addView(view);
        bind(elementViewList.get(position), view, position);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }
        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        cardView.setPreventCornerOverlap(false);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardBalance.onCardClick(v, position);
            }
        });
        mViews.set(position, cardView);
        return view;
    }

    private void bind(ElementWallet item, View view, final int position) {
        FlipView flipView = (FlipView) view.findViewById(R.id.cardflip_element);
        flipView.setFrontImage(item.getResourceCard());
        if (position == 0) {
            Bitmap qrCode = getQrCode();
            flipView.setRearImageBitmap(qrCode);
        } else {
            flipView.setRearImage(item.getResourceBack());
        }
        flipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardBalance.onCardClick(v, position);
            }
        });
        flipView.flip(false);
        mFlippers.set(position, flipView);
    }

    public void restartFlippers() {
        for (FlipView flipView : mFlippers) {
            flipView.flip(false);
        }
        this.notifyDataSetChanged();
    }

    public ElementWallet getElemenWallet(int position) {
        return this.elementViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView((LinearLayout) object);
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    private Bitmap getQrCode() {
        String name = prefs.loadData(FULL_NAME_USER);
        String phone = prefs.loadData(PHONE_NUMBER);
        String cardNumber = prefs.loadData(CARD_NUMBER);
        String clabe = prefs.loadData(CLABE_NUMBER);
        QrcodeGenerator.MyQr myQr = new QrcodeGenerator.MyQr(name, phone, cardNumber, clabe);
        String gson = new Gson().toJson(myQr);
        QrcodeGenerator qrCodeEncoder = new QrcodeGenerator(gson, null, BarcodeFormat.QR_CODE.toString(), /*smallerDimension*/Utils.convertDpToPixels(500));
        try {
            return qrCodeEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
