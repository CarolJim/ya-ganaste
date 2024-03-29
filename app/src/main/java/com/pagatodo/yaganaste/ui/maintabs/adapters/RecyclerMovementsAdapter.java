package com.pagatodo.yaganaste.ui.maintabs.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecyclerMovementsAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<ItemMovements<T>> itemMovementses;
    private OnRecyclerItemClickListener listener;
    private boolean isEmpty = true;
    private boolean adq;

    public RecyclerMovementsAdapter(@NonNull List<ItemMovements<T>> itemMovementses, @Nullable OnRecyclerItemClickListener listener) {
        if (itemMovementses.size() > 0) {
            this.itemMovementses = itemMovementses;
            isEmpty = false;
        } else {
            this.itemMovementses = new ArrayList<>();
            this.itemMovementses.add(new ItemMovements<T>(App.getContext().getString(R.string.no_movimientos), "", 0, "", "", R.color.transparent));
        }

        this.listener = listener;
    }

    public RecyclerMovementsAdapter(@NonNull List<ItemMovements<T>> itemMovementses, @Nullable OnRecyclerItemClickListener listener, boolean adq) {
        this.adq = adq;
        if (itemMovementses.size() > 0) {
            this.itemMovementses = itemMovementses;
            isEmpty = false;
        } else {
            this.itemMovementses = new ArrayList<>();
            this.itemMovementses.add(new ItemMovements<T>(App.getContext().getString(R.string.no_movimientos), "", 0, "", "", R.color.transparent));
        }

        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return isEmpty ? new RecyclerViewHolderMovementsEmpty(inflater.inflate(R.layout.item_movements_empty, parent, false))
                : new RecyclerViewHolderMovements(inflater.inflate(R.layout.item_movement, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (!isEmpty) {
            ((RecyclerViewHolderMovements) holder).bindData(itemMovementses.get(position), position, this);
        }
    }

    @Override
    public int getItemCount() {
        return itemMovementses.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onRecyclerItemClick(v, Integer.valueOf(v.getTag().toString()));
        }
    }

    static class RecyclerViewHolderMovementsEmpty extends RecyclerView.ViewHolder {

        public RecyclerViewHolderMovementsEmpty(View itemView) {
            super(itemView);
        }
    }

    public class RecyclerViewHolderMovements extends RecyclerView.ViewHolder {

        View layoutMovementTypeColor;
        ImageView upDown;
        TextView txtItemMovDate;
        TextView txtItemMovMonth;
        TextView txtTituloDescripcion;
        TextView txtSubTituloDetalle;
        MontoTextView txtMonto;
        public RelativeLayout viewBackgroundLeft;
        public RelativeLayout viewBackgroundRight;
        public RelativeLayout viewBackgroundLeftAd;
        public LinearLayout viewForeground;

        private RecyclerViewHolderMovements(View itemView) {
            super(itemView);
            layoutMovementTypeColor = itemView.findViewById(R.id.layout_movement_type_color);
            txtItemMovDate = (TextView) itemView.findViewById(R.id.txt_item_mov_date);
            txtItemMovMonth = (TextView) itemView.findViewById(R.id.txt_item_mov_month);
            txtTituloDescripcion = (TextView) itemView.findViewById(R.id.txtTituloDescripcion);
            txtSubTituloDetalle = itemView.findViewById(R.id.txtSubTituloDetalle);
            txtMonto = itemView.findViewById(R.id.txt_monto);
            //txtItemMovCents = (TextView)itemView.findViewById(R.id.txt_item_mov_cents);
            viewBackgroundLeft = itemView.findViewById(R.id.view_background_left);
            viewBackgroundRight = itemView.findViewById(R.id.view_background_right);
            viewBackgroundLeftAd = itemView.findViewById(R.id.view_background_left_adq);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            upDown = itemView.findViewById(R.id.up_down);
        }

        void bindData(ItemMovements itemMovements, int position, View.OnClickListener clickListener) {
            //String[] monto = Utils.getCurrencyValue(itemMovements.getMonto()).split("\\.");
            boolean isComerioUyU = false;
            try {
                isComerioUyU = new DatabaseManager().isComercioUyU(RequestHeaders.getIdCuentaAdq());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (itemMovements.getColor() == R.color.redColorNegativeMovements) {
                if (adq) {
                    upDown.setBackgroundResource(R.drawable.down);
                } else {
                    upDown.setBackgroundResource(R.drawable.down_red);
                }
            }

            if (itemMovements.getColor() == R.color.greenColorPositiveMovements) {
                if (adq) {
                    if (!isComerioUyU)
                    upDown.setBackgroundResource(R.drawable.upadq);
                } else {
                    upDown.setBackgroundResource(R.drawable.up);
                }
            }

            if (itemMovements.getColor() == R.color.colorAccent) {
                if (!isComerioUyU)
                upDown.setBackgroundResource(R.drawable.ico_idle);
            }

            if (itemMovements.getColor() == R.color.redColorNegativeMovementsCancel) {
                if (!isComerioUyU)
                upDown.setBackgroundResource(R.drawable.down);
            }

            layoutMovementTypeColor.setBackgroundResource(itemMovements.getColor());
            //txtMonto.setTextColor(ContextCompat.getColor(App.getContext(), itemMovements.getColor()));
            //updown.se
            if (position % 2 == 0) {
                //System.out.println("El número es par");
                viewForeground.setBackgroundResource(R.color.item_movement);
            } else {
                viewForeground.setBackgroundResource(R.color.whiteColor);
            }
            /*if (((MovimientosResponse)itemMovements.getMovement()) != null){

            }*/
            if (itemMovements.getTituloDescripcion().equals("Envío de Dinero")) {
                MovimientosResponse response = (MovimientosResponse) itemMovements.getMovement();
                if (response !=null)
                {
                    if (itemMovements.getTituloDescripcion().equals("Envío de Dinero"))
                        txtTituloDescripcion.setText("Envío a " + response.getComercio());
                    else
                        txtTituloDescripcion.setText(itemMovements.getTituloDescripcion());
                }else{
                    txtTituloDescripcion.setText(itemMovements.getTituloDescripcion());
                }
            }else{
                txtTituloDescripcion.setText(itemMovements.getTituloDescripcion());
            }




            txtSubTituloDetalle.setText(itemMovements.getSubtituloDetalle());

            txtMonto.setText(StringUtils.getCurrencyValue(Double.toString(itemMovements.getMonto())));//(monto[0].concat("."));

            txtItemMovDate.setText(itemMovements.getDate());
            txtItemMovMonth.setText(itemMovements.getMonth());

            if (itemMovements.getColor() == android.R.color.transparent) {
                //txtMonto.setTextColor(ContextCompat.getColor(App.getContext(), R.color.colorAccent));
            }

            itemView.setOnClickListener(clickListener);
            itemView.setTag(position);
        }
    }

    public void removeAt(int position) {
        this.itemMovementses.remove(position);
        notifyDataSetChanged();
    }

    public ItemMovements getMovItem(int position) {
        return this.itemMovementses.get(position);
    }

    public T getItem(int position) {
        return this.itemMovementses.get(position).getMovement();
    }

    public void updateChange() {
        notifyDataSetChanged();
    }

    public void setVisibilityAddFav(int position) {

    }
}