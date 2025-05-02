package com.example.buildyourbd
import android.R
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var loginEmail: EditText? = null
    private var loginPass: EditText? = null
    private var entrarButton: Button? = null
    private var buttonGoogle: Button? = null
    private var invitadoButton: Button? = null
    private var registro: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item) //

        // Vincular los elementos de la vista con las variables
        loginEmail = findViewById<EditText>(R.id.loginEmail)
        loginPass = findViewById<EditText>(R.id.loginPass)
        entrarButton = findViewById<Button>(R.id.entrarButton)
        buttonGoogle = findViewById(R.id.button3)
        invitadoButton = findViewById<Button>(R.id.invitadoButton)
        registro = findViewById<TextView>(R.id.registro)

        // Lógica para el botón "Entrar"
        entrarButton.setOnClickListener(View.OnClickListener { login() })

        // Lógica para el botón "Invitado"
        invitadoButton.setOnClickListener(View.OnClickListener { loginAsGuest() })

        // Lógica para el botón "Google"
        buttonGoogle.setOnClickListener(View.OnClickListener { loginWithGoogle() })

        // Lógica para el enlace "¿No tienes cuenta? Regístrate"
        registro.setOnClickListener(View.OnClickListener { goToRegister() })
    }

    // Lógica de inicio de sesión
    private fun login() {
        val email = loginEmail!!.text.toString()
        val password = loginPass!!.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Aquí se puede agregar la lógica para validar el email y la contraseña
        if (email == "usuario@dominio.com" && password == "123456") {
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
            // Redirigir a la pantalla principal o al dashboard
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }

    // Lógica para iniciar sesión como invitado
    private fun loginAsGuest() {
        Toast.makeText(this, "Iniciando sesión como invitado", Toast.LENGTH_SHORT).show()
        // Aquí iría la lógica para iniciar sesión como invitado
    }

    // Lógica para iniciar sesión con Google
    private fun loginWithGoogle() {
        Toast.makeText(this, "Iniciando sesión con Google", Toast.LENGTH_SHORT).show()
        // Aquí iría la lógica para la autenticación de Google
    }

    // Lógica para ir a la pantalla de registro
    private fun goToRegister() {
        Toast.makeText(this, "Redirigiendo a registro", Toast.LENGTH_SHORT).show()
        // Aquí puedes redirigir a una actividad de registro
    }
}
