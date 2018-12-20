package com.pagatodo.yaganaste.ui.account.register;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_DOC_CHECK;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.ADQ_REVISION;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_CONFIG_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class RegisterCompleteFragment extends GenericFragment implements View.OnClickListener {
    public static String TIPO_MENSAJE = "TIPO_MENSAJE";
    public String TAG = getClass().getSimpleName();
    @BindView(R.id.lottie_view)
    LottieAnimationView lottie;
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
    private View rootview;
    private COMPLETE_MESSAGES type;
    private int iIdIcon = 0;
    private String title = "";
    private String subTitle = "";
    private String message = "";
    private String btnName = "";
    private String NEXT_SCREEN = "";
    public static boolean isdocumentsrevision = false;

    public static RegisterCompleteFragment newInstance(COMPLETE_MESSAGES type) {
        RegisterCompleteFragment fragmentRegister = new RegisterCompleteFragment();
        Bundle args = new Bundle();
        args.putSerializable(TIPO_MENSAJE, type);

        if (type == ADQ_REVISION) {
            isdocumentsrevision = true;
        }
        fragmentRegister.setArguments(args);
        return fragmentRegister;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        if (isdocumentsrevision) {
            btnName = getString(R.string.nextButton);
            txtSubtitle.setText(getString(R.string.adq_subtitle_thanks));
        }
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
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
            Log.e("type ", "- -- - - - - " + type);
        }
        switch (type) {
            case EMISOR:
                iIdIcon = R.drawable.ic_done;
                title = getString(R.string.txt_felicidades);
                subTitle = getString(R.string.congratulation_text1);
                message = getString(R.string.congratulation_text2);
                btnName = getString(R.string.next);
                txtSubtitle.setText(subTitle);
                btnNextComplete.setText(btnName);
                NEXT_SCREEN = EVENT_GO_MAINTAB;
                break;
            case ADQ_REVISION:
                iIdIcon = R.drawable.ic_done;
                title = getString(R.string.adq_title_thanks);
                subTitle = getString(R.string.adq_subtitle_thanks);
                message = getString(R.string.adq_title_thanks_msg);
                btnName = getString(R.string.nextButton);
                txtSubtitle.setText(getString(R.string.adq_subtitle_thanks));
                btnNextComplete.setText("Continuar");
                App.getInstance().getPrefs().saveDataInt(MODE_CONNECTION_DONGLE, QPOSService.CommunicationMode.BLUETOOTH.ordinal());
                App.getInstance().getPrefs().saveDataBool(HAS_CONFIG_DONGLE, true);
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
            case ADD_FAV:
                iIdIcon = R.drawable.ic_star;
                title = getString(R.string.txt_felicidades);
                subTitle = getString(R.string.congratulation_text3);
                message = getString(R.string.adq_msg_accept);
                btnName = getString(R.string.nextButton);
                lottie.setVisibility(View.GONE);
                imgCompleted.setImageResource(iIdIcon);
                imgCompleted.setVisibility(View.VISIBLE);
                txtSubtitle.setText(subTitle);
                btnNextComplete.setText(btnName);
                NEXT_SCREEN = EVENT_GO_MAINTAB;
                break;
            default:
                NEXT_SCREEN = EVENT_GO_MAINTAB;
                break;
        }

        /*Seteamos la información.*/
        /*imgCompleted.setImageResource(iIdIcon);
        txtTitle.setText(title);
        txtSubtitle.setText(subTitle);
        txtMessage.setText(message);
        btnNextComplete.setText(btnName);*/
    }

    public enum COMPLETE_MESSAGES {
        EMISOR,
        ADQ_REVISION,
        ADQ_ACEPTADOS,
        SALDO,
        ADD_FAV,
        ASOCIATE_PHN
    }
}

