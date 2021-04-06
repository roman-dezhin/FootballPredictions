package biz.ddroid.domain.repository

import biz.ddroid.domain.data.LoginRequestData
import biz.ddroid.domain.data.UserData
import biz.ddroid.domain.interactor.Result

interface UserRepository {

    suspend fun getUser(): Result<UserData>

    suspend fun login(requestData: LoginRequestData): Result<UserData>
}