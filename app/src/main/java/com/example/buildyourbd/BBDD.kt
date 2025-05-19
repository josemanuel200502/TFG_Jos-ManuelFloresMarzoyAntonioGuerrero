package com.example.buildyourbd

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class BBDD(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "usuarios.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }


    override fun onCreate(db: SQLiteDatabase) {
        Log.e("BBDD", "🔥 onCreate() ejecutado - Creando la base de datos")

        val createTable = """
        CREATE TABLE $TABLE_USERS (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_USERNAME TEXT UNIQUE,
            $COLUMN_EMAIL TEXT UNIQUE,
            $COLUMN_PASSWORD TEXT
        );
    """.trimIndent()

        db.execSQL(createTable)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }



    //Agrega un nuevo usuario (por username o email) con contraseña.
    //@return true si se insertó correctamente, false si hubo error (por ejemplo, duplicado).*/
    fun addUser(username: String?, email: String?, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)}
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L}

    fun getDatabasePath(context: Context): String {
        return context.getDatabasePath(DATABASE_NAME).absolutePath
    }



    //Verifica si un usuario con este username o email ya existe.*/
    fun isUserExists(username: String, email: String): Boolean {
        val db = readableDatabase
        val query = "SELECT 1 FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ? OR $COLUMN_EMAIL = ? LIMIT 1"
        val cursor: Cursor = db.rawQuery(query, arrayOf(username, email))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists}

    /**
     *
     *

    Autentica credenciales: busca por username o email, y compara contraseña.
    @return true si coinciden.*/
    fun authenticate(identifier: String, password: String): Boolean {
        val db = readableDatabase// Consulta usuario por identifier (username o email)
        val query = "SELECT $COLUMN_PASSWORD FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ? OR $COLUMN_EMAIL = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(identifier, identifier))
        var valid = false
        if (cursor.moveToFirst()) {
            val storedPwd = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            valid = (storedPwd == password)}
        cursor.close()
        db.close()
        return valid}

}

/*
Uso en Activities:

val db = BBDD(this)
if (!db.isUserExists(username, email)) {
    val added = db.addUser(username, email, password)
    if (added) {
        // registro OK
    } else {
        // error al guardar
    }
} else {
    // usuario o email ya existe
}

// Para login:
val db = BBDD(this)
if (db.authenticate(identifier, password)) {
    // login exitoso
} else {
    // credenciales inválidas
}
*/