package biz.ddroid.data.cache

import android.content.Context
import biz.ddroid.data.entity.MatchesEntity
import biz.ddroid.data.entity.PredictionEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PredictionsCache(context: Context) : MatchesCache {

    private val sharedPreferences = context.getSharedPreferences(
        "MatchesCache",
        Context.MODE_PRIVATE
    )
    private val gson = Gson()
    private val key = "predictions"

    override fun put(matches: List<MatchesEntity>) {
        val json = gson.toJson(matches)
        sharedPreferences.edit().putString(key, json).apply()
    }

    override fun get(): List<PredictionEntity> {
        val json = sharedPreferences.getString(key, "")
        val type = object : TypeToken<List<PredictionEntity>>() {
        }.type
        return Gson().fromJson(json, type)
    }

    override fun isCached(): Boolean {
        val value = sharedPreferences.getString(key, null)
        return value?.isNotEmpty() ?: false
    }
}