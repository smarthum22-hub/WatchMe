package com.yourname.watchme

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val urlInput: EditText = itemView.findViewById(R.id.urlInput)
    private val saveButton: Button = itemView.findViewById(R.id.saveButton)

    fun bind(onSave: (String) -> Unit) {
        saveButton.setOnClickListener {
            val url = urlInput.text.toString().trim()
            if (url.isNotEmpty()) {
                onSave(url)
                urlInput.text.clear()
            }
        }
    }
}
