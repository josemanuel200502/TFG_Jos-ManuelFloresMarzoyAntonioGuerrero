package com.example.buildyourbd

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ComunidadActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comunidad)

        recyclerView = findViewById(R.id.recyclerComunidad)
        recyclerView.layoutManager = LinearLayoutManager(this)

        sharedPreferences = getSharedPreferences("ComunidadPrefs", MODE_PRIVATE)

        // Inicializar lista de publicaciones
        val posts = cargarPublicaciones()
        postAdapter = PostAdapter(posts)
        recyclerView.adapter = postAdapter
        recyclerView.visibility = if (posts.isNotEmpty()) RecyclerView.VISIBLE else RecyclerView.GONE

        val btnAgregar = findViewById<Button>(R.id.btnAgregarPublicacion)
        val inputPost = findViewById<EditText>(R.id.inputComunidad)

        btnAgregar.setOnClickListener {
            val nuevoPost = inputPost.text.toString().trim()
            if (nuevoPost.isNotEmpty()) {
                guardarPublicacion(nuevoPost)
                postAdapter.agregarPost(nuevoPost)
                recyclerView.visibility = RecyclerView.VISIBLE
                inputPost.text.clear()
                Toast.makeText(this, "¡Publicación agregada!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Escribe algo antes de publicar.", Toast.LENGTH_SHORT).show()
            }
        }

        val btnVolver = findViewById<Button>(R.id.btnBackComunidad)
        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun guardarPublicacion(post: String) {
        val posts = sharedPreferences.getStringSet("posts", mutableSetOf()) ?: mutableSetOf()
        posts.add(post)
        sharedPreferences.edit().putStringSet("posts", posts).apply()
    }

    private fun cargarPublicaciones(): MutableList<String> {
        return sharedPreferences.getStringSet("posts", mutableSetOf())?.toMutableList() ?: mutableListOf()
    }
}
