package biz.ddroid.data.repository.datasource

import biz.ddroid.data.cache.UserCache
import biz.ddroid.data.entity.LoggedUser
import biz.ddroid.data.entity.LoginRequest
import biz.ddroid.data.net.ConnectionManager
import biz.ddroid.data.net.ServiceInterceptor
import biz.ddroid.data.net.UserService
import biz.ddroid.domain.data.LoginRequestData
import biz.ddroid.domain.data.UserData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.NoLoggedInUserException
import biz.ddroid.domain.interactor.Result
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class UserDataStore(private val connectionManager: ConnectionManager,
                    private val userService: UserService,
                    private val userCache: UserCache) {

    suspend fun getUser(): Result<UserData> {
        if (userCache.isCached()) {
            val user = userCache.get()
            ServiceInterceptor.setToken(user.token)
            return Result.Success(UserData(user.id, user.name, user.avatar))
        }

        return Result.Error(NoLoggedInUserException())
    }

    suspend fun login(requestData: LoginRequestData): Result<UserData> {
        if (connectionManager.isNetworkAbsent()) {
            return Result.Error(NetworkConnectionException())
        }

        try {
            // TODO: handle loggedInUser authentication
            val response = userService.loginAsync(LoginRequest(requestData.name, requestData.password))
            val result = response.await()
            val loginResponse = result.body()!!
            ServiceInterceptor.setToken(loginResponse.token)
            userCache.put(LoggedUser(
                loginResponse.userEntity.id,
                loginResponse.userEntity.name,
                loginResponse.userEntity.email,
                loginResponse.userEntity.picture,
                loginResponse.token
            ))
            return Result.Success(
                UserData(
                    loginResponse.userEntity.id,
                    loginResponse.userEntity.name,
                    loginResponse.userEntity.picture
                )
            )
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}