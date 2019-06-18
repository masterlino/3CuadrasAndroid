package com.example.a3cuadras.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.a3cuadras.R
import com.example.a3cuadras.model.BusinessItem
import com.squareup.picasso.Picasso
import java.math.BigDecimal
import java.math.RoundingMode


class BusinessAdapter(private var items: List<BusinessItem>, val listener: (BusinessItem, Int) -> Unit):RecyclerView.Adapter<BusinessAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.business_detail_cardview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val business = items[position]
        viewHolder.tvName.text = business.name
        viewHolder.tvDistance.text = BigDecimal(business.distance).setScale(2, RoundingMode.HALF_EVEN).toString()

        if (business.favorite){
            viewHolder.ivFavorite.setImageResource(R.drawable.heart_on)
        }
        else
            viewHolder.ivFavorite.setImageResource(R.drawable.heart_off)

        if (business.image_url.isNotEmpty()){
            Picasso.get().load(business.image_url).into(viewHolder.ivBusiness)
        }

        viewHolder.ivFavorite.setOnClickListener{
            listener(business, 1) //action 1 favorites
            //save on DB and change icon
            viewHolder.ivFavorite.setImageResource(R.drawable.heart_on)


        }

        viewHolder.rlCardView.setOnClickListener{
            listener(business, 0) //action 0 Details
            //open detail activity
        }
    }

    fun updateItemsAdapter (newItems : List<BusinessItem>){
        this.items = newItems
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var rlCardView : RelativeLayout = itemView.findViewById(R.id.rl_card)
        var tvName : TextView = itemView.findViewById(R.id.tv_business_name)
        var tvDistance : TextView = itemView.findViewById(R.id.tv_business_distance)
        var ivBusiness : ImageView = itemView.findViewById(R.id.iv_business)
        var ivFavorite : ImageView = itemView.findViewById(R.id.iv_business_favorite)
    }
}