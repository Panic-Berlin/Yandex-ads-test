package com.exemple.yandexadstest

import android.app.Application

class App : Application() {
    companion object{
        lateinit var instance: App
            private set
    }

    val yandexAdWorker = YandexAdWorker(this)

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
