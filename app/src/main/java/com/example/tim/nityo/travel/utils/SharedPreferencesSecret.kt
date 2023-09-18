package com.example.tim.nityo.travel.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

open class SharedPreferencesSecret protected constructor(context: Context) {

    init {
        val masterKeyAlias = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        mSharedPreference = EncryptedSharedPreferences.create(
            context,
            PREF_FILE,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        private const val PREF_FILE = "NITYO"
        private const val PREF_KEY_STATUS_FIRST_TIME_LOGIN_APP = "PREF_KEY_STATUS_FIRST_TIME_LOGIN_APP"
        private const val PREF_KEY_SET_LANGUAGE = "PREF_KEY_SET_LANGUAGE"

        @Volatile
        protected var sInst: SharedPreferencesSecret? = null
        protected var mSharedPreference: SharedPreferences? = null

        fun createInstance(context: Context) {
            if (sInst == null) {
                sInst = SharedPreferencesSecret(context)
            }
        }

        /**
         * 首次開啟
         */
        var isFirstTimeLoginApp
            set(value) {
                mSharedPreference?.edit {
                    putBoolean(PREF_KEY_STATUS_FIRST_TIME_LOGIN_APP, value)
                }
            }
            get() = mSharedPreference?.getBoolean(PREF_KEY_STATUS_FIRST_TIME_LOGIN_APP, true) ?: true

        /**
         * 語言
         */
        var language
            set(value) {
                mSharedPreference?.edit {
                    putString(PREF_KEY_SET_LANGUAGE, value)
                }
            }
            get() = mSharedPreference?.getString(PREF_KEY_SET_LANGUAGE, "zh-tw") ?: ""

    }

}