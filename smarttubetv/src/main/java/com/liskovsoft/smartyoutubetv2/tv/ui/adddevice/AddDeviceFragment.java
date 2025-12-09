package com.liskovsoft.smartyoutubetv2.tv.ui.adddevice;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.app.GuidedStepSupportFragment;
import androidx.leanback.widget.GuidanceStylist;
import androidx.leanback.widget.GuidedAction;
import com.liskovsoft.smartyoutubetv2.common.app.presenters.AddDevicePresenter;
import com.liskovsoft.smartyoutubetv2.common.app.views.AddDeviceView;
import com.liskovsoft.smartyoutubetv2.tv.R;

import java.util.List;

/**
 * The Add device fragment class.
 *
 */
public class AddDeviceFragment extends GuidedStepSupportFragment implements AddDeviceView {
    private static final int CONTINUE = 2;
    private AddDevicePresenter mAddDevicePresenter;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAddDevicePresenter = AddDevicePresenter.instance(getContext());
        mAddDevicePresenter.setView(this);
    }

    /**
     * On activity created.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAddDevicePresenter.onViewInitialized();
    }

    /**
     * On destroy.
     *
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mAddDevicePresenter.onViewDestroyed();
    }

    /**
     * Show code.
     *
     * @param userCode The User code.
     */
    @Override
    public void showCode(String userCode) {
        setTitle(userCode);
    }

    private void setTitle(String userCode) {
        getGuidanceStylist().getTitleView().setText(userCode);
    }

    /**
     * Close.
     *
     */
    @Override
    public void close() {
        getActivity().finish();
    }

    /**
     * On create guidance.
     *
     * @param savedInstanceState The saved instance state bundle.
     * @return The Guidance stylist. guidance.
     */
    @Override
    @NonNull
    public GuidanceStylist.Guidance onCreateGuidance(@NonNull Bundle savedInstanceState) {
        String title = getString(R.string.signin_view_title);
        String description = getString(R.string.add_device_view_description);
        return new GuidanceStylist.Guidance(title, description, "", null);
    }

    /**
     * On create actions.
     *
     * @param actions The Actions.
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        GuidedAction login = new GuidedAction.Builder()
                .id(CONTINUE)
                .title(getString(R.string.signin_view_action_text))
                .build();
        actions.add(login);
    }

    /**
     * On guided action clicked.
     *
     * @param action The Action.
     */
    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        if (action.getId() == CONTINUE) {
            mAddDevicePresenter.onActionClicked();
        }
    }
}
