package com.dreampiece.a3cuadras.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dreampiece.a3cuadras.R

import com.dreampiece.a3cuadras.Services.Preferences
import kotlinx.android.synthetic.main.activity_preferences.*

class PreferencesActivity : AppCompatActivity() {

    var prefs: Preferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = Preferences(this)

        setContentView(R.layout.activity_preferences)

        seekBar1.progress = prefs!!.maxDistance

        seekBar2.progress = prefs!!.maxResults

        switch1.isChecked = prefs!!.cheap

        switch2.isChecked = prefs!!.average

        switch3.isChecked = prefs!!.expensive

        switch4.isChecked = prefs!!.very_expensive

    }

    override fun onPause() {
        super.onPause()

        prefs!!.maxDistance = seekBar1.progress

        prefs!!.maxResults = seekBar2.progress

        prefs!!.cheap = switch1.isChecked

        prefs!!.average =  switch2.isChecked

        prefs!!.expensive = switch3.isChecked

        prefs!!.very_expensive = switch4.isChecked

    }
}
