package dev.thib.compteurmini

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class PlaqueImmatriculation(val id: Long? = null, val plaque: String)
data class User(val id: Long? = null, val user: String)

class PlaqueImmatriculationDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "plaque_immatriculation.db", null, 3) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE plaques (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_user INTEGER, " +
                    "plaque TEXT UNIQUE," +
                    "nombre_fois_vu INTEGER, " +
                    "FOREIGN KEY(id_user) REFERENCES user(id)" +
                ")"
        )
        db.execSQL(
            "CREATE TABLE user (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user TEXT, " +
                    "nombre_mini INTEGER, " +
                    "nombre_plaque INTEGER" +
                ")"
        )
        val values = ContentValues().apply {
            put("user", "local")
            put("nombre_mini", 0)
            put("nombre_plaque", 0)
        }
        db.insert("user", null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS plaques")
        db.execSQL("DROP TABLE IF EXISTS user")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getUserById(userId: Long): User? {
        val db = readableDatabase
        var user: User? = null

        // Sélectionner l'utilisateur avec l'ID donné
        val cursor = db.rawQuery("SELECT id, user FROM user WHERE id = ?", arrayOf(userId.toString()))

        // S'il y a des résultats, extraire les informations de l'utilisateur
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val username = cursor.getString(cursor.getColumnIndex("user"))
            user = User(id, username)
        }

        cursor.close()
        db.close()

        return user
    }

    @SuppressLint("Range")
    fun getNombreMini(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT nombre_mini FROM user WHERE user = 'local'", null)
        var nombreMini = 0
        if (cursor.moveToFirst()) {
            nombreMini = cursor.getInt(cursor.getColumnIndex("nombre_mini"))
        }
        cursor.close()

        return nombreMini
    }

    @SuppressLint("Range")
    fun getNombrePlaque(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT nombre_plaque FROM user WHERE user = 'local'", null)
        var nombrePlaque = 0
        if (cursor.moveToFirst()) {
            nombrePlaque = cursor.getInt(cursor.getColumnIndex("nombre_plaque"))
        }
        cursor.close()

        return nombrePlaque
    }

    @SuppressLint("Range")
    fun ajouterUneMini(plaque: Boolean = false) {
        val db = writableDatabase

        val nombreMini = getNombreMini()

        // Mettez à jour le nombre de plaques associées à l'utilisateur local
        val newNombreMini = nombreMini + 1
        val valuesUser = ContentValues().apply {
            put("nombre_mini", newNombreMini)
        }
        db.update("user", valuesUser, "user = ?", arrayOf("local"))

        if (plaque) {
            ajouterUnePlaqueCompteur()
        }
    }

    private fun ajouterUnePlaqueCompteur() {
        val db = writableDatabase

        val nombrePlaque = getNombrePlaque()

        // Mettez à jour le nombre de plaques associées à l'utilisateur local
        val newNombrePlaque = nombrePlaque + 1
        val valuesUser = ContentValues().apply {
            put("nombre_plaque", newNombrePlaque)
        }
        db.update("user", valuesUser, "user = ?", arrayOf("local"))
    }

    fun verifierNombreMiniPlaque(): Boolean {
        val nombreMini = getNombreMini()
        val nombrePlaque = getNombrePlaque()

        return nombreMini > nombrePlaque
    }

    // Verifier le nombre de plaque avant
    // -> ne peut pas supprimer une mini si il ne reste que des plaques nombre_mini
    fun retirerUneMini(plaque: Boolean = false) {
        val db = writableDatabase

        val nombreMini = getNombreMini()

        // Mettez à jour le nombre de plaques associées à l'utilisateur local
        val newNombrePlaque = nombreMini - 1
        val valuesUser = ContentValues().apply {
            put("nombre_mini", newNombrePlaque)
        }
        db.update("user", valuesUser, "user = ?", arrayOf("local"))

        if (plaque) {
            retirerUnePlaqueCompteur()
        }
    }

    private fun retirerUnePlaqueCompteur() {
        val db = writableDatabase

        val nombrePlaque = getNombrePlaque()

        // Mettez à jour le nombre de plaques associées à l'utilisateur local
        val newNombrePlaque = nombrePlaque - 1
        val valuesUser = ContentValues().apply {
            put("nombre_plaque", newNombrePlaque)
        }
        db.update("user", valuesUser, "user = ?", arrayOf("local"))
    }

    @SuppressLint("Range")
    fun ajouterPlaque(plaque: PlaqueImmatriculation) {
        val db = writableDatabase

        val valuesPlaque = ContentValues().apply {
            put("plaque", plaque.plaque)
            put("nombre_fois_vu", 1)
        }

        db.insert("plaques", null, valuesPlaque)
        db.close()
    }

    fun updatePlaque(id: Int, nombreFoisVu: Int) {
        val db = writableDatabase

        val newNombreFoisVu = nombreFoisVu + 1

        val valuesPlaque = ContentValues().apply {
            put("nombre_fois_vu", newNombreFoisVu)
        }

        db.update("plaques", valuesPlaque, "id = ?", arrayOf(id.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun verifierSiPlaqueExiste(plaque: PlaqueImmatriculation): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM plaques WHERE plaque = ?", arrayOf(plaque.plaque))
        var nombreFoisVu = -1 // Valeur par défaut si la plaque n'existe pas
        var id = 0
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("id"))
            nombreFoisVu = cursor.getInt(cursor.getColumnIndex("nombre_fois_vu"))
        }

        if (nombreFoisVu != -1) {
            updatePlaque(id = id, nombreFoisVu = nombreFoisVu)
            ajouterUneMini(plaque = true)
        }
        else {
            ajouterPlaque(plaque = plaque)
            ajouterUneMini(plaque = true)
        }

        cursor.close()
        db.close()
        return nombreFoisVu
    }

    @SuppressLint("Range")
    fun diminuerNombreFoisVu(plaqueId: Long) {
        val db = writableDatabase

        val cursor = db.rawQuery("SELECT nombre_fois_vu FROM plaques WHERE id = ?", arrayOf(plaqueId.toString()))
        var nombreFoisVu = 0
        if (cursor.moveToFirst()) {
            nombreFoisVu = cursor.getInt(cursor.getColumnIndex("nombre_fois_vu"))
        }
        cursor.close()

        if (nombreFoisVu > 1) {
            val newNombreFoisVu = nombreFoisVu - 1
            val valuesPlaque = ContentValues().apply {
                put("nombre_fois_vu", newNombreFoisVu)
            }
            db.update("plaques", valuesPlaque, "id = ?", arrayOf(plaqueId.toString()))
            retirerUneMini(plaque = true)
        }

        db.close()
    }

    fun supprimerPlaque(plaqueId: Long) {
        val db = writableDatabase

        // Supprimer la plaque de la table des plaques
        db.delete("plaques", "id = ?", arrayOf(plaqueId.toString()))

        retirerUneMini(plaque = true)

        db.close()
    }


    data class PlaqueInfo(val id: Long, val plaque: String, val nombreFoisVu: Int)

    @SuppressLint("Range")
    fun obtenirPlaques(): List<PlaqueInfo> {
        val plaques = mutableListOf<PlaqueInfo>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT id, plaque, nombre_fois_vu FROM plaques", null)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex("id"))
            val plaque = cursor.getString(cursor.getColumnIndex("plaque"))
            val nombreFoisVu = cursor.getInt(cursor.getColumnIndex("nombre_fois_vu"))
            plaques.add(PlaqueInfo(id, plaque, nombreFoisVu))
        }
        cursor.close()
        db.close()
        return plaques
    }


    fun remettreToutAZero() {
        val db = writableDatabase

        val valuesUser = ContentValues().apply {
            put("nombre_mini", 0)
            put("nombre_plaque", 0)
        }
        db.update("user", valuesUser, "user = ?", arrayOf("local"))

        db.delete("plaques", null, null)
        db.close()
    }
}
