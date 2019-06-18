package com.example.a3cuadras.model

class BusinessListItem {
    private var businesses : Array<BusinessItem>? = null

    fun getbusinesses(): Array<BusinessItem>? {
        return businesses
    }

    fun setbusinesses(businessList: Array<BusinessItem>) {
        this.businesses = businessList
    }

}