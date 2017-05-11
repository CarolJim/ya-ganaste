package com.pagatodo.yaganaste.ui.account.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.RecoveryPasswordView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui._manager.PagerAdapter;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RECOVERY_PASS_BACK;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class MainFragment extends GenericFragment implements View.OnClickListener{

    private View rootview;

    public final static String SELECTION = "SELECTION";
    public final static String GO_TO_LOGIN = "GO_TO_LOGIN";
    public final static String GO_TO_REGISTER = "GO_TO_REGISTER";

    @BindView(R.id.btnMainCreateAccount)
    StyleButton btnMainCreateAccount;
    @BindView(R.id.txtMainLogin)
    StyleTextView txtMainLogin;
    @BindView(R.id.viewpager)
    AutoScrollViewPager pager;
    private PagerAdapter pagerAdapter;
    private List<Fragment> listSlides;
    private Preferencias pref;

    private int[] imgs = {
            R.drawable.carrousel1,
            R.drawable.carrousel2,
            R.drawable.carrousel3,
            R.drawable.carrousel4,
    };


    public MainFragment() {

    }

    public static MainFragment newInstance() {
        MainFragment fragmentRegister = new MainFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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
        listSlides = new ArrayList<Fragment>();

        String[] topText = {
                getString(R.string.carrouse_title_1),
                getString(R.string.carrouse_title_2),
                getString(R.string.carrouse_title_3),
                "",
        };

        for (int index = 0; index < imgs.length; index++) {
            listSlides.add(ScreenSlidePagefragment.newInstance(imgs[index], topText[index]));
        }


        String textLogin = getString(R.string.tienes_cuenta);
        SpannableString ss = new SpannableString(textLogin);
        ClickableSpan span1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                intent.putExtra(SELECTION,GO_TO_LOGIN);
                startActivity(intent);
            }
        };
        ss.setSpan(span1, 22, textLogin.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)),  22, textLogin.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtMainLogin.setText(ss);
        txtMainLogin.setMovementMethod(LinkMovementMethod.getInstance());


        pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        for(Fragment slide: listSlides){
            pagerAdapter.addFragment(slide);
        }
        this.pager.setAdapter(pagerAdapter);
        this.pager.startAutoScroll(1000);

        /*this.pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                // Page is not an immediate sibling, just make transparent
                if(position < -1 || position > 1) {
                    page.setAlpha(0);
                }
                // Page is sibling to left or right
                else if (position <= 0 || position <= 1) {

                    // Calculate alpha.  Position is decimal in [-1,0] or [0,1]
                    float alpha = (position <= 0) ? position + 1 : 1 - position;
                    page.setAlpha(alpha);

                }
                // Page is active, make fully visible
                else if (position == 0) {
                    page.setAlpha(1);
                }
            }
        });*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMainCreateAccount:
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                intent.putExtra(SELECTION,GO_TO_REGISTER);
                startActivity(intent);
                break;
        }
    }
}

