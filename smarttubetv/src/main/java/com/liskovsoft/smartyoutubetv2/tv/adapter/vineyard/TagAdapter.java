package com.liskovsoft.smartyoutubetv2.tv.adapter.vineyard;

import android.content.Context;

import androidx.leanback.widget.Presenter;
import com.liskovsoft.smartyoutubetv2.common.app.models.search.vineyard.Tag;
import com.liskovsoft.smartyoutubetv2.tv.presenter.vineyard.TagPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * The Tag adapter class.
 *
 */
public class TagAdapter extends PaginationAdapter {

    /**
     * Tag adapter.
     *
     * @param context The application context.
     * @param tag The Tag.
     */
    public TagAdapter(Context context, String tag) {
        super(context, new TagPresenter(), tag);
    }

    /**
     * Tag adapter.
     *
     * @param context The application context.
     * @param presenter The Presenter.
     * @param tag The Tag.
     */
    public TagAdapter(Context context, Presenter presenter, String tag) {
        super(context, presenter, tag);
    }

    /**
     * Add all items.
     *
     * @param items The Items.
     */
    @Override
    public void addAllItems(List<?> items) {
        addPosts(items);
    }

    /**
     * Gets All items.
     *
     * @return A list of Get all items.
     */
    @Override
    public List<?> getAllItems() {
        List<Object> itemList = getItems();
        ArrayList<Tag> tags = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            Object object = itemList.get(i);
            if (object instanceof Tag) tags.add((Tag) object);
        }
        return tags;
    }
}
