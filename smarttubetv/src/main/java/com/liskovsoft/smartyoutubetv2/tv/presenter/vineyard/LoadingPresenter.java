package com.liskovsoft.smartyoutubetv2.tv.presenter.vineyard;

import android.view.ViewGroup;
import androidx.leanback.widget.Presenter;
import com.liskovsoft.smartyoutubetv2.tv.ui.widgets.vineyard.LoadingCardView;

/**
 * The Loading presenter class.
 *
 */
public class LoadingPresenter extends Presenter {

    /**
     * On create view holder.
     *
     * @param parent The Parent.
     * @return The View holder.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        LoadingCardView cardView = new LoadingCardView(parent.getContext());
        return new ViewHolder(cardView);
    }

    /**
     * On bind view holder.
     *
     * @param viewHolder The View holder.
     * @param item The Item.
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if (item instanceof LoadingCardView){
            LoadingCardView cardView = (LoadingCardView) viewHolder.view;
            cardView.isLoading(true);
        }
    }

    /**
     * On unbind view holder.
     *
     * @param viewHolder The View holder.
     */
    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        if (viewHolder.view instanceof LoadingCardView) {
            LoadingCardView cardView = (LoadingCardView) viewHolder.view;
            cardView.isLoading(false);
        }
    }
}