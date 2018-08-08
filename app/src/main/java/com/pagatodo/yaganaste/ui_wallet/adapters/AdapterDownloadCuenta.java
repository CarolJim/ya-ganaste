package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.MonthsMovementsTab;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

public class AdapterDownloadCuenta extends BaseAdapter {

    private List<MonthsMovementsTab> months;
    private Context context;

    public AdapterDownloadCuenta(@NonNull Context context) {
        this.context = context;
        months = DateUtil.getLastDonwloadStatmentsMonths();
    }

    @Nullable
    @Override
    public MonthsMovementsTab getItem(int position) {
        return months.get(position);
    }

    @Override
    public long getItemId(int position) {
        return months.get(position).getMonth();
    }

    @Override
    public int getCount() {
        return months.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MonthsMovementsTab dataModel = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_download_edo_cuenta, parent, false);
            viewHolder.lyt = (LinearLayout) convertView.findViewById(R.id.lyt_row_download_edo_cuenta);
            viewHolder.txt = (StyleTextView) convertView.findViewById(R.id.txt_month);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String monthName = dataModel.getName(context).substring(0, 1).toUpperCase() + dataModel.getName(context).substring(1);
        viewHolder.txt.setText(monthName + " " + dataModel.getYear());
        return convertView;
    }

    static class ViewHolder {
        LinearLayout lyt;
        ImageView img;
        StyleTextView txt;
    }
}
