package com.yourname.watchme

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UrlAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Edge-to-edge
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        )

        recyclerView = findViewById(R.id.urlRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = UrlAdapter(
            urls = UrlStorage.getUrls(this),
            onItemClick = { url -> openUrl(url) },
            onItemLongClick = { url -> showDeleteDialog(url) },
            onSaveClick = { rawUrl ->
                val fullUrl = if (!rawUrl.startsWith("http")) "https://$rawUrl" else rawUrl
                UrlStorage.addUrl(this, fullUrl)
                adapter.updateUrls(UrlStorage.getUrls(this))
                openUrl(fullUrl)
            }
        )
        recyclerView.adapter = adapter
    }

    private fun openUrl(url: String) {
        val intent = Intent(this, WebViewActivity::class.java).apply {
            putExtra("url", url)
        }
        startActivity(intent)
    }

    private fun showDeleteDialog(url: String) {
        AlertDialog.Builder(this)
            .setTitle("Remove URL")
            .setMessage("Delete this saved link?")
            .setPositiveButton("Yes") { _, _ ->
                UrlStorage.removeUrl(this, url)
                adapter.updateUrls(UrlStorage.getUrls(this))
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        adapter.updateUrls(UrlStorage.getUrls(this))
    }
}
