package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class TienesTarjetaFragment extends GenericFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private View rootview;
    @BindView(R.id.radioHasCard)
    RadioGroup radioHasCard;
    @BindView(R.id.radioBtnYes)
    AppCompatRadioButton radioBtnYes;
    @BindView(R.id.radioBtnNo)
    AppCompatRadioButton radioBtnNo;
    @BindView(R.id.btnBackTienesTarjeta)
    Button btnBackTienesTarjeta;
    @BindView(R.id.btnNextTienesTarjeta)
    Button btnNextTienesTarjeta;
    @BindView(R.id.layoutCard)
    RelativeLayout layoutCard;
    @BindView(R.id.editNumber)
    EditText editNumber;
    InputMethodManager imm;

    int keyDel;
    String a;

    public TienesTarjetaFragment() {
    }

    public static TienesTarjetaFragment newInstance() {
        TienesTarjetaFragment fragmentRegister = new TienesTarjetaFragment();
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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

        rootview = inflater.inflate(R.layout.fragment_tienes_tarjeta, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        final Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Light.ttf");
        radioHasCard.setOnCheckedChangeListener(this);
        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        editNumber.setTypeface(typeface);
        ViewTreeObserver viewTreeObserver = layoutCard.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int layoutHeight = layoutCard.getHeight();
                float sp = (float) ((layoutHeight * 0.09) / getResources().getDisplayMetrics().scaledDensity);
                editNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
            }
        });
        editNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean flag = true;
                String eachBlock[] = editNumber.getText().toString().split("-");
                for (int i = 0; i < eachBlock.length; i++) {
                    if (eachBlock[i].length() > 4) {
                        flag = false;
                    }
                }
                if (flag) {
                    editNumber.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_DEL)
                                keyDel = 1;
                            return false;
                        }
                    });

                    if (keyDel == 0) {

                        if (((editNumber.getText().length() + 1) % 5) == 0) {

                            if (editNumber.getText().toString().split("-").length <= 3) {
                                editNumber.setText(editNumber.getText() + "-");
                                editNumber.setSelection(editNumber.getText().length());
                            }
                        }
                        a = editNumber.getText().toString();
                    } else {
                        a = editNumber.getText().toString();
                        keyDel = 0;
                    }

                } else {
                    editNumber.setText(a);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radioBtnYes:
                editNumber.setFocusableInTouchMode(true);
                editNumber.requestFocus();
                editNumber.setEnabled(true);
                editNumber.setCursorVisible(true);
                imm.showSoftInput(editNumber, InputMethodManager.SHOW_IMPLICIT);
                break;
            case R.id.radioBtnNo:
                editNumber.setText("");
                editNumber.setFocusableInTouchMode(false);
                editNumber.clearFocus();
                editNumber.setEnabled(false);
                imm.hideSoftInputFromWindow(rootview.getWindowToken(), 0);
                break;
            default:
                break;
        }
    }
}

