package biz.ddroid.data.cache

import biz.ddroid.data.entity.MatchesEntity

interface MatchesCache {

    fun put(matches: List<MatchesEntity>)

    fun get(): List<MatchesEntity>

    fun isCached(): Boolean
}