package com.example.proyectodispositivosmoviles

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductosDao {
    @Query("select * from productos")
    fun getAll(): LiveData<List<Producto>>

    @Query("select * from productos where idProducto = :id")
    fun get(id: Int): LiveData<List<Producto>>

    @Insert
    fun insertAll(vararg productos: Producto)

    @Update
    fun update(producto: Producto)

    @Delete
    fun delete(producto: Producto)

}