package com.example.gatoscrud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etRaza: EditText
    private lateinit var etEdad: EditText
    private lateinit var btnAgregar: Button
    private lateinit var btnVerTodos: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHandler: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etRaza = findViewById(R.id.etRaza)
        etEdad = findViewById(R.id.etEdad)
        btnAgregar = findViewById(R.id.btnAdd)
        btnVerTodos = findViewById(R.id.btnViewAll)
        recyclerView = findViewById(R.id.rvGatos)
        dbHandler = DatabaseHelper(this)

        btnAgregar.setOnClickListener { addGato() }
        btnVerTodos.setOnClickListener { viewGatos() }

        viewGatos()
    }

    private fun addGato() {
        val raza = etRaza.text.toString()
        val edad = etEdad.text.toString().toIntOrNull()

        if (raza.isNotEmpty() && edad != null) {
            val gato = Gato(raza = raza, edad = edad)
            val status = dbHandler.addGato(gato)
            if (status > -1) {
                Toast.makeText(applicationContext, "Gato agregado", Toast.LENGTH_LONG).show()
                clearEditTexts()
                viewGatos()
            }
        } else {
            Toast.makeText(applicationContext, "Raza y Edad son requeridos", Toast.LENGTH_LONG).show()
        }
    }

    private fun viewGatos() {
        val gatosList = dbHandler.getAllGatos()
        val adapter = GatosAdapter(gatosList) { gato -> deleteGato(gato) }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun deleteGato(gato: Gato) {
        dbHandler.deleteGato(gato)
        viewGatos()
    }

    private fun clearEditTexts() {
        etRaza.text.clear()
        etEdad.text.clear()
    }
}
