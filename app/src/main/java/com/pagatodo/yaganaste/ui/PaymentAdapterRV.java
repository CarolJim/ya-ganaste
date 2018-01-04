package com.pagatodo.yaganaste.ui;

import android.content.Context;
import android.graphics.Color;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.ui.NewPaymentFragment.TYPE_CARRIER;

/**
 * Created by FranciscoManzo on 27/12/2017.
 */

public class PaymentAdapterRV extends BaseAdapter {
    private static DisplayMetrics metrics = null;
    ArrayList<DataFavoritosGridView> myDataset;
    IPaymentFragment mContext;
    int mType, mOperation;

    public PaymentAdapterRV(ArrayList<DataFavoritosGridView> myDataset, IPaymentFragment mContext,
                            int mType, int typeOperation) {
        this.myDataset = myDataset;
        this.mContext = mContext;
        this.mType = mType;
        this.mOperation = typeOperation;
        metrics = App.getContext().getResources().getDisplayMetrics();
    }

    @Override
    public int getCount() {
        return myDataset.size();
    }

    @Override
    public Object getItem(int position) {
        //return myDataset.get(position).getmDrawable();
        return 0;
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
//CircleImageView  imgItemGalleryMark  ImageView   imgItemGalleryPay

            grid = new View(App.getContext());
            grid = inflater.inflate(R.layout.new_payment_item, null);

            // Agregamos el color del borde
            CircleImageView imageViewBorder = (CircleImageView) grid.findViewById(R.id.imgItemGalleryMark);
            imageViewBorder.setBorderColor(Color.parseColor(myDataset.get(position).getmColor()));


            if (mOperation == TYPE_CARRIER) {
                // Validacion para cambiar IAVE a Pase Urbano
                TextView textView = (TextView) grid.findViewById(R.id.grid_text);
                if (myDataset.get(position).getName().equals("IAVE/Pase Urbano")) {
                    textView.setText("Tag");
                } else if (myDataset.get(position).getName().equals("Telcel Datos")) {
                    textView.setText("Datos");
                } else {
                    textView.setText("" + myDataset.get(position).getName());
                }


                // Cargamos la lupa en caso de existir
                ImageView imageView = (ImageView) grid.findViewById(R.id.imgItemGalleryPay);
                if (myDataset.get(position).getUrlLogo().equals("R.mipmap.buscar_con_texto")) {
                    imageView.setBackground(App.getContext().getDrawable(R.drawable.places_ic_search));
                    imageViewBorder.setBorderColor(Color.WHITE);
                } else {
                    setImagePicaso(imageView, myDataset.get(position).getUrlLogo());
                }

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.sendData(position, mType);
                    }
                });
            } else {
                // Validacion para cambiar IAVE a Pase Urbano
                TextView textView = (TextView) grid.findViewById(R.id.grid_text);
             /*   if (myDataset.get(position).getName().equals("IAVE/Pase Urbano")) {
                    textView.setText("Tag");
                } else if (myDataset.get(position).getName().equals("Telcel Datos")) {
                    textView.setText("Datos");
                } else {
                    textView.setText("" + myDataset.get(position).getName());
                }*/
                textView.setText("" + myDataset.get(position).getName());

                // Cargamos la lupa en caso de existir
                ImageView imageView = (ImageView) grid.findViewById(R.id.imgItemGalleryPay);
                if (myDataset.get(position).getUrlLogo().equals("R.mipmap.buscar_con_texto")) {
                    imageView.setBackground(App.getContext().getDrawable(R.drawable.places_ic_search));
                    imageViewBorder.setBorderColor(Color.WHITE);
                } else {
                    setImagePicasoFav(imageView, myDataset.get(position).getUrlLogo());
                    //imageView.setBackground(App.getContext().getDrawable(R.drawable.ic_add_new_favorite));
                }

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.sendData(position, mType);
                    }
                });
            }
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    private void setImagePicaso(ImageView imageView, String urlLogo) {
        Picasso.with(App.getContext())
                .load(App.getContext().getString(R.string.url_images_logos) + urlLogo)
                .into(imageView);
    }

    private void setImagePicasoFav(ImageView imageView, String urlLogo) {
        Picasso.with(App.getContext())
                .load("http://10.10.45.11:8033/RecursosApp/RecursosYaGanaste/Favoritos/f865f5e529a95c9ef26bbd760b6e94e3303ae6b320e76e5e0bf267b168107ac1.png")
                .placeholder(R.mipmap.icon_user)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
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
