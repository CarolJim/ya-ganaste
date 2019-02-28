package com.pagatodo.view_manager.recyclers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.ItemOffsetDecoration;
import com.pagatodo.view_manager.controllers.dataholders.MovementDataHolder;
import com.pagatodo.view_manager.recyclers.adapters.MovementAdapters;
import com.pagatodo.view_manager.recyclers.adapters.RechargesAdapter;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovementsRecycler extends LinearLayout {

    private MovementAdapters adapter;
    private TextView textEmpty;

    public MovementsRecycler(Context context) {
        super(context);
        init();
    }

    public MovementsRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MovementsRecycler(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.recharges,this,false);
        RecyclerView recyclerMain = rootView.findViewById(R.id.recycler_main);
        textEmpty = rootView.findViewById(R.id.text_empty);
        recyclerMain.setHasFixedSize(true);
        recyclerMain.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerMain.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.item_offset));
        adapter = new MovementAdapters();
        defualtList();
        recyclerMain.setAdapter(adapter);
        this.addView(rootView);
    }

    private void defualtList(){
        ArrayList<MovementDataHolder> itemlist = new ArrayList<>();
        itemlist.add(MovementDataHolder.create("26","Ene","Venta con tarjeta",
                "Santader 5896","150",
                "",null));

        itemlist.add(MovementDataHolder.create("26","Ene","Venta con tarjeta",
                "Santader 5896","150",
                "",null));
        setItems(itemlist);
    }

    public void setItems(ArrayList<MovementDataHolder> itemlist){
        if (itemlist.isEmpty()){
            textEmpty.setVisibility(VISIBLE);
        } else {
            textEmpty.setVisibility(GONE);
            adapter.setItemlist(itemlist);
            adapter.notifyDataSetChanged();
        }

    }
}
