package com.liskovsoft.smartyoutubetv2.tv.ui.dialogs.other;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.DialogPreference;
import com.liskovsoft.smartyoutubetv2.common.app.models.playback.ui.ChatReceiver;

/**
 * The Chat preference class.
 *
 */
public class ChatPreference extends DialogPreference {
    private ChatReceiver mChatReceiver;

    /**
     * Chat preference.
     *
     * @param context The application context.
     * @param attrs The Attrs.
     * @param defStyleAttr The Def style attr.
     * @param defStyleRes The Def style res.
     */
    public ChatPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Chat preference.
     *
     * @param context The application context.
     * @param attrs The Attrs.
     * @param defStyleAttr The Def style attr.
     */
    public ChatPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Chat preference.
     *
     * @param context The application context.
     * @param attrs The Attrs.
     */
    public ChatPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Chat preference.
     *
     * @param context The application context.
     */
    public ChatPreference(Context context) {
        super(context);
    }

    /**
     * Sets Chat receiver.
     *
     * @param chatReceiver The Chat receiver.
     */
    public void setChatReceiver(ChatReceiver chatReceiver) {
        mChatReceiver = chatReceiver;
    }

    /**
     * Gets Chat receiver.
     *
     * @return The Chat receiver.
     */
    public ChatReceiver getChatReceiver() {
        return mChatReceiver;
    }
}
