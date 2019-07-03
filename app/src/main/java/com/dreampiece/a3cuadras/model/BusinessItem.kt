package com.dreampiece.a3cuadras.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
data class BusinessItem (
    @PrimaryKey var id: String = "0",
    var name: String,
    var distance: Double,
    var image_url: String,
    var favorite: Boolean = false


) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeDouble(distance)
        parcel.writeString(image_url)
        parcel.writeByte(if (favorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BusinessItem> {
        override fun createFromParcel(parcel: Parcel): BusinessItem {
            return BusinessItem(parcel)
        }

        override fun newArray(size: Int): Array<BusinessItem?> {
            return arrayOfNulls(size)
        }
    }
}

