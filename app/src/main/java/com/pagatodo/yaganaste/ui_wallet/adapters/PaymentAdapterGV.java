package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
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
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.ui_wallet.fragments.NewPaymentFragment.ITEM_CARRIER_PAGOS;
import static com.pagatodo.yaganaste.ui_wallet.fragments.NewPaymentFragment.ITEM_CARRIER_RECARGA;

/**
 * Created by FranciscoManzo on 27/12/2017.
 * Genera un GridView. En dos casos diferentes:
 * 1 - Es un simple GridView que pinta en la View para Carriers
 * 2 - Pinta los datos de Favoritos en un Recycler
 * <p>
 * Para que este metodo se mantenga estable para ambos casos, se agrega adicional recyclerPosition, que es
 * la posicion del RecyclerView
 * 1 - Siempre tendra un -1 al generarse en los Carriers
 * 2 - TEndra una posicion del 0 al N
 */

public class PaymentAdapterGV extends BaseAdapter {
    ArrayList<DataFavoritosGridView> myDataset;
    IPaymentFragment mContext;
    int mTypeItem, mOperationFav, recyclerPosition;

    /**
     * @param myDataset
     * @param mContext
     * @param mTypeItem        Funciona para identificar con que tipo de elemento trabajamos.
     *                         ITEM_CARRIER_RECARGA = 1;
     *                         ITEM_CARRIER_PAGOS = 2;
     *                         ITEM_FAVORITO_RECARGA = 3;
     *                         ITEM_FAVORITO_PAGOS = 4;
     * @param mOperationFav    Controla la operacion que vamos a hacer
     *                         1 : PAgar Favorito
     *                         2 : Editar Favorito
     *                         >3 : Valor para Carriers e ignoramos el camino
     * @param recyclerPosition Controla la posicion del RV para obtener la referencia en el GV. En el caso
     *                         de carriersm se puede manejar una constante TYPE_POSITION = -1
     */
    public PaymentAdapterGV(ArrayList<DataFavoritosGridView> myDataset, IPaymentFragment mContext,
                            int mTypeItem, int mOperationFav, int recyclerPosition) {
        this.myDataset = myDataset;
        this.mContext = mContext;
        this.mTypeItem = mTypeItem;
        this.mOperationFav = mOperationFav;
        this.recyclerPosition = recyclerPosition;
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
            CircleImageView imageCircleCenter = grid.findViewById(R.id.imgItemGalleryMark);
            CircleImageView imageBorder = grid.findViewById(R.id.imgItemGalleryMark2);
            CircleImageView imageCircleEdit = grid.findViewById(R.id.imgItemGalleryStatus);

            // BORDE COLORES imageViewBorder.setBorderColor(Color.parseColor(myDataset.get(position).getmColor()));
            // BORDE GRIS imageCircleCenter.setBorderColor(Color.GRAY);


            // Procesos para Carriers
            if (mTypeItem == ITEM_CARRIER_RECARGA || mTypeItem == ITEM_CARRIER_PAGOS) {
                imageCircleEdit.setVisibility(View.GONE);
                GradientDrawable gd = createCircleDrawable(Color.WHITE, Color.WHITE);

                /*
                BORDE COLORES
                GradientDrawable gd = createCircleDrawable(Color.WHITE,
                        Color.parseColor(myDataset.get(position).getmColor()));
                */
                imageCircleCenter.setBackground(gd);

                // Validacion para cambiar Strings por texto especifico ejemplo IAVE a Pase Urbano
                StyleTextView textView =  grid.findViewById(R.id.grid_text);
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
                ImageView imageLogos = (ImageView) grid.findViewById(R.id.imgItemGalleryPay);
                if (myDataset.get(position).getUrlLogo().equals("R.mipmap.buscar_con_texto")) {
                    GradientDrawable gdCarrier = createCircleDrawable(Color.WHITE, Color.WHITE);
                    //  imageViewBorder.setBackground(gdCarrier);
                    // imageBorder.setBackgroundResource(R.drawable.ic_circle_border_shadow);
                    imageCircleCenter.setVisibility(View.GONE);

                    imageLogos.setBackground(App.getContext().getResources().getDrawable(R.drawable.new_fav_search));
                    imageLogos.getLayoutParams().height =  ViewGroup.LayoutParams.WRAP_CONTENT;
                    imageLogos.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    imageLogos.requestLayout();

                } else {
                    setImagePicaso(imageLogos, myDataset.get(position).getUrlLogo());
                }

                imageBorder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.sendData(position, mTypeItem, recyclerPosition);
                    }
                });
                imageBorder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.sendData(position, mTypeItem, recyclerPosition);
                    }
                });

            } else {
                // Procesos para favoritos

                /**
                 * Mostramos la imagen de editar, esta es solo la parte visual
                 */
                if (mOperationFav == 2 && position != 0) {
                    imageCircleEdit.setImageDrawable(ContextCompat.getDrawable(App.getContext(), R.drawable.edit_icon));
                    imageCircleEdit.setVisibility(View.VISIBLE);
                }

                // Localizamos el elementos de TextView para Iniciales
                TextView textIniciales = (TextView) grid.findViewById(R.id.textIniciales);


                // Validacion para cambiar IAVE a Pase Urbano
                StyleTextView textView = grid.findViewById(R.id.grid_text);
                textView.setText("" + myDataset.get(position).getName());

                String urlImage = myDataset.get(position).getUrlLogo();
                // Cargamos la lupa en caso de existir
                ImageView imageView = (ImageView) grid.findViewById(R.id.imgItemGalleryPay);
                if (urlImage.equals("R.mipmap.buscar_con_texto")) {
                    //  imageViewBorder.setBorderColor(Color.parseColor(myDataset.get(position).getmColor()));

                    GradientDrawable gd = createCircleDrawable(Color.WHITE, Color.WHITE);
                    imageCircleCenter.setBackground(gd);

                    imageView.setBackground(App.getContext().getResources().getDrawable(R.drawable.new_fav_search));
                    imageView.getLayoutParams().height =  ViewGroup.LayoutParams.WRAP_CONTENT;
                    imageView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    imageView.requestLayout();

                    //imageViewBorder.setBorderColor(Color.WHITE);
                } else if (urlImage.equals("R.mipmap.ic_add_new_favorite")) {
                    //  imageViewBorder.setBorderColor(Color.parseColor(myDataset.get(position).getmColor()));

                    GradientDrawable gd = createCircleDrawable(Color.WHITE, Color.WHITE);
                    imageCircleCenter.setBackground(gd);

                    imageView.setBackground(App.getContext().getResources().getDrawable(R.drawable.ic_add_new_favorite));
                    imageView.getLayoutParams().height =  ViewGroup.LayoutParams.WRAP_CONTENT;
                    imageView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    imageView.requestLayout();
                    //  imageViewBorder.setBorderColor(Color.GRAY);
                } else {
                    if (urlImage.equals("")) {
                        textIniciales.setVisibility(View.VISIBLE);
                        // imageViewBorder.setBorderColor(Color.parseColor(myDataset.get(position).getmColor()));

                        int colorBackground = Color.parseColor(myDataset.get(position).getmColor());
                        GradientDrawable gd = createCircleDrawable(colorBackground,
                                Color.parseColor(myDataset.get(position).getmColor()));
                        imageCircleCenter.setBackground(gd);

                        String sIniciales = getIniciales(myDataset.get(position).getName());
                        textIniciales.setText(sIniciales);
                    } else {
                        //imageViewBorder.setBorderColor(Color.GRAY);

                        if (mOperationFav == 2 && position != 0) {
                            int colorBackground = App.getContext().getResources().getColor(R.color.colorTituloDialog);
                            GradientDrawable gd = createCircleDrawable(colorBackground, colorBackground);
                            imageBorder.setBackground(gd);
                        } else {
                            imageBorder.setBorderColor(Color.GRAY);
                        }

                        setImagePicasoFav(imageCircleCenter, urlImage);
                    }
                    //imageView.setBackground(App.getContext().getDrawable(R.drawable.ic_add_new_favorite));
                }

                /**
                 * Enviamos el control de la funcionalidad dependiendo del caso.
                 * mOperationFav = 2 enviamos a editar
                 * mOperationFav = 1 enviamos a pagar
                 *
                 * De esta forma no necesitamos codigo adicional en ningun proceso fuera de aqui
                 */
                imageBorder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOperationFav == 2 && position != 0) {
                            mContext.editFavorite(position, mTypeItem, recyclerPosition);
                        } else {
                            mContext.sendData(position, mTypeItem, recyclerPosition);
                        }
                    }
                });
                imageBorder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOperationFav == 2 && position != 0) {
                            mContext.editFavorite(position, mTypeItem, recyclerPosition);
                        } else {
                            mContext.sendData(position, mTypeItem, recyclerPosition);
                        }
                    }
                });

                /*imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mContext.editFavorite(position, mTypeItem, recyclerPosition);
                        return true;
                    }
                });*/
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
        Picasso.with(App.getContext()).load(App.getContext().getString(R.string.url_images_logos) + urlLogo)
                .into(imageView);
    }

    private void setImagePicasoFav(ImageView imageView, String urlLogo) {
        Picasso.with(App.getContext()).load(urlLogo)
                .placeholder(R.mipmap.icon_user)
                .error(R.mipmap.icon_user)
                .into(imageView);
    }
}
