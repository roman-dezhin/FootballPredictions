package biz.ddroid.footballpredictions.di

import android.app.Application

object DI {
    sealed class Config {
        object RELEASE : Config()
        object TEST : Config()
    }

    fun initialize(app: Application, configuration: Config = Config.RELEASE) {
        NetworkModule.initialize(app)
        MainModule.initialize(app, configuration)
    }
}