package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.maintabs.adapters.FragmentPagerAdapter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsTabPresenter;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.NoSwipeViewPager;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB1;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB2;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB3;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.EVENT_HIDE_MANIN_TAB;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.EVENT_SHOW_MAIN_TAB;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;

/**
 * Created by Jordan on 06/04/2017.
 */

public class PaymentsTabFragment extends SupportFragment implements View.OnClickListener,
        View.OnDragListener {

    public boolean isOnForm = false;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.payment_view_pager)
    NoSwipeViewPager payment_view_pager;
    @BindView(R.id.tab_recargas)
    Button botonRecargas;
    @BindView(R.id.tab_servicios)
    Button botonServicios;
    @BindView(R.id.tab_envios)
    Button botonEnvios;
    @BindView(R.id.rlimgPagosServiceToPay)
    RelativeLayout rlimgPagosServiceToPay;
    @BindView(R.id.imgPagosServiceToPay)
    ImageView imgPagosServiceToPay;
    @BindView(R.id.imgPagosServiceToPayRound)
    CircleImageView imgPagosServiceToPayRound;
    @BindView(R.id.txtPagosUserName)
    TextView txtPagosUserName;
    @BindView(R.id.txtPagosYourBalance)
    TextView txtBalance;
    @BindView(R.id.imgPagosUserProfile)
    ImageView imgPagosUserProfile;

    //@BindView(R.id.txtPagosYourBalanceNumber)
    //MontoTextView txtBalance;

    MovementsTab currentTab = TAB1;
    SingletonUser singletonUser;
    private View rootView;
    //private Animation animIn, animOut;
    private PaymentsTabPresenter paymentsTabPresenter;
    private FragmentPagerAdapter viewPAgerAdapter;

    public static PaymentsTabFragment newInstance() {
        PaymentsTabFragment paymentsTabFragment = new PaymentsTabFragment();

        Bundle args = new Bundle();
        paymentsTabFragment.setArguments(args);
        return paymentsTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentsTabPresenter = new PaymentsTabPresenter();
        viewPAgerAdapter = new FragmentPagerAdapter(getChildFragmentManager());
        /*animIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_left);
        animOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_to_right);
        animIn.setDuration(1000);
        animOut.setDuration(1000);*/
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(getContext()).load(SingletonUser.getInstance().getDataUser().getUsuario().getImagenAvatarURL()).placeholder(R.mipmap.icon_user).error(R.mipmap.icon_user).dontAnimate().into(imgPagosUserProfile);
    }

    public PaymentsTabPresenter getPresenter() {
        return this.paymentsTabPresenter;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        singletonUser = SingletonUser.getInstance();
        return inflater.inflate(R.layout.fragment_child_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        UsuarioClienteResponse userData = SingletonUser.getInstance().getDataUser().getUsuario();
        payment_view_pager.setAdapter(viewPAgerAdapter);
        payment_view_pager.setCurrentItem(0);
        bringViewToFront((RelativeLayout) botonRecargas.getParent(), botonRecargas.getId());

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int widthButton = dm.widthPixels / 3;

        botonRecargas.setWidth(widthButton);
        botonServicios.setWidth(widthButton);
        botonEnvios.setWidth(widthButton);

        botonRecargas.setOnClickListener(this);
        botonServicios.setOnClickListener(this);
        botonEnvios.setOnClickListener(this);

        rlimgPagosServiceToPay.setOnDragListener(this);
       // txtPagosUserName.setText(StringUtils.getFirstName(SingletonUser.getInstance().getDataUser().getUsuario().getNombre()));
        txtPagosUserName.setText(StringUtils.getFirstName(SingletonUser.getInstance().getDataUser().getUsuario().getNombre())+" "+ userData.getPrimerApellido());
        txtBalance.setText(getString(R.string.your_balance).concat(StringUtils.getCurrencyValue(singletonUser.getDatosSaldo().getSaldoEmisor())));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_recargas:
                changeTab(v, TAB1);
                break;
            case R.id.tab_servicios:
                changeTab(v, TAB2);
                break;
            case R.id.tab_envios:
                changeTab(v, TAB3);
                break;
            default:
                break;
        }
    }

    public void changeTab(View v, MovementsTab TAB) {
        if (currentTab != TAB) {
            tabSelector(v, TAB);
        }
    }

    private void tabSelector(View v, MovementsTab TAB) {
        isOnForm = false;
        currentTab = TAB;
        changeBackTabs(v, TAB);
        setTab(TAB);
    }

    public void onBackPresed(MovementsTab TAB) {
        if (isOnForm) {
            setTab(TAB);
            isOnForm = false;
        }
    }

    private void setTab(MovementsTab TAB) {
        payment_view_pager.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
        payment_view_pager.setCurrentItem(TAB.getId() - 1);
        removeLastFragment();
        onEventListener.onEvent(EVENT_SHOW_MAIN_TAB, true);
        imgPagosServiceToPayRound.setBorderColor(Color.BLACK);
        imgPagosServiceToPay.setImageResource(R.mipmap.circulo_add_servicio);
    }

    private void changeBackTabs(View v, MovementsTab TAB) {
        botonRecargas.setBackground(ContextCompat.getDrawable(getContext(),
                TAB == TAB1 ? R.drawable.tab_selected
                        : TAB == TAB2 ? R.drawable.left_tab
                        : TAB == MovementsTab.TAB3 ? R.drawable.tab_unselected
                        : 0));
        botonServicios.setBackground(ContextCompat.getDrawable(getContext(),
                TAB == TAB1 ? R.drawable.right_tab
                        : TAB == TAB2 ? R.drawable.tab_selected
                        : TAB == MovementsTab.TAB3 ? R.drawable.left_tab
                        : 0));
        botonEnvios.setBackground(ContextCompat.getDrawable(getContext(),
                TAB == TAB1 ? R.drawable.tab_unselected
                        : TAB == TAB2 ? R.drawable.right_tab
                        : TAB == MovementsTab.TAB3 ? R.drawable.tab_selected
                        : 0));
        bringViewToFront((RelativeLayout) v.getParent(), v.getId());
    }

    public void bringViewToFront(RelativeLayout parent, int id) {
        int childPosition;

        for (childPosition = 0; childPosition < parent.getChildCount() - 1; childPosition++) {
            if (parent.getChildAt(childPosition).getId() == id) {
                break;
            }
        }

        parent.getChildAt(childPosition).bringToFront();
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {

        if (event.getAction() == DragEvent.ACTION_DROP) {
            Log.i(getTag(), String.valueOf(v.getId()));
            onItemSelected();
        }
        return true;
    }

    public void onItemSelected() {
        changeImgageToPay();
        openPaymentFragment();
    }

    public void changeImgageToPay() {
        CarouselItem item = paymentsTabPresenter.getCarouselItem();
        Glide.with(getContext()).load(item.getImageUrl()).dontAnimate().into(imgPagosServiceToPay);
        imgPagosServiceToPayRound.setBorderColor(Color.parseColor(item.getColor()));
        //onEventListener.onEvent(TabActivity.EVENT_CHANGE_MAIN_TAB_VISIBILITY, false);
    }

    public void openPaymentFragment() {
        //payment_view_pager.startAnimation(animOut);
        payment_view_pager.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        //container.startAnimation(animIn);
        isOnForm = true;
        switch (currentTab) {
            case TAB1:
                loadFragment(RecargasFormFragment.newInstance(), Direction.NONE, false);
                break;
            case TAB2:
                loadFragment(ServiciosFormFragment.newInstance(), Direction.NONE, false);
                break;
            case TAB3:
                loadFragment(EnviosFormFragment.newInstance(), Direction.NONE, false);
                break;
        }
        onEventListener.onEvent(EVENT_HIDE_MANIN_TAB, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = getChildFragmentManager().getFragments();

        if (fragmentList != null) {
            if (requestCode == CONTACTS_CONTRACT) {
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof RecargasFormFragment
                            || fragment instanceof EnviosFormFragment) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                        break;

                    }
                }
            } else if (requestCode == BARCODE_READER_REQUEST_CODE) {
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof ServiciosFormFragment) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                        break;
                    }
                }
            }
        }

    }

    public MovementsTab getCurrenTab() {
        return currentTab;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            Double saldo = Double.parseDouble(singletonUser.getDatosSaldo().getSaldoEmisor());
            /*if (singletonUser.getDataUser().isEsAgente() && singletonUser.getDataUser().getEstatusDocumentacion() == Recursos.CRM_DOCTO_APROBADO) {
                saldo = Double.parseDouble(singletonUser.getDatosSaldo().getSaldoAdq());
            } else {
                saldo = Double.parseDouble(singletonUser.getDatosSaldo().getSaldoEmisor());
            }*/
            txtBalance.setText(getString(R.string.your_balance) + StringUtils.getCurrencyValue(saldo));
        }
    }
}
