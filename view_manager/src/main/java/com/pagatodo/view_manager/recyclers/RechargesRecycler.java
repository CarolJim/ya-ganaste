package com.pagatodo.view_manager.recyclers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.ItemOffsetDecoration;
import com.pagatodo.view_manager.controllers.LauncherHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class RechargesRecycler extends LinearLayout implements LauncherHolder<ArrayList<IconButtonDataHolder>> {

    private View rootView;
    private RechargesAdapter adapter;

    public RechargesRecycler(@NonNull Context context) {
        super(context);
        initMain();
    }

    public RechargesRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initMain();
    }

    public RechargesRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initMain();
    }

    private void initMain(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        rootView = inflater.inflate(R.layout.recharges,this,false);
        init();
        defaultView();
        this.addView(rootView);
    }

    private void defaultView(){
        adapter.addItem(new IconButtonDataHolder(getContext().getResources().getDrawable(R.drawable.ic_ico_wallet),
                "AT&T",IconButtonDataHolder.TYPE.ITEM_PAY));
        adapter.addItem(new IconButtonDataHolder(getContext().getResources().getDrawable(R.drawable.ic_ico_wallet),
                "AT&T",IconButtonDataHolder.TYPE.ITEM_RECHARGE));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void init() {
        RecyclerView recyclerMain = rootView.findViewById(R.id.recycler_main);
        recyclerMain.setHasFixedSize(true);
        recyclerMain.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerMain.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.item_offset));
        adapter = new RechargesAdapter();
        recyclerMain.setAdapter(adapter);
    }

    @Override
    public void bind(ArrayList<IconButtonDataHolder> item, OnHolderListener<ArrayList<IconButtonDataHolder>> listener) {
        adapter.setListData(item);
    }

    @Override
    public void inflate(ViewGroup layout) {
        this.addView(layout);
    }

    @Override
    public View getView() {
        return this.rootView;
    }
}
