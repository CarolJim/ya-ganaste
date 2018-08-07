package com.pagatodo.yaganaste.ui.account.register;


import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
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

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAccountCardView;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASSIGN_PIN;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.utils.Recursos.DEFAULT_CARD;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;
import static com.pagatodo.yaganaste.utils.Utils.getCardNumberRamdon;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class TienesTarjetaFragment extends GenericFragment implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, IAccountCardView {

    private static int LEGTH_CARD_NUMBER_FORMAT = 19;
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
    @BindView(R.id.txt_tarjeta_date_tdc)
    StyleTextView dateTDC;
    @BindView(R.id.txt_tarjeta_user_name)
    StyleTextView userName;
    private static Preferencias preferencias = App.getInstance().getPrefs();

    String a;
    private View rootview;
    private AccountPresenterNew accountPresenter;
    private char mask_number_card = '_';
    public static int LONG_PRESS_TIME = 500;
    private final Handler handler = new Handler();

    private final Runnable longPressed = new Runnable() {
        public void run() {
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.i(getTag(), "LongPress");
            }
        }
    };
    private String cadenaHuella = null;

    public TienesTarjetaFragment() {
    }

    public static TienesTarjetaFragment newInstance() {
        TienesTarjetaFragment fragmentRegister = new TienesTarjetaFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);

        cadenaHuella = App.getInstance().getCadenaHuella();

        cadenaHuella = new StringBuilder()
                .append(cadenaHuella)
                .append("-")
                .append(preferencias.loadData("SHA_256_FREJA"))
                .toString();
        App.getInstance().setCadenaHuella(cadenaHuella);

        String f = cadenaHuella;

        if (f != null) {


        }
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
    public void onResume() {
        super.onResume();

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
        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip_tarjeta);
        keyboardView.setPreviewEnabled(false);

        btnNextTienesTarjeta.setOnClickListener(this);
        final Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Regular.ttf");
        radioHasCard.setOnCheckedChangeListener(this);
        //radioBtnNo.setChecked(true);//Selección por Default

        RegisterUser registerUser = RegisterUser.getInstance();
        String name;
        String lastame;
        if (registerUser.getNombre().isEmpty() || registerUser.getApellidoPaterno().isEmpty()) {
            name = SingletonUser.getInstance().getDataUser().getCliente().getNombre();
            lastame = SingletonUser.getInstance().getDataUser().getCliente().getPrimerApellido();
        } else {
            name = registerUser.getNombre();
            lastame = registerUser.getApellidoPaterno();
        }

        name = StringUtils.getFirstName(name);
        userName.setText(name.concat(" ").concat(lastame));

        editNumber.setTypeface(typeface);
        ViewTreeObserver viewTreeObserver = layoutCard.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int layoutHeight = layoutCard.getHeight();
                float sp = (float) ((layoutHeight * 0.11) / getResources().getDisplayMetrics().scaledDensity);
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

                editNumber.removeTextChangedListener(this);
                editNumber.setCursorVisible(false);

                editNumber.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        StringBuilder cardNumber = new StringBuilder(editNumber.getText().toString());
                        int lastSharpIndex = cardNumber.indexOf("_");
                        if (keyCode == KeyEvent.KEYCODE_DEL) {
                            if (lastSharpIndex <= 7 && lastSharpIndex >= 0) {
                                return false;
                            }
                            if (lastSharpIndex != -1) {
                                if (lastSharpIndex == 10 || lastSharpIndex == 15) {
                                    cardNumber.setCharAt(lastSharpIndex - 2, mask_number_card);
                                } else {
                                    cardNumber.setCharAt(lastSharpIndex - 1, mask_number_card);
                                }
                            } else {
                                cardNumber.setCharAt(cardNumber.length() - 1, mask_number_card);
                            }

                        } else {
                            char lastChar = (char) event.getUnicodeChar();
                            if (lastSharpIndex != -1 && keyCode != 29 && keyCode != 1021 && keyCode != 4) {
                                cardNumber.setCharAt(lastSharpIndex, lastChar);
                            }
                        }
                        editNumber.setText(cardNumber);
                        if (cardNumber.indexOf("_") == -1) {
                            hideKeyboard();
                        }
                        return false;
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // keyboardView.showCustomKeyboard(v);
                } else {
                    keyboardView.hideCustomKeyboard();
                }
            }
        });

        editNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler.postDelayed(longPressed, LONG_PRESS_TIME);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        handler.removeCallbacks(longPressed);
                        onTouchEvent(v, event);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacks(longPressed);
                        onTouchEvent(v, event);
                        break;
                }
                return true; // Consume touch event
            }
        });

        editNumber.setFocusableInTouchMode(true);
        editNumber.setEnabled(true);
        editNumber.setCursorVisible(false);
        editNumber.requestFocus();
        keyboardView.showCustomKeyboard(editNumber);
        txtMessageCard.setText(getString(R.string.si_tiene_tarjeta));
        resetCardNumberDefault();
    }

    private void onTouchEvent(View v, MotionEvent event) {
        EditText edittext = (EditText) v;
        int inType = edittext.getInputType();       // Backup the input type
        edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
        edittext.onTouchEvent(event);               // Call native handler
        //  keyboardView.showCustomKeyboard(v);
        edittext.setInputType(inType);// Restore input type
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
                editNumber.setCursorVisible(false);
                editNumber.requestFocus();
                btnNextTienesTarjeta.setVisibility(View.VISIBLE);
                keyboardView.showCustomKeyboard(editNumber);
                txtMessageCard.setText(getString(R.string.si_tiene_tarjeta));
                resetCardNumberDefault();
                break;
            case R.id.radioBtnNo:
                txtMessageCard.setText(getString(R.string.no_tiene_tarjeta));

                generateCardNumberRamdon();
                keyboardView.hideCustomKeyboard();
                btnNextTienesTarjeta.setVisibility(View.VISIBLE);
                // Reiniciamos al TDC vacia
                editNumber.setText("");
                break;
            default:
                // Reiniciamos al TDC vacia
                editNumber.setText("");
                break;
        }
    }

    private void selectNextAction() {
        if (radioBtnNo.isChecked()) { // Selecciona que no tiene tarjeta
            /*TODO Asignar cuenta*/
            assingAccountAvaliable();
        } else {
            String numberCard = editNumber.getText() != null ? editNumber.getText().toString().trim() : "";
            if (radioBtnYes.isChecked() && numberCard.length() == LEGTH_CARD_NUMBER_FORMAT) { // Validamos que ingrese la tarjeta
                accountPresenter.checkCardAssigment(numberCard);
            } else {
                // UI.showToastShort(getString(R.string.tienes_tarjeta_numero),getActivity());
                UI.createSimpleCustomDialog("", getString(R.string.tienes_tarjeta_numero), getFragmentManager(),
                        new DialogDoubleActions() {
                            @Override
                            public void actionConfirm(Object... params) {

                            }

                            @Override
                            public void actionCancel(Object... params) {

                            }
                        },
                        true, false);
            }
        }
    }

    private void assingAccountAvaliable() {
        accountPresenter.assignAccount();
    }

    @Override
    public void accountAssigned(String message) {
        showLoader("");
        txtMessageCard.setText(getString(R.string.si_tiene_tarjeta));
        new Handler().postDelayed(new Runnable() {
            public void run() {
                hideLoader();
                nextScreen(EVENT_GO_ASSIGN_PIN, null);// Mostramos la pantalla para obtener tarjeta.
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
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(Object error) {
        if (!error.toString().isEmpty()) {
            // UI.showToastShort(error.toString(),getActivity());
            String errorS = error.toString();
            String title = "";
            if (error.toString().contains("_")) {
                String[] custom = error.toString().split("_");
                title = custom[0];
                errorS = custom[1];
            }
            UI.createSimpleCustomDialog(title, errorS, getFragmentManager(),
                    new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {

                        }

                        @Override
                        public void actionCancel(Object... params) {

                        }
                    },
                    true, false);
        }
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    public boolean isCustomKeyboardVisible() {
        return keyboardView.getVisibility() == View.VISIBLE;
    }

    public void hideKeyboard() {
        keyboardView.hideCustomKeyboard();
    }

    private void resetCardNumberDefault() {
        editNumber.setText(DEFAULT_CARD + "__ ____ ____");
    }

    private void generateCardNumberRamdon() {
        String randomNumber = DEFAULT_CARD + getCardNumberRamdon();
        editNumber.setText(randomNumber);
        editNumber.setFocusableInTouchMode(false);
        editNumber.setEnabled(false);
    }
}
