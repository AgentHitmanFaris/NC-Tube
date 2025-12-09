package com.liskovsoft.smartyoutubetv2.tv.presenter.base;

import androidx.leanback.widget.Presenter;

/**
 * The On item long pressed listener interface.
 *
 */
public interface OnItemLongPressedListener {
    void onItemLongPressed(Presenter.ViewHolder itemViewHolder, Object item);
}
