package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Toast;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.dto.MonthsMovementsTab;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui.maintabs.adapters.RecyclerMovementsAdapter;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.AccountMovementsPresenter;

import java.util.List;

/**
 * @author Juan Guerra on 27/11/2016.
 */

public class PersonalAccountFragment extends AbstractAdEmFragment<MonthsMovementsTab, ItemMovements<MovimientosResponse>> {

    RecyclerView.Adapter currentAdapter;

    public static PersonalAccountFragment newInstance() {
        PersonalAccountFragment homeTabFragment = new PersonalAccountFragment();
        Bundle args = new Bundle();
        homeTabFragment.setArguments(args);
        return homeTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movementsPresenter = new AccountMovementsPresenter(this);
    }

    @Override
    public void initViews() {
        super.initViews();
        tabMonths.setEnabled(false);
    }

    @Override
    protected void onTabLoaded() {
        tabMonths.getTabAt(tabMonths.getTabCount() - 1).select();
    }

    @Override
    protected ViewPagerDataFactory.TABS getTab() {
        return ViewPagerDataFactory.TABS.PERSONAL_ACCOUNT;
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        super.onRefresh(direction);
        this.direction = direction;
        List<ItemMovements<MovimientosResponse>> actualList = this.movementsList.get(tabMonths.getSelectedTabPosition());
        if (actualList != null) {
            String itemId = "";
            int listSize = actualList.size();
            if (direction == SwipyRefreshLayoutDirection.TOP) {
                if (listSize > 0) {
                    itemId = actualList.get(0).getMovement().getIdMovimiento();
                }
            } else {
                if (listSize > 0) {
                    itemId = actualList.get(listSize - 1).getMovement().getIdMovimiento();
                }
            }
            movementsPresenter.getRemoteMovementsData(tabMonths.getCurrentData(tabMonths.getSelectedTabPosition()), direction, itemId);
        } else {
            //showLoader("");
            getDataForTab(tabMonths.getCurrentData(tabMonths.getSelectedTabPosition()));
        }
    }

    @Override
    public void loadMovementsResult(List<ItemMovements<MovimientosResponse>> movementsList) {

        List<ItemMovements<MovimientosResponse>> actualList = null;
        int tabPosition = tabMonths.getSelectedTabPosition();


        try {
            actualList = this.movementsList.get(tabPosition);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (actualList != null && actualList.size() > 0) {
            if (movementsList.size() > 0) {
                if (direction.equals(SwipyRefreshLayoutDirection.TOP)) {
                    this.movementsList.get(tabPosition).addAll(0, movementsList);
                } else {
                    this.movementsList.get(tabPosition).addAll(actualList.size(), movementsList);
                }
                currentAdapter.notifyDataSetChanged();
            } else {
                if (direction.equals(SwipyRefreshLayoutDirection.BOTTOM)) {
                    swipeContainer.setDirection(SwipyRefreshLayoutDirection.TOP);
                    doubleSwipePosition.put(tabPosition, false);
                }
            }
        } else {
            this.movementsList.set(tabPosition, movementsList);
            currentAdapter = createAdapter(this.movementsList.get(tabPosition));
            updateRecyclerData(currentAdapter, movementsList);
        }
    }

    @Override
    protected void updateRecyclerData(RecyclerView.Adapter adapter, List<ItemMovements<MovimientosResponse>> movements) {
        //txtInfoMovements.setVisibility(Movements.isEmpty() ? View.VISIBLE : View.GONE);
        updateRecyclerData(adapter);
    }

    @Override
    protected RecyclerView.Adapter createAdapter(final List<ItemMovements<MovimientosResponse>> movementsList) {
        return new RecyclerMovementsAdapter<>(movementsList, this);
    }

    @Override
    protected void performClickOnRecycler(ItemMovements<MovimientosResponse> itemClicked) {
        startActivity(DetailsActivity.createIntent(getActivity(), itemClicked.getMovement()));
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.change_image_transform));
            setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));
        }*/
/*
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.ID, Contact.CONTACTS[position].getId());
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                // the context of the activity
                MainActivity.this,

                // For each shared element, add to this method a new Pair item,
                // which contains the reference of the view we are transitioning *from*,
                // and the value of the transitionName attribute
                new Pair<View, String>(view.findViewById(R.id.CONTACT_circle),
                        getString(R.string.transition_name_circle)),
                new Pair<View, String>(view.findViewById(R.id.CONTACT_name),
                        getString(R.string.transition_name_name)),
                new Pair<View, String>(view.findViewById(R.id.CONTACT_phone),
                        getString(R.string.transition_name_phone))
        );
        ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
        */
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof RecyclerMovementsAdapter.RecyclerViewHolderMovements) {
            Toast.makeText(getContext(),"" + position,Toast.LENGTH_SHORT).show();
            // get the removed item name to display it in snack bar
            //String name = cartList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            //final ClipData.Item deletedItem = cartList.get(viewHolder.getAdapterPosition());
            //final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            //mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            /*Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    //mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();*/
        } else {
            Toast.makeText(getContext(),"" + position,Toast.LENGTH_SHORT).show();
        }
    }
}
