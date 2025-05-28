package com.example.buildyourbd

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.SharedPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class GimnasiosActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var ubicacionUsuario: Location? = null // Guardar ubicación del usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gimnasio)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val btnEncontrar = findViewById<Button>(R.id.btnFind) // Ajustado al `id` correcto
        btnEncontrar.setOnClickListener {
            solicitarUbicacion()
        }

        val btnVerMapa = findViewById<Button>(R.id.btnMapa) // Ajustado al `id` correcto
        btnVerMapa.setOnClickListener {
            abrirGoogleMaps()
        }

        val btnVolver = findViewById<Button>(R.id.btnBack) // Ajustado al `id` correcto
        btnVolver.setOnClickListener {
            finish() // Cierra la actividad y regresa
        }
    }

    private fun solicitarUbicacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            obtenerUbicacion()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacion() // Solo si el permiso se concedió
            } else {
                Toast.makeText(
                    this,
                    "Debes conceder permisos para usar esta función",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun obtenerUbicacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso de ubicación no concedido", Toast.LENGTH_SHORT).show()
            return
        }

        val locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
            priority = com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
            interval = 5000 // Solicita actualización cada 5 segundos
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    android.util.Log.d("UBICACION", "Latitud actualizada: ${location.latitude}, Longitud: ${location.longitude}")

                    val prefs = getSharedPreferences("GimnasioPrefs", MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putFloat("latitude", location.latitude.toFloat())
                    editor.putFloat("longitude", location.longitude.toFloat())
                    editor.apply()

                    Toast.makeText(this@GimnasiosActivity, "Ubicación actualizada: ${location.latitude}, ${location.longitude}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@GimnasiosActivity, "Ubicación no disponible, inténtalo de nuevo", Toast.LENGTH_SHORT).show()
                }
            }
        }, null)
    }





    private fun abrirGoogleMaps() {
        val prefs = getSharedPreferences("GimnasioPrefs", MODE_PRIVATE)
        val latitude = prefs.getFloat("latitude", 0f)
        val longitude = prefs.getFloat("longitude", 0f)

        if (latitude == 0f || longitude == 0f) {
            Toast.makeText(
                this,
                "Error: la ubicación no está guardada. Intenta nuevamente.",
                Toast.LENGTH_LONG
            ).show()
            solicitarUbicacion()
            return
        }

        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=gimnasios cerca de mí")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        // 🔥 Asegurar que la actividad no se cierre
        mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mapIntent)
    }
}