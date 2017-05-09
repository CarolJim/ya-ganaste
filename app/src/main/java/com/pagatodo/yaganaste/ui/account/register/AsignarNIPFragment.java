package com.pagatodo.yaganaste.ui.account.register;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IAccountCardNIPView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_CONFIRM_PIN;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class AsignarNIPFragment extends GenericFragment implements ValidationForms, IAccountCardNIPView {

    private static int PIN_LENGHT = 4;
    private View rootview;
    @BindView(R.id.asignar_edittext)
    CustomValidationEditText etGen;
    @BindView(R.id.btnNextAsignarPin)
    Button btnNextAsignarPin;
    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;
    @BindView(R.id.progressIndicator)
    ProgressLayout progressLayout;
    private String nip = "";
    private Keyboard keyboard;
    LinearLayout layout_control;
    TextView tv1Num;
    TextView tv2Num;
    TextView tv3Num;
    TextView tv4Num;
    String newText = "";
    String oldText = "";

    public AsignarNIPFragment() {
    }

    public static AsignarNIPFragment newInstance() {
        AsignarNIPFragment fragmentRegister = new AsignarNIPFragment();
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
        rootview = inflater.inflate(R.layout.fragment_asignar_nip, container, false);
        initViews();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnNextAsignarPin.setVisibility(GONE);

        layout_control = (LinearLayout) rootview.findViewById(R.id.asignar_control_layout);

        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);

        final Bitmap smiley2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
       // final Bitmap smiley2 = BitmapFactory.decodeResource(getResources(), R.drawable.pin_bullet_blue);
        tv1Num = (TextView) rootview.findViewById(R.id.asignar_tv1);
        tv2Num = (TextView) rootview.findViewById(R.id.asignar_tv2);
        tv3Num = (TextView) rootview.findViewById(R.id.asignar_tv3);
        tv4Num = (TextView) rootview.findViewById(R.id.asignar_tv4);

        etGen = (CustomValidationEditText) rootview.findViewById(R.id.asignar_edittext);
       // edtPin.addCustomTextWatcher(new TextWatcher() {
        etGen.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                newText = s.toString();
                if (newText.length() > oldText.length()) {
                    final int countString = etGen.getText().toString().length();
                    switch (countString) {
                        case 1:
                            tv1Num.setText("" + s.charAt(s.toString().length() - 1));
                            break;
                        case 2:
                            tv2Num.setText("" + s.charAt(s.toString().length() - 1));
                            break;
                        case 3:
                            tv3Num.setText("" + s.charAt(s.toString().length() - 1));
                            break;
                        case 4:
                            tv4Num.setText("" + s.charAt(s.toString().length() - 1));
                            break;
                    }
                    oldText = s.toString();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            SpannableStringBuilder ssb = new SpannableStringBuilder(" "); // 20
                            ssb.setSpan(new ImageSpan(smiley2), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                            switch (countString) {
                                case 1:
                                    tv1Num.setText(ssb, TextView.BufferType.SPANNABLE);
                                    break;
                                case 2:
                                    tv2Num.setText(ssb, TextView.BufferType.SPANNABLE);
                                    break;
                                case 3:
                                    tv3Num.setText(ssb, TextView.BufferType.SPANNABLE);
                                    break;
                                case 4:
                                    tv4Num.setText(ssb, TextView.BufferType.SPANNABLE);
                                    break;
                            }
                        }
                    }, 500);
                } else {
                    int countString = etGen.getText().toString().length();
                    switch (countString) {
                        case 0:
                            tv1Num.setText("");
                            break;
                        case 1:
                            tv2Num.setText("");
                            break;
                        case 2:
                            tv3Num.setText("");
                            break;
                        case 3:
                            tv4Num.setText("");
                            break;
                    }
                    oldText = s.toString();
                }
            }
        });

        // Make the custom keyboard appear
        etGen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    keyboardView.showCustomKeyboard(v);
                } else {
                    keyboardView.hideCustomKeyboard();
                }
            }
        });

        //edtPin.setOnClickListener(new View.OnClickListener() {
        layout_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etGen.requestFocus();
                keyboardView.showCustomKeyboard(v);
            }
        });

        etGen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                keyboardView.showCustomKeyboard(v);
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });

        setValidationRules();
    }

    /*Implementación de ValidationForms*/

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        getDataForm();

        if (nip.length() < PIN_LENGHT) {
            showValidationError(getString(R.string.asignar_pin));
            return;
        }

        onValidationSuccess();
    }

    @Override
    public void showValidationError(Object error) {
        UI.showToastShort(error.toString(), getActivity());
    }

    @Override
    public void onValidationSuccess() {
        nextScreen(EVENT_GO_CONFIRM_PIN, nip);
    }

    @Override
    public void getDataForm() {
        nip = etGen.getText().toString().trim();
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void showLoader(String message) {
        progressLayout.setVisibility(VISIBLE);
        progressLayout.setTextMessage(message);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(GONE);
    }

    @Override
    public void showError(Object error) {
        UI.showToastShort(error.toString(), getActivity());
    }


    public boolean isCustomKeyboardVisible() {
        return keyboardView.getVisibility() == View.VISIBLE;
    }

    public void hideKeyboard() {
        keyboardView.hideCustomKeyboard();
    }
}