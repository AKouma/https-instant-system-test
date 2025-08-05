package com.alkursi.presentation.common

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

fun openCustomTab(context: Context, url: String) {
    try {
        val builder = CustomTabsIntent.Builder()

        builder.setColorScheme(CustomTabsIntent.COLOR_SCHEME_SYSTEM)
        builder.setUrlBarHidingEnabled(true)
        builder.setShowTitle(true)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, url.toUri())
    } catch (e: Exception) {
        Log.e("ClickableLinkText", "Impossible d'ouvrir le lien: $url", e)
        openDeviceBrowsers(url, context)
    }
}

private fun openDeviceBrowsers(url: String, context: Context) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
    } catch (ex: Exception) {
        Log.e("ClickableLinkText", "Impossible d'ouvrir le lien: $url", ex)
    }
}