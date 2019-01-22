package com.pagatodo.view_manager.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.pagatodo.view_manager.R;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class InputSecretPass extends LinearLayout implements View.OnClickListener, TextWatcher,View.OnFocusChangeListener{

    private TextInputEditText inputEditText;
    private ConstraintLayout rootView;
    private ImageView astOne;
    private ImageView astTwo;
    private ImageView astThree;
    private ImageView astFour;
    private ImageView astFive;
    private ImageView astSix;
    private TextView textOne;
    private TextView textTwo;
    private TextView textThree;
    private TextView textFour;
    private TextView textFive;
    private TextView textSix;
    private ImageView imageviewEye;
    private LinearLayout linearText;
    private LinearLayout linearAst;
    private LinearLayout linearLines;
    private TextView textviewHint;
    private TextView textLabel;
    private int resD;
    private InputSecret.InputSecretListener listener;

    public InputSecretPass(Context context) {
        super(context);
        init(null);
    }

    public InputSecretPass(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public InputSecretPass(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.input_secret_pass,this,false   );
        //inputLayout = view.findViewById(R.id.text_input_layout);
        inputEditText = view.findViewById(R.id.edit_input_layout);
        rootView = view.findViewById(R.id.root_view);
        textviewHint = view.findViewById(R.id.textview_hint);
        textLabel = view.findViewById(R.id.text_label);

        astOne = view.findViewById(R.id.ast_one);
        astTwo = view.findViewById(R.id.ast_two);
        astThree = view.findViewById(R.id.ast_three);
        astFour = view.findViewById(R.id.ast_four);
        astFive = view.findViewById(R.id.ast_five);
        astSix = view.findViewById(R.id.ast_six);

        textOne = view.findViewById(R.id.num_text_one);
        textTwo = view.findViewById(R.id.num_text_two);
        textThree = view.findViewById(R.id.num_text_trhee);
        textFour = view.findViewById(R.id.num_text_four);
        textFive = view.findViewById(R.id.num_text_five);
        textSix = view.findViewById(R.id.num_text_six);

        imageviewEye = view.findViewById(R.id.imageview_eye);
        linearText = view.findViewById(R.id.linear_text);
        linearAst = view.findViewById(R.id.linear_ast);
        linearLines = view.findViewById(R.id.layer_lines);

        initView();
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.InputSecretPass,
                    0, 0);
            try {
                String resText = a.getString(R.styleable.InputSecretPass_labelHint);
                setHint(resText);
            } finally {
                a.recycle();
            }
        }
        rootView.setOnClickListener(this);
        this.addView(view);
    }

    private void initView(){
        hidePass();
        //active();
        imageviewEye.setOnClickListener(this);
        inputEditText.addTextChangedListener(this);
        inputEditText.setOnFocusChangeListener(this);

    }

    public TextInputEditText getInputEditText(){
        return this.inputEditText;
    }
    public String getTextEdit(){
        String text = textOne.getText().toString() + textTwo.getText().toString() +
                textThree.getText().toString() + textFour.getText().toString() + textFive.getText().toString() +
                textSix.getText().toString();
        return text.trim();
    }

    public void setRequestFocus(){
        inputEditText.requestFocus();

    }

    public void setHint(String hitnText){
        textLabel.setText(hitnText);
        textviewHint.setText(hitnText);
    }

    public void active(){
        rootView.setBackgroundResource(R.drawable.input_text_active);
        textviewHint.setVisibility(INVISIBLE);
        linearLines.setVisibility(VISIBLE);
        linearAst.setVisibility(VISIBLE);
        textLabel.setVisibility(VISIBLE);
    }

    public void desactive(){

        rootView.setBackgroundResource(R.drawable.input_text_normal);
        if (Objects.requireNonNull(inputEditText.getText()).toString().isEmpty()){
            textLabel.setVisibility(INVISIBLE);
            textviewHint.setVisibility(VISIBLE);
            linearLines.setVisibility(INVISIBLE);
        } else {
            textLabel.setVisibility(VISIBLE);
            textviewHint.setVisibility(INVISIBLE);
        }

    }

    public void isError(){
        rootView.setBackgroundResource(R.drawable.input_text_error);
    }

    public void hidePass(){
        resD = R.drawable.ic_eye_close;
        imageviewEye.setBackgroundResource(resD);
        linearText.setVisibility(INVISIBLE);
        setShowAst();

    }

    public void showPass(){
        resD = R.drawable.ic_eye_open;
        imageviewEye.setBackgroundResource(resD);
        linearText.setVisibility(VISIBLE);
        astOne.setVisibility(View.INVISIBLE);
        astTwo.setVisibility(View.INVISIBLE);
        astThree.setVisibility(View.INVISIBLE);
        astFour.setVisibility(View.INVISIBLE);
        astFive.setVisibility(View.INVISIBLE);
        astSix.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.imageview_eye) {
            if (resD == R.drawable.ic_eye_close){
                showPass();
            } else if (resD == R.drawable.ic_eye_open){
                hidePass();
            }
        } else if (i == R.id.root_view){
            active();
        }

    }

    public void setActionListener(EditText.OnEditorActionListener listener){
        inputEditText.setOnEditorActionListener(listener);
    }

    private void setShowAst(){

        switch (Objects.requireNonNull(inputEditText.getText()).toString().length()){
            case 1:
                astOne.setVisibility(View.VISIBLE);
                astTwo.setVisibility(View.INVISIBLE);
                astThree.setVisibility(View.INVISIBLE);
                astFour.setVisibility(View.INVISIBLE);
                astFive.setVisibility(View.INVISIBLE);
                astSix.setVisibility(View.INVISIBLE);
                break;
            case 2:
                astOne.setVisibility(View.VISIBLE);
                astTwo.setVisibility(View.VISIBLE);
                astThree.setVisibility(View.INVISIBLE);
                astFour.setVisibility(View.INVISIBLE);
                astFive.setVisibility(View.INVISIBLE);
                astSix.setVisibility(View.INVISIBLE);
                break;
            case 3:
                astOne.setVisibility(View.VISIBLE);
                astTwo.setVisibility(View.VISIBLE);
                astThree.setVisibility(View.VISIBLE);
                astFour.setVisibility(View.INVISIBLE);
                astFive.setVisibility(View.INVISIBLE);
                astSix.setVisibility(View.INVISIBLE);
                break;
            case 4:
                astOne.setVisibility(View.VISIBLE);
                astTwo.setVisibility(View.VISIBLE);
                astThree.setVisibility(View.VISIBLE);
                astFour.setVisibility(View.VISIBLE);
                astFive.setVisibility(View.INVISIBLE);
                astSix.setVisibility(View.INVISIBLE);
                break;
            case 5:
                astOne.setVisibility(View.VISIBLE);
                astTwo.setVisibility(View.VISIBLE);
                astThree.setVisibility(View.VISIBLE);
                astFour.setVisibility(View.VISIBLE);
                astFive.setVisibility(View.VISIBLE);
                astSix.setVisibility(View.INVISIBLE);
                break;
            case 6:
                astOne.setVisibility(View.VISIBLE);
                astTwo.setVisibility(View.VISIBLE);
                astThree.setVisibility(View.VISIBLE);
                astFour.setVisibility(View.VISIBLE);
                astFive.setVisibility(View.VISIBLE);
                astSix.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            active();
            if (!Objects.requireNonNull(inputEditText.getText()).toString().isEmpty()) {
                inputEditText.setSelection(inputEditText.getText().length());
            }
        } else {
            desactive();
            if (Objects.requireNonNull(inputEditText.getText()).toString().length() < 6){
                isError();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        switch (Objects.requireNonNull(inputEditText.getText()).toString().length()){
            case 1:
                if (resD == R.drawable.ic_eye_close){
                    astOne.setVisibility(View.VISIBLE);
                } else {
                    astOne.setVisibility(View.INVISIBLE);
                }
                astTwo.setVisibility(View.INVISIBLE);
                textTwo.setText("");
                textOne.setText(s.toString());
                break;
            case 2:
                astThree.setVisibility(View.INVISIBLE);
                textThree.setText("");
                if (resD == R.drawable.ic_eye_close){
                    astTwo.setVisibility(View.VISIBLE);
                } else {
                    astTwo.setVisibility(View.INVISIBLE);
                }

                textTwo.setText(s.toString().substring(1));
                break;
            case 3:
                astFour.setVisibility(View.INVISIBLE);
                textFour.setText("");
                if (resD == R.drawable.ic_eye_close){
                    astThree.setVisibility(View.VISIBLE);
                } else {
                    astThree.setVisibility(View.INVISIBLE);
                }
                textThree.setText(s.toString().substring(2));
                break;
            case 4:
                astFive.setVisibility(View.INVISIBLE);
                if (resD == R.drawable.ic_eye_close){
                    astFour.setVisibility(View.VISIBLE);
                } else {
                    astFour.setVisibility(View.INVISIBLE);
                }
                textFour.setText(s.toString().substring(3));
                textFive.setText("");
                break;
            case 5:
                astSix.setVisibility(View.INVISIBLE);
                if (resD == R.drawable.ic_eye_close){
                    astFive.setVisibility(View.VISIBLE);
                } else {
                    astFive.setVisibility(View.INVISIBLE);
                }
                textFive.setText(s.toString().substring(4));
                textSix.setText("");
                break;
            case 6:
                rootView.setBackgroundResource(R.drawable.input_text_active);
                if (resD == R.drawable.ic_eye_close){
                    astSix.setVisibility(View.VISIBLE);
                } else {
                    astSix.setVisibility(View.INVISIBLE);
                }
                textSix.setText(s.toString().substring(5));
                if (this.listener != null){
                    this.listener.inputListener();
                }
                break;
            default:
                astOne.setVisibility(View.INVISIBLE);
                textOne.setText("");
                active();
                break;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setInputSecretListener(InputSecret.InputSecretListener listener){
        this.listener = listener;
    }
}
