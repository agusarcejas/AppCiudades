package com.example.appciudades

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.appciudades.databinding.ActivityDeleteBinding

class DeleteActivity : AppCompatActivity() {

    lateinit var binding: ActivityDeleteBinding
    lateinit var helper: DB_Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helper = DB_Helper(this)

        // Obtener las ciudades desde la base de datos y almacenarlas en una lista
        val ciudades = helper.getAllCiudades()

        // Crear un adaptador para el spinner que use la lista de ciudades
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, ciudades)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Establecer el adaptador del spinner
        binding.spinnerDelete.adapter = adapter

        //Eliminar Ciudad y volver al Menu Principal

        binding.btnDeleteCiudad.setOnClickListener{
            if(binding.spinnerDelete.selectedItem != null) {
                helper.deleteCiudad(binding.spinnerDelete.selectedItem.toString())
                Toast.makeText(this,"Ciudad eliminadada exitosamente", Toast.LENGTH_LONG).show()
                val volver = Intent(this, MainActivity::class.java)
                startActivity(volver)
            }
            else {
                Toast.makeText(this,"Debe ingresar una ciudad", Toast.LENGTH_LONG).show()
            }
        }

        // Obtener los paises desde la base de datos y almacenarlas en una lista
        val paises = helper.getAllPaises()

        // Crear un adaptador para el spinner que use la lista de paises
        val adapterPais = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, paises)
        adapterPais.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Establecer el adaptador del spinner
        binding.spinnerDeletePais.adapter = adapterPais

        //Eliminar País y volver al Menu Principal

        binding.btnDeletePais.setOnClickListener{
            if(binding.spinnerDeletePais.selectedItem != null) {
                helper.deletePais(binding.spinnerDeletePais.selectedItem.toString())
                Toast.makeText(this,"Pais eliminado exitosamente", Toast.LENGTH_LONG).show()
                val volver = Intent(this, MainActivity::class.java)
                startActivity(volver)
            }
            else {
                Toast.makeText(this,"Debe ingresar un país", Toast.LENGTH_LONG).show()
            }
        }

        //Volver al Menu Principal

        binding.btnDeleteVolver.setOnClickListener{
            val volver = Intent(this, MainActivity::class.java)
            startActivity(volver)
        }
    }
}