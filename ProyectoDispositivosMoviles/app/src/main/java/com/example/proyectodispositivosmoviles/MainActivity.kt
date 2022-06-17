package com.example.proyectodispositivosmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listaProducto= emptyList<Producto>()

        var database= AppBasedeDatos.getDataBase(this)

        database.productos().getAll().observe(this, Observer {
            listaProducto=it

            val adapter=ProductosAdapter(this,listaProducto)

            lista.adapter=adapter
        })


        lista.setOnItemClickListener { parent, view, position, id ->
            val intent=Intent(this,ProductoActivity::class.java)
            intent.putExtra("id" , listaProducto[position].idProducto)
            startActivity(intent)
        }

        floatingActionButton.setOnClickListener {
            val intent=Intent(this,NuevoProductoActivity::class.java)
            startActivity(intent)
        }
    }
}