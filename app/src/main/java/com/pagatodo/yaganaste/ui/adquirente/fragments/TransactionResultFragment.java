package com.pagatodo.yaganaste.ui.adquirente.fragments;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.PageResult;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.data.model.PageResult.BTN_ACTION_ERROR;
import static com.pagatodo.yaganaste.data.model.PageResult.BTN_DIRECTION_BACK;
import static com.pagatodo.yaganaste.data.model.PageResult.BTN_DIRECTION_NEXT;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class TransactionResultFragment extends GenericFragment implements View.OnClickListener, INavigationView {

    @IdRes
    private static final int idBtnPrimary = 43322;
    @IdRes
    private static final int idBtnSecondary = 43321;
    public static String KEY_PAGE_RESULT = "KEYPAGERESULT";
    @BindView(R.id.imgResult)
    ImageView imgResult;
    @BindView(R.id.txtTitleResult)
    StyleTextView txtTitleResult;
    @BindView(R.id.txtSubtitleResult)
    StyleTextView txtMessageResult;
    //@BindView(R.id.btnNextResult)
    //StyleButton btnPrimaryResult;
    //@BindView(R.id.btnErrorResult)
    //StyleButton btnSecondaryResult;
    @BindView(R.id.layoutButtonsResult)
    LinearLayout llContentBtns;
    @BindView(R.id.txtDescriptionResult)
    StyleTextView txtDescriptionResult;
    private View rootview;
    private PageResult pageResultData;

    public TransactionResultFragment() {
    }


    public static TransactionResultFragment newInstance(PageResult pageResult) {

        TransactionResultFragment fragmentRegister = new TransactionResultFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_PAGE_RESULT, pageResult);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        pageResultData = (PageResult) getArguments().getParcelable(KEY_PAGE_RESULT);
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

        rootview = inflater.inflate(R.layout.fragment_result_trasaction, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        if (pageResultData != null) {
            txtTitleResult.setText(!pageResultData.getTitle().isEmpty() ? pageResultData.getTitle() : "");
            txtMessageResult.setText(!pageResultData.getMessage().isEmpty() ? pageResultData.getMessage() : "");

            txtDescriptionResult.setText(!pageResultData.getDescription().isEmpty() ? pageResultData.getDescription() : "");
            imgResult.setImageResource(pageResultData.getIdResurceIcon());
        }
        setAndConfigureBtns(llContentBtns);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case idBtnPrimary:
                if (pageResultData.getActionBtnPrimary() != null)
                    pageResultData.getActionBtnPrimary().action(getActivity(), this);
                break;
            case idBtnSecondary:
                if (pageResultData.getActionBtnSecondary() != null)
                    pageResultData.getActionBtnSecondary().action(getActivity(), this);
                break;
            default:
                break;
        }
    }


    @Override
    public void nextScreen(String event, Object data) {
        //getActivity().getFragmentManager().popBackStack();
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showError(Object error) {

    }

    private void setAndConfigureBtns(LinearLayout llContentBtns) {


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.width_btns)
                - (pageResultData.isHasSecondaryAction() ? Utils.convertDpToPixels(5) : 0),
                getResources().getDimensionPixelSize(R.dimen.height_btns));

        LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT, 1);

        if (llContentBtns != null) {
            StyleButton btnPrimary = createButton(pageResultData.getBtnPrimaryType());
            btnPrimary.setOnClickListener(this);
            btnPrimary.setId(idBtnPrimary);
            btnPrimary.setText(pageResultData.getNamerBtnPrimary());
            llContentBtns.addView(new Space(getContext()), spaceParams);
            llContentBtns.addView(btnPrimary, params);
            llContentBtns.addView(new Space(getContext()), spaceParams);
            if (pageResultData.isHasSecondaryAction()) {
                StyleButton btnSecondary = createButton(pageResultData.getBtnSecundaryType());
                btnSecondary.setOnClickListener(this);
                btnSecondary.setId(idBtnSecondary);
                btnSecondary.setText(pageResultData.getNamerBtnSecondary());
                llContentBtns.addView(btnSecondary, params);
                llContentBtns.addView(new Space(getContext()), spaceParams);
            }
        }
    }

    private StyleButton createButton(String types) {
        StyleButton button;

        switch (types) {
            case BTN_DIRECTION_NEXT:
                ContextThemeWrapper themeContext = new ContextThemeWrapper(getActivity(), R.style.buttonDirecctionNext);
                button = new StyleButton(themeContext);
                break;
            case BTN_DIRECTION_BACK:
                themeContext = new ContextThemeWrapper(getActivity(), R.style.buttonDirecctionBack);
                button = new StyleButton(themeContext);
                break;
//            case BTN_ACTION_OK:
//                button.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.button_rounded_blue));
//                button.setTextColor(ContextCompat.getColor(getActivity(), R.color.whiteColor));
//                break;
            case BTN_ACTION_ERROR:
                themeContext = new ContextThemeWrapper(getActivity(), R.style.buttonSquareRoundedBlue);
                button = new StyleButton(themeContext);
                break;
            default:
                themeContext = new ContextThemeWrapper(getActivity(), R.style.buttonSquareRoundedBlue);
                button = new StyleButton(themeContext);
                break;
        }
        button.setTransformationMethod(null);
        return button;
    }
}
