package biz.ddroid.footballpredictions.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ddroid.domain.data.PredictionData
import biz.ddroid.domain.data.ResultData
import biz.ddroid.domain.interactor.Result
import biz.ddroid.footballpredictions.di.MainModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CompletedMatchesViewModel : ViewModel() {

    private val interactor = MainModule.getResultsInteractorImpl()

    var error = MutableLiveData<String>()

    private val matches: MutableLiveData<List<ResultData>> by lazy {
        MutableLiveData<List<ResultData>>().also {
            loadMatches()
        }
    }

    fun getMatches(): LiveData<List<ResultData>> {
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
            is Result.Success<List<ResultData>> -> matches.postValue(result.data)
            else -> error.postValue(result.toString())
        }
    }
}
