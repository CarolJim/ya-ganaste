package com.pagatodo.yaganaste.ui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;

import java.util.ArrayList;

/**
 * Created by FranciscoManzo on 27/12/2017.
 */

public class PaymentAdapterRV extends BaseAdapter {
    private static DisplayMetrics metrics = null;
    ArrayList<DataFavoritosGridView> myDataset;
    IPaymentFragment mContext;
    int mType;

    public PaymentAdapterRV(ArrayList<DataFavoritosGridView> myDataset, IPaymentFragment mContext, int mType) {
        this.myDataset = myDataset;
        this.mContext = mContext;
        this.mType = mType;
        metrics = App.getContext().getResources().getDisplayMetrics();
    }

    @Override
    public int getCount() {
        return myDataset.size();
    }

    @Override
    public Object getItem(int position) {
        return myDataset.get(position).getmDrawable();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) App.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(App.getContext());
            grid = inflater.inflate(R.layout.new_payment_item, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText("" + myDataset.get(position).getmName());
            imageView.setImageResource(myDataset.get(position).getmDrawable());
            //imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.sendData(position, mType);
                }
            });
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
        /*ImageView imageView;
        int dpInt = 50;
        int dpPad = 5;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(App.getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(toPixels(dpInt),
                    toPixels(dpInt)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(toPixels(dpPad), toPixels(dpPad), toPixels(dpPad), toPixels(dpPad));
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(myDataset.get(position).getmDrawable());
        imageView.setImageResource(R.mipmap.ic_launcher);
        return imageView;*/

    public static int toPixels(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}
