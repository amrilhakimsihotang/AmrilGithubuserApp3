package com.amrilhs.amrilgithubuserapp3.web

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import com.amrilhs.amrilgithubuserapp3.R

class WebDicodingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_dicoding)
        var webView: WebView
        var progressBar: ProgressBar
        webView = findViewById(R.id.web_dicoding)
        progressBar = findViewById(R.id.progressBar_web)
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                }
            }
        }
        webView.loadUrl("https://www.dicoding.com")

    }

}