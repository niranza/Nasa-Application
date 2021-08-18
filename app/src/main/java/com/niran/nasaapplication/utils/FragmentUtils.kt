package com.niran.nasaapplication.utils

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.niran.nasaapplication.R
import com.niran.nasaapplication.ui.NasaActivity

class FragmentUtils {
    companion object {

        fun Fragment.nasaViewModel() = (activity as NasaActivity).viewModel

        fun Fragment.showSwipeToRefreshSnackBar(onDismiss: () -> Unit) = view?.let {
            try {
                val view = it.findViewById<CoordinatorLayout>(R.id.layout_snack_bar_top)
                Snackbar.make(view, R.string.swipe_to_refresh, Snackbar.LENGTH_INDEFINITE).apply {
                    setAction(R.string.close) { onDismiss() }
                    show()
                }
            } catch (e: Exception) {
                throw NullPointerException("Fragment ${this::class.java.name} has no top snack bar layout")
            }
        }

        fun Fragment.showUndoSnackBar(onUndo: () -> Unit) = view?.let {
            try {
                val view = it.findViewById<CoordinatorLayout>(R.id.layout_snack_bar_bottom)
                Snackbar.make(view, R.string.picture_deleted, Snackbar.LENGTH_LONG).apply {
                    setAction(R.string.undo) { onUndo() }
                    show()
                }
            } catch (e: Exception) {
                throw NullPointerException("Fragment ${this::class.java.name} has no bottom snack bar layout")
            }
        }
    }
}