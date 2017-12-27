package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._controllers.manager.EditFavoritesActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsTabPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;
import com.pagatodo.yaganaste.utils.customviews.ListDialog;
import com.pagatodo.yaganaste.utils.customviews.carousel.Carousel;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselAdapter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.EDIT_FAVORITE;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE;
import static com.pagatodo.yaganaste.utils.Recursos.CURRENT_TAB;

/**
 * Created by Jordan on 06/04/2017.
 */

public class FavoritesFragmentCarousel extends GenericFragment implements PaymentsCarrouselManager {

    private static int MAX_CAROUSEL_ITEMS = 12;
    @BindView(R.id.carouselFav)
    Carousel carouselFav;
    @BindView(R.id.layoutCarouselFav)
    LinearLayout layoutCarouselFav;
    @BindView(R.id.imgPagosMain)
    ImageView imgPagosMain;
    View rootView;
    Carousel.ImageAdapter favoriteImageAdapter = null;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    PaymentsTabPresenter paymentsTabPresenter;
    PaymentsTabFragment fragment;
    MovementsTab current_tab;
    boolean isFromClick = false, longClicked;
    private boolean msnOpen = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.current_tab = MovementsTab.getMovementById(getArguments().getInt("TAB"));
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(this.current_tab, this, getContext(), true);
        fragment = (PaymentsTabFragment) getParentFragment();
        try {
            paymentsTabPresenter = fragment.getPresenter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCarouselAdapter(ArrayList<CarouselItem> items) {
        favoriteImageAdapter = new Carousel.ImageAdapter(getContext(), items);
        carouselFav.setAdapter(favoriteImageAdapter);
        carouselFav.setSelection(items.size() > 4 ? 2 : 0, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favoritos_carousel, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        layoutCarouselFav.setVisibility(View.GONE);
        paymentsCarouselPresenter.getFavoriteCarouselItems();
        imgPagosMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.hideFavorites();
            }
        });
        carouselFav.setOnDragCarouselListener(new CarouselAdapter.OnDragCarouselListener() {
            @Override
            public void onStarDrag(CarouselItem item) {
                paymentsTabPresenter.setCarouselItem(item);
            }
        });
        carouselFav.setOnItemClickListener(new CarouselAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CarouselAdapter<?> parent, CarouselItem view, int position, long id) {

                if (position == 0) {
                    isFromClick = true;
                    if (msnOpen) {
                        msnOpen = false;
                        paymentsCarouselPresenter.getFavoriteCarouselItems();
                    }
                } else if (((CarouselItem) favoriteImageAdapter.getItem(position)).getComercio() != null &&
                        ((CarouselItem) favoriteImageAdapter.getItem(position)).getComercio().getIdComercio() == -1) {
                    paymentsTabPresenter.setCarouselItem((CarouselItem) favoriteImageAdapter.getItem(position));
                    fragment.onItemSelected();
                } else if (((CarouselItem) favoriteImageAdapter.getItem(position)).getFavoritos().getIdComercio() != 0) {
                    paymentsTabPresenter.setCarouselItem((CarouselItem) favoriteImageAdapter.getItem(position));
                    fragment.onItemSelected();
                }
            }
        });

        carouselFav.setOnItemLongClickListener(new CarouselAdapter.OnItemLongClickListener()

        {
            @Override
            public boolean onItemLongClick(CarouselAdapter<?> parent, View view, int position,
                                           long id) {
                DataFavoritos dataFavoritos = ((CarouselItem) favoriteImageAdapter.getItem(position)).getFavoritos();
                if (dataFavoritos != null) {
                    Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(100);
                    Intent intent = new Intent(App.getContext(), EditFavoritesActivity.class);
                    intent.putExtra(getString(R.string.favoritos_tag), dataFavoritos);
                    intent.putExtra(CURRENT_TAB, current_tab.getId());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getActivity().startActivityForResult(intent, EDIT_FAVORITE, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    } else {
                        getActivity().startActivityForResult(intent, EDIT_FAVORITE);
                    }
                    longClicked = true;
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutCarouselFav.setClickable(true);
    }

    @Override
    public void showError() {
        //Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        layoutCarouselFav.setVisibility(View.GONE);
    }

    @Override
    public void showErrorService() {
        /*onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        UI.createSimpleCustomDialog("", getString(R.string.error_respuesta), getActivity().getSupportFragmentManager(), getFragmentTag());*/
    }

    @Override
    public void showFavorites() {

    }

    @Override
    public void onSuccess(Double monto) {

    }

    @Override
    public void onError(String error) {
       /* onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        UI.createSimpleCustomDialog("", error, getActivity().getSupportFragmentManager(), getFragmentTag());*/
    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        layoutCarouselFav.setVisibility(View.VISIBLE);
        if (isFromClick) {
            CarouselItem currentItem;
            for (int pos = response.size() - 1; pos > -1; pos--) {
                currentItem = response.get(pos);
                if (currentItem.getFavoritos() == null || currentItem.getFavoritos().getIdComercio() == 0) {
                    response.remove(pos);
                }
            }
            Collections.sort(response, new Comparator<CarouselItem>() {
                @Override
                public int compare(CarouselItem o1, CarouselItem o2) {
                    return o1.getFavoritos().getNombreComercio().compareToIgnoreCase(o2.getFavoritos().getNombre());
                }
            });
            if (response.size() > 0) {
                msnOpen = true;
                ListDialog dialog = new ListDialog(getContext(), response, paymentsTabPresenter, fragment);
                dialog.show();

            } else {
                //Toast.makeText(getActivity(),getString(R.string.empty_list_favorites), Toast.LENGTH_SHORT).show();
                //  UI.createSimpleCustomDialog(getString(R.string.title_dialog_busqueda),getString(R.string.empty_list_favorites),getFragmentManager(),"");
                showDialogMesage(getString(R.string.title_dialog_busqueda), getString(R.string.empty_list_favorites));


            }
            isFromClick = false;
        } else {
            if (response.size() > MAX_CAROUSEL_ITEMS) {
                ArrayList<CarouselItem> subList = new ArrayList<>(response.subList(0, MAX_CAROUSEL_ITEMS));
                setCarouselAdapter(subList);
            } else {
                setCarouselAdapter(response);
            }
        }
    }

    private void showDialogMesage(String mTitulo, String mMesage) {
        UI.createSimpleCustomDialog(mTitulo, mMesage, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        msnOpen = true;
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

  /*  public static void createSimpleCustomDialog(String title, String message, FragmentManager fragmentManager, String tag) {
        final CustomErrorDialog customErrorDialog = CustomErrorDialog.getInstance(R.layout.dialog_custom_error_message, title, message, true, false);
        customErrorDialog.setDialogActions(new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                customErrorDialog.dismiss();
            }

            @Override
            public void actionCancel(Object... params) {

            }
        });
        customErrorDialog.show(fragmentManager, tag);
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == NEW_FAVORITE) {
                paymentsCarouselPresenter.getFavoriteCarouselItems();
            }
        }
    }
}
