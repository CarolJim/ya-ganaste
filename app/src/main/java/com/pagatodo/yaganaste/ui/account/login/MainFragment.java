package com.pagatodo.yaganaste.ui.account.login;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.rollviewpager.RollPagerView;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.CustomTypefaceSpan;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class MainFragment extends GenericFragment implements View.OnClickListener {

    public final static String SELECTION = "SELECTION";
    public final static String GO_TO_LOGIN = "GO_TO_LOGIN";
    public final static String GO_TO_REGISTER = "GO_TO_REGISTER";
    public final static String NO_SIM_CARD = "NO_SIM_CARD";
    public final static String MAIN_SCREEN = "MAIN_SCREEN";
    @BindView(R.id.btnMainCreateAccount)
    StyleButton btnMainCreateAccount;
    @BindView(R.id.txtMainLogin)
    StyleTextView txtMainLogin;
    //    @BindView(R.id.viewpager)
//    AutoScrollViewPager pager;
    @BindView(R.id.rollPager)
    RollPagerView rollPagerView;
    private View rootview;

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

        String textLogin = getString(R.string.tienes_cuenta);
        SpannableString ss = new SpannableString(textLogin);

        ss.setSpan(new ClickableSpanLogin(), 22, textLogin.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.whiteColor)), 22, textLogin.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Light.ttf");
        Typeface fontBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Bold.ttf");

        ss.setSpan(new CustomTypefaceSpan("", font), 0, 22, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ss.setSpan(new CustomTypefaceSpan("", fontBold), 22, textLogin.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        //ss.setSpan(new StyleSpan(Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Light.ttf").getStyle()), 0, 22, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //ss.setSpan(new StyleSpan(Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Bold.ttf").getStyle()), 22, textLogin.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);


        txtMainLogin.setText(ss);
        txtMainLogin.setMovementMethod(LinkMovementMethod.getInstance());

        // Se encarga de hacer Set en el Adapter
        rollPagerView.setAdapter(new AdapterRollPager(rollPagerView, getActivity()));
        rollPagerView.setHintView(null);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMainCreateAccount:
                Intent intent = new Intent(getActivity(), RegistryCupoActivity.class);
                intent.putExtra(SELECTION, GO_TO_REGISTER);
                startActivity(intent);
                //getActivity().finish();
                break;
        }
    }

    class ClickableSpanLogin extends ClickableSpan {

        public ClickableSpanLogin() {
            super();
        }

        public void onClick(View tv) {
            Intent intent = new Intent(getActivity(), AccountActivity.class);
            intent.putExtra(SELECTION, GO_TO_LOGIN);
            startActivity(intent);
        }

        public void updateDrawState(TextPaint ds) {// override updateDrawState
            ds.setUnderlineText(false);
        }
    }
}

