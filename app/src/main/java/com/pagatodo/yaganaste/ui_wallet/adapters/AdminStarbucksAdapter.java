package com.pagatodo.yaganaste.ui_wallet.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CardStarbucks;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.NUMBER_CARD_STARBUCKS;

public class AdminStarbucksAdapter extends RecyclerView.Adapter<AdminStarbucksAdapter.CardStarbucksViewHolder> {

    private OnRecyclerItemClickListener onRecyclerItemClickListener;
    private List<CardStarbucks> cardStarbucks;

    public AdminStarbucksAdapter(OnRecyclerItemClickListener onRecyclerItemClickListener, List<CardStarbucks> cardStarbucks) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
        this.cardStarbucks = cardStarbucks;
    }

    @Override
    public CardStarbucksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_admin_card_i, parent, false);
        return new CardStarbucksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardStarbucksViewHolder holder, int position) {
        CardStarbucks card = cardStarbucks.get(position);
        if (card.getStatusTarjeta().equals("Active")) {
            Picasso.with(App.getContext()).load(card.getImagenes().getMediana()).into(holder.imgCard);
            holder.txtTitle.setText(App.getContext().getString(R.string.title_cards_starbucks));
            holder.txtDesc.setText("Saldo: " + StringUtils.getCurrencyValue(card.getSaldo()));
            holder.imgStatus.setVisibility(View.VISIBLE);
            if (card.getNumeroTarjeta().equals(App.getInstance().getPrefs().loadData(NUMBER_CARD_STARBUCKS))) {
                holder.imgStatus.setImageResource(R.drawable.rdb_pressed);
            } else {
                holder.imgStatus.setImageResource(R.drawable.rdb_not_pressed);
            }
        }
    }


    @Override
    public int getItemCount() {
        return cardStarbucks.size();
    }

    class CardStarbucksViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lytRow;
        StyleTextView txtTitle, txtDesc;
        ImageView imgStatus, imgCard;

        public CardStarbucksViewHolder(View itemView) {
            super(itemView);
            lytRow = (LinearLayout) itemView.findViewById(R.id.lyt_row_admin_card);
            imgCard = (ImageView) itemView.findViewById(R.id.img_card_admin);
            txtTitle = (StyleTextView) itemView.findViewById(R.id.txt_title_admin_card);
            txtDesc = (StyleTextView) itemView.findViewById(R.id.txt_desc_admin_card);
            imgStatus = (ImageView) itemView.findViewById(R.id.img_status_admin_card);
            lytRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerItemClickListener.onRecyclerItemClick(v, getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
