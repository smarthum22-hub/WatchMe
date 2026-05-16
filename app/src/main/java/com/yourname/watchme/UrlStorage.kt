package com.yourname.watchme

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object UrlStorage {
    private const val PREFS_NAME = "saved_urls"
    private const val KEY_URLS = "url_list"

    fun getUrls(context: Context): MutableList<String> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_URLS, "[]")
        val type = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(json, type)
    }

    fun addUrl(context: Context, url: String) {
        val urls = getUrls(context)
        if (!urls.contains(url)) {
            urls.add(0, url)
            saveUrls(context, urls)
        }
    }

    fun removeUrl(context: Context, url: String) {
        val urls = getUrls(context)
        urls.remove(url)
        saveUrls(context, urls)
    }

    private fun saveUrls(context: Context, urls: List<String>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_URLS, Gson().toJson(urls)).apply()
    }
}
