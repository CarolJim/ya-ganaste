package com.pagatodo.yaganaste.ui_wallet.bookmarks.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.interfaces.View;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.system.FacadeBookmarks;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.patterns.ContainerBuilder;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;

public class BoardBookmarks implements FacadeBookmarks.ListenerFavorite  {

    private OnClickItemHolderListener listener;
    private FacadeBookmarks facade;
    private Context context;
    private ViewGroup viewGroup;

    public BoardBookmarks(Context context, ViewGroup viewGroup, OnClickItemHolderListener listener) {
        this.context = context;
        this.listener = listener;
        this.viewGroup = viewGroup;
        this.facade = new FacadeBookmarks(context,this);
        //init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        /*this.layout = new LinearLayout(this.context);
        this.layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));*/

    }

    public void getLayout(){
        this.facade.getFavorites();
        //return viewGroup;
    }

    @Override
    public void setFavoList(List<Favoritos> lista) {
        viewGroup.removeAllViews();
        ContainerBuilder.FAVORITOS(this.context, viewGroup, lista, this.listener);
    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {


            ArrayList<Integer> orderBy = new ArrayList<>();
            orderBy.add(8609);
            orderBy.add(785);
            orderBy.add(779);
            orderBy.add(787);
            orderBy.add(809);
            orderBy.add(790);
            orderBy.add(799);
            orderBy.add(796);
            orderBy.add(832);

            response = Utils.removeNullCarouselItem(response);
            ArrayList<CarouselItem> list = new ArrayList<>(response);
            /*for (CarouselItem carouselItem : response) {
                list.add(carouselItem);
            }*/

            /**
             * Buscamos en nuestro orderBy cada elemento en un ciclo adicional de originalList, si el ID existe
             * lo agregamos a nuesta finalList. Y eliminamos ese elemnto de originalList
             */
            ArrayList<CarouselItem> finalList = new ArrayList<>();
            for (Integer miList : orderBy) {
                for (int x = 0; x < list.size(); x++) {
                    if (list.get(x).getComercio().getIdComercio() == miList) {
                        finalList.add(list.get(x));
                        list.remove(x);
                    }
                }
            }
            Collections.sort(list, (o1, o2) -> o1.getComercio().getNombreComercio().compareToIgnoreCase(o2.getComercio().getNombreComercio()));
            finalList.addAll(list);
            //ContainerBuilder.FAVORITOS(context, viewGroup, lista, this.listener);
            /*for (int x = 0; x < list.size(); x++) {
                finalList.add(list.get(x));
            }*/

    }


    /*
    backUpResponsefavo = new ArrayList<>();
    backUpResponseFavoritos = new ArrayList<>();

        for (CarouselItem carouselItem : mResponse) {
        backUpResponsefavo.add(carouselItem);
    }
        Collections.sort(backUpResponsefavo, (o1, o2) -> o1.getFavoritos().getNombre().compareToIgnoreCase(o2.getFavoritos().getNombre()));*/
}
