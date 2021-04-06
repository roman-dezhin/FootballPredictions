package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.UserData
import biz.ddroid.domain.repository.UserRepository

class UserInteractorImpl(private val repository: UserRepository): UserInteractor {

    override suspend fun fetch(): Result<UserData> =
        try {
            repository.getUser()
        } catch (e: Exception) {
            Result.Error(e)
        }

}