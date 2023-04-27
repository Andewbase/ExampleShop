package com.example.exampleshop.app.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.exampleshop.R


fun Fragment.findTopNavController(): NavController {
    val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}
