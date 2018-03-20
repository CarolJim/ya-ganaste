package com.pagatodo.yaganaste.ui_wallet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.utils.customviews.GenericTabLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovimentsActivity<T extends IEnumTab> extends LoaderActivity {

    @BindView(R.id.tab_months)
    GenericTabLayout<T> tabMonths;
    @BindView(R.id.title)
    StyleTextView title;
    @BindView(R.id.swipe_container)
    SwipyRefreshLayout swipeContainer;
    @BindView(R.id.recycler_movements)
    RecyclerView recyclerMovements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviments_layout);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerMovements.setLayoutManager(layoutManager);
        recyclerMovements.setHasFixedSize(true);
        recyclerMovements.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    @Override
    public boolean requiresTimer() {
        return true;
    }


}
