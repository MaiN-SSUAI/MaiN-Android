package com.MaiN.main_android.View.Notice

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.MaiN.main_android.R

class Funsys_WebView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.funsys_webview)

        val webView = findViewById<WebView>(R.id.funsys_web)
        webView.webViewClient = WebViewClient()

        val url = intent.getStringExtra("url")

        if(url!=null) {
            webView.loadUrl(url)
        }

        val BackButton = findViewById<ImageView>(R.id.imageView8)
        BackButton.setOnClickListener{
            finish()
        }
    }
}