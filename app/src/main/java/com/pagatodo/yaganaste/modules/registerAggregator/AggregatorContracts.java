package com.pagatodo.yaganaste.modules.registerAggregator;

import com.pagatodo.yaganaste.interfaces.enums.Direction;

public class AggregatorContracts {

    public interface Presenter{
        void initViews();
        void nextStep();
        void backStep();
    }

    public interface Listener{

    }

    public interface Iteractor{

    }

    public interface Router{
        void showBusinessData(Direction direction);
    }
}
