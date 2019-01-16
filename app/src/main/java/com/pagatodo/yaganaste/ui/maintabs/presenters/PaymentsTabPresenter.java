package com.pagatodo.yaganaste.ui.maintabs.presenters;

import androidx.room.Database;
import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsTabPresenter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.concurrent.ExecutionException;

/**
 * Created by Jordan on 11/04/2017.
 */

public class PaymentsTabPresenter implements IPaymentsTabPresenter {
    private CarouselItem carouselItem;

    @Override
    public CarouselItem getCarouselItem() {
        return carouselItem;
    }

    @Override
    public Comercio getComercioById(int idComercio) {
        try {
            return new DatabaseManager().getComercioById(idComercio);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Comercio();
    }

    @Override
    public void setCarouselItem(CarouselItem carouselItem) {
        this.carouselItem = carouselItem;
    }
}