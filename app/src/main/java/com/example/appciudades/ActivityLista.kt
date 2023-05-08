package com.example.appciudades

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.SearchView
import com.example.appciudades.databinding.ActivityListaCiudadesBinding
import com.example.appciudades.databinding.ItemListviewBinding

class ActivityLista : AppCompatActivity() {

    lateinit var binding: ActivityListaCiudadesBinding
    lateinit var helper: DB_Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaCiudadesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        helper = DB_Helper(this)

        val db : SQLiteDatabase = helper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Ciudad", null)

        val adapter = CursorAdapterListView(this, cursor)
        binding.lvCiudades.adapter = adapter
        db.close()

        // Aquí agregamos la funcionalidad de búsqueda en el SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText)
                return false
            }
        })

        binding.btnVolver.setOnClickListener{
            val intentListView = Intent(this, MainActivity::class.java)
            startActivity(intentListView)
        }
    }

    inner class CursorAdapterListView(context: Context, cursor: Cursor) :
        CursorAdapter(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER) {

        var cursorFiltered: Cursor

        init {
            cursorFiltered = cursor
        }

            override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
                val inflater = LayoutInflater.from(context)
                return inflater.inflate(R.layout.item_listview, parent, false)
            }

        @SuppressLint("Range")
        override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
            val bindingItems = ItemListviewBinding.bind(view!!)
            val ciudad = getItem(cursor!!.position) as Ciudad
            bindingItems.tvPais.text = ciudad.pais
            bindingItems.tvCapital.text = ciudad.capital
            bindingItems.tvPoblacion.text = ciudad.poblacion.toString()
        }

        fun filter(query: String?) {
            cursorFiltered = if (query.isNullOrEmpty()) {
                cursor
            } else {
                val db = helper.readableDatabase
                val newCursor = db.rawQuery("SELECT * FROM Ciudad WHERE capital LIKE '%$query%'", null)
                newCursor
            }
            notifyDataSetChanged()
        }

        override fun getCount(): Int {
            return cursorFiltered.count
        }

        @SuppressLint("Range")
        override fun getItem(position: Int): Any {
            cursorFiltered.moveToPosition(position)
            val id = cursorFiltered.getInt(cursorFiltered.getColumnIndex("_id"))
            val pais = cursorFiltered.getString(cursorFiltered.getColumnIndex("pais"))
            val capital = cursorFiltered.getString(cursorFiltered.getColumnIndex("capital"))
            val poblacion = cursorFiltered.getInt(cursorFiltered.getColumnIndex("poblacion"))
            return Ciudad(id, pais, capital, poblacion)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }
}

class Ciudad(val id: Int, val pais: String, val capital: String, val poblacion: Int)