package biz.ddroid.footballpredictions.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ddroid.footballpredictions.core.mvvm.SingleLiveEvent
import biz.ddroid.footballpredictions.di.MainModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    companion object {
        private const val SPLASH_DELAY_IN_MS = 100L
    }

    private val _splashNavCommand = SingleLiveEvent<SplashNavCommand?>()

    val splashNavCommand: LiveData<SplashNavCommand?> = _splashNavCommand


    init {
        // Add special delay for splash screen
        viewModelScope.launch {
            delay(SPLASH_DELAY_IN_MS)

            val navCommand = if (MainModule.isUserCached()) {
                SplashNavCommand.NAVIGATE_TO_MAIN
            } else {
                SplashNavCommand.NAVIGATE_TO_LOGIN
            }
            _splashNavCommand.postValue(navCommand)
        }
    }

}