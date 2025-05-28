package com.example.buildyourbd

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecetaDetalleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receta_detalle)

        val recetaTitulo = intent.getStringExtra("recetaTitulo")
        val recetaVideoUrl = intent.getStringExtra("recetaVideoUrl")
        val recetaInstrucciones = intent.getStringExtra("recetaInstrucciones")

        findViewById<TextView>(R.id.textTituloReceta).text = recetaTitulo
        findViewById<TextView>(R.id.textInstrucciones).text = recetaInstrucciones

        // Botón para volver a la lista de recetas
        val btnVolver = findViewById<Button>(R.id.btnVolverReceta)
        btnVolver.setOnClickListener {
            finish() // Cierra la actividad y regresa a RecetasActivity
        }

        // Configurar WebView para mostrar el video sin UI extra de YouTube
        val webView = findViewById<WebView>(R.id.webViewReceta)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.webViewClient = WebViewClient()

        // Convertir la URL para que cargue el video en modo embed
        val embedUrl = recetaVideoUrl?.replace("watch?v=", "embed/")

        val iframeVideo = """
            <html>
            <body style="margin:0;padding:0;">
                <iframe width="100%" height="100%" src="$embedUrl?rel=0&modestbranding=1&controls=0&showinfo=0" frameborder="0" allowfullscreen></iframe>
            </body>
            </html>
        """

        // Cargar el iframe en WebView
        webView.loadDataWithBaseURL(null, iframeVideo, "text/html", "utf-8", null)
    }
}
