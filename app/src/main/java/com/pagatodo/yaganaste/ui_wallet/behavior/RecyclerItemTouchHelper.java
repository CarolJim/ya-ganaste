package com.pagatodo.yaganaste.ui_wallet.behavior;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.pagatodo.yaganaste.ui.maintabs.adapters.RecyclerMovementsAdapter;

/**
 * Created by icruz on 22/02/2018.
 */

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    public static int LEFT = 1;
    public static int RIGHT = 2;
    public static int LEFT_AD = 3;
    public static int RIGHT_AD = 4;

    private RecyclerItemTouchHelperListener listener;
    private int direction;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener, int direction) {
        super(dragDirs, swipeDirs);
        this.direction = direction;
        this.listener = listener;

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null && viewHolder instanceof RecyclerMovementsAdapter.RecyclerViewHolderMovements) {
            final View foregroundView = ((RecyclerMovementsAdapter.RecyclerViewHolderMovements) viewHolder).viewForeground;
            final View leftView = ((RecyclerMovementsAdapter.RecyclerViewHolderMovements) viewHolder).viewBackgroundLeft;
            final View rightView = ((RecyclerMovementsAdapter.RecyclerViewHolderMovements) viewHolder).viewBackgroundRight;
            final View leftViewAd = ((RecyclerMovementsAdapter.RecyclerViewHolderMovements) viewHolder).viewBackgroundLeftAd;
            if (direction == LEFT) {
                leftView.setVisibility(View.VISIBLE);
                rightView.setVisibility(View.GONE);
            }

            if (direction == RIGHT) {
                rightView.setVisibility(View.VISIBLE);
                leftView.setVisibility(View.GONE);
            }

            if (direction == LEFT_AD){
                leftViewAd.setVisibility(View.VISIBLE);
            }


            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof RecyclerMovementsAdapter.RecyclerViewHolderMovements) {
            final View foregroundView = ((RecyclerMovementsAdapter.RecyclerViewHolderMovements) viewHolder).viewForeground;
            getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                    actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof RecyclerMovementsAdapter.RecyclerViewHolderMovements) {
            final View foregroundView = ((RecyclerMovementsAdapter.RecyclerViewHolderMovements) viewHolder).viewForeground;
            getDefaultUIUtil().clearView(foregroundView);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof RecyclerMovementsAdapter.RecyclerViewHolderMovements) {
            final View foregroundView = ((RecyclerMovementsAdapter.RecyclerViewHolderMovements) viewHolder).viewForeground;
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                    actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
