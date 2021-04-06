package biz.ddroid.data.repository

import biz.ddroid.data.repository.datasource.UserDataStore
import biz.ddroid.domain.data.LoginRequestData
import biz.ddroid.domain.data.UserData
import biz.ddroid.domain.interactor.Result
import biz.ddroid.domain.repository.UserRepository
import java.lang.Exception

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class UserRepositoryImpl(val dataStore: UserDataStore): UserRepository {

    // in-memory cache of the loggedInUser object
    var user: UserData? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataStore.logout()
    }

    override suspend fun getUser(): Result<UserData> {
        return if (isLoggedIn) {
            Result.Success(user!!)
        } else {
            dataStore.getUser()
        }
    }

    override suspend fun login(requestData: LoginRequestData): Result<UserData> {
        // handle login
        val result = try {
            dataStore.login(requestData)
        }
        catch (e: Exception) {
            return Result.Error(e)
        }

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: UserData) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}