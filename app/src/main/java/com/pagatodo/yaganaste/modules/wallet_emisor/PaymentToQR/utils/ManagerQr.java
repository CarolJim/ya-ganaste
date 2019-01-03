package com.pagatodo.yaganaste.modules.wallet_emisor.PaymentToQR.utils;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.utils.qrcode.QrcodeGenerator;

import java.util.Objects;

import static android.content.Context.WINDOW_SERVICE;

public class ManagerQr {

    public static Bitmap showQRCode(String jsonString) {

        WindowManager manager = (WindowManager) App.getContext().getSystemService(WINDOW_SERVICE);
        Display display = Objects.requireNonNull(manager).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        QrcodeGenerator qrCodeEncoder = new QrcodeGenerator(jsonString, null,
                BarcodeFormat.QR_CODE.toString(), smallerDimension);
        try {
            return qrCodeEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
