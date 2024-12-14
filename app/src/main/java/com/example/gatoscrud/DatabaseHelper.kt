package com.example.gatoscrud

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "GatosDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_GATOS = "gatos"
        private const val KEY_ID = "id"
        private const val KEY_RAZA = "raza"
        private const val KEY_EDAD = "edad"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_GATOS($KEY_ID INTEGER PRIMARY KEY, $KEY_RAZA TEXT, $KEY_EDAD INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GATOS")
        onCreate(db)
    }

    fun addGato(gato: Gato): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_RAZA, gato.raza)
        values.put(KEY_EDAD, gato.edad)
        return db.insert(TABLE_GATOS, null, values)
    }

    @SuppressLint("Range")
    fun getAllGatos(): ArrayList<Gato> {
        val gatosList = ArrayList<Gato>()
        val selectQuery = "SELECT * FROM $TABLE_GATOS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val raza = cursor.getString(cursor.getColumnIndex(KEY_RAZA))
                val edad = cursor.getInt(cursor.getColumnIndex(KEY_EDAD))
                gatosList.add(Gato(id, raza, edad))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return gatosList
    }

    fun updateGato(gato: Gato): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_RAZA, gato.raza)
        values.put(KEY_EDAD, gato.edad)
        return db.update(TABLE_GATOS, values, "$KEY_ID = ?", arrayOf(gato.id.toString()))
    }

    fun deleteGato(gato: Gato): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_GATOS, "$KEY_ID = ?", arrayOf(gato.id.toString()))
    }
}
