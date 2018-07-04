package com.pagatodo.yaganaste.ui_wallet.bookmarks.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui_wallet.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.system.FacadeBookmarks;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementFavorite;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment.ITEM_OPERATION;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ADDFAVORITE_PAYMENT;

public class BoardBookmarks implements FacadeBookmarks.ListenerFavorite, OnClickItemHolderListener{

    private OnClickItemHolderListener listener;
    private FacadeBookmarks facade;
    private Activity context;
    private ViewGroup viewGroup;

    public BoardBookmarks(Activity context, ViewGroup viewGroup, OnClickItemHolderListener listener) {
        this.context = context;
        this.listener = listener;
        this.viewGroup = viewGroup;
        this.facade = new FacadeBookmarks(context,this);
    }

    public void getLayout(){
        this.facade.getFavorites();
    }

    @Override
    public void setFavoList(List<Favoritos> lista) {
        viewGroup.removeAllViews();
        ContainerBuilder.FAVORITOS(this.context, viewGroup, lista, this);
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


    private void intentNewFavorite(Activity activity, ElementFavorite itemOperation){
        //UtilsIntents.favoriteNewIntent(activity);
        Intent intent = new Intent(activity, WalletMainActivity.class);
        intent.putExtra(ITEM_OPERATION, itemOperation);
        activity.startActivity(intent);
    }


    @Override
    public void onClick(Object item) {
        Favoritos favorito = (Favoritos) item;
        ElementFavorite itemOperation = new ElementFavorite(OPTION_ADDFAVORITE_PAYMENT,favorito);
        if (favorito.getIdComercio() == 0) {
            intentNewFavorite(context, itemOperation);
        }
    }
}
