package com.liskovsoft.smartyoutubetv2.tv.ui.dialogs.other;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.DialogPreference;
import com.liskovsoft.smartyoutubetv2.common.app.models.playback.ui.CommentsReceiver;

/**
 * The Comments preference class.
 *
 */
public class CommentsPreference extends DialogPreference {
    private CommentsReceiver mCommentsReceiver;

    /**
     * Comments preference.
     *
     * @param context The application context.
     * @param attrs The Attrs.
     * @param defStyleAttr The Def style attr.
     * @param defStyleRes The Def style res.
     */
    public CommentsPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Comments preference.
     *
     * @param context The application context.
     * @param attrs The Attrs.
     * @param defStyleAttr The Def style attr.
     */
    public CommentsPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Comments preference.
     *
     * @param context The application context.
     * @param attrs The Attrs.
     */
    public CommentsPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Comments preference.
     *
     * @param context The application context.
     */
    public CommentsPreference(Context context) {
        super(context);
    }

    /**
     * Sets Comments receiver.
     *
     * @param commentsReceiver The Comments receiver.
     */
    public void setCommentsReceiver(CommentsReceiver commentsReceiver) {
        mCommentsReceiver = commentsReceiver;
    }

    /**
     * Gets Comments receiver.
     *
     * @return The Comments receiver.
     */
    public CommentsReceiver getCommentsReceiver() {
        return mCommentsReceiver;
    }
}
