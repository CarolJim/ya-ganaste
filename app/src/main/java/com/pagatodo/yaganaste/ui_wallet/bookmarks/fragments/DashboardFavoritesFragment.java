package com.pagatodo.yaganaste.ui_wallet.bookmarks.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.system.FacadeBookmarks;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.patterns.ContainerBuilder;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFavoritesFragment extends SupportFragment implements FacadeBookmarks.ListenerFavorite,
        OnClickItemHolderListener {


    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private View rootview;

    public DashboardFavoritesFragment newInstance() {
        return new DashboardFavoritesFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootview = inflater.inflate(R.layout.fragment_dashboard_favorites, container, false);
        this.initViews();
        return this.rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        FacadeBookmarks facadeBookmarks = new FacadeBookmarks(getContext(),this);
        facadeBookmarks.getFavorites();
    }

    @Override
    public void setFavoList(List<Favoritos> lista) {
        container.removeAllViews();
        ContainerBuilder.FAVORITOS(getContext(), container, lista, this);
    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {

    }

    @Override
    public void onClick(Object item) {

    }
}
