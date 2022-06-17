package com.example.proyectodispositivosmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_nuevo_producto.*
import kotlinx.android.synthetic.main.activity_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)

        var idProducto: Int? = null

        if(intent.hasExtra("producto")){
            val producto= intent.extras?.getSerializable("producto") as Producto

            nombreet.setText(producto.nombre)
            precioedit.setText(producto.precio.toString())
            descripcionedit.setText(producto.descripcion)
            idProducto=producto.idProducto

        }

        val database= AppBasedeDatos.getDataBase(this)

        savebtn.setOnClickListener {
            val nombre= nombreedit.textView.toString()
            val precio=precioedit.text.toString().toDouble()
            val descripcion=descripcionedit.text.toString()

            val producto=Producto(nombre,precio,descripcion,R.drawable.ic_launcher_background)

            if(idProducto != null){
                CoroutineScope(Dispatchers.IO).launch {
                    producto.idProducto=idProducto
                    database.productos().update(producto)

                    this@NuevoProductoActivity.finish()
                }
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    database.productos().insertAll(producto)

                    this@NuevoProductoActivity.finish()

                }
            }
        }
    }
}