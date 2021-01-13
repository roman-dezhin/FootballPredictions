package biz.ddroid.footballpredictions.di

import android.app.Application
import biz.ddroid.data.cache.NewMatchesCache
import biz.ddroid.data.entity.mapper.NewMatchDataMapper
import biz.ddroid.data.net.MatchesService
import biz.ddroid.data.repository.NewMatchesRepositoryImpl
import biz.ddroid.data.repository.datasource.CloudNewMatchesDataStore
import biz.ddroid.data.repository.datasource.DiskNewMatchesDataStore
import biz.ddroid.data.repository.datasource.NewMatchesDataStoreFactoryImpl
import biz.ddroid.domain.interactor.NewMatchesInteractor
import biz.ddroid.domain.interactor.NewMatchesInteractorImpl
import biz.ddroid.domain.repository.NewMatchesRepository
import java.lang.UnsupportedOperationException

object MainModule {

    private lateinit var config: DI.Config
    private lateinit var newMatchesCache: NewMatchesCache

    private var newMatchesRepository: NewMatchesRepository? = null
    private var newMatchesInteractor: NewMatchesInteractor? = null

    fun initialize(app: Application, configuration: DI.Config = DI.Config.RELEASE) {
        config = configuration
        newMatchesCache = NewMatchesCache(app)
    }

    fun getNewMatchesInteractorImpl(): NewMatchesInteractor {
        if (config == DI.Config.RELEASE && newMatchesInteractor == null)
            newMatchesInteractor = makeNewMatchesInteractor(getMatchesRepository())
        return newMatchesInteractor!!
    }

    fun setNewMatchesInteractor(interactor: NewMatchesInteractor) =
        if (config == DI.Config.TEST) {
            newMatchesInteractor = interactor
        } else {
            throw UnsupportedOperationException("one cannot simply set interactor if not a DI.Config.TEST")
        }

    //region private methods

    private fun makeNewMatchesInteractor(repositoryNew: NewMatchesRepository) =
        NewMatchesInteractorImpl(repositoryNew)

    private fun getMatchesRepository(): NewMatchesRepository {
        if (newMatchesRepository == null)
            newMatchesRepository = NewMatchesRepositoryImpl(getNewMatchesDataStoreFactory(), NewMatchDataMapper())
        return newMatchesRepository!!
    }

    private fun getDiskNewMatchesDataStore() = DiskNewMatchesDataStore(newMatchesCache)
    private fun getCloudNewMatchesDataStore() = CloudNewMatchesDataStore(
        NetworkModule.connectionManager,
        NetworkModule.getService(MatchesService::class.java),
        newMatchesCache
    )

    private fun getNewMatchesDataStoreFactory() =
        NewMatchesDataStoreFactoryImpl(newMatchesCache, getDiskNewMatchesDataStore(), getCloudNewMatchesDataStore())

    //endregion
}