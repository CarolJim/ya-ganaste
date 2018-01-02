package com.pagatodo.yaganaste.ui_wallet;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.maintabs.fragments.HomeTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment.ID_OPERATION;

public class WalletMainActivity extends SupportFragmentActivity implements View.OnClickListener{

    @BindView(R.id.btn_back)
    AppCompatImageView back;

    private int idOperation;
    private int currentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_main);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            if (getIntent().getExtras() != null){
                currentPage = getIntent().getIntExtra("CURRENT_PAGE",0);
                idOperation = getIntent().getIntExtra(ID_OPERATION,0);
            }

            getLoadFragment(idOperation);

            //loadFragment(MovementsGenericFragment.newInstance(), R.id.fragment_container); ;
        }

    }

    private void getLoadFragment(int idoperation){
        switch (idoperation){
            case 1:
                loadFragment(HomeTabFragment.newInstance(currentPage),R.id.fragment_container);
                break;
            case 2:
                loadFragment(DepositsFragment.newInstance(),R.id.fragment_container);
                break;
            case 7:
                /*
        Idestatus = SingletonUser.getInstance().getDataUser().getIdEstatus();
        if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && SingletonUser.getInstance().getDataUser().getEstatusAgente() == CRM_DOCTO_APROBADO) {
            fragmentList.add(GetMountFragment.newInstance());
        }else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && Idestatus == IdEstatus.I6.getId() && SingletonUser.getInstance().getDataUser().getEstatusAgente() == STATUS_DOCTO_PENDIENTE) {
           // fragmentList.add(DocumentsContainerFragment.newInstance());
            fragmentList.add(InviteAdquirenteFragment.newInstance());
        } else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && Idestatus == IdEstatus.I6.getId()) {
            //fragmentList.add(DocumentsContainerFragment.newInstance());
         //   fragmentList.add(StatusRegisterAdquirienteFragment.newInstance());
            fragmentList.add(InviteAdquirenteFragment.newInstance());
        } else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && Idestatus == IdEstatus.I7.getId()) {
            //fragmentList.add(DocumentsContainerFragment.newInstance());
            fragmentList.add(StatusRegisterAdquirienteFragment.newInstance());
        } else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && Idestatus == IdEstatus.I8.getId()) {
            //fragmentList.add(DocumentsContainerFragment.newInstance());
            fragmentList.add(StatusRegisterAdquirienteFragment.newInstance());
        } else if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                   Idestatus == IdEstatus.I9.getId()) {
            //fragmentList.add(DocumentsContainerFragment.newInstance());
            fragmentList.add(StatusRegisterAdquirienteFragment.newInstance());
        } else if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                Idestatus == IdEstatus.I11.getId()) {
            //fragmentList.add(DocumentsContainerFragment.newInstance());
            fragmentList.add(StatusRegisterAdquirienteFragment.newInstance());
        } else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && SingletonUser.getInstance().getDataUser().getEstatusAgente() == STATUS_DOCTO_PENDIENTE) {
            fragmentList.add(StatusRegisterAdquirienteFragment.newInstance());
        } else {
            fragmentList.add(InviteAdquirenteFragment.newInstance());
        }
        */
                break;

            default:
                Toast.makeText(this,"Error Interno",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean requiresTimer() {

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
