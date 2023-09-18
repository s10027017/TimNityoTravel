package com.example.tim.nityo.travel.base

import android.app.Application
import com.example.tim.nityo.travel.data.model.Language
import com.example.tim.nityo.travel.utils.SharedPreferencesSecret

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesSecret.createInstance(this)
        if(SharedPreferencesSecret.isFirstTimeLoginApp){
            SharedPreferencesSecret.language = Language.TradionnalChinese.language
            SharedPreferencesSecret.isFirstTimeLoginApp = false
        }
    }

}
