package com.pagatodo.yaganaste.ui.adquirente;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.PageResult;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class TransactionResultFragment extends GenericFragment implements View.OnClickListener, INavigationView {

    public static String KEY_PAGE_RESULT = "KEYPAGERESULT";
    private View rootview;

    @BindView(R.id.imgResult)
    ImageView imgResult;
    @BindView(R.id.txtTitleResult)
    StyleTextView txtTitleResult;
    @BindView(R.id.txtSubtitleResult)
    StyleTextView txtMessageResult;
    @BindView(R.id.txtDescriptionResult)
    StyleTextView txtDescriptionResult;
    @BindView(R.id.btnNextResult)
    StyleButton btnPrimaryResult;
    @BindView(R.id.btnErrorResult)
    StyleButton btnSecondaryResult;

    private PageResult pageResultData;

    public TransactionResultFragment() {
    }

    public static TransactionResultFragment newInstance(PageResult pageResult){
        TransactionResultFragment fragmentRegister = new TransactionResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_PAGE_RESULT,pageResult);
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
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        pageResultData = (PageResult) getArguments().get(KEY_PAGE_RESULT);
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
        btnPrimaryResult.setOnClickListener(this);
        btnSecondaryResult.setOnClickListener(this);
        btnPrimaryResult.setText(pageResultData.getNamerBtnPrimary());
        btnSecondaryResult.setText(pageResultData.getNamerBtnSecondary());
        txtTitleResult.setText(pageResultData.getTitle());
        txtMessageResult.setText(pageResultData.getMessage());

        txtDescriptionResult.setText(pageResultData.getDescription());
        imgResult.setImageResource(pageResultData.getIdResurceIcon());
        btnSecondaryResult.setVisibility(pageResultData.isHasSecondaryAction() ? VISIBLE : GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNextResult:
                if(pageResultData.getActionBtnPrimary() != null)
                    pageResultData.getActionBtnPrimary().action(getActivity(),this);
                break;
            case R.id.btnErrorResult:
                if(pageResultData.getActionBtnSecondary() != null)
                    pageResultData.getActionBtnSecondary().action(getActivity(),this);
                break;
            default:
                break;
        }
    }


    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event,data);
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
}

