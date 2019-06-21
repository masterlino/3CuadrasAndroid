package com.example.a3cuadras.fragments

import android.arch.persistence.room.Room
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Response
import com.example.a3cuadras.adapters.BusinessAdapter
import com.example.a3cuadras.activities.MainActivity.Companion.FavoritesDBNAME
import com.example.a3cuadras.R
import com.example.a3cuadras.activities.BusinessDetailsActivity
import com.example.a3cuadras.database.FavoriteBusinessDatabase
import com.example.a3cuadras.model.BusinessItem
import com.example.a3cuadras.model.BusinessListItem
import com.mimo.sampleadvanced.network.GsonRequest
import com.mimo.sampleadvanced.network.RequestsManager
import kotlinx.android.synthetic.main.business_list_fragment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class BusinessListFragment: Fragment() {
    private lateinit var favoritesDB : FavoriteBusinessDatabase

    private val URL = "https://api.yelp.com/v3/businesses/search?"
    private var URLLocation : String? = null
    private var mLocation: Location? = null

    private var mView : View? = null

    companion object{
        fun newInstance(location: Location?): BusinessListFragment{
            val businessListFragment : BusinessListFragment = BusinessListFragment()
            val args : Bundle = Bundle()
            args.putParcelable("currentLocation" , location)
            businessListFragment.arguments = args
            return businessListFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //get the current location if exits
        mLocation = arguments?.getParcelable("currentLocation")
        if (mLocation != null){
            URLLocation = URL + "latitude=" + mLocation!!.latitude.toString() + "&longitude=" + mLocation!!.longitude.toString()
        }
        mView = inflater.inflate(R.layout.business_list_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launcRequest()
    }

    private fun launcRequest() {
        var mUrl : String
        if (URLLocation != null) mUrl = URLLocation!!
        else mUrl = URL
        val newRequest = GsonRequest<BusinessListItem>(mUrl, BusinessListItem::class.java, null,
            Response.Listener<BusinessListItem> {

                doAsync {
                    val businesses: List<BusinessItem> =  it.getbusinesses()!!.toList()
                    favoritesDB = Room.databaseBuilder(activity!!, FavoriteBusinessDatabase::class.java, FavoritesDBNAME).build()
                    val favoritesList : List<BusinessItem> = favoritesDB.favoriteBusinessDao().getAll()
                    //check the current favorites and mark them all
                    markFavorites(businesses, favoritesList)
                    uiThread {
                        rvBusiness.layoutManager = LinearLayoutManager(activity)
                        rvBusiness.adapter =
                            BusinessAdapter(businesses) { businessItem, action ->
                                if (action == 1) { //favorite action
                                    if (businessItem.favorite == false)
                                    //Insert the favorite on the DataBase in a secondary Thread
                                        doAsync {
                                            businessItem.favorite = true
                                            favoritesDB.favoriteBusinessDao().insert(businessItem)
                                        }
                                }
                                if (action == 0) {
                                    //open detail activity
                                    openBusinessDetail(businessItem)
                                }
                            }
                    }
                }
            },
            Response.ErrorListener {
                print("NOT OK")
            })

        RequestsManager.getInstance(this.requireContext()).addToRequestQueue(newRequest)
        rvBusiness.adapter?.notifyDataSetChanged()
    }

    //show the Business Detail Activity
    private fun openBusinessDetail(businessItem: BusinessItem) {
        val intent : Intent= Intent(this.context, BusinessDetailsActivity::class.java )
        intent.putExtra("business", businessItem)
        startActivity(intent)
    }

    //check the list of items and mark the favorites kept on our DataBase
    private fun markFavorites(businesses: List<BusinessItem>, favoritesList: List<BusinessItem>) {
        favoritesList.forEach {
            val currentFav: BusinessItem = it
            businesses.forEach {
                if (currentFav.id == it.id) {
                    it.favorite = true
                }
            }
        }
    }



}