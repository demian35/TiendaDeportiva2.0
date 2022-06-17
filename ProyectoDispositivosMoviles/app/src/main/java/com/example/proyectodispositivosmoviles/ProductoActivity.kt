package com.example.proyectodispositivosmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductoActivity : AppCompatActivity() {

    private lateinit var database: AppBasedeDatos
    private lateinit var producto: Producto
    private lateinit var productoLiveData: LiveData<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        database=AppBasedeDatos.getDataBase(this)

       val idproducto = intent.getIntExtra("id",0)

        //productoLiveData= database.productos().get(idproducto)

        productoLiveData.observe(this, Observer {
            producto=it

            nombre_producto.text=producto.nombre
            precio2.text="$${producto.precio}"
            detalles_producto.text=producto.descripcion
            imageView2.setImageResource(producto.imagen)


        })



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.producto_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.editItem ->{
                val intent=Intent(this,NuevoProductoActivity::class.java)
                intent.putExtra("producto",producto)
                startActivity(intent)

            }

            R.id.deleteitem ->{
                productoLiveData.removeObservers(this)

                CoroutineScope(Dispatchers.IO).launch {
                    database.productos().delete(producto)
                    this@ProductoActivity.finish()
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }
}