package biz.ddroid.data.cache

import android.content.Context
import biz.ddroid.data.entity.LoggedUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserCacheImpl(context: Context) : UserCache {
    private val sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val key = "user"

    override fun put(user: LoggedUser) {
        val json = gson.toJson(user)
        sharedPreferences.edit().putString(key, json).apply()
    }

    override fun get(): LoggedUser {
        val json = sharedPreferences.getString(key, "")
        val type = object : TypeToken<LoggedUser>() {}.type
        return Gson().fromJson(json, type)
    }

    override fun isCached(): Boolean {
        val value = sharedPreferences.getString(key, null)
        return value?.isNotEmpty() ?: false
    }
}