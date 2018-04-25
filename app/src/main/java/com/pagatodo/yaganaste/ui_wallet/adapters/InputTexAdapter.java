package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui_wallet.pojos.InputText;
import com.pagatodo.yaganaste.utils.ValidateForm;


import java.util.ArrayList;

/**
 * Created by icruz on 14/02/2018.
 */

public class InputTexAdapter extends ArrayAdapter<String> {

    String txt;
    ChangeEditListener changeEditListener;
    private ArrayList<InputText> listItems;
    private View convertView;
    //private Listener listener;

    private static class ViewHolderInputText {
        EditText editText;
        TextInputLayout inputLayout;
    }

    public InputTexAdapter(Context context, ArrayList<InputText> listItems ) {
        super(context, R.layout.adapter_input_text);
        this.listItems = listItems;
    }
    /*public InputTexAdapter(Context context, ArrayList<InputText> listItems, Listener listener ) {
        super(context, R.layout.adapter_input_text);
        this.listItems = listItems;
        this.listener = listener;
    }*/


    public InputTexAdapter(Context context, ArrayList<InputText> listItems, ChangeEditListener changeEditListener) {
        super(context, R.layout.adapter_input_text);
        this.listItems = listItems;
        this.changeEditListener = changeEditListener;
    }


    @SuppressLint("CardStarbucksViewHolder")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //final InputTexAdapter.ViewHolderInputText viewHolder;
        final ViewHolderInputText viewHolder = new InputTexAdapter.ViewHolderInputText();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.adapter_input_text, parent, false);

        assert convertView != null;
        viewHolder.editText = convertView.findViewById(R.id.editTextpass);
        viewHolder.inputLayout = convertView.findViewById(R.id.passwordnew);
        viewHolder.inputLayout.setHint(getContext().getResources().getString(listItems.get(position).getHintText()));

      if (listItems.get(position).getTipo().equals("PAS")) {
            viewHolder.inputLayout.setPasswordVisibilityToggleEnabled(true);
            viewHolder.editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            viewHolder.editText.setKeyListener(DigitsKeyListener.getInstance(getContext().getString(R.string.input_int_unsigned)));
        }
        if (listItems.get(position).getTipo().equals("EMA")) {
            viewHolder.inputLayout.setPasswordVisibilityToggleEnabled(false);
            viewHolder.editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }

            /*

            viewHolder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasfocus) {
                    if (!hasfocus) {
                        changeEditListener.ChangeEdit(listItems.get(position).getContentText());

                    }
                }
            });

*/



        viewHolder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                listItems.get(position).setContentText(editable.toString());
            }
        });


        /*viewHolder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.setOnFocus(viewHolder.editText);

                if (hasFocus) {
                    // code to execute when EditText loses focus
                    viewHolder.inputLayout.setBackgroundResource(R.drawable.inputtext_active);

                }else {
                    viewHolder.inputLayout.setBackgroundResource(R.drawable.inputtext_error);
                }
            }
        });*/

        return convertView;
    }

    public String getText(int position){
        return listItems.get(position).getContentText();
    }

    public void setError(int position){

    }

    @Override
    public int getCount() {
        super.getCount();
        return listItems.size();
    }


    public String getInpuText(int position) {
        return listItems.get(position).getContentText();
    }

    public interface ChangeEditListener{

        public void ChangeEdit(String text);

}


}
