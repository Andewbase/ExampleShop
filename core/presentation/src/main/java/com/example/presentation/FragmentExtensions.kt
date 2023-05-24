@file:Suppress("DEPRECATION", "UNCHECKED_CAST")

package com.example.presentation

import androidx.fragment.app.Fragment

const val ARG_SCREEN = "screen"

/**
 * Get screen args attached to the [Fragment].
 */
fun <T : BaseScreen> Fragment.args(): T {
    return requireArguments().getSerializable(ARG_SCREEN) as? T
        ?: throw IllegalStateException("Screen args don't exist")
}