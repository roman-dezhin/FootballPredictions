package biz.ddroid.footballpredictions.di

import android.app.Application
import biz.ddroid.data.cache.NewMatchesCache
import biz.ddroid.data.cache.UserCache
import biz.ddroid.data.cache.UserCacheImpl
import biz.ddroid.data.entity.mapper.NewMatchDataMapper
import biz.ddroid.data.net.MatchesService
import biz.ddroid.data.net.UserService
import biz.ddroid.data.repository.NewMatchesRepositoryImpl
import biz.ddroid.data.repository.UserRepositoryImpl
import biz.ddroid.data.repository.datasource.CloudNewMatchesDataStore
import biz.ddroid.data.repository.datasource.DiskNewMatchesDataStore
import biz.ddroid.data.repository.datasource.NewMatchesDataStoreFactoryImpl
import biz.ddroid.data.repository.datasource.UserDataStore
import biz.ddroid.domain.interactor.*
import biz.ddroid.domain.repository.NewMatchesRepository
import biz.ddroid.domain.repository.UserRepository

object MainModule {

    private lateinit var config: DI.Config
    private lateinit var newMatchesCache: NewMatchesCache
    private lateinit var userCache: UserCache

    private var newMatchesRepository: NewMatchesRepository? = null
    private var newMatchesInteractor: NewMatchesInteractor? = null

    private var userRepository: UserRepository? = null
    private var loginInteractor: LoginInteractor? = null
    private var userInteractor: UserInteractor? = null

    fun initialize(app: Application, configuration: DI.Config = DI.Config.RELEASE) {
        config = configuration
        newMatchesCache = NewMatchesCache(app)
        userCache = UserCacheImpl(app)
    }

    fun isUserCached(): Boolean = userCache.isCached()

    fun getNewMatchesInteractorImpl(): NewMatchesInteractor {
        if (config == DI.Config.RELEASE && newMatchesInteractor == null)
            newMatchesInteractor = makeNewMatchesInteractor(getMatchesRepository())
        return newMatchesInteractor!!
    }

    fun getUserInteractor(): UserInteractor {
        if (config == DI.Config.RELEASE && userInteractor == null)
            userInteractor = makeUserInteractor(getUserRepository())
        return userInteractor!!
    }

    fun getLoginInteractor(): LoginInteractor {
        if (config == DI.Config.RELEASE && loginInteractor == null)
            loginInteractor = makeLoginInteractor(getUserRepository())
        return loginInteractor!!
    }
    //region private methods

    private fun makeLoginInteractor(repository: UserRepository) =
        LoginInteractorImpl(repository)

    private fun makeUserInteractor(repository: UserRepository) =
        UserInteractorImpl(repository)

    private fun getUserRepository(): UserRepository {
        if (userRepository == null)
            userRepository = UserRepositoryImpl(UserDataStore(
                NetworkModule.connectionManager,
                NetworkModule.getService(UserService::class.java),
                userCache
            ))
        return userRepository!!
    }

    private fun makeNewMatchesInteractor(repository: NewMatchesRepository) =
        NewMatchesInteractorImpl(repository)

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