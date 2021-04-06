package biz.ddroid.footballpredictions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ddroid.domain.data.UserData
import biz.ddroid.domain.interactor.Result
import biz.ddroid.footballpredictions.di.MainModule
import kotlinx.coroutines.launch
import kotlin.Exception

class UserViewModel : ViewModel()  {

    private val interactor = MainModule.getUserInteractor()

    private val user: MutableLiveData<UserData> by lazy {
        MutableLiveData<UserData>().also {
            fetch()
        }
    }

    fun getUser(): LiveData<UserData> {
        return user
    }

    private fun fetch() = viewModelScope.launch {
        val result = try {
            interactor.fetch()
        }
        catch (e: Exception) {
            Result.Error(e)
        }
        when (result) {
            is Result.Success<UserData> -> user.postValue(result.data)
            else -> Result.Error(Exception(result.toString()))
        }
    }
}