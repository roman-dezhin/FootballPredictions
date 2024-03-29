package biz.ddroid.data.cache

import android.content.Context
import biz.ddroid.data.entity.MatchesEntity
import biz.ddroid.data.entity.PredictionEntity
import biz.ddroid.data.entity.ResultEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ResultsCache(context: Context) : MatchesCache {

    private val sharedPreferences = context.getSharedPreferences(
        "MatchesCache",
        Context.MODE_PRIVATE
    )
    private val gson = Gson()
    private val key = "results"

    override fun put(matches: List<MatchesEntity>) {
        val json = gson.toJson(matches)
        sharedPreferences.edit().putString(key, json).apply()
    }

    override fun get(): List<ResultEntity> {
        val json = sharedPreferences.getString(key, "")
        val type = object : TypeToken<List<ResultEntity>>() {
        }.type
        return Gson().fromJson(json, type)
    }

    override fun isCached(): Boolean {
        val value = sharedPreferences.getString(key, null)
        return value?.isNotEmpty() ?: false
    }
}