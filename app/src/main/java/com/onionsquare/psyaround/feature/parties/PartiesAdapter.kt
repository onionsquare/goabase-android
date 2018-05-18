package com.onionsquare.psyaround.feature.parties

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.heetch.countrypicker.Utils
import com.onionsquare.psyaround.R
import com.onionsquare.psyaround.model.Party
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class PartiesAdapter(val items: List<Party>, val listener: PartyClickListener) : RecyclerView.Adapter<PartiesAdapter.PartyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
        return PartyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.party_item, parent, false), listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class PartyViewHolder(itemView: View, val listener: PartyClickListener) : RecyclerView.ViewHolder(itemView) {

        val partyName = itemView.findViewById<TextView>(R.id.party_name)
        val partyPicture = itemView.findViewById<SimpleDraweeView>(R.id.party_picture)
        val partyDate = itemView.findViewById<TextView>(R.id.party_date)
        val partyCountry = itemView.findViewById<TextView>(R.id.party_country)
        val partyFlag = itemView.findViewById<ImageView>(R.id.party_country_flag)
        val openMap = itemView.findViewById<Button>(R.id.open_map)

        fun bind(party: Party) {
            partyName.text = party.nameParty
            partyCountry.text = "${party.nameCountry} • ${party.nameTown}"

            val date = OffsetDateTime.parse(party.dateStart).toLocalDateTime()
            val txt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(date)
            partyDate.text = txt
            partyFlag.setImageDrawable(itemView.context.getDrawable(Utils.getMipmapResId(itemView.context, party.isoCountry.toLowerCase() + "_flag")))
            party.urlImageMedium?.let {
                val uri = Uri.parse(it)
                partyPicture.setImageURI(uri)
            }
            openMap.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:${party.geoLat},${party.geoLon}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.`package` = "com.google.android.apps.maps"
                itemView.context.startActivity(mapIntent)
            }
        }
    }

    interface PartyClickListener {
        fun onClick(party: Party)
    }
}