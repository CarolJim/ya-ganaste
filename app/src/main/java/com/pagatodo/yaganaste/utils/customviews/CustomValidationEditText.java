package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.ValidateForm;

/**
 * Created by Jordan on 27/03/2017.
 */

public class CustomValidationEditText extends LinearLayout {
    //@BindView(R.id.editTextCustom)
    EditText editText;
    //@BindView(R.id.imageViewValidation)
    ImageView imageView;
    Boolean isValid = false;

    String hint = "";
    String type = "";
    Integer maxLength = 0;
    boolean imageViewIsGone = false;
    int maxLines = 0;
    boolean isSingleLine = false;
    boolean isTextEnabled = true;

    public CustomValidationEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomValidationEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomValidationEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.edittext_layout, this);
        //ButterKnife.bind(context, this);
        editText = (EditText) findViewById(R.id.editTextCustom);
        imageView = (ImageView) findViewById(R.id.imageViewValidation);
        //imageView.setBackgroundResource(R.drawable.validation_fail);

        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomValidationEditText,
                    0, 0);

            try {
                hint = typedArray.getString(R.styleable.CustomValidationEditText_hintText);
                type = typedArray.getString(R.styleable.CustomValidationEditText_formato);
                maxLength = typedArray.getInteger(R.styleable.CustomValidationEditText_maxLength, 0);
                imageViewIsGone = typedArray.getBoolean(R.styleable.CustomValidationEditText_imageIsGone, false);
                maxLines = typedArray.getInt(R.styleable.CustomValidationEditText_maxLength, 0);
                isSingleLine = typedArray.getBoolean(R.styleable.CustomValidationEditText_isSingleLine, false);
                isTextEnabled = typedArray.getBoolean(R.styleable.CustomValidationEditText_isTextEnabled, true);
            } catch (Exception e) {
                Log.e(context.getPackageName(), "Error loading attributes:" + e.getMessage());
            } finally {
                typedArray.recycle();
            }

            if (type != null && !type.isEmpty()) {
                setCustomFormatAttr(type);
            }

            if (hint != null && !hint.isEmpty()) {
                setHintText(hint);
            }

            if (maxLength > 1) {
                setMaxLength(maxLength);
            }

            if (maxLines > 0) {
                setMaxLines(maxLines);
            }

            setEnabled(isTextEnabled);

            imageViewIsGone(imageViewIsGone);

            setSingleLine(isSingleLine);

        }
    }

    private void setHintText(String txt) {
        editText.setHint(txt);
    }

    private void setCustomFormatAttr(String txt) {
        if (txt != null && !txt.isEmpty()) {
            switch (txt) {
                case "0"://email
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    setValidationListener(txt);
                    break;
                case "1"://password
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    setValidationListener(txt);
                    break;
                case "2"://phone
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
                    setValidationListener(txt);
                    break;
                case "3"://zipcode
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    setValidationListener(txt);
                    break;
                case "4"://text
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    setValidationListener(txt);
                    break;
                case "5"://number
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case "6"://cellPhone
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
                    setValidationListener(txt);
                    break;
            }


        }
    }

    private void setValidationListener(final String type) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txt = editText.getEditableText().toString().trim();
                boolean result;
                if (txt.isEmpty()) {
                    imageViewIsGone(true);
                } else {
                    switch (type) {
                        case "0"://email
                            result = ValidateForm.isValidEmailAddress(txt);
                            break;
                        case "1"://password
                            result = ValidateForm.isValidPassword(txt);
                            break;
                        case "2"://phone
                            result = ValidateForm.isValidPhone(txt);
                            break;
                        case "3"://zipcode
                            result = ValidateForm.isValidZipCode(txt);
                            break;
                        case "4"://text
                            result = !txt.isEmpty();
                            break;
                        case "5"://cellPhone
                            result = ValidateForm.isValidCellPhone(txt.trim());
                            break;
                        default:
                            result = false;
                            break;
                    }

                    if (result) {
                        setValidView();
                    } else {
                        setNonValidView();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setValidView() {
        imageView.setVisibility(VISIBLE);
        imageView.setBackgroundResource(R.drawable.validation_ok_);
        isValid = true;
    }

    private void setNonValidView() {
        imageView.setVisibility(VISIBLE);
        imageView.setBackgroundResource(R.drawable.validation_fail);
        isValid = false;
    }

    public String getText() {
        return editText.getText().toString().trim();
    }

    public Boolean isValidText() {
        return isValid;
    }

    public void setIsValid() {
        setValidView();
    }

    public void setIsInvalid() {
        setNonValidView();
    }

    public void setDrawableImage(int image) {
        imageView.setBackgroundResource(image);
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public void setMaxLength(Integer i) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(i);
        editText.setFilters(fArray);
    }

    public void imageViewIsGone(boolean isGone) {
        if (isGone) {
            imageView.setVisibility(GONE);
        } else {
            imageView.setVisibility(VISIBLE);
        }
    }

    public void setMaxLines(int n) {
        editText.setLines(n);
    }

    public void setSingleLine(boolean singleLine) {
        //editText.setSingleLine(singleLine);
        if (singleLine) {
            editText.setLines(1);
        }
    }

    public void setTextEnabled(boolean isEnabled) {
        editText.setEnabled(isEnabled);
    }

    public void setFullOnClickListener(OnClickListener onClickListener) {
        editText.setFocusableInTouchMode(false);
        editText.setOnClickListener(onClickListener);
        this.setOnClickListener(onClickListener);
    }

    public void addCustomTextWatcher(TextWatcher watcher) {
        editText.addTextChangedListener(watcher);
    }

    public void removeCustomTextWatcher(TextWatcher watcher) {
        editText.removeTextChangedListener(watcher);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener){
        editText.setOnFocusChangeListener(onFocusChangeListener);
    }

    public EditText getEditText(){
        return editText;
    }
}
