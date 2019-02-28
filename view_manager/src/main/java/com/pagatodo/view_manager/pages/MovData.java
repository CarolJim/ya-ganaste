package com.pagatodo.view_manager.pages;

import com.pagatodo.view_manager.controllers.dataholders.MovementDataHolder;

import java.util.ArrayList;

public class MovData {
    private String nameTab;
    private ArrayList<MovementDataHolder> movementDataHolders;

    public MovData(String nameTab) {
        this.nameTab = nameTab;
        this.movementDataHolders = new ArrayList<>();
    }

    public String getNameTab() {
        return nameTab;
    }

    public void setNameTab(String nameTab) {
        this.nameTab = nameTab;
    }

    public ArrayList<MovementDataHolder> getMovementDataHolders() {
        return movementDataHolders;
    }

    public void setMovementDataHolders(ArrayList<MovementDataHolder> movementDataHolders) {
        this.movementDataHolders = movementDataHolders;
    }
}
