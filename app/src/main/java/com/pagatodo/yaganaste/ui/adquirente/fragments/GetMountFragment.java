package com.pagatodo.yaganaste.ui.adquirente.fragments;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dspread.xpos.QPOSService;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.EditTextImeBackListener;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.AdqActivity;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentFormBaseFragment;
import com.pagatodo.yaganaste.utils.NumberCalcTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.TYPE_TRANSACTION;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.REQUEST_CHECK_SETTINGS;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENTS_ADQUIRENTE;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;

public class GetMountFragment extends PaymentFormBaseFragment implements EditTextImeBackListener,
        OnCompleteListener<LocationSettingsResponse>, View.OnClickListener, NumberCalcTextWatcher.TextChange {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 900;
    private static final String NAME_COMERCE = "NAME_COMERCE";

    @BindView(R.id.txtNameComerce)
    StyleTextView txtNameComerce;
    @BindView(R.id.et_amount)
    public EditText et_amount;
    @BindView(R.id.edtConcept)
    EditText edtConcept;
    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;
    @BindView(R.id.img_arrow_previous)
    ImageView imgArrowPrev;
    @BindView(R.id.btncobrar)
    LinearLayout btncobrar;
    @BindView(R.id.cobrar)
    StyleTextView cobrar;
    @BindView(R.id.amount_text)
    MontoTextView amount_text;
    @BindView(R.id.text_input_concepto)
    TextInputLayout inputConcept;

    LinearLayout layout_amount;
    private String nameComerce = "";
    private float MIN_AMOUNT = 1.0f;
    boolean isValid;
    private int[] perrmisionArray = {1, 1};
    private StyleTextView tvMontoEntero, tvMontoDecimal;

    public GetMountFragment() {
        // Required empty public constructor
    }

    public static GetMountFragment newInstance(String nameComerce) {
        GetMountFragment getMountFragment = new GetMountFragment();
        Bundle args = new Bundle();
        args.putString(NAME_COMERCE, nameComerce);
        getMountFragment.setArguments(args);
        return getMountFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListener) {
            this.onEventListener = (OnEventListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nameComerce = getArguments().getString(NAME_COMERCE);
        }
        isValid = true;
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_monto, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();

        /**
         * layout_amount capa que controla el abrir el teclado
         * tvMontoEntero TextView que contiene los enteros del elemento
         * tvMontoDecimal TextView que contiene los decimales del elemento
         * et_amount EditText oculto que captura los elementos que procesaremos, pero que no se
         * muestra en pantalla
         */
        txtNameComerce = (StyleTextView) rootview.findViewById(R.id.txtNameComerce);
        layout_amount = (LinearLayout) rootview.findViewById(R.id.layout_amount_control);
        tvMontoEntero = (StyleTextView) rootview.findViewById(R.id.tv_monto_entero);
        tvMontoDecimal = (StyleTextView) rootview.findViewById(R.id.tv_monto_decimal);
        imgArrowPrev = (ImageView) rootview.findViewById(R.id.img_arrow_previous);
        cobrar = (StyleTextView) rootview.findViewById(R.id.cobrar);
        edtConcept = (EditText) rootview.findViewById(R.id.edtConcept);
        inputConcept = rootview.findViewById(R.id.text_input_concepto);

        et_amount.addTextChangedListener(new NumberCalcTextWatcher(et_amount, tvMontoEntero, tvMontoDecimal, edtConcept, this));
        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);
        btncobrar.setOnClickListener(this);
        txtNameComerce.setText(nameComerce);
        et_amount.requestFocus();

/*      Se comenta este codigo para quitar el Shebron y la funcionalidad, se deja por si se retoma el disenio
        if (getActivity() instanceof AccountActivity) {
            imgArrowPrev.setVisibility(View.VISIBLE);
            imgArrowPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((QuickBalanceContainerFragment) getParentFragment()).getQuickBalanceManager().onBackPress();
                }
            });
        }*/


        // Make the custom keyboard appear
        et_amount.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                keyboardView.showCustomKeyboard(v);
            } else {
                //keyboardView.hideCustomKeyboard();
            }
        });

        /**
         * Agregamos el Listener al layout que contiene todos los elementos, esto es para que se
         * abra el teclado custom
         */
        layout_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_amount.requestFocus();
                keyboardView.showCustomKeyboard(v);
            }
        });

        et_amount.setOnTouchListener(new View.OnTouchListener() {
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

        //edtConcept.setOnEditTextImeBackListener(this);
        edtConcept.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtConcept.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    et_amount.requestFocus();
                    return true;
                }
                return false;
            }
        });
        edtConcept.setOnFocusChangeListener((view, b) -> {
            if (b) {
                inputConcept.setBackgroundResource(R.drawable.inputtext_active);
            } else {
                inputConcept.setBackgroundResource(R.drawable.inputtext_normal);
                et_amount.requestFocus();
            }
        });
        et_amount.setText("0");
        edtConcept.setText(null);
        mySeekBar.setProgress(0);
        NumberCalcTextWatcher.cleanData();
        et_amount.requestFocus();
    }

    @Override
    protected void continuePayment() {
        App.getInstance().setCurrentMount(et_amount.getText().toString().trim());
        int permissionCall = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.RECORD_AUDIO);
        int permissionLocationFine = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionLocation = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionLocation == -1 || permissionCall == -1 || permissionLocationFine == -1) {
            ValidatePermissions.checkPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO},
                    REQUEST_ID_MULTIPLE_PERMISSIONS);

        } else {
            isValid = true;
            setLocationSetting();
        }
    }

    private void setLocationSetting() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(getContext()).checkLocationSettings(builder.build());
        result.addOnCompleteListener(this);
    }

    private void actionCharge() {
        //String valueAmount = et_amount.getText().toString().trim();
        String valueAmount = App.getInstance().getCurrentMount();

        // Limpiamos del "," que tenemos del EditText auxiliar
        int positionQuote = valueAmount.indexOf(",");
        if (positionQuote > 0) {
            String[] valueAmountArray = valueAmount.split(",");
            valueAmount = valueAmountArray[0] + valueAmountArray[1];
        }

        if (valueAmount.length() > 0 && !valueAmount.equals(getString(R.string.mount_cero))) {
            try {

                StringBuilder cashAmountBuilder = new StringBuilder(valueAmount);

                // Limpiamos del caracter $ en caso de tenerlo
                int positionMoney = valueAmount.indexOf("$");
                if (positionMoney == 0) {
                    valueAmount = cashAmountBuilder.deleteCharAt(0).toString();
                }

                float current_mount = Float.parseFloat(valueAmount);
                String current_concept = edtConcept.getText().toString().trim();//Se agrega Concepto opcional
                if (current_mount >= MIN_AMOUNT) {
                    TransactionAdqData.getCurrentTransaction().setAmount(valueAmount);
                    TransactionAdqData.getCurrentTransaction().setDescription(current_concept);
                    //setData("", "");
                    /*NumberCalcTextWatcher.cleanData();
                    et_amount.setText("0");
                    edtConcept.setText(null);
                    mySeekBar.setProgress(0);
                    NumberCalcTextWatcher.cleanData();*/

                    //onEventListener.onEvent(EVENT_GO_INSERT_DONGLE,null);
                } else showValidationError(getString(R.string.mount_be_higer));
            } catch (NumberFormatException e) {
                showValidationError(getString(R.string.mount_valid));
            }
        } else showValidationError(getString(R.string.enter_mount));

    }

    private void actionChargecobro() {
        //String valueAmount = et_amount.getText().toString().trim();
        App.getInstance().setCurrentMount(et_amount.getText().toString().trim());
        String valueAmount = App.getInstance().getCurrentMount();
        if (valueAmount.length() > 0 && !valueAmount.equals(getString(R.string.mount_cero))) {
            try {

                StringBuilder cashAmountBuilder = new StringBuilder(valueAmount);

                // Limpiamos del caracter $ en caso de tenerlo
                int positionMoney = valueAmount.indexOf("$");
                if (positionMoney == 0) {
                    valueAmount = cashAmountBuilder.deleteCharAt(0).toString();
                }

                // Limpiamos del "," que tenemos del EditText auxiliar
                int positionQuote = valueAmount.indexOf(",");
                if (positionQuote > 0) {
                    String[] valueAmountArray = valueAmount.split(",");
                    valueAmount = valueAmountArray[0] + valueAmountArray[1];
                }


                float current_mount = Float.parseFloat(valueAmount);
                String current_concept = edtConcept.getText().toString().trim();//Se agrega Concepto opcional
                if (current_mount >= MIN_AMOUNT) {
                    TransactionAdqData.getCurrentTransaction().setAmount(valueAmount);
                    TransactionAdqData.getCurrentTransaction().setDescription(current_concept);
                    //setData("", "");
                    /*NumberCalcTextWatcher.cleanData();
                    et_amount.setText("0");
                    edtConcept.setText(null);
                    mySeekBar.setProgress(0);
                    NumberCalcTextWatcher.cleanData();*/

                    //onEventListener.onEvent(EVENT_GO_INSERT_DONGLE,null);

                    Intent intent = new Intent(getActivity(), AdqActivity.class);
                    intent.putExtra(TYPE_TRANSACTION, QPOSService.TransactionType.PAYMENT.ordinal());
                    getActivity().startActivityForResult(intent, PAYMENTS_ADQUIRENTE);
                } else showValidationError(getString(R.string.mount_be_higer));
            } catch (NumberFormatException e) {
                showValidationError(getString(R.string.mount_valid));
            }
        } else
            showValidationError(getString(R.string.enter_mount));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:

                isValid = grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (isValid) {
                    setLocationSetting();
                } else {
                    //et_amount.setText(App.getInstance().getCurrentMount());
                }
                break;

        }

    }


    @Override
    public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
        try {
            LocationSettingsResponse response = task.getResult(ApiException.class);
            //  actionCharge();
        } catch (final ApiException exception) {
            switch (exception.getStatusCode()) {
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    showDialogMesage(exception, getContext().getResources().getString(R.string.eneable_gps));
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    // Location settings are not satisfied. However, we have no way to fix the
                    // settings so we won't show the dialog.
                    //showDialogMesage(null,getContext().getResources().getString(R.string.new_tittle_error_interno));
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:

                        //  actionCharge();
                        break;
                    case Activity.RESULT_CANCELED:
                        //et_amount.setText(App.getInstance().getCurrentMount());
                        break;
                    default:
                        break;
                }
                break;
            case PAYMENTS_ADQUIRENTE:
                if (resultCode == RESULT_OK) {
                    NumberCalcTextWatcher.cleanData();
                    et_amount.setText("0");
                    edtConcept.setText(null);
                    mySeekBar.setProgress(0);
                    NumberCalcTextWatcher.cleanData();
                }
                break;
        }
    }

    private void showValidationError(String error) {
        UI.showToast(error, getActivity());
        mySeekBar.setProgress(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*NumberCalcTextWatcher.cleanData();
        et_amount.setText("0");
        //et_amount.setText(App.getInstance().getCurrentMount());
        edtConcept.setText(null);
        mySeekBar.setProgress(0);*/

        et_amount.requestFocus();
        int permissionCall = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.RECORD_AUDIO);
        int permissionLocationFine = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionLocation = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionLocation == -1 || permissionCall == -1 || permissionLocationFine == -1) {
            ValidatePermissions.checkPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO},
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
        if (App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            if (!adapter.isEnabled()) {
                Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enabler);
            }
        }
    }

    @Override
    public void onImeBack() {
        et_amount.requestFocus();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            et_amount.requestFocus();
        } else if (et_amount != null) {
            NumberCalcTextWatcher.cleanData();
            et_amount.setText("0");
            edtConcept.setText(null);
        }
    }

    private void showDialogMesage(final ApiException exception, final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        if (exception != null) {
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            } catch (ClassCastException e) {
                                e.printStackTrace();

                            }
                        }
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btncobrar) {
            actionChargecobro();
        }
    }


    @Override
    public void onChangeTextListener(String text) {
        amount_text.setText(text);
    }
}

