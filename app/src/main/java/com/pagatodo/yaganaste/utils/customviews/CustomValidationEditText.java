package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.FontCache;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.ValidateForm;

/**
 * Created by Jordan on 27/03/2017.
 */

public class CustomValidationEditText extends LinearLayout implements View.OnTouchListener, View.OnLongClickListener {
    //@BindView(R.id.editTextCustom)
    EditText editText;
    //@BindView(R.id.imageViewValidation)
    AppCompatImageView imageView;
    Boolean isValid = false;

    String hint = "";
    String type = "";
    Integer maxLength = 0;
    boolean imageViewIsGone = false;
    int maxLines = 0;
    boolean isSingleLine = false;
    boolean isTextEnabled = true;
    private int pinnedIcon;
    private String blockCharacterSetEmail = "|°¬!\"\\#$%&/()=?¡¿'¨´+*{}[],;:";

    private OnClickListener externalListener;

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
        imageView = (AppCompatImageView) findViewById(R.id.imageViewValidation);

        //imageView.setBackgroundResource(R.drawable.validation_fail);
        int inputType = EditorInfo.TYPE_NULL;
        float textSize = EditorInfo.TYPE_NULL;

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

                editText.setHintTextColor(typedArray.getColor(R.styleable.CustomValidationEditText_hintColor, ContextCompat.getColor(App.getInstance().getApplicationContext(), R.color.inputTextColorHint)));

                pinnedIcon = typedArray.getInt(R.styleable.CustomValidationEditText_defaultIcon, -1);
                inputType = typedArray.getInt(R.styleable.CustomValidationEditText_android_inputType, EditorInfo.TYPE_NULL);
                editText.setInputType(inputType);
                inputType = typedArray.getInt(R.styleable.CustomValidationEditText_android_inputType, EditorInfo.TYPE_NULL);
                textSize = typedArray.getDimension(R.styleable.CustomValidationEditText_android_textSize, EditorInfo.TYPE_NULL);

            } catch (Exception e) {
                Log.e(context.getPackageName(), "Error loading attributes:" + e.getMessage());
            } finally {
                typedArray.recycle();
            }

            if (inputType != EditorInfo.TYPE_NULL) {
                editText.setInputType(inputType);
            } else {
                //  editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, App.getContext().getResources().getDimension(R.dimen.text_custom_validation_editext_size));
            }

            if (textSize != EditorInfo.TYPE_NULL) {
                editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
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
            setIconPinned(pinnedIcon);

        }

        Typeface customFont = FontCache.getTypeface("fonts/roboto/Roboto-Light.ttf", context);
        editText.setTypeface(customFont);
        editText.setOnLongClickListener(this);
        editText.setFilters(new InputFilter[] { filter });
    }

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSetEmail.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };


    public void setHintText(String txt) {
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
                    editText.setKeyListener(DigitsKeyListener.getInstance(getContext().getString(R.string.input_int_unsigned)));
                    setValidationListener(txt);
                    break;
                case "3"://zipcode
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editText.setKeyListener(DigitsKeyListener.getInstance(getContext().getString(R.string.input_int_unsigned)));
                    setValidationListener(txt);
                    break;
                case "4"://text
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    setValidationListener(txt);
                    break;
                case "5"://number
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case "6"://cellPhone
                    InputFilter[] fArray = new InputFilter[1];
                    fArray[0] = new InputFilter.LengthFilter(12);
                    editText.setFilters(fArray);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
                    editText.addTextChangedListener(new PhoneTextWatcher(editText));
                    editText.setKeyListener(DigitsKeyListener.getInstance(getContext().getString(R.string.input_int_unsigned)));
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
                    isValid = false;
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
                        case "5"://number
                            result = !txt.isEmpty();
                            break;
                        case "6"://cellPhone
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
        imageView.setImageResource(R.drawable.done_campo_canvas);
        isValid = true;
    }

    private void setNonValidView() {
        imageView.setVisibility(VISIBLE);
        imageView.setImageResource(R.drawable.warning_canvas);
        isValid = false;
    }

    public String getText() {
        if ("6".equals(type)) {
            return editText.getText().toString().trim().replace(" ", "");
        }
        return editText.getText().toString().trim();
    }

    public void setText(String text) {
        editText.setText(text);
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

    public void setDrawableImage(@DrawableRes int image) {
        imageView.setImageResource(image);
        this.pinnedIcon = image;
    }

    public void setMaxLength(Integer i) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(i);
        editText.setFilters(fArray);
    }

    public void imageViewIsGone(boolean isGone) {
        if (isGone) {
            if (pinnedIcon != -1) {
                imageView.setVisibility(VISIBLE);
                setIconPinned(pinnedIcon);
                return;
            }
            imageView.setVisibility(GONE);
        } else {
            imageView.setVisibility(VISIBLE);
        }
    }

    public void setMaxLines(int n) {
        if (n > 1) {
            editText.setLines(n);
        } else {
            editText.setSingleLine(true);
        }
    }

    public void setSingleLine(boolean singleLine) {
        //editText.setSingleLine(singleLine);
        if (singleLine) {
            editText.setLines(1);
        }
    }

    public void setTextEnabled(boolean isEnabled) {
        editText.setFocusable(isEnabled);
        editText.setFocusableInTouchMode(isEnabled);
    }


    public void setFullOnClickListener(OnClickListener onClickListener) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setEnabled(true);
        externalListener = onClickListener;
        //editText.setOnClickListener(onClickListener);
        editText.setOnTouchListener(this);
        imageView.setOnClickListener(onClickListener);
        //this.setOnClickListener(onClickListener);
    }

    public void addCustomTextWatcher(TextWatcher watcher) {
        editText.addTextChangedListener(watcher);
    }

    public void removeCustomTextWatcher(TextWatcher watcher) {
        editText.removeTextChangedListener(watcher);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        editText.setOnFocusChangeListener(onFocusChangeListener);
    }

    public EditText getEditText() {
        return editText;
    }

    public boolean imageViewIsVisible() {

        return imageView.getVisibility() == VISIBLE ? true : false;
    }

    private void setIconPinned(int pinnedIcon) {
        if (imageView != null && pinnedIcon != -1) {
            switch (pinnedIcon) {
                case R.drawable.calendar:
                    imageView.setImageResource(R.drawable.calendar);
                    break;
                case 0:
                    break;
                default:
                    imageView.setImageResource(R.drawable.mail_canvas);
                    break;
            }
        }
    }

    public void requestEditFocus() {
        editText.requestFocus();
    }

    public void setEnabled(boolean isEnabled){
        editText.setEnabled(isEnabled);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP && externalListener != null) {
            externalListener.onClick(v);
        }
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        return true;
    }


}
