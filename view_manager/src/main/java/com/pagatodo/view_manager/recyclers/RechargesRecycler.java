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
import com.pagatodo.view_manager.recyclers.adapters.RechargesAdapter;


import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder.TYPE.ADD_RECHARGE;


public class RechargesRecycler extends LinearLayout implements LauncherHolder<ArrayList<IconButtonDataHolder>>,
        Serializable {

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
        addFav(ADD_RECHARGE);
        adapter.notifyDataSetChanged();
        this.addView(rootView);
    }

    public IconButtonDataHolder addFav(IconButtonDataHolder.TYPE type){
        return new IconButtonDataHolder(getContext().getResources().getDrawable(R.drawable.ic_add),
                getContext().getResources().getString(R.string.add_fav),
                "Agregar",
                type);
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
        if (item != null){
            adapter.setListData(item);
            adapter.notifyDataSetChanged();
        }
    }

    public ArrayList<IconButtonDataHolder> getListData(){
        return this.adapter.getListData();
    }



    @Override
    public void inflate(ViewGroup layout) {
        this.addView(layout);
    }

    @Override
    public View getView() {
        return this.rootView;
    }

    public void setListItem(ArrayList<IconButtonDataHolder> listData){
        if (!listData.isEmpty()){
            //defaultView();
            adapter.setListData(listData);
            adapter.notifyDataSetChanged();
        }
    }




    public ArrayList<IconButtonDataHolder> getList(){
        return adapter.getListData();
    }

    public void setOnClickItems(OnHolderListener<IconButtonDataHolder> listener){
        adapter.setListener(listener);
    }
}