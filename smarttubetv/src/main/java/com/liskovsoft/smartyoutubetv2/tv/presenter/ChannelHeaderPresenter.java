package com.liskovsoft.smartyoutubetv2.tv.presenter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;

import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.SearchBar;
import androidx.leanback.widget.SearchEditText;
import androidx.leanback.widget.SearchOrbView;
import androidx.leanback.widget.SpeechOrbView;
import androidx.leanback.widget.SpeechRecognitionCallback;

import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.helpers.KeyHelpers;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.helpers.PermissionHelpers;
import com.liskovsoft.smartyoutubetv2.common.misc.MotherActivity;
import com.liskovsoft.smartyoutubetv2.common.prefs.SearchData;
import com.liskovsoft.smartyoutubetv2.tv.BuildConfig;
import com.liskovsoft.smartyoutubetv2.tv.R;
import com.liskovsoft.smartyoutubetv2.tv.util.ViewUtil;

import net.gotev.speech.GoogleVoiceTypingDisabledException;
import net.gotev.speech.Speech;
import net.gotev.speech.SpeechDelegate;
import net.gotev.speech.SpeechRecognitionNotAvailable;
import net.gotev.speech.SpeechUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * The Channel header presenter class.
 *
 */
public class ChannelHeaderPresenter extends RowPresenter {
    private static final String TAG = ChannelHeaderPresenter.class.getSimpleName();
    private static final String EXTRA_LEANBACK_BADGE_PRESENT = "LEANBACK_BADGE_PRESENT";
    private static final int REQUEST_SPEECH = 0x00000010;
    private static final int RESULTS_CHANGED = 0x1;
    private static final int QUERY_COMPLETE = 0x2;
    private boolean mIsKeyboardAutoShowEnabled;
    private boolean mIsKeyboardFixEnabled;
    private Drawable mBadgeDrawable;
    private int mStatus;
    private String mTitle;

    /**
     * The Channel header provider interface.
     *
     */
    public interface ChannelHeaderProvider {
        boolean onSearchChange(String newQuery);
        boolean onSearchSubmit(String query);
        void onSearchSettingsClicked();
        String getChannelTitle();
    }

    /**
     * The Channel header callback class.
     *
     */
    public static class ChannelHeaderCallback extends Row implements ChannelHeaderProvider {
        /**
         * On search change.
         *
         * @param newQuery The New query.
         * @return True if on search change, false otherwise.
         */
        @Override
        public boolean onSearchChange(String newQuery) {
            return false;
        }

        /**
         * On search submit.
         *
         * @param query The Query.
         * @return True if on search submit, false otherwise.
         */
        @Override
        public boolean onSearchSubmit(String query) {
            return false;
        }

        /**
         * On search settings clicked.
         *
         */
        @Override
        public void onSearchSettingsClicked() {

        }

        /**
         * Gets Channel title.
         *
         * @return The string value.
         */
        @Override
        public String getChannelTitle() {
            return null;
        }
    }

    @Override
    protected ViewHolder createRowViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View channelHeader = inflater.inflate(R.layout.channel_header, parent, false);
        init(channelHeader);

        setSelectEffectEnabled(ViewUtil.ROW_SELECT_EFFECT_ENABLED);

        return new ViewHolder(channelHeader);
    }

    private void init(View header) {
        Context context = header.getContext();
        SearchData searchData = SearchData.instance(context);
        mIsKeyboardAutoShowEnabled = searchData.isKeyboardAutoShowEnabled();
        mIsKeyboardFixEnabled = searchData.isKeyboardFixEnabled();
        SearchBar searchBar = header.findViewById(R.id.lb_search_bar);
        SearchOrbView searchOrbView = searchBar.findViewById(R.id.lb_search_bar_search_orb);
        SpeechOrbView speechOrbView = searchBar.findViewById(R.id.lb_search_bar_speech_orb);
        SearchEditText searchTextEditor = searchBar.findViewById(R.id.lb_search_text_editor);
        SearchOrbView searchSettingsOrbView = searchBar.findViewById(R.id.search_settings_orb);
        // Default recognizer. Used when there's no speech callbacks specified.
        searchBar.setSpeechRecognizer(SpeechRecognizer.createSpeechRecognizer(context));
        searchBar.setOnFocusChangeListener((v, focused) -> {
            Log.d(TAG, "search bar focused");
        });
        searchBar.setPermissionListener(() -> PermissionHelpers.verifyMicPermissions(context));
        searchTextEditor.setSelectAllOnFocus(true); // Select all on focus (easy clear previous search)
        searchTextEditor.setOnFocusChangeListener((v, focused) -> {
            Log.d(TAG, "on search field focused");

            if (mIsKeyboardAutoShowEnabled && focused) {
                Helpers.showKeyboardAlt(v.getContext(), v);
            }
        });

        if (mIsKeyboardFixEnabled) {
            KeyHelpers.fixEnterKey(searchTextEditor);
        }

        searchOrbView.setOnFocusChangeListener((v, focused) -> {
            if (focused) {
                Helpers.hideKeyboard(context, v);
            }
        });

        searchSettingsOrbView.setOnFocusChangeListener((v, focused) -> {
            if (focused) {
                Helpers.hideKeyboard(context, v);
            }
        });

        OnFocusChangeListener previousListener = speechOrbView.getOnFocusChangeListener();
        speechOrbView.setOnFocusChangeListener((v, focused) -> {
            if (!focused) {
                stopSpeechService(context);
            }

            // Fix: Enable edit field dynamic style: white/grey, listening/non listening
            if (previousListener != null) {
                previousListener.onFocusChange(v, focused);
            }
        });
    }

    @Override
    protected void onBindRowViewHolder(ViewHolder vh, Object item) {
        super.onBindRowViewHolder(vh, item);

        ChannelHeaderProvider provider = (ChannelHeaderProvider) item;
        SearchBar searchBar = vh.view.findViewById(R.id.lb_search_bar);
        Context context = searchBar.getContext();
        SearchOrbView searchOrbView = searchBar.findViewById(R.id.lb_search_bar_search_orb);
        SpeechOrbView speechOrbView = searchBar.findViewById(R.id.lb_search_bar_speech_orb);
        SearchEditText searchTextEditor = searchBar.findViewById(R.id.lb_search_text_editor);
        SearchOrbView searchSettingsOrbView = searchBar.findViewById(R.id.search_settings_orb);
        String channelName = provider.getChannelTitle();
        searchBar.setTitle(channelName != null ? channelName : context.getString(R.string.content_type_channel));
        switch (SearchData.instance(context).getSpeechRecognizerType()) {
            case SearchData.SPEECH_RECOGNIZER_SYSTEM:
                // NOP
                break;
            case SearchData.SPEECH_RECOGNIZER_INTENT:
                searchBar.setSpeechRecognizer(null);
                searchBar.setSpeechRecognitionCallback(new RecognizerIntentCallback(context, provider, searchBar));
                break;
            case SearchData.SPEECH_RECOGNIZER_GOTEV:
                searchBar.setSpeechRecognizer(null);
                Speech.init(context);
                searchBar.setSpeechRecognitionCallback(new GotevCallback(context, provider, searchBar, speechOrbView));
                break;
        }
        searchBar.setSearchBarListener(new SearchBar.SearchBarListener() {
            /**
             * On search query change.
             *
             * @param query The Query.
             */
            @Override
            public void onSearchQueryChange(String query) {
                if (BuildConfig.DEBUG) Log.v(TAG, String.format("onSearchQueryChange %s %s", query,
                        null == provider ? "(null)" : provider));
                if (null != provider) {
                    retrieveResults(provider, query);
                }
            }

            /**
             * On search query submit.
             *
             * @param query The Query.
             */
            @Override
            public void onSearchQuerySubmit(String query) {
                if (BuildConfig.DEBUG) Log.v(TAG, String.format("onSearchQuerySubmit %s", query));
                submitQuery(provider, query);
            }

            /**
             * On keyboard dismiss.
             *
             * @param query The Query.
             */
            @Override
            public void onKeyboardDismiss(String query) {
                if (BuildConfig.DEBUG) Log.v(TAG, String.format("onKeyboardDismiss %s", query));
                // MOD: don't focus on results row after hiding keyboard
                //queryComplete();
            }
        });
        searchTextEditor.addTextChangedListener(new TextWatcher() {
            /**
             * Before text changed.
             *
             * @param s The S.
             * @param start The Start.
             * @param count The Count.
             * @param after The After.
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // NOP
            }

            /**
             * On text changed.
             *
             * @param s The S.
             * @param start The Start.
             * @param before The Before.
             * @param count The Count.
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // NOP
            }

            /**
             * After text changed.
             *
             * @param s The S.
             */
            @Override
            public void afterTextChanged(Editable s) {
                //Utils.enableScreensaver(getActivity(), true);
            }
        });
        searchOrbView.setOnOrbClickedListener(v -> submitQuery(provider, getSearchBarText(searchTextEditor)));
        searchSettingsOrbView.setOnOrbClickedListener(v -> provider.onSearchSettingsClicked());

        if (null != mBadgeDrawable) {
            setBadgeDrawable(searchBar, mBadgeDrawable);
        }

        if (null != mTitle) {
            setTitle(searchBar, mTitle);
        }
    }

    private final class RecognizerIntentCallback implements SpeechRecognitionCallback {
        private final Context mContext;
        private final ChannelHeaderProvider mProvider;
        private final SearchBar mSearchBar;

        /**
         * Recognizer intent callback.
         *
         * @param context The application context.
         * @param provider The Provider.
         * @param searchBar The Search bar.
         */
        public RecognizerIntentCallback(Context context, ChannelHeaderProvider provider, SearchBar searchBar) {
            mContext = context;
            mProvider = provider;
            mSearchBar = searchBar;
        }

        /**
         * Recognize speech.
         *
         */
        @Override
        public void recognizeSpeech() {
            if (PermissionHelpers.hasMicPermissions(mContext)) {
                MessageHelpers.showMessage(mContext, R.string.disable_mic_permission);
            }

            try {
                if (mContext instanceof MotherActivity) {
                    ((MotherActivity) mContext).addOnResult(this::onActivityResult);
                    ((MotherActivity) mContext).startActivityForResult(getRecognizerIntent(mSearchBar), REQUEST_SPEECH);
                }
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, "Cannot find activity for speech recognizer", e);
            } catch (NullPointerException e) {
                Log.e(TAG, "Speech recognizer can't obtain applicationInfo", e);
            }
        }

        private void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_SPEECH) {
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        String result = getRecognizerResult(data);
                        if (result != null) {
                            submitQuery(mProvider, result);
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "Recognizer canceled");
                        break;
                }
            }
        }
    }

    private final class GotevCallback implements SpeechRecognitionCallback {
        private final Context mContext;
        private final ChannelHeaderProvider mProvider;
        private final SearchBar mSearchBar;
        private final SpeechOrbView mSpeechOrbView;

        /**
         * Gotev callback.
         *
         * @param context The application context.
         * @param provider The Provider.
         * @param searchBar The Search bar.
         * @param speechOrbView The Speech orb view.
         */
        public GotevCallback(Context context, ChannelHeaderProvider provider, SearchBar searchBar, SpeechOrbView speechOrbView) {
            mContext = context;
            mProvider = provider;
            mSearchBar = searchBar;
            mSpeechOrbView = speechOrbView;
        }

        /**
         * Recognize speech.
         *
         */
        @Override
        public void recognizeSpeech() {
            try {
                // you must have android.permission.RECORD_AUDIO granted at this point
                PermissionHelpers.verifyMicPermissions(mContext);
                Speech.getInstance().startListening(new SpeechDelegate() {
                    /**
                     * On start of speech.
                     *
                     */
                    @Override
                    public void onStartOfSpeech() {
                        com.liskovsoft.sharedutils.mylogger.Log.i(TAG, "speech recognition is now active");
                        showListening(mSpeechOrbView);
                    }

                    /**
                     * On speech rms changed.
                     *
                     * @param value The Value.
                     */
                    @Override
                    public void onSpeechRmsChanged(float value) {
                        com.liskovsoft.sharedutils.mylogger.Log.d(TAG, "rms is now: " + value);
                    }

                    /**
                     * On speech partial results.
                     *
                     * @param results The Results.
                     */
                    @Override
                    public void onSpeechPartialResults(List<String> results) {
                        StringBuilder str = new StringBuilder();
                        for (String res : results) {
                            str.append(res).append(" ");
                        }

                        String result = str.toString().trim();
                        com.liskovsoft.sharedutils.mylogger.Log.i(TAG, "partial result: " + result);
                        applyExternalQuery(mProvider, mSearchBar, result, true);

                        showNotListening(mSpeechOrbView);
                    }

                    /**
                     * On speech result.
                     *
                     * @param result The Result.
                     */
                    @Override
                    public void onSpeechResult(String result) {
                        com.liskovsoft.sharedutils.mylogger.Log.i(TAG, "result: " + result);
                        applyExternalQuery(mProvider, mSearchBar, result, true);

                        showNotListening(mSpeechOrbView);
                    }
                });
            } catch (SpeechRecognitionNotAvailable | GoogleVoiceTypingDisabledException exc) {
                com.liskovsoft.sharedutils.mylogger.Log.e(TAG, "Speech recognition is not available on this device!");
                // You can prompt the user if he wants to install Google App to have
                // speech recognition, and then you can simply call:
                try {
                    SpeechUtil.redirectUserToGoogleAppOnPlayStore(mContext);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Returns an intent that can be used to request speech recognition.
     * Built from the base {@link RecognizerIntent#ACTION_RECOGNIZE_SPEECH} plus
     * extras:
     *
     * <ul>
     * <li>{@link RecognizerIntent#EXTRA_LANGUAGE_MODEL} set to
     * {@link RecognizerIntent#LANGUAGE_MODEL_FREE_FORM}</li>
     * <li>{@link RecognizerIntent#EXTRA_PARTIAL_RESULTS} set to true</li>
     * <li>{@link RecognizerIntent#EXTRA_PROMPT} set to the search bar hint text</li>
     * </ul>
     */
    private Intent getRecognizerIntent(SearchBar searchBar) {
        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        if (searchBar != null && searchBar.getHint() != null) {
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, searchBar.getHint());
        }
        recognizerIntent.putExtra(EXTRA_LEANBACK_BADGE_PRESENT, mBadgeDrawable != null);
        return recognizerIntent;
    }

    private String getRecognizerResult(Intent intent) {
        ArrayList<String> matches = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        return matches != null && matches.size() > 0 ? matches.get(0) : null;
    }

    private void submitQuery(ChannelHeaderProvider provider, String query) {
        if (query == null) {
            return;
        }

        if (null != provider) {
            provider.onSearchSubmit(query);
        }
    }

    private String getSearchBarText(SearchEditText searchTextEditor) {
        return searchTextEditor.getText().toString();
    }

    private void retrieveResults(ChannelHeaderProvider provider, String searchQuery) {
        if (BuildConfig.DEBUG) Log.v(TAG, "retrieveResults " + searchQuery);
        if (provider.onSearchChange(searchQuery)) {
            mStatus &= ~QUERY_COMPLETE;
        }
    }

    private void stopSpeechService(Context context) {
        // Note: Other services don't need to be stopped

        if (SearchData.instance(context).getSpeechRecognizerType() != SearchData.SPEECH_RECOGNIZER_GOTEV) {
            return;
        }

        try {
            Speech.getInstance().stopListening();
        } catch (IllegalArgumentException | NoSuchMethodError e) { // Speech service not registered/Android 4 (no such method)
            e.printStackTrace();
        }
    }

    private void setTitle(SearchBar searchBar, String title) {
        mTitle = title;
        if (null != searchBar) {
            searchBar.setTitle(title);
        }
    }

    private void setBadgeDrawable(SearchBar searchBar, Drawable drawable) {
        mBadgeDrawable = drawable;
        if (null != searchBar) {
            searchBar.setBadgeDrawable(drawable);
        }
    }

    private void applyExternalQuery(ChannelHeaderProvider provider, SearchBar mSearchBar, String query, boolean submit) {
        if (query == null || mSearchBar == null) {
            return;
        }
        mSearchBar.setSearchQuery(query);
        if (submit) {
            submitQuery(provider, query);
        }
    }

    private void showListening(SpeechOrbView speechOrbView) {
        if (speechOrbView != null) {
            speechOrbView.showListening();
        }
    }

    private void showNotListening(SpeechOrbView speechOrbView) {
        if (speechOrbView != null) {
            speechOrbView.showNotListening();
        }

        //if (mSearchTextEditor != null) {
        //    // Hide "Speak to search" when not listening
        //    mSearchTextEditor.setHint("");
        //}
    }
}
