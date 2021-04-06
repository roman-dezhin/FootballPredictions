package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.LoginRequestData
import biz.ddroid.domain.data.UserData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.repository.UserRepository
import java.lang.Exception

class LoginInteractorImpl(
    private val repository: UserRepository
) : LoginInteractor {

    override suspend fun login(requestData: LoginRequestData): Result<UserData> =
        try {
            repository.login(requestData)
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is NetworkConnectionException)
                Result.Error(NetworkConnectionException())
            else
                Result.Error(ServerUnavailableException())
        }
}