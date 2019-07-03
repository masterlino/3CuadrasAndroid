package com.dreampiece.a3cuadras.fragments

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dreampiece.a3cuadras.adapters.BusinessAdapter
import com.dreampiece.a3cuadras.activities.MainActivity
import com.dreampiece.a3cuadras.R
import com.dreampiece.a3cuadras.activities.BusinessDetailsActivity
import com.dreampiece.a3cuadras.database.FavoriteBusinessDatabase
import com.dreampiece.a3cuadras.model.BusinessItem
import kotlinx.android.synthetic.main.favorite_business_list_fragment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FavoritesBusinessListFragment : Fragment() {

    private lateinit var favoritesDB : FavoriteBusinessDatabase

    companion object{
       fun newInstance(): FavoritesBusinessListFragment{
           return FavoritesBusinessListFragment()
       }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favorite_business_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doAsync {
        favoritesDB = Room.databaseBuilder(activity!!, FavoriteBusinessDatabase::class.java, MainActivity.FavoritesDBNAME).build()

            val businesses: List<BusinessItem> =  favoritesDB.favoriteBusinessDao().getAll()
            uiThread {
                rvFavoriteBusiness.layoutManager = LinearLayoutManager(activity)
                rvFavoriteBusiness.adapter =
                    BusinessAdapter(businesses) { businessItem, action ->
                        if (action == 1) { //favorite action
                            //Delete the favorite on the DataBase in a secondary Thread
                            doAsync {
                                favoritesDB.favoriteBusinessDao().delete(businessItem)
                                (rvFavoriteBusiness.adapter as BusinessAdapter).updateItemsAdapter(
                                    favoritesDB.favoriteBusinessDao().getAll()
                                )
                                uiThread {
                                    rvFavoriteBusiness.adapter?.notifyDataSetChanged()
                                }
                            }
                        }
                        if (action == 0) {
                            //open detail activity
                            openBusinessDetail(businessItem)
                        }
                    }
            }
        }
    }

    //show the Business Detail Activity
    private fun openBusinessDetail(businessItem: BusinessItem) {
        val intent : Intent = Intent(this.context, BusinessDetailsActivity::class.java )
        intent.putExtra("business", businessItem)
        startActivity(intent)
    }
}