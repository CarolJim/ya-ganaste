package com.pagatodo.yaganaste.utils.customviews;

/**
 * Created by flima on 07/02/17.
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;

import java.lang.reflect.Field;


public class CustomErrorDialog extends DialogFragment implements ViewTreeObserver.OnGlobalLayoutListener {

    public static final String TAG = "ActionsDialog";
    public static final String KEY_LAYOUT_NOTIFICATION = "KEY_LAYOUT_NOTIFICATION";
    public static final String KEY_CONFIRM_TITLE = "KEY_CONFIRM_TITLE";
    public static final String KEY_MESSAGE_NOTIFICATION = "KEY_MESSAGE_NOTIFICATION";
    public static final String KEY_MESSAGE_NOMBRE = "KEY_MESSAGE_NOMBRE";
    public static final String KEY_MESSAGE_NUMBER = "KEY_MESSAGE_NUMBER";
    public static final String KEY_SHOW_BTN_CONFIRM = "KEY_SHOW_BTN_CONFIRM";
    public static final String KEY_SHOW_BTN_CANCEL = "KEY_SHOW_BTN_CANCEL";

    private int idLayoutDialog;
    private String titleMessage = "";
    private String messageNotification = "";
    private String titleBtnAcept = "";
    private String titleBtnCancel = "";
    private String my_card_name_user = "";
    private String my_card_number_user = "";


    private DialogDoubleActions dialogActions;


    private boolean showConfirmButton = true;
    private boolean showCancelButton = true;

    private LinearLayout buttonsContainer;

    /**
     * Método para obtener una instancia del dialog personalizado
     *
     * @param idLayout            int id del layout del dialog
     * @param messageNotification String mensaje que contiene el dialog
     * @param hasConfirmBtn       boolean de confirmación del dialog
     * @param hasCancelBtn        boolean de cancelación del dialog
     * @return {@link DialogDoubleActions} instancia del dialog
     */
    public static CustomErrorDialog getInstance(@LayoutRes int idLayout, String titleNotification, String messageNotification,
                                                boolean hasConfirmBtn,String nombre,String number, boolean hasCancelBtn) {

        CustomErrorDialog actionsDialog = new CustomErrorDialog();
        Bundle args = new Bundle();
        args.putInt(CustomErrorDialog.KEY_LAYOUT_NOTIFICATION, idLayout);
        args.putString(CustomErrorDialog.KEY_CONFIRM_TITLE, titleNotification);
        args.putString(CustomErrorDialog.KEY_MESSAGE_NOTIFICATION, messageNotification);
        args.putBoolean(CustomErrorDialog.KEY_SHOW_BTN_CONFIRM, hasConfirmBtn);
        args.putBoolean(CustomErrorDialog.KEY_SHOW_BTN_CANCEL, hasCancelBtn);
        args.putString(CustomErrorDialog.KEY_MESSAGE_NOMBRE, nombre);
        args.putString(CustomErrorDialog.KEY_MESSAGE_NUMBER, number);
        actionsDialog.setArguments(args);

        return actionsDialog;
    }

    public static CustomErrorDialog getInstance(@LayoutRes int idLayout, String titleNotification, String messageNotification,
                                                boolean hasConfirmBtn, boolean hasCancelBtn) {

        CustomErrorDialog actionsDialog = new CustomErrorDialog();
        Bundle args = new Bundle();
        args.putInt(CustomErrorDialog.KEY_LAYOUT_NOTIFICATION, idLayout);
        args.putString(CustomErrorDialog.KEY_CONFIRM_TITLE, titleNotification);
        args.putString(CustomErrorDialog.KEY_MESSAGE_NOTIFICATION, messageNotification);
        args.putBoolean(CustomErrorDialog.KEY_SHOW_BTN_CONFIRM, hasConfirmBtn);
        args.putBoolean(CustomErrorDialog.KEY_SHOW_BTN_CANCEL, hasCancelBtn);
        actionsDialog.setArguments(args);

        return actionsDialog;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(idLayoutDialog, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        //setStyle();
        //getDialog() (getActivity(), R.style.DialogFragment);
        /**Creamos el {@link StyleButton} de confirmación, si se recibe título como parámetro, lo seteamos.*/
        final StyleButton btnConfirmNotification = (StyleButton) rootView.findViewById(R.id.btnConfirmDialog);
        final StyleButton btnCancelNotification = (StyleButton) rootView.findViewById(R.id.btnCancelDialog);

        /*Seteamos visibilidad de botones*/
        if (btnConfirmNotification != null) {
            btnConfirmNotification.setVisibility(showConfirmButton ? View.VISIBLE : View.GONE);
            btnConfirmNotification.setText(!titleBtnAcept.isEmpty() ? titleBtnAcept : getString(R.string.title_aceptar));
        }
        if (btnCancelNotification != null) {
            btnCancelNotification.setVisibility(showCancelButton ? View.VISIBLE : View.GONE);
            btnCancelNotification.setText(!titleBtnCancel.isEmpty() ? titleBtnCancel : getString(R.string.title_cancelar));
        }

        /**Creamos el {@link StyleTextView} para el mensaje si existe mensaje dinámico.*/
        if (!titleMessage.isEmpty()) {
            StyleTextView txtTitleNotification = (StyleTextView) rootView.findViewById(R.id.txtTitleNotification);
            txtTitleNotification.setText(titleMessage);
        }

        if (!my_card_name_user.isEmpty()) {
            StyleTextView txtTitleNotification = (StyleTextView) rootView.findViewById(R.id.my_card_name_user);
            txtTitleNotification.setText(my_card_name_user);
        }
        if (!my_card_number_user.isEmpty()) {
            StyleTextView txtTitleNotification = (StyleTextView) rootView.findViewById(R.id.my_card_num_cuenta);
            txtTitleNotification.setText(my_card_number_user);
        }



        /**Creamos el {@link StyleTextView} para el mensaje si existe mensaje dinámico.*/
        if (!messageNotification.isEmpty()) {
            StyleTextView txtMessageNotification = (StyleTextView) rootView.findViewById(R.id.txtMessageNotification);
            txtMessageNotification.setText(messageNotification);
        }

        if (showConfirmButton && dialogActions != null) {
            btnConfirmNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogActions.actionConfirm();
                    dismiss();
                }
            });
        }

        if (showConfirmButton && dialogActions != null) {
            btnCancelNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogActions.actionCancel();
                    dismiss();
                }
            });
        }

        buttonsContainer = (LinearLayout) rootView.findViewById(R.id.buttons_container);
        if (buttonsContainer != null) {
            buttonsContainer.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle arg = getArguments();
            idLayoutDialog = arg.getInt(CustomErrorDialog.KEY_LAYOUT_NOTIFICATION);
            titleMessage = arg.getString(CustomErrorDialog.KEY_CONFIRM_TITLE, "");
            messageNotification = arg.getString(CustomErrorDialog.KEY_MESSAGE_NOTIFICATION, "");
            showConfirmButton = arg.getBoolean(CustomErrorDialog.KEY_SHOW_BTN_CONFIRM, true);
            showCancelButton = arg.getBoolean(CustomErrorDialog.KEY_SHOW_BTN_CANCEL, true);
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        //dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_iset);

        return dialog;
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            Field dismissed = getClass().getSuperclass().getDeclaredField("mDismissed");
            Field shown = getClass().getSuperclass().getDeclaredField("mShownByMe");
            shown.setAccessible(true);
            dismissed.setAccessible(true);

            dismissed.set(this, false);
            shown.set(this, true);

            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para setear el {@link DialogDoubleActions} del dialog
     */
    public void setDialogActions(DialogDoubleActions dialogActions) {
        this.dialogActions = dialogActions;
    }

    public void setTitleBtnAcept(String titleBtnAcept) {
        this.titleBtnAcept = titleBtnAcept;
    }

    public void setTitleMessageNotification(String message) {
        this.messageNotification = message;
    }

    public void myCardNameUser(String nombreusuario) {
        this.my_card_name_user = nombreusuario;
    }
    public void myCardNumberUser(String numero) {
        this.my_card_number_user = numero;
    }


    public void setTitleBtnCancel(String titleBtnCancel) {
        this.titleBtnCancel = titleBtnCancel;
    }

    @Override
    public void onGlobalLayout() {
        buttonsContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }


}



