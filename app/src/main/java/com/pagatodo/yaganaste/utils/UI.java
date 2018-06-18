package com.pagatodo.yaganaste.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;

public class UI {

    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToast(@StringRes int message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static AlertDialog.Builder showAlertDialog(String message, String button, Context context, final DialogDoubleActions dialogActions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_DialogPrueba);

        builder.setMessage(message)
                .setCancelable(false)
                .setNeutralButton(button, (dialog, id) -> {
                    dialog.cancel();
                    if (dialogActions != null) dialogActions.actionConfirm();
                });
        return builder;
    }

    public static AlertDialog.Builder showAlertDialog2(String title, String message, String button, Context context, final DialogDoubleActions dialogActions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_DialogPrueba);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNeutralButton(button, (dialog, id) -> {
                    dialog.cancel();
                    if (dialogActions != null) dialogActions.actionConfirm();
                });
        return builder;
    }

    public static AlertDialog.Builder showAlertDialogGps(String title, String message, String button, String buttonCancel, Context context, final DialogDoubleActions dialogActions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_DialogPrueba);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton(buttonCancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton(button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (dialogActions != null) dialogActions.actionConfirm();
                    }
                });
        return builder;
    }


    public static AlertDialog.Builder showAlertDialog3Button(String title, String message, String buttonOk, String buttonCancel, String ButtonNeutral, Context context, final DialogDoubleActions dialogActions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_DialogPrueba);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton(buttonCancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton(ButtonNeutral, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        // if (o != null) o.action();
                    }
                })
                .setPositiveButton(buttonOk, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder;
    }


    public static AlertDialog.Builder showAlertDialogCancel(String message, String buttonOk, String buttonCancel, Context context, final DialogDoubleActions dialogActions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_DialogPrueba);
        builder.setMessage(message)
                .setCancelable(true)
                .setNegativeButton(buttonCancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialogActions != null) dialogActions.actionCancel();

                    }
                })
                .setPositiveButton(buttonOk, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialogActions != null) dialogActions.actionConfirm();
                    }
                });

        return builder;
    }


    /**
     * Keyboard
     **/
    public static void hideKeyBoard(Activity activity) {

        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void createSimpleCustomDialog(String title, String message, FragmentManager fragmentManager, String tag) {
        final CustomErrorDialog customErrorDialog = CustomErrorDialog.getInstance(R.layout.dialog_custom_curp_message, title, message, true, false);
        customErrorDialog.setDialogActions(new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                customErrorDialog.dismiss();
            }

            @Override
            public void actionCancel(Object... params) {

            }
        });
        customErrorDialog.show(fragmentManager, tag);
    }

    public static void SimpleCustomDialogCURP(String title, String message, FragmentManager fragmentManager, String tag) {
        final CustomErrorDialog customErrorDialog = CustomErrorDialog.getInstance(R.layout.dialog_custom_curp_error_message, title, message, true, false);
        customErrorDialog.setDialogActions(new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                customErrorDialog.dismiss();
            }

            @Override
            public void actionCancel(Object... params) {

            }
        });
        customErrorDialog.show(fragmentManager, tag);
    }



    public static void createCustomDialogSMS(String title, String message, FragmentManager fragmentManager, String tag,
                                             DialogDoubleActions actions, String btnAceptar, String btnCancelar) {
        final CustomErrorDialog customErrorDialog = CustomErrorDialog.getInstance(R.layout.dialog_custom_curp_message,
                title, message, true, true);

        customErrorDialog.setTitleBtnAcept(btnAceptar);
        customErrorDialog.setTitleBtnCancel(btnCancelar);
        customErrorDialog.setCancelable(false);
        customErrorDialog.setDialogActions(actions);
        customErrorDialog.show(fragmentManager, tag);
    }

    public static void createCustomDialogextranjero(String title, String message, FragmentManager fragmentManager, String tag,
                                                    DialogDoubleActions actions, String btnAceptar, String btnCancelar) {
        final CustomErrorDialog customErrorDialog = CustomErrorDialog.getInstance(R.layout.dialog_custom_error_message_pais_error,
                title, message, true, true);


        customErrorDialog.setTitleBtnAcept(btnAceptar);
        customErrorDialog.setTitleBtnCancel(btnCancelar);

        customErrorDialog.setDialogActions(actions);
        customErrorDialog.show(fragmentManager, tag);
    }


    public static void createCustomDialogCURP(String title, String message, FragmentManager fragmentManager, String tag,
                                                    DialogDoubleActions actions, String btnAceptar, String btnCancelar) {
        final CustomErrorDialog customErrorDialog = CustomErrorDialog.getInstance(R.layout.dialog_custom_curp_error_message,
                title, message, true, false);



        customErrorDialog.setTitleBtnAcept(btnAceptar);
        customErrorDialog.setTitleBtnCancel(btnCancelar);

        customErrorDialog.setDialogActions(actions);
        customErrorDialog.show(fragmentManager, tag);
    }


    public static void createSimpleCustomDialogNoCancel(String title, String message,
                                                        FragmentManager fragmentManager, final DialogDoubleActions actions) {
        createSimpleCustomDialog(title, message, fragmentManager, actions, true, false);
    }

    public static void createSimpleCustomDialog(String title, String message,
                                                FragmentManager fragmentManager, final DialogDoubleActions actions,
                                                boolean hasConfirmBtn, boolean hasCancelBtn) {
        final CustomErrorDialog customErrorDialog = CustomErrorDialog.getInstance(R.layout.dialog_custom_curp_message, title, message, hasConfirmBtn, hasCancelBtn);
        customErrorDialog.setDialogActions(new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                customErrorDialog.dismiss();
                if (actions != null) {
                    actions.actionConfirm(params);
                }
            }

            @Override
            public void actionCancel(Object... params) {
                customErrorDialog.dismiss();
                if (actions != null) {
                    actions.actionCancel(params);
                }
            }
        });
        customErrorDialog.setCancelable(false);
        customErrorDialog.show(fragmentManager, CustomErrorDialog.class.getSimpleName());
    }

    public static void createSimpleCustomDialogCURP(String title, String message,
                                                FragmentManager fragmentManager, final DialogDoubleActions actions,
                                                boolean hasConfirmBtn, boolean hasCancelBtn) {
        final CustomErrorDialog customErrorDialog = CustomErrorDialog.getInstance(R.layout.dialog_custom_curp_error_message, title, message, hasConfirmBtn, hasCancelBtn);
        customErrorDialog.setDialogActions(new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                customErrorDialog.dismiss();
                if (actions != null) {
                    RegisterUser registerUser = RegisterUser.getInstance();
                    String curp=customErrorDialog.getcurpedittext();
                    registerUser.setCURP(curp);
                    actions.actionConfirm(params);
                }
            }

            @Override
            public void actionCancel(Object... params) {
                customErrorDialog.dismiss();
                if (actions != null) {
                    actions.actionCancel(params);
                }
            }
        });
        customErrorDialog.setCancelable(false);
        customErrorDialog.show(fragmentManager, CustomErrorDialog.class.getSimpleName());
    }



    public static void showErrorSnackBar(Activity rootView, String message, int length) {
        Snackbar snack = Snackbar.make(rootView.getWindow().getDecorView(), message, length);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        view.setBackgroundColor(App.getContext().getResources().getColor(R.color.redColorTransparent));
        snack.show();
    }

    public static void showSuccessSnackBar(Activity rootView, String message, int length) {
        Snackbar snack = Snackbar.make(rootView.getWindow().getDecorView(), message, length);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        view.setBackgroundColor(App.getContext().getResources().getColor(R.color.redGreenTransparent));
        snack.show();
    }

    public static void showAlertDialog(Context context, String message, DialogInterface.OnClickListener positive) {
        AlertDialog builder = new AlertDialog.Builder(new ContextThemeWrapper(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT))
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(R.string.title_aceptar, positive)
                .setNegativeButton(R.string.title_cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        builder.show();
    }

    public static void showAlertDialogCancelar(Context context, String message, DialogInterface.OnClickListener positive) {
        AlertDialog builder = new AlertDialog.Builder(new ContextThemeWrapper(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT))
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(R.string.entendido_titulo, positive)
                .setNegativeButton(R.string.title_cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        builder.show();
    }
    public static void showAlertDialog(Context context, String titulo,String message, DialogInterface.OnClickListener positive) {
        AlertDialog builder = new AlertDialog.Builder(new ContextThemeWrapper(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT))
                .setTitle(titulo)
                .setMessage(message)
                .setPositiveButton(R.string.title_aceptar, positive)
                .setNegativeButton(R.string.title_cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        builder.show();
    }

    public static void showAlertDialogLlamar(Context context, String titulo,String message, DialogInterface.OnClickListener positive) {
        AlertDialog builder = new AlertDialog.Builder(new ContextThemeWrapper(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT))
                .setTitle(titulo)
                .setMessage(message)
                .setPositiveButton(R.string.title_llamar, positive)
                .setNegativeButton(R.string.title_cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        builder.show();
    }

    public static void showAlertDialog(Context context, String title, String message, int textPsitive,DialogInterface.OnClickListener positive) {
        AlertDialog builder = new AlertDialog.Builder(new ContextThemeWrapper(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT))
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.title_aceptar, positive)
                .setNegativeButton(R.string.title_cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        builder.show();
    }

    public static AlertDialog showAlertDialog(Context context, int title, String message){
        return new AlertDialog.Builder(new ContextThemeWrapper(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT))
                .setTitle(title)
                .setMessage(message).create();
    }
}
