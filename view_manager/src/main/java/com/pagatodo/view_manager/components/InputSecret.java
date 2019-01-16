package com.pagatodo.view_manager.components;

import android.content.Context;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.view_manager.R;

import java.util.Objects;

public class InputSecret extends FrameLayout implements TextWatcher, View.OnFocusChangeListener{

    private TextInputLayout inputLayout;
    private TextInputEditText inputEditText;
    private ImageView astOne;
    private ImageView astTwo;
    private ImageView astThree;
    private ImageView astFour;
    private InputSecretListener listener;

    public InputSecret(Context context) {
        super(context);
        init(null);
    }

    public InputSecret(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public InputSecret(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        //this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.input_secret,this,false);
        inputLayout = view.findViewById(R.id.text_input_layout);
        inputEditText = view.findViewById(R.id.edit_input_layout);
        astOne = view.findViewById(R.id.ast_one);
        astTwo = view.findViewById(R.id.ast_two);
        astThree = view.findViewById(R.id.ast_three);
        astFour = view.findViewById(R.id.ast_four);
        initView();
        this.addView(view);
    }

    private void initView(){
        inputEditText.addTextChangedListener(this);
        inputEditText.setOnFocusChangeListener(this);
    }

    public void resquestFoucus(){
        inputEditText.requestFocus();
    }

    public View getView(){
        return inputEditText;
    }

    public void setInputSecretListener(InputSecretListener listener){
        this.listener = listener;
    }

    public void setActionListener(EditText.OnEditorActionListener listener){
        inputEditText.setOnEditorActionListener(listener);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        switch (Objects.requireNonNull(inputEditText.getText()).toString().length()){
            case 1:
                astOne.setVisibility(View.VISIBLE);
                astTwo.setVisibility(View.INVISIBLE);
                break;
            case 2:
                astThree.setVisibility(View.INVISIBLE);
                astTwo.setVisibility(View.VISIBLE);
                break;
            case 3:
                astFour.setVisibility(View.INVISIBLE);
                astThree.setVisibility(View.VISIBLE);
                break;
            case 4:
                astFour.setVisibility(View.VISIBLE);
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
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            inputLayout.setBackgroundResource(R.drawable.input_text_active);
            if (!Objects.requireNonNull(inputEditText.getText()).toString().isEmpty()) {
                inputEditText.setSelection(inputEditText.getText().length());
            }
        } else {
            inputLayout.setBackgroundResource(R.drawable.input_text_normal);
        }
    }

    public interface InputSecretListener{
        void inputListener();
    }
}
