package com.yourname.watchme

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UrlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textView: TextView = itemView.findViewById(R.id.urlText)

    fun bind(url: String, onClick: (String) -> Unit, onLongClick: (String) -> Unit) {
        textView.text = url
        itemView.setOnClickListener { onClick(url) }
        itemView.setOnLongClickListener {
            onLongClick(url)
            true
        }
    }
}
