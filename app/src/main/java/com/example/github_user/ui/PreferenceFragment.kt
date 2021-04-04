package com.example.github_user.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.github_user.R
import com.example.github_user.receiver.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class PreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var REMINDER: String
    private lateinit var LANGUAGE: String

    private lateinit var reminderference: SwitchPreference
    private lateinit var languagePreference: Preference

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        init()
        setSummaries()

        alarmReceiver = AlarmReceiver()
        languagePreference.setOnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }
    }

    private fun init() {
        REMINDER = getString(R.string.key_reminder)
        LANGUAGE = getString(R.string.key_language)

        reminderference = findPreference<SwitchPreference>(REMINDER) as SwitchPreference
        languagePreference = findPreference<Preference>(LANGUAGE) as Preference
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        reminderference.isChecked = sh.getBoolean(REMINDER, false)

        val currentLanguage = Locale.getDefault().displayLanguage
        languagePreference.summary = currentLanguage
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == REMINDER) {
            val sh = preferenceManager.sharedPreferences
            if (sh.getBoolean(REMINDER, false)) {
                setAlarmTime()
            } else {
                alarmReceiver.cancelAlarm(
                    requireContext()
                )
            }
            setSummaries()
        }
    }

    private fun setAlarmTime() {
        val calendar: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
        }

        val dateFormat = SimpleDateFormat(AlarmReceiver.TIME_FORMAT, Locale.getDefault())
        val timeTaken = dateFormat.format(calendar.time)
        setRepeatAlarm(timeTaken)
    }

    private fun setRepeatAlarm(repeatTime: String) {
        alarmReceiver.setRepeatingAlarm(
            requireContext(),
            repeatTime
        )
    }
}