package com.pagatodo.yaganaste.modules.sidebar.DataAccount;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;

import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.PHONE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.SIMPLE_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.URL_PHOTO_USER;
import static com.pagatodo.yaganaste.utils.Recursos.USER_PROVISIONED;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountDataFragment extends SupportFragment {
    private StyleTextView display_names, display_emails, display_phones;
    private ImageView image_user;


    public static AccountDataFragment newInstance() {
        AccountDataFragment accountDataFragment = new AccountDataFragment();
        return accountDataFragment;
    }

    public AccountDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_account_data, container, false);
        ButterKnife.bind(this, rootView);
        display_names = (StyleTextView) rootView.findViewById(R.id.display_names);
        display_emails = (StyleTextView) rootView.findViewById(R.id.display_emails);
        display_phones = (StyleTextView) rootView.findViewById(R.id.display_phones);
        image_user = (ImageView) rootView.findViewById(R.id.image_user);

        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
//int notifPendents = App.getInstance().getPrefs().loadDataInt(NOTIF_COUNT);
        String nombre = App.getInstance().getPrefs().loadData(SIMPLE_NAME);
        display_names.setText(nombre);
        String email = App.getInstance().getPrefs().loadData(USER_PROVISIONED);
        display_emails.setText(email);
        String phone_number = App.getInstance().getPrefs().loadData(PHONE_NUMBER);
        display_phones.setText(phone_number);
        String image_usr = App.getInstance().getPrefs().loadData(URL_PHOTO_USER);
        if (image_usr.isEmpty()) {
            image_user.setBackgroundResource(R.drawable.icon_user);
        } else {
            Picasso.with(getContext()).load(image_usr)
                    .placeholder(R.drawable.icon_user)
                    .into(image_user);
        }

    }
}
