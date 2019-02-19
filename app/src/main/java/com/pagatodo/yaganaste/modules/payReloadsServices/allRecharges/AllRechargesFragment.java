package com.pagatodo.yaganaste.modules.payReloadsServices.allRecharges;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.pagatodo.view_manager.controllers.dataholders.RowFavDataHolder;
import com.pagatodo.view_manager.recyclers.AllFavoritesRecycler;
import com.pagatodo.view_manager.recyclers.RechargesRecycler;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.modules.sidebar.About.AboutInfoFragment;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AllRechargesFragment extends GenericFragment {

    private static final String TAG_LIST = "TAG_LIST";
    private View rootView;
    private ArrayList<IconButtonDataHolder> recList;

    @BindView(R.id.all_recharge)
    AllFavoritesRecycler recAll;

    public static AllRechargesFragment newInstance(ArrayList<IconButtonDataHolder> list){
        AllRechargesFragment fragment = new AllRechargesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_LIST,list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            this.recList = (ArrayList<IconButtonDataHolder>) getArguments().getSerializable(TAG_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.allrecharges_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        recAll.setListItem(converte(recList));

    }

    private ArrayList<RowFavDataHolder> converte(ArrayList<IconButtonDataHolder> listData){
        ArrayList<RowFavDataHolder> list = new ArrayList<>();
        for (IconButtonDataHolder dataHolder:listData){
            if (dataHolder.getT() instanceof Comercio){
                Comercio comercio = ((Comercio) dataHolder.getT());
                list.add(RowFavDataHolder.create(comercio.getLogoURLColor(),
                        comercio.getNombreComercio(),""));
            }
        }
        return list;
    }
}
