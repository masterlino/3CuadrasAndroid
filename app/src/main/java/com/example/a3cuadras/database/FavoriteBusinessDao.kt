package com.example.a3cuadras.database

import android.arch.persistence.room.*
import com.example.a3cuadras.model.BusinessItem

@Dao
interface FavoriteBusinessDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(business: BusinessItem)

    @Delete
    fun delete(business: BusinessItem)

    @Query("SELECT * FROM BusinessItem")
    fun getAll(): List<BusinessItem>
}