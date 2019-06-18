package com.example.a3cuadras.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class BusinessDetailItem (
    @PrimaryKey var id : String,
    var name : String,
    var is_closed : Boolean,
    var phone : String,
    var url : String,
    var rating : Double,
    var coordinates : Coordinates,
    var photos : Array<String>,
    var price : String
)

data class Coordinates( var latitude : Double, var longitude : Double)