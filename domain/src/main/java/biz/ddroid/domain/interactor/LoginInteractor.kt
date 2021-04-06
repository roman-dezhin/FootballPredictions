package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.LoginRequestData
import biz.ddroid.domain.data.UserData

interface LoginInteractor {

    suspend fun login(requestData: LoginRequestData): Result<UserData>
}