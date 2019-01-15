package com.pagatodo.yaganaste.ui.onlinetx.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;


/**
 * @author Juan Guerra on 10/11/2016.
 */

public class TxApprovedFragment extends GenericFragment implements View.OnClickListener {

    private View rootView;

    private ImageView imgStatus;
    private TextView txtTitle;
    private TextView txtDetails;
    private LinearLayout llButton;

    private boolean isApproved;
    private String message;

    private static final String IS_APPROVED = "IS_APPROVED";
    private static final String PARAM_MESSAGE = "MESSAGE";

    public static TxApprovedFragment newInstance() {
        return newInstance(true, App.getInstance().getString(R.string.revisa_el_resumen));
    }


    public static TxApprovedFragment newInstance(boolean isApproved, String message) {
        TxApprovedFragment txApprovedFragment = new TxApprovedFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_APPROVED, isApproved);
        args.putString(PARAM_MESSAGE, message);
        txApprovedFragment.setArguments(args);
        return txApprovedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.isApproved = args.getBoolean(IS_APPROVED);
        this.message = args.getString(PARAM_MESSAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online_operation_success, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {

        imgStatus = (ImageView)rootView.findViewById(R.id.img_status);
        txtTitle = (TextView)rootView.findViewById(R.id.txt_title);
        txtDetails = (TextView)rootView.findViewById(R.id.txt_details);
        llButton = (LinearLayout)rootView.findViewById(R.id.ll_button);

        imgStatus.setImageResource(isApproved ? R.drawable.ic_done : R.drawable.ic_triangle_error);
        txtTitle.setText(isApproved ? R.string.autorizacion_completa : R.string.algo_salio);
        txtDetails.setText(message);
        Button button = createButton(isApproved);
        button.setText(isApproved ? R.string.terminar : R.string.entendido_titulo);
        button.setOnClickListener(this);
        llButton.addView(button, new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.width_btns),
                getResources().getDimensionPixelSize(R.dimen.height_btns)));
    }

    @Override
    public void onClick(View v) {
        getActivity().finish();
    }


    @SuppressLint("RestrictedApi")
    private Button createButton(boolean isApproved) {
        StyleButton button;
        ContextThemeWrapper themeContext;
        if (isApproved) {
            themeContext = new ContextThemeWrapper(getActivity(), R.style.buttonDirecctionNext);
        } else {
            themeContext = new ContextThemeWrapper(getActivity(), R.style.buttonSquareRoundedBlue);
        }
        button = new StyleButton(themeContext);
        button.setTransformationMethod(null);

        return button;
    }

}