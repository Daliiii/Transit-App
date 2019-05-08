package de.door2door.androidcandidatetest.features.list.view

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import de.door2door.androidcandidatetest.R
import de.door2door.androidcandidatetest.features.list.model.RouteModel
import de.door2door.androidcandidatetest.features.maps.view.MapActivity

class RouteListAdapter(private val context: Context, private val items: List<RouteModel>) :
    RecyclerView.Adapter<RouteListAdapter.ViewHolder>() {
    companion object {
        const val ROUTE_ID = "route_id"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.route_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.departureTime.text = item.departureTime
        holder.departureLocation.text = item.departureStop
        holder.duration.text = context.getString(R.string.time_min, item.duration)
        holder.changeNumberLabel.text = context.resources.getQuantityString(R.plurals.changes_number, item.changeNumber,item.changeNumber)
        holder.destinationTime.text = item.arrivalTime
        holder.destinationLocation.text = item.arrivalStop
        holder.transportType.text = item.transportType
        holder.parentLayout.setOnClickListener {
            val intent = Intent(context, MapActivity::class.java)
            intent.putExtra(ROUTE_ID, position)
            context.startActivity(intent)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val departureTime: TextView = view.findViewById(R.id.departureTime)
        val changeNumberLabel: TextView = view.findViewById(R.id.changeNumberLabel)
        val departureLocation: TextView = view.findViewById(R.id.departureLocation)
        val duration: TextView = view.findViewById(R.id.duration)
        val destinationTime: TextView = view.findViewById(R.id.destinationTime)
        val destinationLocation: TextView = view.findViewById(R.id.destinationLocation)
        val transportType: TextView = view.findViewById(R.id.transportType)
        val parentLayout: LinearLayout = view.findViewById(R.id.parentLayout)
    }
}