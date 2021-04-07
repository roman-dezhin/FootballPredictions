package biz.ddroid.footballpredictions.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ddroid.domain.data.NewMatchData
import biz.ddroid.footballpredictions.di.MainModule
import kotlinx.coroutines.launch
import java.lang.Exception
import biz.ddroid.domain.interactor.Result
import kotlinx.coroutines.Dispatchers

class NewMatchesViewModel : ViewModel() {

    private val interactor = MainModule.getNewMatchesInteractorImpl()

    var error = MutableLiveData<String>()

    private val matches: MutableLiveData<List<NewMatchData>> by lazy {
        MutableLiveData<List<NewMatchData>>().also {
            loadMatches()
        }
    }

    fun getMatches(): LiveData<List<NewMatchData>> {
        return matches
    }

    private fun loadMatches()  = viewModelScope.launch(Dispatchers.IO) {
        val result = try {
            interactor.fetch(false)
        }
        catch (e: Exception) {
            Result.Error(e)
        }
        when (result) {
            is Result.Success<List<NewMatchData>> -> matches.postValue(result.data)
            else -> error.postValue(result.toString())
        }

    }
}
