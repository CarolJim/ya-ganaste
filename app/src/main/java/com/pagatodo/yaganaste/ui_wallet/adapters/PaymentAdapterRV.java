package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPaymentFragment;
import com.pagatodo.yaganaste.ui_wallet.views.DataFavoritosGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.ui_wallet.fragments.NewPaymentFragment.TYPE_CARRIER;

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

            grid = new View(App.getContext());
            grid = inflater.inflate(R.layout.new_payment_item, null);

            // Agregamos el color del borde para todos los casos, en los casos particulares se reescribe
            CircleImageView imageViewBorder = (CircleImageView) grid.findViewById(R.id.imgItemGalleryMark);
            imageViewBorder.setBorderColor(Color.parseColor(myDataset.get(position).getmColor()));


            // Procesos para Carriers
            if (mOperation == TYPE_CARRIER) {
                GradientDrawable gd = createCircleDrawable(Color.BLACK,
                        Color.parseColor(myDataset.get(position).getmColor()));
                imageViewBorder.setBackground(gd);

                // Validacion para cambiar Strings por texto especifico ejemplo IAVE a Pase Urbano
                TextView textView = (TextView) grid.findViewById(R.id.grid_text);
                if (myDataset.get(position).getName().equals("IAVE/Pase Urbano")) {
                    textView.setText("Tag");
                } else if (myDataset.get(position).getName().equals("Telcel Datos")) {
                    textView.setText("Datos");
                } else if (myDataset.get(position).getName().equals("Telmex s/recibo")) {
                    textView.setText("Sin Recibo");
                } else if (myDataset.get(position).getName().equals("Telmex c/recibo")) {
                    textView.setText("Con Recibo");
                } else {
                    textView.setText("" + myDataset.get(position).getName());
                }

                // Cargamos la lupa en caso de existir
                ImageView imageView = (ImageView) grid.findViewById(R.id.imgItemGalleryPay);
                if (myDataset.get(position).getUrlLogo().equals("R.mipmap.buscar_con_texto")) {
                    GradientDrawable gdCarrier = createCircleDrawable(Color.BLACK, Color.WHITE);
                    imageViewBorder.setBackground(gdCarrier);

                    imageView.setBackground(App.getContext().getResources().getDrawable(R.mipmap.new_fav_search));
                    imageView.getLayoutParams().height = 70;
                    imageView.getLayoutParams().width = 70;
                    imageView.requestLayout();

                } else {
                    setImagePicaso(imageView, myDataset.get(position).getUrlLogo());
                }

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.sendData(position, mType);
                    }
                });
                imageViewBorder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.sendData(position, mType);
                    }
                });

            } else {
                // Procesos para favoritos

                // Localizamos el elementos de TextView para Iniciales
                TextView textIniciales = (TextView) grid.findViewById(R.id.textIniciales);


                // Validacion para cambiar IAVE a Pase Urbano
                TextView textView = (TextView) grid.findViewById(R.id.grid_text);
                textView.setText("" + myDataset.get(position).getName());

                String urlImage = myDataset.get(position).getUrlLogo();
                // Cargamos la lupa en caso de existir
                ImageView imageView = (ImageView) grid.findViewById(R.id.imgItemGalleryPay);
                if (urlImage.equals("R.mipmap.buscar_con_texto")) {
                    //  imageViewBorder.setBorderColor(Color.parseColor(myDataset.get(position).getmColor()));

                    GradientDrawable gd = createCircleDrawable(Color.BLACK, Color.WHITE);
                    imageViewBorder.setBackground(gd);

                    imageView.setBackground(App.getContext().getResources().getDrawable(R.mipmap.new_fav_search));
                    imageView.getLayoutParams().height = 35;
                    imageView.getLayoutParams().width = 35;
                    imageView.requestLayout();

                    //imageViewBorder.setBorderColor(Color.WHITE);
                } else if (urlImage.equals("R.mipmap.ic_add_new_favorite")) {
                    //  imageViewBorder.setBorderColor(Color.parseColor(myDataset.get(position).getmColor()));

                    GradientDrawable gd = createCircleDrawable(Color.BLACK, Color.GRAY);
                    imageViewBorder.setBackground(gd);

                    imageView.setBackground(App.getContext().getResources().getDrawable(R.drawable.new_fav_add));
                    //  imageViewBorder.setBorderColor(Color.GRAY);
                } else {
                    if (urlImage.equals("")) {
                        textIniciales.setVisibility(View.VISIBLE);
                        // imageViewBorder.setBorderColor(Color.parseColor(myDataset.get(position).getmColor()));

                        int colorBackground = Color.parseColor(myDataset.get(position).getmColor());
                        GradientDrawable gd = createCircleDrawable(colorBackground,
                                Color.parseColor(myDataset.get(position).getmColor()));
                        imageViewBorder.setBackground(gd);

                        String sIniciales = getIniciales(myDataset.get(position).getName());
                        textIniciales.setText(sIniciales);
                    } else {
                        setImagePicasoFav(imageViewBorder, urlImage);
                    }
                    //imageView.setBackground(App.getContext().getDrawable(R.drawable.ic_add_new_favorite));
                }

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.sendData(position, mType);
                    }
                });
                imageViewBorder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.sendData(position, mType);
                    }
                });

                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mContext.editFavorite(position, mType);
                        return true;
                    }
                });
            }
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    // Se encarga de crear el circulo Drawable que usaremos para mostrar las imagenes o los textos
    private GradientDrawable createCircleDrawable(int colorBackground, int colorBorder) {
        // Creamos el circulo que mostraremos
        int strokeWidth = 2; // 3px not dp
        int roundRadius = 140; // 8px not dp
        int strokeColor = colorBorder;
        int fillColor = colorBackground;

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);

        return gd;
    }

    /**
     * Obtiene las iniciales a mostrar si no tenemos foto: Ejemplo
     * Frank Manzo Nava= FM
     * Francisco = Fr
     *
     * @param fullName
     * @return
     */
    private String getIniciales(String fullName) {
        String[] spliName = fullName.split(" ");
        String sIniciales = "";
        if (spliName.length > 1) {
            sIniciales = spliName[0].substring(0, 1) + spliName[1].substring(0, 1).toUpperCase();
        } else {
            sIniciales = fullName.substring(0, 2).toUpperCase();
        }
        return sIniciales;
    }

    private void setImagePicaso(ImageView imageView, String urlLogo) {
        Picasso.with(App.getContext())
                .load(App.getContext().getString(R.string.url_images_logos) + urlLogo)
                .placeholder(R.mipmap.logo_ya_ganaste)
                .error(R.mipmap.logo_ya_ganaste)
                .into(imageView);
    }

    private void setImagePicasoFav(ImageView imageView, String urlLogo) {
        Picasso.with(App.getContext())
                .load(urlLogo)
                .placeholder(R.mipmap.icon_user)
                .error(R.mipmap.icon_user)
                .into(imageView);

        /*Glide.with(App.getContext()).load(urlLogo).placeholder(R.mipmap.icon_user)
                .error(R.mipmap.ic_launcher)
                .dontAnimate().into(imageView);*/
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
