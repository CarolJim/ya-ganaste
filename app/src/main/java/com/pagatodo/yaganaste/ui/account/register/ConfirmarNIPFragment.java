package com.pagatodo.yaganaste.ui.account.register;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAccountCardNIPView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.ICropper;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.utils.AsignarNipTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.customviews.BorderTitleLayout;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class ConfirmarNIPFragment extends GenericFragment implements View.OnClickListener,
        ValidationForms, IAccountCardNIPView,IListaOpcionesView, ICropper {

    public static String PIN_TO_CONFIRM = "PIN_TO_CONFIRM";
    private static int PIN_LENGHT = 4;
    @BindView(R.id.borderLayout)
    BorderTitleLayout borderTitleLayout;
    @BindView(R.id.nip)
    EditText edtPin;
    @BindView(R.id.confim_nip)
    EditText edtPinconfirm;


    @BindView(R.id.btnNextAsignarPin)
    Button btnNextAsignarPin;

    @BindView(R.id.btnNextPersonalInfo)
    Button btnNextPersonalInfo;
    @BindView(R.id.frag_lista_opciones_photo_item)
    CircleImageView iv_photo_item;

    CameraManager cameraManager;

    @BindView(R.id.progressIndicator)
    ProgressLayout progressLayout;

    LinearLayout layout_control;
    TextView tv1Num;
    TextView tv2Num;
    TextView tv3Num;
    TextView tv4Num;
    private View rootview;
    private String nip = "";
    private String nipToConfirm = "";
    private AccountPresenterNew accountPresenter;
    ImageView imageView;
    public ConfirmarNIPFragment() {
    }

    public static ConfirmarNIPFragment newInstance(String nip) {
        ConfirmarNIPFragment fragmentRegister = new ConfirmarNIPFragment();
        Bundle args = new Bundle();
        args.putString(PIN_TO_CONFIRM, nip);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
        imageView = (ImageView)getActivity().findViewById(R.id.btn_back);
        //accountPresenter = new AccountPresenterNew(getActivity(),this);

        cameraManager = new CameraManager(this);
        cameraManager.initCamera(getActivity(), iv_photo_item, this);


    }

    @Override
    public void onResume() {
        super.onResume();
        imageView.setVisibility(VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_asignar_nip, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnNextAsignarPin.setOnClickListener(this);
        btnNextPersonalInfo.setOnClickListener(this);
        layout_control = (LinearLayout) rootview.findViewById(R.id.asignar_control_layout);
        tv1Num = (TextView) rootview.findViewById(R.id.asignar_tv1);
        tv2Num = (TextView) rootview.findViewById(R.id.asignar_tv2);
        tv3Num = (TextView) rootview.findViewById(R.id.asignar_tv3);
        tv4Num = (TextView) rootview.findViewById(R.id.asignar_tv4);
        borderTitleLayout.setTitle(getString(R.string.confirma_pin));
        imageView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNextPersonalInfo:
                validateForm();
                break;
            default:
                break;
        }
    }

    /*Implementación de ValidationForms*/

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        getDataForm();

        if (nip.length() < PIN_LENGHT) {
            showValidationError(getString(R.string.asignar_pin));
            return;
        }

        if (!nip.equals(nipToConfirm)) {
            showValidationError(getString(R.string.confirmar_pin));
            return;
        }

        onValidationSuccess();
    }

    private void showValidationError(Object err) {
        showValidationError(0, err);
    }

    @Override
    public void showValidationError(int id, Object error) {
        //UI.showToastShort(error.toString(), getActivity());
        UI.createSimpleCustomDialog("", error.toString(), getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {

                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        RegisterUser registerUser = RegisterUser.getInstance();

        if (!registerUser.getPerfirlfoto().toString().isEmpty()){
            cameraManager.setCropImageselfie(registerUser.getPerfirlfoto());
        }else {
            accountPresenter.assignNIP(nip);
        }

    }

    @Override
    public void getDataForm() {
        nip = edtPin.getText().toString();
        nipToConfirm=edtPinconfirm.getText().toString();
    }

    @Override
    public void nextScreen(String event, Object data) {
        showLoader("");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                hideLoader();
                if (SingletonUser.getInstance().getDataUser().isRequiereActivacionSMS()) {
                    onEventListener.onEvent(EVENT_GO_ASOCIATE_PHONE, null);//Mostramos la siguiente pantalla SMS.
                } else {
                    onEventListener.onEvent(EVENT_GO_MAINTAB, null);
                }
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    @Override
    public void backScreen(String event, Object data) {
       // onEventListener.onEvent(event, data);
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
       // UI.showToastShort(error.toString(), getActivity());
        showValidationError(0, error.toString());

    }

    public boolean isCustomKeyboardVisible() {
        return false;
    }

    private void buttonIsVisible(boolean isVisible) {
        btnNextAsignarPin.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void hideKeyboard() {
        //keyboardView.hideCustomKeyboard();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void setPhotoToService(Bitmap bitmap) {
        cameraManager.setBitmap(bitmap);

        // Procesamos el Bitmap a Base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // Creamos el objeto ActualizarAvatarRequest
        ActualizarAvatarRequest avatarRequest = new ActualizarAvatarRequest(encoded, "png");


        onEventListener.onEvent("DISABLE_BACK", true);

        // Enviamos al presenter
        accountPresenter.sendPresenterActualizarAvatar(avatarRequest);

    }

    @Override
    public void showProgress(String mMensaje) {
        showLoader("");

    }

    @Override
    public void showExceptionToView(String mMesage) {

    }

    @Override
    public void sendSuccessAvatarToView(String mensaje) {
        hideLoader();
        accountPresenter.assignNIP(nip);

    }

    @Override
    public void sendErrorAvatarToView(String mensaje) {
        hideLoader();
        accountPresenter.assignNIP(nip);
    }

    @Override
    public void setLogOutSession() {

    }

    @Override
    public void onCropper(Uri uri) {

    }

    @Override
    public void onHideProgress() {

    }

    @Override
    public void failLoadJpg() {

    }
}