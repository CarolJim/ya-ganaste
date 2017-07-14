package com.pagatodo.yaganaste.ui.account.login;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.pagatodo.yaganaste.R;

/**
 * Created by Francisco Manzo on 26/05/2017.
 */

public class AdapterRollPager extends LoopPagerAdapter {

    private Context context;

    private int[] imgs = {
            R.drawable.carrousel1a,
            R.drawable.carrousel2,
            R.drawable.carrousel3,
            R.drawable.carrousel4
    };

    private String[] topText = {
            "",
            "",
            "",
            ""
    };

    private String[] bothText = {
            "que no te Cuesta Nada...",
            "que te da Dinero por Comprar",
            "Tiempo Aire y Tag",
            "tus Servicios"
    };

    public AdapterRollPager(RollPagerView viewPager, Context ctx) {
        super(viewPager);
        context = ctx;
    }

    @Override
    public View getView(ViewGroup container, int position) {

        ViewHolder holder = new ViewHolder();
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View fila = inflater.inflate(R.layout.item_adapter_roll_pager, container, false);
        holder.imgBg = (ImageView) fila.findViewById(R.id.imgBgRollPage);
        holder.txtTop = (TextView) fila.findViewById(R.id.txtHeadRollPage);
        holder.txtBoth = (TextView) fila.findViewById(R.id.txtBothRollPage);

        holder.imgBg.setImageResource(imgs[position]);
        holder.txtTop.setText(topText[position]);
        holder.txtBoth.setText(bothText[position]);

        return fila;
    }

    @Override
    public int getRealCount() {
        return imgs.length;
    }

    private class ViewHolder {
        ImageView imgBg;
        TextView txtTop;
        TextView txtBoth;
    }
}
