package com.pagatodo.yaganaste.ui_wallet.holders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.customviews.CustomRadioButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class QuestionViewHolder extends GenericHolder {

    private StyleTextView textQuestion;
    private RadioGroup radioGroup;
    private CustomRadioButton responseYes;
    private CustomRadioButton responseNo;
    private RadioGroup.OnCheckedChangeListener listener;

    public QuestionViewHolder(View itemView, RadioGroup.OnCheckedChangeListener listener) {
        super(itemView);
        this.listener = listener;
        init();
    }

    @Override
    public void init() {
        this.textQuestion = itemView.findViewById(R.id.textPublicServant);
        this.radioGroup = itemView.findViewById(R.id.radioPublicServant);
        this.responseYes = itemView.findViewById(R.id.radioBtnPublicServantYes);
        this.responseNo = itemView.findViewById(R.id.radioBtnPublicServantNo);
    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {
        Question question = (Question) item;
        this.textQuestion.setText(question.getTextQuestion());
        if (question.isDefaultResponse()){
            this.responseYes.setChecked(true);
            //this.responseNo.setChecked(false);
        } else {
            this.responseNo.setChecked(true);
        }
        if (this.listener != null){
            this.radioGroup.setOnCheckedChangeListener(this.listener);
        }
    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(itemView);
    }

    @Override
    public View getView() {
        return null;
    }

    public static class Question {
        private int textQuestion;
        private boolean defaultResponse; //Si es true la respuesta es SI si False la respuesta es No

        public Question(int textQuestion, boolean defaultResponse) {
            this.textQuestion = textQuestion;
            this.defaultResponse = defaultResponse;
        }

        public int getTextQuestion() {
            return textQuestion;
        }

        public void setTextQuestion(int textQuestion) {
            this.textQuestion = textQuestion;
        }

        public boolean isDefaultResponse() {
            return defaultResponse;
        }

        public void setDefaultResponse(boolean defaultResponse) {
            this.defaultResponse = defaultResponse;
        }
    }
}
