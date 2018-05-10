package com.pagatodo.yaganaste.ui_wallet.adapters;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.PDF417Writer;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ICardBalance;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.utils.QrcodeGenerator;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flipview.FlipView;

import static com.pagatodo.yaganaste.utils.QrcodeGenerator.BLUE;
import static com.pagatodo.yaganaste.utils.QrcodeGenerator.WHITE;
import static com.pagatodo.yaganaste.utils.Recursos.DEBUG;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.NUMBER_CARD_STARBUCKS;
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
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), item.getResourceBack());
        switch (position) {
            case 0:
                Bitmap qrCode = getQrCode(backView);
                flipView.setRearImageBitmap(createSingleImageFromMultipleImages(backView, qrCode));
                break;
            case 1:
                if (!RequestHeaders.getTokenAdq().isEmpty()) {
                    flipView.setRearImage(item.getResourceBack());
                } else if (prefs.loadDataBoolean(HAS_STARBUCKS, false)) {
                    Bitmap starbucksCode = getStarbucksCode(backView);
                    flipView.setRearImageBitmap(createSingleImageFromMultipleImages(backView, starbucksCode));
                }
                break;
            case 2:
                Bitmap starbucksCode = getStarbucksCode(backView);
                flipView.setRearImageBitmap(createSingleImageFromMultipleImages(backView, starbucksCode));
                break;
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

    private Bitmap getQrCode(Bitmap parentBitmap) {
        String name = prefs.loadData(FULL_NAME_USER);
        String phone = prefs.loadData(PHONE_NUMBER);
        String cardNumber = prefs.loadData(CARD_NUMBER);
        String clabe = prefs.loadData(CLABE_NUMBER);
        QrcodeGenerator.MyQr myQr = new QrcodeGenerator.MyQr(name, phone, cardNumber, clabe);
        String gson = new Gson().toJson(myQr);
        QrcodeGenerator qrCodeEncoder = new QrcodeGenerator(gson, null, BarcodeFormat.QR_CODE.toString(),
                /*smallerDimension*/parentBitmap.getHeight() - 15);
        try {
            return qrCodeEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap getStarbucksCode(Bitmap parentBitmap) {
        Writer writer = new PDF417Writer();
        String finaldata = Uri.encode(prefs.loadData(NUMBER_CARD_STARBUCKS), null);
        BitMatrix bm = null;
        try {
            bm = writer.encode(finaldata, BarcodeFormat.PDF_417, parentBitmap.getWidth() - 15, parentBitmap.getHeight() - 15);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = bm.getWidth();
        int height = bm.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bm.get(x, y) ? BLUE : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage) {
        Bitmap result = Bitmap.createBitmap(firstImage.getWidth(), firstImage.getHeight(), firstImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0f, 0f, null);
        canvas.drawBitmap(secondImage, (firstImage.getWidth() / 2) - (secondImage.getWidth() / 2),
                (firstImage.getHeight() / 2) - (secondImage.getHeight() / 2), null);
        return result;
    }
}
