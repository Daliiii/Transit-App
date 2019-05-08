package de.door2door.androidcandidatetest.features.maps.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.door2door.androidcandidatetest.R
import de.door2door.androidcandidatetest.features.maps.model.PublicTransportStop
import de.door2door.androidcandidatetest.utils.DateHelper

class StopAdapter(private val context: Context, private val items: List<PublicTransportStop>) :
    RecyclerView.Adapter<StopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.stop_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.stopText.text = item.stopName
        holder.stopTime.text = DateHelper.formatDate(item.stopTime)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stopText: TextView = view.findViewById(R.id.stopText)
        val stopTime: TextView = view.findViewById(R.id.stopTime)
    }
}