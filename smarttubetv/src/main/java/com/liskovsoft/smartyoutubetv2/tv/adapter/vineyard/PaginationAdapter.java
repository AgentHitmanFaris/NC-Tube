package com.liskovsoft.smartyoutubetv2.tv.adapter.vineyard;

import android.content.Context;
import android.os.Handler;

import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.PresenterSelector;
import com.liskovsoft.smartyoutubetv2.tv.R;
import com.liskovsoft.smartyoutubetv2.common.app.models.search.vineyard.Option;
import com.liskovsoft.smartyoutubetv2.tv.presenter.vineyard.IconItemPresenter;
import com.liskovsoft.smartyoutubetv2.tv.presenter.vineyard.LoadingPresenter;
import com.liskovsoft.smartyoutubetv2.tv.ui.widgets.vineyard.LoadingCardView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Pagination adapter class.
 *
 */
public abstract class PaginationAdapter extends ArrayObjectAdapter {
    public static final String KEY_TAG = "tag";
    public static final String KEY_ANCHOR = "anchor";
    public static final String KEY_NEXT_PAGE = "next_page";

    private Context mContext;
    private Integer mNextPage;
    private LoadingPresenter mLoadingPresenter;
    private IconItemPresenter mIconItemPresenter;
    private Presenter mPresenter;

    private String mRowTag;
    private String mAnchor;
    private int mLoadingIndicatorPosition;


    /**
     * Pagination adapter.
     *
     * @param context The application context.
     * @param presenter The Presenter.
     * @param tag The Tag.
     */
    public PaginationAdapter(Context context, Presenter presenter, String tag) {
        mContext = context;
        mPresenter = presenter;
        mLoadingPresenter = new LoadingPresenter();
        mIconItemPresenter = new IconItemPresenter();
        mLoadingIndicatorPosition = -1;
        mNextPage = 1;
        mRowTag = tag;
        setPresenterSelector();
    }

    /**
     * Sets Tag.
     *
     * @param tag The Tag.
     */
    public void setTag(String tag) {
        mRowTag = tag;
    }

    /**
     * Sets Next page.
     *
     * @param page The Page.
     */
    public void setNextPage(int page) {
        mNextPage = page;
    }

    /**
     * Sets Presenter selector.
     *
     */
    public void setPresenterSelector() {
        setPresenterSelector(new PresenterSelector() {
            /**
             * Gets Presenter.
             *
             * @param item The Item.
             * @return The Presenter.
             */
            @Override
            public Presenter getPresenter(Object item) {
                if (item instanceof LoadingCardView) {
                    return mLoadingPresenter;
                } else if (item instanceof Option) {
                    return mIconItemPresenter;
                }
                return mPresenter;
            }
        });
    }

    /**
     * Gets Items.
     *
     * @return A list of Get items.
     */
    public List<Object> getItems() {
        return unmodifiableList();
    }

    /**
     * Should show loading indicator.
     *
     * @return True if should show loading indicator, false otherwise.
     */
    public boolean shouldShowLoadingIndicator() {
        return mLoadingIndicatorPosition == -1;
    }

    /**
     * Show loading indicator.
     *
     */
    public void showLoadingIndicator() {
        new Handler().post(new Runnable() {
            /**
             * Run.
             *
             */
            @Override
            public void run() {
                mLoadingIndicatorPosition = size();
                add(mLoadingIndicatorPosition, new LoadingCardView(mContext));
                notifyItemRangeInserted(mLoadingIndicatorPosition, 1);
            }
        });
    }

    /**
     * Remove loading indicator.
     *
     */
    public void removeLoadingIndicator() {
        removeItems(mLoadingIndicatorPosition, 1);
        notifyItemRangeRemoved(mLoadingIndicatorPosition, 1);
        mLoadingIndicatorPosition = -1;
    }

    /**
     * Sets Anchor.
     *
     * @param anchor The Anchor.
     */
    public void setAnchor(String anchor) {
        mAnchor = anchor;
    }

    /**
     * Add posts.
     *
     * @param posts The Posts.
     */
    public void addPosts(List<?> posts) {
        if (posts == null) {
            return;
        }

        if (posts.size() > 0) {
            addAll(size(), posts);
        } else {
            mNextPage = 0;
        }
    }

    /**
     * Should load next page.
     *
     * @return True if should load next page, false otherwise.
     */
    public boolean shouldLoadNextPage() {
        return shouldShowLoadingIndicator() && mNextPage != 0;
    }

    /**
     * Gets Adapter options.
     *
     * @return The Map< string,  string>.
     */
    public Map<String, String> getAdapterOptions() {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_TAG, mRowTag);
        map.put(KEY_ANCHOR, mAnchor);
        map.put(KEY_NEXT_PAGE, mNextPage.toString());
        return map;
    }

    /**
     * Show reload card.
     *
     */
    public void showReloadCard() {
        Option option = new Option(
                mContext.getString(R.string.title_no_videos),
                mContext.getString(R.string.message_check_again),
                R.drawable.ic_refresh_white);
        add(option);
    }

    /**
     * Show try again card.
     *
     */
    public void showTryAgainCard() {
        Option option = new Option(
                mContext.getString(R.string.title_oops),
                mContext.getString(R.string.message_try_again),
                R.drawable.ic_refresh_white);
        add(option);
    }

    /**
     * Remove reload card.
     *
     */
    public void removeReloadCard() {
        if (isRefreshCardDisplayed()) {
            removeItems(0, 1);
            notifyItemRangeRemoved(size(), 1);
        }
    }

    /**
     * Checks if Refresh card displayed.
     *
     * @return True if is refresh card displayed, false otherwise.
     */
    public boolean isRefreshCardDisplayed() {
        Object item = get(size() - 1);
        if (item instanceof Option) {
            Option option = (Option) item;
            String noVideosTitle = mContext.getString(R.string.title_no_videos);
            String oopsTitle = mContext.getString(R.string.title_oops);
            return (option.title.equals(noVideosTitle) ||
                            option.title.equals(oopsTitle));
        }
        return false;
    }

    /**
     * Add all items.
     *
     * @param items The Items.
     */
    public abstract void addAllItems(List<?> items);

    /**
     * Gets All items.
     *
     * @return A list of Get all items.
     */
    public abstract List<?> getAllItems();


}
