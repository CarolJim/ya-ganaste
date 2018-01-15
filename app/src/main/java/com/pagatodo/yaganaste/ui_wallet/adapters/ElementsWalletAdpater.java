package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.holders.ButtonsViewHolder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ElementView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icruz on 11/12/2017.
 */

public class ElementsWalletAdpater extends RecyclerView.Adapter<ButtonsViewHolder>{

    private List<ElementView> elementViews;
    private Context context;
    private OnItemClickListener listener;

    public ElementsWalletAdpater(Context context, OnItemClickListener listener, List<ElementView> elementViews){
        this.context = context;
        this.elementViews = elementViews;
        this.listener = listener;
    }
    public void setOnListener(OnItemClickListener listener){
        this.listener = listener;
    }
    /*
    public ElementsWalletAdpater(Context context, OnItemClickListener listener){
        this.context = context;
        this.elementViews = new ArrayList<>();
        this.listener = listener;
    }*/



    @Override
    public ButtonsViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ButtonsViewHolder(inflater.inflate(R.layout.view_element, parent, false));
    }

    @Override
    public void onBindViewHolder(final ButtonsViewHolder holder, final int position) {
            holder.bind(elementViews.get(position),listener);

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return this.elementViews.size();
    }

    public interface OnItemClickListener{
        void onItemClick(ElementView elementView);
    }

    public void setEmptyList(){
        this.elementViews = new ArrayList<>();
        //this.mViews.clear();
    }

/*    public void setList(ArrayList<ElementView> elementViews){

        this.elementViews = elementViews;
    }*/


}
