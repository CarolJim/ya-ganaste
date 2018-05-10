package com.pagatodo.yaganaste.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.PDF417Writer;
import com.pagatodo.yaganaste.App;

import static com.pagatodo.yaganaste.utils.QrcodeGenerator.BLUE;
import static com.pagatodo.yaganaste.utils.QrcodeGenerator.WHITE;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.NUMBER_CARD_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.PHONE_NUMBER;

public class UtilsGraphics  {

    public static Bitmap getQrCode(Bitmap parentBitmap) {
        String name = App.getInstance().getPrefs().loadData(FULL_NAME_USER);
        String phone = App.getInstance().getPrefs().loadData(PHONE_NUMBER);
        String cardNumber = App.getInstance().getPrefs().loadData(CARD_NUMBER);
        String clabe = App.getInstance().getPrefs().loadData(CLABE_NUMBER);
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

    public static Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage) {
        Bitmap result = Bitmap.createBitmap(firstImage.getWidth(), firstImage.getHeight(), firstImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0f, 0f, null);
        canvas.drawBitmap(secondImage, (firstImage.getWidth() / 2) - (secondImage.getWidth() / 2),
                (firstImage.getHeight() / 2) - (secondImage.getHeight() / 2), null);
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
                pixels[offset + x] = bm.get(x, y) ? BLUE : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
