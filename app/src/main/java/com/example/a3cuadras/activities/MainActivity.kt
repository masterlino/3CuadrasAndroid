package com.example.a3cuadras.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.MenuItem
import com.example.a3cuadras.R
import com.example.a3cuadras.fragments.BusinessListFragment
import com.example.a3cuadras.fragments.FavoritesBusinessListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        val FavoritesDBNAME = "favoriusiness"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottonNavigationView.selectedItemId = R.id.navigationAll
        val targetFragment: Fragment
        targetFragment = BusinessListFragment.newInstance()
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.mainflContent, targetFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
        bottonNavigationView.setOnNavigationItemSelectedListener {
            menuItemTapped(it)
            true
        }

    }

    private fun menuItemTapped(it: MenuItem) {
        val targetFragment: Fragment

        when (it.itemId ){
            R.id.navigationAll -> targetFragment = BusinessListFragment.newInstance()
            else -> targetFragment = FavoritesBusinessListFragment.newInstance()


        }
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.mainflContent, targetFragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()

    }
}
