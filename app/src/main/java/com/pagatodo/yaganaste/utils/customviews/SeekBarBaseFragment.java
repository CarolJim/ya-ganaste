package com.pagatodo.yaganaste.utils.customviews;

import android.view.View;
import android.widget.SeekBar;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 12/04/2017.
 */

public abstract class SeekBarBaseFragment extends GenericFragment implements SeekBar.OnSeekBarChangeListener {
    protected View rootview;
    @BindView(R.id.myseek)
    protected SeekBar mySeekBar;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

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

    protected abstract void continuePayment();

    public void setSeekBarProgress(int progress) {
        mySeekBar.setProgress(progress);
    }
}
