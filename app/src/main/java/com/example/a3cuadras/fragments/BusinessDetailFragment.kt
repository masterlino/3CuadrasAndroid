package com.example.a3cuadras.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.Response
import com.example.a3cuadras.R
import com.example.a3cuadras.adapters.LoopPagerAdapter
import com.example.a3cuadras.adapters.PhotosBusinessAdapter
import com.example.a3cuadras.model.BusinessDetailItem
import com.example.a3cuadras.model.BusinessItem
import com.example.a3cuadras.model.BusinessListItem
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mimo.sampleadvanced.network.GsonRequest
import com.mimo.sampleadvanced.network.RequestsManager
import java.net.URL

class BusinessDetailFragment: Fragment(), OnMapReadyCallback {
    override fun onMapReady(p0: GoogleMap?) {
        if (p0 != null && currentBusinessDetailItem != null) {
            map = p0
            var latitude : Double = currentBusinessDetailItem?.coordinates?.latitude!!
            var longitude : Double = currentBusinessDetailItem?.coordinates?.longitude!!
            if (latitude!=null && longitude != null){
                val mLatLng : LatLng = LatLng(latitude, longitude)
                map.addMarker(MarkerOptions().position(mLatLng))
                map.moveCamera(CameraUpdateFactory.newLatLng(mLatLng))
                val mGoogleMapOptions : GoogleMapOptions = GoogleMapOptions().liteMode(true)
                map.mapType = mGoogleMapOptions.mapType
                //map.uiSettings.setAllGesturesEnabled(false)
                //map.animateCamera(CameraUpdateFactory.zoomTo(10.0f))
            }


        }
    }

    private val URL = "https://api.yelp.com/v3/businesses/" // add {id}
    private var businessItem : BusinessItem? = null
    private var currentBusinessDetailItem : BusinessDetailItem? = null
    private lateinit var map : GoogleMap


    companion object{
        fun newInstance(business : BusinessItem): BusinessDetailFragment{
            val businessDetailFragment : BusinessDetailFragment = BusinessDetailFragment()
            val args : Bundle = Bundle()
            args.putParcelable("business" , business)
            businessDetailFragment.arguments = args
            return businessDetailFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.business_details_fragment, container, false)

        businessItem = arguments?.getParcelable("business")

        val newRequest = GsonRequest<BusinessDetailItem>(
            URL+ businessItem?.id, BusinessDetailItem::class.java, null,
            Response.Listener<BusinessDetailItem> {
                currentBusinessDetailItem = it
                (view.findViewById(R.id.tv_rating) as TextView).text = it.rating.toInt().toString() + getString(R.string.rating_tail)
                (view.findViewById(R.id.tv_price) as TextView).text = it.price + " " + getString(R.string.price_tail)
                if ( it.is_closed ){
                    (view.findViewById(R.id.tv_available) as TextView).text = getString(R.string.closed)
                }
                else (view.findViewById(R.id.tv_available) as TextView).text = getString(R.string.open)

                if (it.phone.count() > 0)
                    (view.findViewById(R.id.tv_phone) as TextView).text = it.phone
                (view.findViewById(R.id.detail_toolbar_title) as TextView).text = it.name

                val mPager : ViewPager =  view.findViewById(R.id.photos_business_pager)
                val mPhotosBusinessAdapter : PhotosBusinessAdapter = PhotosBusinessAdapter(this.requireContext(), it.photos.toList())
                mPager.adapter = mPhotosBusinessAdapter
                //val mloopPagerAdapter : LoopPagerAdapter = LoopPagerAdapter(mPhotosBusinessAdapter)
                //mPager.adapter = mloopPagerAdapter
                //mPager.setCurrentItem(mloopPagerAdapter.infiniteCenteredItem)

                val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment!!.getMapAsync(this)
            },
            Response.ErrorListener {
                print("NOT OK")
            })
        //launch request
        RequestsManager.getInstance(this.requireContext()).addToRequestQueue(newRequest)

        return view
    }

}