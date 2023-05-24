package com.example.impl

import android.util.Log
import com.example.core.Logger

class AndroidLogger: Logger {
    override fun log(message: String) {
        Log.d("AndroidLogger", message)
    }

    override fun err(exception: Throwable, message: String?) {
        Log.e("AndroidLogger", message, exception)
    }
}