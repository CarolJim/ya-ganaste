package com.pagatodo.yaganaste.modules.onboarding;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.onboarding.fragments.StartFragment;


public class OnboardingRouter implements OnboardingContracts.Router {

    private OnboardingActivity activityOnboarding;

    OnboardingRouter(OnboardingActivity activityOnboarding) {
        this.activityOnboarding = activityOnboarding;
    }

    @Override
    public void showOnboarding(Direction direction) {
        //activityOnboarding.loadFr
        activityOnboarding.startActivity(OnboardingActivity.createIntent(activityOnboarding));
        //startActivity(OnboardingActivity.createIntent(getActivity()));
    }

    @Override
    public void showStart(Direction direction) {
        activityOnboarding.loadFragment(StartFragment.newInstance(),R.id.container,Direction.FORDWARD,false);
    }
}
