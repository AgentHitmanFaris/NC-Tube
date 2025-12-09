package com.liskovsoft.smartyoutubetv2.tv.ui.adddevice;

import android.os.Bundle;
import androidx.leanback.app.GuidedStepSupportFragment;
import com.liskovsoft.smartyoutubetv2.tv.ui.common.LeanbackActivity;

/**
 * The Add device activity class.
 *
 */
public class AddDeviceActivity extends LeanbackActivity {
    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == savedInstanceState) {
            GuidedStepSupportFragment.addAsRoot(this, new AddDeviceFragment(), android.R.id.content);
        }
    }

    /**
     * Finish.
     *
     */
    @Override
    public void finish() {
        super.finish();

        finishReally();
    }
}
