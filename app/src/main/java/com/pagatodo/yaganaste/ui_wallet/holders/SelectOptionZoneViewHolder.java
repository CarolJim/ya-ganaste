package com.pagatodo.yaganaste.ui_wallet.holders;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dspread.xpos.QPOSService;
import com.facebook.stetho.common.Util;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import static com.pagatodo.yaganaste.utils.Recursos.HAS_CONFIG_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;

/**
 * Created by icruz on 26/02/2018.
 */

public class SelectOptionZoneViewHolder extends OptionsViewHolder implements View.OnClickListener {

    //public Context context;
    private static final int FIRST_OPTION = 1, SECOND_OPTION = 2, THIRD_OPTION = 3;
    public Activity activity;
    public ImageView imgBtn1, imgBtn2, imgBtn3;
    public TextView title;
    public StyleTextView descBtn1, descBtn2, descBtn3;
    public LinearLayout btn1, btn2, btn3;
    public StyleButton btnContinue;
    private int idButton;

    public SelectOptionZoneViewHolder(Activity context, View itemView) {
        super(itemView);
        this.activity = context;
        this.title = itemView.findViewById(R.id.txt_title);
        this.descBtn1 = itemView.findViewById(R.id.button_1_text);
        this.descBtn2 = itemView.findViewById(R.id.button_2_text);
        this.descBtn3 = itemView.findViewById(R.id.button_3_text);
        this.imgBtn1 = itemView.findViewById(R.id.button_1_image);
        this.imgBtn2 = itemView.findViewById(R.id.button_2_image);
        this.imgBtn3 = itemView.findViewById(R.id.button_3_image);
        this.btn1 = itemView.findViewById(R.id.btn_1);
        this.btn2 = itemView.findViewById(R.id.btn_2);
        this.btn3 = itemView.findViewById(R.id.btn_3);
        this.btnContinue = itemView.findViewById(R.id.btn_continue);
    }

    @Override
    public void bind(final ElementView elementView, final OnItemClickListener listener) {
        this.title.setText(elementView.getTitle());

        if (elementView.isStatus()) {
            this.descBtn1.setText(activity.getString(R.string.lector_plug));
            this.descBtn2.setText(activity.getString(R.string.lector_inalambrico));
            this.descBtn3.setText(activity.getString(R.string.sin_lector_aun));
            this.imgBtn1.setImageResource(R.drawable.ico_cobrar_in);
            this.imgBtn2.setImageResource(R.drawable.ic_bluetooth_dongle);
            this.imgBtn3.setImageResource(R.drawable.ic_no_dongle);
            this.btnContinue.setText(activity.getString(R.string.continuar));
            this.btnContinue.setOnClickListener(v -> {

                switch (idButton) {
                    case FIRST_OPTION:
                        App.getInstance().getPrefs().saveDataBool(HAS_CONFIG_DONGLE, true);
                        App.getInstance().getPrefs().saveDataInt(MODE_CONNECTION_DONGLE, QPOSService.CommunicationMode.AUDIO.ordinal());
                        listener.onItemClick(elementView);
                        break;
                    case SECOND_OPTION:
                        App.getInstance().getPrefs().saveDataBool(HAS_CONFIG_DONGLE, true);
                        App.getInstance().getPrefs().saveDataInt(MODE_CONNECTION_DONGLE, QPOSService.CommunicationMode.BLUETOOTH.ordinal());
                        listener.onItemClick(elementView);
                        break;
                    case THIRD_OPTION:
                        App.getInstance().getPrefs().saveDataBool(HAS_CONFIG_DONGLE, true);
                        /* SIN LECTOR */
                        App.getInstance().getPrefs().saveDataInt(MODE_CONNECTION_DONGLE, 0);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yaganaste.com"));
                        activity.startActivity(browserIntent);
                        break;
                    default:
                        UI.showErrorSnackBar(activity, activity.getString(R.string.error_seleccion_lector), Snackbar.LENGTH_SHORT);
                        break;




                }
            });
        }
        this.btn1.setOnClickListener(this);
        this.btn2.setOnClickListener(this);
        this.btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                idButton = FIRST_OPTION;
                this.btn1.setBackground(activity.getResources().getDrawable(R.drawable.blue_border_rectangle));
                this.btn2.setBackground(activity.getResources().getDrawable(R.drawable.gray_border_rectangle));
                this.btn3.setBackground(activity.getResources().getDrawable(R.drawable.gray_border_rectangle));
                break;
            case R.id.btn_2:
                idButton = SECOND_OPTION;
                this.btn1.setBackground(activity.getResources().getDrawable(R.drawable.gray_border_rectangle));
                this.btn2.setBackground(activity.getResources().getDrawable(R.drawable.blue_border_rectangle));
                this.btn3.setBackground(activity.getResources().getDrawable(R.drawable.gray_border_rectangle));
                break;
            case R.id.btn_3:
                idButton = THIRD_OPTION;
                this.btn1.setBackground(activity.getResources().getDrawable(R.drawable.gray_border_rectangle));
                this.btn2.setBackground(activity.getResources().getDrawable(R.drawable.gray_border_rectangle));
                this.btn3.setBackground(activity.getResources().getDrawable(R.drawable.blue_border_rectangle));
                break;
        }
    }
}
