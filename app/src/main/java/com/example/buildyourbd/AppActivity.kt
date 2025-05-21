package com.example.buildyourbd

import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AppActivity : AppCompatActivity() {

    private lateinit var youtubeWebView: WebView
    private lateinit var botonInicio: ImageButton
    // Ya no usamos botonAjustes hasta que tengas SettingsActivity
    // private lateinit var botonAjustes: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        youtubeWebView = findViewById(R.id.youtubeWebView)
        botonInicio     = findViewById(R.id.botonInicio)
        // botonAjustes    = findViewById(R.id.botonAjustes)

        // Configurar WebView
        youtubeWebView.webViewClient = WebViewClient()
        val webSettings: WebSettings = youtubeWebView.settings
        webSettings.javaScriptEnabled = true
        val videoUrl = "https://www.youtube.com/embed/dQw4w9WgXcQ"
        youtubeWebView.loadUrl(videoUrl)

        // Barra inferior
        botonInicio.setOnClickListener {
            youtubeWebView.loadUrl(videoUrl)
        }
        // botonAjustes.setOnClickListener {
        //     startActivity(Intent(this, SettingsActivity::class.java))
        // }

        // Grid de botones
        findViewById<Button>(R.id.boton1).setOnClickListener {
            startActivity(Intent(this, Entrenamientos::class.java))
        }

        // Los botones 2, 3 y 4 quedan deshabilitados hasta crear sus Activities
        // findViewById<Button>(R.id.boton2).setOnClickListener { /* Recetas */ }
        // findViewById<Button>(R.id.boton3).setOnClickListener { /* Comunidad */ }
        // findViewById<Button>(R.id.boton4).setOnClickListener { /* Recordatorios */ }

        findViewById<Button>(R.id.boton5).setOnClickListener {
            startActivity(Intent(this, Alimentos::class.java))
        }

        findViewById<Button>(R.id.boton6).setOnClickListener {
            startActivity(Intent(this, Gimnasios::class.java))
        }
    }
}
