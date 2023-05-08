package com.example.appciudades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appciudades.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Llamo a la clase donde se conecta la BD

    lateinit var binding: ActivityMainBinding
    lateinit var helper: DB_Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helper = DB_Helper(this)


        //Boton añadir ciudad
        binding.btnAdd.setOnClickListener() {
            if(binding.pais.text.isNotBlank() && binding.capital.text.isNotBlank() && binding.poblacion.text.isNotBlank() ) {
                helper.insertDB(binding.pais.text.toString(), binding.capital.text.toString(), binding.poblacion.text.toString().toInt())
                binding.pais.text.clear()
                binding.capital.text.clear()
                binding.poblacion.text.clear()
                Toast.makeText(this,"Ciudad añadida exitosamente", Toast.LENGTH_LONG).show()
                }
            else {
                Toast.makeText(this,"Debe completar todos los campos", Toast.LENGTH_LONG).show()
            }
        }

        //Boton Ver Ciudades
        binding.btnCiudad.setOnClickListener{
            val intentListView = Intent(this, ActivityLista::class.java)
            startActivity(intentListView)
        }

        //Boton Modificar Población segun la ciudad
        binding.btnModificarPoblacion.setOnClickListener{
            val intentListView = Intent(this, ModifyActivity::class.java)
            startActivity(intentListView)
        }

        //Boton para eliminar la ciudad o todas las ciudades de un país
        binding.btnEliminar.setOnClickListener{
            val intentListView = Intent(this, DeleteActivity::class.java)
            startActivity(intentListView)
        }

    }



}