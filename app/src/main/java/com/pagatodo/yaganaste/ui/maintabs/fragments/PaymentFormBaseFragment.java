package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.view.View;
import android.widget.SeekBar;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.SeekYaGanaste;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 12/04/2017.
 */

public abstract class PaymentFormBaseFragment extends GenericFragment implements SeekBar.OnSeekBarChangeListener {
    public View rootview;
    protected boolean isValid = false;

    @BindView(R.id.myseek)
    protected SeekBar mySeekBar;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {};

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {};

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getProgress() >= 85) {
            seekBar.setProgress(100);
            continuePayment();
        } else if (seekBar.getProgress() < 85) {
            seekBar.setProgress(0);
        }
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        mySeekBar.setOnSeekBarChangeListener(this);

    }

    protected void continuePayment(){};
        //mySeekBar.setEnabled(false);
}
