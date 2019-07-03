package com.dreampiece.a3cuadras.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.dreampiece.a3cuadras.R
import com.dreampiece.a3cuadras.fragments.BusinessListFragment
import com.dreampiece.a3cuadras.fragments.FavoritesBusinessListFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var currentLocation : Location? = null

    private var mView : View? = null
    private var mswipeRefreshLayout : SwipeRefreshLayout? = null

    companion object{
        val FavoritesDBNAME = "favoriusiness"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottonNavigationView.selectedItemId = R.id.navigationAll
        //calling the location service
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        openBusinessListFragmentWithLocation()

        mswipeRefreshLayout = findViewById(R.id.swipeRefresh_Layout)

        mswipeRefreshLayout?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            openBusinessListFragmentWithLocation()
            bottonNavigationView.selectedItemId = R.id.navigationAll
            mswipeRefreshLayout?.isRefreshing = false
        })
    }

    private fun openBusinessListFragmentWithLocation() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ){
                mFusedLocationProviderClient?.lastLocation?.addOnSuccessListener(this, OnSuccessListener<Location>(){
                    if (it != null){
                        mswipeRefreshLayout?.isRefreshing = false
                        currentLocation = it
                        //new instance of BusinessListFragment
                        val targetFragment: Fragment
                        targetFragment = BusinessListFragment.newInstance(currentLocation)
                        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                        ft.replace(R.id.mainflContent, targetFragment)
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        ft.commit()
                        bottonNavigationView.setOnNavigationItemSelectedListener {
                            menuItemTapped(it)
                            true
                        }
                    }
                })
            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            }
        }
        else{
            mFusedLocationProviderClient?.lastLocation?.addOnSuccessListener(this, OnSuccessListener<Location>(){
                if (it != null){
                    mswipeRefreshLayout?.isRefreshing = false
                    currentLocation = it
                    //new instance of BusinessListFragment
                    val targetFragment: Fragment
                    targetFragment = BusinessListFragment.newInstance(currentLocation)
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.mainflContent, targetFragment)
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    ft.commit()
                    bottonNavigationView.setOnNavigationItemSelectedListener {
                        menuItemTapped(it)
                        true
                    }
                }
            })
        }
    }

    private fun menuItemTapped(it: MenuItem) {
        var targetFragment: Fragment? = null

        when (it.itemId ){
            R.id.navigationAll -> openBusinessListFragmentWithLocation()
            else -> targetFragment = FavoritesBusinessListFragment.newInstance()


        }
        if (targetFragment != null){
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.mainflContent, targetFragment!!)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            ft.commit()
        }

    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
            mFusedLocationProviderClient?.lastLocation?.addOnSuccessListener(this, OnSuccessListener<Location>(){
                if (it != null){
                    mswipeRefreshLayout?.isRefreshing = false
                    currentLocation = it
                    //new instance of BusinessListFragment
                    val targetFragment: Fragment
                    targetFragment = BusinessListFragment.newInstance(currentLocation)
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.mainflContent, targetFragment)
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    ft.commit()
                    bottonNavigationView.setOnNavigationItemSelectedListener {
                        menuItemTapped(it)
                        true
                    }
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.preferences, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id  = item?.itemId
        when (id){
            R.id.navigationPreferences -> {
                val intent : Intent = Intent(this.baseContext, PreferencesActivity::class.java )
                startActivity(intent)
            }
            R.id.navigationInfo -> {
                val intent : Intent = Intent(this.baseContext, InfoActivity::class.java )
                startActivity(intent)
            }
        }
        return true
    }

}
