package com.yourname.watchme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UrlAdapter(
    private var urls: List<String>,
    private val onItemClick: (String) -> Unit,
    private val onItemLongClick: (String) -> Unit,
    private val onSaveClick: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_URL = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_URL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.header_url_input, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_url_card, parent, false)
            UrlViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(onSaveClick)
            is UrlViewHolder -> {
                val url = urls[position - 1]
                holder.bind(url, onItemClick, onItemLongClick)
            }
        }
    }

    override fun getItemCount(): Int = urls.size + 1

    fun updateUrls(newUrls: List<String>) {
        urls = newUrls
        notifyDataSetChanged()
    }
}
