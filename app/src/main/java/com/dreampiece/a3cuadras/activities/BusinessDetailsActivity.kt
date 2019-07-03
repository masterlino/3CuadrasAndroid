package com.dreampiece.a3cuadras.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dreampiece.a3cuadras.R
import com.dreampiece.a3cuadras.fragments.BusinessDetailFragment
import com.dreampiece.a3cuadras.model.BusinessItem

class BusinessDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_details)

        supportFragmentManager.beginTransaction().add(
            android.R.id.content,
            BusinessDetailFragment.newInstance(intent.getParcelableExtra("business") as BusinessItem)
        ).commit()
    }
}
