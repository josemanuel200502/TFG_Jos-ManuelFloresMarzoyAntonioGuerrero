package com.example.buildyourbd

import org.junit.Assert
import org.junit.Test


class LoginTest {
    // Prueba para un correo mínimo de longitud válida (7 caracteres)
    @Test
    fun testValidEmailMinLength() {
        val email = "a@a.com" // Mínimo 7 caracteres
        val result = isValidEmail(email)
        Assert.assertTrue("El correo debería ser válido", result)
    }

    // Prueba para un correo con 100 caracteres (máximo permitido)
    @Test
    fun testValidEmailMaxLength() {
        val email = "a".repeat(94) + "@domain.com" // 100 caracteres
        val result = isValidEmail(email)
        Assert.assertTrue("El correo debería ser válido", result)
    }

    // Método de validación simple para el correo
    private fun isValidEmail(email: String?): Boolean {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$".toRegex())
    }
}


class LoginTest2 {
    // Prueba para una contraseña mínima válida (6 caracteres)
    @Test
    fun testValidPasswordMinLength() {
        val password = "123456" // Mínimo 6 caracteres
        val result = isValidPassword(password)
        Assert.assertTrue("La contraseña debería ser válida", result)
    }

    // Prueba para una contraseña máxima válida (20 caracteres)
    @Test
    fun testValidPasswordMaxLength() {
        val password = "12345678901234567890" // Máximo 20 caracteres
        val result = isValidPassword(password)
        Assert.assertTrue("La contraseña debería ser válida", result)
    }

    // Método de validación simple para la contraseña
    private fun isValidPassword(password: String?): Boolean {
        return password != null && password.length >= 6 && password.length <= 20
    }
}

