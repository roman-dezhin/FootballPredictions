package biz.ddroid.footballpredictions

import android.app.Application
import biz.ddroid.footballpredictions.di.DI

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        DI.initialize(this)
        //SyncDataPeriodicJob().start()
    }
}