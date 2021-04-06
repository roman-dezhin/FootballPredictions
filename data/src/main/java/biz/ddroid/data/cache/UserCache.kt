package biz.ddroid.data.cache

import biz.ddroid.data.entity.LoggedUser

interface UserCache {
    fun put(user: LoggedUser)

    fun get(): LoggedUser

    fun isCached(): Boolean
}