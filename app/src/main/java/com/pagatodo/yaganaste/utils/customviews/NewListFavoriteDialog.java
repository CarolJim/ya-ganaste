package com.pagatodo.yaganaste.utils.customviews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui_wallet.interfaces.INewPaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.fragments.NewPaymentFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsTabFragment;
import com.pagatodo.yaganaste.utils.customviews.carousel.CustomAdapterPagos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 17/04/2017.
 */

public class NewListFavoriteDialog extends Dialog implements AdapterView.OnItemClickListener {

    ;
    ArrayAdapter<String> adapter = null;
    CustomAdapterPagos adapter2 = null;
    ArrayList<String> mList = new ArrayList<>();
    List<DataFavoritos> listCarousel;
    Context context;
    PaymentsTabFragment parentFragment;
    INewPaymentPresenter presenter;
    int mType;
    private NewPaymentFragment fragment;
    private ListView list;
    private EditText filterText = null;
    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
//
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            adapter2.getFilter().filter(s);
        }
    };

    public NewListFavoriteDialog(Context context, List<DataFavoritos> list, INewPaymentPresenter presenter,
                               int mType) {
        super(context);
        this.context = context;
        this.presenter = presenter;
        this.mType = mType;
        listCarousel = list;

        for (DataFavoritos item : list) {
            if (item.getIdComercio() > 0) {
                mList.add(item.getNombre().trim());
            }

//            else if (item.getFavoritos() != null) {
//                mList.add(item.getFavoritos().getNombre().trim());
//            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_listview);
        filterText = (EditText) findViewById(R.id.searchText);
        filterText.addTextChangedListener(filterTextWatcher);
        list = (ListView) findViewById(R.id.list);
//        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mList);
//        list.setAdapter(adapter);

        adapter2 = new CustomAdapterPagos(getContext(), R.layout.item_pagos_textview, mList);
        list.setAdapter(adapter2);
        list.setItemsCanFocus(true);
        list.setOnItemClickListener(this);
    }

    @Override
    protected void onStop() {
        filterText.removeTextChangedListener(filterTextWatcher);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       /* String value = adapter2.getItem(position);
        int pos = mList.indexOf(value);
        presenter.setCarouselItem(listCarousel.get(pos));

        parentFragment.changeImgageToPay();
        parentFragment.openPaymentFragment();
        dismiss();*/
        String value = adapter2.getItem(position);
        int pos = mList.indexOf(value);

        presenter.sendChoiceFavorite(listCarousel.get(pos+1) , mType);
        dismiss();
    }
}
