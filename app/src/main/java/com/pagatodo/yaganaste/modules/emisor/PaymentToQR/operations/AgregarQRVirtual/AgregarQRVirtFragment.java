package com.pagatodo.yaganaste.modules.emisor.PaymentToQR.operations.AgregarQRVirtual;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.emisor.PaymentToQR.operations.QrOperationActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarQRVirtFragment extends GenericFragment implements  AgregarVirtContracts.Listener {
    @BindView(R.id.edit_code_qr)
    EditText edit_code_qr;
    @BindView(R.id.text_name_qr)
    TextInputLayout text_name_qr;
    AgregarVirtContracts.Iteractor iteractor;
    @BindView(R.id.subtitle_addqr)
    StyleTextView subtitle_addqr;
    @BindView(R.id.btnAddQR)
    StyleButton btnAddQR;
    View rootView;
    public static String plate;
    boolean isValid=false;
    public static int MILISEGUNDOS_ESPERA = 1000;
    public static QrOperationActivity activityf;
    public static AgregarQRVirtFragment newInstance( QrOperationActivity activity){

        activityf =activity;
        return new AgregarQRVirtFragment();
    }

    public AgregarQRVirtFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_agrega_qr, container, false);
        initViews();
        iteractor= new AgregarVirtQRIteractor(this,getContext());
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        text_name_qr.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
        imm.showSoftInput(text_name_qr, InputMethodManager.SHOW_IMPLICIT);
        // Toast.makeText(getActivity(), "Plate: "+plate, Toast.LENGTH_LONG).show();
        edit_code_qr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                text_name_qr.setBackgroundResource(R.drawable.inputtext_active);
                return false;
            }
        });

        edit_code_qr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                subtitle_addqr.setText(edit_code_qr.getText().toString());
                if (edit_code_qr.getText().length()>0)
                {
                    btnAddQR.setBackgroundResource(R.drawable.button_rounded_blue);
                    isValid=true;
                }
                else{
                    btnAddQR.setBackgroundResource(R.drawable.button_rounded_gray);
                    isValid = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });



        btnAddQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid){
                    activityf.showLoader("Generando QR");
                    iteractor.validarQRValido(edit_code_qr.getText().toString().trim());
                }else {

                    UI.showErrorSnackBar(getActivity(), getString(R.string.qr_name_add), Snackbar.LENGTH_SHORT);
                    text_name_qr.setBackgroundResource(R.drawable.input_text_error);

                }
            }
        });


    }
    @Override
    public void onSuccessQRs() {
        activityf.hideLoader();
        UI.showSuccessSnackBar(getActivity(), getString(R.string.qr_add_succes), Snackbar.LENGTH_SHORT);
        esperarYCerrar(MILISEGUNDOS_ESPERA);

      /*  UI.showAlertDialog(getActivity(), getResources().getString(R.string.qr_add_succes),"",
                R.string.title_aceptar, (dialogInterface, i) -> {
            getActivity().finish();
                });*/


    }
    public void esperarYCerrar(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                finalizarApp();
            }
        }, milisegundos);
    }

    /**
     * Finaliza la aplicación
     */
    public void finalizarApp() {
        getActivity().finish();
    }
    @Override
    public void onErrorQRs() {
        activityf.hideLoader();
        UI.showErrorSnackBar(getActivity(), getString(R.string.qr_add_error), Snackbar.LENGTH_SHORT);
    }
}
