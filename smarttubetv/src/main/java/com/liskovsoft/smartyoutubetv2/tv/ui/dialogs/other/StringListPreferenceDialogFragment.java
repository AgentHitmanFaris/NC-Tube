package com.liskovsoft.smartyoutubetv2.tv.ui.dialogs.other;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.liskovsoft.smartyoutubetv2.tv.R;
import com.liskovsoft.smartyoutubetv2.tv.ui.mod.leanback.preference.LeanbackListPreferenceDialogFragment;
import com.liskovsoft.smartyoutubetv2.tv.util.ViewUtil;

import java.util.Set;

/**
 * The String list preference dialog fragment class.
 *
 */
public class StringListPreferenceDialogFragment extends LeanbackListPreferenceDialogFragment {
    private boolean mIsTransparent;

    /**
     * New instance string list.
     *
     * @param key The Key.
     * @return A list of New instance string list.
     */
    public static StringListPreferenceDialogFragment newInstanceStringList(String key) {
        final Bundle args = new Bundle(1);
        args.putString(ARG_KEY, key);

        final StringListPreferenceDialogFragment
                fragment = new StringListPreferenceDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * On create adapter.
     *
     * @return The Adapter< view holder>.
     */
    @Override
    public Adapter<ViewHolder> onCreateAdapter() {
        return new AdapterMultiStrings(mEntries, mEntryValues, mInitialSelections);
    }

    /**
     * On create view.
     *
     * @param inflater The Inflater.
     * @param container The Container.
     * @param savedInstanceState The saved instance state bundle.
     * @return The View.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (mIsTransparent && view != null) {
            ViewUtil.enableTransparentDialog(getActivity(), view);
        }

        return view;
    }

    /**
     * Enable transparent.
     *
     * @param enable The Enable.
     */
    public void enableTransparent(boolean enable) {
        mIsTransparent = enable;
    }

    /**
     * The Adapter multi strings class.
     *
     */
    public class AdapterMultiStrings extends AdapterMulti {
        /**
         * Adapter multi strings.
         *
         * @param entries The Entries.
         * @param entryValues The Entry values.
         * @param initialSelections The Initial selections.
         */
        public AdapterMultiStrings(CharSequence[] entries, CharSequence[] entryValues, Set<String> initialSelections) {
            super(entries, entryValues, initialSelections);
        }

        /**
         * On create view holder.
         *
         * @param parent The Parent.
         * @param viewType The View type.
         * @return The View holder.
         */
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            final View view = inflater.inflate(R.layout.dialog_list_preference_item_multi, parent,
                    false);
            return new ViewHolder(view, this);
        }

        /**
         * On bind view holder.
         *
         * @param holder The Holder.
         * @param position The Position.
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.getWidgetView().setChecked(
                    mSelections.contains(mEntryValues[position].toString()));
            holder.getTitleView().setText(mEntries[position]);
        }
    }
}
