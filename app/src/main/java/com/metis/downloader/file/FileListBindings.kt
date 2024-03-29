package com.metis.downloader.file

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.metis.downloader.data.VideoFile

/**
 * [BindingAdapter]s for the [VideoFile]s list.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<VideoFile>) {
  (listView.adapter as FilesAdapter).submitList(items)
}

@BindingAdapter("app:completedFile")
fun setStyle(textView: TextView, enabled: Boolean) {
  if (enabled) {
    textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
  } else {
    textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
  }
}
