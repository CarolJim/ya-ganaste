package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Ventas;
import com.pagatodo.yaganaste.interfaces.IVentasAdmin;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.presenter.VentasDiariasPresenter;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 * Armando Sandoval
 */
public class VentasDiariasFragment extends SupportFragment implements View.OnClickListener, IVentasAdmin {
    @BindView(R.id.titulo_negocio)
    StyleTextView titulo_negocio;
    @BindView(R.id.linerarventashoy)
    LinearLayout linerarventashoy;
    @BindView(R.id.linerarventasayer)
    LinearLayout linerarventasayer;
    @BindView(R.id.linerarventashoycontainer)
    LinearLayout linerarventashoycontainer;
    @BindView(R.id.linerarventasayercontainer)
    LinearLayout linerarventasayercontainer;
    @BindView(R.id.sub_actualizacin)
    StyleTextView sub_actualizacin;
    private StyleTextView tvMontoEnteroPrincipal, tvMontoDecimalPrincipal,tvMontoEnteroTicketp, tvMontoDecimalTicketp;
    @BindView(R.id.cobrosrealizados)
    StyleTextView cobrosrealizados;
    Calendar c = Calendar.getInstance();
    String ayer,hoy,mes,annio;
    String ultimaactu;
    View rootView;
    VentasDiariasPresenter presenter ;
    String datetime;
    public static  VentasDiariasFragment newInstance(ElementView elementView){

        VentasDiariasFragment fragment = new VentasDiariasFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, elementView);
        fragment.setArguments(args);
        return fragment;
    }
    public VentasDiariasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView  =  inflater.inflate(R.layout.fragment_ventas_diarias, container, false);


        Calendar cal = Calendar.getInstance();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
        datetime = dateformat.format(c.getTime());

        System.out.println(datetime);

        ayer = Integer.toString(c.get(Calendar.DATE));
        presenter = new VentasDiariasPresenter(getContext(),this);
        ultimaactu = datetime.substring(11,24);
        initViews();
        presenter.obtenerResumendia(datetime.substring(0,10));
        return rootView;
    }
    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.linerarventasayercontainer){
            String ayer  = getYesterdayDateString().substring(0,10).replace("/","-");
            presenter.obtenerResumendia(ayer);
            linerarventashoycontainer.setBackgroundColor(Color.parseColor("#ffffff"));
            linerarventasayercontainer.setBackgroundColor(Color.parseColor("#00a1e1"));


        }
        if (view.getId()==R.id.linerarventashoycontainer){
            presenter.obtenerResumendia(datetime.substring(0,10));
            linerarventashoy.setBackgroundColor(Color.parseColor("#00a1e1"));
            linerarventasayer.setBackgroundColor(Color.parseColor("#ffffff"));
        }

    }
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        tvMontoEnteroPrincipal = (StyleTextView) rootView.findViewById(R.id.tv_monto_enteroprincipal);
        tvMontoDecimalPrincipal = (StyleTextView) rootView.findViewById(R.id.tv_monto_decimalprincipal);
        tvMontoEnteroTicketp = (StyleTextView) rootView.findViewById(R.id.tv_monto_enteroticketp);
        tvMontoDecimalTicketp = (StyleTextView) rootView.findViewById(R.id.tv_monto_decimalticketp);
        sub_actualizacin.setText(ultimaactu);
        linerarventasayercontainer.setOnClickListener(this::onClick);
        linerarventashoycontainer.setOnClickListener(this::onClick);
    }

    @Override
    public void succedGetResumenDia(String mensaje) {
        Ventas ventas = Ventas.getInstance();
        cobrosrealizados.setText(ventas.getCobrosr());
        String ventasprincipal =ventas.getMontoventas();
        String[] split = ventasprincipal.split("\\.");
        String enteroP = split[0];
        String decimalP = split[1];
        tvMontoEnteroPrincipal.setText(enteroP);
        tvMontoDecimalPrincipal.setText(decimalP);
        String ticket =ventas.getTicketp();
        String[] split2 = ticket .split("\\.");
        String enteroT = split2[0];
        String decimalT = split2[1];
        tvMontoEnteroTicketp.setText(enteroT);
        tvMontoDecimalTicketp.setText(decimalT);
    }

    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(yesterday());
    }
    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    @Override
    public void failGetResumenDia(String mensaje) {

    }

    @Override
    public void succedGetSaldo(String mensaje) {

    }

    @Override
    public void failGetSaldo(String mensaje) {

    }

    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void backScreen(String event, Object data) {

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

    }
}
