package com.pagatodo.yaganaste.ui_wallet.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.pagatodo.yaganaste.ui.maintabs.adapters.RecyclerMovementsAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;

/**
 * Created by icruz on 27/02/2018.
 */

public abstract class SwipeToFav extends ItemTouchHelper.SimpleCallback {

    private final Drawable deleteIcon;
    private final int intrinsicWidth;
    private final int intrinsicHeight;
    private final ColorDrawable background;
    private final int backgroundColor;


    public boolean onMove(@Nullable RecyclerView recyclerView, @Nullable RecyclerView.ViewHolder viewHolder, @Nullable RecyclerView.ViewHolder target) {
        return false;
    }

    public void onChildDraw(@Nullable Canvas c, @Nullable RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Intrinsics.checkParameterIsNotNull(viewHolder, "viewHolder");
        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();
        this.background.setColor(this.backgroundColor);
        this.background.setBounds(itemView.getRight() + (int)dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        this.background.draw(c);
        int deleteIconTop = itemView.getTop() + (itemHeight - this.intrinsicHeight) / 2;
        int deleteIconMargin = (itemHeight - this.intrinsicHeight) / 2;
        int deleteIconLeft = itemView.getRight() - deleteIconMargin - this.intrinsicWidth;
        int deleteIconRight = itemView.getRight() - deleteIconMargin;
        int deleteIconBottom = deleteIconTop + this.intrinsicHeight;
        if(this.deleteIcon != null) {
            this.deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        }

        if(this.deleteIcon != null) {
            this.deleteIcon.draw(c);
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @SuppressLint("ResourceType")
    public  SwipeToFav(@NotNull Context context) {
        super(0, ItemTouchHelper.LEFT);
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.deleteIcon = ContextCompat.getDrawable(context, 2131231079);
        Drawable var10001 = this.deleteIcon;
        if(this.deleteIcon == null) {
            Intrinsics.throwNpe();
        }

        this.intrinsicWidth = var10001.getIntrinsicWidth();
        var10001 = this.deleteIcon;
        if(this.deleteIcon == null) {
            Intrinsics.throwNpe();
        }

        this.intrinsicHeight = var10001.getIntrinsicHeight();
        this.background = new ColorDrawable();
        this.backgroundColor = android.graphics.Color.parseColor("#00A1E1");
    }
}