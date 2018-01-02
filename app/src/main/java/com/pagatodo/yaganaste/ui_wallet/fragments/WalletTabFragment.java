package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.AbstractAdEmFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.BlankFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.HomeTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsFragment;
import com.pagatodo.yaganaste.ui_wallet.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.CardWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.holders.ButtonsViewHolder;
import com.pagatodo.yaganaste.ui_wallet.interactors.WalletInteractor;
import com.pagatodo.yaganaste.ui_wallet.interactors.WalletInteractorImpl;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ElementView;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WlletNotifaction;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.ui_wallet.presenter.WalletPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.WalletPresenterImpl;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.ui_wallet.views.WalletView;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.GridLayout.VERTICAL;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;

/**
 *
 */
public class WalletTabFragment extends SupportFragment implements WalletView, ElementsWalletAdpater.OnItemClickListener,
        WlletNotifaction{

    public static final String ID_OPERATION = "ID_OPERATION";
    @BindView(R.id.progressGIF)
    ProgressLayout progressLayout;
    @BindView(R.id.viewpager_wallet)
    ViewPager viewPagerWallet;
    @BindView(R.id.rcv_elements)
    RecyclerView rcvOpciones;
    @BindView(R.id.txt_monto)
    StyleTextView txtSaldo;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout pager_indicator;
    @BindView(R.id.tipo_saldo)
    StyleTextView tipoSaldo;


    private WalletPresenter walletPresenter;
    private CardWalletAdpater cardWalletAdpater;
    protected OnEventListener onEventListener;

    private int dotsCount;
    private ImageView[] dots;
    private int previous_pos = 0;
    private int pageCurrent = 0;

    public static WalletTabFragment newInstance() {
        return new WalletTabFragment();
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
        walletPresenter = new WalletPresenterImpl(this);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_main, container, false);
        ButterKnife.bind(this,view);
        walletPresenter.getWalletsCards();

        return view;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void setError() {
        Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void completed() {



        progressLayout.setVisibility(View.GONE);
        cardWalletAdpater = new CardWalletAdpater();
        cardWalletAdpater.addCardItem(new ElementWallet().getCardyaganaste(getContext()));
        //cardWalletAdpater.addCardItem(new ElementWallet().getCardStarBucks(getContext()));
        if (SingletonUser.getInstance().getDataUser().isEsAgente() && SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == Recursos.CRM_DOCTO_APROBADO){
           cardWalletAdpater.addCardItem(new ElementWallet().getCardLectorAdq(getContext()));
        } else {
            cardWalletAdpater.addCardItem(new ElementWallet().getCardLectorEmi(getContext()));
        }


        viewPagerWallet.setAdapter(cardWalletAdpater);
        //viewPagerWallet.setPageTransformer(true, new ZoomOutPageTransformer(true));
        viewPagerWallet.setCurrentItem(0);
        viewPagerWallet.setOffscreenPageLimit(3);

        viewPagerWallet.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_selected_dot_wallet));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selected_dot_wallet));


                int pos = position + 1;

                if(pos == dotsCount && previous_pos == (dotsCount - 1)) {


                }
                else if(pos==(dotsCount-1)&&previous_pos == dotsCount) {


                }

                previous_pos = pos;

                updateOperations(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setUiPageViewController();

        GridLayoutManager llm = new GridLayoutManager(getContext(),3);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        rcvOpciones.addItemDecoration(itemDecoration);
        rcvOpciones.setLayoutManager(llm);
        updateOperations(0);
    }

    private void setUiPageViewController() {

        dotsCount = cardWalletAdpater.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_selected_dot_wallet));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(22, 0, 22, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selected_dot_wallet));
    }

    private void updateOperations(int psition){
        pageCurrent = psition;
        rcvOpciones.setAdapter(new ElementsWalletAdpater(getContext(),cardWalletAdpater.getElementWallet(psition),this));
        txtSaldo.setText(cardWalletAdpater.getElemenWallet(psition).getSaldo());
        tipoSaldo.setText(cardWalletAdpater.getElemenWallet(psition).getTipoSaldo());
    }

    @Override
    public void onItemClick(ElementView elementView) {
        Intent intent = new Intent(getContext(), WalletMainActivity.class);

        intent.putExtra(ID_OPERATION,elementView.getIdOperacion());
        intent.putExtra("CURRENT_PAGE",pageCurrent);
        startActivity(intent);
        /*
        switch (elementView.getIdOperacion()) {
            case 1:

                intent.putExtra("CURRENT_PAGE",pageCurrent);
                startActivity(intent);


                break;
            case 2:
                startActivity(intent);
                break;
            case 7:
                intent.putExtra("CURRENT_PAGE",pageCurrent);
                startActivity(intent);
                break;
            default:
                Toast.makeText(getContext(),"Proximamente",Toast.LENGTH_SHORT).show();
                break;

        }*/

    }

    @Override
    public void onFailed(int errorCode, int action, String error) {

    }

    @Override
    public void onSuccess() {

    }
}
