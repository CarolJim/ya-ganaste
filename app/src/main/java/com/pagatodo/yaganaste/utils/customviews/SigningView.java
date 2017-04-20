package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.Signature;
import com.pagatodo.yaganaste.utils.UI;


/**
 * Created by jvazquez on 27/10/2016.
 */

public class SigningView extends View {

    private static final float TOUCH_TOLERANCE = 4;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private TextView tvFirmaAqui;

    private final Paint mPaint;
    Path path = new Path();
    private Signature signature;


    float mX, mY;
    public int width, height;

    public SigningView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(2f);
        mPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
//        mPaint.setARGB(0xff, 0x33, 0x33, 0x33);
    }

    public SigningView(Context context, TextView tvFirmaAqui) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(2f);
        mPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
//        mPaint.setARGB(0xff, 0x33, 0x33, 0x33);

        this.tvFirmaAqui = tvFirmaAqui;
        signature =  new Signature();
    }

    public void clear() {
        if (this.mCanvas != null) {
            this.mCanvas.drawColor(Color.TRANSPARENT);
            mBitmap.eraseColor(Color.TRANSPARENT);
            invalidate();
            signature =  new Signature();
            tvFirmaAqui.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int curW = this.mBitmap != null ? this.mBitmap.getWidth() : 0;
        int curH = this.mBitmap != null ? this.mBitmap.getHeight() : 0;
        if (curW >= w && curH >= h) {
            return;
        }

        if (curW < w) curW = w;
        if (curH < h) curH = h;

        width = w;
        height = h;

//        this.mBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.RGB_565);
        this.mBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.ARGB_4444);

        this.mCanvas = new Canvas(this.mBitmap);
        this.mCanvas.drawColor(Color.TRANSPARENT);
//        this.mCanvas.drawColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.mBitmap != null) {
            canvas.drawBitmap(this.mBitmap, 0, 0, null);
        }
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tvFirmaAqui.setVisibility(View.GONE);
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }


    private void touchStart(float x, float y) {
        path.reset();
        path.moveTo(x, y);
        mX = x;
        mY = y;

        signature.newStroke();
        signature.setPoint(x,y);
        signature.setDimension(String.valueOf(width), String.valueOf(height));
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mCanvas.drawPath(path, mPaint);
            mX = x;
            mY = y;
            signature.setPoint(x,y);
        }
    }

    public Signature getSign() {
        return signature;
    }

    public int getW(){
        return width;
    }

    public int getH(){
        return height;
    }
}
