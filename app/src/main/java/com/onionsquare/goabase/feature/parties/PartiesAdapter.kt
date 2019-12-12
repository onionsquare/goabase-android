package com.onionsquare.goabase.feature.parties

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.onionsquare.goabase.R
import com.onionsquare.goabase.model.Party
import kotlinx.android.synthetic.main.party_item.view.*
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class PartiesAdapter(val items: ArrayList<Party>, val listener: PartyClickListener) : RecyclerView.Adapter<PartiesAdapter.PartyViewHolder>(), Observer<List<Party>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
        return PartyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.party_item, parent, false), listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onChanged(parties: List<Party>) {
        items.clear()
        items.addAll(parties)
        notifyDataSetChanged()
    }

    class PartyViewHolder(itemView: View, val listener: PartyClickListener) : RecyclerView.ViewHolder(itemView) {

        fun bind(party: Party) {
            val date = OffsetDateTime.parse(party.dateStart).toLocalDateTime()
            val txt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(date)

            itemView.apply {
                party_name.text = party.nameParty
                party_country.text = " ${party.nameTown}"
                party_date.text = txt
                party_picture.load(party.urlImageMedium){
                    crossfade(true)
                }
                setOnClickListener {
                    listener.onPartySelected(party)
                }
            }
        }
    }

    interface PartyClickListener {
        fun onPartySelected(party: Party)
    }
}