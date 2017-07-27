package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.maintabs.managers.DepositsManager;
import com.pagatodo.yaganaste.utils.FontCache;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.StringUtils.getCreditCardFormat;

/**
 * Created by Jordan on 17/05/2017.
 */

public class DepositsCupoDataFragment extends SupportFragment implements View.OnClickListener {
    DepositsManager depositsManager;

    private TextView txtNameTitular;
    private TextView txtNumberAgent;
    private TextView txtNumAfil;
    private Button btnDepositar;

    private View rootView;

    public static DepositsCupoDataFragment newInstance() {
        DepositsCupoDataFragment depositsDataFragment = new DepositsCupoDataFragment();
        Bundle args = new Bundle();
        depositsDataFragment.setArguments(args);
        return depositsDataFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depositsManager = ((DepositsFragment) getParentFragment()).getDepositManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onEventListener.onEvent(ToolBarActivity.EVENT_CHANGE_TOOLBAR_VISIBILITY, true);
        return inflater.inflate(R.layout.fragment_deposito_datos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
        UsuarioClienteResponse usuario = SingletonUser.getInstance().getDataUser().getUsuario();
        txtNameTitular.setText(usuario.getNombre() + " " + usuario.getPrimerApellido() + " " + usuario.getSegundoApellido());
        // TODO: 26/07/2017 Verificar si es este numero agente y ver de donde se saca el numero de afiliacion 
        txtNumberAgent.setText(usuario.getNumeroAgente());
        txtNumAfil.setText(usuario.getClaveAgente());
    }

    @Override
    public void initViews() {
        txtNameTitular = (TextView)rootView.findViewById(R.id.txt_name_titular);
        txtNumberAgent = (TextView)rootView.findViewById(R.id.txt_number_agent);
        txtNumAfil = (TextView)rootView.findViewById(R.id.txt_num_afil);
        btnDepositar = (Button)rootView.findViewById(R.id.btn_depositar);
        btnDepositar.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDepositar) {
            depositsManager.onTapButton();
        }
    }


}
