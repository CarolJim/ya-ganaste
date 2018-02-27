package com.pagatodo.yaganaste.ui_wallet.holders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;

/**
 * Created by icruz on 26/02/2018.
 */

public class StatusZoneViewHolder extends OptionsViewHolder {

    //public Context context;
    public Activity activity;
    public ImageView iconImg;
    public TextView title;
    public TextView description;
    public AppCompatButton button;
    //hide
    public TextView numero;
    public TextView contacto;


    public StatusZoneViewHolder(Activity context, View itemView) {
        super(itemView);
        this.activity = context;
        this.iconImg = itemView.findViewById(R.id.icon_view);
        this.title = itemView.findViewById(R.id.txt_uno);
        this.description = itemView.findViewById(R.id.txt_desc);
        this.numero = itemView.findViewById(R.id.numero_tel);
        this.contacto = itemView.findViewById(R.id.numero_contacto);
        this.button = itemView.findViewById(R.id.btn_action);
    }


    @Override
    public void bind(final ElementView elementView, final OnItemClickListener listener) {
        /*
         private int idOperacion;
    private int resource;
    private int title;
    private int description;
         */
        this.iconImg.setBackgroundResource(elementView.getResource());
        this.title.setText(elementView.getTitle());
        this.description.setText(elementView.getDescription());
        this.numero.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                String number = activity.getString(R.string.numero_telefono_contactanos);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" + number));

                if (!ValidatePermissions.isAllPermissionsActives(activity, ValidatePermissions.getPermissionsCheck())) {
                    ValidatePermissions.checkPermissions(activity, new String[]{
                            Manifest.permission.CALL_PHONE},PERMISSION_GENERAL);
                } else {
                    activity.startActivity(callIntent);
                }
            }
        });

        this.contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, "contacto@yaganaste.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contacto");
                //intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
                activity.startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        if (elementView.isStatus()) {
            this.numero.setVisibility(View.GONE);
            this.contacto.setVisibility(View.GONE);
            this.button.setVisibility(View.VISIBLE);
            this.button.setText(elementView.getTextbutton());
            this.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(elementView);
                }
            });
        }

        if (elementView.isColor()){
            this.title.setTextColor(activity.getResources().getColor(R.color.redColor));
        }
    }
}
