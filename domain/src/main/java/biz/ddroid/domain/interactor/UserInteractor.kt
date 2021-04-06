package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.UserData

interface UserInteractor {
    suspend fun fetch(): Result<UserData>
}