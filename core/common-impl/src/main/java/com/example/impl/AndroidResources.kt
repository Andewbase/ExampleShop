package com.example.impl

import android.content.Context
import com.example.core.Resources

class AndroidResources(
    private val applicationContext: Context
): Resources {

    override fun getString(id: Int): String {
        return applicationContext.getString(id)
    }

    override fun getString(id: Int, vararg placeholders: Any): String {
        return applicationContext.getString(id, placeholders)
    }
}