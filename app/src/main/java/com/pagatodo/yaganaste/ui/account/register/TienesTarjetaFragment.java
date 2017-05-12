package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IAccountCardView;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import butterknife.ButterKnife;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASSIGN_PIN;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.utils.Recursos.DEFAULT_CARD;
import static com.pagatodo.yaganaste.utils.Utils.getCardNumberRamdon;
import butterknife.BindView;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class TienesTarjetaFragment extends GenericFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener,IAccountCardView {

    private static int LEGTH_CARD_NUMBER_FORMAT = 19;
    private View rootview;
    @BindView(R.id.radioHasCard)
    RadioGroup radioHasCard;
    @BindView(R.id.radioBtnYes)
    RadioButton radioBtnYes;
    @BindView(R.id.radioBtnNo)
    RadioButton radioBtnNo;
    @BindView(R.id.btnNextTienesTarjeta)
    Button btnNextTienesTarjeta;
    @BindView(R.id.txtMessageCard)
    StyleTextView txtMessageCard;
    @BindView(R.id.layoutCard)
    RelativeLayout layoutCard;
    @BindView(R.id.editNumber)
    EditText editNumber;
    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;
    private AccountPresenterNew accountPresenter;

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
        accountPresenter = ((AccountActivity)getActivity()).getPresenter();
        accountPresenter.setIView(this);
        //accountPresenter = new AccountPresenterNew(getActivity(),this);
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        keyboardView.setKeyBoard(getActivity(),R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);
        btnNextTienesTarjeta.setOnClickListener(this);
        final Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Light.ttf");
        radioHasCard.setOnCheckedChangeListener(this);
        radioBtnNo.setChecked(true);//Selección por Default

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
                String eachBlock[] = editNumber.getText().toString().split(" ");
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

                            if (editNumber.getText().toString().split(" ").length <= 3) {
                                editNumber.setText(editNumber.getText() + " ");
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

                if(editNumber.getText().length() == LEGTH_CARD_NUMBER_FORMAT){
                    hideKeyboard();
                }

            }
        });

        editNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    keyboardView.showCustomKeyboard(v);
                }else{
                    keyboardView.hideCustomKeyboard();}
            }
        });

        editNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                keyboardView.showCustomKeyboard(v);
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnNextTienesTarjeta:
                selectNextAction();
                break;

            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radioBtnYes:
                editNumber.setFocusableInTouchMode(true);
                editNumber.setEnabled(true);
                editNumber.setCursorVisible(true);
                editNumber.requestFocus();
                keyboardView.showCustomKeyboard(editNumber);
                txtMessageCard.setText(getString(R.string.si_tiene_tarjeta));
                resetCardNumberDefault();
                break;
            case R.id.radioBtnNo:
                txtMessageCard.setText(getString(R.string.no_tiene_tarjeta));
                generateCardNumberRamdon();
                keyboardView.hideCustomKeyboard();
                break;
            default:
                break;
        }
    }

    private void selectNextAction(){
        if(radioBtnNo.isChecked()){ // Selecciona que no tiene tarjeta
            /*TODO Asignar cuenta*/
            assingAccountAvaliable();
        }else{
            String numberCard = editNumber.getText() != null  ? editNumber.getText().toString().trim() : "";
            if(radioBtnYes.isChecked() && numberCard.length() == LEGTH_CARD_NUMBER_FORMAT){ // Validamos que ingrese la tarjeta
                accountPresenter.checkCardAssigment(numberCard);
            }else{
                UI.showToastShort(getString(R.string.tienes_tarjeta_numero),getActivity());
            }
        }
    }

    private void assingAccountAvaliable(){
        accountPresenter.assignAccount();
    }

    @Override
    public void accountAssigned(String message) {
        showLoader(message);
        txtMessageCard.setText(getString(R.string.si_tiene_tarjeta));
        new Handler().postDelayed(new Runnable() {
            public void run() {
                hideLoader();
                nextScreen(EVENT_GO_ASSIGN_PIN,null);// Mostramos la pantalla para obtener tarjeta.
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    @Override
    public void cardIsValidate(String message) {
        /*TODO verificar una ves validada ¿que se hace?*/
        assingAccountAvaliable();
    }

    @Override
    public void showLoader(String message) {
        progressLayout.setVisibility(VISIBLE);
        progressLayout.setTextMessage(message);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(GONE);
    }

    @Override
    public void showError(Object error){
        if(!error.toString().isEmpty()) {
            UI.showToastShort(error.toString(),getActivity());
        }
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    public boolean isCustomKeyboardVisible() {
        return keyboardView.getVisibility() == View.VISIBLE;
    }

    public void hideKeyboard(){
        keyboardView.hideCustomKeyboard();
    }

    private void resetCardNumberDefault(){
        editNumber.setText(DEFAULT_CARD+"");
        editNumber.setSelection(editNumber.getText().length());
    }

    private void generateCardNumberRamdon(){
        String randomNumber = DEFAULT_CARD + getCardNumberRamdon();
        editNumber.setText(randomNumber);
        editNumber.setFocusableInTouchMode(false);
        editNumber.setEnabled(false);
    }
}
