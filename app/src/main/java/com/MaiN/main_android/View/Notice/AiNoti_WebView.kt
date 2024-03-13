package com.MaiN.main_android.View.Notice

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.MaiN.main_android.R

class AiNoti_WebView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ainoti_webview)

        val webView = findViewById<WebView>(R.id.ainoti_web)
        webView.webViewClient = WebViewClient()

        val url = intent.getStringExtra("url")

        if (url != null) {
            webView.loadUrl(url)
        }

        val BackButton = findViewById<ImageView>(R.id.imageView4)
        BackButton.setOnClickListener {
            finish()
        }
    }
}