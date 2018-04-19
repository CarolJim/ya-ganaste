package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoAdminCards;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_EMISOR;

public class AdminCardsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;
    private List<DtoAdminCards> cardsList;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public AdminCardsAdapter(List<DtoAdminCards> cardsList, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.cardsList = cardsList;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_admin_card_h, parent, false);
            return new HeaderAdminViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_admin_card_i, parent, false);
            return new ItemAdminViewHolder(layoutView);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DtoAdminCards dtoAdminCards = cardsList.get(position);
        if (holder instanceof HeaderAdminViewHolder) {
            ((HeaderAdminViewHolder) holder).txtTitle.setText(dtoAdminCards.getTitle());
        } else if (holder instanceof ItemAdminViewHolder) {
            ((ItemAdminViewHolder) holder).imgCard.setImageResource(dtoAdminCards.getResource());
            ((ItemAdminViewHolder) holder).txtTitle.setText(dtoAdminCards.getTitle());
            ((ItemAdminViewHolder) holder).txtDesc.setText(dtoAdminCards.getDescription());
            if (dtoAdminCards.getID() != TYPE_EMISOR && dtoAdminCards.getID() != TYPE_ADQ) {
                ((ItemAdminViewHolder) holder).imgStatus.setVisibility(View.VISIBLE);
                if (dtoAdminCards.getStatus() == 1) {
                    ((ItemAdminViewHolder) holder).imgStatus.setImageResource(R.drawable.rdb_pressed);
                } else {
                    ((ItemAdminViewHolder) holder).imgStatus.setImageResource(R.drawable.rdb_not_pressed);
                }
            } else {
                ((ItemAdminViewHolder) holder).imgStatus.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return cardsList.get(position).getTypeView();
    }

    class HeaderAdminViewHolder extends RecyclerView.ViewHolder {
        StyleTextView txtTitle;

        public HeaderAdminViewHolder(View itemView) {
            super(itemView);
            txtTitle = (StyleTextView) itemView.findViewById(R.id.header_admin_card);
        }
    }

    class ItemAdminViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout lytRow;
        public StyleTextView txtTitle, txtDesc;
        public ImageView imgStatus, imgCard;

        public ItemAdminViewHolder(View itemView) {
            super(itemView);
            lytRow = (LinearLayout) itemView.findViewById(R.id.lyt_row_admin_card);
            imgCard = (ImageView) itemView.findViewById(R.id.img_card_admin);
            txtTitle = (StyleTextView) itemView.findViewById(R.id.txt_title_admin_card);
            txtDesc = (StyleTextView) itemView.findViewById(R.id.txt_desc_admin_card);
            imgStatus = (ImageView) itemView.findViewById(R.id.img_status_admin_card);
            lytRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //positionSelected = getAdapterPosition();
                    onRecyclerItemClickListener.onRecyclerItemClick(v, getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
