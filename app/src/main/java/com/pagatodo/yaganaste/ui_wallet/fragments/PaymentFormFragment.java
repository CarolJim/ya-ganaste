package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.adapters.SpinnerArrayAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.RecargasPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IRecargasPresenter;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB1;
import static com.pagatodo.yaganaste.utils.Constants.IAVE_ID;
import static com.pagatodo.yaganaste.utils.Constants.TYPE_RELOAD;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link PaymentFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFormFragment extends GenericFragment implements PaymentsManager {
    private static final String ARG_PARAM1 = "param1";

    private ComercioResponse comercioResponse;
    private DataFavoritos dataFavoritos;

    private View rootView;
    @BindView(R.id.txt_title_payment)
    StyleTextView txtTitleFragment;
    @BindView(R.id.imgPagosUserProfile)
    CircleImageView imgUserPhoto;
    @BindView(R.id.imgPagosServiceToPayRound)
    CircleImageView circuleDataPhoto;
    @BindView(R.id.imgItemGalleryPay)
    ImageView imageDataPhoto;
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
    boolean isFavorito = false;
    boolean isIAVE;

    private SpinnerArrayAdapter dataAdapter;
    private IRecargasPresenter recargasPresenter;
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
                if(dataFavoritos != null){
                    if(dataFavoritos.getIdFavorito() >= 0){
                        isFavorito = true;
                    }
                }
            } else {
                comercioResponse = (ComercioResponse) getArguments().getSerializable(ARG_PARAM1);
                if(comercioResponse != null){
                    if(comercioResponse.getIdTipoComercio() == 1){
                        isRecarga = true;
                        isFavorito = false;
                    }
                }
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

        // Mostramos el titular
        if(isRecarga){
            // Recargas
            txtTitleFragment.setText(getResources().getString(R.string.txt_recargas));
            //  showImageData(circuleDataPhoto, isFavorito);
          /*  imgUserPhoto
            circuleDataPhoto
            txtNameUser
            txtData
            txtSaldo
            txtMonto*/
        }else{
            // Servicios
            txtTitleFragment.setText(getResources().getString(R.string.txt_servicios));
        }

        // Info espeficica de Carrier o Favorito
        if(dataFavoritos != null){
            // Cargamos el nombre del favorito, imagen y borde
            txtData.setText(dataFavoritos.getNombre());
            setImagePicasoFav(imageDataPhoto, circuleDataPhoto, 1);
        }

        if(comercioResponse != null){
            // Cargamos el nombre del Carrier, imagen y borde
            txtData.setText(comercioResponse.getNombreComercio());
            setImagePicasoFav(imageDataPhoto, circuleDataPhoto, 2);

            isIAVE = comercioResponse.getIdComercio() == IAVE_ID;
            recargasPresenter = new RecargasPresenter(this, isIAVE);

            List<Double> montos = comercioResponse.getListaMontos();
            if (montos.get(0) != 0D) {
                montos.add(0, 0D);
            }

            //dataAdapter = new SpinnerArrayAdapter(getContext(), 1, montos);
        }

        /**
         * Iniciamos el monto en cero, Mostramos la informacion del usuario, foto y saldo
         */
        txtMonto.setText("" + Utils.getCurrencyValue(0));
        SingletonUser dataUser = SingletonUser.getInstance();
        txtNameUser.setText("" + dataUser.getDataUser().getUsuario().getNombre());
        txtSaldo.setText("" + Utils.getCurrencyValue(11350));
        String imagenavatar = dataUser.getDataUser().getUsuario().getImagenAvatarURL();
        if(!imagenavatar.equals("")){
            Picasso.with(App.getContext())
                    .load(imagenavatar)
                    .placeholder(R.mipmap.icon_user)
                    .into(circuleDataPhoto);
        }


    }

    private void setImagePicasoFav(ImageView imageDataPhoto, CircleImageView circuleDataPhoto, int mType) {
        if(mType == 1){
            String mPhoto = dataFavoritos.getImagenURL();
            if(!mPhoto.equals("")){
                Picasso.with(App.getContext())
                        .load(mPhoto)
                        .placeholder(R.mipmap.icon_user)
                        .into(circuleDataPhoto);
            }
            circuleDataPhoto.setBorderColor(Color.parseColor(dataFavoritos.getColorMarca()));
        }

        if(mType == 2){
            String mPhoto = comercioResponse.getLogoURL();
            if(!mPhoto.equals("")){
                Picasso.with(App.getContext())
                        .load(App.getContext().getString(R.string.url_images_logos) + mPhoto)
                        .placeholder(R.mipmap.icon_user)
                        .into(imageDataPhoto);
            }
            circuleDataPhoto.setBorderColor(Color.parseColor(comercioResponse.getColorMarca()));
        }


        /*Glide.with(App.getContext()).load(urlLogo).placeholder(R.mipmap.icon_user)
                .error(R.mipmap.ic_launcher)
                .dontAnimate().into(imageView);*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showError() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess(Double importe) {

    }
}
