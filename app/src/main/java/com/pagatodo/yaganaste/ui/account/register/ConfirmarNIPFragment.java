package com.pagatodo.yaganaste.ui.account.register;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAccountCardNIPView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.AsignarNipTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.BorderTitleLayout;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class ConfirmarNIPFragment extends GenericFragment implements View.OnClickListener, ValidationForms, IAccountCardNIPView {

    public static String PIN_TO_CONFIRM = "PIN_TO_CONFIRM";
    private static int PIN_LENGHT = 4;
    @BindView(R.id.borderLayout)
    BorderTitleLayout borderTitleLayout;
    @BindView(R.id.asignar_edittext)
    CustomValidationEditText edtPin;
    @BindView(R.id.btnNextAsignarPin)
    Button btnNextAsignarPin;
    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;
    @BindView(R.id.progressIndicator)
    ProgressLayout progressLayout;
    LinearLayout layout_control;
    TextView tv1Num;
    TextView tv2Num;
    TextView tv3Num;
    TextView tv4Num;
    private View rootview;
    private String nip = "";
    private String nipToConfirm = "";
    private AccountPresenterNew accountPresenter;

    public ConfirmarNIPFragment() {
    }

    public static ConfirmarNIPFragment newInstance(String nip) {
        ConfirmarNIPFragment fragmentRegister = new ConfirmarNIPFragment();
        Bundle args = new Bundle();
        args.putString(PIN_TO_CONFIRM, nip);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nipToConfirm = getArguments().getString(PIN_TO_CONFIRM);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
        //accountPresenter = new AccountPresenterNew(getActivity(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_asignar_nip, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnNextAsignarPin.setOnClickListener(this);

        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);
        keyboardView.showCustomKeyboard(rootview);

        layout_control = (LinearLayout) rootview.findViewById(R.id.asignar_control_layout);

        tv1Num = (TextView) rootview.findViewById(R.id.asignar_tv1);
        tv2Num = (TextView) rootview.findViewById(R.id.asignar_tv2);
        tv3Num = (TextView) rootview.findViewById(R.id.asignar_tv3);
        tv4Num = (TextView) rootview.findViewById(R.id.asignar_tv4);
        borderTitleLayout.setTitle(getString(R.string.confirma_pin));

        edtPin = (CustomValidationEditText) rootview.findViewById(R.id.asignar_edittext);
        edtPin.setMaxLength(4); // Se asigna un maximo de 4 caracteres para no tener problrmas
        edtPin.addCustomTextWatcher(new AsignarNipTextWatcher(edtPin, tv1Num, tv2Num, tv3Num, tv4Num));
        edtPin.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 4) {
                    buttonIsVisible(false);
                    //keyboardView.hideCustomKeyboard();
                    validateForm();
                }
            }
        });


        layout_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonIsVisible(false);
                edtPin.requestFocus();
                keyboardView.showCustomKeyboard(v);
            }
        });

        edtPin.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                buttonIsVisible(false);
                keyboardView.showCustomKeyboard(v);
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        setValidationRules();
        edtPin.requestEditFocus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNextAsignarPin:
                validateForm();
                break;
            default:
                break;
        }
    }

    /*Implementación de ValidationForms*/

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        getDataForm();

        if (nip.length() < PIN_LENGHT) {
            showValidationError(getString(R.string.asignar_pin));
            return;
        }

        if (!nip.equals(nipToConfirm)) {
            showValidationError(getString(R.string.confirmar_pin));
            return;
        }

        onValidationSuccess();
    }

    private void showValidationError(Object err) {
        showValidationError(0, err);
    }

    @Override
    public void showValidationError(int id, Object error) {
        //UI.showToastShort(error.toString(), getActivity());
        UI.createSimpleCustomDialog("", error.toString(), getFragmentManager(),
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

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        accountPresenter.assignNIP(nip);
    }

    @Override
    public void getDataForm() {
        nip = edtPin.getText().toString().trim();
    }

    @Override
    public void nextScreen(String event, Object data) {
        showLoader("");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                hideLoader();
                onEventListener.onEvent(EVENT_GO_ASOCIATE_PHONE, null);//Mostramos la siguiente pantalla SMS.
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
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
        UI.showToastShort(error.toString(), getActivity());
    }

    public boolean isCustomKeyboardVisible() {
        return false;
    }

    private void buttonIsVisible(boolean isVisible) {
        btnNextAsignarPin.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void hideKeyboard() {
        //keyboardView.hideCustomKeyboard();
    }
}