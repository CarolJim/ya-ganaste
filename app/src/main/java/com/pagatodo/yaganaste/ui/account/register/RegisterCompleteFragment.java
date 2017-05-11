package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_DOC_CHECK;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class RegisterCompleteFragment extends GenericFragment implements View.OnClickListener {
    public String TAG = getClass().getSimpleName();
    public static String TIPO_MENSAJE = "TIPO_MENSAJE";
    private View rootview;
    @BindView(R.id.imgCompleted)
    AppCompatImageView imgCompleted;
    @BindView(R.id.txtTitle)
    StyleTextView txtTitle;
    @BindView(R.id.txtSubtitle)
    StyleTextView txtSubtitle;
    @BindView(R.id.txtMessage)
    StyleTextView txtMessage;
    @BindView(R.id.btnNextComplete)
    Button btnNextComplete;

    private COMPLETE_MESSAGES type;
    private int iIdIcon = 0;
    private String title = "";
    private String subTitle = "";
    private String message = "";
    private String btnName = "";
    private String NEXT_SCREEN = "";

    public enum COMPLETE_MESSAGES {
        EMISOR,
        ADQ_REVISION,
        ADQ_ACEPTADOS,
        SALDO
    }

    public RegisterCompleteFragment() {
    }

    public static RegisterCompleteFragment newInstance(COMPLETE_MESSAGES type) {
        RegisterCompleteFragment fragmentRegister = new RegisterCompleteFragment();
        Bundle args = new Bundle();
        args.putSerializable(TIPO_MENSAJE, type);
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
        if (getArguments() != null) {

            Bundle b = getArguments();
            type = (COMPLETE_MESSAGES) b.get(TIPO_MENSAJE);
        }
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
        rootview = inflater.inflate(R.layout.fragment_register_complete, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        setContent();
        btnNextComplete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNextComplete:
                onEventListener.onEvent(NEXT_SCREEN, null);
                break;
            default:
                break;
        }
    }

    private void setContent() {
        Log.e("type ", "- -- - - - - " + type);
        switch (type) {
            case EMISOR:
                iIdIcon = R.drawable.ic_done;
                title = getString(R.string.txt_felicidades);
                subTitle = getString(R.string.congratulation_text1);
                message = getString(R.string.congratulation_text2);
                btnName = getString(R.string.next);
                NEXT_SCREEN = EVENT_GO_MAINTAB;
                break;
            case ADQ_REVISION:
                Log.e(TAG, "-  - - - -  - - - - ADQ_REVISION");
                iIdIcon = R.drawable.ic_done;
                title = getString(R.string.adq_title_thanks);
                subTitle = getString(R.string.adq_subtitle_thanks);
                message = getString(R.string.adq_title_thanks_msg);
                btnName = getString(R.string.nextButton);
                NEXT_SCREEN = EVENT_DOC_CHECK;
                break;
            case ADQ_ACEPTADOS:
                iIdIcon = R.drawable.ic_done;
                title = getString(R.string.txt_felicidades);
                subTitle = getString(R.string.adq_subtitle_accept);
                message = getString(R.string.adq_msg_accept);
                btnName = getString(R.string.nextButton);
                NEXT_SCREEN = EVENT_GO_MAINTAB;
                break;
            case SALDO:
                iIdIcon = R.drawable.ic_done;
                title = getString(R.string.txt_felicidades);
                subTitle = getString(R.string.txt_felicidades);
                message = getString(R.string.txt_felicidades);
                btnName = getString(R.string.txt_felicidades);
                NEXT_SCREEN = EVENT_GO_MAINTAB;
                break;
        }

        /*Seteamos la información.*/
        imgCompleted.setImageResource(iIdIcon);
        txtTitle.setText(title);
        txtSubtitle.setText(subTitle);
        txtMessage.setText(message);
        btnNextComplete.setText(btnName);
    }
}

