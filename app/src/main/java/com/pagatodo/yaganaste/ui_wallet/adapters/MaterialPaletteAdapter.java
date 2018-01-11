package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.List;

/**
 * Created by Armando Sandoval on 09/01/2018.
 */

public class MaterialPaletteAdapter extends RecyclerView.Adapter<MaterialPaletteAdapter.PaletteViewHolder> {
    private List<CarouselItem> data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public MaterialPaletteAdapter(@NonNull List<CarouselItem> data,
                                  @NonNull RecyclerViewOnItemClickListener
                                          recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        CarouselItem item = data.get(position);
        GradientDrawable gradientDrawable = (GradientDrawable) holder.getCircleView().getBackground();
        int colorId;
        if (position == 0) {
            holder.getTitleTextView().setText("Agregar");
            colorId = android.graphics.Color.parseColor(item.getColor());
        } else {
            holder.getTitleTextView().setText(item.getFavoritos().getNombre());
            colorId = android.graphics.Color.parseColor(item.getFavoritos().getColorMarca());
        }
        gradientDrawable.setColor(colorId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PaletteViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        private View circleView;
        private TextView titleTextView;


        public PaletteViewHolder(View itemView) {
            super(itemView);
            circleView = itemView.findViewById(R.id.circleView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);

            itemView.setOnClickListener(this);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }


        public View getCircleView() {
            return circleView;
        }

        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

}

