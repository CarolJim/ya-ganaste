package com.pagatodo.yaganaste.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.View;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.PDF417Writer;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.utils.qrcode.InterbankQr;
import com.pagatodo.yaganaste.utils.qrcode.MyQr;
import com.pagatodo.yaganaste.utils.qrcode.QrcodeGenerator;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static com.pagatodo.yaganaste.App.getContext;
import static com.pagatodo.yaganaste.utils.qrcode.QrcodeGenerator.BLACK;
import static com.pagatodo.yaganaste.utils.qrcode.QrcodeGenerator.WHITE;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.LAST_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.NUMBER_CARD_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.PHONE_NUMBER;
import static com.pagatodo.yaganaste.utils.StringUtils.ocultarCardNumberFormat;

public class UtilsGraphics {

    public static Bitmap getQrCode(Bitmap parentBitmap) {
        String name = App.getInstance().getPrefs().loadData(FULL_NAME_USER);
        /*String phone = App.getInstance().getPrefs().loadData(PHONE_NUMBER);
        String cardNumber = App.getInstance().getPrefs().loadData(CARD_NUMBER);*/
        String clabe = App.getInstance().getPrefs().loadData(CLABE_NUMBER);
        /*MyQr myQr = new MyQr(name, phone, cardNumber, clabe);*/
        InterbankQr qr = new InterbankQr();
        qr.setVersion("0.1");
        qr.setType("300");
        qr.getOptionalData().countryCode = "MX";
        qr.getOptionalData().currencyCode = "MXN";
        qr.getOptionalData().bankId = "148";
        qr.getOptionalData().beneficiaryAccount = clabe.replace(" ", "");
        qr.getOptionalData().beneficiaryName = (name.length() > 19 ? name.substring(0, 19) : name);
        qr.getOptionalData().referenceNumber = DateUtil.getDayMonthYear();
        String gson = new Gson().toJson(qr);
        QrcodeGenerator qrCodeEncoder = new QrcodeGenerator(gson, null, BarcodeFormat.QR_CODE.toString(),
                /*smallerDimension*/parentBitmap.getHeight() - 15);
        try {
            return qrCodeEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap overlayImages(Bitmap firstImage, Bitmap secondImage, float left, float top) {
        Bitmap result = Bitmap.createBitmap(firstImage.getWidth(), firstImage.getHeight(), firstImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0f, 0f, null);
        canvas.drawBitmap(secondImage, left, top, null);
       /* canvas.drawBitmap(secondImage, (firstImage.getWidth() / 2) - (secondImage.getWidth() / 2),
                (firstImage.getHeight() / 2) - (secondImage.getHeight() / 2), null);*/
        return result;
    }

    public static Bitmap getStarbucksCode(Bitmap parentBitmap) {
        Writer writer = new PDF417Writer();
        String finaldata = Uri.encode(App.getInstance().getPrefs().loadData(NUMBER_CARD_STARBUCKS), null);
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
                pixels[offset + x] = bm.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static Bitmap getTextInBitmap(String text, float textSize) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        Typeface typeface = FontCache.getTypeface("fonts/roboto/Roboto-Regular.ttf", getContext());
        paint.setTypeface(typeface);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    public static Bitmap frontCardYg(Bitmap cardYG) {
        /* Obtener numero de tarjeta en Bitmap */
        Bitmap cardNumber = getTextInBitmap(!App.getInstance().getPrefs().loadData(CARD_NUMBER).equals("") ? ocultarCardNumberFormat(App.getInstance().getPrefs().loadData(CARD_NUMBER)) :
                        "**** **** **** XXXX",
                20 * App.getContext().getResources().getDisplayMetrics().density);
        /* Obtener nombre del cliente en Bitmap */
        String splitName[] = App.getInstance().getPrefs().loadData(NAME_USER).split(" ");
        Bitmap nameUser = null;
        if (SingletonUser.getInstance().getDataUser().getCliente() == null){
            nameUser = getTextInBitmap(splitName[0] + " " +
                    App.getInstance().getPrefs().loadData(LAST_NAME), 14 * App.getContext().getResources().getDisplayMetrics().density);
        } else {
            nameUser = getTextInBitmap(splitName[0] + " " +
                    SingletonUser.getInstance().getDataUser().getCliente().getPrimerApellido(), 14 * App.getContext().getResources().getDisplayMetrics().density);
        }

        /* Pegar numero de tarjeta en diseño tarjeta */
        Bitmap faceView = overlayImages(cardYG, cardNumber, 40, cardYG.getHeight() / 2);
        /* Pegar nombre del cliente en diseño tarjeta */
        return overlayImages(faceView, nameUser, 40, (faceView.getHeight() / 2) + (cardNumber.getHeight() * 1.2F));
    }

    public static Bitmap frontCardBusiness(Bitmap cardBusiness, String name) {
        Bitmap nameBusiness = getTextInBitmap(name,
                20 * App.getContext().getResources().getDisplayMetrics().density);
        return overlayImages(cardBusiness, nameBusiness, 40, cardBusiness.getHeight() / 2);
        /* Pegar nombre del cliente en diseño tarjeta */
        //return overlayImages(faceView, nameUser, 40, (faceView.getHeight() / 2) + (cardNumber.getHeight() * 1.2F));
    }

    public static int Dp(int px, View view){
        float scale = view.getResources().getDisplayMetrics().density;
        return (int) (scale * px + 0.5f);
    }
}
