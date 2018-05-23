package com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.builders.Component;

public class InputDataNode implements Component {

    private TextInputLayout inputLayout;
    private EditText editText;
    private LinearLayout container;
    private Context context;
    private int idR;

    public InputDataNode(Context context, LinearLayout container, int id, int idR) {
        this.context = context;
        this.container = container;
        this.idR = idR;
        setContent(id);
    }

    @Override
    public void add(Component component) {

    }

    @SuppressLint("NewApi")
    @Override
    public void setContent(int id) {
        this.container.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        this.container.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.inputdata, container, false);
        this.inputLayout = itemView.findViewById(R.id.text_input);
        this.editText = itemView.findViewById(R.id.edit_text);
        this.inputLayout.setHint(context.getString(id));
        if (idR != 0) {
            this.editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, this.idR, 0);
        }
        this.container.addView(itemView);

    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(this.container);
    }

    public void remove(ViewGroup layout){
        layout.removeAllViews();
    }

    private int dp(float px){
        float scale = this.container.getResources().getDisplayMetrics().density;
        return (int) (scale * px + 0.5f);
    }

    public void setInputLayout(TextInputLayout inputLayout) {
        this.inputLayout = inputLayout;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public TextInputLayout getInputLayout() {
        return inputLayout;
    }

    public EditText getEditText() {
        return editText;
    }

    public LinearLayout getContainer() {
        return container;
    }
}
