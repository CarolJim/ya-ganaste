package com.pagatodo.yaganaste.ui.account.login;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.Scroller;

import com.airbnb.lottie.LottieAnimationView;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.onboarding.OnboardingActivity;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_HELP;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class MainFragment extends GenericFragment implements View.OnClickListener {

    public final static String SELECTION = "SELECTION";
    public final static String IS_FROM_TIMER = "IS_FROM_TIMER";
    public final static String GO_TO_LOGIN = "GO_TO_LOGIN";
    public final static String GO_TO_REGISTER = "GO_TO_REGISTER";
    public final static String NO_SIM_CARD = "NO_SIM_CARD";
    public final static String MAIN_SCREEN = "MAIN_SCREEN";
    private float[] ANIMATION_TIMES = {0f, 0.25f, 0.50f, 0.75f, 1f, 1f};
    private final int NUM_PAGES = 5;
    private View rootview;

    @BindView(R.id.vp_onboarding)
    ViewPager mPager;
    @BindView(R.id.anim_onboarding)
    LottieAnimationView animOnboarding;
    @BindView(R.id.btn_create_user)
    Button btnMainCreateAccount;
    @BindView(R.id.btn_login_user)
    Button btnLogin;
    @BindView(R.id.help_text)
    StyleTextView help_text;

    private PagerAdapter pagerAdapter;

    public static MainFragment newInstance() {
        MainFragment fragmentRegister = new MainFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.activity_main, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnMainCreateAccount.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        help_text.setOnClickListener(this);
        SpannableString content = new SpannableString("¿Necesitas ayuda?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        help_text.setText(content);
        pagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //setAnimationProgress(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setViewPagerScroller();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_user:
                Intent intent = new Intent(getActivity(), ScannVisionActivity.class);
                //intent.putExtra(SELECTION, GO_TO_REGISTER);
                //startActivity(intent);
                //getActivity().finish();

                //startActivity(RegActivity.createIntent(getActivity()));
                startActivity(OnboardingActivity.createIntent(getActivity()));

                /*Intent intent = new Intent(getActivity(), ScannVisionActivity.class);
                intent.putExtra(ScannVisionActivity.QRObject, true);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
*/
                break;

            case R.id.btn_login_user:
                intent = new Intent(getActivity(), AccountActivity.class);
                intent.putExtra(SELECTION, GO_TO_LOGIN);
                startActivity(intent);
                break;
            case R.id.help_text:
                intent = new Intent(getActivity(), AccountActivity.class);
                intent.putExtra(SELECTION, EVENT_GO_HELP);
                startActivity(intent);
                break;



        }
    }

    private void setAnimationProgress(int position, float positionOffset) {
        float startProgress = ANIMATION_TIMES[position];
        float endProgress = ANIMATION_TIMES[position + 1];
        animOnboarding.setProgress(lerp(startProgress, endProgress, positionOffset));
    }

    private float lerp(float startValue, float endValue, float f) {
        return startValue + f * (endValue - startValue);
    }

    private void setViewPagerScroller() {
        //noinspection TryWithIdenticalCatches
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(getActivity(), (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, duration * 7);
                }
            };
            scrollerField.set(mPager, scroller);
        } catch (NoSuchFieldException e) {
            // Do nothing.
        } catch (IllegalAccessException e) {
            // Do nothing.
        }
    }

    /**
     * A simple pager adapter that represents 5 {@link EmptyFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<EmptyFragment> emptyList = new ArrayList<>();

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            for (int i = 0; i < NUM_PAGES; i++) {
                emptyList.add(EmptyFragment.newInstance());
            }
        }

        @Override
        public Fragment getItem(int position) {
            return emptyList.get(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @SuppressLint("ValidFragment")
    public static class EmptyFragment extends Fragment {

        public static EmptyFragment newInstance() {
            return new EmptyFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_empty, container, false);
        }
    }
}

