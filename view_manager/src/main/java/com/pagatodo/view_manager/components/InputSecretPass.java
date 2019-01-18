package com.pagatodo.view_manager.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
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
import com.google.android.material.textfield.TextInputLayout;
import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;

import java.util.Objects;

import androidx.annotation.Nullable;

public class InputSecretPass extends LinearLayout implements View.OnClickListener, TextWatcher,View.OnFocusChangeListener{

    private TextInputLayout inputLayout;
    private TextInputEditText inputEditText;
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
        inputLayout = view.findViewById(R.id.text_input_layout);
        inputEditText = view.findViewById(R.id.edit_input_layout);
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


        this.addView(view);
    }

    private void initView(){
        hidePass();
        active();
        imageviewEye.setOnClickListener(this);
        inputEditText.addTextChangedListener(this);
        inputEditText.setOnFocusChangeListener(this);

    }

    public void setHint(String hitnText){
        inputLayout.setHint(hitnText);
    }
    public void active(){
        inputLayout.setBackgroundResource(R.drawable.input_text_active);
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
            inputLayout.setBackgroundResource(R.drawable.input_text_active);
            if (!Objects.requireNonNull(inputEditText.getText()).toString().isEmpty()) {
                inputEditText.setSelection(inputEditText.getText().length());
            }
        } else {
            inputLayout.setBackgroundResource(R.drawable.input_text_normal);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        switch (Objects.requireNonNull(inputEditText.getText()).toString().length()){
            case 1:
                astOne.setVisibility(View.VISIBLE);
                astTwo.setVisibility(View.INVISIBLE);
                textTwo.setText("");
                textOne.setText(s.toString());
                break;
            case 2:
                astThree.setVisibility(View.INVISIBLE);
                textThree.setText("");
                astTwo.setVisibility(View.VISIBLE);
                textTwo.setText(s.toString().substring(1));
                break;
            case 3:
                astFour.setVisibility(View.INVISIBLE);
                textFour.setText("");
                astThree.setVisibility(View.VISIBLE);
                textThree.setText(s.toString().substring(2));
                break;
            case 4:
                astFive.setVisibility(View.INVISIBLE);
                astFour.setVisibility(View.VISIBLE);
                textFour.setText(s.toString().substring(3));
                textFive.setText("");
                break;
            case 5:
                astSix.setVisibility(View.INVISIBLE);
                astFive.setVisibility(View.VISIBLE);
                textFive.setText(s.toString().substring(4));
                textSix.setText("");
                break;
            case 6:
                astSix.setVisibility(View.VISIBLE);
                textSix.setText(s.toString().substring(5));
                if (this.listener != null){
                    this.listener.inputListener();
                }
                break;
            default:
                astOne.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}