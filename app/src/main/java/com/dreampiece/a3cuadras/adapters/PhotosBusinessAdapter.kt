package com.dreampiece.a3cuadras.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.dreampiece.a3cuadras.R
import com.squareup.picasso.Picasso

class PhotosBusinessAdapter(private val context: Context, private var photos : List<String>) : PagerAdapter() {


    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflate = LayoutInflater.from(context).inflate(R.layout.photo_business_view_pager, container, false)
        val ivphoto = inflate.findViewById(R.id.ivBusinessPhoto) as ImageView

        Picasso.get().load(photos[position]).into(ivphoto)

        container.addView(inflate)
        return inflate
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return photos.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == o
    }



}
