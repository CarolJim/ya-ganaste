package com.pagatodo.view_manager.recyclers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.LauncherHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.RowFavDataHolder;
import com.pagatodo.view_manager.recyclers.adapters.AllFavoritesAdapter;
import com.pagatodo.view_manager.recyclers.interfaces.PencilListener;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllFavoritesRecycler extends LinearLayout implements LauncherHolder<ArrayList<RowFavDataHolder>> {

    private View rootView;
    private AllFavoritesAdapter adapter;
    private RecyclerView recyclerMain;

    public AllFavoritesRecycler(Context context) {
        super(context);
        intMain();
    }

    public AllFavoritesRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        intMain();
    }

    public AllFavoritesRecycler(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intMain();
    }

    private void intMain(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        rootView = inflater.inflate(R.layout.recharges,this,false);
        init();
        defaultView();
        this.addView(rootView);
    }

    private void defaultView(){
        adapter.addItem(RowFavDataHolder.create("","Agregar","","#FF00FF", null,true));
        adapter.notifyDataSetChanged();
    }

    public void setListItem(ArrayList<RowFavDataHolder> listData){

        if (!listData.isEmpty()){
            adapter.setListData(listData);
            adapter.notifyDataSetChanged();
        }

    }
    public void setOnClickItems(OnHolderListener<RowFavDataHolder> listener){
        adapter.setListener(listener);
    }

    public void setPencilOnClickItem(PencilListener<RowFavDataHolder> listener){
        adapter.setListenerPencil(listener);
    }

    public RecyclerView getRecycler() {
        return recyclerMain;
    }

    public ArrayList<RowFavDataHolder> getListData() {
        return adapter.getListData();
    }

    @Override
    public void init() {
        recyclerMain = rootView.findViewById(R.id.recycler_main);
        recyclerMain.setHasFixedSize(true);
        recyclerMain.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new AllFavoritesAdapter();
        recyclerMain.setAdapter(adapter);
    }

    @Override
    public void bind(ArrayList<RowFavDataHolder> item, OnHolderListener<ArrayList<RowFavDataHolder>> listener) {
        if (item != null){
            adapter.setListData(item);
            adapter.notifyDataSetChanged();
        }
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
