package com.example.a3cuadras.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.a3cuadras.model.BusinessItem

@Database(entities = [BusinessItem::class], version = 1, exportSchema = false)
abstract class FavoriteBusinessDatabase: RoomDatabase() {
    abstract fun favoriteBusinessDao(): FavoriteBusinessDao
}