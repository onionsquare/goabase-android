package com.onionsquare.psyaround.feature.country

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.heetch.countrypicker.Utils
import com.onionsquare.psyaround.R
import com.onionsquare.psyaround.model.Country

class CountryAdapter(val items: List<Country>, val listener: CountryClickListener) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false), listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class CountryViewHolder(itemView: View, val listener: CountryClickListener) : RecyclerView.ViewHolder(itemView) {

        var name = itemView.findViewById<TextView>(R.id.country_name)
        var count = itemView.findViewById<TextView>(R.id.party_count)
        var flag = itemView.findViewById<ImageView>(R.id.party_country_flag)

        fun bind(country: Country) {
            name.text = country.nameCountry
            count.text = itemView.context.resources.getQuantityString(R.plurals.numberOfParties, country.numParties.toInt(), country.numParties.toInt())

            flag.setImageDrawable(itemView.context.getDrawable(Utils.getMipmapResId(itemView.context, country.isoCountry.toLowerCase() + "_flag")))

            itemView.setOnClickListener { listener.onClick(country) }
        }
    }

    interface CountryClickListener {
        fun onClick(country: Country)
    }

}