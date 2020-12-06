package com.ariefzuhri.moveecatalog.preference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import com.ariefzuhri.moveecatalog.MainActivity;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.reminder.DailyReminder;
import com.ariefzuhri.moveecatalog.reminder.ReleaseReminder;
import static com.ariefzuhri.moveecatalog.preference.PreferenceActivity.EXTRA_CHANGE_LANGUAGE;
import static com.ariefzuhri.moveecatalog.preference.PreferenceActivity.RESULT_CHANGE_LANGUAGE;
import static com.ariefzuhri.moveecatalog.reminder.ReminderHelper.REQUEST_DAILY_REMINDER;
import static com.ariefzuhri.moveecatalog.reminder.ReminderHelper.REQUEST_RELEASE_REMINDER;
import static com.ariefzuhri.moveecatalog.reminder.ReminderHelper.cancelReminder;

public class PreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private String LANGUAGE, DAILY_REMINDER, RELEASE_TODAY_REMINDER;

    private ListPreference languagePreference;
    private SwitchPreferenceCompat dailyReminderPreference;
    private SwitchPreferenceCompat releaseTodayPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        init();
        setSummaries();
    }

    private void init() {
        LANGUAGE = getResources().getString(R.string.key_language);
        DAILY_REMINDER = getResources().getString(R.string.key_daily_reminder);
        RELEASE_TODAY_REMINDER = getResources().getString(R.string.key_release_today_reminder);

        languagePreference = findPreference(LANGUAGE);
        dailyReminderPreference = findPreference(DAILY_REMINDER);
        releaseTodayPreference = findPreference(RELEASE_TODAY_REMINDER);
    }

    private void setSummaries(){
        SharedPreferences sp = getPreferenceManager().getSharedPreferences();
        languagePreference.setSummary(sp.getString(LANGUAGE, getResources().getStringArray(R.array.language)[0]));
        dailyReminderPreference.setChecked(sp.getBoolean(DAILY_REMINDER, false));
        releaseTodayPreference.setChecked(sp.getBoolean(RELEASE_TODAY_REMINDER, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        DailyReminder dailyReminder = new DailyReminder();
        ReleaseReminder releaseReminder = new ReleaseReminder();

        if (key.equals(LANGUAGE)){
            languagePreference.setSummary(sp.getString(LANGUAGE, getResources().getStringArray(R.array.language)[0]));

            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(EXTRA_CHANGE_LANGUAGE, true);
            getActivity().setResult(RESULT_CHANGE_LANGUAGE, intent);
            getActivity().finish();
        }

        if (key.equals(DAILY_REMINDER)){
            dailyReminderPreference.setChecked(sp.getBoolean(DAILY_REMINDER, false));

            if (sp.getBoolean(DAILY_REMINDER, false)){
                dailyReminder.setDailyReminder(getContext(),
                        getResources().getString(R.string.daily_reminder_title),
                        getResources().getString(R.string.daily_reminder_message)
                );
            } else{
                cancelReminder(getContext(), REQUEST_DAILY_REMINDER);
            }
        }

        if (key.equals(RELEASE_TODAY_REMINDER)){
            releaseTodayPreference.setChecked(sp.getBoolean(RELEASE_TODAY_REMINDER, false));

            if (sp.getBoolean(RELEASE_TODAY_REMINDER, false)){
                releaseReminder.setReleaseReminder(getContext(), getResources().getString(R.string.today_release_reminder_title));
            } else{
                cancelReminder(getContext(), REQUEST_RELEASE_REMINDER);
            }
        }
    }
}
