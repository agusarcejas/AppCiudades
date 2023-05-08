package com.example.appciudades

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.appciudades.databinding.ActivityModifyBinding

class ModifyActivity : AppCompatActivity() {

    lateinit var binding: ActivityModifyBinding
    lateinit var helper: DB_Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helper = DB_Helper(this)

        // Obtener las ciudades desde la base de datos y almacenarlas en una lista
        val ciudades = helper.getAllCiudades()

        // Crear un adaptador para el spinner que use la lista de ciudades
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, ciudades)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Establecer el adaptador del spinner
        binding.spinner.adapter = adapter

        //Modificar y volver al Menu Principal

        binding.btnMod.setOnClickListener{
            if(binding.spinner.selectedItem != null && binding.poblacionMod.text.isNotBlank()) {
                helper.updateCiudadPoblacion(binding.poblacionMod.text.toString().toInt(), binding.spinner.selectedItem.toString())
                Toast.makeText(this,"Poblacion modificada exitosamente", Toast.LENGTH_LONG).show()
                val volver = Intent(this, MainActivity::class.java)
                startActivity(volver)
                    }
        else {
            Toast.makeText(this,"Debe completar todos los campos", Toast.LENGTH_LONG).show()
            }
        }

        //Volver al Menu Principal

        binding.btnVolverMod.setOnClickListener{
            val volver = Intent(this, MainActivity::class.java)
            startActivity(volver)
        }
    }
}