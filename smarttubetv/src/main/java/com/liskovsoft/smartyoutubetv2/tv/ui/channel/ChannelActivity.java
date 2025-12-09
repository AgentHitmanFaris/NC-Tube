package com.liskovsoft.smartyoutubetv2.tv.ui.channel;

import android.os.Bundle;
import com.liskovsoft.smartyoutubetv2.tv.R;
import com.liskovsoft.smartyoutubetv2.tv.ui.common.LeanbackActivity;

/**
 * The Channel activity class.
 *
 */
public class ChannelActivity extends LeanbackActivity {
    private static final String TAG = ChannelActivity.class.getSimpleName();
    private ChannelFragment mFragment;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_channel);
        mFragment = (ChannelFragment) getSupportFragmentManager().findFragmentById(R.id.channel_fragment);
    }

    /**
     * Finish really.
     *
     */
    @Override
    public void finishReally() {
        super.finishReally();

        mFragment.onFinish();
    }
}
