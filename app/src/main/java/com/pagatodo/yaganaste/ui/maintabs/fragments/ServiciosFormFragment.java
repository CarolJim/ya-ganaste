package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.ServiciosPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IServiciosPresenter;
import com.pagatodo.yaganaste.utils.NumberTagPase;
import com.pagatodo.yaganaste.utils.NumberTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;

import butterknife.BindView;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB2;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;

/**
 * Created by Jordan on 12/04/2017.
 */

public class ServiciosFormFragment extends PaymentFormBaseFragment implements PaymentsManager,
        View.OnClickListener {



    @BindView(R.id.referenceNumber)
    EditText referenceNumber;
    @BindView(R.id.layoutImageReference)
    RelativeLayout layoutImageReference;
    @BindView(R.id.serviceImport)
    EditText serviceImport;
    @BindView(R.id.serviceConcept)
    EditText serviceConcept;
    @BindView(R.id.comisionText)
    TextView comisionText;
    int maxLength;

    private boolean isValid = false;
    private IServiciosPresenter serviciosPresenter;
    PaymentsTabFragment fragment;

    public static ServiciosFormFragment newInstance() {
        ServiciosFormFragment fragment = new ServiciosFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            tab = TAB2;
            paymentsTabPresenter = ((PaymentsTabFragment) getParentFragment()).getPresenter();
            comercioItem = paymentsTabPresenter.getCarouselItem().getComercio();
            favoriteItem = paymentsTabPresenter.getCarouselItem().getFavoritos();
            if (comercioItem == null && favoriteItem != null) {
                comercioItem = paymentsTabPresenter.getComercioById(favoriteItem.getIdComercio());
            }
            serviciosPresenter = new ServiciosPresenter(this);
            fragment = (PaymentsTabFragment) getParentFragment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_servicios_form, container, false);

        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();




        layoutImageReference.setOnClickListener(this);
        serviceImport.addTextChangedListener(new NumberTextWatcher(serviceImport));


        if (comercioItem.getLongitudReferencia() > 0) {
            InputFilter[] fArray = new InputFilter[1];
            maxLength = Utils.calculateFilterLength(comercioItem.getLongitudReferencia());
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            referenceNumber.setFilters(fArray);
        }

        if (comercioItem.getFormato().equals("AN")) {
            referenceNumber.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        referenceNumber.addTextChangedListener(new NumberTagPase(referenceNumber, maxLength));
        if (comercioItem.getSobrecargo() > 0) {
            comisionText.setText(String.format(getString(R.string.comision_service_payment), StringUtils.getCurrencyValue(comercioItem.getSobrecargo())));
        } else {
            comisionText.setVisibility(View.INVISIBLE);
        }
        if (favoriteItem != null) {
            referenceNumber.setText(favoriteItem.getReferencia());
            //referenceNumber.setEnabled(false);
        }
/*
        serviceConcept.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        serviceConcept.setFocusable(true);
                        serviceConcept.requestFocus();
                    }
                });
                return false;
            }
        });


        serviceConcept.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    serviceConcept.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            final ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
                            scrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                    serviceConcept.setFocusable(true);
                                    serviceConcept.requestFocus();
                                }
                            });
                            return false;
                        }
                    });

                }else {
                    serviceConcept.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            final ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
                            scrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                    serviceConcept.setFocusable(true);
                                    serviceConcept.requestFocus();
                                }
                            });
                            return false;
                        }
                    });
                }
            }
        });

        serviceConcept.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        serviceConcept.setFocusable(true);
                        serviceConcept.requestFocus();
                    }
                });
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        serviceConcept.setFocusable(true);
                        serviceConcept.requestFocus();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
                final ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        serviceConcept.setFocusable(true);
                        serviceConcept.requestFocus();
                    }
                });
            }
        });



        serviceImport.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }
            }
        });
*/

        serviceImport.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionid, KeyEvent keyEvent) {
                if (actionid== EditorInfo.IME_ACTION_NEXT) {
                    // si pasamos al siguiente item

                    final ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            serviceConcept.setFocusable(true);
                            serviceConcept.requestFocus();
                        }
                    });
                    return true; // Focus will do whatever you put in the logic.)
                }
                return false;
            }
        });




        // Agregamos un setOnFocusChangeListener a nuestro campo de importe, solo si es un favorito
        if (favoriteItem != null) {
            serviceImport.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            serviceImport.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        // Toast.makeText(App.getContext(), "Foco fuera", Toast.LENGTH_SHORT).show();
                        if (serviceImport.getText().length() > 0) {
                            String serviceImportStr = serviceImport.getText().toString().substring(1).replace(",", "");

                            if (serviceImportStr != null && !serviceImportStr.isEmpty()) {
                                monto = Double.valueOf(serviceImportStr);
                                fragment.updateValueTabFrag(monto);
                            } else {
                                fragment.updateValueTabFrag(0.0);
                            }
                        }
                    }

                }
            });

        }

    }

    @Override
    protected void continuePayment() {
        if (!isValid) {
            showError();
            mySeekBar.setProgress(0);
        } else {
            //Toast.makeText(getContext(), "Realizar Pago", Toast.LENGTH_SHORT).show();
            //Se debe crear un objeto que se envía a la activity que realizará el pago
            payment = new Servicios(referencia, monto, concepto, comercioItem, favoriteItem != null);
            sendPayment();
        }
    }

    @Override
    public void showError() {
        if (errorText != null && !errorText.equals("")) {

            String errorTittle = "";
            if (errorText.equals(App.getContext().getString(R.string.txt_referencia_empty))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_serv_reference_error_empty);
                errorText = App.getContext().getResources().getString(R.string.new_body_serv_reference_error_empty);

            } else if (errorText.equals(App.getContext().getString(R.string.txt_referencia_errornuevo))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_serv_reference_error_empty);

            } else if (errorText.equals(App.getContext().getString(R.string.txt_importe_empty))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_serv_importe_error_empty);
                errorText = App.getContext().getResources().getString(R.string.new_body_serv_importe_error_empty);

            } else if (errorText.equals(App.getContext().getString(R.string.txt_importe_error))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_envios_importe_empty_error);
                errorText = App.getContext().getResources().getString(R.string.new_body_envios_importe_error);

            } else if (errorText.equals(App.getContext().getString(R.string.txt_concept_empty))) {
                errorTittle = App.getContext().getResources().getString(R.string.new_tittle_serv_concepto_error_empty);
                errorText = App.getContext().getResources().getString(R.string.new_body_serv_concepto_error_empty);
            }
            //Toast.makeText(getContext(), errorText, Toast.LENGTH_SHORT).show();
            UI.createSimpleCustomDialog(errorTittle, errorText, getActivity().getSupportFragmentManager(), getFragmentTag());
        }
    }

    @Override
    public void onError(String error) {
        isValid = false;
        errorText = error;
    }

    @Override
    public void onSuccess(Double importe) {
        this.monto = importe;
        isValid = true;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layoutImageReference) {
            Intent intent = new Intent(getActivity(), ScannVisionActivity.class);
            getActivity().startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    referenceNumber.setText(barcode.displayValue);
                }
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        referencia = referenceNumber.getText().toString().replaceAll(" ", "");
        concepto = serviceConcept.getText().toString().trim();
        serviciosPresenter.validateFields(referencia, serviceImport.getText().toString().trim(), concepto, comercioItem.getLongitudReferencia());
    }

}
