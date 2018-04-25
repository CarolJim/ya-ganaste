package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CardStarbucks;
import com.pagatodo.yaganaste.data.room_db.entities.Rewards;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.NUMBER_CARD_STARBUCKS;

public class RewardsStarbucksAdapter extends RecyclerView.Adapter<RewardsStarbucksAdapter.ViewHolder> {

    private List<Rewards> rewards;

    public RewardsStarbucksAdapter(List<Rewards> rewards) {
        this.rewards = rewards;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rewards_starbucks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Rewards reward = rewards.get(position);
        holder.txtTitle.setText(reward.getDescripcion());
        if(position%2!=0){
            holder.row.setBackgroundColor(Color.WHITE);
        } else {
            holder.row.setBackgroundColor(App.getContext().getResources().getColor(R.color.backgraund_wallet));
        }
    }


    @Override
    public int getItemCount() {
        return rewards.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout row;
        StyleTextView txtTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            row = (LinearLayout) itemView.findViewById(R.id.row_rewards);
            txtTitle = (StyleTextView) itemView.findViewById(R.id.txt_title_rewards);
        }
    }
}
