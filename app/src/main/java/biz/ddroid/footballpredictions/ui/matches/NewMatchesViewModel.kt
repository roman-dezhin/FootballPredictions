package biz.ddroid.footballpredictions.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ddroid.domain.data.NewMatchData
import biz.ddroid.domain.interactor.Status
import biz.ddroid.footballpredictions.di.MainModule
import kotlinx.coroutines.launch

class NewMatchesViewModel : ViewModel() {

    private val interactor = MainModule.getNewMatchesInteractorImpl()

    private val matches: MutableLiveData<List<NewMatchData>> by lazy {
        MutableLiveData<List<NewMatchData>>().also {
            loadMatches()
        }
    }

    fun getMatches(): LiveData<List<NewMatchData>> {
        return matches
    }

    private fun loadMatches()  = viewModelScope.launch {
        val (status, list) = interactor.getNewMatches(false)
        when (status) {
            Status.NO_RESULTS -> matches.value = list //showScreenWithId(R.id.no_results)
            Status.NO_CONNECTION -> matches.value = list //showScreenWithId(R.id.no_connection)
            Status.SERVICE_UNAVAILABLE -> matches.value = list //showScreenWithId(R.id.service_unavailable)
            Status.SUCCESS -> matches.value = list //showScreenWithId(R.id.go_to_search_results)
        }

    }
}
