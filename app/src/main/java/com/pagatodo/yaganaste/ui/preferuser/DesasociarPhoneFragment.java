package com.pagatodo.yaganaste.ui.preferuser;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DesasociarPhoneFragment extends GenericFragment implements View.OnClickListener,
        IPreferUserView{

    @BindView(R.id.fragment_desasociar_btn)
    StyleButton btn_desasociar;
    View rootview;
    
    public DesasociarPhoneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static DesasociarPhoneFragment newInstance() {
        DesasociarPhoneFragment fragmentDesasociarPhone = new DesasociarPhoneFragment();
        return fragmentDesasociarPhone;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_desasociar_phone, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        btn_desasociar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), "Click Desasociar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void testToastSucess() {
        Toast.makeText(getContext(), "TEST Fragment", Toast.LENGTH_SHORT).show();
    }
}
