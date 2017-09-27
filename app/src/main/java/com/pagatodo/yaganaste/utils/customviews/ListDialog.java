package com.pagatodo.yaganaste.utils.customviews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsTabPresenter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;
import com.pagatodo.yaganaste.utils.customviews.carousel.CustomAdapterPagos;

import java.util.ArrayList;

/**
 * Created by Jordan on 17/04/2017.
 */

public class ListDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener {

    ArrayAdapter<String> adapter = null;
    CustomAdapterPagos adapter2 = null;
    ArrayList<String> mList = new ArrayList<>();
    ArrayList<CarouselItem> listCarousel;
    Context context;
    PaymentsTabFragment parentFragment;
    PaymentsTabPresenter presenter;
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


    public ListDialog(Context c, ArrayList<CarouselItem> list, PaymentsTabPresenter paymentsTabPresenter, PaymentsTabFragment fragment) {
        super(c);
        context = c;
        presenter = paymentsTabPresenter;
        parentFragment = fragment;

        listCarousel = list;
        for (CarouselItem item : list) {
            if(item.getComercio()!=null) {
                mList.add(item.getComercio().getNombreComercio().trim());
            }else if (item.getFavoritos()!=null){
                mList.add(item.getFavoritos().getNombre().trim());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_listview);
        this.setTitle("Selecciona");
        filterText = (EditText) findViewById(R.id.searchText);
        filterText.addTextChangedListener(filterTextWatcher);
        list = (ListView) findViewById(R.id.list);
//        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mList);
//        list.setAdapter(adapter);

        adapter2 = new CustomAdapterPagos(getContext(), R.layout.item_pagos_textview, mList);
        list.setAdapter(adapter2);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStop() {
        filterText.removeTextChangedListener(filterTextWatcher);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String value = adapter2.getItem(position);
        int pos = mList.indexOf(value);
        presenter.setCarouselItem(listCarousel.get(pos));

        parentFragment.changeImgageToPay();
        parentFragment.openPaymentFragment();
        dismiss();
    }
}
