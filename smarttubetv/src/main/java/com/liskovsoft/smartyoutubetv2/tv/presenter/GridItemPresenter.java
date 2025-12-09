package com.liskovsoft.smartyoutubetv2.tv.presenter;

import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.leanback.widget.Presenter;
import com.liskovsoft.smartyoutubetv2.tv.R;

/**
 * The Grid item presenter class.
 *
 */
public class GridItemPresenter extends Presenter {
    private final Fragment mainFragment;

    /**
     * Grid item presenter.
     *
     * @param mainFragment The Main fragment.
     */
    public GridItemPresenter(Fragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    /**
     * On create view holder.
     *
     * @param parent The Parent.
     * @return The View holder.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        TextView view = new TextView(parent.getContext());

        Resources res = parent.getResources();
        int width = res.getDimensionPixelSize(R.dimen.settings_card_width);
        int height = res.getDimensionPixelSize(R.dimen.settings_card_height);

        view.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setBackgroundColor(ContextCompat.getColor(parent.getContext(),
                R.color.card_default_background));
        view.setTextColor(Color.WHITE);
        view.setGravity(Gravity.CENTER);
        return new ViewHolder(view);
    }

    /**
     * On bind view holder.
     *
     * @param viewHolder The View holder.
     * @param item The Item.
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ((TextView) viewHolder.view).setText((String) item);
    }

    /**
     * On unbind view holder.
     *
     * @param viewHolder The View holder.
     */
    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
    }
}
