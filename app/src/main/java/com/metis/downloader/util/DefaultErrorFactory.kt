package com.metis.downloader.util

import android.content.Context
import com.metis.downloader.R
import com.tuga.konum.util.ErrorFactory
import javax.inject.Inject

class DefaultErrorFactory @Inject constructor(val context: Context) : ErrorFactory {
    override fun createEmptyErrorMessage(): String = context.getString(R.string.empty_state_message)

    override fun createApiErrorMessage(e: Exception): String = e.message.toString()
}
