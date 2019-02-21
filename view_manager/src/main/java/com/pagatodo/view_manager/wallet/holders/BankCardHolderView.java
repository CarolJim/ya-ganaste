package com.pagatodo.view_manager.wallet.holders;

import android.view.View;
import android.widget.ImageView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.wallet.components.CardData;
import com.pagatodo.view_manager.wallet.components.CardWalletLinearLayout;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

import static com.pagatodo.view_manager.wallet.components.CardWalletLinearLayout.BIG_SCALE;
import static com.pagatodo.view_manager.wallet.components.CardWalletLinearLayout.SMALL_SCALE;

public class BankCardHolderView extends GenericHolder<CardData> {

    private ImageView cardBank;
    private CardWalletLinearLayout cardWalletLinearLayout;
    private boolean isScalabe;

    public BankCardHolderView(@NonNull View itemView, boolean isScalabe) {
        super(itemView);
        this.isScalabe = isScalabe;
        init();

    }

    @Override
    public void init() {
        this.cardBank = this.itemView.findViewById(R.id.bank_card);
        this.cardWalletLinearLayout = this.itemView.findViewById(R.id.root);
    }

    @Override
    public void bind(CardData item, OnHolderListener<CardData> listener) {
        if (item != null) {
            if (item.getUrlIMG() != null){
                Picasso.get()
                        .load(item.getUrlIMG())
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .into(this.cardBank);
            } else {
                if (item.getResIconCard() != null){
                    this.cardBank.setBackground(item.getResIconCard());
                }
            }

        }

        if (listener != null){
            this.itemView.setOnClickListener(view -> listener.onClickView(item,itemView));
        }

        if (this.isScalabe){
            cardWalletLinearLayout.setScaleBoth(BIG_SCALE);
        } else {
            cardWalletLinearLayout.setScaleBoth(SMALL_SCALE);
        }
    }
}
