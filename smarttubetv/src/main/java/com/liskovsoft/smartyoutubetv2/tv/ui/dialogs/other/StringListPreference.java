package com.liskovsoft.smartyoutubetv2.tv.ui.dialogs.other;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.MultiSelectListPreference;

/**
 * The String list preference class.
 *
 */
public class StringListPreference extends MultiSelectListPreference {
    /**
     * String list preference.
     *
     * @param context The application context.
     * @param attrs The Attrs.
     * @param defStyleAttr The Def style attr.
     * @param defStyleRes The Def style res.
     */
    public StringListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * String list preference.
     *
     * @param context The application context.
     * @param attrs The Attrs.
     * @param defStyleAttr The Def style attr.
     */
    public StringListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * String list preference.
     *
     * @param context The application context.
     * @param attrs The Attrs.
     */
    public StringListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * String list preference.
     *
     * @param context The application context.
     */
    public StringListPreference(Context context) {
        super(context);
    }
}
