package com.pagatodo.yaganaste.modules.onboarding;

import com.pagatodo.yaganaste.interfaces.enums.Direction;

public class OnboardingContracts {
    public interface Presenter {
        void initViews();
    }

    public interface Router {
        void showOnboarding(Direction direction);

        void showStart(Direction direction);
    }
}
