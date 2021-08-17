package com.niran.nasaapplication.utils

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.niran.nasaapplication.R

class FragmentUtils {
    companion object {
        fun Fragment.showSwipeToRefreshSnackBar() {
            val view = view?.findViewById<CoordinatorLayout>(R.id.layout_snack_bar) as View
            Snackbar.make(view, R.string.swipe_to_refresh, Snackbar.LENGTH_INDEFINITE).apply {
                setAction(R.string.close) { dismiss() }
                show()
            }
        }
    }
}