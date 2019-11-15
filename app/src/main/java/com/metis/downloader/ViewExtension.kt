package com.metis.downloader

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
  Snackbar.make(this, snackbarText, timeLength).run {
    addCallback(object : Snackbar.Callback() {
      override fun onShown(sb: Snackbar?) {
      }

      override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
      }
    })
    show()
  }
}


fun View.setupSnackbar(
  lifecycleOwner: LifecycleOwner,
  snackbarEvent: LiveData<Event<Int>>,
  timeLength: Int
){
  snackbarEvent.observe(lifecycleOwner, Observer { event ->
    event.getContentIfNotHandled()?.let {
      showSnackbar(context.getString(it), timeLength)
    }
  })
}
