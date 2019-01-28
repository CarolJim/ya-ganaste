package com.pagatodo.view_manager.components.inputs;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.pagatodo.view_manager.R;

import java.util.Objects;

import androidx.annotation.Nullable;

public class InputSecret extends InputLauncher{

    public InputSecret(Context context) {
        super(context);
        initView(null);
    }

    public InputSecret(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public InputSecret(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    @Override
    public void initView(AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        viewMain = inflater.inflate(R.layout.input_secret,this,false   );
        rootView = viewMain.findViewById(R.id.root_view);
        inputEditText = viewMain.findViewById(R.id.edit_input_layout);

        textviewHint = viewMain.findViewById(R.id.textview_hint);
        textLabel = viewMain.findViewById(R.id.text_label);

        linearText = viewMain.findViewById(R.id.linear_text);
        linearAst = viewMain.findViewById(R.id.linear_ast);
        linearLines = viewMain.findViewById(R.id.layer_lines);

        astOne = viewMain.findViewById(R.id.ast_one);
        astTwo = viewMain.findViewById(R.id.ast_two);
        astThree = viewMain.findViewById(R.id.ast_three);
        astFour = viewMain.findViewById(R.id.ast_four);


        textOne = viewMain.findViewById(R.id.num_text_one);
        textTwo = viewMain.findViewById(R.id.num_text_two);
        textThree = viewMain.findViewById(R.id.num_text_trhee);
        textFour = viewMain.findViewById(R.id.num_text_four);


        viewMain.findViewById(R.id.ast_five).setVisibility(GONE);
        viewMain.findViewById(R.id.ast_six).setVisibility(GONE);
        viewMain.findViewById(R.id.num_text_five).setVisibility(GONE);
        viewMain.findViewById(R.id.num_text_six).setVisibility(GONE);
        viewMain.findViewById(R.id.line_five).setVisibility(GONE);
        viewMain.findViewById(R.id.line_six).setVisibility(GONE);
        viewMain.findViewById(R.id.space_five).setVisibility(GONE);
        viewMain.findViewById(R.id.space_six).setVisibility(GONE);
        viewMain.findViewById(R.id.num_text_five).setVisibility(GONE);
        viewMain.findViewById(R.id.num_text_six).setVisibility(GONE);

        bind();
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.InputSecret,
                    0, 0);
            try {
                String resText = a.getString(R.styleable.InputSecret_labelHintNip);
                setHint(resText);

            } finally {
                a.recycle();
            }
        }
        this.addView(viewMain);
    }

    private void bind(){
        inputEditText.addTextChangedListener(this);
        inputEditText.setOnFocusChangeListener(this);
    }



    /* @Override
    public void active() {
        rootView.setBackgroundResource(R.drawable.input_text_active);
        textviewHint.setVisibility(INVISIBLE);
        linearLines.setVisibility(VISIBLE);
        linearAst.setVisibility(VISIBLE);
        textLabel.setVisibility(VISIBLE);
    }*/

    /*@Override
    public void desactive() {
        rootView.setBackgroundResource(R.drawable.input_text_normal);
        if (Objects.requireNonNull(inputEditText.getText()).toString().isEmpty()){
            textLabel.setVisibility(INVISIBLE);
            textviewHint.setVisibility(VISIBLE);
            linearLines.setVisibility(INVISIBLE);
        } else {
            textLabel.setVisibility(VISIBLE);
            textviewHint.setVisibility(INVISIBLE);
        }
    }*/

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        if (Objects.requireNonNull(inputEditText.getText()).toString().length() < 4){
            if (listener != null){
                listener.inputListenerBegin();
            }
        }
        switch (Objects.requireNonNull(inputEditText.getText()).toString().length()){
            case 1:
                astTwo.setVisibility(View.INVISIBLE);
                astOne.setVisibility(View.VISIBLE);
                textTwo.setText("");
                textOne.setText(s.toString());
                break;
            case 2:
                astThree.setVisibility(View.INVISIBLE);
                astTwo.setVisibility(View.VISIBLE);
                textThree.setText("");
                textTwo.setText(s.toString().substring(1));
                break;
            case 3:
                astFour.setVisibility(View.INVISIBLE);
                astThree.setVisibility(View.VISIBLE);
                textFour.setText("");
                textThree.setText(s.toString().substring(2));
                break;
            case 4:
                astFour.setVisibility(View.VISIBLE);
                if (this.listener != null){
                    this.listener.inputListenerFinish();
                }
                textFour.setText(s.toString().substring(3));
                if (listener != null){
                    listener.inputListenerFinish();
                }
                break;
            default:
                astOne.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public String getTextEdit(){
        String text = textOne.getText().toString() + textTwo.getText().toString() +
                textThree.getText().toString() + textFour.getText().toString();
        return text.trim();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus){
            active();
            if (!Objects.requireNonNull(inputEditText.getText()).toString().isEmpty()) {
                inputEditText.setSelection(inputEditText.getText().length());
            }
        } else {
            desactive();
            if (Objects.requireNonNull(inputEditText.getText()).toString().length() < 4){
                isError();
            }
        }
    }


}
