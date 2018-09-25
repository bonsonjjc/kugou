package com.kugou.commom.framwork

import androidx.fragment.app.Fragment

class FragmentStackManager {
    var fragment: androidx.fragment.app.Fragment? = null

    fun onBackPress(): Boolean {
        return popBackStack(fragment?.parentFragment)
    }

    private fun popBackStack(fragment: androidx.fragment.app.Fragment?): Boolean {
        if (fragment == null) return false
        val fragmentManager = fragment.childFragmentManager
        var popBackStackImmediate = fragmentManager.backStackEntryCount == 0
        if (popBackStackImmediate) {
            popBackStackImmediate = popBackStack(fragment.parentFragment)
        } else {
            this.fragment = fragment
            fragmentManager.popBackStackImmediate()
            if (!fragmentManager.fragments.isEmpty()) {
                this.fragment = fragmentManager.fragments[fragmentManager.fragments.size - 1]
            } else {
                this.fragment = fragment
            }
            return true
        }
        return popBackStackImmediate
    }



    fun push(fragment: androidx.fragment.app.Fragment?) {
        this.fragment = fragment
    }
}
