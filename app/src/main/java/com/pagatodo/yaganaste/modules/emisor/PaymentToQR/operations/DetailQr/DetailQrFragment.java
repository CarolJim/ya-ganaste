package com.pagatodo.yaganaste.modules.emisor.PaymentToQR.operations.DetailQr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.components.FeetQrDetail;
import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.modules.emisor.PaymentToQR.QrManagerContracts;
import com.pagatodo.yaganaste.modules.emisor.PaymentToQR.QrManagerIteractor;
import com.pagatodo.yaganaste.modules.emisor.PaymentToQR.operations.QrOperationActivity;
import com.pagatodo.yaganaste.modules.emisor.PaymentToQR.utils.ImageProcessing;
import com.pagatodo.yaganaste.modules.emisor.PaymentToQR.utils.ManagerQr;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailQrFragment extends GenericFragment implements View.OnClickListener, QrManagerContracts.Listener {

    private static String TAG_QR_ITEM = "TAG_QR_ITEM";
    private View rootView;
    private QrOperationActivity activity;
    private QrItems item;
    private ImageView delQR;

    @BindView(R.id.btn_edit)
    ImageView btnEdit;
    @BindView(R.id.feet_qr_detail)
    FeetQrDetail feetQrDetail;
    @BindView(R.id.text_alias)
    StyleTextView textAlias;
    @BindView(R.id.share_btn)
    StyleButton shareBtn;
    @BindView(R.id.qr_view)
    LinearLayout QrView;
    @BindView(R.id.qr_code)
    ImageView qrCode;
    public static int MILISEGUNDOS_ESPERA = 1500;
    private ImageView del_qrs;
    private QrManagerIteractor iteractor;


    public static DetailQrFragment newInstance(QrItems item) {
        DetailQrFragment fragment = new DetailQrFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_QR_ITEM, item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (QrOperationActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        iteractor = new QrManagerIteractor(this, getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.detail_qr_fragment, container, false);
        del_qrs = (ImageView) getActivity().findViewById(R.id.delete_qr);
        initViews();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.menu_qr_delete, menu);
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            item = (QrItems) getArguments().getSerializable(TAG_QR_ITEM);
            assert item != null;
            Log.d("QR_ITEM", item.getQrUser().getAlias());
            textAlias.setText(item.getQrUser().getAlias());
            qrCode.setImageBitmap(ManagerQr.showQRCode(item.getJsonQr()));
            feetQrDetail.setPlate(item.getQrUser().getPlate());
            btnEdit.setOnClickListener(this);
            shareBtn.setOnClickListener(this);

        }
        del_qrs.setOnClickListener(this::onClick);
        showDeleteQR(true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_edit:
                if (item != null) {
                    activity.getRouter().showEditQr(Direction.NONE, item);
                }
                break;
            case R.id.share_btn:
                Bitmap returnedBitmap = Bitmap.createBitmap(QrView.getWidth(), QrView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(returnedBitmap);
                canvas.drawColor(Color.WHITE);
                QrView.draw(canvas);
                ImageProcessing.onShareItem(getActivity(), returnedBitmap);
                break;
            case R.id.delete_qr:
                item.getQrUser().getPlate();
                iteractor.delQR(item);
                this.activity.showLoader("");
                break;
        }
    }

    public void generateQR() {

    }

    private void showDeleteQR(boolean show) {
        if (show) {
            del_qrs.setVisibility(View.VISIBLE);
        } else {
            del_qrs.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccessQRs(ArrayList<QrItems> listQRs) {

    }

    @Override
    public void onErrorQRs() {

    }

    @Override
    public void onSuccesDel() {
        this.activity.hideLoader();
        UI.showSuccessSnackBar(getActivity(), getString(R.string.qr_delete_succes), Snackbar.LENGTH_SHORT);
        esperarYCerrar(MILISEGUNDOS_ESPERA);
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

}
