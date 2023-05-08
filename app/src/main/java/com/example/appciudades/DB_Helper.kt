package com.example.appciudades

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB_Helper (context: Context): SQLiteOpenHelper(context, "Ciudad.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Ciudad(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pais varchar(50)," +
                "capital varchar(50)," +
                "poblacion INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, versionVieja: Int, versionNueva: Int) {
        if (versionVieja < 2) {
            db?.execSQL("ALTER TABLE Ciudad RENAME TO Ciudad_old")
            db?.execSQL("CREATE TABLE Ciudad (_id INTEGER PRIMARY KEY AUTOINCREMENT, pais TEXT, capital TEXT, poblacion INTEGER)")
            db?.execSQL("INSERT INTO Ciudad (_id, pais, capital, poblacion) SELECT id, pais, capital, poblacion FROM Ciudad_old")
            db?.execSQL("DROP TABLE Ciudad_old")
        } else {
            db?.execSQL("ALTER TABLE Ciudad ADD COLUMN _id INTEGER")
            db?.execSQL("UPDATE Ciudad SET _id = id")
            db?.execSQL("DROP INDEX IF EXISTS index_ciudad_id")
            db?.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_ciudad__id ON Ciudad (_id)")
            db?.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_ciudad_pais ON Ciudad (pais)")
        }
    }

    fun insertDB (pais: String, capital: String, poblacion: Int ) {
        val date = ContentValues()

        //Datos a insertar
        date.put("pais", pais)
        date.put("capital", capital)
        date.put("poblacion", poblacion)

        val db = this.writableDatabase
        db.insert("Ciudad", null, date)
        db.close()
    }

    fun deleteCiudad(nombreCiudad: String) {

        //Datos a eliminar

        val db = this.writableDatabase
        val selection = "capital = ?"
        val selectionArgs = arrayOf(nombreCiudad)
        db.delete("Ciudad", selection, selectionArgs)
        db.close()
    }

    fun deletePais(nombrePais: String) {

        //Datos a eliminar

        val db = this.writableDatabase
        val selection = "pais = ?"
        val selectionArgs = arrayOf(nombrePais)
        db.delete("Ciudad", selection, selectionArgs)
        db.close()
    }

    fun updateCiudadPoblacion(nuevaPoblacion: Int, nombreCiudad: String) {
        val date = ContentValues()

        //Datos a cambiar
        date.put("poblacion", nuevaPoblacion)

        val db = this.writableDatabase
        val selection = "capital = ?"
        val selectionArgs = arrayOf(nombreCiudad)
        db.update("Ciudad", date, selection, selectionArgs)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllCiudades(): List<String> {
        val ciudades = mutableListOf<String>()
        val db = this.readableDatabase
        val query = "SELECT capital FROM Ciudad ORDER BY capital"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val ciudad = cursor.getString(cursor.getColumnIndex("capital"))
            ciudades.add(ciudad)
        }
        cursor.close()
        db.close()
        return ciudades
    }

    @SuppressLint("Range")
    fun getAllPaises(): List<String> {
        val paises = mutableListOf<String>()
        val db = this.readableDatabase
        val query = "SELECT DISTINCT pais FROM Ciudad ORDER BY pais"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val pais = cursor.getString(cursor.getColumnIndex("pais"))
            paises.add(pais)
        }
        cursor.close()
        db.close()
        return paises
    }


}