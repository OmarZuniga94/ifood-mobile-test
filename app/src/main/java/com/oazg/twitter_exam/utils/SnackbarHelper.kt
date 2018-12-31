package com.oazg.twitter_exam.utils

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import com.oazg.twitter_exam.App
import com.oazg.twitter_exam.R

class SnackbarHelper {

    companion object {
        fun showSuccessSnackbar(view: View, text: String, time: Int) {
            val snack = Snackbar.make(view, text, time)
            val view = snack.view
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.BOTTOM
            view.layoutParams = params
            view.setBackgroundColor(App.getContext().resources.getColor(R.color.colorGreenTransparent))
            snack.show()
        }

        fun showErrorSnackbar(view: View, text: String, time: Int) {
            val snack = Snackbar.make(view, text, time)
            val view = snack.view
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.BOTTOM
            view.layoutParams = params
            view.setBackgroundColor(App.getContext().resources.getColor(R.color.colorRedTransparent))
            snack.show()
        }
    }
}