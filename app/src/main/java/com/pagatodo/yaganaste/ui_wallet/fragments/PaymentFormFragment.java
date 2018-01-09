package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.utils.Constants.TYPE_RELOAD;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link PaymentFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFormFragment extends GenericFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private ComercioResponse comercioResponse;
    private DataFavoritos dataFavoritos;

    private View rootView;
    @BindView(R.id.txt_title_payment)
    StyleTextView txtTitleFragment;
    @BindView(R.id.imgPagosUserProfile)
    CircleImageView imgUserPhoto;
    @BindView(R.id.imgPagosServiceToPayRound)
    CircleImageView imgDataPhoto;
    @BindView(R.id.txt_username_payment)
    StyleTextView txtNameUser;
    @BindView(R.id.txt_data)
    StyleTextView txtData;
    @BindView(R.id.txt_saldo)
    StyleTextView txtSaldo;
    @BindView(R.id.txt_monto)
    StyleTextView txtMonto;
    @BindView(R.id.btn_continue_payment)
    StyleButton btnContinue;

    /* RECARGAS BLOCK */
    @BindView(R.id.containerRecargaForm)
    LinearLayout lytContainerRecargas;
    @BindView(R.id.recargaNumber)
    StyleEdittext edtPhoneNumber;
    @BindView(R.id.imgMakePaymentContact)
    ImageView imgContacts;
    @BindView(R.id.sp_montoRecarga)
    Spinner spnMontoRecarga;
    @BindView(R.id.comisionTextRecarga)
    StyleTextView txtComisionRecarga;
    /***/

    /* SERVICIOS BLOCK */
    @BindView(R.id.containerServiciosForm)
    LinearLayout lytContainerServicios;
    @BindView(R.id.referenceNumber)
    StyleEdittext edtReferenceNumber;
    @BindView(R.id.imgMakePaymentRef)
    ImageView imgReferencePayment;
    @BindView(R.id.serviceImport)
    StyleEdittext edtServiceImport;
    @BindView(R.id.serviceConcept)
    StyleEdittext edtServiceConcept;
    @BindView(R.id.comisionTextServicio)
    StyleTextView txtComisionServicio;

    boolean isRecarga = false;

    /***/

    public PaymentFormFragment() {
    }

    public static PaymentFormFragment newInstance(ComercioResponse param1) {
        PaymentFormFragment fragment = new PaymentFormFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static PaymentFormFragment newInstance(DataFavoritos param1) {
        PaymentFormFragment fragment = new PaymentFormFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getSerializable(ARG_PARAM1) instanceof DataFavoritos) {
                dataFavoritos = (DataFavoritos) getArguments().getSerializable(ARG_PARAM1);
                isRecarga = true;
            } else {
                comercioResponse = (ComercioResponse) getArguments().getSerializable(ARG_PARAM1);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        if (comercioResponse != null) {
            if (comercioResponse.getIdTipoComercio() == TYPE_RELOAD) {
                lytContainerRecargas.setVisibility(View.VISIBLE);
            } else {
                lytContainerServicios.setVisibility(View.VISIBLE);
            }
        }

        /**
         * Mostramos la informacion en la cabecera correspondiente
         */
        if(isRecarga){
            // Carriers
            txtTitleFragment.setText(getResources().getString(R.string.tab_recargas));
          /*  imgUserPhoto
            imgDataPhoto
            txtNameUser
            txtData
            txtSaldo
            txtMonto*/
        }else{
            // Favoritos
            txtTitleFragment.setText(getResources().getString(R.string.tab_recargas));
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
